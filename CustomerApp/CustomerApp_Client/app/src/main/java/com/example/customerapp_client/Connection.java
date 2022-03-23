package com.example.customerapp_client;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.HttpURLConnection;
import java.net.InetAddress;
import java.net.MalformedURLException;
import java.net.Socket;
import java.net.URL;



public class Connection implements Runnable {

    PrintWriter response;
    BufferedReader request;

    public void connect()
    {
        try {
            Socket socket = new Socket("localhost", 8080);
//            request = new BufferedReader(new InputStreamReader(socket.getInputStream()));
//            response = new PrintWriter(socket.getOutputStream(), true);

            System.out.println("CONNECTED");

            while(true)
            {
                String echo = request.readLine();
            }
        }
        catch (IOException exception) {
            System.out.println("COULDN'T CONNECT");
        }
    }

    public void run()
    {
        connect();
    }
}
