package com.example.customerapp_client;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class Connection implements Runnable {

    public static void connect()
    {
        try {
            URL url = new URL("https://brown-badger-82.loca.lt/test");
            HttpURLConnection con = (HttpURLConnection) url.openConnection();
            con.setRequestMethod("GET");
            con.setRequestProperty("Authorization", "Basic YXZha2phbkBtYWlsLnJ1OnBhc3N3b3Jk");
            int status = con.getResponseCode();
            System.out.println("RESPONSE: " + status);
//            con.setDoOutput(true);
            /*DataOutputStream out = new DataOutputStream(con.getOutputStream());
            out.writeBytes("REQUEST FROM ANDROID");
            out.flush();
            out.close();

            BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
            String inputLine;
            StringBuffer content = new StringBuffer();
            while((inputLine = in.readLine()) != null)
                content.append(inputLine);
            in.close();*/

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
