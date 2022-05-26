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
    int newIndexValue;
    String fullAddress;      //address for the new index     !!!!!!!1 TO BE CHANGED
    private ArrayList<String> addressesList;
    private ArrayList<DataIndex> indexesList;
    DataIndex newIndexData;


    public HttpRequestsIndex(String path)
    {
        this.path = path;
        this.addressesList = new ArrayList<>();
        this.indexesList = new ArrayList<>();
    }

    public HttpRequestsIndex(String path, int newIndexValue, String fullAddress)
    {
        this.path = path;
        this.newIndexValue = newIndexValue;
        this.fullAddress = fullAddress;
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
        else if(path.equals("/index/new"))
            path_index_new();
    }

    private void path_index_addresses()            //requests list of user addresses
    {
        try {
            URL url = new URL(GlobalManager.httpNGROKAddress() + "/index/addresses");
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
            System.out.println("INTERNAL SERVER ERROR: " + e.getMessage());
        }
    }

    private void path_index_indexes()         //request all user's indexes from all addresses
    {
        try {
            URL url = new URL(GlobalManager.httpNGROKAddress() + "/index/indexes");
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
            System.out.println("INTERNAL SERVER ERROR: " + e.getMessage());
        }
    }

    private void path_index_new()         //post new index introduced by user
    {
        try {
            URL url = new URL(GlobalManager.httpNGROKAddress() + "/index/new");
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("POST");
            connection.setRequestProperty("Content-Type", "application/json; utf-8");
            connection.setRequestProperty("Accept", "application/json");
            connection.setDoOutput(true);
            connection.setConnectTimeout(2000);

            DataOutputStream request = new DataOutputStream(connection.getOutputStream());
            String message =  parseNewIndexToJson();
            request.writeBytes(message);
            request.flush();
            request.close();

            BufferedReader response = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            String responseLine = response.readLine();

            if(responseLine.equals("SUCCESS"))
                status = "Successful";
            else
                throw new NullPointerException();
        }
        catch (IOException e) {
            status = "Failed";
            System.out.println("COULDN'T SEND HTTP REQUEST: " + e.getMessage());
        }
        catch (NullPointerException e)     //this happens if the response is a null string, so there was a db error and couldn't extract addresses
        {
            status = "Failed";
            System.out.println("INTERNAL SERVER ERROR: " + e.getMessage());
        }
    }

    ///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    private String parseClientIdToJson()
    {
        return "{'clientId': " + GlobalManager.getClientId() + "}";
    }

    private String parseNewIndexToJson()
    {
        return "{'clientId': " + GlobalManager.getClientId() +
              ", 'newIndex': " + newIndexValue +
              ", 'fullAddress': '" + fullAddress + "'}";   //we put the value between '' because they can contain spaces and this would cause problems
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

    private void parseIndexesListJson(String input)
    {
        JsonObject jsonObject = JsonParser.parseString(input).getAsJsonObject();
        indexesList.clear();
        int numOfAddresses = jsonObject.get("numOfIndexes").getAsInt();

        for(int i = 0; i < numOfAddresses; i++)  //bills get their id indexed from 0 in json
        {
            String key = "id" + Integer.toString(i);
            JsonObject jsonBill = jsonObject.getAsJsonObject(key);
            int value = jsonBill.get("value").getAsInt();
            int consumption = jsonBill.get("consumption").getAsInt();
            String sendDate = jsonBill.get("sendDate").getAsString();
            String previousDate = jsonBill.get("previousDate").getAsString();
            String fullAddress = jsonBill.get("fullAddress").getAsString();

            DataIndex indexData = new DataIndex(value, consumption, sendDate, previousDate, fullAddress);

            indexesList.add(indexData);
        }
    }

    public ArrayList<String> getAddressesList() {return addressesList;}

    public ArrayList<DataIndex> getIndexesList() {return indexesList;}

    public String getStatus() {return status;}
}
