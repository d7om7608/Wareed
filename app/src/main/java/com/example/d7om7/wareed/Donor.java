package com.example.d7om7.wareed;

import java.util.List;

/**
 * Created by d7om7 on 7/30/2017.
 */

public class Donor {

    protected String name;
    protected String phoneNumber;
    protected int donateCount;
    protected String city;
    protected String bloodType;
    protected String lastDonatingDate;
    protected String lastNotificationDate;
    protected String userID;
    protected List<String> requestsId;


    public String getRequestBlood(int position) {
        return requestsId.get(position);
    }

    public void setRequestBlood(String requestId) {
        this.requestsId.add(requestId);
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public int getDonateCount() {
        return donateCount;
    }

    public void setDonateCount(int donateCount) {
        this.donateCount = donateCount;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }


    public String getBloodType() {
        return bloodType;
    }

    public void setBloodType(String bloodType) {
        this.bloodType = bloodType;
    }

    public String getLastDonatingDate() {
        return lastDonatingDate;
    }

    public void setLastDonatingDate(String lastDonatingDate) {
        this.lastDonatingDate = lastDonatingDate;
    }

    public String getLastNotificationDate() {
        return lastNotificationDate;
    }

    public void setLastNotificationDate(String lastNotificationDate) {
        this.lastNotificationDate = lastNotificationDate;
    }

    public String getUserID() {
        return this.userID;
    }

    public void setUserID(String userID) {
        this.userID = userID;
    }


}
