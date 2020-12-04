package com.example.a3rdwheel.userprofile;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.viewpager.widget.ViewPager;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.a3rdwheel.LoginActivity;
import com.example.a3rdwheel.Models.User;
import com.example.a3rdwheel.R;
import com.example.a3rdwheel.userprofile.UserPagerAdapter;
import com.google.android.material.tabs.TabItem;
import com.google.android.material.tabs.TabLayout;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class UserProfile extends AppCompatActivity implements UserSettingFragment.OnFragmentInteractionListener {
    private boolean isFragDisplayed = false;
    private int mRadioButtonChoice = 2;

    private FirebaseAuth fAuth;
    private FirebaseUser fUser;
    private DatabaseReference mDatabaseRef;

    private static final String TAG = "UserProfile";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_profile);

        TabLayout tabLayout = findViewById(R.id.userTabBar);
        TabItem tabInfo = findViewById(R.id.user_tabInfo);
        TabItem tabTrip = findViewById(R.id.user_tabTrip);
        TabItem tabBilling = findViewById(R.id.user_tabBilling);
        ViewPager viewPager = findViewById(R.id.user_viewpager);
        ImageView imageView = findViewById(R.id.user_setting);
        ImageView logoutView = findViewById(R.id.user_logout);
        ImageView backButton = findViewById(R.id.user_back);



        fAuth = FirebaseAuth.getInstance();
        fUser = fAuth.getCurrentUser();
        mDatabaseRef = FirebaseDatabase.getInstance().getReference("users").child(fUser.getUid());

        mDatabaseRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                User user = snapshot.getValue(User.class);
                Log.i(TAG, "Database Ref user: " + user);

                String currUserFirstName = user.getFirstName();
                String currUserLastName = user.getLastName();
                String currUserGender = user.getGender();
                String currUserEmail = user.getEmail();
                String currUserPhone = user.getPhone();
                String currUserDriverLn = user.getDriverLN();


                assert user != null;
                setViewText(currUserFirstName, currUserLastName, currUserGender, currUserEmail, currUserPhone, currUserDriverLn);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(isFragDisplayed){
                    closeFragment();
                }else {
                    displayFragment();
                }
            }
        });

        logoutView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FirebaseAuth.getInstance().signOut();
                startActivity(new Intent(getApplicationContext(), LoginActivity.class));
            }
        });
        backButton.setOnClickListener(new View.OnClickListener() {
          @Override
          public void onClick(View view) {
              finish();
          }
        });

        UserPagerAdapter userPagerAdapter = new UserPagerAdapter(getSupportFragmentManager(),tabLayout.getTabCount());
        viewPager.setAdapter(userPagerAdapter);

        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                viewPager.setCurrentItem(tab.getPosition());
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
    }

    public void closeFragment() {
        FragmentManager fragmentManager = getSupportFragmentManager();
        UserSettingFragment userSettingFragment = (UserSettingFragment) fragmentManager.findFragmentById(R.id.user_frag_container);

        if(userSettingFragment != null){
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
            fragmentTransaction.remove(userSettingFragment).commit();
        }
        isFragDisplayed = false;
    }

    public void displayFragment(){
        UserSettingFragment userSettingFragment = UserSettingFragment.newInstance();
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction ft = fragmentManager.beginTransaction();
        ft.add(R.id.user_frag_container,userSettingFragment).addToBackStack(null).commit();
        isFragDisplayed = true;
    }

    @Override
    public void OnRadioButtonChoice(int choice) {
        TextView userorHost;
        userorHost = (TextView) findViewById(R.id.user_userorhost);
        mRadioButtonChoice = choice;
        if(choice == 0){
            userorHost.setText("User");
        }else if(choice == 1){
            userorHost.setText("Host");
        }
    }

    public void setViewText(String firstName, String lastName, String gender, String email, String phone, String driver) {
        TextView userFirstName = (TextView) findViewById(R.id.user_firstname);
        TextView userLastName = (TextView) findViewById(R.id.user_lastname);
        TextView userGender = (TextView) findViewById(R.id.user_txt_gender);
        TextView userEmail = (TextView) findViewById(R.id.user_txt_email);
        TextView userPhone = (TextView) findViewById(R.id.user_txt_phone);
        TextView userDriver = (TextView) findViewById(R.id.user_txt_driver);

        userFirstName.setText(firstName);
        userLastName.setText(lastName);
        userGender.setText(gender);
        userEmail.setText(email);
        userPhone.setText(phone);
        userDriver.setText(driver);

    }

}