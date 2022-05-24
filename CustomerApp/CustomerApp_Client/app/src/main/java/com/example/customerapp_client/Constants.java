package com.example.customerapp_client;

public class Constants {

    static String emailOrUsername = "KEY_EMAIL_OR_USERNAME";
    static String password = "KEY_PASSWORD";
    static String phoneNumber = "074-853-8489";


    public static void setEmailOrUsername(String emailOrUsername) {
        Constants.emailOrUsername = emailOrUsername;
    }

    public static void setPassword(String password) {
        Constants.password = password;
    }

    public static String getEmailOrUsername() {
        return emailOrUsername;
    }

    public static String getPassword() {
        return password;
    }

    public static String getPhoneNumber(){return phoneNumber;}
}
