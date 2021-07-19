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

import com.example.ryteapplication.Adapter.RVAdapterMyRev;
import com.example.ryteapplication.HelperClass.StoryHelperClass;
import com.example.ryteapplication.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;

import static android.content.Context.MODE_PRIVATE;

public class MyStoryFragment extends Fragment {

    RecyclerView RV_myRev;
    RVAdapterMyRev adapter;

    TextView noStoriesLabel;

    Boolean storyExist = false;

    ArrayList<StoryHelperClass> listStory;

    View myFragment;

    private DatabaseReference database;
    private FirebaseAuth mAuth;

    public MyStoryFragment() {
        // Required empty public constructor
    }

    public static MyStoryFragment getInstance() {
        return new MyStoryFragment();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        myFragment = inflater.inflate(R.layout.fragment_my_story, container, false);
        mAuth = FirebaseAuth.getInstance();
        FirebaseUser currentUser = mAuth.getCurrentUser();
        RV_myRev = myFragment.findViewById(R.id.rv_mystory);

        noStoriesLabel = myFragment.findViewById(R.id.noStories);

        listStory = new ArrayList<>();
        database = FirebaseDatabase.getInstance().getReference("stories");
        Query storyData = database.orderByChild("userid").equalTo(currentUser.getUid());

        adapter = new RVAdapterMyRev(getContext(), listStory);
        RV_myRev.setAdapter(adapter);
        RV_myRev.setLayoutManager(new LinearLayoutManager(getContext()));

        if(!isStoryExist(storyData)){
            noStoriesLabel.setVisibility(View.GONE);
            displayData(storyData);

        }else{
            noStoriesLabel.setVisibility(View.VISIBLE);
            RV_myRev.setVisibility(View.GONE);
        }

        return myFragment;
    }

    void displayData(Query storyData) {
        storyData.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull @NotNull DataSnapshot snapshot) {
                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                    StoryHelperClass helper = dataSnapshot.getValue(StoryHelperClass.class);
                    listStory.add(helper);
                }
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull @NotNull DatabaseError error) {

            }
        }
        );
    }

    Boolean isStoryExist(Query storyData){

        storyData.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()){
                    storyExist = true;
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        return storyExist;
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