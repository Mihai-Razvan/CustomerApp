package com.company;

import java.sql.*;
import java.util.ArrayList;

public class DatabaseGET {


    public static ArrayList<BillData> getAllBills()  //return a list of billdata objects
    {
        String dbConnectionStatus;
        ArrayList<BillData> billDataList = new ArrayList<>();

        try {
            Connection connection = DriverManager.getConnection("jdbc:sqlite:D:\\Projects\\Android Apps\\CustomerApp_GitRep\\database.db");
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery("SELECT ci.first_name AS 'FirstName', b.total AS 'Total', b.status AS 'Status', a.address_name AS 'AddressName'\n" +
                                                             "FROM Client_Info ci, Bill b, Address a\n" +
                                                             "WHERE ci.client_id = b.client_id\n" +
                                                             "AND a.address_id = b.address_id\n" +
                                                             "AND b.client_id = 1");   //1 is used for testing purpose, it will be changed to user's id after login system

            while (resultSet.next())
            {
                String first_name = resultSet.getString("FirstName");
                int total = resultSet.getInt("Total");
                String status = resultSet.getString("Status");
                String address_name = resultSet.getString("AddressName");

                BillData billData = new BillData(first_name, total, status, address_name);
                billDataList.add(billData);
            }

            dbConnectionStatus = "Bills extracted successfully from database";
        }
        catch (SQLException e) {
            dbConnectionStatus = "Failed to extract bills from database";
        }

        System.out.println(dbConnectionStatus);
        return billDataList;
    }
}
