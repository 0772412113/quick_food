package com.example.quick_food.Adapters;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.quick_food.recycler.FoodCategory;
import com.example.quick_food.R;
import com.example.quick_food.Universities;
import com.squareup.picasso.Picasso;

import java.util.List;

public class AdapterForUniversities extends RecyclerView.Adapter<UniFinder>{

    private Context uContext;
    private List<Universities> myUniversityList;

    public AdapterForUniversities(Context uContext, List<Universities> myUniversityList) {
        this.uContext = uContext;
        this.myUniversityList = myUniversityList;
    }

    @Override
    public UniFinder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {

        View uView = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.uni_row,viewGroup,false);

        return new UniFinder(uView);
    }

    @Override
    public void onBindViewHolder(@NonNull UniFinder uniFinder, int i) {

        final String nameOfUniversity = myUniversityList.get(i).getUniName();

       // uniFinder.uimageView.setImageResource(myUniversityList.get(i).getItemImage());
        String ImageOfUniversity = myUniversityList.get(i).getItemImage();
        Picasso.get()
                .load(ImageOfUniversity)
                .into( uniFinder.uimageView);
        uniFinder.uTitle.setText(myUniversityList.get(i).getUniName());

        uniFinder.uCardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(uContext, FoodCategory.class);
                intent.putExtra("EXTRA_UNI_ID", nameOfUniversity);
                uContext.startActivity(intent);
            }
        });

    }

    @Override
    public int getItemCount() {
        return myUniversityList.size();
    }
}



class UniFinder extends RecyclerView.ViewHolder{

    ImageView uimageView;
    TextView uTitle;
    CardView uCardView;

    public UniFinder(@NonNull View itemView) {
        super(itemView);

        uimageView = itemView.findViewById(R.id.nsbm);
        uTitle = itemView.findViewById(R.id.UniName);

        uCardView= itemView.findViewById(R.id.uniCardView);

    }
}
