package com.example.d7om7.wareed;


import android.app.Dialog;
import android.content.Context;
import android.content.Intent;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;


public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {
    Donor donor;

    //____________________________________dateStart
    private Dialog D_DatePicker;
    private SimpleDateFormat date;
    private Calendar calendar;
    private Button BTN;
    //____________________________________dateFinsh

    Button notification;

    public static final int RC_SIGN_IN = 1;
    private FirebaseDatabase mFirebaseDatabase;
    private DatabaseReference SearchForProfile;
    private ChildEventListener mChildEventListener;
    private FirebaseAuth mFirebaseAuth;
    private FirebaseAuth.AuthStateListener mAuthStateListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //____________________________________

        menagerModel menagerModel = new menagerModel();
        menagerModel.creatDonor();
        //_____________________________________
        setContentView(R.layout.sidebar);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        //__________________________________________________


        //____________________________________dateStart
        BTN = (Button) findViewById(R.id.BTN);


        calendar = Calendar.getInstance();
        date = new SimpleDateFormat("yyyy/MM/dd  :  EEEE", Locale.getDefault());

        BTN.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DatePicker();
            }
        });
        //____________________________________DateFinish


        mFirebaseDatabase = FirebaseDatabase.getInstance();
        mFirebaseAuth = FirebaseAuth.getInstance();

        mAuthStateListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser user = firebaseAuth.getCurrentUser();

                SharedPreferences data = getPreferences(Context.MODE_PRIVATE);

                if (user == null && data.getString("id", null) == null) {
                    Intent intent = new Intent(MainActivity.this, RegisterActicity.class);
                    MainActivity.this.startActivity(intent);
                    finish();
                }
            }
        };


    }

    @Override
    protected void onResume() {
        super.onResume();
        mFirebaseAuth.addAuthStateListener(mAuthStateListener);
    }

    @Override
    protected void onPause() {
        super.onPause();
        mFirebaseAuth.removeAuthStateListener(mAuthStateListener);
    }

    long time;

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            if (time + 2000 > System.currentTimeMillis())
                super.onBackPressed();
            else
                Toast.makeText(this, "اضغط مرتين للخروج", Toast.LENGTH_SHORT).show();
            time = System.currentTimeMillis();
        }
    }


    @SuppressWarnings("StatmentWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_mainActivity) {
            Intent ProfileIntent = new Intent(MainActivity.this, MainActivity.class);
            startActivity(ProfileIntent);

        } else if (id == R.id.nav_Talks) {
            Intent ProfileIntent = new Intent(MainActivity.this, ListMyChating.class);
            startActivity(ProfileIntent);
            finish();
        } else if (id == R.id.nav_profile) {
            Intent ProfileIntent = new Intent(MainActivity.this, ProfileActivity.class);
            startActivity(ProfileIntent);
            finish();

        } else if (id == R.id.nav_signout) {

            SharedPreferences data = getPreferences(MODE_PRIVATE);
            data.edit().clear().commit();
            mFirebaseAuth.signOut();

        } else if (id == R.id.nav_myCases) {
            Intent ProfileIntent = new Intent(MainActivity.this, MyCases.class);
            startActivity(ProfileIntent);
            finish();


        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    public void go_to_List_Emergency(View view) {

        Intent startChildActivityIntent = new Intent(this, EmergencyListActivity.class);
        startActivity(startChildActivityIntent);
        finish();
    }

    public void go_to_requst_blood(View view) {


        Intent startChildActivityIntent = new Intent(this, RequestActivity.class);
        startActivity(startChildActivityIntent);
        finish();
    }


    public void DatePicker() {


        D_DatePicker = new Dialog(this);
        D_DatePicker.setContentView(R.layout.dilalog_date_picker);
        final DatePicker datepicker = (DatePicker) D_DatePicker.findViewById(R.id.date_picker);
        Button BTN_GetDate = (Button) D_DatePicker.findViewById(R.id.BTN_GetDate);
        Button BTN_Close = (Button) D_DatePicker.findViewById(R.id.BTN_Close);

        datepicker.setMinDate(calendar.getTimeInMillis());

        Calendar calendar_1 = Calendar.getInstance();
        calendar_1.add(Calendar.MONTH, 24);
        datepicker.setMaxDate(calendar_1.getTimeInMillis());

        BTN_GetDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final Calendar calendar2 = Calendar.getInstance();
                String FinalDate;
                calendar2.set(datepicker.getYear(), datepicker.getMonth(), datepicker.getDayOfMonth());
                FinalDate = date.format(calendar2.getTime());
                D_DatePicker.dismiss();
            }
        });
        BTN_Close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                D_DatePicker.dismiss();
            }
        });

        D_DatePicker.show();
    }


}
