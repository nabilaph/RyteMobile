package com.example.ryteapplication.nav_fragment;

import android.content.res.Configuration;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.ryteapplication.Adapter.RVAdapter;
import com.example.ryteapplication.HelperClass.StoryHelperClass;
import com.example.ryteapplication.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static android.content.Context.MODE_PRIVATE;

public class PublicStoryFragment extends Fragment {

    RecyclerView RV_publicRev;
    RVAdapter adapter;
    TextView noStoriesLabel;

    ArrayList <StoryHelperClass> listStory;
    DatabaseReference database;
    View myFragment;

    public PublicStoryFragment() {
        // Required empty public constructor
    }

    public static PublicStoryFragment getInstance() {
        return new PublicStoryFragment();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        myFragment = inflater.inflate(R.layout.fragment_public_story, container, false);

        RV_publicRev = myFragment.findViewById(R.id.rv_publicstory);

        noStoriesLabel = myFragment.findViewById(R.id.noStories);

        listStory = new ArrayList<>();
        database = FirebaseDatabase.getInstance().getReference("stories");

        adapter = new RVAdapter(getContext(),listStory);
        RV_publicRev.setAdapter(adapter);
        RV_publicRev.setLayoutManager(new LinearLayoutManager(getContext()));

        displayData();

        return myFragment;
    }

    void displayData(){

        database.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull @NotNull DataSnapshot snapshot){
                if (snapshot.hasChildren()){
                    noStoriesLabel.setVisibility(View.GONE);
                    RV_publicRev.setVisibility(View.VISIBLE);
                    listStory.clear();
                    for (DataSnapshot dataSnapshot:snapshot.getChildren()){
                        StoryHelperClass helper = dataSnapshot.getValue(StoryHelperClass.class);
                        helper.setKey(dataSnapshot.getKey());
                        listStory.add(helper);

                    }
                }else {
                    noStoriesLabel.setVisibility(View.VISIBLE);
                    RV_publicRev.setVisibility(View.GONE);
                }
                Collections.reverse(listStory);
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull @NotNull DatabaseError error) {

            }
        }
        );
    }


    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);

        if (getFragmentManager() != null) {
            getFragmentManager()
                    .beginTransaction()
                    .detach(this)
                    .attach(this)
                    .commit();
        }
    }
}