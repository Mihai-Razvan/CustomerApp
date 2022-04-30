package com.company;

public class IndexData {

    private int value;
    private int consumption;
    private String sendDate;
    private String previousDate;
    private String addressName;

    public IndexData(int value, int consumption, String sendDate, String previousDate, String addressName)
    {
        this.value = value;
        this.consumption = consumption;
        this.sendDate = sendDate;
        this.previousDate = previousDate;
        this.addressName = addressName;
    }

    public int getValue() {return value;}

    public int getConsumption() {return consumption;}

    public String getSendDate() {
        return sendDate;
    }

    public String getPreviousDate() {
        return previousDate;
    }

    public String getAddressName() {
        return addressName;
    }
}
