package com.company;

import com.sun.net.httpserver.HttpContext;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import com.sun.net.httpserver.HttpServer;

import java.io.*;
import java.net.*;
import java.nio.charset.StandardCharsets;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadPoolExecutor;

public class Connection {

    BufferedReader request;
    PrintWriter response;

    public void startServer()
    {
        try (ServerSocket serverSocket = new ServerSocket(8080)){
            System.out.println("SERVER OPENED");
            
            Socket clientSocket = serverSocket.accept();

            try {
                request = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
                response = new PrintWriter(clientSocket.getOutputStream(), true);
                System.out.println("CLIENT CONNECTED");
            }
            catch (IOException exception) {
                System.out.println("CLIENT COULDN'T CONNECT");
            }
        }
        catch (IOException e) {
            System.out.println("COULDN'T START SERVER");
        }
    }
}
