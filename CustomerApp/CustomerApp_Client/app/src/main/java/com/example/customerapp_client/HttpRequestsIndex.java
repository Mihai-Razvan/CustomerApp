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

public class HttpRequestsIndex implements Runnable, HttpRequestBasics{

    private final String path;
    private String status;
    private ArrayList<String> addressesList;
    private ArrayList<String> indexesList;

    public HttpRequestsIndex(String path)
    {
        this.path = path;
        this.addressesList = new ArrayList<>();
        this.indexesList = new ArrayList<>();
    }

    @Override
    public void run() {
        choosePath();
    }

    @Override
    public void choosePath() {
        if (path.equals("/index/addresses"))
            path_index_addresses();
        else if(path.equals("/index/indexes"))
            path_index_indexes();
    }

    private void path_index_addresses()
    {
        try {
            URL url = new URL(GlobalManager.httpNGROKAddress() + "/index/addresses");            //http://10.0.2.2:8080/bills
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");
            connection.setRequestProperty("Content-Type", "application/json; utf-8");
            connection.setRequestProperty("Accept", "application/json");
            connection.setDoOutput(true);
            connection.setConnectTimeout(2000);

            DataOutputStream request = new DataOutputStream(connection.getOutputStream());
            String message =  parseClientIdToJson();
            request.writeBytes(message);
            request.flush();
            request.close();

            BufferedReader response = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            String responseLine = response.readLine();
            System.out.println(responseLine);
            parseAddressesListJson(responseLine);

            status = "Successful";
        }
        catch (IOException e) {
            status = "Failed";
            System.out.println("COULDN'T SEND HTTP REQUEST: " + e.getMessage());
        }
        catch (NullPointerException e)     //this happens if the response is a null string, so there was a db error and couldn't extract addresses
        {
            status = "Failed";
            System.out.println("INTERNAL SERVER ERROR" + e.getMessage());
        }
    }

    private void path_index_indexes()
    {
        try {
            URL url = new URL(GlobalManager.httpNGROKAddress() + "/index/indexes");            //http://10.0.2.2:8080/bills
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");
            connection.setRequestProperty("Content-Type", "application/json; utf-8");
            connection.setRequestProperty("Accept", "application/json");
            connection.setDoOutput(true);
            connection.setConnectTimeout(2000);

            DataOutputStream request = new DataOutputStream(connection.getOutputStream());
            String message =  parseClientIdToJson();
            request.writeBytes(message);
            request.flush();
            request.close();

            BufferedReader response = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            String responseLine = response.readLine();
            System.out.println(responseLine);
            parseIndexesListJson(responseLine);

            status = "Successful";
        }
        catch (IOException e) {
            status = "Failed";
            System.out.println("COULDN'T SEND HTTP REQUEST: " + e.getMessage());
        }
        catch (NullPointerException e)     //this happens if the response is a null string, so there was a db error and couldn't extract addresses
        {
            status = "Failed";
            System.out.println("INTERNAL SERVER ERROR" + e.getMessage());
        }
    }

    ///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    private String parseClientIdToJson()
    {
        return "{'clientId': " + GlobalManager.getClientId() + "}";
    }

    private void parseAddressesListJson(String input)
    {
        JsonObject jsonObject = JsonParser.parseString(input).getAsJsonObject();
        addressesList.clear();
        int numOfAddresses = jsonObject.get("numOfAddresses").getAsInt();

        for(int i = 0; i < numOfAddresses; i++)  //bills get their id indexed from 0 in json
        {
            String key = "id" + Integer.toString(i);
            JsonObject jsonBill = jsonObject.getAsJsonObject(key);
            String addressName = jsonBill.get("name").getAsString();

            addressesList.add(addressName);
        }
    }

    private void parseIndexesListJson(String input)
    {
        JsonObject jsonObject = JsonParser.parseString(input).getAsJsonObject();
        indexesList.clear();
        int numOfAddresses = jsonObject.get("numOfAddresses").getAsInt();

        for(int i = 0; i < numOfAddresses; i++)  //bills get their id indexed from 0 in json
        {
            String key = "id" + Integer.toString(i);
            JsonObject jsonBill = jsonObject.getAsJsonObject(key);
            String addressName = jsonBill.get("value").getAsString();

            indexesList.add(addressName);
        }
    }

    public ArrayList<String> getAddressesList() {return addressesList;}

    public String getStatus() {return status;}
}
