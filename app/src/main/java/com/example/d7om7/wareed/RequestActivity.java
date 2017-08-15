package com.example.d7om7.wareed;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import static com.example.d7om7.wareed.menagerModel.donor;

public class RequestActivity extends AppCompatActivity {
    RequestBlood requestBlood;
    EditText pantienNameText;
    EditText fileNumberText;
    EditText countBloodText;
    EditText reasonOfRequistText;

    /*
    variables of citiesSpinner
     */
    List<String> CityArray = new ArrayList<String>();
    ArrayAdapter<String> cityadapter;
    Spinner CitySpinner;



    String selectBloodType;
    String selectHospetal;

    private SimpleDateFormat date;
    private Calendar calendar;
    private FirebaseDatabase SignFirebaseDatabase;
    private FirebaseAuth SignAuth;
    private DatabaseReference SignDataBase;
    private ProgressDialog SignprogressDialog;
    private DatabaseReference root;
    private String temp_key;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_requst);
        setTitle("إنشاء طلب للتبرع");



        calendar = Calendar.getInstance();
        date = new SimpleDateFormat("yyyy/MM/dd  :  EEEE", Locale.getDefault());


        final Spinner spinner = (Spinner) findViewById(R.id.planets_spiner);
        final String[] spinnerArray = CityBloodPreferences.getBloodTypes();


        ArrayAdapter adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, spinnerArray);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                selectBloodType = spinnerArray[position];
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                spinner.setSelection(0);
            }

        });

        CitySpinner = (Spinner) findViewById(R.id.city_spiner);
        citiesSpinner();
        SignFirebaseDatabase = FirebaseDatabase.getInstance();
        SignAuth = FirebaseAuth.getInstance();
        SignprogressDialog = new ProgressDialog(this);




    }

    private void citiesSpinner() {
        DatabaseReference root = FirebaseDatabase.getInstance().getReference().child("cities");
        root.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for(DataSnapshot chelldDataSnapshot:dataSnapshot.getChildren() ){

                    String nameCity = chelldDataSnapshot.child("name").getValue().toString();
                    CityArray.add(nameCity);
                    cityadapter.notifyDataSetChanged();

                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
        cityadapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, CityArray);
        CitySpinner.setAdapter(cityadapter);
    }

    public void onclick(View view) {

        SignDataBase = FirebaseDatabase.getInstance().getReference().child("cities").child("makkah")
        .child("BloodType").child("A+").child("Requests");

        String FinalDate;
        FinalDate = date.format(calendar.getTime());
        int selectedCity = CitySpinner.getSelectedItemPosition();
        pantienNameText = (EditText) findViewById(R.id.pantienName);
        fileNumberText = (EditText) findViewById(R.id.fileNumber);
        countBloodText = (EditText) findViewById(R.id.countBlood);
        reasonOfRequistText = (EditText) findViewById(R.id.reasonOfRequist);
        SharedPreferences data = getApplicationContext().getSharedPreferences("UserData",0);


        if ( pantienNameText.getText().toString().equals("") ||
                fileNumberText.getText().toString().equals("") || countBloodText.getText().toString().equals("") ||
                reasonOfRequistText.getText().toString().equals("")) {
        } else {
            root =FirebaseDatabase.getInstance().getReference().child("Main").child("cities").child(String.valueOf(selectedCity))
            .child(selectBloodType).child("cases");

                    Map<String, Object> map = new HashMap<String, Object>();
            temp_key = root.push().getKey();
            root.updateChildren(map);



            DatabaseReference message_root = root.child(temp_key);

//            donor.requestsId.add(temp_key);
            String userID=temp_key;
            Map<String, Object> map2 = new HashMap<String, Object>();
            map2.put("pantienName", pantienNameText.getText().toString());
            map2.put("FileNumber", fileNumberText.getText().toString());
            map2.put("BloodBags", countBloodText.getText().toString());
            map2.put("Reason", reasonOfRequistText.getText().toString());
            map2.put("Hospital", selectHospetal);
            SharedPreferences prefs = getSharedPreferences("UserData", MODE_PRIVATE);

            map2.put("UserID",prefs.getString("id", "NOTHING HERE"));
            map2.put("BloodType", selectBloodType);
            map2.put("statusTime",FinalDate);
            map2.put("RequestID", temp_key);
            map2.put("done", "0");


            message_root.updateChildren(map2);


            Intent startChildActivityIntent = new Intent(this, MainActivity.class);
            startActivity(startChildActivityIntent);
            finish();
            txetEmpty();

        }



    }

    public void txetEmpty() {
        fileNumberText.setText("");
        countBloodText.setText("");
        reasonOfRequistText.setText("");


    }

    public void onBackPressed() {
        Intent ProfileIntent = new Intent(this, MainActivity.class);
        startActivity(ProfileIntent);
        finish();
    }
}
