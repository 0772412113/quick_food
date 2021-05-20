package com.example.quick_food;

public class CartDetails {

    private String cartId;
    private String foodName;
    private String foodPrice;
    private int itemImage;

    public CartDetails(String foodName, String foodPrice, int itemImage) {
        this.foodName = foodName;
        this.foodPrice = foodPrice;
        this.itemImage = itemImage;
    }

    public String getFoodName() {
        return foodName;
    }

    public String getFoodPrice() {
        return foodPrice;
    }

    public int getItemImage() {
        return itemImage;
    }
}
