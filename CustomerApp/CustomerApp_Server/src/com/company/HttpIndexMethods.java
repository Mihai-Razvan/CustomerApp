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
}
