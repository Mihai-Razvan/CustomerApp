package com.example.customerapp_client;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.provider.SyncStateContract;
import android.util.Log;

import java.util.Map;

public class GlobalManager {

    private static int clientId;

    public static String httpNGROKAddress()
    {
        return "http://84e5-2a02-2f0c-5a09-aa00-5592-9b01-e854-9f65.ngrok.io";
    }

    public static void setClientId(int clientId) {
        GlobalManager.clientId = clientId;
    }

    public static int getClientId() {
        return clientId;
    }

    public static void saveEmailOrUsername(Context context, String usernameOrUsername)        //saves usernameOrEmail to preferences in order to persist after application close
    {
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(Constants.getEmailOrUsername(), usernameOrUsername);
        editor.apply();

//        editor.remove("KEY+PASSWORD");
//        editor.apply();
//        Map<String,?> keys = sharedPreferences.getAll();
//        for(Map.Entry<String,?> entry : keys.entrySet()){
//            Log.d("map values",entry.getKey() + ": " +
//                    entry.getValue().toString());
//        }
    }

    public static void savePassword(Context context, String password)     //saves password to preferences in order to persist after application close
    {
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(Constants.getPassword(), password);
        editor.apply();
    }

    public static String getSavedEmailOrUsername(Context context)  //returns emailOrUsername if user is logged in, otherwise null
    {
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
        return sharedPreferences.getString(Constants.getEmailOrUsername(), null);
    }

    public static String getSavedPassword(Context context)  //returns password if user is logged in, otherwise null
    {
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
        return sharedPreferences.getString(Constants.getPassword(), null);
    }
}
