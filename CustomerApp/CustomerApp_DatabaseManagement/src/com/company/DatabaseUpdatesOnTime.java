package com.company;

import java.sql.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Timer;
import java.util.TimerTask;

public class DatabaseUpdatesOnTime implements Runnable{

    @Override
    public void run() {

        Timer timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                updateBillStatus();
            }
        }, 0, 5000);

    }


    public static void updateBillStatus()     //if it passes the due_date and the status is not PAID it changes to UNPAID (this is just an example)
    {
        String dbConnectionStatus;

        try {
            Connection connection = DriverManager.getConnection("jdbc:sqlite:D:\\Projects\\Android Apps\\CustomerApp_GitRep\\database.db");
            Statement statement = connection.createStatement();
            statement.execute("UPDATE Bill\n" +
                                  "SET status = 'RESTANTIER'\n" +
                                  "WHERE due_date < DATE()\n" +
                                  "AND status IS NOT 'PAID'");

            dbConnectionStatus = "Bill status updated successful";
        }
        catch (SQLException e) {
            System.out.println(e.getMessage());
            dbConnectionStatus = "Failed to update bill status";
        }

        System.out.println(dbConnectionStatus);
    }
}
