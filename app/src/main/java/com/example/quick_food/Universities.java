package com.example.quick_food;

public class Universities {

    private String UniName;
    private String itemImage;

    public Universities(String uniName, String itemImage) {
        UniName = uniName;
        this.itemImage = itemImage;
    }

    public String getUniName() {
        return UniName;
    }

    public String getItemImage() {
        return itemImage;
    }
}
