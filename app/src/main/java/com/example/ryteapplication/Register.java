package com.example.ryteapplication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import static android.content.ContentValues.TAG;

public class Register extends AppCompatActivity {

    // define variables
    Button login, regis;
    TextInputLayout fullname, email, username, pass;

    private FirebaseAuth mAuth;

    @Override
    public void onStart() {
        super.onStart();
        // Check if user is signed in (non-null) and update UI accordingly.
        FirebaseUser user = mAuth.getInstance().getCurrentUser();
        if (user != null) {
            // Name, email address, and profile photo Url
            String name = user.getDisplayName();
            String email = user.getEmail();
            Uri photoUrl = user.getPhotoUrl();

            // Check if user's email is verified
            //boolean emailVerified = user.isEmailVerified();

            // The user's ID, unique to the Firebase project. Do NOT use this value to
            // authenticate with your backend server, if you have one. Use
            // FirebaseUser.getIdToken() instead.
            String uid = user.getUid();

            Toast.makeText(Register.this, "Hello "+ email, Toast.LENGTH_SHORT) .show();
        }else{
            Toast.makeText(Register.this, "No Active user", Toast.LENGTH_SHORT) .show();
        }
        //updateUI(currentUser);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        mAuth = FirebaseAuth.getInstance();

        // find components by id according to the defined variable
        fullname = findViewById(R.id.fullname);
        email = findViewById(R.id.email);
        username = findViewById(R.id.username);
        pass = findViewById(R.id.password);
        login = findViewById(R.id.btn_login);
        regis = findViewById(R.id.btn_regis);

        //set onclick login button which will display on login page
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(Register.this, Login.class);
                startActivity(i);
            }
        });

        //set onclick register button
        regis.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String full_name = fullname.getEditText().getText().toString().trim();
                String mail = email.getEditText().getText().toString().trim();
                String user =  username.getEditText().getText().toString().trim();
                String password =  pass.getEditText().getText().toString().trim();

                mAuth.createUserWithEmailAndPassword(mail, password)
                        .addOnCompleteListener(Register.this, new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                if (task.isSuccessful()) {
                                    // Sign in success, update UI with the signed-in user's information
                                    Log.d(TAG, "createUserWithEmail:success");
                                    FirebaseUser user = mAuth.getCurrentUser();
                                    Toast.makeText(Register.this, "Register successful", Toast.LENGTH_SHORT).show();

                                    //change page from register to login page
                                    Intent i = new Intent( Register.this, Login.class);
                                    startActivity(i);
                                    //updateUI(user);
                                } else {
                                    // If sign in fails, display a message to the user.
                                    Log.w(TAG, "createUserWithEmail:failure", task.getException());
                                    Toast.makeText(Register.this, "Authentication failed.",
                                            Toast.LENGTH_SHORT).show();
                                    //updateUI(null);
                                }

                                // ...
                            }
                        });


            }
        });
    }

    private Boolean validateEmail(){
        String val = email.getEditText().getText().toString();
        Boolean boolVal = false;

        if(val.isEmpty()){
            email.setError("Field cannot be empty!");
        }else{
            email.setError(null);
            email.setErrorEnabled(false);
            boolVal = true;
        }

        return boolVal;
    }

    private Boolean validateUsername(){
        String val = username.getEditText().getText().toString();
        String noWhiteSpace = "\\A\\w{4,20}\\z";
        Boolean boolVal = false;

        if(val.isEmpty()){
            username.setError("Field cannot be empty!");
        }else if(val.length() >= 15){
            username.setError("Username too long!");
        }else if(!val.matches(noWhiteSpace)){
            username.setError("white space are not allowed!");
        }else{
            username.setError(null);
            username.setErrorEnabled(false);
            boolVal = true;
        }

        return boolVal;
    }

    private Boolean validatePassword(){
        String val = pass.getEditText().getText().toString();
        Boolean boolVal = false;

        if(val.isEmpty()){
            pass.setError("Field cannot be empty!");
        }else{
            pass.setError(null);
            pass.setErrorEnabled(false);
            boolVal = true;
        }

        return boolVal;
    }

    public void registerUser (View view){
        if(!validateUsername() | !validatePassword()){
            return;
        }else{
            isUser();
        }
    }

    private void isUser() {

        String userEnteredUsername = username.getEditText().getText().toString();
        String userEnteredPassword = pass.getEditText().getText().toString();

        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("users");
    }

}