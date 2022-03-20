package com.example.customerapp_client;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;


public class Connection implements Runnable {

    public static void connect2()
    {

    }

    public static void connect()
    {
        try {
            URL url = new URL("https://192.168.1.2:8080/test");
            HttpURLConnection con = (HttpURLConnection) url.openConnection();
            con.setRequestMethod("GET");
            con.setRequestProperty("Authorization", "Basic YXZha2phbkBtYWlsLnJ1OnBhc3N3b3Jk");
            int status = con.getResponseCode();
            System.out.println("RESPONSE: " + status);

            con.setDoOutput(true);
            DataOutputStream requestStream = new DataOutputStream(con.getOutputStream());
            requestStream.writeBytes("REQUEST FROM ANDROID");
            requestStream.flush();
            requestStream.close();
//
//            BufferedReader responseStream = new BufferedReader(new InputStreamReader(con.getInputStream()));
//            String inputLine;
//
//            while((inputLine = responseStream.readLine()) != null)
//                System.out.println(inputLine);
//
//            responseStream.close();
            con.disconnect();
        }
        catch (MalformedURLException e) {
            System.out.println("MalformedURLException: " + e.getMessage());
        }
        catch (IOException e) {
            System.out.println("IOException: " + e.getMessage());
        }
    }

    public void run()
    {
        connect();
    }
}
