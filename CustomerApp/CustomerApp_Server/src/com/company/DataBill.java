package com.company;

public class DataBill {  //class contains info about a bill, so info from table Bill but also info about the client who got this bill, suchs as his first_name etc

    //from the Bill table
    private final String total;
    private final String releaseDate;
    private final String payDate;
    private final String status;
    private final String fullAddress;

    public DataBill(String total, String status, String fullAddress, String releaseDate, String payDate)
    {
        this.total = total;
        this.status = status;
        this.fullAddress = fullAddress;
        this.releaseDate = releaseDate;
        this.payDate = payDate;
    }



    public String getTotal() {
        return total;
    }

    public String getStatus() {
        return status;
    }

    public String  getFullAddress(){return fullAddress;}
    public String getReleaseDate() {return releaseDate;}

    public String getPayDate() {return payDate;}

}
