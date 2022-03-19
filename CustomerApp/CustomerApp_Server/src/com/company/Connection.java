package com.company;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import com.sun.net.httpserver.HttpServer;

import java.io.IOException;
import java.net.*;
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
            }
        });

        ThreadPoolExecutor threadPoolExecutor = (ThreadPoolExecutor) Executors.newFixedThreadPool(10);
        server.setExecutor(threadPoolExecutor);
        server.start();

        System.out.println("Server started");
    }
}
