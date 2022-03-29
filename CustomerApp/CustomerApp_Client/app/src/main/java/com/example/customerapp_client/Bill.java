package com.example.customerapp_client;

public class Bill {

    private String displayName;          //string contains "Nume: "
    private String displayTotal;
    private String displayStatus;

    public Bill(String name, String total, String status) {

        setDisplayName(name);
        setDisplayTotal(total);
        setDisplayStatus(status);
    }

    public String getDisplayName() {
        return displayName;
    }

    public void setDisplayName(String name) {
        this.displayName = "Nume: " + name;
    }

    public String getDisplayTotal() {
        return displayTotal;
    }

    public void setDisplayTotal(String total) {
        this.displayTotal = "Total: " + total;
    }

    public String getDisplayStatus() {
        return displayStatus;
    }

    public void setDisplayStatus(String status) {
        this.displayStatus = "Status: " + status;
    }
}
