package com.example.customerapp_client;

public class DataClientInfo {

    private final String firstName;
    private final String lastName;
    private final String email;
    private final String phone;

    public DataClientInfo(String firstName, String lastName, String email, String phone) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.phone = phone;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getEmail() {
        return email;
    }

    public String getPhone() {
        return phone;
    }
}
