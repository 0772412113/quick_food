package com.example.quick_food.GetterSetters;

public class CartDetails {

    private String cartId;
    private String foodName;
    private String foodPrice;
    private String itemImage;

    public CartDetails(String foodName, String foodPrice, String itemImage) {
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

    public String getItemImage() {
        return itemImage;
    }
}
