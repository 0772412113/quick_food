package com.example.quick_food.recycler;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.quick_food.Adapters.CartViewAdapter;
import com.example.quick_food.GetterSetters.CartDetails;
import com.example.quick_food.PaypalPayment;
import com.example.quick_food.R;

import java.util.ArrayList;
import java.util.List;

public class CartView extends AppCompatActivity {

    RecyclerView mRecycleView;
    public static List<CartDetails> myCartList;
    CartDetails mcartData;
    static double totalValueForAllCart;
    static TextView mTxtTotalForCart;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.view_cart);
        getSupportActionBar().setTitle("Cart");

        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
        }

        mTxtTotalForCart = findViewById(R.id.tv_total);
        mRecycleView = (RecyclerView) findViewById(R.id.recycler_cart);
        if (myCartList == null || myCartList.isEmpty()) {
            myCartList = new ArrayList<>();
        }

        GridLayoutManager gridLayoutManager = new GridLayoutManager(CartView.this, 1);
        mRecycleView.setLayoutManager(gridLayoutManager);


        final String foodImage = getIntent().getStringExtra("image_Name");
        final String foodId = getIntent().getStringExtra("item_name");
        final String foodPrice = getIntent().getStringExtra("total_price");

        if (foodId != null) {
            mcartData = new CartDetails(foodId, foodPrice, foodImage);
            myCartList.add(mcartData);
        }

        method();

        CartViewAdapter myAdapter = new CartViewAdapter(CartView.this, myCartList);
        mRecycleView.setAdapter(myAdapter);
    }

    public void insertOrder(View view) {

        startActivity(new Intent(getApplicationContext(), PaypalPayment.class));
    }


    public static void method() {
        totalValueForAllCart = 0.0;
        for (int i = 0; i < myCartList.size(); i++) {
            double addonInInt = Double.parseDouble(myCartList.get(i).getFoodPrice());
            totalValueForAllCart = totalValueForAllCart + addonInInt;
        }

        mTxtTotalForCart.setText(String.valueOf(totalValueForAllCart));
    }


    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    public boolean onCreateOptionsMenu(Menu menu) {
        return true;
    }


}
