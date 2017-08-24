package com.wadimakkah.d7om7.wareed;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.TranslateAnimation;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.List;

import static android.R.attr.id;


public class Main_status_adapter extends RecyclerView.Adapter<Main_status_adapter.ViewHolder> {
    private List<RequestBlood> requestedBloodList;
    private changeActivity mCategoryHandler;


    public Main_status_adapter(List<RequestBlood> requestBloods, changeActivity handler) {
        this.requestedBloodList = requestBloods;
        mCategoryHandler = handler;

    }

    public interface changeActivity {
        public void Clicked(int position, int id);

        public void ClickedNVG(int position, int id);

        public void ClickedDonerDonate(int position, int id);

        public void ClickedShare(int position, int id);

        public void ClickedcoundBlood(int position, int id);

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
        holder.bloodTyep.setText(" " + requestedBloodList.get(position).getBloodType());
        holder.cityAndHospital_textview.setText(requestedBloodList.get(position).getCityName() + " , " + requestedBloodList.get(position).getNameOfHospital());
        holder.FileNumber.setText("ID File " + requestedBloodList.get(position).getPatientFileNumber());

        holder.BloodInfoLayout.setVisibility(View.GONE);


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
                    animation.setFillAfter(false);
                    holder.BloodInfoLayout.startAnimation(animation);
                    holder.BloodInfoLayout.setVisibility(View.GONE);

                }
                //  mCategoryHandler.Clicked(position, id);
            }
        });
        holder.chat_pic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mCategoryHandler.Clicked(position, id);

            }
        });
        holder.imageViewNAV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mCategoryHandler.ClickedNVG(position, id);

            }
        });
        holder.share.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mCategoryHandler.ClickedShare(position, id);
            }
        });

        holder.button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mCategoryHandler.ClickedDonerDonate(position,id);
            }
        });
        holder.countOfblood_pic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mCategoryHandler.ClickedcoundBlood(position,id);

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
        ImageView chat_pic;
        ImageView share;
        TextView countOfrequest;
        TextView cityAndHospital_textview;
        TextView bloodTyep;
        LinearLayout BloodInfoLayout;
        public boolean isExpanded = false;
        TextView FileNumber;
        ImageView imageViewNAV;
        Button button2;
         ImageView countOfblood_pic;
        public ViewHolder(View itemLayout) {
            super(itemLayout);
            button2=(Button)itemLayout.findViewById(R.id.button2);
            FileNumber = (TextView) itemLayout.findViewById(R.id.file_number);
            cityAndHospital_textview = (TextView) itemLayout.findViewById(R.id.cityAndHospital_textview);
            reasonOfRequest = (TextView) itemLayout.findViewById(R.id.reasonOfRequist);
            statusTime = (TextView) itemLayout.findViewById(R.id.statusTime);
            chat_pic = (ImageView) itemLayout.findViewById(R.id.chat_pic);
            countOfrequest = (TextView) itemLayout.findViewById(R.id.countOfblood);
            bloodTyep = (TextView) itemLayout.findViewById(R.id.bloodTyep);
            BloodInfoLayout = (LinearLayout) itemLayout.findViewById(R.id.LinearLayoutVisibale);
            imageViewNAV = (ImageView) itemLayout.findViewById(R.id.imageViewNAV);
            share = (ImageView) itemLayout.findViewById(R.id.share);
            countOfblood_pic=(ImageView)itemLayout.findViewById(R.id.countOfblood_pic);
        }
    }

}
