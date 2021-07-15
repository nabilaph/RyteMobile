package com.example.ryteapplication.nav_fragment;

import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.ryteapplication.Adapter.RVAdapterMyRev;
import com.example.ryteapplication.R;

import java.util.ArrayList;

import static android.content.Context.MODE_PRIVATE;

public class MyStoryFragment extends Fragment {

    RecyclerView RV_myRev;
    RVAdapterMyRev adapter;

    ArrayList<String> like_count, story_detail, story_date, username;

    View myFragment;

    SharedPreferences sp;

    // define the name of shared preferences and key
    String SP_NAME = "mypref";

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

        RV_myRev = myFragment.findViewById(R.id.rv_mystory);

        //get shared preferences
        sp = getContext().getSharedPreferences(SP_NAME, MODE_PRIVATE);

        //array list for display review
        like_count = new ArrayList<>();
        story_detail = new ArrayList<>();
        username = new ArrayList<>();
        story_date = new ArrayList<>();

        //harus di store dulu value nya ke array
        //storeDataInArray();

        adapter = new RVAdapterMyRev(getContext(),like_count, story_detail, story_date, username);
        RV_myRev.setAdapter(adapter);
        RV_myRev.setLayoutManager(new LinearLayoutManager(getContext()));


        return myFragment;
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