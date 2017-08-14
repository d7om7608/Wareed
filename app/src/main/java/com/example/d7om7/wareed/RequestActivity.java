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
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

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

    List<String> spinnerArray;
    String selectBloodType;
    List<String> spinnerArrayOfHospetal = null;
    String selectHospetal;
    List<String> spinnerArrayOfcity;
    String selectcity;

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

//---------------------------------------------------------------------
        Intent intent = getIntent();
        String requestID = intent.getStringExtra("requestID");
//---------------------------------------------------------------------
        final Spinner spinner = (Spinner) findViewById(R.id.planets_spiner);
        spinnerArray = Arrays(3);


        ArrayAdapter adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, spinnerArray);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                selectBloodType = spinnerArray.get(position);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                spinner.setSelection(0);
            }

        });
        //___________________________________________________________________________________________


        //---------------------------------------------------------------------
        final Spinner spinnerOfCity = (Spinner) findViewById(R.id.city_spiner);
        spinnerArrayOfcity = Arrays(2);
        ArrayAdapter adapterOfCity = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, spinnerArrayOfcity);
        adapterOfCity.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerOfCity.setAdapter(adapterOfCity);


        //___________________________________________________________________________________________


        //---------------------------------------------------------------------
        final Spinner spinnerOfHospetal = (Spinner) findViewById(R.id.Hospetal_spiner);
        spinnerArrayOfHospetal = Arrays(1);
        ArrayAdapter adapterOfHospetal = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, spinnerArrayOfHospetal);

        adapterOfHospetal.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerOfHospetal.setAdapter(adapterOfHospetal);
        spinnerOfCity.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                selectcity = spinnerArrayOfcity.get(position);
                if (selectcity.equals("makkah"))
                    spinnerArrayOfHospetal = Arrays(1);
                else
                    spinnerArrayOfHospetal = Arrays(4);

                ArrayAdapter adapterOfHospetal = new ArrayAdapter<String>(RequestActivity.this, android.R.layout.simple_spinner_item, spinnerArrayOfHospetal);

                adapterOfHospetal.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                spinnerOfHospetal.setAdapter(adapterOfHospetal);
                adapterOfHospetal.notifyDataSetChanged();
            }


            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                spinnerOfHospetal.setSelection(0);
            }

        });
        spinnerOfHospetal.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                selectHospetal = spinnerArrayOfHospetal.get(position);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        //___________________________________________________________________________________________

        String ReqBloodType = spinner.getSelectedItem().toString()   ;
        String ReqCity = spinnerOfCity.getSelectedItem().toString().trim() ;
        SignFirebaseDatabase = FirebaseDatabase.getInstance();
        SignAuth = FirebaseAuth.getInstance();
        SignprogressDialog = new ProgressDialog(this);




    }

    public void onclick(View view) {

        SignDataBase = FirebaseDatabase.getInstance().getReference().child("cities").child("makkah")
        .child("BloodType").child("A+").child("Requests");

        String FinalDate;
        FinalDate = date.format(calendar.getTime());

        pantienNameText = (EditText) findViewById(R.id.pantienName);
        fileNumberText = (EditText) findViewById(R.id.fileNumber);
        countBloodText = (EditText) findViewById(R.id.countBlood);
        reasonOfRequistText = (EditText) findViewById(R.id.reasonOfRequist);
        SharedPreferences data = getApplicationContext().getSharedPreferences("UserData",0);


        if ( pantienNameText.getText().toString().equals("") ||
                fileNumberText.getText().toString().equals("") || countBloodText.getText().toString().equals("") ||
                reasonOfRequistText.getText().toString().equals("")) {
        } else {
            root =FirebaseDatabase.getInstance().getReference().child("Main").child("cities").child(data.getString("city","null"))
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

    public List<String> Arrays(int i) {
        ArrayList<String> arrayList = new ArrayList<String>();

        if (i == 1) {
            arrayList.add("king khaled");
            arrayList.add("king abdullah");
            arrayList.add("noor");


        } else if (i == 2) {
            arrayList.add("makkah");
            arrayList.add("jeddah");

        } else if (i == 3) {

            arrayList.add("O+");
            arrayList.add("O-");
            arrayList.add("A+");
            arrayList.add("A-");
            arrayList.add("B+");
            arrayList.add("B-");
            arrayList.add("AB+");
            arrayList.add("AB-");

        }
        if (i == 4) {
            arrayList.add("king abdulrahman");
            arrayList.add("king s3od");
        }


        return arrayList;
    }
    public void onBackPressed() {
        Intent ProfileIntent = new Intent(this, MainActivity.class);
        startActivity(ProfileIntent);
        finish();
    }
}
