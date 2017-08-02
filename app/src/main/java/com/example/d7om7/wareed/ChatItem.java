package com.example.d7om7.wareed;

/**
 * Created by Azura on 8/1/2017.
 */

public class ChatItem {
    String title;
    boolean check;
    int id;
    int Chatid;
    //    public TodoItem(String s, boolean b){
//        title=s;
//        check=b;
//    }
    public ChatItem(String s, boolean b,int Chatid){
        title=s;
        check=b;
        this.Chatid=Chatid;

    }
}
