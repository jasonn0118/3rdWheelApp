package com.example.a3rdwheel.userprofile;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import android.os.Bundle;

import com.example.a3rdwheel.R;
import com.example.a3rdwheel.userprofile.UserPagerAdapter;
import com.google.android.material.tabs.TabItem;
import com.google.android.material.tabs.TabLayout;

public class UserProfile extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_profile);

        TabLayout tabLayout = findViewById(R.id.userTabBar);
        TabItem tabInfo = findViewById(R.id.user_tabInfo);
        TabItem tabTrip = findViewById(R.id.user_tabTrip);
        TabItem tabBilling = findViewById(R.id.user_tabBilling);
        ViewPager viewPager = findViewById(R.id.user_viewpager);

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
}