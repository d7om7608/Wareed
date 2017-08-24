package com.wadimakkah.d7om7.wareed;

import android.content.Context;
import android.content.SharedPreferences;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import com.github.library.bubbleview.BubbleTextView;

import java.util.List;

import static android.content.Context.MODE_PRIVATE;

/**
 * Created by d7om7 on 8/20/2017.
 */

public class AdabterSMG extends BaseAdapter {

    private List<ChatModel> list_chat_models;
    private Context context;
    private LayoutInflater layoutInflater;
    SharedPreferences prefs;

    public AdabterSMG(List<ChatModel> list_chat_models, Context context) {
        this.list_chat_models = list_chat_models;
        this.context = context;
        layoutInflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        return list_chat_models.size();
    }

    @Override
    public Object getItem(int position) {
        return list_chat_models.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view = convertView;
        if(view == null)
        {


            prefs = context.getSharedPreferences("UserData", MODE_PRIVATE);

            if(list_chat_models.get(position).isSend.equals(prefs.getString("id", null)))
                view = layoutInflater.inflate(R.layout.list_item_message_send,null);
            else
                view = layoutInflater.inflate(R.layout.list_item_message_recv,null);

            BubbleTextView text_message = (BubbleTextView)view.findViewById(R.id.msg_text_viewSend);
            text_message.setText(list_chat_models.get(position).getMessage());

        }
        return view;
    }

    @Override
    public int getViewTypeCount() {
        if(list_chat_models.size()==0){
            return 1;
        }
        return list_chat_models.size();
    }

    @Override
    public int getItemViewType(int position) {
        return position;
    }
}
