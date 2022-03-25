package com.company;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class DatabaseConnection {

    private Connection connection;

    public void connect()
    {
        try {
            connection = DriverManager.getConnection("jdbc:sqlite:D:\\Projects\\Android Apps\\CustomerApp_GitRep\\database.db");
            Statement statement = connection.createStatement();
            statement.execute("CREATE TABLE Client (id INTEGER, username TEXT, password TEXT)");
        }
        catch (SQLException e) {
            System.out.println("SQLException: " + e.getMessage());
        }
    }
}
