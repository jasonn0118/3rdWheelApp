package com.example.a3rdwheel;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class HostNewCarActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_host_new_car);

        //get extra data: is this a car for sale?
        boolean salePage = getIntent().getBooleanExtra("SELLNEW",false);
        if(salePage){ //if new car is for sale, switch displays
            EditText carPriceTxt = findViewById(R.id.hostnew_txt_price);
            TextView carPriceLbl = findViewById(R.id.hostnew_lbl_price);
            TextView rentSellLbl = findViewById(R.id.hostnew_lbl_RentSell);
            TextView fareLbl = findViewById(R.id.hostnew_lbl_FareBy);
            fareLbl.setVisibility(View.GONE);
            rentSellLbl.setText("Put new car for sale");
            carPriceLbl.setText("Car Price ($): ");
            carPriceTxt.setHint("Price of car");
        }

        //cancel button- ends the activity
        Button cancelBtn = findViewById(R.id.hostnew_btn_cancel);
        cancelBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }
}