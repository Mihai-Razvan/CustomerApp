package com.company;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import com.sun.net.httpserver.HttpServer;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;

public class HttpContextTest {

    private HttpServer server;

    public HttpContextTest(HttpServer server)
    {
        this.server = server;
        createContexts();
    }

    private void createContexts()
    {
        context_test();
    }

    private void context_test()
    {
        server.createContext("/test", new HttpHandler() {
            @Override
            public void handle(HttpExchange exchange) throws IOException {

                System.out.println("REQUEST RECEIVED ON /test");
                BufferedReader request = new BufferedReader(new InputStreamReader(exchange.getRequestBody()));
                String requestLine = request.readLine();
                while(requestLine != null)
                {
                    System.out.println(requestLine);
                    requestLine = request.readLine();
                }

                System.out.println("");
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
