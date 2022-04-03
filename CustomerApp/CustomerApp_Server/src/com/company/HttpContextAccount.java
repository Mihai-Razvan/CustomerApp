package com.company;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import com.sun.net.httpserver.HttpServer;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;

public class HttpContextAccount {

    private final HttpServer server;

    public HttpContextAccount(HttpServer server)
    {
        this.server = server;
        createContexts();
    }

    private void createContexts()
    {
        context_account_locations_new();
    }

    ///////////////////////////////////////////////CONTEXTS/////////////////////////////////////////////


    private void context_account_locations_new()
    {
        server.createContext("/account/locations/new", new HttpHandler() {
            @Override
            public void handle(HttpExchange exchange) throws IOException {

                System.out.println("REQUEST RECEIVED ON /account/locations/new");
                BufferedReader request = new BufferedReader(new InputStreamReader(exchange.getRequestBody()));
                String requestLine = request.readLine();       //the request contains only one line, the location

                DatabasePOST.postLocation(requestLine);
            }
        });
    }
}
