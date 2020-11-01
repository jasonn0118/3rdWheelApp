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

public class RegisterActivity extends AppCompatActivity {

    EditText mEmail, mPassword, mPasswordConfirm;
    Button mRegisterBtn;
    TextView mLoginLink;
    ProgressBar progressBar;
    FirebaseAuth fAuth;

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

        if(fAuth.getCurrentUser() != null){
            startActivity(new Intent(getApplicationContext(), MainActivity.class));
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

                //register user in the Firebase.
                fAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful()){
                            Toast.makeText(RegisterActivity.this, "User Created", Toast.LENGTH_SHORT).show();
                            startActivity(new Intent(getApplicationContext(), MainActivity.class));
                        } else{
                            Toast.makeText(RegisterActivity.this, "Error!: " + task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                            progressBar.setVisibility(View.GONE);
                        }
                    }
                });
            }
        });

        mLoginLink.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), LoginActivity.class));
            }
        });
    }
}