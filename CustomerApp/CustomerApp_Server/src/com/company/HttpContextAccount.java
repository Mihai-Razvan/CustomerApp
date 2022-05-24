package com.company;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import com.sun.net.httpserver.HttpServer;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class HttpContextAccount implements HttpContextBasics {

    private final HttpServer server;

    public HttpContextAccount(HttpServer server)
    {
        this.server = server;
        createContexts();
    }

    @Override
    public void createContexts()
    {
        context_addresses_address_new();
    }

    ///////////////////////////////////////////////CONTEXTS/////////////////////////////////////////////


    private void context_addresses_address_new()
    {
        server.createContext("/account/addresses/new", new HttpHandler() {
            @Override
            public void handle(HttpExchange exchange) throws IOException {

                System.out.println("REQUEST RECEIVED ON /account/addresses/new");
                BufferedReader request = new BufferedReader(new InputStreamReader(exchange.getRequestBody()));
                String requestLine = request.readLine();
                int clientId = HttpAccountMethods.extractClientIdFromJson(requestLine);
                String city = HttpAccountMethods.extractCityFromJson(requestLine);
                String street = HttpAccountMethods.extractStreetFromJson(requestLine);
                String number = HttpAccountMethods.extractNumberFromJson(requestLine);
                String details = HttpAccountMethods.extractDetailsFromJson(requestLine);

                DatabasePOST.postAddress(clientId, city, street, number, details);
            }
        });
    }
}
