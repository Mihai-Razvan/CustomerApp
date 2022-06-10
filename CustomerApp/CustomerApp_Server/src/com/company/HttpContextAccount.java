package com.company;

import com.BankingLibrary.Bank;
import com.BankingLibrary.Card;
import com.mongodb.MongoCommandException;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import com.sun.net.httpserver.HttpServer;

import javax.xml.crypto.Data;
import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.security.NoSuchAlgorithmException;
import java.sql.SQLException;
import java.util.ArrayList;

public class HttpContextAccount implements HttpContextBasics {

    private final HttpServer server;

    public HttpContextAccount(HttpServer server) {
        this.server = server;
        createContexts();
    }

    @Override
    public void createContexts() {
        context_account_addresses_new();
        context_account_addresses();
        context_account_addresses_delete();
        context_account_delete();
        context_account_password_change();
        context_account_contact_change();
        context_account_contact();
        context_account_cards_new();
        context_account_cards();
        context_account_balance();
        context_account_balance_add();
    }

    ///////////////////////////////////////////////CONTEXTS/////////////////////////////////////////////


    private void context_account_addresses_new() {
        server.createContext("/account/addresses/new", new HttpHandler() {
            @Override
            public void handle(HttpExchange exchange) throws IOException {

                System.out.println("REQUEST RECEIVED ON /account/addresses/new");
                BufferedReader request = new BufferedReader(new InputStreamReader(exchange.getRequestBody()));
                String requestLine = request.readLine();

                int clientId = MethodsAccount.extractClientIdFromJson(requestLine);
                String city = MethodsAccount.extractCityFromJson(requestLine);
                String street = MethodsAccount.extractStreetFromJson(requestLine);
                String number = MethodsAccount.extractNumberFromJson(requestLine);
                String details = MethodsAccount.extractDetailsFromJson(requestLine);

                DatabasePOST.postAddress(clientId, city, street, number, details);
            }
        });
    }

    private void context_account_addresses() {
        server.createContext("/account/addresses", new HttpHandler() {
            @Override
            public void handle(HttpExchange exchange) throws IOException {

                System.out.println("REQUEST RECEIVED ON /account/addresses");
                BufferedReader request = new BufferedReader(new InputStreamReader(exchange.getRequestBody()));
                String requestLine = request.readLine();
                String responseMessage = "";     //if inside try will throw exception then the response won't be a json but an empty String

                try {
                    int clientId = MethodsAccount.extractClientIdFromJson(requestLine);
                    ArrayList<String> addressesList = DatabaseGET.getAllAddresses(clientId);   //could throw SQLException
                    responseMessage = MethodsIndex.addressListToJson(addressesList);
                } catch (SQLException e) {
                    System.out.println(e.getMessage());
                }

                exchange.sendResponseHeaders(200, responseMessage.length());
                DataOutputStream response = new DataOutputStream(exchange.getResponseBody());
                response.writeBytes(responseMessage);
                response.flush();
                response.close();
            }
        });
    }

    private void context_account_addresses_delete() {
        server.createContext("/account/addresses/delete", new HttpHandler() {
            @Override
            public void handle(HttpExchange exchange) throws IOException {

                System.out.println("REQUEST RECEIVED ON /account/addresses/delete");
                BufferedReader request = new BufferedReader(new InputStreamReader(exchange.getRequestBody()));
                String requestLine = request.readLine();
                String responseMessage;

                try {
                    int clientId = MethodsAccount.extractClientIdFromJson(requestLine);
                    String city = MethodsAccount.extractCityFromJson(requestLine);
                    String street = MethodsAccount.extractStreetFromJson(requestLine);
                    String number = MethodsAccount.extractNumberFromJson(requestLine);
                    String details = MethodsAccount.extractDetailsFromJson(requestLine);

                    responseMessage = DatabasePOST.deleteAddress(clientId, city, street, number, details);       //could throw SqlException
                    System.out.println("SUCCESSFULLY DELETED ADDRESS FROM DATABASE");
                } catch (SQLException e) {
                    e.printStackTrace();
                    System.out.println("COULDN'T DELETE ADDRESS FROM DATABASE");
                    responseMessage = "Failed";
                }

                exchange.sendResponseHeaders(200, responseMessage.length());
                DataOutputStream response = new DataOutputStream(exchange.getResponseBody());
                response.writeBytes(responseMessage);
                response.flush();
                response.close();
            }
        });
    }

    private void context_account_delete() {
        server.createContext("/account/delete", new HttpHandler() {
            @Override
            public void handle(HttpExchange exchange) throws IOException {

                System.out.println("REQUEST RECEIVED ON /account/delete");
                BufferedReader request = new BufferedReader(new InputStreamReader(exchange.getRequestBody()));
                String requestLine = request.readLine();
                String responseMessage;

                try {
                    int clientId = MethodsAccount.extractClientIdFromJson(requestLine);

                    responseMessage = DatabasePOST.deleteAccount(clientId);       //could throw SqlException
                    System.out.println("SUCCESSFULLY DELETED ACCOUNT FROM DATABASE");
                } catch (SQLException e) {
                    e.printStackTrace();
                    System.out.println("COULDN'T DELETE ACCOUNT FROM DATABASE");
                    responseMessage = "Failed";
                }

                exchange.sendResponseHeaders(200, responseMessage.length());
                DataOutputStream response = new DataOutputStream(exchange.getResponseBody());
                response.writeBytes(responseMessage);
                response.flush();
                response.close();
            }
        });
    }

    private void context_account_password_change() {  //response is -2 for failed, -1 for wrong current password and 1 for success
        server.createContext("/account/password/change", new HttpHandler() {
            @Override
            public void handle(HttpExchange exchange) throws IOException {

                System.out.println("REQUEST RECEIVED ON /account/password/change");
                BufferedReader request = new BufferedReader(new InputStreamReader(exchange.getRequestBody()));
                String requestLine = request.readLine();
                String responseMessage = "";

                try {
                    int clientId = MethodsAccount.extractClientIdFromJson(requestLine);
                    String currentPassword = MethodsAccount.extractCurrentPasswordFromJson(requestLine);
                    String newPassword = MethodsAccount.extractNewPasswordFromJson(requestLine);

                    String response = DatabasePOST.changePassword(clientId, currentPassword, newPassword);   //throws exception or returns "Success"/"Wrong password"
                    if(response.equals("Success"))
                        responseMessage = "1";
                    else    //if introduced current password is wrong
                        responseMessage = "-1";

                } catch (SQLException | NoSuchAlgorithmException e) {
                    System.out.println(e.getMessage());
                    responseMessage = "-2";
                }

                exchange.sendResponseHeaders(200, responseMessage.length());
                DataOutputStream response = new DataOutputStream(exchange.getResponseBody());
                response.writeBytes(responseMessage);
                response.flush();
                response.close();
            }
        });
    }

    private void context_account_contact_change() {   //response is -2 for failed, -1 if email used by another user and 1 for success
        server.createContext("/account/contact/change", new HttpHandler() {
            @Override
            public void handle(HttpExchange exchange) throws IOException {

                System.out.println("REQUEST RECEIVED ON /account/contact/change");
                BufferedReader request = new BufferedReader(new InputStreamReader(exchange.getRequestBody()));
                String requestLine = request.readLine();
                String responseMessage = "";

                try {
                    int clientId = MethodsAccount.extractClientIdFromJson(requestLine);
                    String firstName = MethodsAccount.extractFirstNameFromJson(requestLine);
                    String lastName = MethodsAccount.extractLastNameFromJson(requestLine);
                    String email = MethodsAccount.extractEmailFromJson(requestLine);
                    String phone = MethodsAccount.extractPhoneFromJson(requestLine);

                    String response = DatabasePOST.changeContactInfo(clientId, firstName, lastName, email, phone);
                    if(response.equals("Success"))
                        responseMessage = "1";
                    else
                        responseMessage = "-1";

                } catch (SQLException e) {
                    System.out.println(e.getMessage());
                    responseMessage = "1";
                }

                exchange.sendResponseHeaders(200, responseMessage.length());
                DataOutputStream response = new DataOutputStream(exchange.getResponseBody());
                response.writeBytes(responseMessage);
                response.flush();
                response.close();
            }
        });
    }

    private void context_account_contact() {
        server.createContext("/account/contact", new HttpHandler() {
            @Override
            public void handle(HttpExchange exchange) throws IOException {

                System.out.println("REQUEST RECEIVED ON /account/contact");
                BufferedReader request = new BufferedReader(new InputStreamReader(exchange.getRequestBody()));
                String requestLine = request.readLine();
                String responseMessage = "";    //if inside try will throw exception then the response won't be a json but an empty String

                try {
                    int clientId = MethodsAccount.extractClientIdFromJson(requestLine);

                    DataClientInfo dataClientInfo = DatabaseGET.getClientInfo(clientId);    //SQLException could come from here
                    responseMessage = MethodsAccount.clientInfoToJson(dataClientInfo);

                } catch (SQLException e) {
                    System.out.println(e.getMessage());
                }

                exchange.sendResponseHeaders(200, responseMessage.length());
                DataOutputStream response = new DataOutputStream(exchange.getResponseBody());
                response.writeBytes(responseMessage);
                response.flush();
                response.close();
            }
        });
    }

    private void context_account_cards_new() {   //returns -3 if internal error, -2 if card doesn't exist in mongoDB, -1 if user already got this card in this app, 1 if ok
        server.createContext("/account/cards/new", new HttpHandler() {
            @Override
            public void handle(HttpExchange exchange) throws IOException {

                System.out.println("REQUEST RECEIVED ON /account/cards/new");
                BufferedReader request = new BufferedReader(new InputStreamReader(exchange.getRequestBody()));
                String requestLine = request.readLine();
                String responseMessage;    //if inside try will throw exception then the response won't be a json but an empty String

                try {
                    int clientId = MethodsAccount.extractClientIdFromJson(requestLine);
                    String cardNumber = MethodsAccount.extractCardNumberFromJson(requestLine);
                    String expirationDate = MethodsAccount.extractExpirationDateFromJson(requestLine);
                    String cvv = MethodsAccount.extractCVVFromJson(requestLine);

                    Bank.getCard(cardNumber, cvv, expirationDate);       //if no card is found it will throw NullPointerException / MongoCommandException
                    responseMessage = DatabasePOST.postCard(clientId, cardNumber, expirationDate, cvv);   //SQLEXCEPTION could come from here
                }
                catch (SQLException | MongoCommandException e){
                    e.printStackTrace();
                    responseMessage = "-3";
                }
                catch (NullPointerException e) {
                    e.printStackTrace();
                    responseMessage = "-2";     //card doesn't exist in mongoDB
                }

                exchange.sendResponseHeaders(200, responseMessage.length());
                DataOutputStream response = new DataOutputStream(exchange.getResponseBody());
                response.writeBytes(responseMessage);
                response.flush();
                response.close();
            }
        });
    }

    private void context_account_cards() {
        server.createContext("/account/cards", new HttpHandler() {
            @Override
            public void handle(HttpExchange exchange) throws IOException {

                System.out.println("REQUEST RECEIVED ON /account/cards");
                BufferedReader request = new BufferedReader(new InputStreamReader(exchange.getRequestBody()));
                String requestLine = request.readLine();
                String responseMessage = "";    //if inside try will throw exception then the response won't be a json but an empty String

                try {
                    int clientId = MethodsAccount.extractClientIdFromJson(requestLine);

                    responseMessage = MethodsAccount.cardListToJson(DatabaseGET.getCards(clientId));  //SQLEXCEPTION could come from here
                }
                catch (SQLException e){
                    e.printStackTrace();
                }

                exchange.sendResponseHeaders(200, responseMessage.length());
                DataOutputStream response = new DataOutputStream(exchange.getResponseBody());
                response.writeBytes(responseMessage);
                response.flush();
                response.close();
            }
        });
    }

    private void context_account_balance() {
        server.createContext("/account/balance", new HttpHandler() {      //returns -1 if error or a number >= 0 if ok
            @Override
            public void handle(HttpExchange exchange) throws IOException {

                System.out.println("REQUEST RECEIVED ON /account/balance");
                BufferedReader request = new BufferedReader(new InputStreamReader(exchange.getRequestBody()));
                String requestLine = request.readLine();
                String responseMessage = "";    //if inside try will throw exception then the response won't be a json but an empty String

                try {
                    int clientId = MethodsAccount.extractClientIdFromJson(requestLine);

                    responseMessage = DatabaseGET.getBalance(clientId);  //SQLEXCEPTION could come from here
                }
                catch (SQLException e){
                    e.printStackTrace();
                    responseMessage = "-1";
                }

                exchange.sendResponseHeaders(200, responseMessage.length());
                DataOutputStream response = new DataOutputStream(exchange.getResponseBody());
                response.writeBytes(responseMessage);
                response.flush();
                response.close();
            }
        });
    }

    private void context_account_balance_add() {   //returns -3 if internal error, -2 if card doesn't exist in mongoDB, -1 if not enought funds, 1 if ok
        server.createContext("/account/balance/add", new HttpHandler() {
            @Override
            public void handle(HttpExchange exchange) throws IOException {

                System.out.println("REQUEST RECEIVED ON /account/balance/add");
                BufferedReader request = new BufferedReader(new InputStreamReader(exchange.getRequestBody()));
                String requestLine = request.readLine();
                String responseMessage;    //if inside try will throw exception then the response won't be a json but an empty String

                try {
                    int clientId = MethodsAccount.extractClientIdFromJson(requestLine);
                    String cardNumber = MethodsAccount.extractCardNumberFromJson(requestLine);
                    String expirationDate = MethodsAccount.extractExpirationDateFromJson(requestLine);
                    String cvv = MethodsAccount.extractCVVFromJson(requestLine);
                    float amount = Float.parseFloat(MethodsAccount.extractAmountFromJson(requestLine));

                    float sold = Bank.getSold(cardNumber, cvv, expirationDate);       //if no card is found it will throw NullPointerException / MongoCommandException

                    if(sold < amount)
                        responseMessage = "-1";
                    else
                    {
                        DatabasePOST.addFunds(clientId, amount);   //SQLEXCEPTION could come from here
                        Bank.reduceSold(cardNumber, cvv, expirationDate, amount);
                        responseMessage = "1";
                    }
                }
                catch (SQLException | MongoCommandException e){
                    e.printStackTrace();
                    responseMessage = "-3";
                }
                catch (NullPointerException e) {
                    e.printStackTrace();
                    responseMessage = "-2";     //card doesn't exist in mongoDB
                }

                exchange.sendResponseHeaders(200, responseMessage.length());
                DataOutputStream response = new DataOutputStream(exchange.getResponseBody());
                response.writeBytes(responseMessage);
                response.flush();
                response.close();
            }
        });
    }
}
