package com.company;

import java.security.NoSuchAlgorithmException;
import java.sql.*;
import java.util.ArrayList;

public class DatabaseGET {

    /////////////////////////////////////////////////////////////BILLS GET///////////////////////////////////////////////////////////////////////////

    public static ArrayList<BillData> getAllBills(int clientId)  //return a list of billdata objects
    {
        String dbConnectionStatus;
        ArrayList<BillData> billDataList = new ArrayList<>();

        try {
            Connection connection = DriverManager.getConnection(GlobalManager.getDatabasePath());
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

            connection.close();
            statement.close();
            resultSet.close();

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
                String hashedPassword = HttpAuthenticationMethods.hashPassword(password);
                if(!hashedPassword.equals(resultSet.getString("password")))  //client exists but the password is wrong
                    responseCode = -2;
                else
                    responseCode = resultSet.getInt("client_id");  //client exists and the password is right
            }

            connection.close();
            statement.close();
            resultSet.close();

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
            ResultSet resultSet = statement.executeQuery("SELECT address_name AS 'AddressName'\n" +
                                                             "FROM Address\n" +
                                                             "WHERE client_id = " + clientId);

            while(resultSet.next())
            {
                String addressName = resultSet.getString("AddressName");
                addressesList.add(addressName);
            }

            connection.close();
            statement.close();
            resultSet.close();

            return addressesList;
        }
        catch (SQLException e) {
            System.out.println("COULDN'T EXTRACT ADDRESSES FROM DATABASE");
            throw e;
        }
    }

    public static ArrayList<IndexData> getAllIndexes(int clientId) throws SQLException
    {
        try {
            ArrayList<IndexData> indexesList= new ArrayList<>();

            Connection connection = DriverManager.getConnection(GlobalManager.getDatabasePath());
            Statement statement = connection.createStatement();

            ResultSet resultSet = statement.executeQuery("SELECT i1.value AS 'Value', i1.send_date AS 'SendDate', i2.send_date AS 'PreviousDate', a.address_name AS 'AddressName'\n" +
                                                             "FROM Index_Table i1, Index_Table i2, Address a\n" +
                                                             "WHERE i1.address_id = a.address_id\n" +
                                                             "AND a.client_id = " + clientId + "\n" +
                                                             "AND i1.previous_index_id = i2.index_id");   //get indexes where previous index IS NOT NULL
            while(resultSet.next())
            {
                int value = resultSet.getInt("Value");
                String sendDate = resultSet.getString("SendDate");
                String previousDate = resultSet.getString("PreviousDate");
                String addressName = resultSet.getString("AddressName");
                indexesList.add(new IndexData(value, sendDate, previousDate, addressName));
            }

            resultSet = statement.executeQuery("SELECT i.value AS 'Value', i.send_date AS 'SendDate', a.address_name AS 'AddressName'\n" +
                                                   "FROM Index_Table i, Address a\n" +
                                                   "WHERE i.address_id = a.address_id\n" +
                                                   "AND a.client_id = " + clientId + "\n" +
                                                   "AND i.previous_index_id IS NULL");   //get indexes where previous index IS NULL
            while(resultSet.next())
            {
                int value = resultSet.getInt("Value");
                String sendDate = resultSet.getString("SendDate");
                String previousDate = "nullDate";
                String addressName = resultSet.getString("AddressName");
                indexesList.add(new IndexData(value, sendDate, previousDate, addressName));
            }

            connection.close();
            statement.close();
            resultSet.close();

            return indexesList;
        }
        catch (SQLException e)
        {
            System.out.println("COULDN'T EXTRACT INDEXES FROM DATABASE");
            throw e;
        }
    }
}
