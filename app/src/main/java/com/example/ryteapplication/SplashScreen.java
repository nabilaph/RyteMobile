package com.example.ryteapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;

public class SplashScreen extends AppCompatActivity {

    // this is duration of splash screen
    private static int SPLASH_SCREEN = 5000;

    // Define variables
    Animation topanim;
    ImageView logo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.splash_screen);

        // input animation from res> anime folder
        topanim = AnimationUtils.loadAnimation(this, R.anim.top_animation);

        // find components by id according to the defined variable
        logo = findViewById(R.id.logo_amori);
        // set animation for logo
        logo.setAnimation(topanim);

        //define the next page after splash screen
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent intent = new Intent(SplashScreen.this, Login.class);
                startActivity(intent);
                finish();

            }
        }, SPLASH_SCREEN);
    }
}