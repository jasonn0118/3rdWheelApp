package com.example.a3rdwheel;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.Switch;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

// Standard client may become host when they enter this page.
// Ask them to register as host the first time, and if YES, change status of user into a host
// and allow them to enter this activity's main layout

public class HostActivity extends AppCompatActivity {

    boolean host = false;   //is this user a host?

    //TODO: PLACEHOLDER. get car datas From database
    List<String> rentCars = new ArrayList<>(Arrays.asList("SUV","Minivan","Pickup","Campervan"));
    List<String> saleCars = new ArrayList<>(Arrays.asList("Truck","Micro","Sedan"));

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_host);

        //set bottom fragment
        FragmentViewTabs fvt = FragmentViewTabs.newInstance(2);
        FragmentManager fManager = getSupportFragmentManager();                 //manager to control
        FragmentTransaction transaction = fManager.beginTransaction();          //transaction for actions
        transaction.add(R.id.host_fragment_navigation, fvt).commit();     //trans process(container, fragment)

        RecyclerView carListDisplay = findViewById(R.id.host_rcycl_CarList);
        carListDisplay.setLayoutManager(new LinearLayoutManager(this));

        //TODO: detect if user is a host
        //host = isUserHost(userId);
        //switch triggers -> change recycler datas
        displayCarsBySwitches();
        Switch displayRentSwitch = findViewById(R.id.host_switch_ViewRent);
        displayRentSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                displayCarsBySwitches();
            }
        });
        Switch displaySellSwitch = findViewById(R.id.host_switch_ViewSell);
        displaySellSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                displayCarsBySwitches();
            }
        });

        //buttons to rent out/put on sale a new car
        Button rentNewButton = findViewById(R.id.host_btn_NewRent);
        rentNewButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               //go to host new car
               startActivity(new Intent(getBaseContext(),HostNewCarActivity.class));
            }
        });
        Button sellNewButton = findViewById(R.id.host_btn_NewSale);
        sellNewButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //go to host new car, but with extra
                Intent newCarIntent = new Intent(getBaseContext(),HostNewCarActivity.class);
                newCarIntent.putExtra("SELLNEW", true);
                startActivity(newCarIntent);
            }
        });

        if(host){  //if user is a host, hide the layout for registering & display main layout
            ConstraintLayout hostRegLayout = findViewById(R.id.host_become_host);
            hostRegLayout.setVisibility(View.GONE);
            ConstraintLayout hostMainLayout = findViewById(R.id.host_main_layout);
            hostMainLayout.setVisibility(View.VISIBLE);
        }

        //attach registration buttons for becoming a host (or not)
        Button registerToHost = findViewById(R.id.host_become_host_btn_register);
        registerToHost.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //TODO: set database user role as 'host'
                //hide the registration, show the main layout
                ConstraintLayout hostRegLayout = findViewById(R.id.host_become_host);
                hostRegLayout.setVisibility(View.GONE);
                ConstraintLayout hostMainLayout = findViewById(R.id.host_main_layout);
                hostMainLayout.setVisibility(View.VISIBLE);
                Toast.makeText(getBaseContext(),"You are now host",Toast.LENGTH_LONG).show();
            }
        });
        Button notRegisterToHost = findViewById(R.id.host_become_host_btn_cancel);
        notRegisterToHost.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //if do not become a host then quit
                finish();
            }
        });
    }

    //TODO: OnResume or OnReturn to this activity, refresh adapter data list

    private void displayCarsBySwitches(){
        //interface views
        RecyclerView carListDisplay = findViewById(R.id.host_rcycl_CarList);
        Switch displayRentSwitch = findViewById(R.id.host_switch_ViewRent);
        Switch displaySellSwitch = findViewById(R.id.host_switch_ViewSell);
        boolean showRent = displayRentSwitch.isChecked();
        boolean showSell = displaySellSwitch.isChecked();

        //set adapter data defined by switch values
        if(showRent && showSell){           //if show both cars for rent & sale
            List<String> combinedList = new ArrayList<>();
            combinedList.addAll(rentCars);
            combinedList.addAll(saleCars);
            carListDisplay.setAdapter(new HostCarListAdapter(getBaseContext(),combinedList));
        }else if (showRent && !showSell){   //if show only rent
            carListDisplay.setAdapter(new HostCarListAdapter(getBaseContext(),rentCars));
        } else if (!showRent && showSell){  //if show only sell
            carListDisplay.setAdapter(new HostCarListAdapter(getBaseContext(),saleCars));
        } else {                            //if show nothing
            carListDisplay.setAdapter(new HostCarListAdapter(getBaseContext(),new ArrayList<>()));
        }
    }
}