package com.example.a3rdwheel;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.a3rdwheel.userprofile.UserProfile;
import com.google.android.material.tabs.TabLayout;

/**
 * A fragment at the bottom of main views that will change the interface screens
 * between Renting|Buying|Host|Profile|Etc.
 *
 */
public class FragmentViewTabs extends Fragment {

    private static int selectedTab;

    public FragmentViewTabs() {
        // Required empty public constructor
    }

    //newInstance builds the fragment and returns it for use
    //params: Int value of the tab that is currently selected
    public static FragmentViewTabs newInstance(int currentTab) {
        FragmentViewTabs fragment = new FragmentViewTabs();
        selectedTab = currentTab;
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_view_tabs, container, false);

        TabLayout viewSelect = view.findViewById(R.id.ViewFragment_ViewTabs);
        //set selected tab
        viewSelect.getTabAt(selectedTab).select();

        //set onClick listener for tab selected:
        viewSelect.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {

            //on tab selected
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                //only work when tab selected is NOT the tab that is currently active
                if(viewSelect.getSelectedTabPosition() != selectedTab){
                    switch(viewSelect.getSelectedTabPosition()) {
                        case 0: //switch to Renting Screen
                            startActivity(new Intent(getContext(),RentalActivity.class));
                            break;
                        case 1: //switch to Purchase Screen
                            //TODO: display PURCHASE ACTIVITY from fragment
                            break;
                        case 2: //switch to Host Screen
                            startActivity(new Intent(getContext(),HostActivity.class));
                            break;
                        case 3: //switch to Profile Screen
                            startActivity(new Intent(getContext(), UserProfile.class));
                            break;
                    }
                }
            }

            //do nothing when tab is unselected or reselected
            @Override
            public void onTabUnselected(TabLayout.Tab tab) {}
            @Override
            public void onTabReselected(TabLayout.Tab tab) {}
        });

        return view;
    }

}