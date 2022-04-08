package com.company;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import com.sun.net.httpserver.HttpServer;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;

public class HttpContextAuthentication implements HttpContextBasics {

    private final HttpServer server;

    public HttpContextAuthentication(HttpServer server)
    {
        this.server = server;
        createContexts();
    }


    @Override
    public void createContexts() {
        context_authentication_login();
    }

    private void context_authentication_login()
    {
        server.createContext("/authentication/login", new HttpHandler() {
            @Override
            public void handle(HttpExchange exchange) throws IOException {
                System.out.println("REQUEST RECEIVED ON /authentication/login");

                BufferedReader request = new BufferedReader(new InputStreamReader(exchange.getRequestBody()));
                String requestLine = request.readLine();

                String emailOrUsername = HttpAuthenticationMethods.extractLoginEmailOrUsernameFromJson(requestLine);
                String password = HttpAuthenticationMethods.extractPasswordFromJson(requestLine);

                String responseMessage = "RESPONSE FROM SERVER ON /AUTHENTICATION/LOGIN";

                exchange.sendResponseHeaders(200, responseMessage.length());
                DataOutputStream response = new DataOutputStream(exchange.getResponseBody());
                response.writeBytes(responseMessage);
                response.flush();
                response.close();
            }
        });
    }
}
