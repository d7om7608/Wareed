package com.example.d7om7.wareed;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.app.AppCompatDelegate;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;
import java.util.ArrayList;

/**
 * Created by Azura on 8/2/2017.
 */

public class ChatActivity extends AppCompatActivity {

    private ArrayList<String> arrayList;
    private ArrayAdapter<String> arrayAdapter;
    private EditText ChatEditText;
    private Button SendBtn;
    ListView ChatListView;

    @Override
    protected void onCreate(@AppCompatDelegate.NightMode Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);

        ChatEditText = (EditText) findViewById(R.id.chat_msg_edit_text);
        SendBtn = (Button) findViewById(R.id.chat_send_button);
        ChatListView = (ListView) findViewById(R.id.chat_list_view);
        arrayList = new ArrayList<>();
        arrayAdapter = new ArrayAdapter<String>(this, R.layout.chat_holder, R.id.msg_text_view, arrayList);
        ChatListView.setAdapter(arrayAdapter);

        SendBtn.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                String newMsg = ChatEditText.getText().toString().trim();

                if (newMsg.isEmpty()) {
                    Toast.makeText(getApplicationContext(), "لا يوجد نص مدخل", Toast.LENGTH_SHORT).show();
                } else {
                    arrayList.add(newMsg);

                    arrayAdapter.notifyDataSetChanged();

                }
            }
        });
//            ChatListView.setOnItemClickListener();
    }

}