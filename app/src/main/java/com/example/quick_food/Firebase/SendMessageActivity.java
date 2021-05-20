package com.example.quick_food.Firebase;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.AuthFailureError;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.example.quick_food.R;
import com.kaopiz.kprogresshud.KProgressHUD;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

//import static com.example.quick_food.MainActivity.myHelmetDetails;

public class SendMessageActivity extends AppCompatActivity {

    final private String FCM_API = "https://fcm.googleapis.com/fcm/send";
    final private String serverKey = "key=" + "AAAAmwlrfl4:APA91bE0OQBsXfcPHZRUqJfseLoJIdmSESfKoRybHwY2CfJ8-mQzdIgbE81OQ4y-HUSrHkG0jAvD9JR9GLxUgse37719UvTTXRwHjq6hY6rwefBMTyMHZnbL6Knt6RDRyx8NmeVnPkEJ";
    final private String contentType = "application/json";
    final String TAG = "NOTIFICATION TAG";

    String NOTIFICATION_TITLE;
    String NOTIFICATION_MESSAGE;
    String TOPIC;
    KProgressHUD progressHUD;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_send_message);

        Button btnSend = findViewById(R.id.btnSend);
        TextView helmetIdText = findViewById(R.id.notification_title_text);
        TextView checkInText = findViewById(R.id.checkin_time_text);
        TextView receiverIDText = findViewById(R.id.receiver_id_text);

        progressHUD = KProgressHUD.create(SendMessageActivity.this)
                .setStyle(KProgressHUD.Style.SPIN_INDETERMINATE)
                .setLabel("Please wait")
                .setDetailsLabel("Downloading data")
                .setCancellable(true)
                .setAnimationSpeed(2)
                .setDimAmount(0.5f);

        final String helmetId = getIntent().getStringExtra("SCANED_HELMET_ID");
        helmetIdText.setText("Helmet Id : " + helmetId);


        String userName = "";
//        if (myHelmetDetails != null) {
//            for (int i = 0; i < myHelmetDetails.size(); i++) {
//
//                if(myHelmetDetails.get(i).getHid().equals(helmetId)){
//                    userName = myHelmetDetails.get(i).getUser();
//                }
//            }
//
//        }

        receiverIDText.setText("Receiver Id : " + userName);

        Calendar c = Calendar.getInstance();
        SimpleDateFormat df = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss a");
        final String formattedDate = df.format(c.getTime());

        checkInText.setText("Current Time : " + formattedDate);

        final String finalUserName = userName;
        btnSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (!finalUserName.equals("")) {
                    TOPIC = "/topics/" + finalUserName;
                    NOTIFICATION_TITLE = "Helmet Storage";
                    NOTIFICATION_MESSAGE = helmetId;

                    JSONObject notification = new JSONObject();
                    JSONObject notifcationBody = new JSONObject();
                    try {
                        notifcationBody.put("title", NOTIFICATION_TITLE);
                        notifcationBody.put("message", NOTIFICATION_MESSAGE);
                        notifcationBody.put("checkIN", formattedDate);

                        notification.put("to", TOPIC);
                        notification.put("data", notifcationBody);
                    } catch (JSONException e) {
                        Log.e(TAG, "onCreate: " + e.getMessage());
                    }
                    sendNotification(notification);
                } else {
                    Toast.makeText(SendMessageActivity.this, "Error! No User found!", Toast.LENGTH_LONG).show();
                }
            }
        });


    }

    private void sendNotification(JSONObject notification) {
        progressHUD.show();
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(FCM_API, notification,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        progressHUD.dismiss();
                        Toast.makeText(SendMessageActivity.this, "Successfully sent", Toast.LENGTH_LONG).show();
                        Log.i(TAG, "onResponse: " + response.toString());

                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        progressHUD.dismiss();
                        Toast.makeText(SendMessageActivity.this, "Request error", Toast.LENGTH_LONG).show();
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
}
