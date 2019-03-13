
package com.swiftqube.soccergist;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class SignupActivity extends AppCompatActivity implements View.OnClickListener {
    FirebaseAuth firebaseAuth;
    FirebaseUser user;
    EditText edtFirstName, edtLastName, edtUsername, edtEmail, edtpassword;
    Button btnSubmit;
    RadioGroup rdbGroup;
    ProgressDialog progressDialog;
    android.support.v7.app.ActionBar actionBar;
    
    String firstName, lastName, username, email, gender, password;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        //initialize UI elements
        edtFirstName = findViewById(R.id.edtFirstName);
        edtLastName = findViewById(R.id.edtLastName);
        edtUsername = findViewById(R.id.edtUsername);
        edtEmail = findViewById(R.id.edtEmail);
        edtpassword = findViewById(R.id.edtPassword);
        btnSubmit = findViewById(R.id.btnSubmit);
        btnSubmit.setOnClickListener(this);
        rdbGroup = findViewById(R.id.rdbGroup);
        actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        progressDialog = new ProgressDialog(this);

        firebaseAuth = FirebaseAuth.getInstance();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btnSubmit:
                signupUser();
                break;
        }
    }

    private void signupUser() {
        firstName = edtFirstName.getText().toString().trim();
        lastName = edtLastName.getText().toString().trim();
        username = edtUsername.getText().toString().trim();
        email = edtEmail.getText().toString().trim();
        password = edtpassword.getText().toString().trim();

        switch (rdbGroup.getCheckedRadioButtonId()) {
            case R.id.rdbMale:
                gender = "male";
                break;
            case R.id.rdbFemale:
                gender = "female";
                break;
        }

        if (TextUtils.isEmpty(firstName)) {
            Toast.makeText(this,"Enter first name",Toast.LENGTH_LONG).show();
            return;}

        if (TextUtils.isEmpty(lastName)) {
            Toast.makeText(this,"Enter last name",Toast.LENGTH_LONG).show();
            return;}

        if (TextUtils.isEmpty(username)) {
            Toast.makeText(this,"Enter username",Toast.LENGTH_LONG).show();
            return;}

        if (TextUtils.isEmpty(email)) {
            Toast.makeText(this,"Enter email",Toast.LENGTH_LONG).show();
            return;}

        if (TextUtils.isEmpty(password)) {
            Toast.makeText(this,"Enter password",Toast.LENGTH_LONG).show();
            return;}

        if (TextUtils.isEmpty(gender)) {
            Toast.makeText(this,"Select gender",Toast.LENGTH_LONG).show();
            return;}

        progressDialog.setMessage("Registering. Please Wait...");
        progressDialog.show();

        firebaseAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful()){
                        /*
                        Get current user from firebase authentication, then set username as user display name
                         */
                            user = firebaseAuth.getCurrentUser();
                            UserProfileChangeRequest profileUpdate = new UserProfileChangeRequest.Builder()
                                    .setDisplayName(username)
                                    .build();
                            user.updateProfile(profileUpdate);
                            addUserToDatabase();
                            progressDialog.dismiss();
                            Toast.makeText(getApplicationContext(), "Registration successful.", Toast.LENGTH_SHORT).show();
                            startActivity(new Intent(SignupActivity.this, MainActivity.class));
                            finish();
                        }
                        else{
                            Toast.makeText(getApplicationContext(), "Registration error. Check your details", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }

    public void addUserToDatabase(){
        FirebaseFirestore database = FirebaseFirestore.getInstance(); //Initialize cloud firestore
        String key = user.getUid(); //retrieve the user id so we can later use as key in the database

        HashMap<String, String> userData = new HashMap<>();
        userData.put("a1_firstName", firstName);
        userData.put("a2_lastname", lastName);
        userData.put("a3_username", username);
        userData.put("a4_email", email);
        userData.put("a5_gender", gender);
        userData.put("a6_imageUrl", "none");

        Map<String, Object> update = new HashMap<>();
        update.put(key, userData);

        database.collection("users").document(key).set(update); //update details to Firestore
    }
}
