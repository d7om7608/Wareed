package com.example.d7om7.wareed;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.firebase.ui.auth.User;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.List;

import static android.R.attr.phoneNumber;
import static android.R.attr.start;

/**
 * Created by Azura on 7/30/2017.
 */

public class RegisterActicity extends AppCompatActivity {

    private EditText UserNameEditText;
    private EditText PasswordEditText;
    private Spinner BloodTypeSpinner;
    private Spinner CitySpinner;
    private EditText PhoneNumberEditText;
    public static String chatUserName ;
    public static String userBloodType;

    private FirebaseDatabase SignFirebaseDatabase;
    private FirebaseAuth SignAuth;
    private DatabaseReference SignDataBase;
    private ProgressDialog SignprogressDialog;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        UserNameEditText = (EditText) findViewById(R.id.reg_username_editText);
        PasswordEditText = (EditText) findViewById(R.id.reg_password_editText);
        BloodTypeSpinner = (Spinner) findViewById(R.id.blood_type_spinner);
        CitySpinner = (Spinner) findViewById(R.id.city_spiner);
        PhoneNumberEditText = (EditText) findViewById(R.id.reg_phoneNumber_editText);

        String Username = UserNameEditText.getText().toString().trim();

        // Blood Type Spinner
        final List<String> BloodList = new ArrayList<String>();
        BloodList.add("O+");
        BloodList.add("O-");
        BloodList.add("A+");
        BloodList.add("A-");
        BloodList.add("B+");
        BloodList.add("B-");
        BloodList.add("AB+");
        BloodList.add("AB-");
        ArrayAdapter<String> BloodSpinnerAdapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_list_item_1, BloodList);
        BloodSpinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        BloodTypeSpinner.setAdapter(BloodSpinnerAdapter);
        userBloodType = BloodTypeSpinner.getSelectedItem().toString();

//        //City Spinner
//        final List<String> CityList = new ArrayList<String>();
//        CityList.add("Makkah");
//        CityList.add("Jeddah");
//        CityList.add("Ta'if");
//        CityList.add("Riyadh");
//        CityList.add("Dammam");
//        CityList.add("Albaha");
//        CityList.add("Bahra");
//        CityList.add("Abha");
//        ArrayAdapter<String> CitySpinnerAdapter = new ArrayAdapter<String>(this,
//                android.R.layout.simple_list_item_2, CityList);
//        BloodSpinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
//        CitySpinner.setAdapter(CitySpinnerAdapter);
//        String userCity = CitySpinner.getSelectedItem().toString();






        SignFirebaseDatabase = FirebaseDatabase.getInstance();
        SignAuth = FirebaseAuth.getInstance();
        SignprogressDialog = new ProgressDialog(this);
        SignDataBase = FirebaseDatabase.getInstance().getReference().child("City").child("BloodType").child(userBloodType).child("User").child(Username);
    }










    public void DoRegister(View view) {

        final String UserName = UserNameEditText.getText().toString().trim();
        final String UserPassword = PasswordEditText.getText().toString().trim();
        final String PhoneNumber = PhoneNumberEditText.getText().toString().trim();

        if (TextUtils.isEmpty(UserName) || TextUtils.isEmpty(UserPassword) || TextUtils.isEmpty(PhoneNumber)) {

            Toast.makeText(RegisterActicity.this, "Missing data", Toast.LENGTH_SHORT).show();
        } else {

            SignprogressDialog.setMessage("Signing up");
            SignprogressDialog.show();
            SignAuth.signInAnonymously().addOnCompleteListener(new OnCompleteListener<AuthResult>() {

            @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
//                    Log.d("test", "onComplete " + task.isSuccessful());
                    if (task.isSuccessful()) {

                        String user_id = SignAuth.getCurrentUser().getUid();
                        DatabaseReference current_user_db = SignDataBase.child(UserName);
                        current_user_db.child("UserNAme").setValue(UserName);
                        current_user_db.child("PhoneNumber").setValue(PhoneNumber);
                        current_user_db.child("Password").setValue(UserPassword);
                        current_user_db.child("UserId").setValue(user_id);

                        Intent mainIntent = new Intent(RegisterActicity.this, MainActivity.class);
                        mainIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        startActivity(mainIntent);


                    }

                    SignprogressDialog.dismiss();
                }
            });


        }
    }
}
