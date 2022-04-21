package com.company;

import java.util.ArrayList;

public class HttpIndexMethods {

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

    public static String indexListToJson(ArrayList<String> indexesList)
    {
        String jsonResponse = "{'numOfIndexes': " + Integer.toString(indexesList.size());

        for(int i = 0; i < indexesList.size(); i++)
        {
            String nameField = "'value': " + indexesList.get(i);

            jsonResponse += ",'id" + Integer.toString(i) + "': {" + nameField + "}";
        }

        jsonResponse += "}";

        return jsonResponse;
    }
}
