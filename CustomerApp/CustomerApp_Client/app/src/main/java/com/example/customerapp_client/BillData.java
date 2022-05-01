package com.example.customerapp_client;

public class BillData {

    private String name;
    private String total;
    private String status;
    private String address;             //address is not displayed on billcard, it is used for the addresses dropdown instead
    private String releaseDate;
    private String payDate;

    private String displayName;          //string contains "Nume: "
    private String displayTotal;
    private String displayStatus;
    private String displayDueDate;

    public BillData(String name, String total, String status, String address, String releaseDate, String payDate) {
        this.name = name;
        this.total = total;
        this.status = status;
        this.address = address;
        this.releaseDate = releaseDate;
        this.payDate = payDate;
    }

    public String getName() { return name; }

    public String getTotal() {
        return total;
    }

    public String getStatus() {
        return status;
    }

    public String getAddress() {return address;}

    public String getReleaseDate() {return releaseDate;}

    public String getPayDate() {return payDate;}
}
