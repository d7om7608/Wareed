package com.example.d7om7.wareed;


import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import static android.R.attr.id;

public class AdapterMyCases extends RecyclerView.Adapter<AdapterMyCases.ViewHolder> {
    private List<RequestBlood> requestedBloodList;
    private changeActivity mCategoryHandler;

    public AdapterMyCases(List<RequestBlood> requestBloods,changeActivity handler) {
        this.requestedBloodList = requestBloods;
        mCategoryHandler =handler;

    }
    public interface changeActivity  {
        public void Clicked (int position,int id);
    }

    @Override
    public AdapterMyCases.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemLayout = LayoutInflater.from(parent.getContext()).inflate(R.layout.my_cases_view_holder, parent, false);
        ViewHolder viewHolder = new ViewHolder(itemLayout);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {
        holder.reasonOfRequest.setText("" + requestedBloodList.get(position).reasonOfRequest);
        holder.statusTime.setText("" + requestedBloodList.get(position).statusTime);
        holder.countOfrequest.setText("" + requestedBloodList.get(position).countOfBlood);
        holder.countDone.setText("" + requestedBloodList.get(position).countOfdone);
        holder.bloodTyep.setText("" + requestedBloodList.get(position).bloodType);
        Intent intent=new Intent();


        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mCategoryHandler.Clicked(position, id);
            }
        });

    }

    @Override
    public int getItemCount() {
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
            countOfrequest = (TextView) itemLayout.findViewById(R.id.countOfblood);
            countDone = (TextView) itemLayout.findViewById(R.id.countDone);
            bloodTyep = (TextView) itemLayout.findViewById(R.id.bloodTyep);


        }
    }

}
