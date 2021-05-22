package com.example.quick_food;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.AuthFailureError;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.example.quick_food.Firebase.MySingleton;
import com.example.quick_food.recycler.MainFoodCategoryActivity;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.kaopiz.kprogresshud.KProgressHUD;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import static com.example.quick_food.recycler.MyCartActivity.FCM_API;
import static com.example.quick_food.recycler.MyCartActivity.serverKey;

public class OrderConfirmActivity extends AppCompatActivity {

    TextView confirmId;
    Button approvalBtn, rejectBtn, doneBtn;
    FirebaseFirestore db;
    String NOTIFICATION_TITLE;
    String NOTIFICATION_MESSAGE;
    String TOPIC;
    KProgressHUD progressHUD;
    String TAG = "NOTIFICATION TAG";
    String contentType = "application/json";
    String userId;
    String orderId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_comfirm);
        getSupportActionBar().setTitle("User Request");

        confirmId = findViewById(R.id.confirm_id);
        approvalBtn = findViewById(R.id.approvalButon);
        rejectBtn = findViewById(R.id.rejectButon);
        doneBtn = findViewById(R.id.doneButon);


        if (getIntent().getStringExtra("NEW_ORDER_ID") != null) {
            orderId = getIntent().getStringExtra("NEW_ORDER_ID");
        } else {
            orderId = getIntent().getStringExtra("NEW_ORDER_ID_QR");
            LinearLayout approveLayout = findViewById(R.id.layoutApprove);
            approveLayout.setVisibility(View.GONE);
            doneBtn.setVisibility(View.VISIBLE);
        }

        confirmId.setText("Order Id : " + orderId);

        db = FirebaseFirestore.getInstance();


        progressHUD = KProgressHUD.create(OrderConfirmActivity.this)
                .setStyle(KProgressHUD.Style.SPIN_INDETERMINATE)
                .setLabel("Please wait")
                .setDetailsLabel("We are generating your order.")
                .setCancellable(true)
                .setAnimationSpeed(2)
                .setDimAmount(0.5f);

        progressHUD.show();
        db = FirebaseFirestore.getInstance();

        db.collection("OrderDetails")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {

                                Log.d("Doc", document.getId() + " => " + document.getData());

                                if (document.getId().equals(orderId)) {
                                    userId = document.getString("userId");
                                }
                                progressHUD.dismiss();
                            }


                        } else {
                            Log.d("Doc", "Error getting documents: ", task.getException());
                            progressHUD.dismiss();
                        }
                    }
                });


        approvalBtn.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {

                progressHUD.show();

                Map<String, Object> get_data = new HashMap<>();
                get_data.put("Status", "A");


                Log.e("DocPath >>", orderId);

                db.collection("OrderDetails").document(orderId).update(get_data)

                        .addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void aVoid) {

                                Toast.makeText(OrderConfirmActivity.this, "Data successfully written! update", Toast.LENGTH_LONG).show();

                                TOPIC = "/topics/" + userId.toString();
                                NOTIFICATION_TITLE = "Approved Order";
                                NOTIFICATION_MESSAGE = "Your Order has been approved. Click here to see details";

                                JSONObject notification = new JSONObject();
                                JSONObject notifcationBody = new JSONObject();
                                try {
                                    notifcationBody.put("title", NOTIFICATION_TITLE);
                                    notifcationBody.put("message", NOTIFICATION_MESSAGE);
                                    notifcationBody.put("orderId", orderId);

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
                                Toast.makeText(OrderConfirmActivity.this, "Data writing Error update", Toast.LENGTH_LONG).show();

                            }
                        });
            }
        });


        doneBtn.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {

                progressHUD.show();

                Map<String, Object> get_data = new HashMap<>();
                get_data.put("Status", "D");


                Log.e("DocPath >>", orderId);

                db.collection("OrderDetails").document(orderId).update(get_data)

                        .addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void aVoid) {

                                Toast.makeText(OrderConfirmActivity.this, "Data successfully written! update", Toast.LENGTH_LONG).show();

                                TOPIC = "/topics/" + userId.toString();
                                NOTIFICATION_TITLE = "Order picked";
                                NOTIFICATION_MESSAGE = "Your Order has been Picked. Click here to see details";

                                JSONObject notification = new JSONObject();
                                JSONObject notifcationBody = new JSONObject();
                                try {
                                    notifcationBody.put("title", NOTIFICATION_TITLE);
                                    notifcationBody.put("message", NOTIFICATION_MESSAGE);
                                    notifcationBody.put("orderId", orderId);

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
                                Toast.makeText(OrderConfirmActivity.this, "Data writing Error update", Toast.LENGTH_LONG).show();

                            }
                        });
            }
        });

        rejectBtn.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {

                progressHUD.show();

                Map<String, Object> get_data = new HashMap<>();
                get_data.put("Status", "R");


                Log.e("DocPath >>", orderId);

                db.collection("OrderDetails").document(orderId).update(get_data)

                        .addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void aVoid) {

                                Toast.makeText(OrderConfirmActivity.this, "Data successfully written! update", Toast.LENGTH_LONG).show();

                                TOPIC = "/topics/" + userId.toString();
                                NOTIFICATION_TITLE = "Rejected Order";
                                NOTIFICATION_MESSAGE = "Your Order has been rejected. Click here to see details";

                                JSONObject notification = new JSONObject();
                                JSONObject notifcationBody = new JSONObject();
                                try {
                                    notifcationBody.put("title", NOTIFICATION_TITLE);
                                    notifcationBody.put("message", NOTIFICATION_MESSAGE);
                                    notifcationBody.put("orderId", orderId);

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
                                Toast.makeText(OrderConfirmActivity.this, "Data writing Error update", Toast.LENGTH_LONG).show();

                            }
                        });
            }
        });
    }

    private void sendNotification(JSONObject notification) {

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(FCM_API, notification,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        progressHUD.dismiss();
                        Toast.makeText(OrderConfirmActivity.this, "Successfully sent", Toast.LENGTH_LONG).show();
                        Log.i(TAG, "onResponse: " + response.toString());
                        Intent intent = new Intent(OrderConfirmActivity.this, MainFoodCategoryActivity.class);
                        startActivity(intent);
                        finish();


                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        progressHUD.dismiss();
                        Toast.makeText(OrderConfirmActivity.this, "Request error", Toast.LENGTH_LONG).show();
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


    @Override
    public void onBackPressed() {
        Intent intent = new Intent(OrderConfirmActivity.this, MainFoodCategoryActivity.class);
        startActivity(intent);
        finish();
    }

}
