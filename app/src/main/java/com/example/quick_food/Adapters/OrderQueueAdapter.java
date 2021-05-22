package com.example.quick_food.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.quick_food.GetterSetters.OrderDetails;
import com.example.quick_food.R;

import java.util.List;

public class OrderQueueAdapter extends RecyclerView.Adapter<HelmetFinder> {


    Context mContext;
    private List<OrderDetails> myOrderDetails;

    public OrderQueueAdapter(Context mContext, List<OrderDetails> myHelmetDetails) {
        this.mContext = mContext;
        this.myOrderDetails = myHelmetDetails;
    }


    @Override
    public HelmetFinder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {

        View mView = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.order_row_view, viewGroup, false);

        return new HelmetFinder(mView);

    }

    @Override
    public void onBindViewHolder(@NonNull final HelmetFinder helmetFinder, final int i) {
        helmetFinder.helmetId.setText(myOrderDetails.get(i).getHid());
        helmetFinder.helmetName.setText(myOrderDetails.get(i).getName());


        helmetFinder.hCardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

    }

    @Override
    public int getItemCount() {
        return myOrderDetails.size();
    }
}


class HelmetFinder extends RecyclerView.ViewHolder {

    TextView helmetId, helmetName;
    CardView hCardView;

    public HelmetFinder(View itemView) {
        super(itemView);

        helmetId = itemView.findViewById(R.id.helmet_id);
        helmetName = itemView.findViewById(R.id.helmet_name);
        hCardView = itemView.findViewById(R.id.helmetDetailsCard);

    }
}
