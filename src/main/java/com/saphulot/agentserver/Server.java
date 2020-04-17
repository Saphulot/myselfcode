package com.saphulot.agentserver;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import com.sun.net.httpserver.HttpServer;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.InetSocketAddress;
import java.net.SocketAddress;
import java.nio.charset.StandardCharsets;

public class Server {
    public static void main(String[] args) throws IOException {
        HttpServer server = HttpServer.create(new InetSocketAddress(8001),1);
        server.createContext("/send_metric",new Handel());
        server.start();
    }

    static class Handel implements HttpHandler{
        @Override
        public void handle(HttpExchange httpExchange) throws IOException {
            System.out.println("recived");
            String  resp = "recived metric success!";
            InputStream ins = httpExchange.getRequestBody();
//            byte[] temp = new byte[ins.available()];
//            ins.read(temp);
//            System.out.println(new String(temp));
            InputStreamReader reader = new InputStreamReader(ins, StandardCharsets.UTF_8);
            StringBuffer sb = new StringBuffer();
            while (reader.ready()){
                sb.append((char) reader.read());
            }
            System.out.println(sb);

            httpExchange.sendResponseHeaders(200,0);
            OutputStream os = httpExchange.getResponseBody();
            os.write(resp.getBytes());
            os.close();
        }
    }
}
