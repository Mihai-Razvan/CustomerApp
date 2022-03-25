package com.company;

public class Main {

    public static void main(String[] args) {

        HttpConnection connection = new HttpConnection();
        connection.createServer();

        DatabaseConnection databaseConnection = new DatabaseConnection();
        databaseConnection.connect();
    }
}
