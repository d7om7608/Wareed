package com.example.d7om7.wareed;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.List;


/**
 * Created by Azura on 8/6/2017.
 */

public class ProfileActivity extends AppCompatActivity {

    CityBloodPreferences cityBloodActivity = new CityBloodPreferences() ;
    RegisterActicity registerActicity = new RegisterActicity();

    private FirebaseDatabase SignFirebaseDatabase;
    private FirebaseAuth SignAuth;
    private DatabaseReference SignDataBase;
    private DatabaseReference SignInCity;
    private DatabaseReference CheckForPreferences;


    private EditText UserNameEditText;
    private EditText EmailEditText;
    private Spinner BloodTypeSpinner ;
    private Spinner CitySpinner ;
    private Spinner GenderSpinner ;

    String UsernameTooked ;
    String Email;
    String BloodTypeTooked  ;
    String CityTooked ;
    String GenderTooked;
    String UserUID ;



    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

//        Ui components
        UserNameEditText = (EditText) findViewById(R.id.username_edittext);
        EmailEditText = (EditText) findViewById(R.id.email_edittext);
        BloodTypeSpinner = (Spinner) findViewById(R.id.profile_bloodType_spinner);
        CitySpinner = (Spinner) findViewById(R.id.profile_city_spinner);
        GenderSpinner = (Spinner) findViewById(R.id.profile_gender_spinner);

        BloodSpinner();
        CitySpinner();
        GenderSpinner();


//        FireBase
        SignFirebaseDatabase = FirebaseDatabase.getInstance();
        SignAuth = FirebaseAuth.getInstance();

        SharedPreferences prefs = getSharedPreferences("UserData",MODE_PRIVATE);

        if(prefs.getString("id",null) != null){
            UserNameEditText.setText(prefs.getString("display_name","NOTHING HERE"));
            EmailEditText.setText(prefs.getString("email","NOTHING HERE"));
            CitySpinner.setSelection(Integer.parseInt(prefs.getString("BloodType","NOTHING HERE")));

        }







    }


    public void CitySpinner () {
        // TODO: ger city from CityBlood Activity
        String CityArray [] = {"makkah\n", "jeddah\n"};
//        List CityArray  = cityBloodActivity.getCities();
        ArrayAdapter<String> cityadapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, CityArray);
        CitySpinner.setAdapter(cityadapter);


    }


    public void BloodSpinner () {
        String[] BloodArray = cityBloodActivity.getBloodTypes();
        ArrayAdapter<String> bloodadapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_item, BloodArray);
        BloodTypeSpinner.setAdapter(bloodadapter);

    }

    public void GenderSpinner () {
        String GenderArray [] = {"male \n", "female\n"};
        ArrayAdapter<String> genderadapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_item, GenderArray);
        GenderSpinner.setAdapter(genderadapter);

    }

    public void SaveSettingsOnClick (View view){

        UserUID = SignAuth.getCurrentUser().getUid();
        UsernameTooked = UserNameEditText.getText().toString().trim();
        BloodTypeTooked = BloodTypeSpinner.getSelectedItem().toString().trim();
        CityTooked = CitySpinner.getSelectedItem().toString().trim();
        GenderTooked = GenderSpinner.getSelectedItem().toString().trim();
        Email = EmailEditText.getText().toString().trim();

        SignDataBase = FirebaseDatabase.getInstance().getReference().child("users");
        SignInCity = FirebaseDatabase.getInstance().getReference().child("cities").child(CityTooked)
                .child("bloodtype").child(BloodTypeTooked).child("users");

        if (UsernameTooked.isEmpty()||BloodTypeTooked.isEmpty()){

            Toast.makeText(ProfileActivity.this,"Missing Data",Toast.LENGTH_SHORT).show();
        }

        else {

            DatabaseReference current_user_db = SignDataBase.child(UserUID);
            current_user_db.child("user name").setValue(UsernameTooked);
            current_user_db.child("BloodType").setValue(BloodTypeTooked);
            current_user_db.child("gender").setValue(GenderTooked);
            current_user_db.child("city").setValue(CityTooked);
            current_user_db.child("email").setValue(Email);

            DatabaseReference current_user_db_city = SignInCity.child(UserUID);
            Log.d("Hello","B4 method");
            CityBloodPreferences c = new CityBloodPreferences();
            c.saveInPrefernces(current_user_db,SignAuth,getApplicationContext());
            Log.d("Hello","After method");

            Intent intent = new Intent(ProfileActivity.this, MainActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(intent);
            finish();


        }



    }


}
