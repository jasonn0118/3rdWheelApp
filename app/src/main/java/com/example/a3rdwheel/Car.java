package com.example.a3rdwheel;

public class Car {
    private int image;
    private String type, year, price, shortDescription;

    public Car() {
    }

    public Car(int image, String type, String year, String price, String shortDescription) {
        this.image = image;
        this.type = type;
        this.year = year;
        this.price = price;
        this.shortDescription = shortDescription;
    }


    public int getImage() {
        return image;
    }

    public String getType() {
        return type;
    }

    public String getYear() {
        return year;
    }

    public String getPrice() {
        return price;
    }

    public String getShortDescription() {
        return shortDescription;
    }
}
