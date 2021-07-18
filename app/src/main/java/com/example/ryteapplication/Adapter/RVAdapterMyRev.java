package com.example.ryteapplication.Adapter;

import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.RecyclerView;

import com.example.ryteapplication.HelperClass.StoryHelperClass;
import com.example.ryteapplication.R;

import java.util.ArrayList;

import static android.content.Context.MODE_PRIVATE;

public class RVAdapterMyRev extends RecyclerView.Adapter<RVAdapterMyRev.RVviewHolder> {

    //define variables
    private Context context;
    ArrayList <StoryHelperClass> listStory;

    public RVAdapterMyRev(Context context, ArrayList<StoryHelperClass> listStory) {
        this.context = context;
        this.listStory = listStory;
    }

    @NonNull
    @Override
    public RVviewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.rv_review_item_my, parent, false);
        return new RVAdapterMyRev.RVviewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final RVviewHolder holder, final int position) {

        StoryHelperClass helper= listStory.get(position);
        //set text
        holder.txt_likeCount.setText(Integer.toString(helper.getLikesCount()));
        holder.txt_storyDate.setText(helper.getDate());
        holder.txt_storyDet.setText(helper.getStoryContent());
        holder.txt_username.setText(helper.getUsername());

        // set on click listener for delete button
        holder.delete_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View v) {
                //get review id from database
//                final int id = dbHelper.getIdRev(product_name.get(position), product_category.get(position),
//                        review_detail.get(position), username.get(position),  review_date.get(position));

                // alert dialog for make sure are the user want to delete this review
                AlertDialog.Builder builder = new AlertDialog.Builder(v.getContext());
                builder.setCancelable(false);
                builder.setMessage("Are you sure to delete this review?");
                builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        //delete review by id
                        boolean res = true;
                        if (res){

                            //make toast for tell the user that post has been successful deleted
                            Toast.makeText(v.getContext(), "This post has been successfully deleted", Toast.LENGTH_SHORT).show();

                            //remove data from array list
                            //like_count.remove(position);
                            //story_date.remove(position);
//                            story_detail.remove(position);
//                            username.remove(position);
                            notifyDataSetChanged();
                        }
                    }
                });
                builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                });
                AlertDialog alert = builder.create();
                alert.show();
            }
        });

        //set on click listener edit button
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

    public class RVviewHolder extends RecyclerView.ViewHolder{

        //define variables
        TextView txt_likeCount,txt_storyDet, txt_storyDate, txt_username;
        ImageView like_btn, delete_btn;

        public RVviewHolder(@NonNull final View itemView) {
            super(itemView);

            // find components by id according to the defined variable
            txt_likeCount = itemView.findViewById(R.id.text_likeCount);
            txt_storyDet = itemView.findViewById(R.id.text_storyDet);
            txt_storyDate = itemView.findViewById(R.id.text_storyDate);
            txt_username = itemView.findViewById(R.id.text_username);

            like_btn = itemView.findViewById(R.id.like_btn);
            delete_btn = itemView.findViewById(R.id.delete_btn);

        }


    }
}
