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

public class HttpContextIndex implements HttpContextBasics{

    private HttpServer server;

    public HttpContextIndex(HttpServer server)
    {
        this.server = server;
        createContexts();
    }

    @Override
    public void createContexts() {
        context_index_addresses();
    }

    private void context_index_addresses()
    {
        server.createContext("/index/addresses", new HttpHandler() {
            @Override
            public void handle(HttpExchange exchange) throws IOException {
                System.out.println("REQUEST RECEIVED ON /index/addresses");

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
