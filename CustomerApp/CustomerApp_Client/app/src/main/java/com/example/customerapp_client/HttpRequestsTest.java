package com.example.customerapp_client;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class HttpRequestsTest implements Runnable{

    private String path;

    public HttpRequestsTest(String path) {
        this.path = path;
    }

    private void choose_path()
    {
        if(path == "/test")
        {
            path_test();
        }
    }

    public void run()
    {
        choose_path();
        return;
    }


    private void path_test()
    {
        try {
            URL url = new URL("http://56fc-2a02-2f0c-5700-d000-e0cf-ad6a-dbb7-a3d1.ngrok.io/test");            //http://10.0.2.2:8080/test
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("POST");
            connection.setDoOutput(true);

            DataOutputStream request = new DataOutputStream(connection.getOutputStream());
            String message = "REQUEST FROM ANDROID";
            request.writeBytes(message);
            request.flush();
            request.close();

            BufferedReader response = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            String responseLine = response.readLine();
            while(responseLine != null)
            {
                System.out.println(responseLine);
                responseLine = response.readLine();
            }

        }
        catch (MalformedURLException e) {
            System.out.println("MalformedURLException: " + e.getMessage());
        }
        catch (IOException e) {
            System.out.println("IOException: " + e.getMessage());
        }
    }
}
