package com.example.pharm;

public class Drug {
     String name;
     String ImageUrl;
     int quantity;

    public Drug() {
    }

    public Drug(String name, String ImageUrl, int quantity) {
        this.name = name;
        this.ImageUrl = ImageUrl;
        this.quantity = quantity;
    }

    public String getName() {
        return name;
    }

    public Drug(String name, String ImageUrl) {
        this.name = name;
        this.ImageUrl = ImageUrl;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getImageUrl() {
        return ImageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.ImageUrl = imageUrl;
    }

    public String getQuantity() {
        return ""+quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }
}
