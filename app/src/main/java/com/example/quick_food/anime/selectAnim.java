package com.example.quick_food.anime;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;

import com.example.quick_food.recycler.PlacesToBuy;
import com.example.quick_food.R;

import static java.lang.Thread.sleep;

public class selectAnim extends AppCompatActivity {

    ImageView welcomeImage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_anim);

        welcomeImage=(ImageView) findViewById(R.id.imagewelcome1);

        Animation welcome= AnimationUtils.loadAnimation(this,R.anim.welcomeanimation);
        welcomeImage.startAnimation(welcome);

        Thread myTread=new Thread(new Runnable() {
            @Override
            public void run() {

                try {
                    sleep(3000);

                    Intent i=new Intent(selectAnim.this, PlacesToBuy.class);
                    startActivity(i);

                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                finish();
            }
        });

        myTread.start();
    }
}
