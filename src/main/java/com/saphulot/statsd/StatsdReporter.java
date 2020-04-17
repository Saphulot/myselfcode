package com.saphulot.statsd;
import com.timgroup.statsd.NonBlockingStatsDClient;
import com.timgroup.statsd.ServiceCheck;
import com.timgroup.statsd.StatsDClient;
import com.timgroup.statsd.StatsDClientErrorHandler;
import lombok.extern.slf4j.Slf4j;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
public class StatsdReporter {
    public static final String VALUE = "value";
    private Map<String, Map<String, Map<String, Object>>> ratesAggregator =
            new HashMap<String, Map<String, Map<String, Object>>>();
    private Map<String, Map<String, Long>> countersAggregator =
            new HashMap<String, Map<String, Long>>();
    private StatsDClient statsDClient;
    private String statsdHost;
    private int statsdPort;
    private long initializationTime;

    private class LoggingErrorHandler implements StatsDClientErrorHandler {

        @Override
        public void handle(Exception exception) {
            log.error("statsd client error:", exception);
        }
    }

    public StatsdReporter(String statsdHost, int statsdPort) {
        this.statsdHost = statsdHost;
        this.statsdPort = statsdPort;
        this.init();
    }

    private void init() {
        initializationTime = System.currentTimeMillis();

        // Only set the entityId to "none" if UDS communication is activated
        String entityId = this.statsdPort == 0 ? "none" : null;

        /* Create the StatsDClient with "entity-id" set to "none" to avoid
           having dogstatsd server adding origin tags, when the connection is
           done with UDS. */
        statsDClient =
                new NonBlockingStatsDClient(
                        null,
                        this.statsdHost,
                        this.statsdPort,
                        Integer.MAX_VALUE,
                        new String[] {},
                        new LoggingErrorHandler(),
                        entityId);
    }

    public void sendMetricPoint(
            String metricType, String metricName, double value, String[] tags) {
        if (System.currentTimeMillis() - this.initializationTime > 300 * 1000) {
            this.statsDClient.stop();
            init();
        }
        if (metricType.equals("monotonic_count")) {
            statsDClient.count(metricName, (long) value, tags);
        } else if (metricType.equals("histogram")) {
            statsDClient.histogram(metricName, value, tags);
        } else {
            statsDClient.gauge(metricName, value, tags);
        }
    }

    public void doSendServiceCheck(
            String serviceCheckName, String status, String message, String[] tags) {
        if (System.currentTimeMillis() - this.initializationTime > 300 * 1000) {
            this.statsDClient.stop();
            init();
        }

        ServiceCheck sc = ServiceCheck.builder()
                .withName(serviceCheckName)
                .withStatus(ServiceCheck.Status.OK)
                .withMessage(message)
                .withTags(tags)
                .build();

        statsDClient.serviceCheck(sc);
    }

//    public ServiceCheck.Status statusToServiceCheckStatus(String status) {
//        if (status == Status.STATUS_OK) {
//            return ServiceCheck.Status.OK;
//        } else if (status == Status.STATUS_WARNING) {
//            return ServiceCheck.Status.WARNING;
//        } else if (status == Status.STATUS_ERROR) {
//            return ServiceCheck.Status.CRITICAL;
//        }
//        return ServiceCheck.Status.UNKNOWN;
//    }

    String generateId(Metric metric) {
        String key = metric.getAlias();
        StringBuilder sb = new StringBuilder(key);
        for (String tag : metric.getTags()) {
            sb.append(tag);
        }
        return sb.toString();
    }

    public void sendMetrics(List<Metric> metrics, String instanceName, boolean canonicalRate) {
        Map<String, Map<String, Object>> instanceRatesAggregator;
        Map<String, Long> instanceCountersAggregator;

        if (ratesAggregator.containsKey(instanceName)) {
            instanceRatesAggregator = ratesAggregator.get(instanceName);
        } else {
            instanceRatesAggregator = new HashMap<String, Map<String, Object>>();
        }

        if (countersAggregator.containsKey(instanceName)) {
            instanceCountersAggregator = countersAggregator.get(instanceName);
        } else {
            instanceCountersAggregator = new HashMap<String, Long>();
        }

        for (Metric metric : metrics) {
            Double currentValue = (Double) metric.getValue();
            if (currentValue.isNaN() || currentValue.isInfinite()) {
                continue;
            }

            String metricName = metric.getAlias();
            String metricType = metric.getMetricType();
            String[] tags = metric.getTags();

            // StatsD doesn't support rate metrics so we need to have our own aggregator to compute
            // rates
            if ("gauge".equals(metricType) || "histogram".equals(metricType)) {
                sendMetricPoint(metricType, metricName, currentValue, tags);
            } else if ("monotonic_count".equals(metricType)) {
                String key = generateId(metric);
                if (!instanceCountersAggregator.containsKey(key)) {
                    instanceCountersAggregator.put(key,  currentValue.longValue());
                    continue;
                }

                long oldValue = instanceCountersAggregator.get(key);
                long delta = currentValue.longValue() - oldValue;

                if (Double.isNaN(delta) || Double.isInfinite(delta)) {
                    continue;
                }

                instanceCountersAggregator.put(key, currentValue.longValue());

                if (delta < 0) {
                    log.info("Counter " + metricName + " has been reset - not submitting.");
                    continue;
                }
                sendMetricPoint(metricType, metricName, delta, tags);

            } else { // The metric should be 'counter'
                String key = generateId(metric);
                if (!instanceRatesAggregator.containsKey(key)) {
                    Map<String, Object> rateInfo = new HashMap<String, Object>();
                    rateInfo.put("ts", System.currentTimeMillis());
                    rateInfo.put(VALUE, currentValue);
                    instanceRatesAggregator.put(key, rateInfo);
                    continue;
                }

                long oldTs = (Long) instanceRatesAggregator.get(key).get("ts");
                double oldValue = (Double) instanceRatesAggregator.get(key).get(VALUE);

                long now = System.currentTimeMillis();
                double rate = 1000 * (currentValue - oldValue) / (now - oldTs);

                boolean sane = (!Double.isNaN(rate) && !Double.isInfinite(rate));
                boolean submit = (rate >= 0 || !canonicalRate);

                if (sane && submit) {
                    sendMetricPoint(metricType, metricName, rate, tags);
                } else if (sane) {
                    log.info(
                            "Canonical rate option set, and negative rate (counter reset)"
                                    + "not submitting.");
                }

                instanceRatesAggregator.get(key).put("ts", now);
                instanceRatesAggregator.get(key).put(VALUE, currentValue);
            }
        }
        ratesAggregator.put(instanceName, instanceRatesAggregator);
        countersAggregator.put(instanceName, instanceCountersAggregator);
    }

    public String getStatsdHost() {
        return statsdHost;
    }

    public int getStatsdPort() {
        return statsdPort;
    }
}
