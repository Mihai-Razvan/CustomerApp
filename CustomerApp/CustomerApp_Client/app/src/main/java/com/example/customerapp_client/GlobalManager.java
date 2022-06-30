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
        return "http://c6fb-2a02-2f0c-5810-4200-9817-2e5c-2f90-40b5.ngrok.io";
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

    public static void setPayMethod(Context context, String payMethod)
    {
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(Constants.getPayMethod(), payMethod);
        editor.apply();
    }

    public static void setCard(Context context, String cardNumber, String expirationDate, String cvv)      //the selected card
    {
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(Constants.getCardNumber(), cardNumber);
        editor.putString(Constants.getCardExpirationDate(), expirationDate);
        editor.putString(Constants.getCvv(), cvv);
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

    public static String getPayMethod(Context context)
    {
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
        return sharedPreferences.getString(Constants.getPayMethod(), null);
    }

    public static String getCardNumber(Context context)
    {
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
        return sharedPreferences.getString(Constants.getCardNumber(), null);
    }

    public static String getExpirationDate(Context context)
    {
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
        return sharedPreferences.getString(Constants.getCardExpirationDate(), null);
    }

    public static String getCvv(Context context)
    {
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
        return sharedPreferences.getString(Constants.getCvv(), null);
    }
}
