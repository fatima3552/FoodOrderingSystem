package com.example.foodorderingsys;

public class gridViewProduct {
    private   String name;
    private   String price;
    private   String image ;

    public String getName() {
        return name;
    }

    public String getPrice() {
        return price;
    }

    public String getImage() {
        return image;
    }

    public gridViewProduct(String name, String price, String image) {
        this.name = name;
        this.price = price;
        this.image = image;
    }
}
