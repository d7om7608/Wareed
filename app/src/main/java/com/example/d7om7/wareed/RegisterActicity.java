package com.example.d7om7.wareed;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseException;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthProvider;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.concurrent.TimeUnit;

/**
 * Created by Azura on 7/30/2017.
 */


public class RegisterActicity extends AppCompatActivity {

    private FirebaseAuth mAuth;
    private DatabaseReference SearchForProfile;


    private EditText PhoneNumberEditText;
    private EditText VerificationCodeEditText;
    String mVerificationId;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        PhoneNumberEditText = (EditText) findViewById(R.id.phone_number_edit_text);
        VerificationCodeEditText = (EditText) findViewById(R.id.write_verification_code_edit_text);

        mAuth = FirebaseAuth.getInstance();
        SearchForProfile = FirebaseDatabase.getInstance().getReference().child("users");


    }

    public void requestCode(View view) {

        String phoneNumber = "00966"+PhoneNumberEditText.getText().toString().trim();
        if (TextUtils.isEmpty(phoneNumber)) return;

        PhoneAuthProvider.getInstance().verifyPhoneNumber(
                phoneNumber, 60, TimeUnit.SECONDS, RegisterActicity.this, new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {

                    @Override
                    public void onVerificationCompleted(PhoneAuthCredential phoneAuthCredential) {
                        signInWithCredentials(phoneAuthCredential);
                    }

                    @Override
                    public void onVerificationFailed(FirebaseException e) {
                        Toast.makeText(RegisterActicity.this, "Verification Failed " + e.getMessage(), Toast.LENGTH_LONG).show();
                    }

                    @Override
                    public void onCodeSent(String verificationId, PhoneAuthProvider.ForceResendingToken forceResendingToken) {
                        super.onCodeSent(verificationId, forceResendingToken);
                        mVerificationId = verificationId;

                    }

                    @Override
                    public void onCodeAutoRetrievalTimeOut(String verificationID) {
                        super.onCodeAutoRetrievalTimeOut(verificationID);
                        Toast.makeText(RegisterActicity.this, "Time Out", Toast.LENGTH_SHORT).show();


                    }
                }
        );
    }

    public void signInWithCredentials(PhoneAuthCredential phoneAuthCredential) {

        mAuth.signInWithCredential(phoneAuthCredential).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()) {
                    SearchForProfile.addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(DataSnapshot dataSnapshot) {


                            if (dataSnapshot.child(mAuth.getCurrentUser().getUid().toString()).hasChildren()) {
                                CityBloodPreferences c = new CityBloodPreferences();
                                c.saveInPrefernces(SearchForProfile.child(mAuth.getCurrentUser().getUid().toString()),mAuth,getApplicationContext());

                                Toast.makeText(RegisterActicity.this, "Signed in Successfully", Toast.LENGTH_LONG).show();
                                Intent intent = new Intent(RegisterActicity.this, MainActivity.class);
                                startActivity(intent);
                                finish();

                            } else {

                                Intent intent = new Intent(RegisterActicity.this, ProfileActivity.class);
                                startActivity(intent);
                                finish();
                            }
                        }
                        @Override
                        public void onCancelled(DatabaseError databaseError) {
                        }
                    });


                } else {
                    Toast.makeText(RegisterActicity.this, "Failed To Sign In ", Toast.LENGTH_SHORT).show();

                }
            }
        });


    }

    /*public void  saveInPrefernces(final DatabaseReference UserData,final FirebaseAuth mAuth) {


                                Save User data in Shared Preference

        Log.d("Hello","B4 sh pref");
        SharedPreferences sharedPref = getBaseContext().getSharedPreferences("UserData",0);
        Log.d("Hello","after sh pref");

        final SharedPreferences.Editor editor = sharedPref.edit();
        Log.d("Hello","B4 if  user");
        if (UserData != null){
            UserData.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    HashMap<String, String> data = (HashMap) dataSnapshot.getValue();
                    editor.putString("display_name", data.get("user name").toString());
                    editor.putString("phone_number", mAuth.getCurrentUser().getPhoneNumber().toString());
                    editor.putString("id", mAuth.getCurrentUser().getUid().toString());
                    editor.putString("BloodType", data.get("BloodType").toString());
                    editor.putString("city", data.get("city").toString());
                    editor.putString("email", data.get("email").toString());
                    editor.putString("gender", data.get("gender").toString());

                    editor.apply();
                    Log.d("Hello", "display name in donor class:" + data.get("email").toString());
                }

                @Override
                public void onCancelled(DatabaseError databaseError) {

                }
            });
        }else{
            Log.d("Hello","user data null");
        }

        //Log.d("Hello",mAuth.getCurrentUser().getUid().toString());

        //editor.putString("email",mAuth.getCurrentUser().getEmail().toString());

        //Log.d("Hello",sharedPref.getString("display_name","nothing in dispaly name"));
    }*/

    public void signin_button(View view) {

        String code = VerificationCodeEditText.getText().toString().trim();
        if (TextUtils.isEmpty(code)) {
            return;
        }

        signInWithCredentials(PhoneAuthProvider.getCredential(mVerificationId, code));

    }

}
