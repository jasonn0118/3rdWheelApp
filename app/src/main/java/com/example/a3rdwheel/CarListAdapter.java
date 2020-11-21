package com.example.a3rdwheel;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.squareup.picasso.Picasso;

import java.util.List;

public class CarListAdapter extends RecyclerView.Adapter<CarListAdapter.ViewHolder> {

    private List<Car> catList;
    private Context mContext;

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
        TextView type, brand, model;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            image = itemView.findViewById(R.id.imgViewPhoto);
            type = itemView.findViewById(R.id.txtViewType);
            brand = itemView.findViewById(R.id.txtViewPrice);
            model = itemView.findViewById(R.id.txtViewShortDescription);
        }

        void setData(Car data){
            Glide.with(mContext).load(data.getImageUrl()).into(image);
            type.setText(data.getType());
            brand.setText(data.getBrand());
            model.setText(data.getModel());
        }
    }
}
