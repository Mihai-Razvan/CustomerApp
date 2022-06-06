package com.company;

import com.sun.jdi.PrimitiveValue;

import java.sql.*;
import java.time.LocalDate;
import java.util.Timer;
import java.util.TimerTask;

public class BillTableManager implements Runnable {

    private final int billsSendDay = 6;     //the day bills are created (on 1st of every month)

    @Override
    public void run() {
        sendBills();       //if the server stays open all the time this method will check every day which day it is and if it is the billsSendDay (1st of every month) it will create bills
                          //based on indexes. If you don't need to keep the server open for other processes you can start it on the first of every month and then close it
    }

    private void sendBills()
    {
        Timer timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                if(LocalDate.parse(GlobalManager.getDate()).getDayOfMonth() == billsSendDay)
                    createBills();
            }
        }, 0, 86400);
    }

    private void createBills()
    {
        try {
            Connection connection = DriverManager.getConnection(GlobalManager.getDatabasePath());
            Statement statement = connection.createStatement();

            ResultSet resultSet = statement.executeQuery("SELECT DISTINCT address_id AS 'AddressId'\n" +  //we get the address_id for the addresses which got indexes, so the addresses which got or can get bills
                                                             "FROM Index_Table");

            while(resultSet.next())
            {
                int addressId = resultSet.getInt("AddressId");
                addBillToDB(connection, addressId);
            }

            connection.close();
        }
        catch (SQLException e) {
            System.out.println("COULDN'T ADD BILL: " + e.getMessage());
        }
    }

    private void addBillToDB(Connection connection, int addressId) //this method create bills based on indexes on a specified date every month
    {
        int pricePerKWH = 5;

        try {
            Statement statement = connection.createStatement();

            //we got the problem that if the user doesn't add a new index since the last bill and it is the date for the bills to be created the last bill for the given address will have the
            //index_id the id of the last index for the given address so we will have an unique constraint error because the new bill will have the same index_id as the old bill
            //so we need to check and see if the last bill's index_id is the same as the id of the last index case in which we don't add a new bill, or if the index is different we add a new bill
            ResultSet resultSet = statement.executeQuery("SELECT IFNULL((SELECT MAX(b.bill_id)\n" +          //get the id of the last bill for the given address, return null if there is no previous bill
                                                                            "FROM Bill b, Index_Table i\n" +
                                                                            "WHERE b.index_id = i.index_id\n" +
                                                                            "AND i.address_id = " + addressId + "), NULL)");

            String lastBillId = resultSet.getString(1);

            if(lastBillId != null)     //if the bil_id is not null it means there are previous bills, so we need to check if index_id of the previous bill is the
                //same as the previous index_id for the given address, case in which we can't add a new bill cuz there was already added a bill for the last index. If its null we add the first bill
            {
                resultSet = statement.executeQuery("SELECT index_id AS 'IndexId'\n" +      //we get index_id of the last bill for the given address
                                                       "FROM Bill\n" +
                                                       "WHERE bill_id = " + lastBillId);

                int lastBillIndexId = resultSet.getInt(1);

                resultSet = statement.executeQuery("SELECT MAX(index_id)\n" +            //we get index_id of the last index for the given address
                                                       "FROM Index_Table\n" +
                                                       "WHERE address_id = " + addressId);

                int lastIndex = resultSet.getInt(1);

                if(lastIndex == lastBillIndexId)                 //it means there is a bill already for the last index of the given address so we can t add a new bill
                    return;
            }
            String billIdQuery = "(SELECT COUNT(*)\n" +
                                  "FROM Bill) + 1";

            String indexIdQuery = "SELECT MAX(index_id)\n" +
                                  "FROM Index_Table\n" +
                                  "WHERE address_id = " + addressId;

            String totalQuery = pricePerKWH + " * ((SELECT value\n" +
                                                   "FROM Index_Table\n" +
                                                   "WHERE index_id = (SELECT MAX(index_id)\n" +
                                                                     "FROM Index_Table\n" +
                                                                     "WHERE address_id = " + addressId + ")) - (SELECT IIF((SELECT MAX(b.bill_id)\n" +
                                                                                                               "FROM Bill b, Index_Table i\n" +
                                                                                                               "WHERE b.index_id = i.index_id\n" +
                                                                                                               "AND i.address_id = " + addressId + "), (SELECT i.value\n" +
                                                                                                                                                       "FROM Bill b, Index_Table i\n" +
                                                                                                                                                       "WHERE b.index_id = i.index_id\n" +
                                                                                                                                                       "AND b.bill_id = (SELECT MAX(b.bill_id)\n" +
                                                                                                                                                                        "FROM Bill b, Index_Table i  \n" +
                                                                                                                                                                        "WHERE b.index_id = i.index_id\n" +
                                                                                                                                                                        "AND i.address_id = " + addressId + ")), 0)))";

            String releaseDate = GlobalManager.getDate();
            String payDate = LocalDate.parse(releaseDate).plusMonths(1).toString();
            String status = "Unpaid";

            statement.execute("INSERT INTO Bill\n" +
                                  "VALUES (" + billIdQuery + ", (" + indexIdQuery + "), " + totalQuery + ", '" + releaseDate + "', '" + payDate + "', '" + status + "')");

            statement.close();
        }
        catch (SQLException e) {
            System.out.println("COULDN'T ADD BILL: " + e.getMessage());
        }
    }
}
