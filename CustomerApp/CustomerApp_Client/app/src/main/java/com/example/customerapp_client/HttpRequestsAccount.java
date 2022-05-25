package com.example.customerapp_client;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

public class HttpRequestsAccount implements Runnable, HttpRequestBasics {

    private final String path;
    private String connectionStatus;

    private String city;     //used for /account/locations/new requests
    private String street;
    private String number;
    private String details;

    private ArrayList<String> addressesList;    //they are in fullAddress form

    public HttpRequestsAccount(String path, String city, String street, String number, String details) {      //used for /account/addresses/new requests
        this.path = path;
        this.city = city;
        this.street = street;
        this.number = number;
        this.details = details;
        this.connectionStatus = "Failed";
    }

    public HttpRequestsAccount(String path) {      //used for /account/addresses requests
        this.path = path;
        this.connectionStatus = "Failed";
        addressesList = new ArrayList<>();
    }

    @Override
    public void run() {
        choosePath();
    }

    @Override
    public void choosePath() {
        if (path.equals("/account/addresses/new"))
            path_addresses_new();
        else if (path.equals("/account/addresses"))
            path_addresses();
    }


    ///////////////PATHS///////////////

    private void path_addresses_new() {
        try {
            URL url = new URL(GlobalManager.httpNGROKAddress() + "/account/addresses/new");
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("POST");
            connection.setRequestProperty("Content-Type", "application/json; utf-8");
            connection.setRequestProperty("Accept", "application/json");
            connection.setDoOutput(true);
            connection.setConnectTimeout(2000);

            DataOutputStream request = new DataOutputStream(connection.getOutputStream());
            String message = parseNewAddressRequestToJson();
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

    private void path_addresses() {
        try {
            URL url = new URL(GlobalManager.httpNGROKAddress() + "/account/addresses");
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");
            connection.setRequestProperty("Content-Type", "application/json; utf-8");
            connection.setRequestProperty("Accept", "application/json");
            connection.setDoOutput(true);
            connection.setConnectTimeout(2000);

            DataOutputStream request = new DataOutputStream(connection.getOutputStream());
            String message = parseAddressesRequestToJson();
            request.writeBytes(message);
            request.flush();
            request.close();

            BufferedReader response = new BufferedReader(new InputStreamReader(connection.getInputStream())); //this line has to be kept to complet the http request-response cycle
            String responseLine = response.readLine();
            parseAddressesListJson(responseLine);
            connectionStatus = "Successful";

            System.out.println("SUCCESSFULLY RETRIEVED ADDRESSES");
        }
        catch (IOException e) {
            System.out.println("COULDN'T RETRIEVE ADDRESSES: " + e.getMessage());
        }

    }


    //////////////////////////////////////////////////////////////////////////////////////////////

    public String getConnectionStatus() {
        return connectionStatus;
    }

    private String parseAddressesRequestToJson()
    {
        return "{'clientId': " + GlobalManager.getClientId() + "}";
    }

    private String parseNewAddressRequestToJson()
    {
        return "{'clientId': " + GlobalManager.getClientId() +
              ", 'city': '" + city + "'" +      //we put the values between '' because they can contain spaces and this would cause problems
              ", 'street': '" + street + "'" +
              ", 'number': '" + number + "'" +
              ", 'details': '" + details + "'}";
    }

    private void parseAddressesListJson(String input)
    {
        System.out.println(input);
        JsonObject jsonObject = JsonParser.parseString(input).getAsJsonObject();
        addressesList.clear();
        int numOfAddresses = jsonObject.get("numOfAddresses").getAsInt();

        for(int i = 0; i < numOfAddresses; i++)  //addresses get their id indexed from 0 in json
        {
            String key = "id" + Integer.toString(i);
            JsonObject jsonBill = jsonObject.getAsJsonObject(key);
            String fullAddress = jsonBill.get("fullAddress").getAsString();

            addressesList.add(fullAddress);
        }
    }

    public ArrayList<String> getAddressesList() {
        return addressesList;
    }

    public String getStatus()
    {
        return connectionStatus;
    }
}
