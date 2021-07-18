package com.example.ryteapplication;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.io.IOException;

public class UpdateProfile extends AppCompatActivity {

    private TextView mTextView;
    private Button cancelBtn, saveBtn, changePicBtn;
    TextInputLayout fullname, username, password, email;
    ImageView profPic;

    Uri imageUri;
    private static final int PICK_IMAGE = 1;

    //firebase
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_profile);

        mAuth = FirebaseAuth.getInstance();
        FirebaseUser currentUser = mAuth.getCurrentUser();

        fullname = findViewById(R.id.fullname);
        username = findViewById(R.id.username);
        email = findViewById(R.id.email);
        password = findViewById(R.id.password);
        cancelBtn = findViewById(R.id.btn_cancel);
        saveBtn = findViewById(R.id.btn_save);

        profPic = findViewById(R.id.profilePic);
        changePicBtn = findViewById(R.id.changePicBtn);

        showUserData(currentUser);

        cancelBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        saveBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                updateProfileData(currentUser);
                Toast.makeText(UpdateProfile.this, "Profile updated!", Toast.LENGTH_SHORT).show();
                //after review has posted, it will go back to the page before
                finish();
            }
        });

        changePicBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent gallery = new Intent();
                gallery.setType("image/*");
                gallery.setAction(Intent.ACTION_GET_CONTENT);

                startActivityForResult(Intent.createChooser(gallery, "Select Picture"), PICK_IMAGE);

            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == PICK_IMAGE && resultCode == RESULT_OK){
            imageUri = data.getData();
            try {
                Bitmap bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), imageUri);
                profPic.setImageBitmap(bitmap);
            }catch(IOException e){
                e.printStackTrace();
            }
        }
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
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    private void updateProfileData(FirebaseUser user){
        String userid = user.getUid();
        String fullnameChanged = fullname.getEditText().getText().toString();
        String usernameChanged = username.getEditText().getText().toString();

        DatabaseReference ref = FirebaseDatabase.getInstance().getReference("users");
        ref.child(userid).child("fullname").setValue(fullnameChanged);
        ref.child(userid).child("username").setValue(usernameChanged);

        updateUsernameInStory(userid, usernameChanged);
    }

    private void updateUsernameInStory(String userid, String username){
        DatabaseReference ref = FirebaseDatabase.getInstance().getReference("stories");
        Query userData = ref.orderByChild("userid").equalTo(userid);

        userData.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
//                String keyStory = snapshot.getKey();
//                ref.child(keyStory).child("username").setValue(username);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
}