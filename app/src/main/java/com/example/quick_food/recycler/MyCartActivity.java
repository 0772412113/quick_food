package com.example.quick_food.recycler;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.AuthFailureError;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.example.quick_food.Adapters.CartViewAdapter;
import com.example.quick_food.Firebase.MySingleton;
import com.example.quick_food.GetterSetters.CartDetails;
import com.example.quick_food.R;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.kaopiz.kprogresshud.KProgressHUD;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import static com.example.quick_food.Login.MY_PREFS_NAME;

public class MyCartActivity extends AppCompatActivity {

    RecyclerView mRecycleView;
    public static List<CartDetails> myCartList;
    CartDetails mcartData;
    static double totalValueForAllCart;
    static TextView mTxtTotalForCart;
    FirebaseFirestore db;
    SharedPreferences prefs;

    public static final String FCM_API = "https://fcm.googleapis.com/fcm/send";
    public static final String serverKey = "key=" + "AAAAmwlrfl4:APA91bE0OQBsXfcPHZRUqJfseLoJIdmSESfKoRybHwY2CfJ8-mQzdIgbE81OQ4y-HUSrHkG0jAvD9JR9GLxUgse37719UvTTXRwHjq6hY6rwefBMTyMHZnbL6Knt6RDRyx8NmeVnPkEJ";
    String contentType = "application/json";
    String TAG = "NOTIFICATION TAG";

    String NOTIFICATION_TITLE;
    String NOTIFICATION_MESSAGE;
    String TOPIC;
    KProgressHUD progressHUD;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.view_cart);
        getSupportActionBar().setTitle("My Cart");

        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
        }


        progressHUD = KProgressHUD.create(MyCartActivity.this)
                .setStyle(KProgressHUD.Style.SPIN_INDETERMINATE)
                .setLabel("Please wait")
                .setDetailsLabel("We are generating your order.")
                .setCancellable(true)
                .setAnimationSpeed(2)
                .setDimAmount(0.5f);

        db = FirebaseFirestore.getInstance();
        prefs = getSharedPreferences(MY_PREFS_NAME, MODE_PRIVATE);

        mTxtTotalForCart = findViewById(R.id.tv_total);
        mRecycleView = (RecyclerView) findViewById(R.id.recycler_cart);
        if (myCartList == null || myCartList.isEmpty()) {
            myCartList = new ArrayList<>();
        }

        GridLayoutManager gridLayoutManager = new GridLayoutManager(MyCartActivity.this, 1);
        mRecycleView.setLayoutManager(gridLayoutManager);


        final String foodImage = getIntent().getStringExtra("image_Name");
        final String foodName = getIntent().getStringExtra("item_name");
        final String foodPrice = getIntent().getStringExtra("total_price");
        final String foodId = getIntent().getStringExtra("item_id");

        if (foodId != null) {
            mcartData = new CartDetails(foodId, foodName, foodPrice, foodImage);
            myCartList.add(mcartData);
        }

        method();

        CartViewAdapter myAdapter = new CartViewAdapter(MyCartActivity.this, myCartList);
        mRecycleView.setAdapter(myAdapter);
    }

    public void insertOrder(View view) {

        progressHUD.show();

        final String docPath = UUID.randomUUID().toString().replace("-", "");

        Map<String, Object> get_data = new HashMap<>();
        get_data.put("Status", "P");
        get_data.put("userId", prefs.getString("loggedUserId", ""));

        for (int i = 0; i < myCartList.size(); i++) {
            get_data.put(myCartList.get(i).getFoodId(), "0");
        }

        Log.e("DocPath >>", docPath);

        db.collection("OrderDetails").document(docPath).set(get_data)

                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {

                        Toast.makeText(MyCartActivity.this, "Data successfully written! update", Toast.LENGTH_LONG).show();

                        TOPIC = "/topics/ToADMIN";
                        NOTIFICATION_TITLE = "New Order";
                        NOTIFICATION_MESSAGE = "New Order received. Please approve or reject it.";

                        JSONObject notification = new JSONObject();
                        JSONObject notifcationBody = new JSONObject();
                        try {
                            notifcationBody.put("title", NOTIFICATION_TITLE);
                            notifcationBody.put("message", NOTIFICATION_MESSAGE);
                            notifcationBody.put("orderId", docPath);

                            notification.put("to", TOPIC);
                            notification.put("data", notifcationBody);
                        } catch (JSONException e) {
                            Log.e(TAG, "onCreate: " + e.getMessage());
                        }
                        sendNotification(notification);


                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        progressHUD.dismiss();
                        Toast.makeText(MyCartActivity.this, "Data writing Error update", Toast.LENGTH_LONG).show();

                    }
                });
    }


    private void sendNotification(JSONObject notification) {

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(FCM_API, notification,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        progressHUD.dismiss();
                        Toast.makeText(MyCartActivity.this, "Successfully sent", Toast.LENGTH_LONG).show();
                        Log.i(TAG, "onResponse: " + response.toString());
                        Intent intent = new Intent(MyCartActivity.this, MainFoodCategoryActivity.class);
                        startActivity(intent);
                        finish();


                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        progressHUD.dismiss();
                        Toast.makeText(MyCartActivity.this, "Request error", Toast.LENGTH_LONG).show();
                        Log.i(TAG, "onErrorResponse: Didn't work");
                    }
                }) {
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("Authorization", serverKey);
                params.put("Content-Type", contentType);
                return params;
            }
        };
        MySingleton.getInstance(getApplicationContext()).addToRequestQueue(jsonObjectRequest);
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
