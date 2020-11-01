package com.example.a3rdwheel.userprofile;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.a3rdwheel.R;

import java.util.ArrayList;

class TripAdapter extends RecyclerView.Adapter<TripAdapter.TripViewHolder>{

    ArrayList<TripDataModel> dataHolder;
    LayoutInflater layoutInflater;

    public TripAdapter(ArrayList<TripDataModel> dataHolder) {
        this.dataHolder = dataHolder;
    }

    @NonNull
    @Override
    public TripViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        layoutInflater = layoutInflater.from(parent.getContext());
        View v = layoutInflater.inflate(R.layout.layout_usertrip,parent,false);

        return new TripViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull TripViewHolder holder, int position) {


      holder.distance.setText(dataHolder.get(position).getDistance());
      holder.distanceTxt.setText(dataHolder.get(position).getDistanceTxt());
      holder.vehicle.setText(dataHolder.get(position).getVehicle());
      holder.vehicleTxt.setText(dataHolder.get(position).getVehicleTxt());
      holder.service.setText(dataHolder.get(position).getService());
      holder.serviceTxt.setText(dataHolder.get(position).getServiceTxt());
      holder.city.setText(dataHolder.get(position).getCity());
      holder.cityTxt.setText(dataHolder.get(position).getCityTxt());
    }

    @Override
    public int getItemCount() {
        return dataHolder.size();
    }

    public class TripViewHolder extends RecyclerView.ViewHolder {
        TextView distance;
        TextView distanceTxt;
        TextView vehicle;
        TextView vehicleTxt;
        TextView service;
        TextView serviceTxt;
        TextView city;
        TextView cityTxt;

        public TripViewHolder(@NonNull View itemView) {
            super(itemView);
            distance = itemView.findViewById(R.id.user_distance);
            distanceTxt = itemView.findViewById(R.id.user_distancetxt);
            vehicle = itemView.findViewById(R.id.user_vehicle);
            vehicleTxt = itemView.findViewById(R.id.user_vehicletxt);
            service = itemView.findViewById(R.id.user_service);
            serviceTxt = itemView.findViewById(R.id.user_servicetxt);
            city = itemView.findViewById(R.id.user_city);
            cityTxt = itemView.findViewById(R.id.user_citytxt);
        }
    }
}
