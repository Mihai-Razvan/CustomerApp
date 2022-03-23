package com.company;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import com.sun.net.httpserver.HttpServer;

import java.io.*;
import java.net.*;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadPoolExecutor;

public class HttpConnection {

    HttpServer server;

    public void createServer() {
        try  {
            server = HttpServer.create(new InetSocketAddress("localhost", 8080), 0);
            createContexts(); //sets the http paths
            ThreadPoolExecutor threadPoolExecutor = (ThreadPoolExecutor) Executors.newFixedThreadPool(10);
            server.setExecutor(threadPoolExecutor);
            server.start();

            System.out.println("SERVER STARTED");

        } catch (IOException e) {
            System.out.println("IOException: " + e.getMessage());
        }
    }


    private void createContexts()
    {
        server.createContext("/test", new HttpHandler() {
            @Override
            public void handle(HttpExchange exchange) throws IOException {

                BufferedReader request = new BufferedReader(new InputStreamReader(exchange.getRequestBody()));
                String requestLine = request.readLine();
                while(requestLine != null)
                {
                    System.out.println(requestLine);
                    requestLine = request.readLine();
                }

                String responseMessage = "YOU REQUESTED /test";
                exchange.sendResponseHeaders(200, responseMessage.length());
                DataOutputStream response = new DataOutputStream(exchange.getResponseBody());
                response.writeBytes(responseMessage);
                response.flush();
                response.close();
            }
        });
    }

}
