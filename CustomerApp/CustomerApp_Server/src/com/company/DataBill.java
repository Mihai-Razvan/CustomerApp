package com.company;

public class DataBill {  //class contains info about a bill, so info from table Bill but also info about the client who got this bill, suchs as his first_name etc

    //from the Bill table
    private final int total;
    private final String releaseDate;
    private final String payDate;
    private final String status;

    //bill related data from other tabels such as Client_Info
    private final String firstName;
    private final String addressName;

    public DataBill(String firstName, int total, String status, String addressName, String releaseDate, String payDate)
    {
        this.firstName = firstName;
        this.total = total;
        this.status = status;
        this.addressName = addressName;
        this.releaseDate = releaseDate;
        this.payDate = payDate;
    }


    public String getFirstName() {
        return firstName;
    }

    public int getTotal() {
        return total;
    }

    public String getStatus() {
        return status;
    }

    public String  getAddressName() {
        return addressName;
    }

    public String getReleaseDate() {return releaseDate;}

    public String getPayDate() {return payDate;}

}
