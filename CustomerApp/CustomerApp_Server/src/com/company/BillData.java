package com.company;

public class BillData {  //class contains info about a bill, so info from table Bill but also info about the client who got this bill, suchs as his first_name etc

    private int id;
    private int client_id;
    private int total;
    private String start_date;
    private String end_date;
    private String status;

    private String first_name;
    private String address_name;

    public BillData(String first_name, int total, String status, String address_name)
    {
        this.first_name = first_name;
        this.total = total;
        this.status = status;
        this.address_name = address_name;
    }


    public String getFirst_name() {
        return first_name;
    }

    public int getTotal() {
        return total;
    }

    public String getStatus() {
        return status;
    }
    public String  getAddress_name() {
        return address_name;
    }

}
