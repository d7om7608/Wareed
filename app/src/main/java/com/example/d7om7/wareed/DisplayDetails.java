package com.example.d7om7.wareed;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class DisplayDetails extends AppCompatActivity {
    TextView pantienName;
    TextView fileNumber;
    TextView countBlood;
    TextView reasonOfRequist;
    TextView bloodType;
    TextView NameCity;
    TextView nameHospetal;
    private DatabaseReference root;
    Intent intent;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
         intent=getIntent();
        setContentView(R.layout.activity_display_detailse);
         pantienName=(TextView)findViewById(R.id.pantienName);
         fileNumber=(TextView)findViewById(R.id.fileNumber);
        root =FirebaseDatabase.getInstance().getReference().child("requestblood").child(intent.getStringExtra("getRequestID"));

         countBlood=(TextView)findViewById(R.id.countBlood);
         reasonOfRequist=(TextView)findViewById(R.id.reasonOfRequist);
         bloodType=(TextView)findViewById(R.id.bloodType);
         NameCity=(TextView)findViewById(R.id.NameCity);
         nameHospetal=(TextView)findViewById(R.id.nameHospetal);




        root.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {


                pantienName.setText( (String) dataSnapshot.child("pantienName").getValue());
                fileNumber.setText( (String) dataSnapshot.child("FileNumber").getValue());
                countBlood.setText( (String) dataSnapshot.child("BloodBags").getValue());
                reasonOfRequist.setText( (String) dataSnapshot.child("Reason").getValue());
                bloodType.setText( (String) dataSnapshot.child("BloodType").getValue());
                NameCity.setText("makkah");
                nameHospetal.setText( (String) dataSnapshot.child("Hospital").getValue());


            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });



    }

    public void GoToChat(View view) {


        String requestID = intent.getStringExtra("getRequestID");
        String userID = intent.getStringExtra("getUserID");

        Intent ChatIntent = new Intent(this, ChatActivity.class);
        ChatIntent.putExtra("requestID", requestID);
        ChatIntent.putExtra("userID", userID);
        startActivity(ChatIntent);

    }
}
