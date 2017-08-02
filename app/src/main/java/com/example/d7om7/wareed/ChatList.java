package com.example.d7om7.wareed;

import java.util.List;

/**
 * Created by Azura on 8/1/2017.
 */

public class ChatList {
    public String title;
    public List<ChatItem>items;
    public int size;
    public int id;
    //    public TodoList(String title, List<TodoItem>items){
//        this.title=title;
//        this.items=items;
//
//    }
    public ChatList(String title, List<ChatItem>items,int id,int size){
        this.title=title;
        this.items=items;
        this.id=id;
        this.size=size;

    }

}