package com.example.customerapp_client;

public class GlobalManager {

    private static int clientId;

    public static String httpNGROKAddress()
    {
        return "http://496e-2a02-2f05-6d1d-9300-150c-9ff3-11f9-b8b5.ngrok.io";
    }

    public static void setClientId(int clientId) {
        GlobalManager.clientId = clientId;
    }

    public static int getClientId() {
        return clientId;
    }
}
