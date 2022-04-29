package com.example.customerapp_client;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class HttpRequestsAccount implements Runnable, HttpRequestBasics {

    private final String path;
    private String connectionStatus;

    private String address;     //used for /account/locations/new requests

    public HttpRequestsAccount(String path, String address) {      //used for /account/locations/new requests
        this.path = path;
        this.address = address;
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
            URL url = new URL(GlobalManager.httpNGROKAddress() + "/account/addresses/new");            //http://10.0.2.2:8080/account/locations/new
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

            System.out.println("SUCCESSFULLY ADDED ADDRESS TO DATABASE");
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
        return "{'clientId': " + GlobalManager.getClientId() +
              ", 'address': " + address + "}";
    }

}
