package com.example.quick_food.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.quick_food.CartDetails;
import com.example.quick_food.FoodData;
import com.example.quick_food.R;
import com.example.quick_food.recycler.CartView;

import java.util.List;

public class CartViewAdapter extends RecyclerView.Adapter<CartViewHolder>{


    Context mContext;
    List<CartDetails> myCartList;


    public CartViewAdapter(Context mContext, List<CartDetails> myCartList) {
        this.mContext = mContext;
        this.myCartList = myCartList;
    }

    @Override
    public CartViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {

        View mView = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.car_confirm, viewGroup, false);

        return new CartViewHolder(mView);
    }

    @Override
    public void onBindViewHolder(@NonNull final CartViewHolder CardViewHolder, final int i) {
        final String nameOfFood = myCartList.get(i).getFoodName();

        CardViewHolder.mTitle.setText(nameOfFood);
        CardViewHolder.mPrice.setText(myCartList.get(i).getFoodPrice());
        CardViewHolder.imageView.setImageResource(myCartList.get(i).getItemImage());


        CardViewHolder.imageDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                myCartList.remove(i);
                CartView.method();
                notifyDataSetChanged();
            }
        });


    }

    @Override
    public int getItemCount() {
        return myCartList.size();
    }
}


class CartViewHolder extends RecyclerView.ViewHolder {

    ImageView imageView, imageDelete;
    TextView mTitle, mPrice;


    public CartViewHolder(View itemView) {
        super(itemView);

        imageView = itemView.findViewById(R.id.photo_thumb);
        mTitle = itemView.findViewById(R.id.tite_text);
        mPrice = itemView.findViewById(R.id.category_text);
        imageDelete = itemView.findViewById(R.id.delete_image);

    }
}


