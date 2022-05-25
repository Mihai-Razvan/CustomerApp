package com.company;

public class AddressData {

    String city;
    String street;
    String number;
    String details;

    public AddressData(String city, String street, String number, String details)
    {
        this.city = city;
        this.street = street;
        this.number = number;
        this.details = details;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public String getDetails() {
        return details;
    }

    public void setDetails(String details) {
        this.details = details;
    }
}
