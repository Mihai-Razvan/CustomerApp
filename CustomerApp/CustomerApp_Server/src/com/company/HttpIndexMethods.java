package com.company;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

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

    public static String  extractFullAddressFromJson(String jsonString)
    {
        JsonObject jsonObject = JsonParser.parseString(jsonString).getAsJsonObject();

        return jsonObject.get("fullAddress").getAsString();
    }

    public static String addressListToJson(ArrayList<String> addressesList)
    {
        String jsonResponse = "{'numOfAddresses': " + Integer.toString(addressesList.size());

        for(int i = 0; i < addressesList.size(); i++)
        {
            String fullAddress = "'fullAddress': '" + addressesList.get(i) + "'";

            jsonResponse += ",'id" + Integer.toString(i) + "': {" + fullAddress + "}";
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
            String consumptionField = "'consumption': " + indexesList.get(i).getConsumption();
            String sendDateField = "'sendDate': " + indexesList.get(i).getSendDate();
            String previousDateField = "'previousDate': " + indexesList.get(i).getPreviousDate();
            String fullAddressField = "'fullAddress': " + "'" + indexesList.get(i).getAddressName() + "'";

            jsonResponse += ",'id" + Integer.toString(i) + "': {" + valueField + ", " + consumptionField + ", " + sendDateField + ", " + previousDateField + ", " + fullAddressField + "}";
        }

        jsonResponse += "}";

        return jsonResponse;
    }

    public static ArrayList<String> splitFullAddress(String fullAddress)
    {
        String[] fullAddressSplit = fullAddress.split(", ");
        String details = fullAddressSplit[3];

        for(int i = 4; i < fullAddressSplit.length; i++)
            details = details + ", " + fullAddressSplit[i];

        fullAddressSplit[3] = details;

        return new ArrayList<String>(Arrays.asList(fullAddressSplit));
    }
}
