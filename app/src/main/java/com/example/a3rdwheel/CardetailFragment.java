package com.example.a3rdwheel;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link CardetailFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class CardetailFragment extends Fragment {

    private String mcarBrand, mcarType, mcarModel, mcarYear, mhostName, mhostEmail, mhostPhone;

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public CardetailFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment CardetailFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static CardetailFragment newInstance(String param1, String param2) {
        CardetailFragment fragment = new CardetailFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    public static CardetailFragment newInstance(){
        return new CardetailFragment();
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

        final View rootView = inflater.inflate(R.layout.fragment_cardetail, container, false);
        final TextView carBrand = rootView.findViewById(R.id.carDetailBrand);
        final TextView carType = rootView.findViewById(R.id.carDetailType);
        final TextView carModel = rootView.findViewById(R.id.carDetailModel);
        final TextView carYear = rootView.findViewById(R.id.carDetailYear);

        final TextView hostName = rootView.findViewById(R.id.carDetailHostName);
        final TextView hostEmail = rootView.findViewById(R.id.carDetailHostEmail);
        final TextView hostPhone = rootView.findViewById(R.id.carDetailHostPhone);

//        mcarBrand = carBrand.getText().toString();
//        mcarType = carType.getText().toString();
//        mcarModel = carModel.getText().toString();
//        mcarYear = carYear.getText().toString();
//
//

        return rootView;
    }
}