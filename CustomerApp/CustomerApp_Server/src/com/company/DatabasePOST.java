package com.company;

import java.sql.*;

public class DatabasePOST {

    public static void postLocation(String address)
    {
        String dbConnectionStatus;
        try {
            Connection connection = DriverManager.getConnection("jdbc:sqlite:D:\\Projects\\Android Apps\\CustomerApp_GitRep\\database.db");
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery("SELECT COUNT(*) AS 'NumOfRows'\n" +
                                                             "FROM Address");

            int id = resultSet.getInt("NumOfRows") + 1;     //id is numOfRows + 1

            statement.execute("INSERT INTO Address\n" +
                    "VALUES (" + id + ", 1, '" + address + "')");

            dbConnectionStatus = "Address added successfully to database";
        }
        catch (SQLException e) {
            System.out.println(e.getMessage());
            dbConnectionStatus = "Failed to add address to database";
        }

        System.out.println(dbConnectionStatus);
    }
}
