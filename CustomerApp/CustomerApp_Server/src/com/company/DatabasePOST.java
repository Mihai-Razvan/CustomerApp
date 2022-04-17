package com.company;

import java.security.NoSuchAlgorithmException;
import java.sql.*;

public class DatabasePOST {

    public static void postLocation(int clientId, String address)
    {
        String dbConnectionStatus;
        try {
            Connection connection = DriverManager.getConnection("jdbc:sqlite:D:\\Projects\\Android Apps\\CustomerApp_GitRep\\database.db");
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery("SELECT COUNT(*) AS 'NumOfRows'\n" +
                                                             "FROM Address");

            int addressId = resultSet.getInt("NumOfRows") + 1;     //id is numOfRows + 1

            statement.execute("INSERT INTO Address\n" +
                    "VALUES (" + addressId + ", " + clientId + ", '" + address + "')");

            dbConnectionStatus = "Address added successfully to database";
        }
        catch (SQLException e) {
            System.out.println(e.getMessage());
            dbConnectionStatus = "Failed to add address to database";
        }

        System.out.println(dbConnectionStatus);
    }

    public static int registerUser(String email, String username, String password) //returns -3 if username already exists, -2 if email exists, -1 internal server error, newClientId if ok
    {
        String dbConnectionStatus;
        int responseCode;

        try {
            Connection connection = DriverManager.getConnection("jdbc:sqlite:D:\\Projects\\Android Apps\\CustomerApp_GitRep\\database.db");
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
                ResultSet resultSet2 = statement.executeQuery("SELECT COUNT(*) as 'NumOfClients'\n" +
                        "FROM Client");
                int newClientId = resultSet2.getInt("NumOfClients") + 1;


                statement.execute("INSERT INTO Client\n" +
                        "VALUES (" + newClientId + ", '" + username + "', '" + hashedPassword + "', '" + email + "')");

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
}
