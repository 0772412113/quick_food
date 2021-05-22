package com.example.quick_food.Adapters;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.quick_food.GetterSetters.OrderDetails;
import com.example.quick_food.R;
import com.example.quick_food.recycler.MyCartActivity;

import java.util.List;

public class OrderQueueAdapter extends RecyclerView.Adapter<OrderQueueHolder> {


    Context mContext;
    private List<OrderDetails> myOrderDetails;

    public OrderQueueAdapter(Context mContext, List<OrderDetails> myHelmetDetails) {
        this.mContext = mContext;
        this.myOrderDetails = myHelmetDetails;
    }


    @Override
    public OrderQueueHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {

        View mView = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.order_row_view, viewGroup, false);

        return new OrderQueueHolder(mView);

    }

    @Override
    public void onBindViewHolder(@NonNull final OrderQueueHolder helmetFinder, final int i) {

        final String OrderId = myOrderDetails.get(i).getOrderId();
        helmetFinder.orderStatus.setText(myOrderDetails.get(i).getStatus());
        helmetFinder.OrderId.setText(OrderId);


        helmetFinder.hCardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(mContext, MyCartActivity.class);
                intent.putExtra("EXTRA_ORDER_ID", OrderId);
                intent.putExtra("EXTRA_ORDER_USER_ID", myOrderDetails.get(i).getUserId());
                mContext.startActivity(intent);
            }
        });

    }

    @Override
    public int getItemCount() {
        return myOrderDetails.size();
    }
}


class OrderQueueHolder extends RecyclerView.ViewHolder {

    TextView orderStatus, OrderId;
    CardView hCardView;

    public OrderQueueHolder(View itemView) {
        super(itemView);

        orderStatus = itemView.findViewById(R.id.order_status_text);
        OrderId = itemView.findViewById(R.id.order_id_text);
        hCardView = itemView.findViewById(R.id.helmetDetailsCard);

    }
}
