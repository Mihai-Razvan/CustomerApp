package com.company;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import com.sun.net.httpserver.HttpServer;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class HttpContextBills implements HttpContextBasics {

    private final HttpServer server;

    public HttpContextBills(HttpServer server)
    {
        this.server = server;
        createContexts();
    }

    @Override
    public void createContexts()
    {
        context_bills();
    }

    ///////////////////////////////////////////////CONTEXTS/////////////////////////////////////////////


    private void context_bills()
    {
        server.createContext("/bills", new HttpHandler() {
            @Override
            public void handle(HttpExchange exchange) throws IOException {
                System.out.println("REQUEST RECEIVED ON /bills");

                String responseMessage = HttpBillsMethods.billListToJson(DatabaseGET.getAllBills());

                exchange.sendResponseHeaders(200, responseMessage.length());
                DataOutputStream response = new DataOutputStream(exchange.getResponseBody());
                response.writeBytes(responseMessage);
                response.flush();
                response.close();
            }
        });
    }

}
