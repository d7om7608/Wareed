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



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        root =FirebaseDatabase.getInstance().getReference().child("reguestBlood").child("-KqxW7KbUD1X9QRJbidj");
        Intent intent=getIntent();
        setContentView(R.layout.activity_display_detailse);
         pantienName=(TextView)findViewById(R.id.pantienName);
         fileNumber=(TextView)findViewById(R.id.fileNumber);


         countBlood=(TextView)findViewById(R.id.countBlood);
         reasonOfRequist=(TextView)findViewById(R.id.reasonOfRequist);
         bloodType=(TextView)findViewById(R.id.bloodType);
         NameCity=(TextView)findViewById(R.id.NameCity);
         nameHospetal=(TextView)findViewById(R.id.nameHospetal);




        pantienName.setText(intent.getStringExtra("getPatientName"));
        fileNumber.setText(intent.getStringExtra("getPatientFileNumber"));
        countBlood.setText(""+intent.getIntExtra("getCountOfBlood",0));
        reasonOfRequist.setText(intent.getStringExtra("getReasonOfRequest"));
        bloodType.setText(intent.getStringExtra("getBloodType"));
        NameCity.setText("makkah");
        nameHospetal.setText(intent.getStringExtra("getNameOfHospital"));



    }

    public void GoToChat(View view) {
        String s = getIntent().getStringExtra("NameUser");

        Intent ChatIntent = new Intent(this, ChatActivity.class);
        ChatIntent.putExtra("NameUser", s);
        startActivity(ChatIntent);

    }
}
