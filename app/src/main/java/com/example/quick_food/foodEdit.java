package com.example.quick_food;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatSpinner;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;

import java.util.ArrayList;
import java.util.List;

public class foodEdit extends AppCompatActivity implements View.OnClickListener {

    Button addSizesButton, addAdderButton;
    LinearLayout foodSizeLayoutList, foodAdderLayout;
    List<String> foodSizeList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_food_edit);

        foodSizeLayoutList = findViewById(R.id.sizeSelect);
        addSizesButton = findViewById(R.id.addAvailableSizes);

        foodAdderLayout = findViewById(R.id.adderSelect);
        addAdderButton = findViewById(R.id.addAvailableAdders);

        addSizesButton.setOnClickListener(this);
        foodSizeList.add("Regular");
        foodSizeList.add("Medium");
        foodSizeList.add("Large");

        addAdderButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addAddderView();
            }

            private void addAddderView() {

                final View foodAdder = getLayoutInflater().inflate(R.layout.raw_add_adders,null,false);
                EditText editAdderName = (EditText)foodAdder.findViewById(R.id.adderName);
                EditText editAdderPrice = (EditText)foodAdder.findViewById(R.id.adderPrice);
                ImageView imageAdderClose = (ImageView)foodAdder.findViewById(R.id.closeAdderImage);

                imageAdderClose.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        removeAdderView(foodAdder);
                    }
                });

                foodAdderLayout.addView(foodAdder);
            }
            private void removeAdderView(View view){
                foodAdderLayout.removeView(view);
            }
        });
    }

    @Override
    public void onClick(View view) {
        addView();
    }

    private void addView() {

        //foodSizes
        final View foodSize = getLayoutInflater().inflate(R.layout.raw_add_sizes,null,false);
        AppCompatSpinner spinnerSize = (AppCompatSpinner)foodSize.findViewById(R.id.foodSizeSpinner);
        EditText editSizePrice = (EditText)foodSize.findViewById(R.id.sizePrice);
        ImageView imageSizeClose = (ImageView)foodSize.findViewById(R.id.closeSizeImage);

        ArrayAdapter arrayAdapter = new ArrayAdapter(this, android.R.layout.simple_spinner_item, foodSizeList);
        spinnerSize.setAdapter(arrayAdapter);

        imageSizeClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                removeSizeView(foodSize);
            }
        });
        foodSizeLayoutList.addView(foodSize);

    }

    private void removeSizeView(View view){

        foodSizeLayoutList.removeView(view);


    }


    @Override
    public void onBackPressed() {
        Intent intent = new Intent(foodEdit.this, UserProfile.class);
        startActivity(intent);
        finish();
    }


}