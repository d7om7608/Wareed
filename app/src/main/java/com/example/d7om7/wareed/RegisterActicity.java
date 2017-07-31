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
    private EditText PhoneNumberEditText;

    private FirebaseDatabase SignFirebaseDatabase;
    private FirebaseAuth SignAuth;
    private DatabaseReference SignDataBase;
    private ProgressDialog SignprogressDialog ;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        UserNameEditText = (EditText) findViewById(R.id.signIn_username_editText);
        PasswordEditText = (EditText) findViewById(R.id.signIn_password_editText);
        BloodTypeSpinner = (Spinner) findViewById(R.id.blood_type_spinner);
        PhoneNumberEditText = (EditText) findViewById(R.id.signIn_phoneNumber_editText);

        final List<String> list = new ArrayList<String>();
        list.add("O+");
        list.add("O+");
        list.add("A+");
        list.add("A-");
        list.add("B+");
        list.add("B-");
        list.add("AB+");
        list.add("AB-");
        ArrayAdapter<String> BloodSpinnerAdapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_list_item_1, list);
        BloodSpinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        BloodTypeSpinner.setAdapter(BloodSpinnerAdapter);


        SignFirebaseDatabase = FirebaseDatabase.getInstance();
        SignAuth = FirebaseAuth.getInstance();
        SignprogressDialog = new ProgressDialog(this);
        SignDataBase = FirebaseDatabase.getInstance().getReference().child("Users");
    }

    public void DoRegister(View view){

        final String UserName = UserNameEditText.getText().toString().trim();
        final String UserPassword = PasswordEditText.getText().toString().trim();
        final String PhoneNumber = PhoneNumberEditText.getText().toString().trim();
        final String BloodType = BloodTypeSpinner.getSelectedItem().toString().trim();

        if (TextUtils.isEmpty(UserName)||TextUtils.isEmpty(UserPassword)||TextUtils.isEmpty(PhoneNumber)||TextUtils.isEmpty(BloodType)){
            System.out.print("445");
            Toast.makeText(RegisterActicity.this,"Missing data",Toast.LENGTH_SHORT).show();
        }
        else {

            Toast.makeText(RegisterActicity.this,"no data missing",Toast.LENGTH_SHORT).show();
            SignprogressDialog.setMessage("Signing up");
            SignprogressDialog.show();
            SignAuth.signInAnonymously().addOnCompleteListener(new OnCompleteListener<AuthResult>() {//not name &mail its number and blood instead
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    Log.d("test","onComplete "+task.isSuccessful());
                    Toast.makeText(RegisterActicity.this,"in on complete",Toast.LENGTH_SHORT).show();
                    if(task.isSuccessful()){

                        Toast.makeText(RegisterActicity.this,"task successful",Toast.LENGTH_SHORT).show();
                        String user_id = SignAuth.getCurrentUser().getUid();
                        DatabaseReference current_user_db = SignDataBase.child(user_id);
                        current_user_db.child("UserName").setValue(UserName);
                        current_user_db.child("PhoneNumber").setValue(PhoneNumber);
                        current_user_db.child("BloodType").setValue(BloodType);
                        current_user_db.child("Password").setValue(UserPassword);



                        Intent mainIntent = new Intent(RegisterActicity.this,MainActivity.class);
                        mainIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        startActivity(mainIntent);


                    }

                    SignprogressDialog.dismiss();
                }
            });


        }
    }
}
