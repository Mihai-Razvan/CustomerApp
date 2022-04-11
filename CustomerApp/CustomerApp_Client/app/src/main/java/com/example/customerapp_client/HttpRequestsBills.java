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
import java.util.ArrayList;
import java.util.List;

public class HttpRequestsBills implements Runnable, HttpRequestBasics {

    private final String path;
    private final int clientId;
    private String connectionStatus;
    private Bill bill;
    private ArrayList<Bill> billList;

    public HttpRequestsBills(String path, int clientId) {
        this.path = path;
        this.clientId = clientId;
        this.connectionStatus = "Failed";
        billList = new ArrayList<>();
    }

    @Override
    public void run()
    {
        choosePath();
    }

    @Override
    public void choosePath()
    {
        if(path.equals("/bills"))
        {
            path_bills();
        }
    }


    ///////////////PATHS///////////////

    private void path_bills()
    {
        try {
            URL url = new URL("http://c2de-2a02-2f0c-5700-d000-88fe-b666-ab70-3957.ngrok.io/bills");            //http://10.0.2.2:8080/bills
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");
            connection.setRequestProperty("Content-Type", "application/json; utf-8");
            connection.setRequestProperty("Accept", "application/json");
            connection.setDoOutput(true);
            connection.setConnectTimeout(2000);

            DataOutputStream request = new DataOutputStream(connection.getOutputStream());
            String message = parseBillsRequestToJson();
            request.writeBytes(message);
            request.flush();
            request.close();

            BufferedReader response = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            String responseLine = response.readLine();
            parseBillsListJson(responseLine);

            connectionStatus = "Successful";
        }
        catch (IOException e) {
            System.out.println("COULDN'T SEND HTTP REQUEST: " + e.getMessage());
        }
    }


    //////////////////////////////////////////////////////////////////////////////////////////////

    private String parseBillsRequestToJson()
    {
        return "{'clientId': " + clientId + "}";
    }


    private void parseBillsListJson(String input)      //the response received from server is a json string that has to be parsed
    {
        JsonObject jsonObject = JsonParser.parseString(input).getAsJsonObject();
        billList.clear();
        int numOfBills = jsonObject.get("numOfBills").getAsInt();

        for(int i = 0; i < numOfBills; i++)  //bills get their id indexed from 0 in json
        {
            String key = "id" + Integer.toString(i);
            JsonObject jsonBill = jsonObject.getAsJsonObject(key);
            String name = jsonBill.get("name").getAsString();
            String total = jsonBill.get("total").getAsString();
            String status = jsonBill.get("status").getAsString();
            String address = jsonBill.get("address").getAsString();
            String dueDate = jsonBill.get("dueDate").getAsString();

            bill = new Bill(name, total, status, address, dueDate);
            billList.add(bill);
        }
    }

    public String getConnectionStatus() {
        return connectionStatus;
    }

    public Bill getBill()
    {
        return bill;
    }

    public ArrayList<Bill> getBillList()
    {
        return billList;
    }

}
