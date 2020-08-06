package com.example.foodorderingsys;

public class Product {

    private String name;
    private String price;
    private String img;

    private String qty;

    public String getBill() {
        return bill;
    }

    public void setBill(String bill) {
        this.bill = bill;
    }

    private String bill;

    public String getImg() {
        return img;
    }

    public String getQty() {
        return qty;
    }

    public void setImg(String img) {
        this.img = img;
    }


    public void setQty(String qty) {
        this.qty = qty;
    }


    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

}
