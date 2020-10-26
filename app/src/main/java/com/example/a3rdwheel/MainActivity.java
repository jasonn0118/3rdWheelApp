package com.example.a3rdwheel;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

    }

    //FIXME: go to hostActivity placeholder button function - delete once redundant
    public void gotoHost(View v){
            startActivity(new Intent(getBaseContext(),HostActivity.class));
    }
}