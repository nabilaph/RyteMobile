package com.example.ryteapplication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.ryteapplication.HelperClass.StoryHelperClass;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.database.annotations.NotNull;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class NewStory extends AppCompatActivity{

    // define variables
    EditText storyDet;

    Button cancelStory;
    Button postStory;

    //define variables
    Date currentDate;

    String username;

    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_story);

        mAuth = FirebaseAuth.getInstance();
        FirebaseUser currentUser = mAuth.getCurrentUser();

        //get current date
        currentDate = new Date();
        SimpleDateFormat formDate = new SimpleDateFormat("yyyy-MM-dd");
        String formattedDate = formDate.format(currentDate);

        // find components by id according to the defined variable
        storyDet = findViewById(R.id.reviewDet);

        cancelStory = findViewById(R.id.btn_cancelRev);
        postStory = findViewById(R.id.btn_postRev);

        cancelStory.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        //set on click listener for post button
        postStory.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String storyContent = storyDet.getText().toString();
                int Likes = 0;
                getUsernamefromDB(currentUser);

                storeStorytoDB(formattedDate, Likes, storyContent, currentUser, username);

                Toast.makeText(NewStory.this, "Story Posted", Toast.LENGTH_SHORT).show();
                //after review has posted, it will go back to the page before
                finish();

            }
        });

    }

    void storeStorytoDB(String date, int likesCount, String storyContent, FirebaseUser user, String username){
        String userid = user.getUid();
        FirebaseDatabase rootNode = FirebaseDatabase.getInstance();
        DatabaseReference reference = rootNode.getReference("stories");

        //Create helperclass reference and store data using firebase
        StoryHelperClass helper = new StoryHelperClass (date, likesCount, storyContent, userid, username);
        reference.push().setValue(helper);
    }

    void getUsernamefromDB(FirebaseUser user) {
        String userid = user.getUid();
        //final String[] username = new String[1];
        DatabaseReference ref = FirebaseDatabase.getInstance().getReference("users");
        Query userData = ref.orderByChild(userid);

        userData.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull @NotNull DataSnapshot snapshot) {
                String usernameFromDB = snapshot.child(userid).child("username").getValue(String.class);

                username = usernameFromDB;
            }

            @Override
            public void onCancelled(@NonNull @NotNull DatabaseError error) {

            }
        });
    }


}