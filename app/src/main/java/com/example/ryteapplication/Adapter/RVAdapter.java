package com.example.ryteapplication.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.ryteapplication.HelperClass.StoryHelperClass;
import com.example.ryteapplication.R;

import java.util.ArrayList;


public class RVAdapter extends RecyclerView.Adapter<RVAdapter.RVviewHolder> {

    //define variables
    public  Context context;
    ArrayList <StoryHelperClass> listStory;

    public RVAdapter(Context context, ArrayList<StoryHelperClass> listStory) {
        this.context = context;
        this.listStory = listStory;
    }

    public class RVviewHolder extends RecyclerView.ViewHolder{

        //define variables
        TextView txt_likeCount,txt_storyDet, txt_storyDate, txt_username;
        ImageView like_btn;

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
        StoryHelperClass helper= listStory.get(position);
        //set text
        holder.txt_likeCount.setText(Integer.toString(helper.getLikesCount()));
        holder.txt_storyDate.setText(helper.getDate());
        holder.txt_storyDet.setText(helper.getStoryContent());
        holder.txt_username.setText(helper.getUsername());

        holder.like_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


            }
        });

    }

    @Override
    public int getItemCount() {
        return listStory.size();
    }



}
