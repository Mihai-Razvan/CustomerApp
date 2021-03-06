package com.example.customerapp_client;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class HttpRequestsAuthentication implements Runnable, HttpRequestBasics{

    private final  String path;
    private  String emailOrUsername;
    private  String email;
    private  String username;
    private  String password;
    private String connectionStatus;
    private int loginResponseCode;  //is -3 if client doesn't exist, -2 if password is wrong,-1 internal server error, 0 internal server error, clientId if login details a
    private int registerResponseCode; //is -3 if username already exists, -2 if email exists, -1 if failed to connect to db, 0 if failed to connect to server, 1 if ok

    public HttpRequestsAuthentication(String path, String emailOrUsername, String password)     //used for /authentication/login
    {
        this.path = path;
        this.emailOrUsername = emailOrUsername;
        this.password = password;
    }

    public HttpRequestsAuthentication(String path, String email, String username, String password)     //used for /authentication/register
    {
        this.path = path;
        this.email = email;
        this.username = username;
        this.password = password;
    }

    @Override
    public void run() {
        choosePath();
    }

    @Override
    public void choosePath() {
        if(path.equals("/authentication/login"))
            path_authentication_login();
        else if(path.equals("/authentication/register"))
            path_authentication_register();
    }

    private void path_authentication_login()
    {
        try {
            URL url = new URL(GlobalManager.httpNGROKAddress() + "/authentication/login");            //http://10.0.2.2:8080/authentication/login
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");
            connection.setRequestProperty("Content-Type", "application/json; utf-8");
            connection.setRequestProperty("Accept", "application/json");
            connection.setDoOutput(true);
            connection.setConnectTimeout(2000);

            DataOutputStream request = new DataOutputStream(connection.getOutputStream());
            String message = parseLoginInfoToJason();
            request.writeBytes(message);
            request.flush();
            request.close();

            BufferedReader response = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            loginResponseCode = Integer.parseInt(response.readLine());
        }
        catch (IOException e) {
            loginResponseCode = 0;
            System.out.println("COULDN'T SEND HTTP REQUEST: " + e.getMessage());
        }
    }

    private void path_authentication_register()
    {
        try {
            URL url = new URL(GlobalManager.httpNGROKAddress() + "/authentication/register");            //http://10.0.2.2:8080/authentication/login
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("POST");
            connection.setRequestProperty("Content-Type", "application/json; utf-8");
            connection.setRequestProperty("Accept", "application/json");
            connection.setDoOutput(true);
            connection.setConnectTimeout(2000);

            DataOutputStream request = new DataOutputStream(connection.getOutputStream());
            String message = parseRegisterInfoToJson();
            request.writeBytes(message);
            request.flush();
            request.close();

            BufferedReader response = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            registerResponseCode = Integer.parseInt(response.readLine());
        }
        catch (IOException e) {
            registerResponseCode = 0;
            System.out.println("COULDN'T SEND HTTP REQUEST: " + e.getMessage());
        }
    }

    ///////////////////////////////////////////////////////////////////////////////////////


    private String parseLoginInfoToJason()
    {
        return "{'emailOrUsername': " + emailOrUsername +
              ", 'password': " + password + "}";
    }

    private String parseRegisterInfoToJson()
    {
        return  "{'email': " + email +
               ", 'username': " + username  +
               ", 'password': " + password + "}";
    }

    public int getLoginResponseCode() {
        return loginResponseCode;
    }

    public int getRegisterResponseCode() { return registerResponseCode;}
}
