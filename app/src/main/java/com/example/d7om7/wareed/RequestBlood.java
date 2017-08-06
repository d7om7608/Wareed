package com.example.d7om7.wareed;



public class RequestBlood {
    String patientName;
    int patientFileNumber;
    int countOfBlood;
    int countOfdone;
    String reasonOfRequest;
    String bloodType;
    String city;
    String nameOfHospital;
    String requestID;
    String userID;
    String statusTime;

    public RequestBlood(String patientName, int patientFileNumber, int countOfBlood, String reasonOfRequest, String bloodType, String city, String nameOfHospital,
                        String statusTime, String requestID, String userID, int countOfdone) {
        this.patientName=patientName;
        this.patientFileNumber=patientFileNumber;
        this.countOfBlood=countOfBlood;
        this.reasonOfRequest=reasonOfRequest;
        this.bloodType=bloodType;
        this.city=city;
        this.nameOfHospital=nameOfHospital;
        this.requestID=requestID;
        this.userID=userID;
        this.statusTime=statusTime;
        this.countOfdone=countOfdone;

    }

    public int getCountOfdone() {
        return countOfdone;
    }

    public void setCountOfdone(int countOfdone) {
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

    public int getPatientFileNumber() {
        return patientFileNumber;
    }

    public void setPatientFileNumber(int patientFileNumber) {
        this.patientFileNumber = patientFileNumber;
    }

    public int getCountOfBlood() {
        return countOfBlood;
    }

    public void setCountOfBlood(int countOfBlood) {
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

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
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
