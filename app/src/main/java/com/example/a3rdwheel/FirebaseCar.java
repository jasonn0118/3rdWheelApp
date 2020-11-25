package com.example.a3rdwheel;

//This is the car object representing the JSON structure of Car in Firebase db.

public class FirebaseCar {
    public String type,name,model;
    //TODO:FOREIGN KEYS?
    //TODO:how to photo from JSON?

    //DEFAULT CONSTRCT FOR SNAPSHOT
    FirebaseCar(){};

    FirebaseCar(String type, String name, String model){
        this.type = type;
        this.name = name;
        this.model = model;
    }
}
