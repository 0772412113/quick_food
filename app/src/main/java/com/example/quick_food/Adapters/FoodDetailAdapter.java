package com.example.quick_food.Adapters;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.quick_food.GetterSetters.FoodDetails;
import com.example.quick_food.R;
import com.example.quick_food.AddToCartActivity;
import com.squareup.picasso.Picasso;

import java.util.List;

public class FoodDetailAdapter extends RecyclerView.Adapter<FoodDeailHolder>{

    private Context fdContext;
    private List<FoodDetails> myFoodDetailList;

    public FoodDetailAdapter(Context fdContext, List<FoodDetails> myFoodDetailList) {
        this.fdContext = fdContext;
        this.myFoodDetailList = myFoodDetailList;
    }

    @Override
    public FoodDeailHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View fView = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.food_item_layout,viewGroup,false);

        return new FoodDeailHolder(fView);
    }

    @Override
    public void onBindViewHolder(@NonNull FoodDeailHolder foodDeailHolder, final int i) {

        final String foodID = myFoodDetailList.get(i).getFoodId();
        final String foodName = myFoodDetailList.get(i).getFoodName();
        final String foodPrice = myFoodDetailList.get(i).getFoodPrice();

        final String foodImage = myFoodDetailList.get(i).getItemImage();
        Picasso.get()
                .load(foodImage)
                .placeholder(R.drawable.image_loading)
                .into(foodDeailHolder.fimageView);

        foodDeailHolder.fdTitle.setText(foodName);
        foodDeailHolder.fdPrice.setText(foodPrice);

        foodDeailHolder.maddToCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(fdContext, AddToCartActivity.class);
                intent.putExtra("FOOD_ID_FOR_CART", foodID);
                intent.putExtra("FOOD_NAME_FOR_CART", foodName);
                intent.putExtra("FOOD_IMAGE_FOR_CART", foodImage);
                intent.putExtra("FOOD_PRICE_FOR_CART", foodPrice);
                fdContext.startActivity(intent);

            }
        });
    }

    @Override
    public int getItemCount() {
        return myFoodDetailList.size();
    }
}
 class FoodDeailHolder extends RecyclerView.ViewHolder{

    ImageView fimageView;
    TextView fdTitle,fdPrice;
    CardView fdCardView;
     Button maddToCard;


     public FoodDeailHolder(@NonNull View itemView) {
         super(itemView);

         fimageView = (ImageView) itemView.findViewById(R.id.fdImage);
         fdTitle = (TextView) itemView.findViewById(R.id.foTitle);
         fdPrice = (TextView) itemView.findViewById(R.id.foPrice);
         maddToCard = itemView.findViewById(R.id.btn_add_cart);
         fdCardView = (CardView)itemView.findViewById(R.id.myFoodCardView);

     }
 }
