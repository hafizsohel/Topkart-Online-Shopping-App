package com.example.topkartonlineshoppingapp.Adapters;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;
import androidx.recyclerview.widget.RecyclerView;
import com.example.topkartonlineshoppingapp.R;
import com.example.topkartonlineshoppingapp.models.OrderModel;

import java.util.List;

public class OrderAdapter extends RecyclerView.Adapter<OrderAdapter.ViewHolder> {
    Context context;
    List<OrderModel> list;
    int totalAmount=0;
    private RecyclerView recyclerView;
    //private ArrayList<OrderModel> orderLists = new ArrayList<>();




    public OrderAdapter(Context context, List<OrderModel> list) {
        this.context = context;
        this.list = list;
    }

    public OrderAdapter(List<OrderModel> orderList) {
    }

    @NonNull
        @Override
        public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
           return new ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.activity_order, parent, false));
        }

        @Override
        public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
            holder.date.setText(list.get(position).getCurrentDate());
            holder.time.setText(list.get(position).getCurrentTime());
            holder.price.setText(list.get(position).getProductPrice());
            holder.name.setText(list.get(position).getProductName());
            holder.totalPrice.setText(String.valueOf(list.get(position).getTotalPrice()));
            holder.totalQuantity.setText(list.get(position).getTotalQuantity());

            //Total amount pass to Cart Activity
            totalAmount = totalAmount + list.get(position).getTotalPrice();
            Intent intent= new Intent("MyTotalAmount");
            intent.putExtra("totalAmount", totalAmount);

            LocalBroadcastManager.getInstance(context).sendBroadcast(intent);

        }

        @Override
        public int getItemCount() {
            return list.size();
        }


            public class ViewHolder extends RecyclerView.ViewHolder {
                TextView name, price,date,time, totalQuantity, totalPrice;

                public ViewHolder(@NonNull View itemView) {
                    super(itemView);

                    // userName=itemView.findViewById(R.id.user_name);
                    name=itemView.findViewById(R.id.product_name);
                    price=itemView.findViewById(R.id.product_price);
                    date=itemView.findViewById(R.id.current_date);
                    time=itemView.findViewById(R.id.current_time);
                    totalQuantity=itemView.findViewById(R.id.total_quantity);
                    totalPrice=itemView.findViewById(R.id.total_price);

                }
        }
    }

