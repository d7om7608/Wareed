package com.wadimakkah.d7om7.wareed;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.iid.FirebaseInstanceId;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;


/**
 * Created by Azura on 8/6/2017.
 */

public class ProfileActivity extends AppCompatActivity {

    CityBloodPreferences cityBloodActivity = new CityBloodPreferences();
    private FirebaseDatabase SignFirebaseDatabase;
    private FirebaseAuth SignAuth;
    private DatabaseReference SignDataBase;
    private DatabaseReference SignInCity;
    private DatabaseReference AllUsers;
    private Dialog D_DatePicker;
    private EditText UserNameEditText;
    private EditText EmailEditText;
    private Spinner BloodTypeSpinner;
    private Spinner CitySpinner;
    private Spinner GenderSpinner;
    private EditText ageText;
    String UsernameTooked;
    String Email;
    String BloodTypeTooked;
    String CityTooked;
    String GenderTooked;
    String UserUID;
    String age;
    Intent getIntent;
    Long DateSecond;
    private Button dateButton;
    private Button BTN;
    private Calendar calendar;
    private TextView TextDate;
    ProgressDialog progressDialog;
    private Task<Void> root;

    private List<String> CityArray=new ArrayList<>();
    ArrayAdapter<String> cityadapter;
    String  IdCity ;
    String  nameCity;
    SharedPreferences sharedPref;
    SharedPreferences prefs;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        dateButton=(Button)findViewById(R.id.dateButton) ;
        sharedPref = getApplicationContext().getSharedPreferences("UserData",0);
          progressDialog= new ProgressDialog(this);
//        Ui components
         getIntent=getIntent();

        UserNameEditText = (EditText) findViewById(R.id.username_edittext);
        EmailEditText = (EditText) findViewById(R.id.email_edittext);
        BloodTypeSpinner = (Spinner) findViewById(R.id.profile_bloodType_spinner);
        CitySpinner = (Spinner) findViewById(R.id.profile_city_spinner);
        GenderSpinner = (Spinner) findViewById(R.id.profile_gender_spinner);
        ageText = (EditText) findViewById(R.id.age);
        TextDate = (TextView) findViewById(R.id.TextDate);
        progressDialog.setTitle("Please wait");
        progressDialog.setMessage("Loading...");
        progressDialog.setCancelable(false);
        progressDialog.setCanceledOnTouchOutside(false);
        progressDialog.show();
        BTN = (Button) findViewById(R.id.dateButton);
        calendar = Calendar.getInstance();
        dateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DatePicker();
            }
        });
        //____________________________________DateFinish

        BloodSpinner();
        CitySpinner();
        GenderSpinner();


//        FireBase
        SignFirebaseDatabase = FirebaseDatabase.getInstance();
        SignAuth = FirebaseAuth.getInstance();

        SharedPreferences prefs = getSharedPreferences("UserData", MODE_PRIVATE);

        if (prefs.getString("id", null) != null) {
            UserNameEditText.setText(prefs.getString("display_name", "NOTHING HERE"));
            EmailEditText.setText(prefs.getString("email", "NOTHING HERE"));
            ageText.setText(prefs.getString("age", "NOTHING HERE"));

            setSelectedSpinner(prefs,"BloodType",BloodTypeSpinner);


            setSelectedSpinner(prefs,"gender",GenderSpinner);

        }


    }

    protected void setSelectedSpinner(SharedPreferences prefs,String prefName,Spinner spinner){
        String valueTooked = prefs.getString(prefName,"NOTHING HERE");
        if(prefName == "city"){
            spinner.setSelection(Integer.parseInt(valueTooked));
        }else{
            ArrayAdapter adapter = (ArrayAdapter) spinner.getAdapter(); //cast to an ArrayAdapter
            int postion = adapter.getPosition(valueTooked);
            spinner.setSelection(postion);
        }

    }


    public void CitySpinner() {
        // TODO: ger city from CityBlood Activity

        DatabaseReference root = FirebaseDatabase.getInstance().getReference();
        root.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                progressDialog.dismiss();

                for(DataSnapshot chelldDataSnapshot:dataSnapshot.child("cities").getChildren() ){
                    IdCity =  chelldDataSnapshot.getKey();
                    nameCity = (String) chelldDataSnapshot.child("name").getValue().toString();
                    CityArray.add(nameCity);
                    cityadapter.notifyDataSetChanged();
                }
                 prefs = getSharedPreferences("UserData", MODE_PRIVATE);

                if (prefs.getString("id", null) != null) {
                    setSelectedSpinner(getSharedPreferences("UserData", 0), "city", CitySpinner);
                }

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
         cityadapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, CityArray);
        CitySpinner.setAdapter(cityadapter);


    }

    public void BloodSpinner() {
        String[] BloodArray = cityBloodActivity.getBloodTypes();
        ArrayAdapter<String> bloodadapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_item, BloodArray);
        BloodTypeSpinner.setAdapter(bloodadapter);

    }

    public void GenderSpinner() {
        String GenderArray[] = {"male", "female"};
        ArrayAdapter<String> genderadapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_item, GenderArray);
        GenderSpinner.setAdapter(genderadapter);

    }

    public void SaveSettingsOnClick(View view) {



//        if (prefs.getString("id", null) != null) {
//
//
//            root = FirebaseDatabase.getInstance().getReference().child("Main").child("cities").child(prefs.getString("city", "null"))
//                    .child(prefs.getString("BloodType", "null")).child("users").child(prefs.getString("id", "NOTHING HERE"))
//                    .removeValue();
//
//
//        }
        UserUID = SignAuth.getCurrentUser().getUid();
        UsernameTooked = UserNameEditText.getText().toString().trim();
        BloodTypeTooked = BloodTypeSpinner.getSelectedItem().toString().trim();
        CityTooked =""+ CitySpinner.getSelectedItemPosition();
        GenderTooked = GenderSpinner.getSelectedItem().toString().trim();
        Email = EmailEditText.getText().toString().trim();
        age=ageText.getText().toString().trim();
        //DateSecond=TextDate.getText().toString().trim();
        SignInCity = FirebaseDatabase.getInstance().getReference().child("cities").child(CityTooked)
        .child("bloodtype").child(BloodTypeTooked).child("users");
        SignDataBase = FirebaseDatabase.getInstance().getReference().child("Main").child("cities").child(CityTooked).child(BloodTypeTooked)
                .child("users");

        AllUsers = FirebaseDatabase.getInstance().getReference().child("Allusers");
        if (UsernameTooked.isEmpty() || BloodTypeTooked.isEmpty()) {

            Toast.makeText(ProfileActivity.this, "Missing Data", Toast.LENGTH_SHORT).show();
        }else {
            if(sharedPref.getString("city",null) != null && sharedPref.getString("city",null)!= CityTooked){
                removeCity();
            }
            DatabaseReference current_user_db = SignDataBase.child(UserUID);
            current_user_db.child("user name").setValue(UsernameTooked);
            current_user_db.child("BloodType").setValue(BloodTypeTooked);
            current_user_db.child("gender").setValue(GenderTooked);
            current_user_db.child("city").setValue(CityTooked);
            current_user_db.child("email").setValue(Email);
            current_user_db.child("age").setValue(age);
            current_user_db.child("DateSecondDonate").setValue(DateSecond);
            current_user_db.child("donateCount").setValue("0");
            current_user_db.child("TimeCanDonate").setValue("0");

            if(sharedPref.getString("LastNotification",null) != null){
                current_user_db.child("LastNotification").setValue(sharedPref.getString("LastNotification",null));
            }else{
                current_user_db.child("LastNotification").setValue("0");
            }


            DatabaseReference Allusers = AllUsers.child(UserUID);
            Allusers.child("user name").setValue(UsernameTooked);
            Allusers.child("BloodType").setValue(BloodTypeTooked);
            Allusers.child("gender").setValue(GenderTooked);
            Allusers.child("city").setValue(CityTooked);
            Allusers.child("email").setValue(Email);
            Allusers.child("age").setValue(age);
            Allusers.child("DateSecondDonate").setValue(DateSecond);
            current_user_db.child("TimeCanDonate").setValue("0");

            if(sharedPref.getString("LastNotification",null) != null){
                Allusers.child("LastNotification").setValue(sharedPref.getString("LastNotification",null));
            }else{
                Allusers.child("LastNotification").setValue("0");
            }

            Allusers.child("donateCount").setValue("0");


            /**
             * Firebase generate token.
             */
            String refreshedToken = FirebaseInstanceId.getInstance().getToken();

            current_user_db.child("NotificationToken").setValue(refreshedToken);



            CityBloodPreferences c = new CityBloodPreferences();
            c.saveInPrefernces(current_user_db, SignAuth, getApplicationContext());
            startService(new Intent(this, Notifications.class));
            startService(new Intent(this, NotificationInstance.class));

            Intent intent = new Intent(ProfileActivity.this, MainActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(intent);
            finish();
            Toast.makeText(getApplicationContext(), "Profile Saved", Toast.LENGTH_SHORT).show();


        }


    }

    public void DatePicker() {


        D_DatePicker = new Dialog(this);
        D_DatePicker.setContentView(R.layout.dilalog_date_picker);
        final DatePicker datepicker = (DatePicker) D_DatePicker.findViewById(R.id.date_picker);
        Button BTN_GetDate = (Button) D_DatePicker.findViewById(R.id.BTN_GetDate);
        Button BTN_Close = (Button) D_DatePicker.findViewById(R.id.BTN_Close);

        Calendar calendar_1 = Calendar.getInstance();
        calendar_1.add(Calendar.MONTH, 24);
        datepicker.setMaxDate(calendar_1.getTimeInMillis());

        BTN_GetDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final Calendar calendar2 = Calendar.getInstance();
                calendar2.set(datepicker.getYear(), datepicker.getMonth(), datepicker.getDayOfMonth());
                Date time = calendar2.getTime();
                long UnixTime = time.getTime();
                DateSecond = UnixTime;
                D_DatePicker.dismiss();
                Toast.makeText(getApplicationContext(),"Data Saved",Toast.LENGTH_SHORT).show();
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


    public void onBackPressed() {

        if (getIntent.getIntExtra("checkActivity",0)==2){
        Intent ProfileIntent = new Intent(this, MainActivity.class);
        startActivity(ProfileIntent);
        finish();}else if (getIntent.getIntExtra("checkActivity",0)==1){
            Intent ProfileIntent = new Intent(this, RegisterActicity.class);
            startActivity(ProfileIntent);
            finish();}

    }

    protected void removeCity(){
        SharedPreferences data = getSharedPreferences("UserData",0);
        FirebaseDatabase.getInstance().getReference().child("Main").child("cities").child(data.getString("city",null)).child(data.getString("BloodType",null))
                .child("users").child(data.getString("id",null)).removeValue();


    }
}
