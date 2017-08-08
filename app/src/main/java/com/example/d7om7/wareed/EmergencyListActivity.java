package com.example.d7om7.wareed;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import android.view.View;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;



public class EmergencyListActivity extends AppCompatActivity implements Main_status_adapter.changeActivity {

    Main_status_adapter status_adapter;


    private DatabaseReference root;
    RequestBlood requestBloodopjict;
    List<RequestBlood> requestBlood;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestBlood = new ArrayList<>();
        setContentView(R.layout.activity_emergency_list);
        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.rv_emergency);

        root = FirebaseDatabase.getInstance().getReference().child("reguestBlood");
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        status_adapter = new Main_status_adapter(requestBlood, this);
        root.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                Add_Request(dataSnapshot);

            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {
                Add_Request(dataSnapshot);

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
        LocationClass location = new LocationClass(this);
        double lon = location.getLongitude();
        double lat = location.getLatitude();
        String city = location.getCurrectCity();

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
        Intent startChildActivityIntent = new Intent(this, DisplayDetails.class);


        startChildActivityIntent.putExtra("getPatientName", requestBlood.get(position).getPatientName());
        startChildActivityIntent.putExtra("getPatientFileNumber", requestBlood.get(position).getPatientFileNumber());
        startChildActivityIntent.putExtra("getCountOfBlood", requestBlood.get(position).getCountOfBlood());
        startChildActivityIntent.putExtra("getBloodType", requestBlood.get(position).getBloodType());
        startChildActivityIntent.putExtra("getNameOfHospital", requestBlood.get(position).getNameOfHospital());
        startChildActivityIntent.putExtra("getReasonOfRequest", requestBlood.get(position).getReasonOfRequest());
        startChildActivityIntent.putExtra("getCountOfdone", requestBlood.get(position).getCountOfdone());
        startChildActivityIntent.putExtra("getStatusTime", requestBlood.get(position).getStatusTime());
        startChildActivityIntent.putExtra("getRequestID", requestBlood.get(position).getRequestID());
        startChildActivityIntent.putExtra("getUserID", requestBlood.get(position).getUserID());

        startActivity(startChildActivityIntent);

    }
}
