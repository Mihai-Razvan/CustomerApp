package com.company;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import com.sun.net.httpserver.HttpServer;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.sql.SQLException;
import java.util.ArrayList;

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
        context_account_addresses_new();
        context_account_addresses();
    }

    ///////////////////////////////////////////////CONTEXTS/////////////////////////////////////////////


    private void context_account_addresses_new()
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

    private void context_account_addresses()
    {
        server.createContext("/account/addresses", new HttpHandler() {
            @Override
            public void handle(HttpExchange exchange) throws IOException {

                System.out.println("REQUEST RECEIVED ON /account/addresses");
                BufferedReader request = new BufferedReader(new InputStreamReader(exchange.getRequestBody()));
                String requestLine = request.readLine();
                String responseMessage = "";

                try {
                    int clientId = HttpAccountMethods.extractClientIdFromJson(requestLine);
                    ArrayList<String> addressesList = DatabaseGET.getAllAddresses(clientId);   //could throw SQLException
                    responseMessage = HttpIndexMethods.addressListToJson(addressesList);
                }
                catch (SQLException e) {
                    System.out.println(e.getMessage());
                }

                exchange.sendResponseHeaders(200, responseMessage.length());
                DataOutputStream response = new DataOutputStream(exchange.getResponseBody());
                response.writeBytes(responseMessage);
                response.flush();
                response.close();
            }
        });
    }
}
