package com.example.d7om7.wareed;


import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.TranslateAnimation;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.List;

import static android.R.attr.animation;
import static android.R.attr.id;

public class AdapterMyCases extends RecyclerView.Adapter<AdapterMyCases.ViewHolder> {
    private List<RequestBlood> requestedBloodList;
    private changeActivity mCategoryHandler;

    public AdapterMyCases(List<RequestBlood> requestBloods, changeActivity handler) {
        this.requestedBloodList = requestBloods;
        mCategoryHandler = handler;

    }

    public interface changeActivity {

        public void ClickedDelet(int position, int id);

        public void ClickedUpdate(int position, int id);

    }

    @Override
    public AdapterMyCases.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemLayout = LayoutInflater.from(parent.getContext()).inflate(R.layout.my_cases_view_holder, parent, false);
        ViewHolder viewHolder = new ViewHolder(itemLayout);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {
        holder.reasonOfRequest.setText("" + requestedBloodList.get(position).getReasonOfRequest());
        holder.statusTime.setText("" + requestedBloodList.get(position).getStatusTime());
        holder.bloodTyep.setText(" " + requestedBloodList.get(position).getBloodType());
        holder.cityAndHospital_textview.setText(requestedBloodList.get(position).getCityName() +
                " , " + requestedBloodList.get(position).getNameOfHospital());
        holder.FileNumber.setText(requestedBloodList.get(position).getPatientFileNumber());


        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (holder.BloodInfoLayout.getVisibility() == View.GONE) {
                    Animation animation = new TranslateAnimation(0, 0, -75, 0);
                    animation.setDuration(175);
                    animation.setFillAfter(true);
                    holder.BloodInfoLayout.startAnimation(animation);
                    holder.BloodInfoLayout.setVisibility(View.VISIBLE);
                } else {
                    Animation animation = new TranslateAnimation(0, 0, 75, 0);
                    animation.setDuration(175);
                    animation.setFillAfter(true);
                    holder.BloodInfoLayout.startAnimation(animation);
                    holder.BloodInfoLayout.setVisibility(View.GONE);

                }
                //  mCategoryHandler.Clicked(position, id);
            }
        });

        holder.DeleteMyDetails.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mCategoryHandler.ClickedDelet(position, id);
                Animation animation = new TranslateAnimation(0, 0, 75, 0);
                animation.setDuration(175);
                animation.setFillAfter(true);
                holder.BloodInfoLayout.startAnimation(animation);
                holder.BloodInfoLayout.setVisibility(View.GONE);
            }
        });

        holder.colseMyDEtails.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Animation animation = new TranslateAnimation(0, 0, 75, 0);
                animation.setDuration(175);
                animation.setFillAfter(true);
                holder.BloodInfoLayout.startAnimation(animation);
                holder.BloodInfoLayout.setVisibility(View.GONE);

            }
        });

        holder.UpdateMyDEtails.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mCategoryHandler.ClickedUpdate(position,id);
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
        TextView cityAndHospital_textview;
        TextView bloodTyep;
        LinearLayout BloodInfoLayout;
        Button DeleteMyDetails;
        public boolean isExpanded = false;
        TextView FileNumber;
        Button colseMyDEtails;
        Button UpdateMyDEtails;

        public ViewHolder(View itemLayout) {
            super(itemLayout);
            FileNumber = (TextView) itemLayout.findViewById(R.id.file_numberMyDetails);
            cityAndHospital_textview = (TextView) itemLayout.findViewById(R.id.cityAndHospital_textviewMyDetails);
            reasonOfRequest = (TextView) itemLayout.findViewById(R.id.reasonOfRequistMyDetails);
            statusTime = (TextView) itemLayout.findViewById(R.id.statusTimeMyDetails);
            bloodTyep = (TextView) itemLayout.findViewById(R.id.bloodTyepMyDetails);
            BloodInfoLayout = (LinearLayout) itemLayout.findViewById(R.id.relativeLayoutvisMyDetails);
            DeleteMyDetails = (Button) itemLayout.findViewById(R.id.DeleteBTNMyDetails);
            colseMyDEtails = (Button) itemLayout.findViewById((R.id.closeBTNMyDetails));
            UpdateMyDEtails = (Button) itemLayout.findViewById(R.id.UpdateBTNMyDetails);
        }
    }

}
