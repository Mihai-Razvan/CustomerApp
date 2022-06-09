package com.example.customerapp_client;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

public class HttpRequestsAccount implements Runnable, HttpRequestBasics {

    private final String path;
    private String status;

    private String city;     //used for /account/locations/new requests
    private String street;
    private String number;
    private String details;

    private String currentPassword;   //used for /account/password/change
    private String newPassword;

    private String firstName;
    private String lastName;
    private String email;
    private String phone;
    private DataClientInfo dataClientInfo;

    private String cardNumber;      //used for /account/cards/new
    private String expirationDate;
    private String cvv;

    private String balance;    //used for /account/balance

    private final ArrayList<String> addressesList = new ArrayList<>();    //they are in fullAddress form
    private final ArrayList<String> cardsList = new ArrayList<>();  //it contains the last 4 digits for every card number

    public HttpRequestsAccount(String path, String city, String street, String number, String details) {      //used for /account/addresses/new requests, or delete
        this.path = path;
        this.city = city;
        this.street = street;
        this.number = number;
        this.details = details;
        this.status = "Failed";
    }

    public HttpRequestsAccount(String path) {      //used for /account/addresses requests or /account/contact
        this.path = path;
        this.status = "Failed";
    }

    public HttpRequestsAccount(String path, String currentPassword, String newPassword) {      //used for /account/password/change
        this.path = path;
        this.status = "Failed";
        this.currentPassword = currentPassword;
        this.newPassword = newPassword;
    }

    public HttpRequestsAccount(String path, String firstName, String lastName, String email, String phone, int x) {      //used for /account/contact/change, x is cuz we already have a constructor with same header
        this.path = path;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.phone = phone;
        this.status = "Failed";
    }

    public HttpRequestsAccount(String path, String cardNumber, String expirationDate, String cvv) {      //used for /account/cards/new
        this.path = path;
        this.cardNumber = cardNumber;
        this.expirationDate = expirationDate;
        this.cvv = cvv;
        this.status = "Failed";
    }

    @Override
    public void run() {
        choosePath();
    }

    @Override
    public void choosePath() {

        switch (path) {
            case "/account/addresses/new":
                path_addresses_new();
                break;
            case "/account/addresses":
                path_addresses();
                break;
            case "/account/addresses/delete":
                path_addresses_delete();
                break;
            case "/account/delete":
                path_delete();
                break;
            case "/account/password/change":
                path_password_change();
                break;
            case "/account/contact/change":
                path_contact_change();
                break;
            case "/account/contact":
                path_contact();
            case "/account/cards/new":
                path_cards_new();
                break;
            case "/account/cards":
                path_cards();
                break;
            case "/account/balance":
                path_balance();
                break;
        }
    }


    ///////////////PATHS///////////////

    private void path_addresses_new() {
        try {
            URL url = new URL(GlobalManager.httpNGROKAddress() + "/account/addresses/new");
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("POST");
            connection.setRequestProperty("Content-Type", "application/json; utf-8");
            connection.setRequestProperty("Accept", "application/json");
            connection.setDoOutput(true);
            connection.setConnectTimeout(2000);

            DataOutputStream request = new DataOutputStream(connection.getOutputStream());
            String message = parseAddressToJson();
            request.writeBytes(message);
            request.flush();
            request.close();

            BufferedReader response = new BufferedReader(new InputStreamReader(connection.getInputStream())); //this line has to be kept to complet the http request-response cycle
            status = "Successful";

            System.out.println("SUCCESSFULLY ADDED ADDRESS TO DATABASE");
        }
        catch (IOException e) {
            System.out.println("COULDN'T SEND HTTP REQUEST: " + e.getMessage());
        }

    }

    private void path_addresses() {
        try {
            URL url = new URL(GlobalManager.httpNGROKAddress() + "/account/addresses");
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");
            connection.setRequestProperty("Content-Type", "application/json; utf-8");
            connection.setRequestProperty("Accept", "application/json");
            connection.setDoOutput(true);
            connection.setConnectTimeout(2000);

            DataOutputStream request = new DataOutputStream(connection.getOutputStream());
            String message = parseClientIdToJson();
            request.writeBytes(message);
            request.flush();
            request.close();

            BufferedReader response = new BufferedReader(new InputStreamReader(connection.getInputStream())); //this line has to be kept to complet the http request-response cycle
            String responseLine = response.readLine();
            parseAddressesListJson(responseLine);
            status = "Successful";

            System.out.println("SUCCESSFULLY RETRIEVED ADDRESSES");
        }
        catch (IOException e) {
            System.out.println("COULDN'T RETRIEVE ADDRESSES: " + e.getMessage());
        }

    }


    private void path_addresses_delete() {
        try {
            URL url = new URL(GlobalManager.httpNGROKAddress() + "/account/addresses/delete");
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("POST");
            connection.setRequestProperty("Content-Type", "application/json; utf-8");
            connection.setRequestProperty("Accept", "application/json");
            connection.setDoOutput(true);
            connection.setConnectTimeout(2000);

            DataOutputStream request = new DataOutputStream(connection.getOutputStream());
            String message = parseAddressToJson();      //the address to be deleted
            request.writeBytes(message);
            request.flush();
            request.close();

            BufferedReader response = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            String responseLine = response.readLine();
            status = responseLine;     //it may be "Success" or "Failed"

            System.out.println("SUCCESSFULLY RETRIEVED ADDRESSES");
        }
        catch (IOException e) {
            status = "Failed";
            System.out.println("COULDN'T RETRIEVE ADDRESSES: " + e.getMessage());
        }

    }

    private void path_delete() {
        try {
            URL url = new URL(GlobalManager.httpNGROKAddress() + "/account/delete");
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("POST");
            connection.setRequestProperty("Content-Type", "application/json; utf-8");
            connection.setRequestProperty("Accept", "application/json");
            connection.setDoOutput(true);
            connection.setConnectTimeout(2000);

            DataOutputStream request = new DataOutputStream(connection.getOutputStream());
            String message = parseClientIdToJson();
            request.writeBytes(message);
            request.flush();
            request.close();

            BufferedReader response = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            String responseLine = response.readLine();
            status = responseLine;     //it may be "Success" or "Failed"
        }
        catch (IOException e) {
            status = "Failed";
            System.out.println("COULDN'T DELETE ACCOUNT: " + e.getMessage());
        }

    }

    private void path_password_change() {
        try {
            URL url = new URL(GlobalManager.httpNGROKAddress() + "/account/password/change");
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("POST");
            connection.setRequestProperty("Content-Type", "application/json; utf-8");
            connection.setRequestProperty("Accept", "application/json");
            connection.setDoOutput(true);
            connection.setConnectTimeout(2000);

            DataOutputStream request = new DataOutputStream(connection.getOutputStream());
            String message = parseNewPasswordToJson();
            request.writeBytes(message);
            request.flush();
            request.close();

            BufferedReader response = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            String responseLine = response.readLine();
            status = responseLine;     //it may be "-2 if failed, -1 if currentPassword is wrong and 1 for success"
        }
        catch (IOException e) {
            status = "-2";
            System.out.println("COULDN'T DELETE ACCOUNT: " + e.getMessage());
        }

    }

    private void path_contact_change() {
        try {
            URL url = new URL(GlobalManager.httpNGROKAddress() + "/account/contact/change");
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("POST");
            connection.setRequestProperty("Content-Type", "application/json; utf-8");
            connection.setRequestProperty("Accept", "application/json");
            connection.setDoOutput(true);
            connection.setConnectTimeout(2000);

            DataOutputStream request = new DataOutputStream(connection.getOutputStream());
            String message = parseContactInfoToJson();
            request.writeBytes(message);
            request.flush();
            request.close();

            BufferedReader response = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            String responseLine = response.readLine();
            status = responseLine;     //it may be -2 if failed, -1 if email already used by other user, 1 if success
        }
        catch (IOException e) {
            status = "-2";
            System.out.println("COULDN'T CHANGE CONTACT INFO: " + e.getMessage());
        }

    }

    private void path_contact() {
        try {
            URL url = new URL(GlobalManager.httpNGROKAddress() + "/account/contact");
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");
            connection.setRequestProperty("Content-Type", "application/json; utf-8");
            connection.setRequestProperty("Accept", "application/json");
            connection.setDoOutput(true);
            connection.setConnectTimeout(2000);

            DataOutputStream request = new DataOutputStream(connection.getOutputStream());
            String message = parseClientIdToJson();
            request.writeBytes(message);
            request.flush();
            request.close();

            BufferedReader response = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            parseClientInfoJson(response.readLine());
        }
        catch (IOException e) {
            System.out.println("COULDN'T GET CONTACT INFO: " + e.getMessage());
        }

    }

    private void path_cards_new() {
        try {
            URL url = new URL(GlobalManager.httpNGROKAddress() + "/account/cards/new");
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("POST");
            connection.setRequestProperty("Content-Type", "application/json; utf-8");
            connection.setRequestProperty("Accept", "application/json");
            connection.setDoOutput(true);
            connection.setConnectTimeout(2000);

            DataOutputStream request = new DataOutputStream(connection.getOutputStream());
            String message = parseCardToJson();
            request.writeBytes(message);
            request.flush();
            request.close();

            BufferedReader response = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            // -3 if internal error, -2 if card doesn't exist in mongoDB, -1 if user already got this card in this app, 1 if ok
            status = response.readLine();
        }
        catch (IOException e) {
            System.out.println("COULDN'T ADD CARD: " + e.getMessage());
            status = "-3";
        }
    }

    private void path_cards() {
        try {
            URL url = new URL(GlobalManager.httpNGROKAddress() + "/account/cards");
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");
            connection.setRequestProperty("Content-Type", "application/json; utf-8");
            connection.setRequestProperty("Accept", "application/json");
            connection.setDoOutput(true);
            connection.setConnectTimeout(2000);

            DataOutputStream request = new DataOutputStream(connection.getOutputStream());
            String message = parseClientIdToJson();
            request.writeBytes(message);
            request.flush();
            request.close();

            BufferedReader response = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            parseCardsJson(response.readLine());
        }
        catch (IOException e) {
            System.out.println("COULDN'T EXTRACT CARDS: " + e.getMessage());
        }
    }

    private void path_balance() {
        try {
            URL url = new URL(GlobalManager.httpNGROKAddress() + "/account/balance");
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");
            connection.setRequestProperty("Content-Type", "application/json; utf-8");
            connection.setRequestProperty("Accept", "application/json");
            connection.setDoOutput(true);
            connection.setConnectTimeout(2000);

            DataOutputStream request = new DataOutputStream(connection.getOutputStream());
            String message = parseClientIdToJson();
            request.writeBytes(message);
            request.flush();
            request.close();

            BufferedReader response = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            balance = response.readLine();
        }
        catch (IOException e) {
            System.out.println("COULDN'T EXTRACT BALANCE: " + e.getMessage());
            balance = "-1";
        }
    }

    /////////////////////////////////////////////////////////////////////////////////////////////
    //////////////////////////////////////////////////////////////////////////////////////////////
    /////////////////////////////////////////////////////////////////////////////////////////////
    //////////////////////////////////////////////////////////////////////////////////////////////

    private String parseClientIdToJson()
    {
        return "{'clientId': " + GlobalManager.getClientId() + "}";
    }

    private String parseAddressToJson()
    {
        return "{'clientId': " + GlobalManager.getClientId() +
              ", 'city': '" + city + "'" +      //we put the values between '' because they can contain spaces and this would cause problems
              ", 'street': '" + street + "'" +
              ", 'number': '" + number + "'" +
              ", 'details': '" + details + "'}";
    }

    private String parseNewPasswordToJson()
    {
        return "{'clientId': " + GlobalManager.getClientId() +
              ", 'currentPassword': " + currentPassword +
              ", 'newPassword': " + newPassword + "}";
    }

    private String parseContactInfoToJson()
    {
        return "{'clientId': '" + GlobalManager.getClientId() + "'" +
              ", 'firstName': '" + firstName + "'" +      //we use '' because first name, last name etc can have spaces
              ", 'lastName': '" + lastName + "'" +
              ", 'email': '" + email + "'" +
              ", 'phone': '" + phone + "'}";
    }

    private void parseAddressesListJson(String input)
    {
        System.out.println(input);
        if(input == null)
            return;

        JsonObject jsonObject = JsonParser.parseString(input).getAsJsonObject();
        addressesList.clear();
        int numOfAddresses = jsonObject.get("numOfAddresses").getAsInt();

        for(int i = 0; i < numOfAddresses; i++)  //addresses get their id indexed from 0 in json
        {
            String key = "id" + Integer.toString(i);
            JsonObject jsonBill = jsonObject.getAsJsonObject(key);
            String fullAddress = jsonBill.get("fullAddress").getAsString();

            addressesList.add(fullAddress);
        }
    }

    private void parseClientInfoJson(String input)
    {
        System.out.println(input);
        if(input == null)
            return;

        JsonObject jsonObject = JsonParser.parseString(input).getAsJsonObject();

        firstName = jsonObject.get("firstName").getAsString();
        lastName = jsonObject.get("lastName").getAsString();
        email = jsonObject.get("email").getAsString();
        phone = jsonObject.get("phone").getAsString();

        dataClientInfo = new DataClientInfo(firstName, lastName, email, phone);

        status = "Success";
    }

    private String parseCardToJson()
    {
        return "{'clientId': '" + GlobalManager.getClientId() + "'" +
              ", 'cardNumber': '" + cardNumber + "'" +
              ", 'expirationDate': '" + expirationDate + "'" +
              ", 'cvv': '" + cvv + "'}";
    }

    private void parseCardsJson(String input)
    {
        System.out.println(input);
        if(input == null)
            return;

        JsonObject jsonObject = JsonParser.parseString(input).getAsJsonObject();

        int numOfCards = jsonObject.get("numOfCards").getAsInt();

        for(int i = 0; i < numOfCards; i++)
        {
            String key = "id" + Integer.toString(i);
            String cardNumber = jsonObject.get(key).getAsString();

            cardsList.add(cardNumber);
        }
    }

    public ArrayList<String> getAddressesList() {
        return addressesList;
    }

    public ArrayList<String> getCardsList() {return cardsList;}

    public String getStatus()
    {
        return status;
    }

    public String getBalance() {return balance;}

    public DataClientInfo getDataClientInfo()
    {
        return dataClientInfo;
    }
}
