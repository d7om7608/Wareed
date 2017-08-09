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
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Azura on 8/9/2017.
 */

public class CityBloodActivity {


    public static String [] getBloodTypes (){
        String BloodTypes[] = {"O+","O-","A+","A-","B+","B-","AB+","AB-"};
        return BloodTypes ;

    }

    public void getCities () {

        DatabaseReference CitiesReference ;
        CitiesReference = FirebaseDatabase.getInstance().getReference().child("cities");
//        Map<String, String> BloodArray = CitiesReference.child("cities")
            return ;
    }

    public void  saveInPrefernces(final DatabaseReference UserData,final FirebaseAuth mAuth,Context c) {

                                /*
                                Save User data in Shared Preference
                                 */
        Log.d("Hello","B4 sh pref");
        SharedPreferences sharedPref = c.getSharedPreferences("UserData",0);
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
    }
}
