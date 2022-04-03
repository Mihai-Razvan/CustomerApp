package com.company;

import java.util.ArrayList;

public class HttpBillsMethods {

    public static String billListToJson(ArrayList<BillData> billDataList)
    {
        String jsonResponse = "{'numOfBills': " + Integer.toString(billDataList.size());

        for(int i = 0; i < billDataList.size(); i++)
        {
            String nameField = "'name': " + billDataList.get(i).getFirst_name();
            String totalField = "'total': " + billDataList.get(i).getTotal();
            String statusField = "'status': " + billDataList.get(i).getStatus();
            String addressField = "'address': " + billDataList.get(i).getAddress_name();

            jsonResponse += ",'id" + Integer.toString(i) + "': {" + nameField + "," + totalField + "," + statusField + "," + addressField
                    + "}";
        }

        jsonResponse += "}";

        return jsonResponse;
    }

}
