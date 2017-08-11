package com.example.d7om7.wareed;

import android.content.Intent;
import android.content.SharedPreferences;
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

import static android.R.attr.id;

public class MyCases extends AppCompatActivity implements AdapterMyCases.changeActivity {

    AdapterMyCases myCases_adapter;
    ProgressBar progressBar;
    private DatabaseReference root;
    RequestBlood requestBloodopjict;
    List<RequestBlood> requestBlood;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_cases);
        setTitle("حالاتي");

        requestBlood = new ArrayList<>();
        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.rv_emergency);

        root = FirebaseDatabase.getInstance().getReference().child("requestblood");
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        //progressBar.setVisibility(View.VISIBLE);
        myCases_adapter = new AdapterMyCases(requestBlood, this);
        progressBar = (ProgressBar) findViewById(R.id.progressBarx);
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
                //  progressBar.setVisibility(View.GONE);
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

        recyclerView.setAdapter(myCases_adapter);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        myCases_adapter.notifyDataSetChanged();

        /*
        Here LOCATION variables
         */
        LocationClass location = new LocationClass(this);
        double lon = location.getLongitude();
        double lat = location.getLatitude();
        String city = location.getCurrectCity();

    }

    @Override
    protected void onResume() {
        super.onResume();
//        progressBar.setVisibility(View.GONE);
    }
    private void Add_Request(DataSnapshot dataSnapshot) {
        SharedPreferences prefs = getSharedPreferences("UserData", MODE_PRIVATE);
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
            

            if (UserID.equals(prefs.getString("id", "NOTHING HERE"))) {
                requestBlood.add(requestBloodopjict);
            }

                myCases_adapter.notifyDataSetChanged();

        }

    }




    @Override
    public void Clicked(int position, int id) {
        Intent startChildActivityIntent = new Intent(this, MyCasesDetails.class);


        startChildActivityIntent.putExtra("getRequestID", requestBlood.get(position).getRequestID());
        startChildActivityIntent.putExtra("getUserID", requestBlood.get(position).getUserID());

        startActivity(startChildActivityIntent);

    }
}

