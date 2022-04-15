package com.example.customerapp_client;

public class GlobalManager {

    private static int clientId;

    public static String httpNGROKAddress()
    {
        return "http://0dff-2a02-2f0c-5700-d000-3d77-d190-3952-7fdb.ngrok.io";
    }

    public static void setClientId(int clientId) {
        GlobalManager.clientId = clientId;
    }

    public static int getClientId() {
        return clientId;
    }
}
