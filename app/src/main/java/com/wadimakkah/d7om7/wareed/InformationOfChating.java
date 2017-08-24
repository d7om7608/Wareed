package com.wadimakkah.d7om7.wareed;

/**
 * Created by d7om7 on 8/11/2017.
 */

public class InformationOfChating {
    String nameRequster;
    String nameDoner;
    String requestID;

    public InformationOfChating(String nameRequster,String nameDoner,String requestID) {
        this.nameRequster=nameRequster;
        this.nameDoner=nameDoner;
        this.requestID=requestID;
    }

    public String getNameRequster() {
        return nameRequster;
    }

    public void setNameRequster(String nameRequster) {
        this.nameRequster = nameRequster;
    }

    public String getNameDoner() {
        return nameDoner;
    }

    public void setNameDoner(String nameDoner) {
        this.nameDoner = nameDoner;
    }

    public String getRequestID() {
        return requestID;
    }

    public void setRequestID(String requestID) {
        this.requestID = requestID;
    }
}
