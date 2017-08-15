package com.example.d7om7.wareed;

import android.content.Context;
import android.content.SharedPreferences;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.List;

import static android.R.attr.id;



public class AdapterMyChating extends RecyclerView.Adapter<AdapterMyChating.ViewHolder> {
    private List<InformationOfChating> informationOfChatings;
    private changeActivity mCategoryHandler;
    private DatabaseReference root;
    String namePantent="";
    Context context;
    public AdapterMyChating(List<InformationOfChating> informationOfChatings, changeActivity handler, Context context) {
        this.informationOfChatings = informationOfChatings;
        mCategoryHandler = handler;
        this.context= context;
    }

    public interface changeActivity {
        public void Clicked(int position, int id);
    }

    @Override
    public AdapterMyChating.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemLayout = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_chat_view_holder, parent, false);
        ViewHolder viewHolder = new ViewHolder(itemLayout);

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {
        final SharedPreferences data = context.getSharedPreferences("UserData", 0);

        root = FirebaseDatabase.getInstance().getReference().child("Main").child("cities").child(data.getString("city", "null"))
                .child(data.getString("BloodType", "null")).child("users");
        root.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {


                holder.namePantent.setText((String)dataSnapshot.child(informationOfChatings.get(position).getNameRequster()).child("user name").getValue());
                holder.senderName.setText((String)dataSnapshot.child(informationOfChatings.get(position).getNameDoner()).child("user name").getValue());
                holder.requstID.setText(informationOfChatings.get(position).getRequestID());
                        }


            @Override
            public void onCancelled(DatabaseError databaseError) {

            }


        });


        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mCategoryHandler.Clicked(position, id);
            }
        });
    }

    @Override
    public int getItemCount() {
        return informationOfChatings.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView namePantent;
        TextView senderName;
        TextView requstID;


        public ViewHolder(View itemLayout) {
            super(itemLayout);
            namePantent = (TextView) itemLayout.findViewById(R.id.namePantent);
            senderName = (TextView) itemLayout.findViewById(R.id.senderName);
            requstID = (TextView) itemLayout.findViewById(R.id.requstID);

        }
    }

}
