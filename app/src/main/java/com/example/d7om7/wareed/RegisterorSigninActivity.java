package com.example.d7om7.wareed;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

/**
 * Created by Azura on 8/3/2017.
 */

public class RegisterorSigninActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_or_signin);
    }

    public void GoToRegisterActivity (View view){

        Intent RegisterIntent = new Intent(RegisterorSigninActivity.this, RegisterActicity.class);
        startActivity(RegisterIntent);

    }

    public void GoToSignInActivity (View view){

        Intent SignInIntent = new Intent(RegisterorSigninActivity.this, SignInActivity.class);
        startActivity(SignInIntent);


    }
}
