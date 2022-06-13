package com.example.customerapp_client;

public class DataBill {

    private final String total;
    private String status;
    private final String fullAddress;             //address is not displayed on billcard, it is used for the addresses dropdown instead
    private final String releaseDate;
    private final String payDate;
    private final String indexId;


    public DataBill(String total, String status, String fullAddress, String releaseDate, String payDate, String indexId) {
        this.total = total;
        this.status = status;
        this.fullAddress = fullAddress;
        this.releaseDate = releaseDate;
        this.payDate = payDate;
        this.indexId = indexId;
    }

    public String getTotal() {
        return total;
    }

    public String getStatus() {
        return status;
    }

    public String getFullAddress() {return fullAddress;}

    public String getReleaseDate() {return releaseDate;}

    public String getPayDate() {return payDate;}

    public String getIndexId() {
        return indexId;
    }

    public void setStatus(String status)
    {
        this.status = status;
    }
}
