package com.wadimakkah.d7om7.wareed;


public class ChatModel{
     String message;
     String isSend;
     String name;
    public ChatModel(String message, String isSend,String name) {
        this.message = message;
        this.isSend = isSend;
        this.name =name;
    }

    public String getIsSend() {
        return isSend;
    }

    public void setIsSend(String isSend) {
        this.isSend = isSend;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }


    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String isSend() {
        return isSend;
    }

    public void setSend(String send) {
        isSend = send;
    }
}
