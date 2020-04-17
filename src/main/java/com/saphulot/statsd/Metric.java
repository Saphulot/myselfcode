package com.saphulot.statsd;

import java.util.Arrays;

public class Metric {
    private final String alias;
    private final String metricType;
    private final String[] tags;
    private final String checkName;
    private double value;


    @Override
    public String toString() {
        return "Metric{" +
                "alias='" + alias + '\'' +
                ", metricType='" + metricType + '\'' +
                ", tags=" + Arrays.toString(tags) +
                ", checkName='" + checkName + '\'' +
                ", value=" + value +
                '}';
    }

    /**
     * Metric constructor.
     */
    public Metric(String alias, String metricType, String[] tags, String checkName, double value) {
        this.alias = alias;
        this.metricType = metricType;
        this.tags = tags;
        this.checkName = checkName;
        this.value = value;
    }

    public String getAlias() {
        return alias;
    }

    public String getMetricType() {
        return metricType;
    }

    public double getValue() {
        return value;
    }

    public String[] getTags() {
        return tags;
    }

    public String getCheckName() {
        return checkName;
    }

    public void setValue(double value) {
        this.value = value;
    }
}
