package com.company;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import com.sun.net.httpserver.HttpServer;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.*;
import java.nio.charset.StandardCharsets;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadPoolExecutor;

public class Connection {

    private HttpServer server;
    private int counter = 0;

    public void startServer()
    {
        try {
            server = HttpServer.create(new InetSocketAddress("localhost", 8080), 0);
        }
        catch(BindException e) {
            System.out.println("BindException: " + e.getMessage());
        }
        catch(IOException e) {
            System.out.println("IOException: " + e.getMessage());
        }

        server.createContext("/test", new HttpHandler() {
            @Override
            public void handle(HttpExchange exchange) throws IOException {
                counter++;
                System.out.println("Request received: " + counter);

                String message = "RESPONSE FROM SERVER";
                exchange.sendResponseHeaders(200, message.length());
                OutputStream response = exchange.getResponseBody();
                response.write(message.getBytes(StandardCharsets.UTF_8));
                /*    BufferedReader requestBody = new BufferedReader(new InputStreamReader(exchange.getRequestBody()));
                String line;
                line = requestBody.readLine();
                System.out.println(line);*/
            }
        });

        ThreadPoolExecutor threadPoolExecutor = (ThreadPoolExecutor) Executors.newFixedThreadPool(10);
        server.setExecutor(threadPoolExecutor);
        server.start();

        System.out.println("Server started");
    }
}
