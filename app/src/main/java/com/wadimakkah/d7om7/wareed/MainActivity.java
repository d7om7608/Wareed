package com.wadimakkah.d7om7.wareed;


import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;

import android.content.SharedPreferences;
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
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import android.widget.DatePicker;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.google.firebase.FirebaseApp;
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
import java.util.Calendar;
import java.util.List;


public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener, Main_status_adapter.changeActivity {


    public static final int RC_SIGN_IN = 1;
    private FirebaseDatabase mFirebaseDatabase;
    private DatabaseReference SearchForProfile;
    private ChildEventListener mChildEventListener;
    private FirebaseAuth mFirebaseAuth;
    private FirebaseAuth.AuthStateListener mAuthStateListener;
    DatabaseReference lastTimeDonate;
    TextView canDonateText;
    //___________________________________________
    Main_status_adapter status_adapter;
    private DatabaseReference root;
    RequestBlood requestBloodopjict;
    List<RequestBlood> requestBlood;
    RecyclerView recyclerView;
    private TextView nullText;
    //____________________________________emergency
    SharedPreferences data;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


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
        FirebaseApp.initializeApp(getApplicationContext());
        FirebaseApp.initializeApp(getApplicationContext());

        nullText=(TextView)findViewById(R.id.nullMain);
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
        recyclerView = (RecyclerView) findViewById(R.id.rv_emergency);
         data = getApplicationContext().getSharedPreferences("UserData", 0);

        root = FirebaseDatabase.getInstance().getReference().child("Main").child("cities").child(data.getString("city", "null"))
                .child(data.getString("BloodType", "null")).child("cases");
        lastTimeDonate=FirebaseDatabase.getInstance().getReference().child("Main").child("cities").child(data.getString("city", "null"))
                .child(data.getString("BloodType", "null")).child("users").child(data.getString("id", "null"));
        canDonateText=(TextView)findViewById(R.id.canDonateText);


        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        status_adapter = new Main_status_adapter(requestBlood, this);
        final ProgressDialog progressDialog = new ProgressDialog(this);
        progressDialog.setTitle("Please wait");
        progressDialog.setMessage("Loading...");
        progressDialog.setCancelable(false);
        progressDialog.setCanceledOnTouchOutside(false);
        progressDialog.show();

//         MainToolBar;

        setTitle("Emergency Cases");
        toolbar.setTitleTextColor(getResources().getColor(R.color.DarkRed));
        toolbar.setNavigationIcon(R.drawable.maskgroup1);


        lastTimeDonate.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Long TimeCanDonate= Long.valueOf(0);
                if (dataSnapshot.child("TimeCanDonate").getValue()!=null){
                      TimeCanDonate=0+Long.valueOf(dataSnapshot.child("TimeCanDonate").getValue().toString());
                }

                Calendar calendar = Calendar.getInstance();
                Long startTime =calendar.getTimeInMillis();

               if (TimeCanDonate>startTime) {
                   canDonateText.setVisibility(View.VISIBLE);

               }else{
                   canDonateText.setVisibility(View.GONE);

               }

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });



        root.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                progressDialog.dismiss();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
        root.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                requestBlood.clear();
                status_adapter.notifyDataSetChanged();
                for (DataSnapshot chelldDataSnapshotCases : dataSnapshot.getChildren()) {

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
                    String location = (String) chelldDataSnapshotCases.child("location").getValue();
                    requestBloodopjict = new RequestBlood(PatientName, NameCity, (PatientFileNumber), (CountOfBlood), ReasonOfRequest, BloodType, NameOfHospital,
                            StatusTime, RequestID, UserID, (CountOfdone), latOfHospital, lngOfHospital,location);



                    requestBlood.add(requestBloodopjict);


                }
                status_adapter.notifyDataSetChanged();


                if (requestBlood.isEmpty())
                    nullText.setVisibility(View.VISIBLE);
                else
                    nullText.setVisibility(View.INVISIBLE);

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
        recyclerView.setAdapter(status_adapter);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        status_adapter.notifyDataSetChanged();

        //__________________________EmergencyEND

   status_adapter.notifyDataSetChanged();
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
                Toast.makeText(this, "Press Again", Toast.LENGTH_SHORT).show();
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
            ProfileIntent.putExtra("checkActivity",2);

            startActivity(ProfileIntent);
            finish();

        } else if (id == R.id.nav_signout) {

            SharedPreferences data = getSharedPreferences("UserData", 0);
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
            Toast.makeText(getApplicationContext(), "This is your case", Toast.LENGTH_SHORT).show();
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

        String latitude = requestBlood.get(position).getLatOfHospital();
        String location=  requestBlood.get(position).getLocation();
        String longitude = requestBlood.get(position).getLngOfHospital();

        if (location.equals("null"))
            Toast.makeText(getApplicationContext(), "don't have map of hospital", Toast.LENGTH_SHORT).show();
        else {
            Uri uri = Uri.parse(location);
            startActivity(new Intent(Intent.ACTION_VIEW, uri));
        }

    }
     DatePicker datePicker;
    TimePicker timePicker;

    @Override
    public void ClickedDonerDonate(int position, int id) {

        lastTimeDonate.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Long  TimeCanDonate=Long.valueOf(dataSnapshot.child("TimeCanDonate").getValue().toString());
                Calendar calendar = Calendar.getInstance();
                Long startTime =calendar.getTimeInMillis();

                if (TimeCanDonate<=startTime) {
                    lastTimeDonate.child("DateSecondDonate").setValue(startTime+"");
                    Long i=startTime+(24*29*3*60*60);
                    lastTimeDonate.child("TimeCanDonate").setValue(i+"");
                    canDonateText.setVisibility(View.VISIBLE);
                    Toast.makeText(getApplicationContext(), "شكرا لك", Toast.LENGTH_SHORT).show();


                }else{
                    Toast.makeText(getApplicationContext(), "       شكرا لك \nلا ينصح بالتبرع الان ", Toast.LENGTH_SHORT).show();
                }

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });



    }

    @Override
    public void ClickedShare(int position, int id) {
        Intent sendIntent = new Intent();
        sendIntent.setAction(Intent.ACTION_SEND);
        sendIntent.putExtra(Intent.EXTRA_TEXT, "I'm "+requestBlood.get(position).getPatientName()+" need your blood donate \n My blood type : "+
                requestBlood.get(position).getBloodType()+" \nand my file number is: "+requestBlood.get(position).getPatientFileNumber()+"\n" +
                "in "+requestBlood.get(position).getNameOfHospital()+" Hospital at "+requestBlood.get(position).getCityName()+"\n" +
                "From: Hiah App");
        sendIntent.setType("text/plain");
        startActivity(sendIntent);
    }

    @Override
    public void ClickedcoundBlood(int position, int id) {
        Toast.makeText(getApplicationContext(),"Request : "+requestBlood.get(position).getCountOfBlood(), Toast.LENGTH_SHORT).show();

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.tool, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.updateGPS) {
            // Here update GPS
            updateLocation();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    protected void updateLocation(){
        final LocationClass location = new LocationClass(this);
        final DatabaseReference root = FirebaseDatabase.getInstance().getReference();
        if(location.getCurrectCity() != null){
            root.child("cities").addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    boolean foundLocation = false;
                    for(DataSnapshot city: dataSnapshot.getChildren()){
                        String cityInDatabase = city.child("name").getValue().toString().toLowerCase().trim();
                        String cityInLocation = location.getCurrectCity().toString().toLowerCase().trim();
                        if(cityInDatabase.equals(cityInLocation)){
                            String cityID = city.getKey().toString();
                            updateUserProfile(cityID);
                            Toast.makeText(getApplicationContext(),"Location Updated",Toast.LENGTH_LONG).show();
                            foundLocation = true;
                            break;
                        }
                    }

                    if(foundLocation == false){
                        root.child("unnamedCity").push().setValue(location.getCurrectCity());
                        Toast.makeText(getApplicationContext(),"your city is not in database",Toast.LENGTH_SHORT).show();
                    }
                }

                @Override
                public void onCancelled(DatabaseError databaseError) {

                }
            });
        }else{
            Toast.makeText(getApplicationContext(),"Cannot find your location name.",Toast.LENGTH_LONG).show();
        }
    }

    protected void updateUserProfile(String newCityID){
        SharedPreferences data = getSharedPreferences("UserData",0);
        FirebaseDatabase.getInstance().getReference().child("Main").child("cities").child(data.getString("city",null)).child(data.getString("BloodType",null))
                .child("users").child(data.getString("id",null)).removeValue();

        DatabaseReference SignDataBase = FirebaseDatabase.getInstance().getReference().child("Main").child("cities").child(newCityID).child(data.getString("BloodType",null))
                .child("users");




        Calendar calendar_1 = Calendar.getInstance();

        DatabaseReference current_user_db = SignDataBase.child(data.getString("id",null));
        current_user_db.child("user name").setValue(data.getString("display_name",null));
        current_user_db.child("BloodType").setValue(data.getString("BloodType",null));
        current_user_db.child("gender").setValue(data.getString("gender",null));
        current_user_db.child("city").setValue(newCityID);
        current_user_db.child("email").setValue(data.getString("email",null));
        current_user_db.child("age").setValue(data.getString("age",null));
        current_user_db.child("donateCount").setValue("0");
        current_user_db.child("DateSecondDonate").setValue("0");
        current_user_db.child("TimeCanDonate").setValue("0");

        if(data.getString("LastNotification",null) != null){
            current_user_db.child("LastNotification").setValue(data.getString("LastNotification",null));
        }else{
            current_user_db.child("LastNotification").setValue("0");
        }

        data.edit().putString("city",newCityID).apply();
    }

}
