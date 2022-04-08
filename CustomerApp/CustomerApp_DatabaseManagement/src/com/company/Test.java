package com.company;

import java.sql.*;

public class Test {

    public static void GUID()
    {
        String dbConnectionStatus;

        try {
            Connection connection = DriverManager.getConnection("jdbc:sqlite:D:\\Projects\\Android Apps\\CustomerApp_GitRep\\database.db");
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery("SELECT NEWID()");

            System.out.println(resultSet);

            dbConnectionStatus = "Bill status updated successful";
        }
        catch (SQLException e) {
            System.out.println(e.getMessage());
            dbConnectionStatus = "Failed to update bill status";
        }

        System.out.println(dbConnectionStatus);
    }
}
