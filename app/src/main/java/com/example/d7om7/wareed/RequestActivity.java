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
import android.widget.Toast;

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

public class RequestActivity extends AppCompatActivity {
    RequestBlood requestBlood;
    EditText pantienNameText;
    EditText fileNumberText;
    EditText countBloodText;
    EditText reasonOfRequistText;
    int selectedCity;
    String NameCity;
    /*
    variables of citiesSpinner
     */
    List<String> CityArray = new ArrayList<String>();
    List<String> HospitalsArray = new ArrayList<String>();
    ArrayAdapter<String> cityadapter;
    ArrayAdapter<String> hospitalsAdapter;

    Spinner CitySpinner;
    Spinner Hospetal_spiner;
     Spinner spinner;

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
    DatabaseReference rootspinnerHospital;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_requst);
        setTitle("إنشاء طلب للتبرع");


        calendar = Calendar.getInstance();
        date = new SimpleDateFormat("yyyy/MM/dd", Locale.getDefault());


         spinner = (Spinner) findViewById(R.id.planets_spiner);
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
        Hospetal_spiner = (Spinner) findViewById(R.id.Hospetal_spiner);
        CitySpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            public void onItemSelected(AdapterView<?> arg0, View v, int position, long id)

            {
                selectedCity = position;
                Log.d("hello", position + "");
                rootspinnerHospital = FirebaseDatabase.getInstance().getReference().child("cities").child("" + selectedCity)
                        .child("hospitals");
                HospetalSpinner();


            }

            public void onNothingSelected(AdapterView<?> arg0) {
            }
        });


    }

    private void HospetalSpinner() {
        HospitalsArray = new ArrayList<>();
        rootspinnerHospital = FirebaseDatabase.getInstance().getReference().child("cities").child("" + selectedCity)
                .child("hospitals");
        rootspinnerHospital.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot chelldDataSnapshotHospital : dataSnapshot.getChildren()) {
                    String hospitals = "";
                    hospitals = chelldDataSnapshotHospital.child("name").getValue().toString();
                    HospitalsArray.add(hospitals);
                    hospitalsAdapter.notifyDataSetChanged();

                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
        hospitalsAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, HospitalsArray);
        Hospetal_spiner.setAdapter(hospitalsAdapter);

    }

    private void citiesSpinner() {
        DatabaseReference root = FirebaseDatabase.getInstance().getReference().child("cities");
        root.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot chelldDataSnapshot : dataSnapshot.getChildren()) {

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

        String FinalDate;
        FinalDate = date.format(calendar.getTime());
        selectedCity = CitySpinner.getSelectedItemPosition();

        pantienNameText = (EditText) findViewById(R.id.pantienName);
        fileNumberText = (EditText) findViewById(R.id.fileNumber);
        countBloodText = (EditText) findViewById(R.id.countDone);
        reasonOfRequistText = (EditText) findViewById(R.id.reasonOfRequist);
        SharedPreferences data = getApplicationContext().getSharedPreferences("UserData", 0);


        if (pantienNameText.getText().toString().equals("") )
            Toast.makeText(getApplicationContext(), "الرجاء كتابة الاسم", Toast.LENGTH_SHORT).show();
        else if (fileNumberText.getText().toString().equals(""))
            Toast.makeText(getApplicationContext(), "الرجاء كتابه رقم ملف المريض", Toast.LENGTH_SHORT).show();
        else if (countBloodText.getText().toString().equals(""))
            Toast.makeText(getApplicationContext(), "الرجاء كتابة كم عدد الاكياس المطلوبه", Toast.LENGTH_SHORT).show();

        else if (reasonOfRequistText.getText().toString().equals(""))
            Toast.makeText(getApplicationContext(), "الرجاء كتابة سبب التنويم", Toast.LENGTH_SHORT).show();

        else if (spinner.getSelectedItem()==null)
            Toast.makeText(getApplicationContext(), "الرجاء اختيار فصيلة الدم", Toast.LENGTH_SHORT).show();

        else if (Hospetal_spiner.getSelectedItem()==null)
            Toast.makeText(getApplicationContext(), "الرجاء اختيار اسم المستشفى", Toast.LENGTH_SHORT).show();
        else if (CitySpinner.getSelectedItem()==null)
            Toast.makeText(getApplicationContext(), "الرجاء اختيار اسم المدينه", Toast.LENGTH_SHORT).show();

        else {

            selectHospetal=Hospetal_spiner.getSelectedItem().toString();
            NameCity=CitySpinner.getSelectedItem().toString();
            root = FirebaseDatabase.getInstance().getReference().child("Main").child("cities").child(String.valueOf(selectedCity))
                    .child(selectBloodType).child("cases");

            Map<String, Object> map = new HashMap<String, Object>();
            temp_key = root.push().getKey();
            root.updateChildren(map);


            DatabaseReference message_root = root.child(temp_key);

//            donor.requestsId.add(temp_key);
            String userID = temp_key;
            Map<String, Object> map2 = new HashMap<String, Object>();
            map2.put("pantienName", pantienNameText.getText().toString());
            map2.put("FileNumber", fileNumberText.getText().toString());
            map2.put("BloodBags", countBloodText.getText().toString());
            map2.put("Reason", reasonOfRequistText.getText().toString());
            map2.put("Hospital", selectHospetal);
            SharedPreferences prefs = getSharedPreferences("UserData", MODE_PRIVATE);

            map2.put("UserID", prefs.getString("id", "NOTHING HERE"));
            map2.put("BloodType", selectBloodType);
            map2.put("statusTime", FinalDate);
            map2.put("RequestID", temp_key);
            map2.put("done", "0");
            map2.put("lastNotificationSent","0");
            map2.put("NameCity",NameCity);


            if(message_root.updateChildren(map2) != null){
                Toast.makeText(getApplicationContext(),"تمت إضافة الحالة بنجاح",Toast.LENGTH_LONG);
                Intent startChildActivityIntent = new Intent(this, MainActivity.class);
                startActivity(startChildActivityIntent);
                finish();
                txetEmpty();
            }else{
                Toast.makeText(getApplicationContext(),"حصل خطأ في إضافة الحالة",Toast.LENGTH_LONG);
            }



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
