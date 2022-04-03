package com.example.customerapp_client;

public class Bill {

    private String name;
    private String total;
    private String status;
    private String address;             //address is not displayed on billcard, it is used for the addresses dropdown instead

    private String displayName;          //string contains "Nume: "
    private String displayTotal;
    private String displayStatus;

    public Bill(String name, String total, String status, String address) {
        this.name = name;
        this.total = total;
        this.status = status;
        this.address = address;
        setDisplayName(name);
        setDisplayTotal(total);
        setDisplayStatus(status);
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setTotal(String total) {
        this.total = total;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public void setAddress(String address) {this.address = address;}

    public void setDisplayName(String name) {
        this.displayName = "Nume: " + name;
    }

    public void setDisplayTotal(String total) {
        this.displayTotal = "Total: " + total;
    }

    public void setDisplayStatus(String status) {
        this.displayStatus = "Status: " + status;
    }



    public String getName() { return name; }

    public String getTotal() {
        return total;
    }

    public String getStatus() {
        return status;
    }

    public String getAddress() {return address;}

    public String getDisplayName() {
        return displayName;
    }

    public String getDisplayTotal() { return displayTotal; }

    public String getDisplayStatus() { return displayStatus; }
}
