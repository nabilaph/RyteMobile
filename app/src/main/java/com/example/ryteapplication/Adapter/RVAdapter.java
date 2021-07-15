package com.example.ryteapplication.Adapter;

import android.content.Context;
import android.content.SharedPreferences;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.ryteapplication.R;

import java.util.ArrayList;


public class RVAdapter extends RecyclerView.Adapter<RVAdapter.RVviewHolder> {

    //define variables
    public  Context context;
    public ArrayList like_count, story_detail, story_date, username;

    //constructor
    public RVAdapter(Context context, ArrayList like_count, ArrayList story_detail,
                     ArrayList story_date, ArrayList username){
        this.context = context;
        this.like_count = like_count;
        this.story_date = story_date;
        this.story_detail = story_detail;
        this.username = username;
    }

    public class RVviewHolder extends RecyclerView.ViewHolder{

        //define variables
        TextView txt_likeCount,txt_storyDet, txt_storyDate, txt_username;
        ImageView like_btn;
        String SP_NAME = "mypref";
        SharedPreferences sp;

        public RVviewHolder(@NonNull View itemView) {
            super(itemView);

            // find components by id according to the defined variable
            txt_likeCount = itemView.findViewById(R.id.text_likeCount);
            txt_storyDet = itemView.findViewById(R.id.text_storyDet);
            txt_storyDate = itemView.findViewById(R.id.text_storyDate);
            txt_username = itemView.findViewById(R.id.text_username);

            like_btn = itemView.findViewById(R.id.like_btn);
        }
    }


    @NonNull
    @Override
    public RVviewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        // set layout for recycler view
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.rv_review_item, parent, false);

        return new RVviewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final RVviewHolder holder, int position) {

        //set text
        holder.txt_likeCount.setText(String.valueOf(like_count.get(position)));
        holder.txt_storyDate.setText(String.valueOf(story_date.get(position)));
        holder.txt_storyDet.setText(String.valueOf(story_detail.get(position)));
        holder.txt_username.setText(String.valueOf(username.get(position)));

        holder.like_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


            }
        });

    }

    @Override
    public int getItemCount() {
        return username.size();
    }



}
