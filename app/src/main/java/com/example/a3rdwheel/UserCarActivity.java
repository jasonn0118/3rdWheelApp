package com.example.a3rdwheel;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;

import java.util.ArrayList;

public class UserCarActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private LinearLayoutManager linearLayoutManager;
    private CarListAdapter userListAdapter;
    
    private ArrayList<Car> userCarList= new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_car);

        //get user list from rental activity
        Intent extras = getIntent();
        userCarList = (ArrayList<Car>)extras.getSerializableExtra("userList");

        //add list to recycler view(user list)
        recyclerView = findViewById(R.id.rvCarList);
        linearLayoutManager = new LinearLayoutManager(this);

        recyclerView.setLayoutManager(linearLayoutManager);
        userListAdapter = new CarListAdapter(getApplicationContext(), userCarList);
        recyclerView.setAdapter(userListAdapter);

    }
}