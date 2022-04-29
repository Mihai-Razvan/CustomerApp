package com.company;

public class IndexData {

    private int value;
    private String sendDate;
    private String previousDate;
    private String addressName;

    public IndexData(int value, String sendDate, String previousDate, String addressName)
    {
        this.value = value;
        this.sendDate = sendDate;
        this.previousDate = previousDate;
        this.addressName = addressName;
    }

    public int getValue() {
        return value;
    }

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
