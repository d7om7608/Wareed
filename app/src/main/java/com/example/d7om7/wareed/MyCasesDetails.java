package com.example.d7om7.wareed;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.example.d7om7.wareed.MainActivity;
import com.example.d7om7.wareed.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import static android.R.attr.data;

public class MyCasesDetails extends AppCompatActivity {
    TextView pantienName;
    TextView fileNumber;
    TextView countBlood;
    TextView reasonOfRequist;
    TextView bloodType;
    TextView dateStatus;
    TextView nameHospetal;
    private DatabaseReference root;
    EditText countDone;
    Intent intent;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_cases_details);
        intent = getIntent();
        setTitle("تفاصيل حالتي");
        SharedPreferences data = getApplicationContext().getSharedPreferences("UserData", 0);

        pantienName = (TextView) findViewById(R.id.pantienName);
        fileNumber = (TextView) findViewById(R.id.fileNumber);
        root = FirebaseDatabase.getInstance().getReference().child("Main").child("cities").child(data.getString("city", "null"))
                .child(data.getString("BloodType", "null")).child("cases").child(intent.getStringExtra("getRequestID"));
        countDone = (EditText) findViewById(R.id.countDone);
        countBlood = (TextView) findViewById(R.id.countBlood);
        reasonOfRequist = (TextView) findViewById(R.id.reasonOfRequist);
        bloodType = (TextView) findViewById(R.id.bloodType);
        nameHospetal = (TextView) findViewById(R.id.nameHospetal);
        dateStatus = (TextView) findViewById(R.id.dateStatus);


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

    public void saveChanget(View view) {


        root.child("done").setValue(countDone.getText().toString());

        Intent ChatIntent = new Intent(this, MainActivity.class);
        startActivity(ChatIntent);
        finish();

    }

    public void deletMyCase(View view) {
        root.removeValue();
        Intent ChatIntent = new Intent(this, MyCases.class);
        startActivity(ChatIntent);
        finish();

    }
}

