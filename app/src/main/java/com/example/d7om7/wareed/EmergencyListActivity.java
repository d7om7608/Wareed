package com.example.d7om7.wareed;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Criteria;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationManager;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import static com.example.d7om7.wareed.menagerModel.donor;


public class EmergencyListActivity extends AppCompatActivity implements Main_status_adapter.changeActivity {

    Main_status_adapter status_adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_emergency_list);
        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.rv_emergency);


        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        status_adapter = new Main_status_adapter(donor.requestBlood, this);
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

    public void GoToChat(View view){
        String s=getIntent().getStringExtra("NameUser");

        Intent ChatIntent = new Intent(EmergencyListActivity.this, ChatActivity.class);
        ChatIntent.putExtra("NameUser", s);
        EmergencyListActivity.this.startActivity(ChatIntent);

    }

    @Override
    public void Clicked(int position, int id) {
        Intent startChildActivityIntent = new Intent(this, DisplayDetailse.class);
        startChildActivityIntent.putExtra("getPatientName", donor.requestBlood.get(position).getPatientName());
        startChildActivityIntent.putExtra("getPatientFileNumber", donor.requestBlood.get(position).getPatientFileNumber());
        startChildActivityIntent.putExtra("getCountOfBlood", donor.requestBlood.get(position).getCountOfBlood());
        startChildActivityIntent.putExtra("getReasonOfRequest", donor.requestBlood.get(position).getReasonOfRequest());
        startChildActivityIntent.putExtra("getBloodType", donor.requestBlood.get(position).getBloodType());
        startChildActivityIntent.putExtra("getCity", donor.requestBlood.get(position).getCity());
        startChildActivityIntent.putExtra("getNameOfHospital", donor.requestBlood.get(position).getNameOfHospital());

        startActivity(startChildActivityIntent);

    }
}
