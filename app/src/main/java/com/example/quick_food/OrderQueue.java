package com.example.quick_food;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;

public class OrderQueue extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_queue);
    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(OrderQueue.this, UserProfile.class);
        startActivity(intent);
        finish();
    }
}