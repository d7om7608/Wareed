package com.example.d7om7.wareed;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;

import java.util.List;

import static android.R.attr.id;

/**
 * Created by d7om7 on 8/11/2017.
 */

public class AdapterMyChating extends RecyclerView.Adapter<AdapterMyChating.ViewHolder> {
private List<InformationOfChating> informationOfChatings;
private changeActivity mCategoryHandler;

public AdapterMyChating(List<InformationOfChating> informationOfChatings,changeActivity handler) {
        this.informationOfChatings = informationOfChatings;
        mCategoryHandler =handler;

        }
public interface changeActivity  {
    public void Clicked (int position,int id);
}

    @Override
    public AdapterMyChating.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemLayout = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_chat_view_holder, parent, false);
        ViewHolder viewHolder = new ViewHolder(itemLayout);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {

        holder.namePantent.setText(informationOfChatings.get(position).getNameRequster());
        holder.senderName.setText(informationOfChatings.get(position).getNameDoner());
        holder.requstID.setText(informationOfChatings.get(position).getFileNumber());

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
         namePantent=(TextView) itemLayout.findViewById(R.id.namePantent);
         senderName=(TextView) itemLayout.findViewById(R.id.senderName);
         requstID=(TextView) itemLayout.findViewById(R.id.requstID);

    }
}

}
