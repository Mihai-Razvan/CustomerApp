package com.company;

import java.time.LocalDate;

public class GlobalManager {

    public static String getDatabasePath()
    {
        return "jdbc:sqlite:D:\\Projects\\Android Apps\\CustomerApp_GitRep\\database.db";
    }

    public static String getDate()
    {
        LocalDate now = LocalDate.now();        //YYYY-MM-DD format

        return now.toString();
    }
}
