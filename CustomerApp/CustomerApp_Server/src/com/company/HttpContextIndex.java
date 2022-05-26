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
        context_index_indexes();
        context_index_new();
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
                    int clientId = MethodsAccount.extractClientIdFromJson(requestLine);
                    ArrayList<String> addressesList = DatabaseGET.getAllAddresses(clientId);   //could throw SQLException
                    responseMessage = MethodsIndex.addressListToJson(addressesList);
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

    private void context_index_indexes()
    {
        server.createContext("/index/indexes", new HttpHandler() {
            @Override
            public void handle(HttpExchange exchange) throws IOException {
                System.out.println("REQUEST RECEIVED ON /index/indexes");

                BufferedReader request = new BufferedReader(new InputStreamReader(exchange.getRequestBody()));
                String requestLine = request.readLine();
                String responseMessage = "";

                try {
                    int clientId = MethodsAccount.extractClientIdFromJson(requestLine);
                    ArrayList<DataIndex> indexesList = DatabaseGET.getAllIndexes(clientId);   //could throw SQLException
                    responseMessage = MethodsIndex.indexListToJson(indexesList);
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

    private void context_index_new()
    {
        server.createContext("/index/new", new HttpHandler() {
            @Override
            public void handle(HttpExchange exchange) throws IOException {
                System.out.println("REQUEST RECEIVED ON /index/new");

                BufferedReader request = new BufferedReader(new InputStreamReader(exchange.getRequestBody()));
                String requestLine = request.readLine();
                String responseMessage = "";

                try {
                    int clientId = MethodsIndex.extractClientIdFromJson(requestLine);
                    int indexValue = MethodsIndex.extractNewIndexFromJson(requestLine);
                    String addressName = MethodsIndex.extractFullAddressFromJson(requestLine);
                    DatabasePOST.postNewIndex(clientId, indexValue, addressName);       //could throw SQLException
                    responseMessage = "SUCCESS";
                }
                catch (SQLException e) {
                    e.printStackTrace();
                    responseMessage = "FAILED";
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
