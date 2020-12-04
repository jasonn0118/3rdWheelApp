package com.example.a3rdwheel;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class LoginActivity extends AppCompatActivity {

    EditText mEmail, mPassword;
    Button mLoginBtn;
    TextView mRegiLink;
    ProgressBar progressBar;
    FirebaseAuth fAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        mEmail = findViewById(R.id.userEmail);
        mPassword = findViewById(R.id.userPassword);
        progressBar = findViewById(R.id.loginProgress);
        mLoginBtn = findViewById(R.id.loginBtn);
        mRegiLink = findViewById(R.id.regiLink);
        fAuth = FirebaseAuth.getInstance();

        mLoginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = mEmail.getText().toString().trim();
                String password = mPassword.getText().toString().trim();

                //Validation check.
                if(TextUtils.isEmpty(email)) {
                    mEmail.setError("Email is required.");
                    return;
                }
                if(TextUtils.isEmpty(password)) {
                    mPassword.setError("Password is required.");
                    return;
                }
                if(password.length() < 6) {
                    mPassword.setError("Password must be longer than 6 characters.");
                    return;
                }

                progressBar.setVisibility(View.VISIBLE);

                //Info: Login method with firebase.
                login(email, password);

            }
        });

        mRegiLink.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), RegisterActivity.class));
            }
        });
    }

    private void login(String email, String password) {
        //Authenticate the user.
        fAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                progressBar.setVisibility(View.GONE);
                if(task.isSuccessful()){
                    Toast.makeText(LoginActivity.this, "User Logged in.", Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(getApplicationContext(), RentalActivity.class));
                    finish();
                } else {
                    Toast.makeText(LoginActivity.this, "Error!: " + task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}