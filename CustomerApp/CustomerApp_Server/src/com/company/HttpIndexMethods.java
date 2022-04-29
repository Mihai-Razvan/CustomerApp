package com.company;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import java.util.ArrayList;

public class HttpIndexMethods {

    public static int extractClientIdFromJson(String jsonString)
    {
        JsonObject jsonObject = JsonParser.parseString(jsonString).getAsJsonObject();

        return jsonObject.get("clientId").getAsInt();
    }

    public static int extractNewIndexFromJson(String jsonString)
    {
        JsonObject jsonObject = JsonParser.parseString(jsonString).getAsJsonObject();

        return jsonObject.get("newIndex").getAsInt();
    }

    public static String  extractAddressNameFromJson(String jsonString)
    {
        JsonObject jsonObject = JsonParser.parseString(jsonString).getAsJsonObject();

        return jsonObject.get("addressName").getAsString();
    }

    public static String addressListToJson(ArrayList<String> addressesList)
    {
        String jsonResponse = "{'numOfAddresses': " + Integer.toString(addressesList.size());

        for(int i = 0; i < addressesList.size(); i++)
        {
            String nameField = "'name': " + addressesList.get(i);

            jsonResponse += ",'id" + Integer.toString(i) + "': {" + nameField + "}";
        }

        jsonResponse += "}";

        return jsonResponse;
    }

    public static String indexListToJson(ArrayList<IndexData> indexesList)
    {
        String jsonResponse = "{'numOfIndexes': " + Integer.toString(indexesList.size());

        for(int i = 0; i < indexesList.size(); i++)
        {
            String valueField = "'value': " + indexesList.get(i).getValue();
            String sendDateField = "'sendDate': " + indexesList.get(i).getSendDate();
            String previousDateField = "'previousDate': " + indexesList.get(i).getPreviousDate();
            String addressNameField = "'addressName': " + indexesList.get(i).getAddressName();

            jsonResponse += ",'id" + Integer.toString(i) + "': {" + valueField + ", " + sendDateField + ", " + previousDateField + ", " + addressNameField + "}";
        }

        jsonResponse += "}";

        return jsonResponse;
    }
}
