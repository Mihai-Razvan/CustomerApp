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

public class HttpRequestsBills implements Runnable {

    private String path;
    private String connectionStatus;
    private Bill bill;
    private ArrayList<Bill> billList;

    public HttpRequestsBills(String path) {
        this.path = path;
        this.connectionStatus = "Failed";
        billList = new ArrayList<>();
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
//        else if(path.equals("/bills/add"))
//        {
//            path_bills_add();
//        }
    }


    ///////////////PATHS///////////////

    private void path_bills()
    {
        try {
            URL url = new URL("http://4ae2-2a02-2f0c-5700-d000-9051-1dbe-9e65-4054.ngrok.io/bills");            //http://10.0.2.2:8080/bills
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
            parseBillsListJson(responseLine);

            connectionStatus = "Successful";
        }
        catch (MalformedURLException e) {
            System.out.println("COULDN'T SEND HTTP REQUEST: " + e.getMessage());
        }
        catch (IOException e) {
            System.out.println("COULDN'T SEND HTTP REQUEST: " + e.getMessage());
        }

    }


    //////////////////////////////////////////////////////////////////////////////////////////////


    private void parseBillsListJson(String input)      //the response received from server is a json string that has to be parsed
    {
        JsonObject jsonObject = JsonParser.parseString(input).getAsJsonObject();
        billList.clear();
        int numOfBills = jsonObject.get("numOfBills").getAsInt();

        for(int i = 0; i < numOfBills; i++)  //bills get their id indexed from 0 in json
        {
            String key = "id" + Integer.toString(i);
            String name = jsonObject.getAsJsonObject(key).get("name").getAsString();
            String total = jsonObject.getAsJsonObject(key).get("total").getAsString();
            String status = jsonObject.getAsJsonObject(key).get("status").getAsString();
            bill = new Bill(name, total, status);
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



//    private void path_bills_add()
//    {
//        try {
//            URL url = new URL("http://10.0.2.2:8080/bills/add");            //http://10.0.2.2:8080/test
//            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
//            connection.setRequestMethod("GET");
//            connection.setRequestProperty("Content-Type", "application/json; utf-8");
//            connection.setRequestProperty("Accept", "application/json");
//            connection.setDoOutput(true);
//            connection.setConnectTimeout(2000);
//
//            DataOutputStream request = new DataOutputStream(connection.getOutputStream());
//            String message = "REQUEST FROM ANDROID";
//            request.writeBytes(message);
//            request.flush();
//            request.close();
//
//            BufferedReader response = new BufferedReader(new InputStreamReader(connection.getInputStream()));
//            String responseLine = response.readLine();
//            parseBillJson(responseLine);
//
//            connectionStatus = "Successful";
//        }
//        catch (MalformedURLException e) {
//            System.out.println("MalformedURLException: " + e.getMessage());
//        }
//        catch (IOException e) {
//            System.out.println("IOException: " + e.getMessage());
//        }
//    }
//
//
//
//    private void parseBillJson(String input)
//    {
//        JsonObject jsonObject = JsonParser.parseString(input).getAsJsonObject();
//        String name = jsonObject.get("name").getAsString();
//        String total = jsonObject.get("total").getAsString();
//        String status = jsonObject.get("status").getAsString();
//        bill = new Bill(name, total, status);
//    }
}
