package com.example.a3rdwheel;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.animation.LinearInterpolator;
import android.widget.Button;
import android.widget.Toast;

import com.yuyakaido.android.cardstackview.CardStackLayoutManager;
import com.yuyakaido.android.cardstackview.CardStackListener;
import com.yuyakaido.android.cardstackview.CardStackView;
import com.yuyakaido.android.cardstackview.Direction;
import com.yuyakaido.android.cardstackview.StackFrom;

import java.util.ArrayList;
import java.util.List;

public class RentalActivity extends AppCompatActivity {

    private CardStackLayoutManager manager;
    private CardStackAdapter adapter;

    private CarListAdapter userListAdapter;

    private List<Car> userCarList= new ArrayList<>();;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rental);

        CardStackView cardStackView = findViewById(R.id.card_stack_view);

        //set bottom fragment
        FragmentViewTabs fvt = FragmentViewTabs.newInstance(0);
        FragmentManager fManager = getSupportFragmentManager();                 //manager to control
        FragmentTransaction transaction = fManager.beginTransaction();          //transaction for actions
        transaction.add(R.id.rental_frm_navigation, fvt).commit();     //trans process(container, fragment)

        manager = new CardStackLayoutManager(this, new CardStackListener() {

            @Override
            public void onCardDragging(Direction direction, float ratio) {

            }

            @Override
            public void onCardSwiped(Direction direction) {
                if (direction == Direction.Right){
                    Toast.makeText(RentalActivity.this, "Save it", Toast.LENGTH_SHORT).show();

                }
                if (direction == Direction.Left){
                    Toast.makeText(RentalActivity.this, "Remove it", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onCardRewound() {

            }

            @Override
            public void onCardCanceled() {

            }

            @Override
            public void onCardAppeared(View view, int position) {
                Car appearedCar = adapter.getItems().get(position);
                //Log.d("Hwayoung", appearedCar.getType());
                userCarList.add(adapter.getItems().get(position));
            }

            @Override
            public void onCardDisappeared(View view, int position) {

            }
        });

        manager.setStackFrom(StackFrom.None);
        manager.setVisibleCount(3);
        manager.setTranslationInterval(8.0f);
        manager.setScaleInterval(0.95f);
        manager.setSwipeThreshold(0.3f);
        manager.setMaxDegree(0);
        manager.setDirections(Direction.HORIZONTAL);
        manager.setOverlayInterpolator(new LinearInterpolator());

        adapter = new CardStackAdapter(addList());
        cardStackView.setLayoutManager(manager);
        cardStackView.setAdapter(adapter);
        cardStackView.setItemAnimator(new DefaultItemAnimator());

        RecyclerView recyclerView = findViewById(R.id.rvCarList);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(linearLayoutManager);
        userListAdapter = new CarListAdapter(addList());
        recyclerView.setAdapter(userListAdapter);

    }

    private List<Car> addList() {
        List<Car> items = new ArrayList<>();
        items.add(new Car(R.drawable.porsche, "Porsche", "2019", "$300/day","One of the most luxurious cars...."));
        items.add(new Car(R.drawable.ferrari, "Ferrari", "2019", "$300/day","One of the most luxurious cars...."));
        items.add(new Car(R.drawable.benz, "Marcedes-Benz", "2019", "$200/day","One of the most luxurious cars...."));
        items.add(new Car(R.drawable.jeep, "Jeep", "2019", "$200/day","One of the most luxurious cars...."));
        items.add(new Car(R.drawable.mini, "Mini Cooper", "2019", "$100/day","One of the most luxurious cars...."));
        return items;
    }


}