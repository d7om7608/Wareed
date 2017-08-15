package com.example.d7om7.wareed;

import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.TranslateAnimation;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.List;

import static android.R.attr.id;


public class Main_status_adapter extends RecyclerView.Adapter<Main_status_adapter.ViewHolder> {
    private List<RequestBlood> requestedBloodList;
    private changeActivity mCategoryHandler;


    public Main_status_adapter(List<RequestBlood> requestBloods,changeActivity handler) {
        this.requestedBloodList = requestBloods;
        mCategoryHandler =handler;

    }
    public interface changeActivity  {
        public void Clicked (int position,int id);
    }

    @Override
    public Main_status_adapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemLayout = LayoutInflater.from(parent.getContext()).inflate(R.layout.emergency_view_holder, parent, false);
        ViewHolder viewHolder = new ViewHolder(itemLayout);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {

        holder.reasonOfRequest.setText("" + requestedBloodList.get(position).getReasonOfRequest());
        holder.statusTime.setText("" + requestedBloodList.get(position).getStatusTime());
        holder.countOfrequest.setText("" + requestedBloodList.get(position).getCountOfBlood());
        holder.countDone.setText("" + requestedBloodList.get(position).getCountOfdone());
        holder.bloodTyep.setText(" " + requestedBloodList.get(position).getBloodType());
        Intent intent=new Intent();

        holder.BloodInfoLayout.setVisibility(View.GONE);


        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(holder.BloodInfoLayout.getVisibility()==View.GONE){
//                    holder.BloodInfoLayout.setVisibility(View.VISIBLE);
                    Animation animation = new TranslateAnimation(0,0,-75, 0);
                    animation.setDuration(175);
                    animation.setFillAfter(true);
                    holder.BloodInfoLayout.startAnimation(animation);
                    holder.BloodInfoLayout.setVisibility(View.VISIBLE);
                }else {
                    Animation animation = new TranslateAnimation(0,0,75, 0);
                    animation.setDuration(175);
                    animation.setFillAfter(true);
                    holder.BloodInfoLayout.startAnimation(animation);
                    holder.BloodInfoLayout.setVisibility(View.GONE);

                }
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
        RelativeLayout BloodInfoLayout ;
        public boolean isExpanded = false ;


        public ViewHolder(View itemLayout) {
            super(itemLayout);
            reasonOfRequest = (TextView) itemLayout.findViewById(R.id.reasonOfRequest);
            statusTime = (TextView) itemLayout.findViewById(R.id.statusTime);
            countOfrequest = (TextView) itemLayout.findViewById(R.id.countOfblood);
            countDone = (TextView) itemLayout.findViewById(R.id.countDone);
            bloodTyep = (TextView) itemLayout.findViewById(R.id.bloodTyep);
            BloodInfoLayout = (RelativeLayout) itemLayout.findViewById(R.id.relativeLayoutvis);


        }
    }

}
