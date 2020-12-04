package com.example.a3rdwheel;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.List;

public class CarListAdapter extends RecyclerView.Adapter<CarListAdapter.ViewHolder> {

    private List<Car> catList;
    private Context mContext;
    private FragmentManager fragmentManager;
    private FragmentTransaction fragmentTransaction;
    Dialog mDialog;

    OnItemClickListener listener;

    public static interface OnItemClickListener{
        public void onItemClick(ViewHolder holder, View v, Car data);
    }



    public CarListAdapter(Context mContext, List<Car> carList) {
        this.mContext = mContext;
        this.catList = carList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.item_userlist, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.setData(catList.get(position));
//        holder.setOnItemClickListener(listener);
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Car data = catList.get(position);
                Bundle bundle = new Bundle();
                bundle.putString("brand", data.getBrand());
                bundle.putString("type", data.getType());
                bundle.putString("model", data.getModel());
                bundle.putString("year", data.getYear());
                bundle.putString("trip", data.getTrip());

                CardetailFragment cardetailFragment = new CardetailFragment();
                cardetailFragment.setArguments(bundle);
                AppCompatActivity activity = (AppCompatActivity)v.getContext();
                activity.getSupportFragmentManager().beginTransaction().replace(R.id.fragment_carDetail, cardetailFragment).addToBackStack(null).commit();

            }
        });

    }

    @Override
    public int getItemCount() {
        return catList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView image;
        TextView price, brand, model;

        OnItemClickListener listener;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            image = itemView.findViewById(R.id.imgViewPhoto);
            price = itemView.findViewById(R.id.txtViewPrice);
            brand = itemView.findViewById(R.id.txtViewBrand);
            model = itemView.findViewById(R.id.txtViewModel);

        }

        void setData(Car data){
            Glide.with(mContext).load(data.getImageUrl()).into(image);
            price.setText("$" + data.getPrice() + "/day");
            brand.setText(data.getBrand());
            model.setText(data.getModel());
        }

        public void setOnItemClickListener(OnItemClickListener listener){
            this.listener = listener;
        }
    }
}
