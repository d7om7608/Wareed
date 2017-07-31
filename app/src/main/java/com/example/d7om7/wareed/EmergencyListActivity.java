package com.example.d7om7.wareed;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;

import java.util.ArrayList;


public class EmergencyListActivity extends AppCompatActivity {
    Donor donor;
    Main_status_adapter status_adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_emergency_list);
        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.rv_emergency);


         donor=new Donor("ahmad","0552777608",0,"makkah","alnassem","O+","0","0",1,new ArrayList<RequestBlood>());
         RequestBlood requestBlood=new RequestBlood("khaled",125467,3,"عمليه جراحيه","A+","makkah","king khaled","1438/11/1",1,donor.getUserID(),2);
         donor.requestBlood.add(requestBlood);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        status_adapter=new Main_status_adapter(donor.requestBlood);
        recyclerView.setAdapter(status_adapter);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        status_adapter.notifyDataSetChanged();

    }

}
