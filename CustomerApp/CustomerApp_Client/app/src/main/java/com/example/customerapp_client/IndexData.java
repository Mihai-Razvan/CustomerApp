package com.example.customerapp_client;

public class IndexData {

    private final int value;
    private final int consumption;
    private final String sendDate;
    private final String previousDate;
    private final String addressAsString;

    public IndexData(int value, int consumption, String sendDate, String previousDate, AddressData addressData)
    {
        this.value = value;
        this.consumption = consumption;
        this.sendDate = sendDate;
        this.previousDate = previousDate;
        this.addressAsString = addressToString(addressData);
    }

    public IndexData(int value, int consumption, String sendDate, String previousDate, String addressAsString)
    {
        this.value = value;
        this.consumption = consumption;
        this.sendDate = sendDate;
        this.previousDate = previousDate;
        this.addressAsString = addressAsString;
    }

    public int getValue() {
        return value;
    }

    public int getConsumption() {return consumption;}

    public String getSendDate() { return sendDate; }

    public String getPreviousDate() {
        return previousDate;
    }

    public String getAddressAsString() {
        return addressAsString;
    }

    private String addressToString(AddressData addressData)
    {
        return addressData.getCity() + ", " + addressData.getStreet() + ", " + addressData.getNumber() + ", " + addressData.getDetails() + ", ";
    }
}
