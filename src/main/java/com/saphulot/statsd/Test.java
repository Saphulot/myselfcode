package com.saphulot.statsd;

import com.fasterxml.jackson.core.JsonProcessingException;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import org.apache.commons.io.IOUtils;

@Slf4j
public class Test {
    private static HttpClient client;
    private static int lastJsonConfigTs;
    private Map<String, Object> adJsonConfigs;

//    private boolean getJsonConfigs() {
//        HttpClient.HttpResponse response;
//        boolean update = false;
//
//        if (this.client == null) {
//            return update;
//        }
//
//        try {
//            String uripath = "agent/jmx/configs?timestamp=" + lastJsonConfigTs;
//            response = client.request("GET", "", uripath);
//            if (!response.isResponse2xx()) {
//                log.warn(
//                        "Failed collecting JSON configs: ["
//                                + response.getResponseCode()
//                                + "] "
//                                + response.getResponseBody());
//                return update;
//            } else if (response.getResponseCode() == 204) {
//                log.debug("No configuration changes...");
//                return update;
//            }
//
//            InputStream jsonInputStream = IOUtils.toInputStream(response.getResponseBody(), StandardCharsets.UTF_8);
//            JsonParser parser = new JsonParser(jsonInputStream);
//            int timestamp = ((Integer) parser.getJsonTimestamp()).intValue();
//            if (timestamp > lastJsonConfigTs) {
//                adJsonConfigs = (Map<String, Object>) parser.getJsonConfigs();
//                lastJsonConfigTs = timestamp;
//                update = true;
//                log.info("update is in order - updating timestamp: " + lastJsonConfigTs);
//                for (String checkName : adJsonConfigs.keySet()) {
//                    log.debug("received config for check '" + checkName + "'");
//                }
//            }
//        } catch (JsonProcessingException e) {
//            log.error("error processing JSON response: " + e);
//        } catch (IOException e) {
//            log.error("unable to collect remote  configs: " + e);
//        }
//
//        return update;
//    }
    public static void main(String[] args) throws IOException {
        String host = args[0];
        int port = Integer.parseInt(args[1]); // dataview-agent中dogstatsd端口
        List<Metric> metrics = FileIO.readFileContent(args[2]);
        StatsdReporter reporter = new StatsdReporter(host,port);
        //client = new HttpClient(appConfig.getIpcHost(), appConfig.getIpcPort(), false);

        while (true) {

            reporter.sendMetrics(metrics, "Test_instance", true);

            System.out.println("report success!");
            try {
                Thread.sleep(5000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

        }
    }


}
