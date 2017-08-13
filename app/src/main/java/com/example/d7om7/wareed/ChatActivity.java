package com.example.d7om7.wareed;

import android.app.Notification;
import android.app.NotificationManager;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.app.AppCompatDelegate;
import android.support.v7.app.NotificationCompat;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

/**
 * Created by Azura on 8/2/2017.
 */

public class ChatActivity extends AppCompatActivity {

    private ArrayList<String> arrayList = new ArrayList<>();

    LinearLayout ChatViewHolder ;
    ;
    private ArrayAdapter<String> arrayAdapter;
    private EditText ChatEditText;
    private Button SendBtn;
    private String temp_key;
    ProgressBar mProgressBar;
    ListView ChatListView;
    private FirebaseDatabase mFirebaseDatabase;
    public static final int DEFAULT_MSG_LENGTH_LIMIT = 1000;
    String name = "";
    private DatabaseReference root;
    private ChildEventListener mChildEventListener;
    private FirebaseAuth mFirebaseAuth;
    private FirebaseAuth.AuthStateListener mAuthStateListener;
    String UserId;
    int count =0;
    SharedPreferences prefs ;
    @Override
    protected void onCreate(@AppCompatDelegate.NightMode Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);
count=0;
        //_________________________________________________

         prefs = getSharedPreferences("UserData", MODE_PRIVATE);

        if (prefs.getString("id", null) != null) {
            name = (prefs.getString("display_name", "NOTHING HERE"));
            UserId = (prefs.getString("id", "NOTHING HERE"));

        }

        //_____________________________________________________________________
        Intent intent = getIntent();
        if (intent.getStringExtra("requestID")!=null) {
            String requestID = intent.getStringExtra("requestID");
            String requesterID = intent.getStringExtra("userID");
            root = FirebaseDatabase.getInstance().getReference().child("MainChat").child(requesterID).child(UserId).child(requestID);
            count=1;
        }else
        {
            String requesterID= intent.getStringExtra("NameRequster");
            String requestID= intent.getStringExtra("FileNumber");
            String donerID= intent.getStringExtra("NameDoner");
            root = FirebaseDatabase.getInstance().getReference().child("MainChat").child(requesterID).child(donerID).child(requestID);
            count=2;

        }

        ChatListView = (ListView) findViewById(R.id.chat_list_view);
        ChatEditText = (EditText) findViewById(R.id.chat_msg_edit_text);
        SendBtn = (Button) findViewById(R.id.chat_send_button);

        ChatViewHolder = (LinearLayout) findViewById(R.id.chat_view_holder);
        arrayAdapter = new ArrayAdapter<String>(this, R.layout.chat_holder, R.id.msg_text_view, arrayList);
        ChatListView.setAdapter(arrayAdapter);


        mProgressBar = (ProgressBar) findViewById(R.id.progressBar);
        mProgressBar.setVisibility(View.VISIBLE);
        SendBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!ChatEditText.getText().toString().equals("")){
                Map<String, Object> map = new HashMap<String, Object>();
                temp_key = root.push().getKey();
                root.updateChildren(map);
                DatabaseReference message_root = root.child(temp_key);
                Map<String, Object> map2 = new HashMap<String, Object>();
                map2.put("name", name);
                map2.put("msg", ChatEditText.getText().toString());
                if (name.equals(prefs.getString("display_name", "NOTHING HERE")))
                ChatEditText.setText("");

                message_root.updateChildren(map2);
            }else
                    Toast.makeText(getApplicationContext(),"الرجاء ادخال نص",Toast.LENGTH_SHORT).show();
            }
        });

        root.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                mProgressBar.setVisibility(View.INVISIBLE);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
        root.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                Add_Chat(dataSnapshot);
                arrayAdapter.notifyDataSetChanged();
                ChatListView.setSelection(arrayAdapter.getCount());
                ChatListView.deferNotifyDataSetChanged();

//
            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {
                Add_Chat(dataSnapshot);
                ChatListView.setSelection(arrayAdapter.getCount());
                ChatListView.deferNotifyDataSetChanged();

                arrayAdapter.notifyDataSetChanged();
                ChatEditText.setText("");


            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {

            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

    }


    public void onBackPressed() {

        if (count == 2) {
            Intent ProfileIntent = new Intent(this, ListMyChating.class);
            startActivity(ProfileIntent);
            finish();
        }else if (count == 1){

            Intent ProfileIntent = new Intent(this, EmergencyListActivity.class);
            startActivity(ProfileIntent);
            finish();
        }
    }
    private String chat_msg, chat_user_name;

    private void Add_Chat(DataSnapshot dataSnapshot) {

        Iterator i = dataSnapshot.getChildren().iterator();
        while (i.hasNext()) {
            ChatListView.setSelection(arrayAdapter.getCount());
            ChatListView.deferNotifyDataSetChanged();
            chat_msg = (String) ((DataSnapshot) i.next()).getValue();
            chat_user_name = (String) ((DataSnapshot) i.next()).getValue();

            arrayList.add(chat_user_name + " :  " + chat_msg);
            arrayAdapter.notifyDataSetChanged();
            ChatListView.setSelection(arrayList.size());
        }


    }
}