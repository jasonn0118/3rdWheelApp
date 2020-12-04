package com.example.a3rdwheel;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.a3rdwheel.Models.User;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class RegisterActivity extends AppCompatActivity {

    EditText mEmail, mPassword, mPasswordConfirm;
    Button mRegisterBtn;
    TextView mLoginLink;
    ProgressBar progressBar;
    private FirebaseAuth fAuth;
    private DatabaseReference mDatabaseRef;
    private static final String TAG = "RegisterActivity";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        mEmail = findViewById(R.id.regiEmail);
        mPassword = findViewById(R.id.regiPassword);
        mPasswordConfirm = findViewById(R.id.regiPasswordConfirm);
        mRegisterBtn = findViewById(R.id.regiBtn);
        mLoginLink = findViewById(R.id.LoginLink);
        progressBar = findViewById(R.id.regiProgress);

        fAuth = FirebaseAuth.getInstance();
        Log.i(TAG, "CURRENT USER:  " + fAuth.getCurrentUser());

        if(fAuth.getCurrentUser() != null){
            startActivity(new Intent(getApplicationContext(), RentalActivity.class));
            finish();
        }

        mRegisterBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = mEmail.getText().toString().trim();
                String password = mPassword.getText().toString().trim();
                String passwordConfirm = mPasswordConfirm.getText().toString().trim();


                //Validation check.
                if(TextUtils.isEmpty(email)) {
                    mEmail.setError("Email is required.");
                    return;
                }
                if(TextUtils.isEmpty(password)){
                    mPassword.setError("Password is required.");
                    return;
                }
                if(TextUtils.isEmpty(passwordConfirm)){
                    mPasswordConfirm.setError("Password confirm field is required.");
                    return;
                }
                if(password.length() < 6 || passwordConfirm.length() < 6) {
                    mPassword.setError("Password must be longer than 6 characters.");
                    return;
                }
                if(!password.equals(passwordConfirm)) {
                    mPassword.setError("Password must be matched. Please double check.");
                    return;
                }

                progressBar.setVisibility(View.VISIBLE);

                //Info: Firebase method called
                register(email, password);
            }

        });
        mLoginLink.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), LoginActivity.class));
            }
        });
    }

    private void register(String email, String password) {
        progressBar.setVisibility(View.VISIBLE);
        fAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful()){
                    FirebaseUser registerUser = fAuth.getCurrentUser();
                    assert registerUser != null;
                    String userId =  registerUser.getUid();
                    mDatabaseRef = FirebaseDatabase.getInstance().getReference("users");
                    createUserOnDB(userId, email);
                } else {
                    progressBar.setVisibility(View.GONE);
                    Toast.makeText(RegisterActivity.this, "Error!: " + Objects.requireNonNull(task.getException()).getMessage(), Toast.LENGTH_SHORT).show();
                }
            }
        });
    }


    private void createUserOnDB(String userId, String email){
        User user = new User(email);
        mDatabaseRef.child(userId).setValue(user).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                progressBar.setVisibility(View.GONE);
                if(task.isSuccessful()) {
                    startActivity(new Intent(getApplicationContext(), RentalActivity.class));
                    finish();
                } else {
                    Toast.makeText(RegisterActivity.this, "Error!: " + Objects.requireNonNull(task.getException()).getMessage(), Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}