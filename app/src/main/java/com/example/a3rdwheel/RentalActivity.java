package com.example.a3rdwheel;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.animation.LinearInterpolator;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.yuyakaido.android.cardstackview.CardStackLayoutManager;
import com.yuyakaido.android.cardstackview.CardStackListener;
import com.yuyakaido.android.cardstackview.CardStackView;
import com.yuyakaido.android.cardstackview.Direction;
import com.yuyakaido.android.cardstackview.StackFrom;
import android.view.animation.DecelerateInterpolator;
import com.yuyakaido.android.cardstackview.RewindAnimationSetting;

import java.util.ArrayList;
import java.util.List;

public class RentalActivity extends AppCompatActivity {

    private CardStackLayoutManager cardManager;
    private CardStackAdapter cardAdapter;
    private CardStackView cardStackView;

    private CarListAdapter userListAdapter;
    private boolean userLike = false;

    private ArrayList<Car> carList= new ArrayList<>();
    private ArrayList<Car> userCarList= new ArrayList<>();

    //firebase
    private DatabaseReference dbRef;

    private RecyclerView recyclerView;
    private LinearLayoutManager linearLayoutManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rental);

        //add list to recycler view(user list)
        recyclerView = findViewById(R.id.rvCarList);
        linearLayoutManager = new LinearLayoutManager(this);


        //set card stack
        cardStackView = findViewById(R.id.card_stack_view);
        cardManager = new CardStackLayoutManager(this, new CardStackListener() {

            @Override
            public void onCardDragging(Direction direction, float ratio) {

            }

            @Override
            public void onCardSwiped(Direction direction) {
                if (direction == Direction.Right){
                    Toast.makeText(RentalActivity.this, "Save it", Toast.LENGTH_SHORT).show();
                    userLike = true;
                }
                if (direction == Direction.Left){
                    Toast.makeText(RentalActivity.this, "Remove it", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onCardRewound() {}

            @Override
            public void onCardCanceled() {}

            @Override
            public void onCardAppeared(View view, int position) {
                if (userLike == true){
                    userCarList.add(cardAdapter.getItems().get(position-1));
                    userListAdapter.notifyDataSetChanged();
                }
            }

            @Override
            public void onCardDisappeared(View view, int position) {

            }
        });
        cardManager.setStackFrom(StackFrom.None);
        cardManager.setVisibleCount(3);
        cardManager.setTranslationInterval(8.0f);
        cardManager.setScaleInterval(0.95f);
        cardManager.setSwipeThreshold(0.3f);
        cardManager.setMaxDegree(0);
        cardManager.setDirections(Direction.HORIZONTAL);
        cardManager.setOverlayInterpolator(new LinearInterpolator());

        dbRef = FirebaseDatabase.getInstance().getReference();
        //arraylist
        carList = new ArrayList<>();

        //clear arraylist
        ClearAll();

        //Get Data Method
        GetDataFromFirebase();

        //set rewind
        RewindAnimationSetting setting = new RewindAnimationSetting.Builder()
                .setDirection(Direction.Bottom)
                .setDuration(200)
                .setInterpolator(new DecelerateInterpolator())
                .build();
        cardManager.setRewindAnimationSetting(setting);
        //when click rewind button, rewind feature will be run
        Button btnRewind = findViewById(R.id.btnRewind);
        btnRewind.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cardStackView.rewind();
            }
        });

    }

    private void GetDataFromFirebase() {

        Query query = dbRef.child("cars");

        query.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                ClearAll();
                for (DataSnapshot snapshot: dataSnapshot.getChildren()){
                    Car car = new Car();

                    car.setImageUrl(snapshot.child("imageUrl").getValue().toString());
                    car.setBrand(snapshot.child("brand").getValue().toString());
                    car.setModel(snapshot.child("model").getValue().toString());
                    car.setType(snapshot.child("type").getValue().toString());
                    car.setYear(snapshot.child("year").getValue().toString());

                    carList.add(car);
                }

                cardAdapter = new CardStackAdapter(getApplicationContext(), carList);
                cardStackView.setAdapter(cardAdapter);
                cardAdapter.notifyDataSetChanged();
                cardStackView.setLayoutManager(cardManager);
                cardStackView.setItemAnimator(new DefaultItemAnimator());

                recyclerView.setLayoutManager(linearLayoutManager);
                userListAdapter = new CarListAdapter(getApplicationContext(), userCarList);
                recyclerView.setAdapter(userListAdapter);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }
    private void ClearAll(){
        if(carList != null){
            carList.clear();
            if(cardAdapter != null){
                cardAdapter.notifyDataSetChanged();
            }
        }
        carList = new ArrayList<>();
    }

}