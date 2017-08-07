package com.example.d7om7.wareed;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.android.gms.auth.api.Auth;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

/**
 * Created by Azura on 8/6/2017.
 */

public class ProfileActivity extends AppCompatActivity {

    private FirebaseDatabase SignFirebaseDatabase;
    private FirebaseAuth SignAuth;
    private DatabaseReference SignDataBase;

    private EditText UserNameEditText;
    private Spinner BloodTypeSpinner ;
    private Spinner CitySpinner ;
    private Spinner GenderSpinner ;

    String UsernameTooked ;
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
        BloodTypeSpinner = (Spinner) findViewById(R.id.profile_bloodType_spinner);
        CitySpinner = (Spinner) findViewById(R.id.profile_city_spinner);
        GenderSpinner = (Spinner) findViewById(R.id.profile_gender_spinner);


        BloodSpinner();
        CitySpinner();
        GenderSpinner();


//        FireBase
        SignFirebaseDatabase = FirebaseDatabase.getInstance();
        SignAuth = FirebaseAuth.getInstance();



    }

    public void CitySpinner () {
        String CityArray [] = {"makkah\n", "jeddah\n"};
        ArrayAdapter<String> cityadapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_item, CityArray);
        CitySpinner.setAdapter(cityadapter);

    }



    public void BloodSpinner () {
        String BloodArray [] = {"A+\n", "A-\n", "B+\n", "B-\n", "AB+\n" , "AB-\n" , "O+\n" , "O-\n"};
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

        SignDataBase = FirebaseDatabase.getInstance().getReference().child("cities").child(CityTooked).child(BloodTypeTooked)
                .child("users").child(GenderTooked);

        if (UsernameTooked.isEmpty()||BloodTypeTooked.isEmpty()){

            Toast.makeText(ProfileActivity.this,"Missing Data",Toast.LENGTH_SHORT).show();
        }

        else {

            DatabaseReference current_user_db = SignDataBase.child(UserUID);
            current_user_db.child("user name").setValue(UsernameTooked);
            current_user_db.child("BloodType").setValue(BloodTypeTooked);

            Intent intent = new Intent(ProfileActivity.this, MainActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(intent);
            finish();


        }



    }


}
