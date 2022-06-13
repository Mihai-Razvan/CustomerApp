package com.example.customerapp_client;

public class Constants {

    static String emailOrUsername = "KEY_EMAIL_OR_USERNAME";
    static String password = "KEY_PASSWORD";
    static String phoneNumber = "074-853-8489";

    static String payMethod = "KEY_PAY_METHOD";
    static String cardNumber = "KEY_CARD_NUMBER";
    static String cardExpirationDate = "KEY_CARD_EXPIRATION_DATE";
    static String cvv = "KEY_CVV";


    public static String getEmailOrUsername() {
        return emailOrUsername;
    }

    public static String getPassword() {
        return password;
    }

    public static String getPhoneNumber(){return phoneNumber;}

    public static String getPayMethod() {
        return payMethod;
    }

    public static String getCardNumber() {
        return cardNumber;
    }

    public static String getCardExpirationDate() {
        return cardExpirationDate;
    }

    public static String getCvv() {
        return cvv;
    }
}
