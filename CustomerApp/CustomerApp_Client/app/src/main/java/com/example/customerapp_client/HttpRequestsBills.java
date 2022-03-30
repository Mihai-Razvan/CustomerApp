package com.example.customerapp_client;

import android.util.JsonReader;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class HttpRequestsBills implements Runnable {

    private String path;
    private String name;
    private String value;
    private String status;
    private String connectionStatus;

    public HttpRequestsBills(String path) {
        this.path = path;
        this.connectionStatus = "Failed";
    }


    @Override
    public void run()
    {
        choose_path();
    }

    private void choose_path()
    {
        if(path.equals("/bills"))
        {
            path_bills();
        }
        else if(path.equals("/bills/add"))
        {
            path_bills_add();
        }
    }


    ///////////////PATHS///////////////

    private void path_bills()
    {
        try {
            URL url = new URL("http://10.0.2.2:8080/bills");            //http://10.0.2.2:8080/test
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");
            connection.setRequestProperty("Content-Type", "application/json; utf-8");
            connection.setRequestProperty("Accept", "application/json");
            connection.setDoOutput(true);
            connection.setConnectTimeout(2000);

            DataOutputStream request = new DataOutputStream(connection.getOutputStream());
            String message = "REQUEST FROM ANDROID";
            request.writeBytes(message);
            request.flush();
            request.close();

            BufferedReader response = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            String responseLine = response.readLine();
            parseTest(responseLine);

            connectionStatus = "Successful";
        }
        catch (MalformedURLException e) {
            System.out.println("MalformedURLException: " + e.getMessage());
        }
        catch (IOException e) {
            System.out.println("IOException: " + e.getMessage());
        }

    }


    private void path_bills_add()
    {
        try {
            URL url = new URL("http://10.0.2.2:8080/bills/add");            //http://10.0.2.2:8080/test
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");
            connection.setRequestProperty("Content-Type", "application/json; utf-8");
            connection.setRequestProperty("Accept", "application/json");
            connection.setDoOutput(true);
            connection.setConnectTimeout(2000);

            DataOutputStream request = new DataOutputStream(connection.getOutputStream());
            String message = "REQUEST FROM ANDROID";
            request.writeBytes(message);
            request.flush();
            request.close();

            BufferedReader response = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            String responseLine = response.readLine();
            parseBillResponse(responseLine);

            connectionStatus = "Successful";
        }
        catch (MalformedURLException e) {
            System.out.println("MalformedURLException: " + e.getMessage());
        }
        catch (IOException e) {
            System.out.println("IOException: " + e.getMessage());
        }

    }

    private void parseTest(String input)
    {
        JsonObject jsonObject = JsonParser.parseString(input).getAsJsonObject();
        System.out.println(jsonObject.getAsJsonObject("id1").get("name"));

    }

    private void parseBillResponse(String input)
    {
        JsonObject jsonObject = JsonParser.parseString(input).getAsJsonObject();
        name = jsonObject.get("name").getAsString();
        value = jsonObject.get("value").getAsString();
        status = jsonObject.get("status").getAsString();
    }


    public String getName() {
        return name;
    }

    public String getValue() {
        return value;
    }

    public String getStatus() {
        return status;
    }

    public String getConnectionStatus() {
        return connectionStatus;
    }
}
