package com.company;

import java.security.NoSuchAlgorithmException;
import java.sql.*;
import java.util.ArrayList;

public class DatabaseGET {

    /////////////////////////////////////////////////////////////BILLS GET///////////////////////////////////////////////////////////////////////////

    public static ArrayList<DataBill> getAllBills(int clientId)  //return a list of billData objects
    {
        String dbConnectionStatus;
        ArrayList<DataBill> billDataList = new ArrayList<>();

        try {
            Connection connection = DriverManager.getConnection(GlobalManager.getDatabasePath());
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery("SELECT ci.first_name AS 'FirstName', b.total AS 'Total', b.release_date AS 'ReleaseDate', b.pay_date AS 'PayDate', " +
                                                             "b.status AS 'Status', a.address_name AS 'AddressName'\n" +
                                                             "FROM Client c, Bill b, Index_Table i, Address a, Client_Info ci\n" +
                                                             "WHERE c.client_id = " + clientId + "\n" +
                                                             "AND c.client_id = a.client_id\n" +
                                                             "AND a.address_id = i.address_id\n" +
                                                             "AND i.index_id = b.index_id\n" +
                                                             "AND c.client_id = ci.client_id");

            System.out.println(resultSet.getString("FirstName"));
            while (resultSet.next())
            {
                String firstName = resultSet.getString("FirstName");
                int total = resultSet.getInt("Total");
                String status = resultSet.getString("Status");
                String addressName = resultSet.getString("AddressName");
                String releaseDate = resultSet.getString("ReleaseDate");
                String payDate = resultSet.getString("PayDate");

                DataBill billData = new DataBill(firstName, total, status, addressName, releaseDate, payDate);
                billDataList.add(billData);
            }

            connection.close();

            dbConnectionStatus = "Bills extracted successfully from database";
        }
        catch (SQLException e) {
            System.out.println(e.getMessage());
            dbConnectionStatus = "Failed to extract bills from database";
        }

        System.out.println(dbConnectionStatus);
        return billDataList;
    }

    /////////////////////////////////////////////////////////////AUTHENTICATION GET///////////////////////////////////////////////////////////////////////////

    public static int logInUser(String emailOrUsername, String password)  //returns -3 if client doesn't exist, -2 if password is wrong,-1 internal server error, clientId if login details are ok
    {
        String dbConnectionStatus;
        int responseCode;

        try {
            Connection connection = DriverManager.getConnection(GlobalManager.getDatabasePath());
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery("SELECT * FROM Client\n" +
                                                             "WHERE LOWER(username) = LOWER('" + emailOrUsername + "')\n" +
                                                             "OR LOWER(email) = LOWER('" + emailOrUsername + "')");

            if(!resultSet.next())   //means that the query didn't return anything, so there was no client found with the specified emailOrUsername
                responseCode = -3;
            else
            {
                String hashedPassword = MethodsAuthentication.hashPassword(password);
                if(!hashedPassword.equals(resultSet.getString("password")))  //client exists but the password is wrong
                    responseCode = -2;
                else
                    responseCode = resultSet.getInt("client_id");  //client exists and the password is right
            }

            connection.close();

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

    /////////////////////////////////////////////////////////////INDEX GET///////////////////////////////////////////////////////////////////////////

    public static ArrayList<String> getAllAddresses(int clientId)  throws SQLException
    {
        try {
            ArrayList<String> addressesList = new ArrayList<>();

            Connection connection = DriverManager.getConnection(GlobalManager.getDatabasePath());
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery("SELECT city AS 'City',\n" +
                                                             "street AS 'Street',\n" +
                                                             "number AS 'Number',\n" +
                                                             "details AS 'Details'\n" +
                                                             "FROM Address\n" +
                                                             "WHERE client_id = " + clientId);

            while(resultSet.next())
            {
                String city = resultSet.getString("City");
                String street = resultSet.getString("Street");
                String number = resultSet.getString("Number");
                String details = resultSet.getString("Details");

                String fullAddress = city + ", " + street + ", " + number + ", " + details;
                addressesList.add(fullAddress);
            }

            connection.close();

            return addressesList;
        }
        catch (SQLException e) {
            System.out.println("COULDN'T EXTRACT ADDRESSES FROM DATABASE");
            throw e;
        }
    }

    public static ArrayList<DataIndex> getAllIndexes(int clientId) throws SQLException
    {
        try {
            ArrayList<DataIndex> indexesList= new ArrayList<>();

            Connection connection = DriverManager.getConnection(GlobalManager.getDatabasePath());
            Statement statement = connection.createStatement();

            ResultSet resultSet = statement.executeQuery("SELECT i1.value AS 'Value', i1.consumption AS 'Consumption', i1.send_date AS 'SendDate', i2.send_date AS 'PreviousDate', " +
                                                             "a.city AS 'City', a.street AS 'Street', a.number AS 'Number', a.details AS 'Details'\n" +
                                                             "FROM Index_Table i1, Index_Table i2, Address a\n" +
                                                             "WHERE i1.address_id = a.address_id\n" +
                                                             "AND a.client_id = " + clientId + "\n" +
                                                             "AND i1.previous_index_id = i2.index_id");   //get indexes where previous index IS NOT NULL
            while(resultSet.next())
            {
                int value = resultSet.getInt("Value");
                int consumption = resultSet.getInt("Consumption");
                String sendDate = resultSet.getString("SendDate");
                String previousDate = resultSet.getString("PreviousDate");
                String city = resultSet.getString("City");
                String street = resultSet.getString("Street");
                String number = resultSet.getString("Number");
                String details = resultSet.getString("Details");

                String fullAddress = city + ", " + street + ", " + number + ", " + details;      //full address as a string
                indexesList.add(new DataIndex(value, consumption, sendDate, previousDate, fullAddress));
            }

            resultSet = statement.executeQuery("SELECT i.value AS 'Value', i.consumption AS 'Consumption', i.send_date AS 'SendDate',\n" +
                                                   "a.city AS 'City', a.street AS 'Street', a.number AS 'Number', a.details AS 'Details'\n" +
                                                   "FROM Index_Table i, Address a\n" +
                                                   "WHERE i.address_id = a.address_id\n" +
                                                   "AND a.client_id = " + clientId + "\n" +
                                                   "AND i.previous_index_id IS NULL");   //get indexes where previous index IS NULL
            while(resultSet.next())
            {
                int value = resultSet.getInt("Value");
                int consumption = resultSet.getInt("Consumption");
                String sendDate = resultSet.getString("SendDate");
                String previousDate = "nullDate";
                String city = resultSet.getString("City");
                String street = resultSet.getString("Street");
                String number = resultSet.getString("Number");
                String details = resultSet.getString("Details");

                String fullAddress = city + ", " + street + ", " + number + ", " + details;
                indexesList.add(new DataIndex(value, consumption, sendDate, previousDate, fullAddress));
            }

            connection.close();

            return indexesList;
        }
        catch (SQLException e)
        {
            System.out.println("COULDN'T EXTRACT INDEXES FROM DATABASE");
            throw e;
        }
    }
}
