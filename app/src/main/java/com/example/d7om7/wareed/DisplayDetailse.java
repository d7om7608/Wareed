package com.example.d7om7.wareed;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class DisplayDetailse extends AppCompatActivity {
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
        Intent intent=getIntent();
        setContentView(R.layout.activity_display_detailse);
         pantienName=(TextView)findViewById(R.id.pantienName);
         fileNumber=(TextView)findViewById(R.id.fileNumber);
         countBlood=(TextView)findViewById(R.id.countBlood);
         reasonOfRequist=(TextView)findViewById(R.id.reasonOfRequist);
         bloodType=(TextView)findViewById(R.id.bloodType);
         NameCity=(TextView)findViewById(R.id.NameCity);
         nameHospetal=(TextView)findViewById(R.id.nameHospetal);

        root = FirebaseDatabase.getInstance().getReference().child("RequestBloosd").child("O+").child("-KqwDA4PBVehFThJgWCP").child("countBlood");

        root.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
              // dataSnapshot.child("countBlood").getValue()+"";
                countBlood.setText(dataSnapshot.getValue()+"");
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });



        pantienName.setText(""+intent.getStringExtra("getPatientName"));
        fileNumber.setText(""+intent.getIntExtra("getPatientFileNumber",0));
      //  countBlood.setText(""+intent.getIntExtra("getCountOfBlood",0));
        reasonOfRequist.setText(""+intent.getStringExtra("getReasonOfRequest"));
        bloodType.setText(""+intent.getStringExtra("getBloodType"));
        NameCity.setText(""+intent.getStringExtra("getCity"));
        nameHospetal.setText(""+intent.getStringExtra("getNameOfHospital"));



    }
}
