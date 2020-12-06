package com.example.a3rdwheel;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CalendarView;
import android.widget.DatePicker;
import android.widget.RadioGroup;
import android.widget.SimpleAdapter;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class HostNewPostActivity extends AppCompatActivity {

    //Firebase Items
    private DatabaseReference database;
    private FirebaseDatabase fbInstance;
    private String userId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_host_new_post);

        //Firebase Reference
        fbInstance = FirebaseDatabase.getInstance();
        database = fbInstance.getReference("posts");

        //get list of cars to populate spinner
        DatabaseReference carRef = fbInstance.getReference("cars");
        carRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                //reset car list
                List<String> carNameList = new ArrayList<>();

                //for each item in snapShot (which is a car json object)
                for (DataSnapshot child : snapshot.getChildren()) {
                    FirebaseCar targetCar = child.getValue(FirebaseCar.class);
                    carNameList.add(targetCar.name);
                }

                //attach to spinner
                Spinner carNameSpn = findViewById(R.id.hostnewpost_spn_CarSelect);
                ArrayAdapter<String> spinnerAdapter = new ArrayAdapter<String>(getBaseContext(), android.R.layout.simple_spinner_dropdown_item,carNameList);
                spinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                carNameSpn.setAdapter(spinnerAdapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Log.e("HOSTNEWPOST","Error in getting car listings");
            }
        });

        Button cancelBtn = findViewById(R.id.hostnewpost_btn_Cancel);
        cancelBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        Button confirmBtn = findViewById(R.id.hostnewpost_btn_AddPost);
        confirmBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //get data from elements
                Spinner spnCar = findViewById(R.id.hostnewpost_spn_CarSelect);
                RadioGroup postTypeGrp = findViewById(R.id.hostnewpost_rdGrp_PostingType);
                TextView  postTitleTxt = findViewById(R.id.hostnewpost_txt_PostTitle);
                TextView  postDescTxt = findViewById(R.id.hostnewpost_txt_PostDesc);
                TextView postPriceTxt = findViewById(R.id.hostnewpost_txt_PostPrice);
                CalendarView postStartDt = findViewById(R.id.hostnewpost_cal_StartDate);
                CalendarView postEndDt = findViewById(R.id.hostnewpost_cal_EndDate);
                String carname = spnCar.getSelectedItem().toString();
                String postTitle = postTitleTxt.getText().toString();
                String postDescript = postDescTxt.getText().toString();
                boolean postingSell = false;
                if(postTypeGrp.getCheckedRadioButtonId() == R.id.hostnewpost_rdo_PostingTypeSell){
                    postingSell = true;
                }
                if(postPriceTxt.getText().toString().isEmpty()){
                    Toast.makeText(getBaseContext(), "Please enter post price.", Toast.LENGTH_LONG).show();
                    return;
                }
                double postPrice = Double.parseDouble(postPriceTxt.getText().toString());
                Date startDate = new Date(postStartDt.getDate());
                Date endDate = new Date(postEndDt.getDate());

                //validate data
                if(postTitle.isEmpty() || carname.isEmpty()){
                    Toast.makeText(getBaseContext(), "Please select car and enter title.", Toast.LENGTH_LONG).show();
                    return;
                }
                //validate date range
                if(endDate.compareTo(startDate) < 0 ){
                    Toast.makeText(getBaseContext(), "Post end-date illegal.", Toast.LENGTH_LONG).show();
                    return;
                }

                //create POST
                FirebasePost newPost = new FirebasePost(postTitle,postDescript,startDate,endDate,postingSell,postPrice);

                //put post into FB
                //get user key
                if (TextUtils.isEmpty(userId)) {
                    userId = database.push().getKey();
                }

                //send to Firebase
                database.child(userId).setValue(newPost);
                Toast.makeText(getBaseContext(),"New post added!",Toast.LENGTH_LONG).show();
                finish();
            }
        });
    }
}