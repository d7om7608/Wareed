package com.example.d7om7.wareed;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import java.util.ArrayList;

import static com.example.d7om7.wareed.menagerModel.donor;


public class EmergencyListActivity extends AppCompatActivity implements Main_status_adapter.changeActivity {

    Main_status_adapter status_adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_emergency_list);
        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.rv_emergency);


        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        status_adapter=new Main_status_adapter(donor.requestBlood,this);
        recyclerView.setAdapter(status_adapter);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        status_adapter.notifyDataSetChanged();

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
