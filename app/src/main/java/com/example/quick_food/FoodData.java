package com.example.quick_food;

public class FoodData {

     private String itemName;
     private String itemDescription;
     private int itemImage;

    public FoodData(String itemName, String itemDescription, int itemImage) {
        this.itemName = itemName;
        this.itemDescription = itemDescription;
        this.itemImage = itemImage;
    }

    public String getItemName() {
        return itemName;
    }

    public String getItemDescription() {
        return itemDescription;
    }

    public int getItemImage() {
        return itemImage;
    }
}
