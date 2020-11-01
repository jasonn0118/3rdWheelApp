package com.example.a3rdwheel.userprofile;

import android.widget.TextView;

class TripDataModel {

    String distance,distanceTxt,vehicle,vehicleTxt,service,serviceTxt,city,cityTxt;

    public TripDataModel(String distance, String distanceTxt, String vehicle, String vehicleTxt, String service, String serviceTxt, String city, String cityTxt) {
        this.distance = distance;
        this.distanceTxt = distanceTxt;
        this.vehicle = vehicle;
        this.vehicleTxt = vehicleTxt;
        this.service = service;
        this.serviceTxt = serviceTxt;
        this.city = city;
        this.cityTxt = cityTxt;
    }

    public String getDistance() {
        return distance;
    }

    public void setDistance(String distance) {
        this.distance = distance;
    }

    public String getDistanceTxt() {
        return distanceTxt;
    }

    public void setDistanceTxt(String distanceTxt) {
        this.distanceTxt = distanceTxt;
    }

    public String getVehicle() {
        return vehicle;
    }

    public void setVehicle(String vehicle) {
        this.vehicle = vehicle;
    }

    public String getVehicleTxt() {
        return vehicleTxt;
    }

    public void setVehicleTxt(String vehicleTxt) {
        this.vehicleTxt = vehicleTxt;
    }

    public String getService() {
        return service;
    }

    public void setService(String service) {
        this.service = service;
    }

    public String getServiceTxt() {
        return serviceTxt;
    }

    public void setServiceTxt(String serviceTxt) {
        this.serviceTxt = serviceTxt;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getCityTxt() {
        return cityTxt;
    }

    public void setCityTxt(String cityTxt) {
        this.cityTxt = cityTxt;
    }
}
