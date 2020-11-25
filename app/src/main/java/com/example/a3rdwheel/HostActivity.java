package com.example.a3rdwheel;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.Switch;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

// Standard client may become host when they enter this page.
// Ask them to register as host the first time, and if YES, change status of user into a host
// and allow them to enter this activity's main layout

public class HostActivity extends AppCompatActivity {

    FirebaseUser currentUser;   //user Object: The user currently logged in

    List<FirebaseCar> carList = new ArrayList<>();
    List<FirebasePost> rentPosts = new ArrayList<>();
    List<FirebasePost> salePosts = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_host);

        //set bottom fragment
        FragmentViewTabs fvt = FragmentViewTabs.newInstance(2);
        FragmentManager fManager = getSupportFragmentManager();                 //manager to control
        FragmentTransaction transaction = fManager.beginTransaction();          //transaction for actions
        transaction.add(R.id.host_fragment_navigation, fvt).commit();     //trans process(container, fragment)

        RecyclerView carListDisplay = findViewById(R.id.host_rcycl_DisplayList);
        carListDisplay.setLayoutManager(new LinearLayoutManager(this));

        //detect if user is a host
        //connect to firebase
        FirebaseAuth fAuth = FirebaseAuth.getInstance();
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        //find who is the user
        String currentUserEmail = fAuth.getInstance().getCurrentUser().getEmail();

        //refer to user roles only
        DatabaseReference ref = database.getReference("user");

        /*/own value event listener
        ValueEventListener userHostRoleVEListener = new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                //if snapShotted user (one user in database) has matching email...
                FirebaseUser targetUser = snapshot.getValue(FirebaseUser.class);
                if(targetUser.Email.equals(currentUserEmail)){
                    //check if user role is HOST
                    if(targetUser.User_Role.contains("HOST")){
                        //then this current user is target user
                        currentUser = targetUser;
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Log.e("HOSTHUB","Error connecting to DB to ascertain host status.");
            }
        };
        ref.addValueEventListener(userHostRoleVEListener);*/

        //switch triggers -> change recycler datas
        displayPostsBySwitches();
        Switch displayRentSwitch = findViewById(R.id.host_switch_ViewRent);
        displayRentSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                displayPostsBySwitches();
            }
        });
        Switch displaySellSwitch = findViewById(R.id.host_switch_ViewSell);
        displaySellSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                displayPostsBySwitches();
            }
        });
        //change the list to change data from Cars || Posts
        Switch displayDataKindSwitch = findViewById(R.id.host_switch_ViewPosts);
        displayDataKindSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                RecyclerView rcyclDisplayList = findViewById(R.id.host_rcycl_DisplayList);
                Switch displaySellSwitch = findViewById(R.id.host_switch_ViewSell);
                Switch displayRentSwitch = findViewById(R.id.host_switch_ViewRent);
                TextView headingLabel = findViewById(R.id.host_lbl_DisplayKind);

                if(displayDataKindSwitch.isChecked()){  //if checked, display POSTS
                    headingLabel.setText("Your car posts");
                    displaySellSwitch.setVisibility(View.VISIBLE);
                    displayRentSwitch.setVisibility(View.VISIBLE);

                    displayPostsBySwitches();

                } else { //if not checked, display CARS
                    headingLabel.setText("Your cars");
                    displaySellSwitch.setVisibility(View.GONE);
                    displayRentSwitch.setVisibility(View.GONE);

                    rcyclDisplayList.setAdapter(new HostCarListAdapter(getBaseContext(),carList));
                }
            }
        });
        displayDataKindSwitch.setChecked(true); //trigger switch to true

        //buttons to rent out/put on sale a new post
        Button createPostBtn = findViewById(R.id.host_btn_CreatePost);
        createPostBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //go to host new car, but with extra
                Intent newCarIntent = new Intent(getBaseContext(),HostNewPostActivity.class);
                startActivity(newCarIntent);
            }
        });
        //button to add a new car
        Button createNewCar = findViewById(R.id.host_btn_NewCar);
        createNewCar.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                //go to host new car, but with extra
                Intent newCarIntent = new Intent(getBaseContext(),HostNewCarActivity.class);
                startActivity(newCarIntent);
            }
        });

        /*if(currentUser.User_Role.contains("HOST")){  //if user is a host, hide the layout for registering & display main layout
            ConstraintLayout hostRegLayout = findViewById(R.id.host_become_host);
            hostRegLayout.setVisibility(View.GONE);
            ConstraintLayout hostMainLayout = findViewById(R.id.host_main_layout);
            hostMainLayout.setVisibility(View.VISIBLE);*/

            //if actually host, get carlists & PostLists from DB
            //get cars data
            DatabaseReference carRef = database.getReference("cars");
            carRef.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    //reset car list
                    carList = new ArrayList<>();

                    //for each item in snapShot (which is a car json object)
                    for (DataSnapshot child : snapshot.getChildren()) {
                        FirebaseCar targetCar = child.getValue(FirebaseCar.class);
                        carList.add(targetCar);
                    }

                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {
                    Log.e("HOSTHUB","Error in getting car listings");
                }
            });
            //get PostList
            DatabaseReference postRef = database.getReference("posts");
            postRef.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    //reset lists
                    salePosts = new ArrayList<>();
                    rentPosts = new ArrayList<>();

                    //for each item in snapShot (which is a POST json object)
                    for (DataSnapshot child : snapshot.getChildren()) {
                        FirebasePost targetPost = child.getValue(FirebasePost.class);
                        if(targetPost.posting_type){
                            salePosts.add(targetPost);
                        } else {
                            rentPosts.add(targetPost);
                        }
                    }

                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {
                    Log.e("HOSTHUB","Error in getting post listings");
                }
            });
        }

        //attach registration buttons for becoming a host (or not)
        /*Button registerToHost = findViewById(R.id.host_become_host_btn_register);
        registerToHost.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //TODO: add database user role as 'host'
                DatabaseReference usersRef = database.getReference("Users");
                usersRef.child(currentUser.User_ID+"").setValue(currentUser.User_Role.add("HOST"));

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
        });*/
    //}

    //switch post filtering based on switches.
    private void displayPostsBySwitches(){
        //interface views
        RecyclerView rcyclDisplayList = findViewById(R.id.host_rcycl_DisplayList);
        Switch displayRentSwitch = findViewById(R.id.host_switch_ViewRent);
        Switch displaySellSwitch = findViewById(R.id.host_switch_ViewSell);
        boolean showRent = displayRentSwitch.isChecked();
        boolean showSell = displaySellSwitch.isChecked();

        //set adapter data defined by switch values
        if(showRent && showSell){           //if show both cars for rent & sale
            List<FirebasePost> combinedList = new ArrayList<>();
            combinedList.addAll(rentPosts);
            combinedList.addAll(salePosts);
            rcyclDisplayList.setAdapter(new HostPostListAdapter(getBaseContext(),combinedList));
        }else if (showRent && !showSell){   //if show only rent
            rcyclDisplayList.setAdapter(new HostPostListAdapter(getBaseContext(),rentPosts));
        } else if (!showRent && showSell){  //if show only sell
            rcyclDisplayList.setAdapter(new HostPostListAdapter(getBaseContext(),salePosts));
        } else {                            //if show nothing
            rcyclDisplayList.setAdapter(new HostPostListAdapter(getBaseContext(),new ArrayList<>()));
        }
    }

    //fill list with cars
    private void displayCars(){
        RecyclerView rcyclListDisplay = findViewById(R.id.host_rcycl_DisplayList);
        rcyclListDisplay.setAdapter(new HostCarListAdapter(getBaseContext(),carList));
    }

}