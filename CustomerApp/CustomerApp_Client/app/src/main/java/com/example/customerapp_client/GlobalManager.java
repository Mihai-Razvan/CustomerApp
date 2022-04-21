package com.example.customerapp_client;

public class GlobalManager {

    private static int clientId;

    public static String httpNGROKAddress()
    {
        return "http://aa93-2a02-2f0c-5700-d000-5870-2c65-325b-b63f.ngrok.io";
    }

    public static void setClientId(int clientId) {
        GlobalManager.clientId = clientId;
    }

    public static int getClientId() {
        return clientId;
    }
}
