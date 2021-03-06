package com.company;

import java.security.NoSuchAlgorithmException;
import java.sql.*;
import java.util.ArrayList;

public class DatabasePOST {

    public static void postAddress(int clientId, String city, String street, String number, String details)
    {
        String dbConnectionStatus;
        try {
            Connection connection = DriverManager.getConnection(GlobalManager.getDatabasePath());
            Statement statement = connection.createStatement();

            ResultSet resultSet = statement.executeQuery("SELECT * \n" +
                                                             "FROM Address\n" +
                                                             "WHERE city = '" + city + "'\n" +
                                                             "AND street = '" + street + "'\n" +
                                                             "AND number = '" + number + "'\n" +
                                                             "AND details = '" + details + "'\n" +
                                                             "AND status = 'Active'");         //if the address was deleted (or its user deleted its account) then you can add a new address with the same info

            if(resultSet.next())      //address already exist
            {
                System.out.println("Address already exist");
                return;
            }

            resultSet = statement.executeQuery("SELECT COUNT(*) AS 'NumOfRows'\n" +
                                                   "FROM Address");

            int addressId = resultSet.getInt("NumOfRows") + 1;     //id is numOfRows + 1

            statement.execute("INSERT INTO Address\n" +
                    "VALUES (" + addressId + ", " + clientId + ", '" + city + "', '" + street + "', '" + number + "', '" + details + "', 'Active')");

            connection.close();

            dbConnectionStatus = "Address added successfully to database";
        }
        catch (SQLException e) {
            System.out.println(e.getMessage());
            dbConnectionStatus = "Failed to add address to database";
        }

        System.out.println(dbConnectionStatus);
    }

    public static String deleteAddress(int clientId, String city, String street, String number, String details) throws SQLException
    {
        try {
            Connection connection = DriverManager.getConnection(GlobalManager.getDatabasePath());
            Statement statement = connection.createStatement();

            statement.execute("UPDATE Address\n" +                      //we don't check for the address_id because a client can't have more addresses with the same data
                                  "SET status = 'Deleted'\n" +
                                  "WHERE client_id = " + clientId + "\n" +
                                  "AND city = '" + city + "'\n" +
                                  "AND street = '" + street + "'\n" +
                                  "AND number = '" + number + "'\n" +
                                  "AND details = '" + details + "'\n");

            connection.close();
        }
        catch (SQLException e) {
            throw e;       //if it throws it won't return success but an exception that will be caught
        }

        return "Success";     //if it doesn't throw it returns "Success"
    }

    public static String deleteAccount(int clientId) throws SQLException
    {
        try {
            Connection connection = DriverManager.getConnection(GlobalManager.getDatabasePath());
            Statement statement = connection.createStatement();

            statement.execute("UPDATE Client\n" +
                                  "SET status = 'Deleted'\n" +
                                  "WHERE client_id = " + clientId);

            statement.execute("UPDATE Address\n" +
                                  "SET status = 'Deleted'\n" +
                                  "WHERE client_id = " + clientId);

            connection.close();
        }
        catch (SQLException e) {
            throw e;       //if it throws it won't return success but an exception that will be caught
        }

        return "Success";     //if it doesn't throw it returns "Success"
    }

    public static String changePassword(int clientId, String currentPassword, String newPassword) throws SQLException, NoSuchAlgorithmException
    {
        try {
            Connection connection = DriverManager.getConnection(GlobalManager.getDatabasePath());
            Statement statement = connection.createStatement();

            String hashedPassword = MethodsAuthentication.hashPassword(currentPassword);  //throw NoSuchAlgorithmException can come from here

            ResultSet resultSet = statement.executeQuery("SELECT *\n" +
                                                             "FROM Client\n" +
                                                             "WHERE password = '" + hashedPassword + "'");

            if(resultSet.next())   //the currentPassword send by user is correct
            {
                hashedPassword = MethodsAuthentication.hashPassword(newPassword);

                statement.execute("UPDATE Client\n" +
                                      "SET password = '" + hashedPassword + "'\n" +
                                      "WHERE client_id = " + clientId);

                statement.execute("UPDATE Address\n" +
                        "SET status = 'Deleted'\n" +
                        "WHERE client_id = " + clientId);
            }
            else
            {
                connection.close();
                return "Wrong password";
            }

            connection.close();
        }
        catch (SQLException | NoSuchAlgorithmException e) {
            throw e;       //if it throws it won't return success but an exception that will be caught
        }

        return "Success";    //if it doesn't throw it returns "Success"
    }

    public static String changeContactInfo(int clientId, String firstName, String lastName, String email, String phone) throws SQLException
    {
        try {
            Connection connection = DriverManager.getConnection(GlobalManager.getDatabasePath());
            Statement statement = connection.createStatement();

            ResultSet resultSet = statement.executeQuery("SELECT *\n" +    //we do this so you won't be able to set a new email that is already used by another user
                                                             "FROM Client\n" +
                                                             "WHERE email = '" + email + "'\n" +
                                                             "AND client_id != " + clientId);

            if(!resultSet.next())
            {
                statement.execute("UPDATE Client\n" +
                        "SET email = '" + email + "'\n" +
                        "WHERE client_id = " + clientId);

                statement.execute("UPDATE Client_Info\n" +
                        "SET first_name = '" + firstName + "', last_name = '" + lastName + "', phone_number = '" + phone + "'\n" +
                        "WHERE client_info_id = (SELECT client_info_id\n" +
                        "FROM Client\n" +
                        "WHERE client_id = " + clientId + ")");
            }
            else
            {
                connection.close();
                return "Email already used";
            }

            connection.close();
        }
        catch (SQLException e) {
            throw e;       //if it throws it won't return success but an exception that will be caught
        }

        return "Success";    //if it doesn't throw it returns "Success"
    }

    ///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    public static String postCard(int clientId, String cardNumber, String expirationDate, String cvv)  throws SQLException //expirationDate has the MM / YY format
    {
        try {
            Connection connection = DriverManager.getConnection(GlobalManager.getDatabasePath());
            Statement statement = connection.createStatement();

            ResultSet resultSet = statement.executeQuery("SELECT *\n" +
                                                             "FROM Card\n" +
                                                             "WHERE card_number = '" + cardNumber + "'\n" +
                                                             "AND expiration_date = '" + expirationDate + "'\n" +
                                                             "AND cvv = '" + cvv + "'\n" +
                                                             "AND status = 'Active'\n" +
                                                             "AND client_id = " + clientId);

            if(resultSet.next())   //card already is in the DB for this user. ! A card could be added multiple times but it needs to be registered on another user so another client_id
                return "-1";     //card already exists for this user

            resultSet = statement.executeQuery("SELECT COUNT(*) AS 'NumOfRows'\n" +
                                                             "FROM Card");

            int cardId = resultSet.getInt("NumOfRows") + 1;     //id is numOfRows + 1

            statement.execute("INSERT INTO Card\n" +
                                  "VALUES (" + cardId + ", " + clientId + ", '" + cardNumber + "', '" + expirationDate + "', '" + cvv + "', 'Active')");

            connection.close();

            return "1";
        }
        catch (SQLException e) {
            throw e;
        }
    }

    public static void addFunds(int clientId, float amount) throws SQLException
    {
        try {
            Connection connection = DriverManager.getConnection(GlobalManager.getDatabasePath());
            Statement statement = connection.createStatement();

            statement.execute("UPDATE Wallet\n" +
                                  "SET balance = balance + " + amount);
        }
        catch (SQLException e) {
            throw e;
        }
    }

    public static void reduceFunds(int clientId, float amount) throws SQLException
    {
        try {
            Connection connection = DriverManager.getConnection(GlobalManager.getDatabasePath());
            Statement statement = connection.createStatement();

            statement.execute("UPDATE Wallet\n" +
                                  "SET balance = balance - " + amount);
        }
        catch (SQLException e) {
            throw e;
        }
    }

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
                                                             "OR username = '" + username + "'");  //if an user already exists but his account was deleted you CAN'T create a new account with
                                                                                                  //the same username or email

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
                String hashedPassword = MethodsAuthentication.hashPassword(password);  //throw NoSuchAlgorithmException can come from here
                ResultSet resultSet2 = statement.executeQuery("SELECT COUNT(*)\n" +
                                                                  "FROM Client");
                int newClientId = resultSet2.getInt(1) + 1;

                statement.execute("INSERT INTO Client_Info\n" +
                        "VALUES (" + newClientId + ", null, null, null)");        //adding the client_info for this client

                statement.execute("INSERT INTO Client\n" +
                        "VALUES (" + newClientId + ", " + newClientId + ", '" + username + "', '" + hashedPassword + "', '" + email + "', 'Active')");  //adding client

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

    public static void  postNewIndex(int clientId, int indexValue, String fullAddress) throws SQLException
    {
        try {
            Connection connection = DriverManager.getConnection(GlobalManager.getDatabasePath());
            Statement statement = connection.createStatement();

            ArrayList<String> fullAddressSplit = MethodsIndex.splitFullAddress(fullAddress);   //split in city, street, number, details

            ResultSet resultSet = statement.executeQuery("SELECT MAX(i.index_id)\n" +          //get previous index_id from this address
                                                             "FROM Index_Table i, Address a\n" +
                                                             "WHERE i.address_id = a.address_id\n" +
                                                             "AND a.client_id = " + clientId + "\n" +
                                                             "AND a.city = '" + fullAddressSplit.get(0) + "'\n" +
                                                             "AND a.street = '" + fullAddressSplit.get(1) + "'\n" +
                                                             "AND a.number = '" + fullAddressSplit.get(2) + "'\n" +
                                                             "AND a.details = '" + fullAddressSplit.get(3) + "'");

            int previousIndexId = resultSet.getInt(1);

            resultSet = statement.executeQuery("SELECT COUNT(*)\n" +                   //gets total number of indexes in the table
                    "FROM Index_Table");

            int newIndexId = resultSet.getInt(1) + 1;

            resultSet = statement.executeQuery("SELECT address_id AS 'AddressId'\n" +                    //gets address_id for the given address and clientId
                    "FROM Address\n" +
                    "WHERE client_id = " + clientId + "\n" +
                    "AND city = '" + fullAddressSplit.get(0) + "'\n" +
                    "AND street = '" + fullAddressSplit.get(1) + "'\n" +
                    "AND number = '" + fullAddressSplit.get(2) + "'\n" +
                    "AND details = '" + fullAddressSplit.get(3) + "'");

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

    ///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    public static void payBill(int indexId) throws SQLException
    {
        try {
            Connection connection = DriverManager.getConnection(GlobalManager.getDatabasePath());
            Statement statement = connection.createStatement();

            statement.execute("UPDATE Bill\n" +
                                  "SET status = 'Paid'\n" +
                                  "WHERE index_id = " + indexId);
        }
        catch (SQLException e) {
            throw e;
        }
    }
}
