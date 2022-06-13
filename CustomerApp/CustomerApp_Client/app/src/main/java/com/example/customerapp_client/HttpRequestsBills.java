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

public class HttpRequestsBills implements Runnable, HttpRequestBasics {

    private final String path;
    private String status;
    private DataBill bill;
    private ArrayList<DataBill> billList;

    private String indexId;

    public HttpRequestsBills(String path) {
        this.path = path;
        this.status = "Failed";
        billList = new ArrayList<>();
    }

    public HttpRequestsBills(String path, String indexId) {     //used for /bills/pay
        this.path = path;
        this.indexId = indexId;
        this.status = "Failed";
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
        else if(path.equals("/bills/pay"))
        {
            path_bills_pay();
        }
    }


    ///////////////PATHS///////////////

    private void path_bills()
    {
        try {
            URL url = new URL(GlobalManager.httpNGROKAddress() + "/bills");            //http://10.0.2.2:8080/bills
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

            status = "Successful";
            System.out.println(responseLine);
        }
        catch (IOException e) {
            System.out.println("COULDN'T SEND HTTP REQUEST: " + e.getMessage());
        }
    }

    private void path_bills_pay()
    {
        try {
            URL url = new URL(GlobalManager.httpNGROKAddress() + "/bills/pay");            //http://10.0.2.2:8080/bills
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("POST");
            connection.setRequestProperty("Content-Type", "application/json; utf-8");
            connection.setRequestProperty("Accept", "application/json");
            connection.setDoOutput(true);
            connection.setConnectTimeout(2000);

            DataOutputStream request = new DataOutputStream(connection.getOutputStream());
            String message = parseBillPayRequestToJson();
            request.writeBytes(message);
            request.flush();
            request.close();

            BufferedReader response = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            String responseLine = response.readLine();

            status = responseLine;       //could be 1 or -1
        }
        catch (IOException e) {
            System.out.println("COULDN'T PAY BILL: " + e.getMessage());
            status = "-1";
        }
    }


    //////////////////////////////////////////////////////////////////////////////////////////////

    private String parseBillsRequestToJson()
    {
        return "{'clientId': " + GlobalManager.getClientId() + "}";
    }


    private void parseBillsListJson(String input)      //the response received from server is a json string that has to be parsed
    {
        System.out.println(input);
        JsonObject jsonObject = JsonParser.parseString(input).getAsJsonObject();
        billList.clear();
        int numOfBills = jsonObject.get("numOfBills").getAsInt();

        for(int i = 0; i < numOfBills; i++)  //bills get their id indexed from 0 in json
        {
            String key = "id" + Integer.toString(i);
            JsonObject jsonBill = jsonObject.getAsJsonObject(key);
            String total = jsonBill.get("total").getAsString();
            String status = jsonBill.get("status").getAsString();
            String fullAddress = jsonBill.get("fullAddress").getAsString();
            String releaseDate = jsonBill.get("releaseDate").getAsString();
            String payDate = jsonBill.get("payDate").getAsString();
            String indexId = jsonBill.get("indexId").getAsString();

            bill = new DataBill(total, status, fullAddress, releaseDate, payDate, indexId);
            billList.add(bill);
        }
    }


    private String parseBillPayRequestToJson()
    {
        return "{'indexId': " + indexId + "}";
    }

    public String getStatus() {
        return status;
    }

    public DataBill getBill()
    {
        return bill;
    }

    public ArrayList<DataBill> getBillList()
    {
        return billList;
    }

}
