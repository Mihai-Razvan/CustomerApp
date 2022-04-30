package com.example.customerapp_client;

public class GlobalManager {

    private static int clientId;

    public static String httpNGROKAddress()
    {
        return "http://d539-2a02-2f0c-5711-4f00-ed12-92f9-3a04-6303.ngrok.io";
    }

    public static void setClientId(int clientId) {
        GlobalManager.clientId = clientId;
    }

    public static int getClientId() {
        return clientId;
    }
}
