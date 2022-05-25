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

    public static String addressListToJson(ArrayList<AddressData> addressesList)
    {
        String jsonResponse = "{'numOfAddresses': " + Integer.toString(addressesList.size());

        for(int i = 0; i < addressesList.size(); i++)
        {
            String cityField = "'city': " + addressesList.get(i).getCity();
            String streetField = "'street': " + addressesList.get(i).getStreet();
            String numberField = "'number': " + addressesList.get(i).getNumber();
            String detailsField = "'city': " + addressesList.get(i).getDetails();

            jsonResponse += ",'id" + Integer.toString(i) + "': {" + cityField + ", " + streetField + ", " + numberField + ", " + detailsField + ", " + "}";
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
            String addressNameField = "'addressName': " + indexesList.get(i).getAddressName();

            jsonResponse += ",'id" + Integer.toString(i) + "': {" + valueField + ", " + consumptionField + ", " + sendDateField + ", " + previousDateField + ", " + addressNameField + "}";
        }

        jsonResponse += "}";

        return jsonResponse;
    }
}
