package com.example.ryteapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import java.text.DateFormat;
import java.util.Date;

public class NewStory extends AppCompatActivity{

    // define variables
    EditText storyDet;

    Button cancelStory;
    Button postStory;

    //define variables
    String name, currentDate;

    SharedPreferences sp;

    // define the name of shared preferences and key
    String SP_NAME = "mypref";
    String KEY_NAME = "name";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_story);

        sp = getSharedPreferences(SP_NAME, MODE_PRIVATE);

        String name = sp.getString(KEY_NAME, null);

        //get current date
        currentDate = DateFormat.getDateInstance().format(new Date());

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
//                addReview(productName.getText().toString(),
//                        categorySelected,
//                        reviewDet.getText().toString(),
//                        name,
//                        currentDate );
                Toast.makeText(NewStory.this, "Story Posted", Toast.LENGTH_SHORT).show();
                //after review has posted, it will go back to the page before
                finish();

            }
        });

    }


}