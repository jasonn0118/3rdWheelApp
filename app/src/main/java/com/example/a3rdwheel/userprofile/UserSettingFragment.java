package com.example.a3rdwheel.userprofile;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.example.a3rdwheel.Models.User;
import com.example.a3rdwheel.R;
import com.example.a3rdwheel.RegisterActivity;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link UserSettingFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class UserSettingFragment extends Fragment {

    private static final int USER = 0;
    private static final int HOST = 1;
    private String firstName;
    private String lastName;
    private String age;
    private String phone;
    private String gender;
    private String driver;

    private static final int NONE = 2;
    public int mRadioButtonChoice = NONE;
    OnFragmentInteractionListener mListener;
    private FirebaseAuth fAuth;
    private DatabaseReference mDatabaseRef;
    private FirebaseDatabase mDatabase;

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public UserSettingFragment() {
        // Required empty public constructor
    }

    public static UserSettingFragment newInstance(){
        return new UserSettingFragment();
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment UserSettingFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static UserSettingFragment newInstance(String param1, String param2) {
        UserSettingFragment fragment = new UserSettingFragment();
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

        fAuth = FirebaseAuth.getInstance();
        mDatabaseRef = FirebaseDatabase.getInstance().getReference();
        String userId = fAuth.getCurrentUser().getUid();


    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        final View rootView  = inflater.inflate(R.layout.fragment_user_setting, container, false);

        final RadioGroup radioGroup = rootView.findViewById(R.id.radio_group);

        Button btnSave = rootView.findViewById(R.id.user_submit);
        Button btnCancel = rootView.findViewById(R.id.user_cancel);
        EditText userFirstName = rootView.findViewById(R.id.edit_user_firstname);
        EditText userLastName = rootView.findViewById(R.id.edit_user_lastname);
        EditText userAge = rootView.findViewById(R.id.edit_user_age);
        EditText userGender = rootView.findViewById(R.id.edit_user_gender);
        EditText userPhone = rootView.findViewById(R.id.edit_user_phone);
        EditText userDrive = rootView.findViewById(R.id.edit_user_drive);


        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                View radioButton = radioGroup.findViewById(i);
                int index =  radioGroup.indexOfChild(radioButton);

                switch (index){
                    case USER:
                        mRadioButtonChoice = USER;
                        mListener.OnRadioButtonChoice(USER);
                        break;
                    case HOST:
                        mRadioButtonChoice = HOST;
                        mListener.OnRadioButtonChoice(HOST);
                        break;
                    default:
                        mRadioButtonChoice = NONE;
                        mListener.OnRadioButtonChoice(NONE);
                        break;
                }
            }
        });

        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FirebaseUser currentUser = fAuth.getCurrentUser();
                assert currentUser != null;
                String fUid = currentUser.getUid();
                String email = currentUser.getEmail();
                Log.i("PROFILE FRAG", "User ID From FAuth: " + fUid);

                firstName = userFirstName.getText().toString();
                lastName = userLastName.getText().toString();
                gender = userGender.getText().toString();
                age = userAge.getText().toString();
                phone = userPhone.getText().toString();
                driver = userDrive.getText().toString();

                //Validation Check.
                if(TextUtils.isEmpty(firstName)) {
                    userFirstName.setError("This field is required.");
                    return;
                }
                if(TextUtils.isEmpty(lastName)) {
                    userLastName.setError("This field is required.");
                    return;
                }
                if(TextUtils.isEmpty(gender)) {
                    userGender.setError("This field is required.");
                    return;
                }
                if(TextUtils.isEmpty(age)) {
                    userAge.setError("This field is required.");
                    return;
                }
                if(TextUtils.isEmpty(phone)) {
                    userPhone.setError("This field is required.");
                    return;
                }
                if(TextUtils.isEmpty(driver)) {
                    userDrive.setError("This field is required.");
                    return;
                }

                writeUpdateUser(fUid, email, firstName, lastName, gender, age, phone, driver);
                closeFragment();
            }
        });

        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                closeFragment();
            }
        });
        return rootView;
    }

    private void closeFragment() {
        getActivity().getSupportFragmentManager().popBackStack();
    }

    private void updateUserInfoToDB(String fUid, User updateUser ) {
        FirebaseDatabase.getInstance().getReference("users").child(fUid).setValue(updateUser).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if(task.isSuccessful()) {
                    Log.i("PROFILE FRAG", "Success!!");
                } else {
                    Log.i("PROFILE FRAG", "Fail!!");
                }
            }
        });
    }

    private void writeUpdateUser(String userId, String email, String newFirstName, String newLastName, String newGender, String newAge, String newPhone, String newDriver) {
        // Create new post at /user-posts/$userid/$postid and at
        // /posts/$postid simultaneously
        String key = mDatabaseRef.child("users").child(userId).getKey();
        Log.i("PROFILE FRAG", "KEY: " +key);
        User user = new User(email, newFirstName, newLastName, newGender, newAge, newPhone, newDriver);
        Map<String, Object> userValue = user.toMap();
        Map<String, Object> childUpdates = new HashMap<>();
        childUpdates.put("/users/"+key, userValue);

        mDatabaseRef.updateChildren(childUpdates);

    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

        if(context instanceof OnFragmentInteractionListener){
            mListener = (OnFragmentInteractionListener) context;
        }else {
            throw new ClassCastException(context.toString() + "must implement OnFragmentInteractionListener");
        }
    }

    interface OnFragmentInteractionListener {
        void OnRadioButtonChoice(int choice);
    }
}