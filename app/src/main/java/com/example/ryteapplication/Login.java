package com.example.ryteapplication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import static android.content.ContentValues.TAG;

public class Login extends AppCompatActivity {

    // define variables
    Button register, login;
    TextInputLayout email, pass;
    String SP_NAME = "mypref";
    SharedPreferences sp;

    //firebase
    private FirebaseAuth mAuth;

    @Override
    protected void onStart() {
        super.onStart();

        FirebaseUser user = mAuth.getInstance().getCurrentUser();
        if (user != null) {
            String email = user.getEmail();

            Toast.makeText(Login.this, "Hello "+ email, Toast.LENGTH_SHORT) .show();
        }else{
            Toast.makeText(Login.this, "No Active user", Toast.LENGTH_SHORT) .show();
        }
        //updateUI(currentUser);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        sp = getSharedPreferences(SP_NAME, MODE_PRIVATE);

        mAuth = FirebaseAuth.getInstance();

        // find components by id according to the defined variable
        email = findViewById(R.id.email);
        pass = findViewById(R.id.password);
        register = findViewById(R.id.btn_regis);
        login = findViewById(R.id.btn_login);

        //set onclick register button which will display on register page
        register.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                Intent i = new Intent(Login.this, Register.class);
                startActivity(i);
            }
        });


        //set onclick login button which will display on dashboard page
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String mail =  email.getEditText().getText().toString().trim();
                String password =  pass.getEditText().getText().toString().trim();

                if(!validateEmail() | !validatePassword()){
                    mAuth.signInWithEmailAndPassword(mail, password)
                            .addOnCompleteListener(Login.this, new OnCompleteListener<AuthResult>() {
                                @Override
                                public void onComplete(@NonNull Task<AuthResult> task) {
                                    if (task.isSuccessful()) {
                                        // Sign in success, update UI with the signed-in user's information
                                        Log.d(TAG, "signInWithEmail:success");
                                        FirebaseUser user = mAuth.getCurrentUser();
                                        Toast.makeText(Login.this, "Login Successful", Toast.LENGTH_SHORT) .show();
                                        startActivity(new Intent(Login.this, Dashboard.class));
                                        //updateUI(user);
                                    } else {
                                        // If sign in fails, display a message to the user.
                                        Log.w(TAG, "signInWithEmail:failure", task.getException());
                                        Toast.makeText(Login.this, "Authentication failed.",
                                                Toast.LENGTH_SHORT).show();
                                        //updateUI(null);
                                    }

                                    // ...
                                }
                            });
                }




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

    private Boolean validatePassword(){
        String val = pass.getEditText().getText().toString();
        Boolean boolVal = false;
        String noWhiteSpace = "\\A\\w{4,20}\\z";

        if(val.isEmpty()){
            pass.setError("Field cannot be empty!");
        }else if(val.length() >= 15){
            pass.setError("Password cannot be more than 15 characters!");
        }else if(val.length() <= 8){
            pass.setError("Username cannot be less than 8 characters!");
        }else if(!val.matches(noWhiteSpace)){
            pass.setError("White space are not allowed!");
        }else{
            pass.setError(null);
            pass.setErrorEnabled(false);
            boolVal = true;
        }

        return boolVal;
    }

}