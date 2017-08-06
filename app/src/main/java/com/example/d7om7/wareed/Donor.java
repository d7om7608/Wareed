package com.example.d7om7.wareed;

import java.util.ArrayList;
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
    protected String UserID;
    protected List<String> requestsId;

    public Donor(String name, String phoneNumber, int donateCount, String city
            , String bloodType, String lastDonatingDate, String lastNotificationDate, String UserID,
                 List<String> requestsId) {
        this.name = name;
        this.phoneNumber = phoneNumber;
        this.donateCount = donateCount;
        this.city = city;
        this.bloodType = bloodType;
        this.lastDonatingDate = lastDonatingDate;
        this.lastNotificationDate = lastNotificationDate;
        this.UserID = UserID;
        this.requestsId = requestsId;
    }

    public String getRequestBlood(int position) {
        return requestsId.get(position);
    }

    public void setRequestBlood(String requestId) {
        this.requestsId.add(requestId);
    }

    public String getName() {
        return name;
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
        return UserID;
    }

    public void setUserID(String userID) {
        UserID = userID;
    }


}
