package com.example.d7om7.wareed;


import android.app.Dialog;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;

import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.drawable.ColorDrawable;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.app.NotificationCompat;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;

import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

import java.util.ArrayList;
import java.util.List;


public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener, Main_status_adapter.changeActivity {


    public static final int RC_SIGN_IN = 1;
    private FirebaseDatabase mFirebaseDatabase;
    private DatabaseReference SearchForProfile;
    private ChildEventListener mChildEventListener;
    private FirebaseAuth mFirebaseAuth;
    private FirebaseAuth.AuthStateListener mAuthStateListener;
    //___________________________________________
    Main_status_adapter status_adapter;
    ProgressBar progressBar;
    private DatabaseReference root;
    RequestBlood requestBloodopjict;
    List<RequestBlood> requestBlood;


    //____________________________________emergency

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

        NotificationGenie notif = new NotificationGenie();

        mFirebaseDatabase = FirebaseDatabase.getInstance();
        mFirebaseAuth = FirebaseAuth.getInstance();

        mAuthStateListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser user = firebaseAuth.getCurrentUser();

                SharedPreferences data = getPreferences(Context.MODE_PRIVATE);

                //  data.edit().putString("id", "yQsA1Av6oHY8RjlM6Y9cTwTEhxn1").commit();
                if (user == null && data.getString("id", null) == null) {
                    Intent intent = new Intent(MainActivity.this, RegisterActicity.class);
                    MainActivity.this.startActivity(intent);
                    finish();
                }
            }
        };

        //__________________________EmergencyStart
        requestBlood = new ArrayList<>();
        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.rv_emergency);
        SharedPreferences data = getApplicationContext().getSharedPreferences("UserData", 0);

        root = FirebaseDatabase.getInstance().getReference().child("Main").child("cities").child(data.getString("city", "null"))
                .child(data.getString("BloodType", "null")).child("cases");
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        status_adapter = new Main_status_adapter(requestBlood, this);
        progressBar = (ProgressBar) findViewById(R.id.progress);
        progressBar.setVisibility(View.VISIBLE);
        progressBar.getIndeterminateDrawable().setColorFilter(Color.parseColor("#ffb3dc"), PorterDuff.Mode.MULTIPLY);

//         MainToolBar;
        Toolbar MainToolBar = (Toolbar) findViewById(R.id.main_toolbar);
        setTitle("Emergency Cases");
        toolbar.setTitleTextColor(getResources().getColor(R.color.DarkRed));


        root.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                progressBar.setVisibility(View.GONE);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
        root.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                for (DataSnapshot chelldDataSnapshotCases : dataSnapshot.getChildren()) {

                    Log.d("hello","fdgdfgf");
                    String CountOfBlood = (String) chelldDataSnapshotCases.child("BloodBags").getValue();

                    String BloodType = (String) chelldDataSnapshotCases.child("BloodType").getValue();

                    String PatientFileNumber = (String) chelldDataSnapshotCases.child("FileNumber").getValue();

                    String NameOfHospital = (String) chelldDataSnapshotCases.child("Hospital").getValue();

                    String ReasonOfRequest = (String) chelldDataSnapshotCases.child("Reason").getValue();

                    String RequestID = (String) chelldDataSnapshotCases.child("RequestID").getValue();

                    String UserID = (String) chelldDataSnapshotCases.child("UserID").getValue();


                    String CountOfdone = (String) chelldDataSnapshotCases.child("done").getValue();

                    String PatientName = (String) chelldDataSnapshotCases.child("pantienName").getValue();

                    String NameCity = (String) chelldDataSnapshotCases.child("NameCity").getValue();

                    String StatusTime = (String) chelldDataSnapshotCases.child("statusTime").getValue();

                    String latOfHospital = (String) chelldDataSnapshotCases.child("latOfHospital").getValue();

                    String lngOfHospital = (String) chelldDataSnapshotCases.child("lngOfHospital").getValue();

                    requestBloodopjict = new RequestBlood(PatientName,NameCity, (PatientFileNumber), (CountOfBlood), ReasonOfRequest, BloodType, NameOfHospital,
                            StatusTime, RequestID, UserID, (CountOfdone),latOfHospital,lngOfHospital);


                    requestBlood.add(requestBloodopjict);

                    status_adapter.notifyDataSetChanged();


                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
        recyclerView.setAdapter(status_adapter);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        status_adapter.notifyDataSetChanged();

        /*
        Here LOCATION variables
         */
        LocationClass location = new LocationClass(getApplicationContext());
        double lon = location.getLongitude();
        double lat = location.getLatitude();
        String city = location.getCurrectCity();
        //__________________________EmergencyEND


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

    public void go_to_request_blood(View view) {


        Intent startChildActivityIntent = new Intent(this, RequestActivity.class);
        startActivity(startChildActivityIntent);
        finish();
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

        if (id == R.id.nav_Talks) {
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


    @Override
    public void Clicked(int position, int id) {
        SharedPreferences prefs = getSharedPreferences("UserData", MODE_PRIVATE);



        if (requestBlood.get(position).getUserID().equals(prefs.getString("id", "NOTHING HERE"))) {
            Toast.makeText(getApplicationContext(), "هذه الحاله خاصه بك", Toast.LENGTH_SHORT).show();
        } else {
            Intent intent = new Intent(this, ChatActivity.class);
            intent.putExtra("requestID", requestBlood.get(position).getRequestID());
            intent.putExtra("userID", requestBlood.get(position).getUserID());
            startActivity(intent);
            finish();

        }


    }

    @Override
    public void ClickedNVG(int position, int id) {

        String latitude =requestBlood.get(position).getLatOfHospital();

        String longitude =requestBlood.get(position).getLngOfHospital();
        String uri = "https://www.google.com/maps/preview/@"+latitude+","+longitude+","+15+"z";
        Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(uri));
        startActivity(intent);

    }

    public class NotificationGenie extends FirebaseMessagingService {
        @Override
        public void onMessageReceived(RemoteMessage remoteMessage) {
            // TODO(developer): Handle FCM messages here.
            // If the application is in the foreground handle both data and notification messages here.
            // Also if you intend on generating your own notifications as a result of a received FCM
            // message, here is where that should be initiated. See sendNotification method below.
            sendNotification(remoteMessage.getNotification().getBody());

        }

        private void sendNotification(String messageBody) {
            Intent intent = new Intent(this, MainActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            PendingIntent pendingIntent = PendingIntent.getActivity(this, 0 /* Request code */, intent,
                    PendingIntent.FLAG_ONE_SHOT);

            Uri defaultSoundUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
            NotificationCompat.Builder notificationBuilder = new NotificationCompat.Builder(this)
                    .setSmallIcon(R.drawable.big_heart)
                    .setContentTitle("FCM Message")
                    .setContentText(messageBody)
                    .setAutoCancel(true)
                    .setSound(defaultSoundUri)
                    .setContentIntent(pendingIntent);

            NotificationManager notificationManager =
                    (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);

            notificationManager.notify(0 /* ID of notification */, notificationBuilder.build());
        }
    }
}
