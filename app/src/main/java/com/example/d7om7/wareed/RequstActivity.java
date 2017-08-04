package com.example.d7om7.wareed;

import android.app.ProgressDialog;
import android.content.Intent;
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

import java.util.ArrayList;
import java.util.List;

import static com.example.d7om7.wareed.menagerModel.donor;

public class RequstActivity extends AppCompatActivity {
    RequestBlood requestBlood;
    EditText requestBloodText;
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

    private FirebaseDatabase SignFirebaseDatabase;
    private FirebaseAuth SignAuth;
    private DatabaseReference SignDataBase;
    private ProgressDialog SignprogressDialog;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_requst);

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
                Log.d("hello", selectcity);
                if (selectcity.equals("makkah"))
                    spinnerArrayOfHospetal = Arrays(1);
                else
                    spinnerArrayOfHospetal = Arrays(4);

                ArrayAdapter adapterOfHospetal = new ArrayAdapter<String>(RequstActivity.this, android.R.layout.simple_spinner_item, spinnerArrayOfHospetal);

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

//        String ReqBloodType =   ;
//        String ReqCity = spinnerOfCity.getSelectedItem().toString().trim() ;
//        String Reqhosptial;
//        SignFirebaseDatabase = FirebaseDatabase.getInstance();
//        SignAuth = FirebaseAuth.getInstance();
//        SignprogressDialog = new ProgressDialog(this);
//        SignDataBase = FirebaseDatabase.getInstance().getReference().child("City").child("BloodType").child(spinnerOfHospetal).child("User").child(Username);




    }

    public void onclick(View view) {
        requestBloodText = (EditText) findViewById(R.id.reasonOfRequist);
        pantienNameText = (EditText) findViewById(R.id.pantienName);
        fileNumberText = (EditText) findViewById(R.id.fileNumber);
        countBloodText = (EditText) findViewById(R.id.countBlood);
        reasonOfRequistText = (EditText) findViewById(R.id.reasonOfRequist);


        if ((requestBloodText.getText().toString()).equals("") || pantienNameText.getText().toString().equals("") ||
                fileNumberText.getText().toString().equals("") || countBloodText.getText().toString().equals("") ||
                reasonOfRequistText.getText().toString().equals("")) {
        } else {

            requestBlood = new RequestBlood(pantienNameText.getText().toString(),
                    Integer.parseInt(fileNumberText.getText().toString()),
                    Integer.parseInt(countBloodText.getText().toString()),
                    reasonOfRequistText.getText().toString(),
                    selectBloodType,
                    selectcity,
                    selectHospetal,
                    "2 Houre",
                    2,
                    donor.getUserID(),
                    0
            );
            Log.d("hello",pantienNameText.getText().toString());
            donor.requestBlood.add(requestBlood);

            Intent startChildActivityIntent = new Intent(this, MainActivity.class);
            startActivity(startChildActivityIntent);
            txetEmpty();

        }

    }

    public void txetEmpty() {
        requestBloodText.setText("");
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
}
