package com.example.customerapp_client;

public class GlobalManager {

    private static int clientId;

    public static String httpNGROKAddress()
    {
        return "http://4ce4-2a02-2f0c-5700-d000-fd7e-6b01-86ae-ed44.ngrok.io";
    }

    public static void setClientId(int clientId) {
        GlobalManager.clientId = clientId;
    }

    public static int getClientId() {
        return clientId;
    }
}
