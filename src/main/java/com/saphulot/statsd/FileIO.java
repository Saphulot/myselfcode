package com.saphulot.statsd;


import com.alibaba.fastjson.JSON;
import lombok.extern.slf4j.Slf4j;


import java.io.*;
import java.sql.Array;
import java.util.*;

@Slf4j
public class FileIO {
    public static List<Metric> readFileContent(String fileName) throws IOException {
        File jsonFile = new File(fileName);
        String jsonStr = "";
        log.info("————开始读取" + jsonFile.getPath() + "文件————");
        try {
            //File jsonFile = new File(fileName);
            FileReader fileReader = new FileReader(jsonFile);
            Reader reader = new InputStreamReader(new FileInputStream(jsonFile), "utf-8");
            int ch = 0;
            StringBuffer sb = new StringBuffer();
            while ((ch = reader.read()) != -1) {
                sb.append((char) ch);
            }
            fileReader.close();
            reader.close();
            jsonStr = sb.toString();
            log.info("————读取" + jsonFile.getPath() + "文件结束!————");
        } catch (Exception e) {
            log.info("————读取" + jsonFile.getPath() + "文件出现异常，读取失败!————");
            e.printStackTrace();
        }
        List<Map> jsonList = (List) JSON.parse(jsonStr);
        List<Metric> list = new ArrayList<>();
        for (Map m : jsonList) {
            String tags = m.get("tags").toString();
            String[] tagss = tags.substring(1,tags.length()-1).split(";");
            Metric metric = new Metric(m.get("alias").toString(),m.get("metricType").toString(), tagss ,
                    m.get("checkName").toString(),Double.valueOf(m.get("value").toString()));
            list.add(metric);
        }
        return list;
    }

    public static void main(String[] args) throws IOException {
        List<Metric> li = readFileContent("/Users/saphulot/Desktop/code/myselfcode/src/main/resources/test.json");
        System.out.println(li);
    }
}
