package com.example.a3rdwheel;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
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
    }

    @Override
    public int getItemCount() {
        return catList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView image;
        TextView price, brand, model;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            image = itemView.findViewById(R.id.imgViewPhoto);
            price = itemView.findViewById(R.id.txtViewPrice);
            brand = itemView.findViewById(R.id.txtViewBrand);
            model = itemView.findViewById(R.id.txtViewModel);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    AppCompatActivity activity = (AppCompatActivity)v.getContext();
                    CardetailFragment cardetailFragment = new CardetailFragment();
                    activity.getSupportFragmentManager().beginTransaction().add(R.id.fragment_carDetail, cardetailFragment).addToBackStack(null).commit();
                }
            });
        }

        void setData(Car data){
            Glide.with(mContext).load(data.getImageUrl()).into(image);
            price.setText("$" + data.getPrice() + "/day");
            brand.setText(data.getBrand());
            model.setText(data.getModel());
        }


    }
}
