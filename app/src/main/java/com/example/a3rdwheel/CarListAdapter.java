package com.example.a3rdwheel;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import java.util.List;

public class CarListAdapter extends RecyclerView.Adapter<CarListAdapter.ViewHolder> {

    private List<Car> catList;

    public CarListAdapter(List<Car> carList) {
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
        ImageView photo;
        TextView type, price, description;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            photo = itemView.findViewById(R.id.imgViewPhoto);
            type = itemView.findViewById(R.id.txtViewType);
            price = itemView.findViewById(R.id.txtViewPrice);
            description = itemView.findViewById(R.id.txtViewShortDescription);
        }

        void setData(Car data){
            Picasso.get()
                    .load(data.getImage())
                    .fit()
                    .centerCrop()
                    .into(photo);
            type.setText(data.getType());
            price.setText(data.getPrice());
            description.setText(data.getShortDescription());
        }
    }
}
