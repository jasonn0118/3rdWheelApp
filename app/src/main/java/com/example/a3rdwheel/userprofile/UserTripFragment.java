package com.example.a3rdwheel.userprofile;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.a3rdwheel.R;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link UserTripFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class UserTripFragment extends Fragment {

    RecyclerView recyclerView;
    RecyclerView.Adapter adapter;
    ArrayList<TripDataModel> dataHolder;

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public UserTripFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment UserTripFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static UserTripFragment newInstance(String param1, String param2) {
        UserTripFragment fragment = new UserTripFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_user_trip, container, false);
        recyclerView = view.findViewById(R.id.user_recycler);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        dataHolder = new ArrayList<>();

        TripDataModel obj1 = new TripDataModel("Distance","53.00km","Vehicle","Porsche",
                "Service","Car Sharing","City", "Vancouver");
        dataHolder.add(obj1);

        TripDataModel obj2 = new TripDataModel("Distance","37.00km","Vehicle","Jeep",
                "Service","Car Sharing","City", "Vancouver");
        dataHolder.add(obj2);


        recyclerView.setAdapter(new TripAdapter(dataHolder));
        // Inflate the layout for this fragment
        return view;
    }
}