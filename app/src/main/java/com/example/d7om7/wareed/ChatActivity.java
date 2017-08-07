package com.example.d7om7.wareed;

import android.app.Notification;
import android.app.NotificationManager;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.app.AppCompatDelegate;
import android.support.v7.app.NotificationCompat;
import android.text.InputFilter;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import static com.example.d7om7.wareed.menagerModel.donor;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import static android.os.Build.VERSION_CODES.M;

/**
 * Created by Azura on 8/2/2017.
 */

public class ChatActivity extends AppCompatActivity {

    private ArrayList<String> arrayList = new ArrayList<>();;
    private ArrayAdapter<String> arrayAdapter;
    private EditText ChatEditText;
    private Button SendBtn;
    private String temp_key;
    ProgressBar mProgressBar;
    ListView ChatListView;
    private FirebaseDatabase mFirebaseDatabase;
    public static final int DEFAULT_MSG_LENGTH_LIMIT = 1000;
    String name="";
    private DatabaseReference root;
    private ChildEventListener mChildEventListener;
    private FirebaseAuth mFirebaseAuth;
    private FirebaseAuth.AuthStateListener mAuthStateListener;

    @Override
    protected void onCreate(@AppCompatDelegate.NightMode Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);

        root = FirebaseDatabase.getInstance().getReference().child("MainChat");
        ChatListView = (ListView) findViewById(R.id.chat_list_view);
        ChatEditText = (EditText) findViewById(R.id.chat_msg_edit_text);
        SendBtn = (Button) findViewById(R.id.chat_send_button);
        arrayAdapter = new ArrayAdapter<String>(this,
                R.layout.chat_holder, R.id.msg_text_view, arrayList);
        ChatListView.setAdapter(arrayAdapter);


        mProgressBar = (ProgressBar) findViewById(R.id.progressBar);
        mProgressBar.setVisibility(ProgressBar.INVISIBLE);
        SendBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Map<String, Object> map = new HashMap<String, Object>();
                temp_key = root.push().getKey();
                root.updateChildren(map);
                Log.d("hello",getIntent().getStringExtra("NameUser")+"");
                name=donor.name;

                DatabaseReference message_root = root.child(temp_key);
                Map<String, Object> map2 = new HashMap<String, Object>();
                map2.put("name", name);
                map2.put("msg", ChatEditText.getText().toString());

                message_root.updateChildren(map2);
            }
        });


        root.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                Add_Chat(dataSnapshot);



                NotificationManager mm=(NotificationManager)getSystemService(Context.NOTIFICATION_SERVICE);
                Bitmap bmp= BitmapFactory.decodeResource(getResources(),R.drawable.img);
                NotificationCompat.Builder bulder= (NotificationCompat.Builder) new  NotificationCompat.Builder(ChatActivity.this).setContentTitle("عنوان الرساله")
                        .setContentText("نص موضوع الرساله").setSmallIcon(R.drawable.img).setLargeIcon(bmp).setAutoCancel(true).setNumber(1);
                bulder.setDefaults(Notification.DEFAULT_SOUND| Notification.DEFAULT_VIBRATE);
                bulder.setVibrate(new long[]{500,1000,500,1000,500});
                bulder.setSound(Uri.parse("android.resource://"+getPackageName()+"/"+R.raw.jrs));

                mm.notify(1,bulder.build());
            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {
                Add_Chat(dataSnapshot);


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


    private String chat_msg, chat_user_name;

    private void Add_Chat(DataSnapshot dataSnapshot) {

        Iterator i = dataSnapshot.getChildren().iterator();
        ChatEditText.setText("");
        while (i.hasNext()) {

            chat_msg = (String) ((DataSnapshot) i.next()).getValue();
            chat_user_name = (String) ((DataSnapshot) i.next()).getValue();

            arrayList.add(chat_user_name+" :  "+ chat_msg);
            arrayAdapter.notifyDataSetChanged();
            ChatListView.setSelection(arrayList.size());
        }


    }
}