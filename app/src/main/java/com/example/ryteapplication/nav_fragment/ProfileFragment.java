package com.example.ryteapplication.nav_fragment;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.ryteapplication.HelperClass.StoryHelperClass;
import com.example.ryteapplication.Login;
import com.example.ryteapplication.R;
import com.example.ryteapplication.UpdateProfile;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

import static android.content.Context.MODE_PRIVATE;

public class ProfileFragment extends Fragment {

    // define variables
    TextInputLayout fullname, username, password, email;
    TextView bigFullname, likesCount, storiesCount;
    Button updateProfile, logOut;
    SwipeRefreshLayout swipeRefresh;

    int likesTotal = 0;

    Context context;
    View myFragment;

    SharedPreferences sp;

    //make array list for user data
    ArrayList<String> userData = new ArrayList();

    //firebase
    private FirebaseAuth mAuth;

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        this.context = context;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        swipeRefresh = myFragment.findViewById(R.id.swipeRefresh);
        //swipe refresh on click listener
        swipeRefresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                getFragmentManager().beginTransaction().replace(R.id.container_fragment, new ProfileFragment()).commit();
                swipeRefresh.setRefreshing(false);
            }
        });
    }

    public static ProfileFragment getInstance() {
        return new ProfileFragment();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        myFragment = inflater.inflate(R.layout.fragment_profile, container, false);

        mAuth = FirebaseAuth.getInstance();
        FirebaseUser currentUser = mAuth.getCurrentUser();

        // find components by id according to the defined variable
        fullname = myFragment.findViewById(R.id.fullname);
        username = myFragment.findViewById(R.id.username);
        email = myFragment.findViewById(R.id.email);
        password = myFragment.findViewById(R.id.password);
        bigFullname = myFragment.findViewById(R.id.bigFullname);
        likesCount = myFragment.findViewById(R.id.likesCount);
        storiesCount = myFragment.findViewById(R.id.storiesCount);

        // find components by id according to the defined variable
        updateProfile = myFragment.findViewById(R.id.btn_update);
        logOut = myFragment.findViewById(R.id.btn_logout);

        showUserData(currentUser);
        showInsight(currentUser);

        //set on click listener for update profile button
        updateProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getContext(), "Profile Updated", Toast.LENGTH_SHORT).show();
                Intent i = new Intent(context, UpdateProfile.class);
                startActivity(i);
            }
        });


        //set on click listener for logout button
        logOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FirebaseAuth.getInstance().signOut();

                //make toast for displays a text that has successfully logged out
                Toast.makeText(getContext(), "Logged Out", Toast.LENGTH_SHORT).show();
                Intent i = new Intent(context, Login.class);
                startActivity(i);

            }
        });

        return myFragment;
    }

    private void showUserData(FirebaseUser user){
        String userid = user.getUid();
        DatabaseReference ref = FirebaseDatabase.getInstance().getReference("users");
        Query userData = ref.orderByChild(userid);

        userData.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                String emailFromDB = snapshot.child(userid).child("email").getValue(String.class);
                String fullnameFromDB = snapshot.child(userid).child("fullname").getValue(String.class);
                String passwordFromDB = snapshot.child(userid).child("password").getValue(String.class);
                String usernameFromDB = snapshot.child(userid).child("username").getValue(String.class);

                fullname.getEditText().setText(fullnameFromDB);
                username.getEditText().setText(usernameFromDB);
                password.getEditText().setText(passwordFromDB);
                email.getEditText().setText(emailFromDB);
                bigFullname.setText(fullnameFromDB);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    private void showInsight(FirebaseUser user){
        String userid = user.getUid();

        countInsight(user);

        DatabaseReference ref = FirebaseDatabase.getInstance().getReference("insight");
        Query userData = ref.orderByChild(userid);

        userData.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                int likesCountFromDB = snapshot.child(userid).child("likesCount").getValue(Integer.class);
                int storiesCountFromDB = snapshot.child(userid).child("storiesCount").getValue(Integer.class);

                likesCount.setText(Integer.toString(likesCountFromDB));
                storiesCount.setText(Integer.toString(storiesCountFromDB));
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    void countInsight(FirebaseUser user){
        String userid = user.getUid();
        DatabaseReference ref = FirebaseDatabase.getInstance().getReference("stories");
        Query userData = ref.orderByChild("userid").equalTo(userid);
        //int likesTotal = 0;

        userData.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                int storiesCount = (int) snapshot.getChildrenCount();
                DatabaseReference insightRef = FirebaseDatabase.getInstance().getReference("insight");
                insightRef.child(userid).child("storiesCount").setValue(storiesCount);

                int likes = 0;
                for (DataSnapshot dataSnapshot:snapshot.getChildren()){
                    likes = likes + dataSnapshot.child("likesCount").getValue(Integer.class);
                }
                insightRef.child(userid).child("likesCount").setValue(likes);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

}