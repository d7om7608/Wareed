package com.example.d7om7.wareed;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import java.util.ArrayList;

import static com.example.d7om7.wareed.menagerModel.donor;


public class EmergencyListActivity extends AppCompatActivity {

    Main_status_adapter status_adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_emergency_list);
        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.rv_emergency);


        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        status_adapter=new Main_status_adapter(donor.requestBlood);
        recyclerView.setAdapter(status_adapter);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        status_adapter.notifyDataSetChanged();

    }

}
