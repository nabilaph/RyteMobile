package com.example.ryteapplication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.ryteapplication.HelperClass.InsightHelperClass;
import com.example.ryteapplication.HelperClass.UserHelperClass;
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
    private DatabaseReference usersRef;
    private DatabaseReference insightRef;

    @Override
    public void onStart() {
        super.onStart();
        // Check if user is signed in (non-null) and update UI accordingly.
        FirebaseUser user = mAuth.getInstance().getCurrentUser();
        if (user != null) {
            String email = user.getEmail();
            Toast.makeText(Register.this, "Hello "+ email, Toast.LENGTH_SHORT) .show();
        }else{
            Toast.makeText(Register.this, "No Active user", Toast.LENGTH_SHORT) .show();
        }
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

                if(validateUsername() & validatePassword() & validateEmail() & validateFullname() ){
                    mAuth.createUserWithEmailAndPassword(mail, password)
                            .addOnCompleteListener(Register.this, new OnCompleteListener<AuthResult>() {
                                @Override
                                public void onComplete(@NonNull Task<AuthResult> task) {
                                    if (task.isSuccessful()) {
                                        // Sign in success, update UI with the signed-in user's information
                                        Log.d(TAG, "createUserWithEmail:success");
                                        FirebaseUser currentUser = mAuth.getCurrentUser();
                                        addInsightCount(currentUser);
                                        storeUserData(currentUser,full_name, user, password, mail);

                                        Toast.makeText(Register.this, "Register successful", Toast.LENGTH_SHORT).show();

                                        //change page from register to login page
                                        Intent i = new Intent( Register.this, Login.class);
                                        startActivity(i);
                                    } else {
                                        // If sign in fails, display a message to the user.
                                        Log.w(TAG, "createUserWithEmail:failure", task.getException());
                                        Toast.makeText(Register.this, "Authentication failed.",
                                                Toast.LENGTH_SHORT).show();
                                    }


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

    private Boolean validateFullname(){
        String val = fullname.getEditText().getText().toString();
        Boolean boolVal = false;

        if(val.isEmpty()){
            fullname.setError("Field cannot be empty!");
        }else{
            fullname.setError(null);
            fullname.setErrorEnabled(false);
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
            username.setError("White space are not allowed!");
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
        String noWhiteSpace = "\\A\\w{4,20}\\z";

        if(val.isEmpty()){
            pass.setError("Field cannot be empty!");
        }else if(val.length() > 15){
            pass.setError("Password cannot be more than 15 characters!");
        }else if(val.length() < 8){
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

    void storeUserData(FirebaseUser user, String fullname, String username, String password, String email){
        String uid = user.getUid();

        usersRef = FirebaseDatabase.getInstance().getReference("users");

        UserHelperClass helper = new UserHelperClass(email, fullname, password, uid, username);

        usersRef.child(uid).setValue(helper);

    }

    void addInsightCount(FirebaseUser user){
        String uid = user.getUid();
        int likesCount = 0;
        int storiesCount = 0;

        insightRef = FirebaseDatabase.getInstance().getReference("insight");

        InsightHelperClass helper = new InsightHelperClass(likesCount, storiesCount, uid);

        insightRef.child(uid).setValue(helper);
    }

}