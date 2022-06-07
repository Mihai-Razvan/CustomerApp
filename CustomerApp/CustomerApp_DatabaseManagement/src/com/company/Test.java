package com.company;



import java.sql.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Test {

    public static void GUID()
    {
        String dbConnectionStatus;

        try {
            Connection connection = DriverManager.getConnection(GlobalManager.getDatabasePath());
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

    public static void date()
    {
        LocalDate now = LocalDate.now();
        System.out.println(now);

        LocalDate date = LocalDate.parse("2022-04-29");

        System.out.println(date.getMonth().toString());
    }

}
