package com.example.quick_food.recycler;

import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.quick_food.Adapters.FoodDetailAdapter;
import com.example.quick_food.GetterSetters.FoodDetails;
import com.example.quick_food.OrderQueue;
import com.example.quick_food.QRCodeScanner;
import com.example.quick_food.R;
import com.example.quick_food.UserProfile;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.kaopiz.kprogresshud.KProgressHUD;

import java.util.ArrayList;
import java.util.List;

import static com.example.quick_food.Login.MY_PREFS_NAME;

public class Foods extends AppCompatActivity {

    RecyclerView fRecycleView;
    List<FoodDetails> myfoodDetailList;
    FoodDetails fFoodDetails;
    private boolean userIsVender = false;
    FirebaseFirestore db;
    FirebaseStorage storage;
    KProgressHUD progressHUD;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_foods);
        getSupportActionBar().setTitle("Select Food");

        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
        }

        fRecycleView = (RecyclerView) findViewById(R.id.recycleFoodView);

        progressHUD = KProgressHUD.create(Foods.this)
                .setStyle(KProgressHUD.Style.SPIN_INDETERMINATE)
                .setLabel("Please wait")
                .setDetailsLabel("Downloading data")
                .setCancellable(true)
                .setAnimationSpeed(2)
                .setDimAmount(0.5f);
        storage = FirebaseStorage.getInstance();

        GridLayoutManager gridLayoutManager = new GridLayoutManager(Foods.this, 1);
        fRecycleView.setLayoutManager(gridLayoutManager);



        setDatalist();

    }


    private void setDatalist() {
        progressHUD.show();
        db = FirebaseFirestore.getInstance();
        myfoodDetailList = new ArrayList<>();
        final String foodId = getIntent().getStringExtra("EXTRA_FOOD_ID");

        db.collection("Foods")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {

                                Log.d("Doc", document.getId() + " => " + document.getData());

                                if(document.getString("Type").equals(foodId)) {

                                    final String Title = document.getString("name");
                                    final String image = document.getString("image");
                                    final String description = document.getString("price");

                                    StorageReference storageRef = storage.getReference();
                                    StorageReference downloadRef = storageRef.child("Foods/" + image);
                                    downloadRef.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                                        @Override
                                        public void onSuccess(Uri uri) {
                                            Log.d("Doc1", " => " + uri);
                                            String imageURI = uri.toString();

                                            fFoodDetails = new FoodDetails(Title, description, imageURI);
                                            myfoodDetailList.add(fFoodDetails);

                                            FoodDetailAdapter foodDetailAdapter = new FoodDetailAdapter(Foods.this, myfoodDetailList);
                                            fRecycleView.setAdapter(foodDetailAdapter);
                                            progressHUD.dismiss();

                                        }
                                    }).addOnFailureListener(new OnFailureListener() {
                                        @Override
                                        public void onFailure(@NonNull Exception exception) {
                                            //   Toast.makeText(getActivity(), "Error getting Data", Toast.LENGTH_LONG).show();
                                            Log.d("Doc", "Error getting Data: ");
                                            progressHUD.dismiss();
                                        }
                                    });
                                }
                            }


                        } else {
                            Log.d("Doc", "Error getting documents: ", task.getException());
                            progressHUD.dismiss();
                        }
                    }
                });
    }

    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu, menu);
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

    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.cart_icon_menu) {
            Intent intent = new Intent(Foods.this, CartView.class);
            startActivity(intent);
            return true;
        } else if (id == R.id.profile_menu) {
            Intent intent = new Intent(Foods.this, UserProfile.class);
            startActivity(intent);
            return true;
        } else if (id == R.id.Orders) {
            Intent intent = new Intent(Foods.this, OrderQueue.class);
            startActivity(intent);
            return true;
        } else if (id == R.id.QR_scanner) {
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
