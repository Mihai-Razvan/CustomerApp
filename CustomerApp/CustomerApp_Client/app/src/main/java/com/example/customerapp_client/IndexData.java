package com.example.customerapp_client;

public class IndexData {

    private final int value;
    private final int consumption;
    private final String sendDate;
    private final String previousDate;
    private final String fullAddress;

    public IndexData(int value, int consumption, String sendDate, String previousDate, String fullAddress)
    {
        this.value = value;
        this.consumption = consumption;
        this.sendDate = sendDate;
        this.previousDate = previousDate;
        this.fullAddress = fullAddress;
    }

    public int getValue() {
        return value;
    }

    public int getConsumption() {return consumption;}

    public String getSendDate() { return sendDate; }

    public String getPreviousDate() {
        return previousDate;
    }

    public String getFullAddress(){return fullAddress;}

}
