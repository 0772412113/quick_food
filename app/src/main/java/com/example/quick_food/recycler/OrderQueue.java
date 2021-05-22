package com.example.quick_food.recycler;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.Button;

import com.example.quick_food.Adapters.OrderQueueAdapter;
import com.example.quick_food.GetterSetters.OrderDetails;
import com.example.quick_food.R;

import java.util.ArrayList;
import java.util.List;

import static com.example.quick_food.Login.MY_PREFS_NAME;
import static com.example.quick_food.UserProfile.myOrderDetails;

public class OrderQueue extends AppCompatActivity {


    Button orderHistory, ongoinOrders;
    RecyclerView mRecycleView;
    List<OrderDetails> orderDetailsList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_queue);

        orderHistory = (Button) findViewById(R.id.order_history);
        ongoinOrders = (Button) findViewById(R.id.ongoing_orders);

        mRecycleView = (RecyclerView) findViewById(R.id.recycler_Order_queue);
        GridLayoutManager gridLayoutManager = new GridLayoutManager(OrderQueue.this, 1);
        mRecycleView.setLayoutManager(gridLayoutManager);
        SharedPreferences prefs = getSharedPreferences(MY_PREFS_NAME, MODE_PRIVATE);

        orderDetailsList = new ArrayList<>();
        if (myOrderDetails != null) {
            for (int i = 0; i < myOrderDetails.size(); i++) {
                if (myOrderDetails.get(i).getUser().equals(prefs.getString("loggedUserId", ""))) {
                    orderDetailsList.add(myOrderDetails.get(i));
                }
            }
            OrderQueueAdapter myAdapter = new OrderQueueAdapter(OrderQueue.this, orderDetailsList);
            mRecycleView.setAdapter(myAdapter);
        }
    }

}