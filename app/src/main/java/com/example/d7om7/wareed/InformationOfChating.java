package com.example.d7om7.wareed;

/**
 * Created by d7om7 on 8/11/2017.
 */

public class InformationOfChating {
    String nameRequster;
    String nameDoner;
    String fileNumber;

    public InformationOfChating(String nameRequster,String nameDoner,String fileNumber) {
        this.nameRequster=nameRequster;
        this.nameDoner=nameDoner;
        this.fileNumber=fileNumber;
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

    public String getFileNumber() {
        return fileNumber;
    }

    public void setFileNumber(String fileNumber) {
        this.fileNumber = fileNumber;
    }
}
