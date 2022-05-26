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
        context_authentication_register();
    }

    private void context_authentication_login()
    {
        server.createContext("/authentication/login", new HttpHandler() {
            @Override
            public void handle(HttpExchange exchange) throws IOException {
                System.out.println("REQUEST RECEIVED ON /authentication/login");

                BufferedReader request = new BufferedReader(new InputStreamReader(exchange.getRequestBody()));
                String requestLine = request.readLine();

                String emailOrUsername = MethodsAuthentication.extractLoginEmailOrUsernameFromJson(requestLine);
                String password = MethodsAuthentication.extractPasswordFromJson(requestLine);

                int logInResponseCode = DatabaseGET.logInUser(emailOrUsername, password);
                String responseMessage = Integer.toString(logInResponseCode);

                exchange.sendResponseHeaders(200, responseMessage.length());
                DataOutputStream response = new DataOutputStream(exchange.getResponseBody());
                response.writeBytes(responseMessage);
                response.flush();
                response.close();
            }
        });
    }

    private void context_authentication_register()
    {
        server.createContext("/authentication/register", new HttpHandler() {
            @Override
            public void handle(HttpExchange exchange) throws IOException {
                System.out.println("REQUEST RECEIVED ON /authentication/register");

                BufferedReader request = new BufferedReader(new InputStreamReader(exchange.getRequestBody()));
                String requestLine = request.readLine();

                String email = MethodsAuthentication.extractEmailFromJson(requestLine);
                String username = MethodsAuthentication.extractUsernameFromJson(requestLine);
                String password = MethodsAuthentication.extractPasswordFromJson(requestLine);

                int logInResponseCode = DatabasePOST.registerUser(email, username, password);
                String responseMessage = Integer.toString(logInResponseCode);

                exchange.sendResponseHeaders(200, responseMessage.length());
                DataOutputStream response = new DataOutputStream(exchange.getResponseBody());
                response.writeBytes(responseMessage);
                response.flush();
                response.close();
            }
        });
    }
}
