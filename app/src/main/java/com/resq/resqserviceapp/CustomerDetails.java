package com.resq.resqserviceapp;

public class CustomerDetails {

    private String companyName;
    private String contactPerson;
    private String eMail;
    private long contactNumber;
    private  String address;

    public CustomerDetails(){
        companyName = "";
        contactPerson = "";
        eMail = "";
        contactNumber = 0;
        address = "";
    }

    public void setCompanyName(String userCompanyName){
        companyName = userCompanyName;
    }

    public void setContactPerson(String userContactNumber){
        contactPerson = userContactNumber;
    }

    public void seteMail(String userEmail){
        eMail = userEmail;
    }

    public void setContactNumber(long userContactNumber){
        contactNumber = userContactNumber;
    }

    public void setAddress(String userAddress){
        address = userAddress;
    }

    public String getCompanyName(){
        return companyName;
    }

    public String getContactPerson(){
        return contactPerson;
    }

    public String geteMail(){
        return eMail;
    }

    public long getContactNumber(){
        return contactNumber;
    }

    public String getAddress(){
        return address;
    }

    public String getFullDetails(){
        return "Company Name: "+companyName+"\nContact Person: "+contactPerson+"\nE-Mail: "+eMail+"\nContact Number: "+String.valueOf(contactNumber)+"\nAddress: "+address;
    }
}
