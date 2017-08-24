package com.wadimakkah.d7om7.wareed;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class ListMyChating extends AppCompatActivity implements AdapterMyChating.changeActivity {
    RecyclerView recyclerView;
    AdapterMyChating adapterMyChating;
    List<InformationOfChating> informationOfChatings;
    DatabaseReference root;
    String UserId;
    SharedPreferences prefs;
    private TextView nullText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_my_chating);
        nullText=(TextView)findViewById(R.id.nullChat);
        recyclerView = (RecyclerView) findViewById(R.id.my_chat_RecyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        informationOfChatings = new ArrayList<>();
        prefs = getSharedPreferences("UserData", MODE_PRIVATE);
        adapterMyChating = new AdapterMyChating(informationOfChatings, this, getApplicationContext());
        recyclerView.setAdapter(adapterMyChating);
        final ProgressDialog progressDialog = new ProgressDialog(this);
        progressDialog.setTitle("Please wait");
        progressDialog.setMessage("Loading...");
        progressDialog.setCancelable(false);
        progressDialog.setCanceledOnTouchOutside(false);
        progressDialog.show();
        adapterMyChating.notifyDataSetChanged();
        if (prefs.getString("id", null) != null) {
            UserId = (prefs.getString("id", "NOTHING HERE"));

        }


        root = FirebaseDatabase.getInstance().getReference().child("MainChat");

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
                informationOfChatings.clear();

                for (DataSnapshot chelldDataSnapshotIdRequester : dataSnapshot.getChildren()) {

                    String idRequester = chelldDataSnapshotIdRequester.getKey();

                    if (idRequester.equals(prefs.getString("id", "NOTHING HERE"))) {
                        for (DataSnapshot chelldDataSnapshotIdDoner : chelldDataSnapshotIdRequester.getChildren()) {
                            String IdDoner = chelldDataSnapshotIdDoner.getKey();

                            for (DataSnapshot chelldDataSnapshotIdRequest : chelldDataSnapshotIdDoner.getChildren()) {
                                String IdRequest = chelldDataSnapshotIdRequest.getKey();
                                InformationOfChating informationOfChatings1 = new InformationOfChating(idRequester, IdDoner, IdRequest);
                                adapterMyChating.notifyDataSetChanged();

                                informationOfChatings.add(informationOfChatings1);


                            }

                        }
                    }
                }

                if (informationOfChatings.isEmpty())
                    nullText.setVisibility(View.VISIBLE);
                else
                    nullText.setVisibility(View.INVISIBLE);
                for (DataSnapshot chelldDataSnapshotIdRequester : dataSnapshot.getChildren()) {

                        String idRequester = chelldDataSnapshotIdRequester.getKey();

                        for (DataSnapshot chelldDataSnapshotIdDoner : chelldDataSnapshotIdRequester.getChildren()) {
                            String IdDoner = chelldDataSnapshotIdDoner.getKey();
                            if (IdDoner.equals(prefs.getString("id", "NOTHING HERE"))) {

                                for (DataSnapshot chelldDataSnapshotIdRequest : chelldDataSnapshotIdDoner.getChildren()) {
                                    String IdRequest = chelldDataSnapshotIdRequest.getKey();
                                    InformationOfChating informationOfChatings1 = new InformationOfChating(idRequester, IdDoner, IdRequest);
                                    adapterMyChating.notifyDataSetChanged();
                                    informationOfChatings.add(informationOfChatings1);

                                }


                            }
                        }
                    }
                if (informationOfChatings.isEmpty())
                    nullText.setVisibility(View.VISIBLE);
                else
                    nullText.setVisibility(View.INVISIBLE);

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

    }


    @Override
    public void Clicked(int position, int id) {

        Intent intent = new Intent(this, ChatActivity.class);
        intent.putExtra("NameRequster", informationOfChatings.get(position).getNameRequster());
        intent.putExtra("FileNumber", informationOfChatings.get(position).getRequestID());
        intent.putExtra("NameDoner", informationOfChatings.get(position).getNameDoner());

        startActivity(intent);
        finish();

    }

    String nameRequster;
    String nameDoner;
    String DonerID;
    String RequestID;


    @Override
    protected void onResume() {
        super.onResume();
        adapterMyChating.notifyDataSetChanged();
    }

    @Override
    protected void onPause() {
        super.onPause();
        adapterMyChating.notifyDataSetChanged();
    }

    public void onBackPressed() {

        Intent ProfileIntent = new Intent(this, MainActivity.class);
        startActivity(ProfileIntent);
        finish();
    }
}
