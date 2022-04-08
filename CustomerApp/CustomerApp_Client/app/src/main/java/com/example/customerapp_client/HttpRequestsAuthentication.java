package com.example.customerapp_client;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class HttpRequestsAuthentication implements Runnable, HttpRequestBasics{

    private String path;
    private String emailOrUsername;
    private String password;
    private String connectionStatus;

    public HttpRequestsAuthentication(String path, String emailOrUsername, String password)     //used for /authentication/login
    {
        this.path = path;
        this.emailOrUsername = emailOrUsername;
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
    }

    private void path_authentication_login()
    {
        try {
            URL url = new URL("http://15c8-2a02-2f0c-5700-d000-6998-3444-80c8-cc3d.ngrok.io/authentication/login");            //http://10.0.2.2:8080/authentication/login
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
            System.out.println(response.readLine());
            connectionStatus = "Successful";
        }
        catch (IOException e) {
            System.out.println("COULDN'T SEND HTTP REQUEST: " + e.getMessage());
        }
    }

    ///////////////////////////////////////////////////////////////////////////////////////


    public String getConnectionStatus() {
        return connectionStatus;
    }

    private String parseLoginInfoToJason()
    {
        return "{'emailOrUsername': " + emailOrUsername + ", 'password': " + password + "}";
    }
}
