package com.company;

import java.security.NoSuchAlgorithmException;
import java.sql.*;
import java.util.ArrayList;

public class DatabaseGET {


    public static ArrayList<BillData> getAllBills(int clientId)  //return a list of billdata objects
    {
        String dbConnectionStatus;
        ArrayList<BillData> billDataList = new ArrayList<>();

        try {
            Connection connection = DriverManager.getConnection("jdbc:sqlite:D:\\Projects\\Android Apps\\CustomerApp_GitRep\\database.db");
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery("SELECT ci.first_name AS 'FirstName', b.total AS 'Total', b.status AS 'Status', a.address_name AS 'AddressName'," +
                                                            " b.due_date AS 'DueDate'\n" +
                                                             "FROM Client_Info ci, Bill b, Address a\n" +
                                                             "WHERE ci.client_id = b.client_id\n" +
                                                             "AND a.address_id = b.address_id\n" +
                                                             "AND b.client_id = " + clientId);   //1 is used for testing purpose, it will be changed to user's id after login system

            while (resultSet.next())
            {
                String firstName = resultSet.getString("FirstName");
                int total = resultSet.getInt("Total");
                String status = resultSet.getString("Status");
                String addressName = resultSet.getString("AddressName");
                String dueDate = resultSet.getString("DueDate");

                BillData billData = new BillData(firstName, total, status, addressName, dueDate);
                billDataList.add(billData);
            }

            dbConnectionStatus = "Bills extracted successfully from database";
        }
        catch (SQLException e) {
            System.out.println(e.getMessage());
            dbConnectionStatus = "Failed to extract bills from database";
        }

        System.out.println(dbConnectionStatus);
        return billDataList;
    }

    public static int logInUser(String emailOrUsername, String password)  //returns -3 if client doesn't exist, -2 if password is wrong,-1 if dbconnection failed, clientId if login details are ok
    {
        String dbConnectionStatus;
        int responseCode;

        try {
            Connection connection = DriverManager.getConnection("jdbc:sqlite:D:\\Projects\\Android Apps\\CustomerApp_GitRep\\database.db");
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery("SELECT * FROM Client\n" +
                                                             "WHERE LOWER(username) = LOWER('" + emailOrUsername + "')\n" +
                                                             "OR LOWER(email) = LOWER('" + emailOrUsername + "')");

            if(!resultSet.next())   //means that the query didn't return anything, so there was no client found with the specified emailOrUsername
                responseCode = -3;
            else
            {
                String hashedPassword = HttpAuthenticationMethods.hashPassword(password);
                if(!hashedPassword.equals(resultSet.getString("password")))  //client exists but the password is wrong
                    responseCode = -2;
                else
                    responseCode = resultSet.getInt("client_id");  //client exists and the password is right
            }


            dbConnectionStatus = "Client log in check successfully executed";
        }
        catch (SQLException | NoSuchAlgorithmException e) {
            System.out.println(e.getMessage());
            responseCode = -1;
            dbConnectionStatus = "Failed to check client log in details";
        }

        System.out.println(dbConnectionStatus);
        return responseCode;
    }
}
