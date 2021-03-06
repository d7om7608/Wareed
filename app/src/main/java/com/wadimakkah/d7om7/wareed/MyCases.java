package com.wadimakkah.d7om7.wareed;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class MyCases extends AppCompatActivity implements AdapterMyCases.changeActivity {

    AdapterMyCases myCases_adapter;
    private DatabaseReference root;
    RequestBlood requestBloodopjict;
    List<RequestBlood> requestBlood;
    RecyclerView recyclerView;
    private Dialog UpdateCase;
    TextView nullText;
    SharedPreferences prefs;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_cases);


        prefs = getApplicationContext().getSharedPreferences("UserData", MODE_PRIVATE);
        requestBlood = new ArrayList<>();
        recyclerView = (RecyclerView) findViewById(R.id.rv_emergency);
        nullText=(TextView)findViewById(R.id.nullMyCases);
                root = FirebaseDatabase.getInstance().getReference().child("Main").child("cities").child(prefs.getString("city", "null"));

        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        myCases_adapter = new AdapterMyCases(requestBlood, this);
        final ProgressDialog progressDialog = new ProgressDialog(this);

        progressDialog.setTitle("Please wait");
        progressDialog.setMessage("Loading...");
        progressDialog.setCancelable(false);
        progressDialog.setCanceledOnTouchOutside(false);
        progressDialog.show();


        root.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                progressDialog.dismiss();
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

                            String location = (String) chelldDataSnapshotCases.child("location").getValue();

                            requestBloodopjict = new RequestBlood(PatientName, NameCity, (PatientFileNumber), (CountOfBlood), ReasonOfRequest, BloodType, NameOfHospital,
                                    StatusTime, RequestID, UserID, (CountOfdone), latOfHospital, lngOfHospital,location);


                            requestBlood.add(requestBloodopjict);
                        }

                        myCases_adapter.notifyDataSetChanged();


                    }
                }
                if (requestBlood.isEmpty())
                    nullText.setVisibility(View.VISIBLE);
                else
                    nullText.setVisibility(View.INVISIBLE);

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });


        recyclerView.setAdapter(myCases_adapter);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        myCases_adapter.notifyDataSetChanged();


    }

    @Override
    protected void onResume() {
        super.onResume();
    }




    public void onBackPressed() {
        Intent ProfileIntent = new Intent(this, MainActivity.class);
        startActivity(ProfileIntent);
        finish();
    }

    @Override
    public void ClickedDelet(int position, int id) {


        root.child(requestBlood.get(position).bloodType).child("cases").child(requestBlood.get(position).getRequestID()).removeValue();

        requestBlood.clear();
        myCases_adapter.notifyDataSetChanged();


    }


    @Override
    public void ClickedUpdate(final int position, int id) {


        UpdateCase = new Dialog(this);
        UpdateCase.setContentView(R.layout.dilalog_update_my_cases);
        Button BTN_CloseMydetails = (Button) UpdateCase.findViewById(R.id.BTN_CloseMydetails);
        Button BTN_OKMyDetails = (Button) UpdateCase.findViewById(R.id.BTN_OKMyDetails);
        final EditText countBloodUpdate = (EditText) UpdateCase.findViewById(R.id.countBloodUpdate);
        countBloodUpdate.setText(requestBlood.get(position).getCountOfBlood());
        BTN_OKMyDetails.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (countBloodUpdate.getText().toString().equals("")) {
                    Toast.makeText(getApplicationContext(), "Please Enter Number", Toast.LENGTH_SHORT).show();
                } else {

                    SharedPreferences data = getSharedPreferences("UserData", 0);
                    root.child(data.getString("BloodType", null))
                            .child("cases").child(requestBlood.get(position).getRequestID()).child("BloodBags").setValue(countBloodUpdate.getText().toString());
                    requestBlood.clear();
                    myCases_adapter.notifyDataSetChanged();

                    UpdateCase.dismiss();
                    Toast.makeText(getApplicationContext(), "Date Saved", Toast.LENGTH_SHORT).show();


                }
            }
        });
        BTN_CloseMydetails.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                UpdateCase.dismiss();
            }
        });

        UpdateCase.show();
    }


    protected void removeCity() {
        SharedPreferences data = getSharedPreferences("UserData", 0);
        FirebaseDatabase.getInstance().getReference().child("Main").child("cities").child(data.getString("city", null)).child(data.getString("BloodType", null))
                .child("users").child(data.getString("id", null)).removeValue();
    }
}

