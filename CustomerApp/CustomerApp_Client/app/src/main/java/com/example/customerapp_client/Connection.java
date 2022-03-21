package com.example.customerapp_client;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.InetAddress;
import java.net.MalformedURLException;
import java.net.URL;



public class Connection implements Runnable {

    public static void connect2()
    {

    }

    public static void connect()
    {
        try {
            URL url = new URL("http://192.168.1.2:8080/test");       //http://192.168.1.2:8080/test
            HttpURLConnection con = (HttpURLConnection) url.openConnection();
            con.setRequestMethod("POST");
            con.setRequestProperty("Authorization", "Basic YXZha2phbkBtYWlsLnJ1OnBhc3N3b3Jk");

//            DataOutputStream requestStream = new DataOutputStream(con.getOutputStream());
//            requestStream.writeBytes("REQUEST FROM ANDROID");
//            requestStream.flush();
//            requestStream.close();

            con.setConnectTimeout(2000);
            int status = con.getResponseCode();
            System.out.println("RESPONSE STATUS: " + status);
//
//        //     con.setDoOutput(true);
//
//
//            BufferedReader responseStream = new BufferedReader(new InputStreamReader(con.getInputStream()));
//            String inputLine;
//            while((inputLine = responseStream.readLine()) != null)
//                System.out.println(inputLine);
//            responseStream.close();
//            con.disconnect();
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
