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

import java.util.List;

public class CardStackAdapter extends RecyclerView.Adapter<CardStackAdapter.ViewHolder> {

    private Context mContext;
    private List<Car> items;

    public CardStackAdapter(Context mContext, List<Car> items) {
        this.mContext = mContext;
        this.items = items;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.item_card, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.setData(items.get(position));
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        ImageView image;
        TextView type, brand, model, price;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            image = itemView.findViewById(R.id.item_image);
            type = itemView.findViewById(R.id.item_type);
            brand = itemView.findViewById(R.id.item_brand);
            model = itemView.findViewById(R.id.item_model);
            price = itemView.findViewById(R.id.item_price);
        }

        void setData(Car data){
            Glide.with(mContext).load(data.getImageUrl()).into(image);
            type.setText(data.getType());
            brand.setText(data.getBrand());
            model.setText(data.getModel());
            price.setText("$" + data.getPrice() + "/day");
        }
    }

    public List<Car> getItems() {
        return items;
    }

    public void setItems(List<Car> items) {
        this.items = items;
    }
}
