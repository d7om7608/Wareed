package com.example.d7om7.wareed;

import android.content.Context;
import android.content.SharedPreferences;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;

/**
 * Created by Azura on 8/9/2017.
 */

public class CityBloodPreferences {



    public static String [] getBloodTypes (){
        String BloodTypes[] = {"I don't know","O+","O-","A+","A-","B+","B-","AB+","AB-"};
        return BloodTypes ;

    }

    public void  saveInPrefernces(final DatabaseReference UserData,final FirebaseAuth mAuth,Context c) {

                                /*
                                Save User data in Shared Preference
                                 */
        SharedPreferences sharedPref = c.getSharedPreferences("UserData",0);

        final SharedPreferences.Editor editor = sharedPref.edit();
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
                    editor.putString("age", data.get("age").toString());


                    editor.apply();
                }

                @Override
                public void onCancelled(DatabaseError databaseError) {

                }
            });
        }
    }
}
