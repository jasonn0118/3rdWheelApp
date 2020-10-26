package com.example.a3rdwheel;

import androidx.appcompat.app.AppCompatActivity;
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

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class HostActivity extends AppCompatActivity {

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
        transaction.add(R.id.host_fragment_navigation, fvt).addToBackStack(null).commit();     //trans process(container, fragment)

        RecyclerView carListDisplay = findViewById(R.id.host_rcycl_CarList);
        carListDisplay.setLayoutManager(new LinearLayoutManager(this));

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

        //button for total statistics of this guy
        Button gotoStatsButton = findViewById(R.id.host_btn_TotalStats);
        gotoStatsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //go to host status
                startActivity(new Intent(getBaseContext(),HostStatisticActivity.class));
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