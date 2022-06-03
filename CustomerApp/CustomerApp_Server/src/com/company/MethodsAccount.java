package com.company;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import java.util.ArrayList;

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

    public static String extractCurrentPasswordFromJson(String jsonString)
    {
        JsonObject jsonObject = JsonParser.parseString(jsonString).getAsJsonObject();

        return jsonObject.get("currentPassword").getAsString();
    }

    public static String extractNewPasswordFromJson(String jsonString)
    {
        JsonObject jsonObject = JsonParser.parseString(jsonString).getAsJsonObject();

        return jsonObject.get("newPassword").getAsString();
    }

    public static String extractFirstNameFromJson(String jsonString)
    {
        JsonObject jsonObject = JsonParser.parseString(jsonString).getAsJsonObject();

        return jsonObject.get("firstName").getAsString();
    }

    public static String extractLastNameFromJson(String jsonString)
    {
        JsonObject jsonObject = JsonParser.parseString(jsonString).getAsJsonObject();

        return jsonObject.get("lastName").getAsString();
    }

    public static String extractEmailFromJson(String jsonString)
    {
        JsonObject jsonObject = JsonParser.parseString(jsonString).getAsJsonObject();

        return jsonObject.get("email").getAsString();
    }

    public static String extractPhoneFromJson(String jsonString)
    {
        JsonObject jsonObject = JsonParser.parseString(jsonString).getAsJsonObject();

        return jsonObject.get("phone").getAsString();
    }

    public static String clientInfoToJson(DataClientInfo dataClientInfo)
    {
        String jsonResponse = "";

        String firstNameField = "'firstName': '" + dataClientInfo.getFirstName() + "'";
        String lastNameField = "'lastName': '" + dataClientInfo.getLastName() + "'";
        String email = "'email': '" + dataClientInfo.getEmail() + "'";
        String phoneField = "'phone': '" + dataClientInfo.getPhone() + "'";

        jsonResponse += "{" + firstNameField + ", " + lastNameField + ", " + email + ", " + phoneField + "}";

        return jsonResponse;
    }
}
