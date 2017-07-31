package com.example.d7om7.wareed;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by d7om7 on 7/30/2017.
 */

public class Donor {

    String name;
    String phoneNumber;
    int donateCount;
    String city;
    String neighborhood ;
    String bloodType;
    String lastDonatingDate;
    String lastNotificationDate;
    int UserID;
    List<RequestBlood >requestBlood;
    public Donor(String name, String phoneNumber, int donateCount, String city
            , String neighborhood , String bloodType, String lastDonatingDate, String lastNotificationDate, int UserID,
                List<RequestBlood > requestBlood) {
        this.name=name;
        this.phoneNumber=phoneNumber;
        this.donateCount=donateCount;
        this.city=city;
        this.neighborhood=neighborhood;
        this.bloodType=bloodType;
        this.lastDonatingDate=lastDonatingDate;
        this.lastNotificationDate=lastNotificationDate;
        this.UserID=UserID;
        this.requestBlood=requestBlood;
    }

    public List<RequestBlood> getRequestBlood() {
        return requestBlood;
    }

    public void setRequestBlood(List<RequestBlood> requestBlood) {
        this.requestBlood = requestBlood;
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

    public String getNeighborhood() {
        return neighborhood;
    }

    public void setNeighborhood(String neighborhood) {
        this.neighborhood = neighborhood;
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

    public int getUserID() {
        return UserID;
    }

    public void setUserID(int userID) {
        UserID = userID;
    }



}
