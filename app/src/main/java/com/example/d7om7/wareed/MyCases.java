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
import android.support.v7.widget.Toolbar;
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

import static android.R.attr.data;
import static android.R.attr.id;

public class MyCases extends AppCompatActivity implements AdapterMyCases.changeActivity {

    AdapterMyCases myCases_adapter;
    ProgressBar progressBar;
    private DatabaseReference root;
    RequestBlood requestBloodopjict;
    List<RequestBlood> requestBlood;
    SharedPreferences prefs;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_cases);


        prefs = getApplicationContext().getSharedPreferences("UserData", MODE_PRIVATE);
        requestBlood = new ArrayList<>();
        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.rv_emergency);

        root = FirebaseDatabase.getInstance().getReference().child("Main").child("cities").child(prefs.getString("city", "null"));

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


        root.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                for (DataSnapshot chelldDataSnapshotBloodType : dataSnapshot.getChildren()) {

                    for (DataSnapshot chelldDataSnapshotCases : chelldDataSnapshotBloodType.child("cases").getChildren()) {

                        String UserID = (String) chelldDataSnapshotCases.child("UserID").getValue();
                        if (UserID.equals(prefs.getString("id", "NOTHING HERE"))) {

                            String CountOfBlood = (String) chelldDataSnapshotCases.child("BloodBags").getValue();

                            String BloodType = (String) chelldDataSnapshotCases.child("BloodType").getValue();

                            String PatientFileNumber = (String) chelldDataSnapshotCases.child("FileNumber").getValue();

                            String NameOfHospital = (String) chelldDataSnapshotCases.child("Hospital").getValue();

                            String ReasonOfRequest = (String) chelldDataSnapshotCases.child("Reason").getValue();

                            String RequestID = (String) chelldDataSnapshotCases.child("RequestID").getValue();



                            String CountOfdone = (String) chelldDataSnapshotCases.child("done").getValue();

                            String PatientName = (String) chelldDataSnapshotCases.child("pantienName").getValue();

                            String StatusTime = (String) chelldDataSnapshotCases.child("statusTime").getValue();

                            String NameCity = (String) chelldDataSnapshotCases.child("NameCity").getValue();

                            String latOfHospital = (String) chelldDataSnapshotCases.child("latOfHospital").getValue();

                            String lngOfHospital = (String) chelldDataSnapshotCases.child("lngOfHospital").getValue();

                            requestBloodopjict = new RequestBlood(PatientName,NameCity, (PatientFileNumber),(CountOfBlood), ReasonOfRequest, BloodType, NameOfHospital,
                                    StatusTime, RequestID, UserID,(CountOfdone),latOfHospital,lngOfHospital);



                                requestBlood.add(requestBloodopjict);
                            }

                            myCases_adapter.notifyDataSetChanged();





                    }
                }
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
        Iterator i = dataSnapshot.getChildren().iterator();


        while (i.hasNext()) {



        }

    }


    @Override
    public void Clicked(int position, int id) {
        Intent startChildActivityIntent = new Intent(this, MyCasesDetails.class);

        startChildActivityIntent.putExtra("getRequestID", requestBlood.get(position).getRequestID());
        startChildActivityIntent.putExtra("getUserID", requestBlood.get(position).getUserID());

        startActivity(startChildActivityIntent);
        finish();

    }

    public void onBackPressed() {
        Intent ProfileIntent = new Intent(this, MainActivity.class);
        startActivity(ProfileIntent);
        finish();
    }
}

