package com.company;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

public class HttpAuthenticationMethods {

    public static String extractLoginEmailOrUsernameFromJson(String jsonString)  //used to extract emailOrUsername from the json send by client which contains emailOrUsername and password
    {
        JsonObject jsonObject = JsonParser.parseString(jsonString).getAsJsonObject();

        return jsonObject.get("emailOrUsername").getAsString();
    }

    public static String extractPasswordFromJson(String jsonString)
    {
        JsonObject jsonObject = JsonParser.parseString(jsonString).getAsJsonObject();

        return jsonObject.get("password").getAsString();
    }
}
