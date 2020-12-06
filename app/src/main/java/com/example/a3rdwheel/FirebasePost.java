package com.example.a3rdwheel;

//this is the java representation of the POST json object in Firebase.

import java.util.Date;

public class FirebasePost {
    public String title;
    public  String description;
    public Date start_date;
    public Date end_date;
    public String car_name;        //TODO: Make foreign key ID
    public boolean posting_type;   //TRUE=SELL, FALSE=RENT
    public boolean inspected;
    public double price;           //if false, this is the price/hr, else this is car selling price.

    FirebasePost(){};

    FirebasePost(String title, String desc, Date start, Date end, boolean type, double price){
        this.title = title;
        this.description = desc;
        this.start_date = start;
        this.end_date = end;
        this.posting_type = type;
        this.price = price;
        this.inspected = false;
    }
}
