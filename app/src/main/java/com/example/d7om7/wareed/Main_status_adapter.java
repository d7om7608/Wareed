package com.example.d7om7.wareed;

import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;


public class Main_status_adapter extends RecyclerView.Adapter<Main_status_adapter.ViewHolder> {
    private List<RequestBlood> requestedBloodList;

    public Main_status_adapter(List<RequestBlood> requestBloods) {
        this.requestedBloodList = requestBloods;
    }

    @Override
    public Main_status_adapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemLayout = LayoutInflater.from(parent.getContext()).inflate(R.layout.emergency_view_holder, parent, false);
        ViewHolder viewHolder = new ViewHolder(itemLayout);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(Main_status_adapter.ViewHolder holder, int position) {
        holder.reasonOfRequest.setText("" + requestedBloodList.get(position).reasonOfRequest);
        holder.statusTime.setText("" + requestedBloodList.get(position).statusTime);
        holder.countOfrequest.setText("" + requestedBloodList.get(position).countOfBlood);
        holder.countDone.setText("" + requestedBloodList.get(position).countOfdone);
        holder.bloodTyep.setText("" + requestedBloodList.get(position).bloodType);
        Log.d("hello", "" + position + "    " + requestedBloodList.get(position).reasonOfRequest);


    }

    @Override
    public int getItemCount() {
        Log.d("hello", requestedBloodList.size() + "");
        return requestedBloodList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView reasonOfRequest;
        TextView statusTime;
        TextView countOfrequest;
        TextView countDone;
        TextView bloodTyep;


        public ViewHolder(View itemLayout) {
            super(itemLayout);
            reasonOfRequest = (TextView) itemLayout.findViewById(R.id.reasonOfRequest);
            statusTime = (TextView) itemLayout.findViewById(R.id.statusTime);
            countOfrequest = (TextView) itemLayout.findViewById(R.id.countOfrequest);
            countDone = (TextView) itemLayout.findViewById(R.id.countDone);
            bloodTyep = (TextView) itemLayout.findViewById(R.id.bloodTyep);


        }
    }

}
