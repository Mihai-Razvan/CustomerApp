package com.company;

import java.security.NoSuchAlgorithmException;
import java.sql.*;

public class DatabasePOST {

    public static void postAddress(int clientId, String address)
    {
        String dbConnectionStatus;
        try {
            Connection connection = DriverManager.getConnection(GlobalManager.getDatabasePath());
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery("SELECT COUNT(*) AS 'NumOfRows'\n" +
                                                             "FROM Address");

            int addressId = resultSet.getInt("NumOfRows") + 1;     //id is numOfRows + 1

            statement.execute("INSERT INTO Address\n" +
                    "VALUES (" + addressId + ", " + clientId + ", '" + address + "')");

            connection.close();

            dbConnectionStatus = "Address added successfully to database";
        }
        catch (SQLException e) {
            System.out.println(e.getMessage());
            dbConnectionStatus = "Failed to add address to database";
        }

        System.out.println(dbConnectionStatus);
    }

    ///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    public static int registerUser(String email, String username, String password) //returns -3 if username already exists, -2 if email exists, -1 internal server error, newClientId if ok
    {
        String dbConnectionStatus;
        int responseCode;

        try {
            Connection connection = DriverManager.getConnection(GlobalManager.getDatabasePath());
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery("SELECT email AS 'Email', username AS 'Username'\n" +    //check if user is already registered
                                                             "FROM Client\n" +
                                                             "WHERE email = '" + email + "'\n" +
                                                             "OR username = '" + username + "'");

            if(resultSet.next())  //user already exists
            {
                if(resultSet.getString("Username").equals(username))
                    responseCode = -3;
                else
                    responseCode = -2;
                dbConnectionStatus = "Successfully added/tried to add user to database";
            }
            else
            {
                String hashedPassword = HttpAuthenticationMethods.hashPassword(password);  //throw NoSuchAlgorithmException can come from here
                ResultSet resultSet2 = statement.executeQuery("SELECT COUNT(*)\n" +
                                                                  "FROM Client");
                int newClientId = resultSet2.getInt(1) + 1;


                statement.execute("INSERT INTO Client\n" +
                        "VALUES (" + newClientId + ", '" + username + "', '" + hashedPassword + "', '" + email + "')");

                connection.close();

                responseCode = newClientId;
                dbConnectionStatus = "Successfully added/tried to add user to database";
            }
        }
        catch (SQLException | NoSuchAlgorithmException e) {
            System.out.println(e.getMessage());
            dbConnectionStatus = "Failed to register user";
            responseCode = -1;
        }

        System.out.println(dbConnectionStatus);
        return responseCode;
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    public static void  postNewIndex(int clientId, int indexValue, String addressName) throws SQLException
    {
        try {
            Connection connection = DriverManager.getConnection(GlobalManager.getDatabasePath());
            Statement statement = connection.createStatement();

            ResultSet resultSet = statement.executeQuery("SELECT MAX(i.index_id)\n" +          //get previous index_id from this address
                                                             "FROM Index_Table i, Address a\n" +
                                                             "WHERE i.address_id = a.address_id\n" +
                                                             "AND a.client_id = " + clientId + "\n" +
                                                             "AND a.address_name = '" + addressName + "'");

            int previousIndexId = resultSet.getInt(1);

            resultSet = statement.executeQuery("SELECT COUNT(*)\n" +                   //gets total number of indexes in the table
                    "FROM Index_Table");

            int newIndexId = resultSet.getInt(1) + 1;

            resultSet = statement.executeQuery("SELECT address_id AS 'AddressId'\n" +                    //gets address_id for the given address_name and clientId
                    "FROM Address\n" +
                    "WHERE client_id = " + clientId + "\n" +
                    "AND address_name = '" + addressName + "'");

            int addressId = resultSet.getInt("AddressId");

            if(previousIndexId != 0)      //there is a previous index so the previous_index_id for this won't be 0
            {
                resultSet = statement.executeQuery("SELECT value AS 'PreviousValue'\n" +
                                                       "FROM Index_Table\n" +
                                                       "WHERE index_id = " + previousIndexId);

                int consumption = indexValue - resultSet.getInt("PreviousValue");

                statement.execute("INSERT INTO Index_Table\n" +
                        "VALUES (" + newIndexId + ", " + addressId + ", " + indexValue + ", " + consumption + ", '" + GlobalManager.getDate() + "', " + previousIndexId + ")");
            }
            else        //this is the first index on this address, so the previous_index_id will be null
            {
                statement.execute("INSERT INTO Index_Table (index_id, address_id, value, consumption, send_date)\n" +
                        "VALUES (" + newIndexId + ", " + addressId + ", " + indexValue + ", " + indexValue + ", '" + GlobalManager.getDate() + "')");
            }

            connection.close();

            System.out.println("SUCCESSFULLY ADDED INDEX TO DATABASE");
        }
        catch (SQLException e) {
            throw e;
        }
    }
}
