package com.example.a3rdwheel;


import android.os.Parcel;
import android.os.Parcelable;

import com.google.firebase.firestore.IgnoreExtraProperties;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.io.Serializable;

@IgnoreExtraProperties
public class Car implements Serializable {

    //FirebaseStorage mFire
    String imageUrl;

    //StorageReference data = storage
//    public int year;
    public String brand, model, type, year;

    public Car() {

    }

    public Car(String imageUrl, String year, String brand, String model, String type) {
        this.imageUrl = imageUrl;
        this.year = year;
        this.brand = brand;
        this.model = model;
        this.type = type;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public String getYear() {
        return year;
    }

    public String getBrand() {
        return brand;
    }

    public String getModel() {
        return model;
    }

    public String getType() {
        return type;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public void setYear(String year) {
        this.year = year;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public void setType(String type) {
        this.type = type;
    }



}
