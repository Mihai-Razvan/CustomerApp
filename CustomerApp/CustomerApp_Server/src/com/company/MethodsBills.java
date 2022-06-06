package com.company;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import java.util.ArrayList;

public class MethodsBills {

    public static String billListToJson(ArrayList<DataBill> billDataList)
    {
        String jsonResponse = "{'numOfBills': " + Integer.toString(billDataList.size());

        for(int i = 0; i < billDataList.size(); i++)
        {
            String totalField = "'total': '" + billDataList.get(i).getTotal() + "'";
            String statusField = "'status': '" + billDataList.get(i).getStatus() + "'";
            String fullAddressField = "'fullAddress': '" + billDataList.get(i).getFullAddress() + "'";
            String releaseDateField = "'releaseDate': '" + billDataList.get(i).getReleaseDate() + "'";
            String payDateField = "'payDate': '" + billDataList.get(i).getPayDate() + "'";

            jsonResponse += ",'id" + Integer.toString(i) + "': {" + totalField + "," + statusField + "," + fullAddressField + "," + releaseDateField + "," + payDateField + "}";
        }

        jsonResponse += "}";

        return jsonResponse;
    }

    public static int getClientIdFromJson(String jsonString)
    {
        JsonObject jsonObject = JsonParser.parseString(jsonString).getAsJsonObject();

        return jsonObject.get("clientId").getAsInt();
    }

}
