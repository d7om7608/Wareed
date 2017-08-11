package com.example.d7om7.wareed;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;



public class EmergencyListActivity extends AppCompatActivity implements Main_status_adapter.changeActivity {

    Main_status_adapter status_adapter;
    ProgressBar progressBar;
    private DatabaseReference root;
    RequestBlood requestBloodopjict;
    List<RequestBlood> requestBlood;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitle("الحالات الطارئه");
        setContentView(R.layout.activity_emergency_list);

        requestBlood = new ArrayList<>();
        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.rv_emergency);

        root = FirebaseDatabase.getInstance().getReference().child("requestblood");
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        status_adapter = new Main_status_adapter(requestBlood, this);
         progressBar = (ProgressBar) findViewById(R.id.progressBarx);
        progressBar.setVisibility(View.VISIBLE);
        progressBar.getIndeterminateDrawable().setColorFilter(Color.parseColor("#ffb3dc"), PorterDuff.Mode.MULTIPLY);


        root.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                progressBar.setVisibility(View.INVISIBLE);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
        root.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                Add_Request(dataSnapshot);
            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {
                Add_Request(dataSnapshot);
            //    progressBar.setVisibility(View.GONE);
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

        recyclerView.setAdapter(status_adapter);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        status_adapter.notifyDataSetChanged();

        /*
        Here LOCATION variables
         */
//        LocationClass location = new LocationClass(this);
//        double lon = location.getLongitude();
//        double lat = location.getLatitude();
//        String city = location.getCurrectCity();
//TODO fares i put here comment
    }

    @Override
    protected void onResume() {
        super.onResume();
    }
    private void Add_Request(DataSnapshot dataSnapshot) {

        Iterator i = dataSnapshot.getChildren().iterator();
        while (i.hasNext()) {

            String CountOfBlood = (String) ((DataSnapshot) i.next()).getValue();

            String BloodType = (String) ((DataSnapshot) i.next()).getValue();

            String PatientFileNumber = (String) ((DataSnapshot) i.next()).getValue();

            String NameOfHospital = (String) ((DataSnapshot) i.next()).getValue();

            String ReasonOfRequest = (String) ((DataSnapshot) i.next()).getValue();

            String RequestID = (String) ((DataSnapshot) i.next()).getValue();

            String UserID = (String) ((DataSnapshot) i.next()).getValue();

            String CountOfdone = (String) ((DataSnapshot) i.next()).getValue();

            String PatientName = (String) ((DataSnapshot) i.next()).getValue();

            String StatusTime = (String) ((DataSnapshot) i.next()).getValue();
            requestBloodopjict = new RequestBlood(PatientName, Integer.valueOf(PatientFileNumber), Integer.valueOf(CountOfBlood), ReasonOfRequest, BloodType, NameOfHospital,
                    StatusTime, RequestID, UserID, Integer.valueOf(CountOfdone));

            requestBlood.add(requestBloodopjict);



            status_adapter.notifyDataSetChanged();
        }


    }




    @Override
    public void Clicked(int position, int id) {
        Intent intent = new Intent(this, DisplayDetails.class);
        intent.putExtra("getRequestID", requestBlood.get(position).getRequestID());
        intent.putExtra("getUserID", requestBlood.get(position).getUserID());
        startActivity(intent);

    }
}
