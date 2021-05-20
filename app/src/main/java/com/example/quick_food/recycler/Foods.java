package com.example.quick_food.recycler;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import com.example.quick_food.Adapters.FoodDetailAdapter;
import com.example.quick_food.FoodDetails;
import com.example.quick_food.OrderQueue;
import com.example.quick_food.QRCodeScanner;
import com.example.quick_food.R;
import com.example.quick_food.Universities;
import com.example.quick_food.UserProfile;

import java.util.ArrayList;
import java.util.List;

import static com.example.quick_food.Login.MY_PREFS_NAME;

public class Foods extends AppCompatActivity {

    RecyclerView fRecycleView;
    List<FoodDetails> myfoodDetailList;
    FoodDetails fFoodDetails;
    private boolean userIsVender = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_foods);
        getSupportActionBar().setTitle("Select Food");

        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
        }

        fRecycleView = (RecyclerView)findViewById(R.id.recycleFoodView);

        GridLayoutManager gridLayoutManager = new GridLayoutManager(Foods.this,1);
        fRecycleView.setLayoutManager(gridLayoutManager);

        myfoodDetailList = new ArrayList<>();

        String foodId = getIntent().getStringExtra("EXTRA_FOOD_ID");

        if(foodId.equals("Noodles")) {

            fFoodDetails = new FoodDetails("Chicken nooddles", "200", R.drawable.chicknoddle);
            myfoodDetailList.add(fFoodDetails);

            fFoodDetails = new FoodDetails("Vegitable nooddles", "120 ", R.drawable.vegnoddle);
            myfoodDetailList.add(fFoodDetails);

            fFoodDetails = new FoodDetails("Egg nooddles", "160", R.drawable.eggnoddle);
            myfoodDetailList.add(fFoodDetails);

        }else if(foodId.equals("Fried Rice")){
            fFoodDetails = new FoodDetails("Chicken Rice", "250", R.drawable.chickrice);
            myfoodDetailList.add(fFoodDetails);

            fFoodDetails = new FoodDetails("Vegitable Rice", "220 ", R.drawable.vegrice);
            myfoodDetailList.add(fFoodDetails);

            fFoodDetails = new FoodDetails("Egg Rice", "1260", R.drawable.eggrice);
            myfoodDetailList.add(fFoodDetails);

        }else if(foodId.equals("Kottu")){

            fFoodDetails = new FoodDetails("Chicken Kottu", "250", R.drawable.chickottu);
            myfoodDetailList.add(fFoodDetails);

            fFoodDetails = new FoodDetails("Vegitable Kottu", "150 ", R.drawable.vegkottu);
            myfoodDetailList.add(fFoodDetails);

            fFoodDetails = new FoodDetails("Egg Kottu", "100", R.drawable.eggkottu);
            myfoodDetailList.add(fFoodDetails);

        }else if(foodId.equals("Drinks")) {

            fFoodDetails = new FoodDetails("Tea", "250", R.drawable.tea);
            myfoodDetailList.add(fFoodDetails);

            fFoodDetails = new FoodDetails("MlikShake", "150 ", R.drawable.milkshake);
            myfoodDetailList.add(fFoodDetails);

            fFoodDetails = new FoodDetails("Soft Drinks", "100", R.drawable.softdrinks);
            myfoodDetailList.add(fFoodDetails);

        }else{

            fFoodDetails = new FoodDetails("Submarine", "250", R.drawable.submarine);
            myfoodDetailList.add(fFoodDetails);

            fFoodDetails = new FoodDetails("Hot Dog", "150 ", R.drawable.hotdog);
            myfoodDetailList.add(fFoodDetails);

            fFoodDetails = new FoodDetails("Sandwitch", "100", R.drawable.sandwitch);
            myfoodDetailList.add(fFoodDetails);

            fFoodDetails = new FoodDetails("Burger", "100", R.drawable.chicburger);
            myfoodDetailList.add(fFoodDetails);

        }

        FoodDetailAdapter foodDetailAdapter = new FoodDetailAdapter(Foods.this,myfoodDetailList);
        fRecycleView.setAdapter(foodDetailAdapter);

    }

    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu,menu);
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

    public boolean onOptionsItemSelected(MenuItem item){
        int id = item.getItemId();
        if (id == R.id.cart_icon_menu){
            Intent intent = new Intent(Foods.this,CartView.class);
            startActivity(intent);
            return true;
        }else if (id == R.id.profile_menu) {
            Intent intent = new Intent(Foods.this, UserProfile.class);
            startActivity(intent);
            return true;
        }else if (id == R.id.Orders){
                Intent intent = new Intent(Foods.this, OrderQueue.class);
                startActivity(intent);
                return true;
        }else if (id == R.id.QR_scanner){
            Intent intent = new Intent(Foods.this, QRCodeScanner.class);
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
