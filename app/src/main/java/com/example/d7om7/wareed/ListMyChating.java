package com.example.d7om7.wareed;

import android.app.Notification;
import android.app.NotificationManager;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
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
import java.util.List;

public class ListMyChating extends AppCompatActivity implements AdapterMyChating.changeActivity {

    AdapterMyChating adapterMyChating;
    ProgressBar mProgressBar;
    List<InformationOfChating> informationOfChatings;
    DatabaseReference root;
    String UserId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_my_chating);
        setTitle("محادثاتي");
        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.my_chat_RecyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        informationOfChatings = new ArrayList<>();
        adapterMyChating = new AdapterMyChating(informationOfChatings, this);
        recyclerView.setAdapter(adapterMyChating);
        mProgressBar = (ProgressBar) findViewById(R.id.chatingProgressBar);
        mProgressBar.setVisibility(ProgressBar.VISIBLE);
        SharedPreferences prefs = getSharedPreferences("UserData", MODE_PRIVATE);
//        InformationOfChating informationOfChatings1=new InformationOfChating("5hIjxtjnCTY4BdpFVHPssIMbWOb2","5hIjxtjnCTY4BdpFVHPssIMbWOb2","-KrHTmI_i-JIhPgZxHox");
//        informationOfChatings.add(informationOfChatings1);
        adapterMyChating.notifyDataSetChanged();
        if (prefs.getString("id", null) != null) {
            UserId = (prefs.getString("id", "NOTHING HERE"));

        }


        root = FirebaseDatabase.getInstance().getReference();

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
                AddMyChating(dataSnapshot);
            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {
                AddMyChating(dataSnapshot);
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


    @Override
    public void Clicked(int position, int id) {

        Intent intent = new Intent(this, ChatActivity.class);
        intent.putExtra("NameRequster", informationOfChatings.get(position).getNameRequster());
        intent.putExtra("FileNumber", informationOfChatings.get(position).getFileNumber());
        intent.putExtra("NameDoner", informationOfChatings.get(position).getNameDoner());

        startActivity(intent);
        finish();

    }

    String nameRequster;
    String nameDoner;
    String DonerID;
    String RequestID;

    private void AddMyChating(DataSnapshot dataSnapshot) {
        SharedPreferences prefs = getSharedPreferences("UserData", MODE_PRIVATE);
        Iterator iRequster = dataSnapshot.getChildren().iterator();
        while (iRequster.hasNext()) {


            nameRequster = ((DataSnapshot) iRequster.next()).getKey();
            Iterator i2 = dataSnapshot.child(nameRequster).getChildren().iterator();

            while (i2.hasNext()) {
                String id=((DataSnapshot) i2.next()).getKey();
                if (id.equals(prefs.getString("id", "NOTHING HERE"))){
                DonerID = id;

                Iterator i3 = dataSnapshot.child(nameRequster).child(DonerID).getChildren().iterator();
                while (i3.hasNext()) {

                    RequestID = (String) ((DataSnapshot) i3.next()).getKey();

//                    String senderName ="ss"+dataSnapshot.child("users").child(DonerID).child("user name").getValue().toString();
//                    String nameRequsest ="ss"+dataSnapshot.child("requestblood").child(nameRequster).child("Reason").getValue().toString();
//                    String pantentName ="ss"+dataSnapshot.child("users").child(RequestID).child("user name").getValue().toString();
                    InformationOfChating informationOfChatings1 = new InformationOfChating(nameRequster, DonerID, RequestID);
                    informationOfChatings.add(informationOfChatings1);

                }
            }

             }
        }

        adapterMyChating.notifyDataSetChanged();
        Iterator idoer = dataSnapshot.getChildren().iterator();

        while (idoer.hasNext()) {
            String id= ((DataSnapshot) idoer.next()).getKey();
            if (id.equals(prefs.getString("id", "NOTHING HERE"))){

                nameRequster = id;
                Iterator i2 = dataSnapshot.child(nameRequster).getChildren().iterator();

                while (i2.hasNext()) {

                    DonerID = ((DataSnapshot) i2.next()).getKey();

                    Iterator i3 = dataSnapshot.child(nameRequster).child(DonerID).getChildren().iterator();
                    while (i3.hasNext()) {

                        RequestID =  ((DataSnapshot) i3.next()).getKey();
//                        String senderName ="ss"+dataSnapshot.child("users").child(DonerID).child("user name").getValue().toString();
//                        String nameRequsest ="ss"+dataSnapshot.child("requestblood").child(nameRequster).child("Reason").getValue().toString();
//                        String pantentName ="ss"+dataSnapshot.child("users").child(RequestID).child("user name").getValue().toString();

                        InformationOfChating informationOfChatings1 = new InformationOfChating(nameRequster, DonerID, RequestID);

                        informationOfChatings.add(informationOfChatings1);

                    }
                }

            }
        }



    }
}
