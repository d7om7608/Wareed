package com.example.d7om7.wareed;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import static android.R.attr.id;

/**
 * Created by Azura on 8/1/2017.
 */

    public class ChatAdaptor extends RecyclerView.Adapter<ChatAdaptor.ViewHolder> {

    ViewGroup viewGroup;
    private List<ChatList> itemDatas;
    private Context change;


    public ChatAdaptor(Context context, List<ChatList> list) {
        this.itemDatas = list;
        change = context;
    }


    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        viewGroup = parent;
        View itemLayout = LayoutInflater.from(parent.getContext()).inflate(R.layout.chat_holder, null);
        ViewHolder viewHolder = new ViewHolder(itemLayout);

        return viewHolder;
    }


    static int myposition = 0;

    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {

        myposition = position;

        holder.itemView.setTag(itemDatas.get(position).id);

        holder.MsgTextView.setText(RegisterActicity.chatUserName);
        holder.MsgTextView.setText(itemDatas.get(position).items.size() + "");


    }

    @Override
    public int getItemCount() {
        return itemDatas.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public TextView MsgTextView;
        public TextView NameTextView;


        public ViewHolder(View itemLayout) {
            super(itemLayout);
            MsgTextView = (TextView) itemLayout.findViewById(R.id.msg_text_view);
            NameTextView = (TextView) itemLayout.findViewById(R.id.name_text_view);

        }
    }
}