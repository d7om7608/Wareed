package com.example.d7om7.wareed;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.google.firebase.database.FirebaseDatabase;

/**
 * Created by Azura on 8/3/2017.
 */

public class SignInActivity extends AppCompatActivity {

    private FirebaseDatabase mDatabase ;

    private EditText SignUserNameEditText ;
    private EditText SignPassWordEditText ;
    private Button SignInButton;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signin);

        SignPassWordEditText = (EditText) findViewById(R.id.signin_username_edittext);
        SignPassWordEditText = (EditText) findViewById(R.id.signin_password_edittext);
        SignInButton = (Button) findViewById(R.id.signin_button);

        mDatabase = FirebaseDatabase.getInstance();

        SignInButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

    }


}
