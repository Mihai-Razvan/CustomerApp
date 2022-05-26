package com.company;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

public class MethodsAccount {

    public static int extractClientIdFromJson(String jsonString)
    {
        JsonObject jsonObject = JsonParser.parseString(jsonString).getAsJsonObject();

        return jsonObject.get("clientId").getAsInt();
    }

    public static String extractCityFromJson(String jsonString)
    {
        JsonObject jsonObject = JsonParser.parseString(jsonString).getAsJsonObject();

        return jsonObject.get("city").getAsString();
    }

    public static String extractStreetFromJson(String jsonString)
    {
        JsonObject jsonObject = JsonParser.parseString(jsonString).getAsJsonObject();

        return jsonObject.get("street").getAsString();
    }

    public static String extractNumberFromJson(String jsonString)
    {
        JsonObject jsonObject = JsonParser.parseString(jsonString).getAsJsonObject();

        return jsonObject.get("number").getAsString();
    }

    public static String extractDetailsFromJson(String jsonString)
    {
        JsonObject jsonObject = JsonParser.parseString(jsonString).getAsJsonObject();

        return jsonObject.get("details").getAsString();
    }
}
