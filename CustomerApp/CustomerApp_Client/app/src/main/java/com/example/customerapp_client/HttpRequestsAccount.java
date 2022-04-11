package com.example.customerapp_client;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class HttpRequestsAccount implements Runnable, HttpRequestBasics {

    private final String path;
    private final int clientId;
    private String connectionStatus;

    private String address;     //used for /account/locations/new requests

    public HttpRequestsAccount(String path, String address, int clientId) {      //used for /account/locations/new requests
        this.path = path;
        this.address = address;
        this.clientId = clientId;
        this.connectionStatus = "Failed";
    }

    @Override
    public void run() {
        choosePath();
    }

    @Override
    public void choosePath() {
        if (path.equals("/account/locations/new")) {
            path_addresses_new();
        }
    }


    ///////////////PATHS///////////////

    private void path_addresses_new() {
        try {
            URL url = new URL("http://c2de-2a02-2f0c-5700-d000-88fe-b666-ab70-3957.ngrok.io/account/addresses/new");            //http://10.0.2.2:8080/account/locations/new
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("POST");
            connection.setRequestProperty("Content-Type", "application/json; utf-8");
            connection.setRequestProperty("Accept", "application/json");
            connection.setDoOutput(true);
            connection.setConnectTimeout(2000);

            DataOutputStream request = new DataOutputStream(connection.getOutputStream());
            String message = parseNewLocationRequestToJson();
            request.writeBytes(message);
            request.flush();
            request.close();

            BufferedReader response = new BufferedReader(new InputStreamReader(connection.getInputStream())); //this line has to be kept to complet the http request-response cycle
            connectionStatus = "Successful";
        }
        catch (IOException e) {
            System.out.println("COULDN'T SEND HTTP REQUEST: " + e.getMessage());
        }

    }


    //////////////////////////////////////////////////////////////////////////////////////////////

    public String getConnectionStatus() {
        return connectionStatus;
    }

    private String parseNewLocationRequestToJson()
    {
        return "{'clientId': " + clientId +
              ", 'address': " + address + "}";
    }

}
