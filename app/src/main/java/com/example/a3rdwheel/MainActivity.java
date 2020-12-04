package com.example.a3rdwheel;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.example.a3rdwheel.Models.User;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class MainActivity extends AppCompatActivity {
    private TextView userEmail;
    private FirebaseUser fUser;
    private FirebaseAuth fAuth;
    private DatabaseReference fDatabaseRef;
    private static final String TAG = "MainActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        userEmail = findViewById(R.id.verifiedUserEmail);
        fAuth = FirebaseAuth.getInstance();
        fUser = fAuth.getCurrentUser();
        fDatabaseRef = FirebaseDatabase.getInstance().getReference("users").child(fUser.getUid());
        Log.i(TAG, "Database Ref user: " + fDatabaseRef );

        fDatabaseRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                User user = snapshot.getValue(User.class);
                Log.i(TAG, "Database Ref user: " + user );

                assert user != null;
                userEmail.setText(user.getEmail());
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(MainActivity.this, error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });

        //set bottom fragment
        FragmentViewTabs fvt = FragmentViewTabs.newInstance(0);
        FragmentManager fManager = getSupportFragmentManager();                 //manager to control
        FragmentTransaction transaction = fManager.beginTransaction();          //transaction for actions
        transaction.add(R.id.main_frm_navigation, fvt).commit();     //trans process(container, fragment)
    }


}