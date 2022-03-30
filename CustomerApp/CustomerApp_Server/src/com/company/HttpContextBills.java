package com.company;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import com.sun.net.httpserver.HttpServer;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;

public class HttpContextBills {

    private HttpServer server;

    public HttpContextBills(HttpServer server)
    {
        this.server = server;
        createContexts();
    }

    private void createContexts()
    {
        context_test();
    }

    private void context_test() {
        context_bills();
        context_bills_add();
    }

    private void context_bills()
    {
        server.createContext("/bills", new HttpHandler() {
            @Override
            public void handle(HttpExchange exchange) throws IOException {

                System.out.println("REQUEST RECEIVED ON /bills");
                BufferedReader request = new BufferedReader(new InputStreamReader(exchange.getRequestBody()));
                String requestLine = request.readLine();
                while (requestLine != null) {
                    System.out.println(requestLine);
                    requestLine = request.readLine();
                }

                System.out.println("");
                String responseMessage = "{'numOfBills': 3," +
                        "'test': testValue," +
                        "'id1': {'name': Marcu," +
                                "'value': 2000}," +
                        "'id2': {'name': John," +
                                "'value': 3000}" +
                        "}";
            //    String responseMessage = "{'name': Marcu, value: '2500', status: 'Unsolved'}";
                exchange.sendResponseHeaders(200, responseMessage.length());
                DataOutputStream response = new DataOutputStream(exchange.getResponseBody());
                response.writeBytes(responseMessage);
                response.flush();
                response.close();
            }
        });
    }


    private void context_bills_add()
    {
        server.createContext("/bills/add", new HttpHandler() {
            @Override
            public void handle(HttpExchange exchange) throws IOException {

                System.out.println("REQUEST RECEIVED ON /bills/test");
                BufferedReader request = new BufferedReader(new InputStreamReader(exchange.getRequestBody()));
                String requestLine = request.readLine();
                while (requestLine != null) {
                    System.out.println(requestLine);
                    requestLine = request.readLine();
                }

                System.out.println("");
                String responseMessage = "{'name': Marcu, value: '2500', status: 'Unsolved'}";
                exchange.sendResponseHeaders(200, responseMessage.length());
                DataOutputStream response = new DataOutputStream(exchange.getResponseBody());
                response.writeBytes(responseMessage);
                response.flush();
                response.close();
            }
        });
    }

}
