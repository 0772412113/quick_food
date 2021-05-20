package com.example.quick_food.recycler;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;
import android.widget.Toolbar;

import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.quick_food.Adapters.FoodCategoryAdapter;
import com.example.quick_food.FoodData;
import com.example.quick_food.OrderQueue;
import com.example.quick_food.QRCodeScanner;
import com.example.quick_food.R;
import com.example.quick_food.UserProfile;

import java.util.ArrayList;
import java.util.List;

import static com.example.quick_food.Login.MY_PREFS_NAME;

public class FoodCategory extends AppCompatActivity {


    RecyclerView mRecycleView;
    List<FoodData> myFoodList;
    FoodData mFoodData;
    private boolean userIsVender = false;
    private Toolbar toolbar;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_food_category);
        getSupportActionBar().setTitle("Select Category");

        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
        }

        mRecycleView = (RecyclerView) findViewById(R.id.recycleView);


        GridLayoutManager gridLayoutManager = new GridLayoutManager(FoodCategory.this, 1);
        mRecycleView.setLayoutManager(gridLayoutManager);

        myFoodList = new ArrayList<>();

        String uniID = getIntent().getStringExtra("EXTRA_UNI_ID");

        if (uniID.equals("NSBM")) {

            mFoodData = new FoodData("Noodles", "Delicious Noodles", R.drawable.image1);
            myFoodList.add(mFoodData);

            mFoodData = new FoodData("Fried Rice", "Best Food", R.drawable.image2);
            myFoodList.add(mFoodData);

            mFoodData = new FoodData("Kottu", "Yammyy", R.drawable.image3);
            myFoodList.add(mFoodData);

            mFoodData = new FoodData("Drinks", "Get Fresh now", R.drawable.image5);
            myFoodList.add(mFoodData);

            mFoodData = new FoodData("Buns", "Many Snacks", R.drawable.image4);
            myFoodList.add(mFoodData);

        } else if (uniID.equals("CINEC")) {

            mFoodData = new FoodData("Drinks", "Get Fresh now", R.drawable.image5);
            myFoodList.add(mFoodData);

            mFoodData = new FoodData("Noodles", "Delicious Noodles", R.drawable.image1);
            myFoodList.add(mFoodData);

            mFoodData = new FoodData("Kottu", "Yammyy", R.drawable.image3);
            myFoodList.add(mFoodData);

            mFoodData = new FoodData("Fried Rice", "Best Food", R.drawable.image2);
            myFoodList.add(mFoodData);

            mFoodData = new FoodData("Buns", "Many Snacks", R.drawable.image4);
            myFoodList.add(mFoodData);

        } else if (uniID.equals("SLIIT")) {

            mFoodData = new FoodData("Noodles", "Delicious Noodles", R.drawable.image1);
            myFoodList.add(mFoodData);

            mFoodData = new FoodData("Fried Rice", "Best Food", R.drawable.image2);
            myFoodList.add(mFoodData);

            mFoodData = new FoodData("Kottu", "Yammyy", R.drawable.image3);
            myFoodList.add(mFoodData);

            mFoodData = new FoodData("Drinks", "Get Fresh now", R.drawable.image5);
            myFoodList.add(mFoodData);

            mFoodData = new FoodData("Buns", "Many Snacks", R.drawable.image4);
            myFoodList.add(mFoodData);

        } else if (uniID.equals("IIT")) {

            mFoodData = new FoodData("Fried Rice", "Best Food", R.drawable.image2);
            myFoodList.add(mFoodData);

            mFoodData = new FoodData("Noodles", "Delicious Noodles", R.drawable.image1);
            myFoodList.add(mFoodData);

            mFoodData = new FoodData("Kottu", "Yammyy", R.drawable.image3);
            myFoodList.add(mFoodData);

            mFoodData = new FoodData("Drinks", "Get Fresh now", R.drawable.image5);
            myFoodList.add(mFoodData);

            mFoodData = new FoodData("Buns", "Many Snacks", R.drawable.image4);
            myFoodList.add(mFoodData);

        }


        FoodCategoryAdapter foodCategoryAdapter = new FoodCategoryAdapter(FoodCategory.this, myFoodList);
        mRecycleView.setAdapter(foodCategoryAdapter);


    }

    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu,menu);
        return true;
    }

    public boolean onPrepareOptionsMenu(Menu menu) {
        final SharedPreferences prefs = getSharedPreferences(MY_PREFS_NAME, MODE_PRIVATE);
        String theVender = prefs.getString("userIsVender", "");


        MenuItem QRScanner = menu.findItem(R.id.QR_scanner);
        MenuItem OrderQueue = menu.findItem(R.id.Orders);

        if (!theVender.equals("")) {
            userIsVender = true;

            QRScanner.setVisible(true);
            OrderQueue.setVisible(false);

        }
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();

        if (id == R.id.cart_icon_menu){
            Intent intent = new Intent(FoodCategory.this,CartView.class);
            startActivity(intent);
            return true;
        }else if (id == R.id.Orders){
            Intent intent = new Intent(FoodCategory.this, OrderQueue.class);
            startActivity(intent);
            return true;
        }else if (id == R.id.profile_menu){
            Intent intent = new Intent(FoodCategory.this, UserProfile.class);
            startActivity(intent);
            return true;
        }else if (id == R.id.QR_scanner){
            Intent intent = new Intent(FoodCategory.this, QRCodeScanner.class);
            startActivity(intent);
            return true;
        }
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
