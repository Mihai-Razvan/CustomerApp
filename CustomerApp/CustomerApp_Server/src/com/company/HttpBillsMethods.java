package com.company;

import java.util.ArrayList;

public class HttpBillsMethods {

    public static String billListToJson(ArrayList<BillData> billDataList)
    {
        String jsonResponse = "{'numOfBills': " + Integer.toString(billDataList.size());

        for(int i = 0; i < billDataList.size(); i++)
        {
            String nameKey = "'name': " + billDataList.get(i).getFirst_name();
            String totalKey = "'total': " + billDataList.get(i).getTotal();
            String statusKey = "'status': " + billDataList.get(i).getStatus();

            jsonResponse += ",'id" + Integer.toString(i) + "': {" + nameKey + "," + totalKey + "," + statusKey + "}";
        }

        jsonResponse += "}";

        return jsonResponse;
    }
}
