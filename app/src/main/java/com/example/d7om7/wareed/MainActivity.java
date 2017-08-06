package com.example.d7om7.wareed;

<<<<<<< HEAD
=======
import android.app.Dialog;
import android.app.Notification;
import android.app.NotificationManager;
import android.content.Context;
>>>>>>> 9b79c6a373815de624971fb8f9a5dcd400983ad9
import android.content.Intent;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.app.NotificationCompat;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
<<<<<<< HEAD
=======
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.TextView;
import android.widget.Toast;
>>>>>>> 9b79c6a373815de624971fb8f9a5dcd400983ad9

import com.firebase.ui.auth.AuthUI;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

<<<<<<< HEAD
import java.util.Arrays;
=======
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;


>>>>>>> 9b79c6a373815de624971fb8f9a5dcd400983ad9

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

//____________________________________dateStart
private Dialog D_DatePicker;
    private SimpleDateFormat date;
    private Calendar calendar;
    private Button BTN;
    private TextView TEXT;
    //____________________________________dateFinsh

    Button notification;

    public static final int RC_SIGN_IN = 1;
    private FirebaseDatabase mFirebaseDatabase;
    private DatabaseReference mMessagesDatabaseReference;
    private ChildEventListener mChildEventListener;
    private FirebaseAuth mFirebaseAuth;
    private FirebaseAuth.AuthStateListener mAuthStateListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //____________________________________

        menagerModel menagerModel=new menagerModel();
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
        TEXT = (TextView) findViewById(R.id.TEXT);

        calendar = Calendar.getInstance();
        date = new SimpleDateFormat("yyyy/MM/dd  :  EEEE", Locale.getDefault());

        BTN.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DatePicker();
            }
        });
        //____________________________________dateFinsh




        mFirebaseDatabase = FirebaseDatabase.getInstance();
        mFirebaseAuth = FirebaseAuth.getInstance();
        mMessagesDatabaseReference = mFirebaseDatabase.getReference().child("messages");





        mAuthStateListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser user = firebaseAuth.getCurrentUser();
<<<<<<< HEAD
                if (user == null) {

                    Intent intent = new Intent(MainActivity.this,RegisterActicity.class);
                    MainActivity.this.startActivity(intent);

                } else {


                }
=======
//                if (user != null) {
//
//                } else {
//
//                    startActivityForResult(
//                            AuthUI.getInstance()
//                                    .createSignInIntentBuilder()
//                                    .setAvailableProviders(
//                                            Arrays.asList(new AuthUI.IdpConfig.Builder(AuthUI.EMAIL_PROVIDER).build(),
//                                                    new AuthUI.IdpConfig.Builder(AuthUI.PHONE_VERIFICATION_PROVIDER).build(),
//                                                    new AuthUI.IdpConfig.Builder(AuthUI.GOOGLE_PROVIDER).build()))
//                                    .build(),
//                            RC_SIGN_IN);
//                }

>>>>>>> 9b79c6a373815de624971fb8f9a5dcd400983ad9
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
            if (time+2000>System.currentTimeMillis())
            super.onBackPressed();
            else
                Toast.makeText(this, "اضغط مرتين للخروج", Toast.LENGTH_SHORT).show();
        time=System.currentTimeMillis();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();


        if (id == R.id.action_settings) {

            Intent startSettingActivity=new Intent(this,SettingsActivity.class);
            startActivity(startSettingActivity);
            return true;
        }
        if (id == R.id.action_help) {


            return true;
        }
        if (id == R.id.action_sign_out) {

                    AuthUI.getInstance().signOut(this);


            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_Talks) {
            // Handle the camera action
        } else if (id == R.id.nav_profile) {

        }  else if (id == R.id.nav_share) {

        } else if (id == R.id.nav_send) {

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    public void go_to_List_Emergency(View view){

        Intent startChildActivityIntent = new Intent(this, EmergencyListActivity.class);
        startActivity(startChildActivityIntent);

    }
    public void go_to_requst_blood(View view){


        Intent startChildActivityIntent = new Intent(this, RequestActivity.class);
        startActivity(startChildActivityIntent);
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
                TEXT.setText(FinalDate);
                donor.getRequestBlood().get(0).setStatusTime(FinalDate);
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
