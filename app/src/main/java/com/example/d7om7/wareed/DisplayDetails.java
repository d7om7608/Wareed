package com.example.d7om7.wareed;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import static android.R.attr.data;

public class DisplayDetails extends AppCompatActivity {
    TextView pantienName;
    TextView fileNumber;
    TextView countBlood;
    TextView reasonOfRequist;
    TextView bloodType;
    TextView dateStatus;
    TextView nameHospetal;
    private DatabaseReference root;
    TextView countDone;
    Intent intent;
    ProgressBar mProgressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        intent = getIntent();
        setContentView(R.layout.activity_display_detailse);
        setTitle("تفاصيل الحالة");

        pantienName = (TextView) findViewById(R.id.pantienName);
        fileNumber = (TextView) findViewById(R.id.fileNumber);
        String requestID = intent.getStringExtra("getRequestID");
        SharedPreferences data = getApplicationContext().getSharedPreferences("UserData", 0);
        root = FirebaseDatabase.getInstance().getReference().child("Main").child("cities").child(data.getString("city", "null"))
                .child(data.getString("BloodType", "null")).child("cases").child(requestID);
        countDone = (TextView) findViewById(R.id.countDone);
        countBlood = (TextView) findViewById(R.id.countBlood);
        reasonOfRequist = (TextView) findViewById(R.id.reasonOfRequist);
        bloodType = (TextView) findViewById(R.id.bloodType);
        nameHospetal = (TextView) findViewById(R.id.nameHospetal);
        dateStatus = (TextView) findViewById(R.id.dateStatus);
        mProgressBar = (ProgressBar) findViewById(R.id.progressDesplayDetails);
        mProgressBar.setVisibility(ProgressBar.VISIBLE);

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

                countDone.setText((String) dataSnapshot.child("done").getValue());
                pantienName.setText((String) dataSnapshot.child("pantienName").getValue());
                fileNumber.setText((String) dataSnapshot.child("FileNumber").getValue());
                countBlood.setText((String) dataSnapshot.child("BloodBags").getValue());
                reasonOfRequist.setText((String) dataSnapshot.child("Reason").getValue());
                bloodType.setText((String) dataSnapshot.child("BloodType").getValue());
                dateStatus.setText((String) dataSnapshot.child("statusTime").getValue());
                nameHospetal.setText((String) dataSnapshot.child("Hospital").getValue());


            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });


    }

    public void GoToChat(View view) {


        String requestID = intent.getStringExtra("getRequestID");
        String userID = intent.getStringExtra("getUserID");

        SharedPreferences prefs = getSharedPreferences("UserData", MODE_PRIVATE);

        if (userID.equals(prefs.getString("id", "NOTHING HERE"))) {
            Log.d("hello", userID);
            Log.d("hello", prefs.getString("id", "NOTHING HERE"));

            Toast.makeText(getApplicationContext(), "هذه الحاله خاصه بك", Toast.LENGTH_SHORT).show();
        } else {
            Intent ChatIntent = new Intent(this, ChatActivity.class);
            ChatIntent.putExtra("requestID", requestID);
            ChatIntent.putExtra("userID", userID);
            startActivity(ChatIntent);
            finish();

        }
    }

    public void onBackPressed() {
        Intent ProfileIntent = new Intent(this, EmergencyListActivity.class);
        startActivity(ProfileIntent);
        finish();
    }
}
