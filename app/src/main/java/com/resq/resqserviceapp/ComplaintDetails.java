package com.resq.resqserviceapp;

public class ComplaintDetails {

    private String type;
    private String model;
    private String serialNumber;
    private String warrantyType;
    private String description;
    private String date;
    private String time;
    private String complaintID;
    public CustomerDetails customerDetails = new CustomerDetails();

    public ComplaintDetails(){
        type = "";
        model = "";
        serialNumber = "";
        warrantyType = "";
        description = "";
        
    }

    public String getFinalString(){
        String fromCustomerDetails = customerDetails.getFullDetails();
        String fromComplainDetails = "Type: "+type+"\nModel Number: "+model+"\nSerial Number: "+serialNumber+"\nWarranty Type: "+warrantyType+"\nDescription: "+description+"\nDate: "+date+"\nTime: "+time;
        return "Customer Details: \n\n"+fromCustomerDetails+"\n\nComplaint Details: \n\n"+fromComplainDetails;
    }

    public String getType(){
        return type;
    }

    public String getModel(){return model;}

    public String getSerialNumber(){
        return serialNumber;
    }

    public String getWarrantyType(){
        return warrantyType;
    }

    public String getDescription(){
        return description;
    }

    public String getDate(){ return date; }

    public String getTime(){return time;}

    public String getComplaintID(){return complaintID;}

    public void setType(String userType){
        type = userType;
    }

    public void setModel(String userModel){model = userModel;}

    public void setSerialNumber(String userSerialNumber){
        serialNumber = userSerialNumber;
    }

    public void setWarrantyType(String userWarrantyType){
        warrantyType = userWarrantyType;
    }

    public void setDescription(String userDescription){
        description = userDescription;
    }

    public void setDate(String userDate){date = userDate;}

    public void setTime(String userTime){time = userTime;}

    public void setComplaintID(String userComplaintID){complaintID = userComplaintID;}

}
