package com.example.d7om7.wareed;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;
import android.widget.Spinner;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.GenericTypeIndicator;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Azura on 8/9/2017.
 */

public class CityBloodPreferences {
    List<String> cities;



    public static String [] getBloodTypes (){
        String BloodTypes[] = {"O+","O-","A+","A-","B+","B-","AB+","AB-"};
        return BloodTypes ;

    }

    public List getCities () {

        DatabaseReference CitiesReference ;
        CitiesReference = FirebaseDatabase.getInstance().getReference().child("cities");
        CitiesReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {


                 cities= dataSnapshot.child("cities").getValue(List.class);


            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        return cities ;
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

                    editor.apply();
                }

                @Override
                public void onCancelled(DatabaseError databaseError) {

                }
            });
        }else{
        }



        //editor.putString("email",mAuth.getCurrentUser().getEmail().toString());

    }
}
