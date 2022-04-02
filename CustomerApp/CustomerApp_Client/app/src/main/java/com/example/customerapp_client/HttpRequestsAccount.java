package com.example.customerapp_client;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

public class HttpRequestsAccount implements Runnable {

    private String path;
    private String connectionStatus;

    private String location;     //used for /account/locations/new requests

    public HttpRequestsAccount(String path) {
        this.path = path;
        this.connectionStatus = "Failed";
    }

    public HttpRequestsAccount(String path, String location) {      //used for /account/locations/new requests
        this.path = path;
        this.connectionStatus = "Failed";
        this.location = location;
    }

    @Override
    public void run() {
        choose_path();
    }

    private void choose_path() {
        if (path.equals("/account/locations/new")) {
            path_locations_new();
        }
    }


    ///////////////PATHS///////////////

    private void path_locations_new() {
        try {
            URL url = new URL("http://ea2a-2a02-2f0c-5700-d000-8448-c58-99a5-656.ngrok.io/account/locations/new");            //http://10.0.2.2:8080/account/locations/new
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("POST");
            connection.setRequestProperty("Content-Type", "application/json; utf-8");
            connection.setRequestProperty("Accept", "application/json");
            connection.setDoOutput(true);
            connection.setConnectTimeout(2000);

            DataOutputStream request = new DataOutputStream(connection.getOutputStream());
            String message = location;
            request.writeBytes(message);
            request.flush();
            request.close();

            BufferedReader response = new BufferedReader(new InputStreamReader(connection.getInputStream())); //this line has to be kept to complet the http request-response cycle
            connectionStatus = "Successful";
        } catch (MalformedURLException e) {
            System.out.println("COULDN'T SEND HTTP REQUEST: " + e.getMessage());
        } catch (IOException e) {
            System.out.println("COULDN'T SEND HTTP REQUEST: " + e.getMessage());
        }

    }


    //////////////////////////////////////////////////////////////////////////////////////////////

    public String getConnectionStatus() {
        return connectionStatus;
    }

}
