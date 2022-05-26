package com.company;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class MethodsAuthentication {

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

    public static String extractEmailFromJson(String jsonString)
    {
        JsonObject jsonObject = JsonParser.parseString(jsonString).getAsJsonObject();

        return jsonObject.get("email").getAsString();
    }

    public static String extractUsernameFromJson(String jsonString)
    {
        JsonObject jsonObject = JsonParser.parseString(jsonString).getAsJsonObject();

        return jsonObject.get("username").getAsString();
    }

    public static String hashPassword(String passwordToHash) throws NoSuchAlgorithmException {
        String hashedPassword = "";

        try {
            MessageDigest messageDigest = MessageDigest.getInstance("MD5");
            messageDigest.update(passwordToHash.getBytes());
            byte[] bytes = messageDigest.digest();
            StringBuilder stringBuilder = new StringBuilder();

            for(int i = 0; i < bytes.length; i++)
                stringBuilder.append(Integer.toString((bytes[i] & 0xff) + 0x100, 16).substring(1));

            hashedPassword = stringBuilder.toString();
        }
        catch (NoSuchAlgorithmException e) {
            System.out.println("Failed to hash password");
            throw e;
        }

        System.out.println("Successfully hashed password");
        return hashedPassword;
    }
}
