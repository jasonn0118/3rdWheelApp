package com.example.a3rdwheel;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

/**
 * This is the adapter for Host's car list displays.
 */

public class HostCarListAdapter extends RecyclerView.Adapter<HostCarListAdapter.CarViewHolder> {
    List<String> carDisplayList;
    Context cntxt;

    //constructor
    public HostCarListAdapter(Context context, List<String> carList){
        this.carDisplayList = carList;
        this.cntxt = context;
    }

    //viewholder onCreate
    @NonNull
    @Override
    public CarViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType){
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.listitem_host_car, parent, false);
        final CarViewHolder myHolder = new CarViewHolder(itemView);				//create the holder with layout

        //assign interface elements
        myHolder.carPhoto = itemView.findViewById(R.id.listitem_host_CarImage);
        myHolder.carName = itemView.findViewById(R.id.listitem_host_CarName);
        myHolder.carItemView = itemView;

        return myHolder;
    }

    //on item created into viewholder
    @Override
    public void onBindViewHolder(@NonNull CarViewHolder holder, int position) {
        holder.carName.setText(carDisplayList.get(position));

        //attach interface onclick functions
        holder.carItemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //go to host car details - TODO: car ID will determine which car to display
                Intent newCarIntent = new Intent(cntxt, HostCarDetailActivity.class);
                newCarIntent.putExtra("TARGETCAR", position);
                cntxt.startActivity(newCarIntent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return carDisplayList.size();
    }

    //viewholder class
    public class CarViewHolder extends RecyclerView.ViewHolder{
        //interfaces of the item
        ImageView carPhoto;
        TextView carName;
        View carItemView;

        CarViewHolder(@NonNull View itemView){
            super(itemView);
        }
    }
}
