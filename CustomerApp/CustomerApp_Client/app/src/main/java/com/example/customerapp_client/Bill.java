package com.example.customerapp_client;

public class Bill {

    private String name;
    private String total;
    private String status;

    private String displayName;          //string contains "Nume: "
    private String displayTotal;
    private String displayStatus;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getTotal() {
        return total;
    }

    public void setTotal(String total) {
        this.total = total;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Bill(String name, String total, String status) {
        this.name = name;
        this.total = total;
        this.status = status;
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
