package com.example.d7om7.wareed;

import android.app.Notification;
import android.app.NotificationManager;
import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.ProgressBar;

import com.example.d7om7.wareed.R;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Iterator;

public class ListMyChating extends AppCompatActivity/* implements AdapterMyChating.changeActivity*/{
    private ArrayList<String> arrayList = new ArrayList<>();
    AdapterMyChating adapterMyChating;
    ProgressBar mProgressBar;
    ListView ChatListView;
    DatabaseReference root;
    String UserId;
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_my_chating);
        setTitle("محادثاتي");
        ChatListView = (ListView) findViewById(R.id.my_chat_list_view);
//        adapterMyChating = new Main_status_adapter(requestBlood, this);
//        ChatListView.setAdapter(adapterMyChating);
        mProgressBar = (ProgressBar) findViewById(R.id.chatingProgressBar);
        mProgressBar.setVisibility(ProgressBar.INVISIBLE);
        SharedPreferences prefs = getSharedPreferences("UserData", MODE_PRIVATE);

        if (prefs.getString("id", null) != null) {
            UserId = (prefs.getString("id", "NOTHING HERE"));

        }


        root = FirebaseDatabase.getInstance().getReference().child("MainChat").child(UserId);
        root.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                mProgressBar.setVisibility(View.INVISIBLE);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
        root.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                //Add_Chat(dataSnapshot);

            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {
               // Add_Chat(dataSnapshot);


             //   arrayAdapter.notifyDataSetChanged();


            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {

            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });


    }





}
