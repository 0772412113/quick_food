package com.example.quick_food.recycler;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;

import com.example.quick_food.Adapters.AdapterForUniversities;
import com.example.quick_food.R;
import com.example.quick_food.Universities;
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


public class PlacesToBuy extends AppCompatActivity {

    RecyclerView uRecycleView;
    List<Universities> myUniversityList;
    Universities uUName;
    FirebaseFirestore db;
    FirebaseStorage storage;
    KProgressHUD progressHUD;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_places_to_buy);



        uRecycleView = (RecyclerView)findViewById(R.id.recycleUniView);
        progressHUD = KProgressHUD.create(PlacesToBuy.this)
                .setStyle(KProgressHUD.Style.SPIN_INDETERMINATE)
                .setLabel("Please wait")
                .setDetailsLabel("Downloading data")
                .setCancellable(true)
                .setAnimationSpeed(2)
                .setDimAmount(0.5f);
        storage = FirebaseStorage.getInstance();

        GridLayoutManager gridLayoutManager = new GridLayoutManager(PlacesToBuy.this,1);
        uRecycleView.setLayoutManager(gridLayoutManager);

        setDatalist();
    }

    private void setDatalist() {
        progressHUD.show();
        db = FirebaseFirestore.getInstance();
        myUniversityList = new ArrayList<>();

        db.collection("Uni_data")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {

                                Log.d("Doc", document.getId() + " => " + document.getData());

                                final String Title = document.getString("Title");
                                final String image = document.getString("image");

                                StorageReference storageRef = storage.getReference();
                                StorageReference downloadRef = storageRef.child("images/" + image);
                                downloadRef.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                                    @Override
                                    public void onSuccess(Uri uri) {
                                        Log.d("Doc1", " => " + uri);
                                        String asd = uri.toString();
                                        uUName = new Universities(Title,asd);
                                        myUniversityList.add(uUName);
                                        AdapterForUniversities adapterForUniversities = new AdapterForUniversities(PlacesToBuy.this,myUniversityList);
                                        uRecycleView.setAdapter(adapterForUniversities);
                                        progressHUD.dismiss();

                                    }
                                }).addOnFailureListener(new OnFailureListener() {
                                    @Override
                                    public void onFailure(@NonNull Exception exception) {
                                        //   Toast.makeText(getActivity(), "Error getting Data", Toast.LENGTH_LONG).show();
                                    }
                                });

                            }


                        } else {
                            Log.d("Doc", "Error getting documents: ", task.getException());
                            // Toast.makeText(getActivity(), "Error getting Data", Toast.LENGTH_LONG).show();
                        }
                    }
                });
    }

}
