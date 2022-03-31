package com.company;

import java.sql.*;
import java.util.ArrayList;

public class DatabaseGET {


    public static ArrayList<BillData> getAllBills()  //return a list of billdata objects
    {
        ArrayList<BillData> billDataList = new ArrayList<>();

        try {
            Connection connection = DriverManager.getConnection("jdbc:sqlite:D:\\Projects\\Android Apps\\CustomerApp_GitRep\\database.db");
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery("SELECT ci.first_name AS 'FirstName', b.total AS 'Total', b.status AS 'Status'\n" +
                                                             "FROM Client_Info ci, Bill b\n" +
                                                             "WHERE ci.client_id = b.client_id;");

            while (resultSet.next())
            {
                String first_name = resultSet.getString("FirstName");
                int total = resultSet.getInt("Total");
                String status = resultSet.getString("Status");
                BillData billData = new BillData(first_name, total, status);
                billDataList.add(billData);
            }
        }
        catch (SQLException e) {
            System.out.println("COULDN'T RETRIEVE BILLS FROM DATABASE: " + e.getMessage());
        }

        return billDataList;
    }
}
