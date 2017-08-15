package com.example.d7om7.wareed;

import android.app.Application;
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

import static android.R.attr.id;

public class ListMyChating extends AppCompatActivity implements AdapterMyChating.changeActivity {
    RecyclerView recyclerView;
    AdapterMyChating adapterMyChating;
    ProgressBar mProgressBar;
    List<InformationOfChating> informationOfChatings;
    DatabaseReference root;
    String UserId;
    SharedPreferences prefs;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_my_chating);
        setTitle("محادثاتي");
        recyclerView = (RecyclerView) findViewById(R.id.my_chat_RecyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        informationOfChatings = new ArrayList<>();
        prefs = getSharedPreferences("UserData", MODE_PRIVATE);
        adapterMyChating = new AdapterMyChating(informationOfChatings, this,getApplicationContext());
        recyclerView.setAdapter(adapterMyChating);
        mProgressBar = (ProgressBar) findViewById(R.id.chatingProgressBar);
        mProgressBar.setVisibility(ProgressBar.VISIBLE);
        adapterMyChating.notifyDataSetChanged();
        if (prefs.getString("id", null) != null) {
            UserId = (prefs.getString("id", "NOTHING HERE"));

        }


        root = FirebaseDatabase.getInstance().getReference().child("MainChat");

        root.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                mProgressBar.setVisibility(View.INVISIBLE);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        root.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                for (DataSnapshot chelldDataSnapshotIdRequester : dataSnapshot.getChildren()) {

                    String idRequester = chelldDataSnapshotIdRequester.getKey();

                    if (idRequester.equals(prefs.getString("id", "NOTHING HERE"))) {
                        for (DataSnapshot chelldDataSnapshotIdDoner : chelldDataSnapshotIdRequester.getChildren()) {
                            String IdDoner = chelldDataSnapshotIdDoner.getKey();

                            for (DataSnapshot chelldDataSnapshotIdRequest : chelldDataSnapshotIdDoner.getChildren()) {
                                String IdRequest = chelldDataSnapshotIdRequest.getKey();
                                InformationOfChating informationOfChatings1 = new InformationOfChating(idRequester, IdDoner, IdRequest);
                                informationOfChatings.add(informationOfChatings1);
                                adapterMyChating.notifyDataSetChanged();
                            }

                        }
                    }
                }

                for (DataSnapshot chelldDataSnapshotIdRequester : dataSnapshot.getChildren()) {

                    String idRequester = chelldDataSnapshotIdRequester.getKey();

                        for (DataSnapshot chelldDataSnapshotIdDoner : chelldDataSnapshotIdRequester.getChildren()) {
                            String IdDoner = chelldDataSnapshotIdDoner.getKey();
                            if (IdDoner.equals(prefs.getString("id", "NOTHING HERE"))) {

                            for (DataSnapshot chelldDataSnapshotIdRequest : chelldDataSnapshotIdDoner.getChildren()) {
                                String IdRequest = chelldDataSnapshotIdRequest.getKey();
                                InformationOfChating informationOfChatings1 = new InformationOfChating(idRequester, IdDoner, IdRequest);
                                informationOfChatings.add(informationOfChatings1);
                                adapterMyChating.notifyDataSetChanged();
                            }

                        }
                    }
                }

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
        intent.putExtra("FileNumber", informationOfChatings.get(position).getRequesterID());
        intent.putExtra("NameDoner", informationOfChatings.get(position).getNameDoner());

        startActivity(intent);
        finish();

    }

    String nameRequster;
    String nameDoner;
    String DonerID;
    String RequestID;


    @Override
    protected void onResume() {
        super.onResume();
        adapterMyChating.notifyDataSetChanged();
    }

    @Override
    protected void onPause() {
        super.onPause();
        adapterMyChating.notifyDataSetChanged();
    }

    public void onBackPressed() {

        Intent ProfileIntent = new Intent(this, MainActivity.class);
        startActivity(ProfileIntent);
        finish();
    }
}
