package com.company;

import java.sql.*;

public class DatabasePOST {

    public static void postLocation(String location)
    {
        String dbConnectionStatus;
        try {
            Connection connection = DriverManager.getConnection("jdbc:sqlite:D:\\Projects\\Android Apps\\CustomerApp_GitRep\\database.db");
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery("SELECT COUNT(*) AS 'NumOfRows'\n" +
                                                             "FROM Location");

            int id = resultSet.getInt("NumOfRows") + 1;     //id is numOfRows + 1

            statement.execute("INSERT INTO Location\n" +
                    "VALUES (" + id + ", 1, '" + location + "')");

            dbConnectionStatus = "Location added successfully to database";
        }
        catch (SQLException e) {
            System.out.println("COULDN'T POST LOCATION IN DATABASE: " + e.getMessage());
            dbConnectionStatus = "Failed to add location to databse";
        }

        System.out.println(dbConnectionStatus);
    }
}
