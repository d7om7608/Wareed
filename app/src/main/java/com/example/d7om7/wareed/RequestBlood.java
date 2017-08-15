package com.example.d7om7.wareed;



public class RequestBlood {
    String patientName;
    String patientFileNumber;
    String countOfBlood;
    String countOfdone;
    String reasonOfRequest;
    String bloodType;

    String nameOfHospital;
    String requestID;
    String userID;
    String statusTime;

    public RequestBlood(String patientName, String patientFileNumber, String countOfBlood, String reasonOfRequest, String bloodType, String nameOfHospital,
                        String statusTime, String requestID, String userID, String countOfdone) {
        this.patientName=patientName;
        this.patientFileNumber=patientFileNumber;
        this.countOfBlood=countOfBlood;
        this.reasonOfRequest=reasonOfRequest;
        this.bloodType=bloodType;

        this.nameOfHospital=nameOfHospital;
        this.requestID=requestID;
        this.userID=userID;
        this.statusTime=statusTime;
        this.countOfdone=countOfdone;

    }

    public String getCountOfdone() {
        return countOfdone;
    }

    public void setCountOfdone(String countOfdone) {
        this.countOfdone = countOfdone;
    }

    public String getStatusTime() {
        return statusTime;
    }

    public void setStatusTime(String statusTime) {
        this.statusTime = statusTime;
    }



    public String getPatientName() {
        return patientName;
    }

    public void setPatientName(String patientName) {
        this.patientName = patientName;
    }

    public String getPatientFileNumber() {
        return patientFileNumber;
    }

    public void setPatientFileNumber(String patientFileNumber) {
        this.patientFileNumber = patientFileNumber;
    }

    public String getCountOfBlood() {
        return countOfBlood;
    }

    public void setCountOfBlood(String countOfBlood) {
        this.countOfBlood = countOfBlood;
    }

    public String getReasonOfRequest() {
        return reasonOfRequest;
    }

    public void setReasonOfRequest(String reasonOfRequest) {
        this.reasonOfRequest = reasonOfRequest;
    }

    public String getBloodType() {
        return bloodType;
    }

    public void setBloodType(String bloodType) {
        this.bloodType = bloodType;
    }


    public String getNameOfHospital() {
        return nameOfHospital;
    }

    public void setNameOfHospital(String nameOfHospital) {
        this.nameOfHospital = nameOfHospital;
    }

    public String getRequestID() {
        return requestID;
    }

    public void setRequestID(String requestID) {
        this.requestID = requestID;
    }

    public String getUserID() {
        return userID;
    }

    public void setUserID(String userID) {
        this.userID = userID;
    }
}
