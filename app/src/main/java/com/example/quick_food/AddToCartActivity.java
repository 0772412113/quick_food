package com.example.quick_food;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.quick_food.recycler.MyCartActivity;
import com.squareup.picasso.Picasso;

public class AddToCartActivity extends AppCompatActivity {

    TextView mTitle, mTxtOneOne, mTxtOneTwo, mTxtTwoOne, mTxtTwoTwo, mTxtTwoThree, mTxtTotal;
    ImageView imageViewCart;
    Button buttoAddCart;
    private RadioGroup radioGroupOne, radioGroupTwo;
    Spinner spinnerCount;

    String[] size = {"1", "2", "3", "4"};
    ArrayAdapter<String> adapter;

    double totalValue, groupOneVal, groupTwoVal, priceDouble;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.cart_layout);

        final String foodImage = getIntent().getStringExtra("FOOD_IMAGE_FOR_CART");
        final String foodId = getIntent().getStringExtra("FOOD_ID_FOR_CART");
        final String foodName = getIntent().getStringExtra("FOOD_NAME_FOR_CART");
        final String price = getIntent().getStringExtra("FOOD_PRICE_FOR_CART");

        mTitle = findViewById(R.id.txt_cart_product_name);
        mTxtOneOne = findViewById(R.id.text_option_one_one);
        mTxtOneTwo = findViewById(R.id.text_option_one_two);
        mTxtTwoOne = findViewById(R.id.text_option_two_one);
        mTxtTwoTwo = findViewById(R.id.text_option_two_two);
        mTxtTwoThree = findViewById(R.id.text_option_two_three);
        mTxtTotal = findViewById(R.id.total_txt);
        imageViewCart = findViewById(R.id.img_cart_product);
        buttoAddCart = findViewById(R.id.cart_submit);
        spinnerCount = (Spinner) findViewById(R.id.spinner_count);


        double priceInInt = Double.parseDouble(price);
        priceDouble = priceInInt;

        mTitle.setText(foodName);
        mTxtTotal.setText(String.valueOf(priceDouble));

        Picasso.get()
                .load(foodImage)
                .placeholder(R.drawable.walking_food)
                .into(imageViewCart);


        adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, size);
        spinnerCount.setAdapter(adapter);

        radioGroupOne = (RadioGroup) findViewById(R.id.radio_group_one);
        radioGroupTwo = (RadioGroup) findViewById(R.id.radio_group_two);

        radioGroupOne.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            public void onCheckedChanged(RadioGroup group, int checkedId) {

                if (checkedId == R.id.fd_sizeL) {

                    double addonInInt = Double.parseDouble(String.valueOf(mTxtOneTwo.getText()));
                    groupOneVal = addonInInt;
                    totalValue = groupOneVal + groupTwoVal + priceDouble;
                    mTxtTotal.setText(String.valueOf(totalValue));
                } else {
                    double addonInInt = Double.parseDouble(String.valueOf(mTxtOneOne.getText()));
                    groupOneVal = addonInInt;
                    totalValue = groupOneVal + groupTwoVal + priceDouble;
                    mTxtTotal.setText(String.valueOf(totalValue));
                }
            }
        });

        radioGroupTwo.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            public void onCheckedChanged(RadioGroup group, int checkedId) {

                if (checkedId == R.id.f_add_cheese) {
                    double addonInInt = Double.parseDouble(String.valueOf(mTxtTwoTwo.getText()));
                    groupTwoVal = addonInInt;
                    totalValue = groupOneVal + groupTwoVal + priceDouble;
                    mTxtTotal.setText(String.valueOf(totalValue));
                } else if (checkedId == R.id.f_add_chicken) {
                    double addonInInt = Double.parseDouble(String.valueOf(mTxtTwoThree.getText()));
                    groupTwoVal = addonInInt;
                    totalValue = groupOneVal + groupTwoVal + priceDouble;
                    mTxtTotal.setText(String.valueOf(totalValue));
                } else {
                    double addonInInt = Double.parseDouble(String.valueOf(mTxtTwoOne.getText()));
                    groupTwoVal = addonInInt;
                    totalValue = groupOneVal + groupTwoVal + priceDouble;
                    mTxtTotal.setText(String.valueOf(totalValue));
                }
            }
        });

        buttoAddCart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                double finalPrice = 0.0;
                if (totalValue == 0) {
                    finalPrice = priceDouble;
                } else {
                    finalPrice = totalValue;
                }
                Intent intent = new Intent(AddToCartActivity.this, MyCartActivity.class);
                intent.putExtra("image_Name", foodImage);
                intent.putExtra("total_price", String.valueOf(finalPrice));
                intent.putExtra("item_name", foodName);
                intent.putExtra("item_id", foodId);
                startActivity(intent);
                finish();
            }
        });
    }

}
