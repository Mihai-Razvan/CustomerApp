package com.company;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

public class HttpAccountMethods {

    public static int extractClientIdFromJson(String jsonString)
    {
        JsonObject jsonObject = JsonParser.parseString(jsonString).getAsJsonObject();

        return jsonObject.get("clientId").getAsInt();
    }

    public static String extractAddressFromJson(String jsonString)
    {
        JsonObject jsonObject = JsonParser.parseString(jsonString).getAsJsonObject();

        return jsonObject.get("address").getAsString();
    }
}
