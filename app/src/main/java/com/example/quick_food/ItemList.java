package com.example.quick_food;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.provider.ContactsContract;

import com.example.quick_food.R;
import com.google.firebase.firestore.auth.User;

public class ItemList extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_item_list);
    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(ItemList.this, UserProfile.class);
        startActivity(intent);
        finish();
    }
}