package com.company;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import com.sun.net.httpserver.HttpServer;

import java.io.IOException;
import java.net.BindException;
import java.net.InetSocketAddress;
import java.net.URL;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadPoolExecutor;

public class Connection {

    private HttpServer server;

    public void startServer()
    {
        try {
            server = HttpServer.create(new InetSocketAddress("localhost", 8000), 0);
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
                System.out.println("Request received");
            }
        });

        ThreadPoolExecutor threadPoolExecutor = (ThreadPoolExecutor) Executors.newFixedThreadPool(10);
        server.setExecutor(threadPoolExecutor);
        server.start();

        System.out.println("Server started");
    }
}
