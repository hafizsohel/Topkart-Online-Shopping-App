package com.example.topkartonlineshoppingapp.Adapters;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.topkartonlineshoppingapp.Activities.CartActivity;
import com.example.topkartonlineshoppingapp.R;
import com.example.topkartonlineshoppingapp.models.MyCartModel;

import java.util.ArrayList;
import java.util.List;

/*public class MyCartAdapter extends RecyclerView.Adapter<MyCartAdapter.ViewHolder> {

    Context context;
    List<MyCartModel>list;
    int totalAmount=0;

    public MyCartAdapter(Context context, List<MyCartModel> list) {
        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.my_cart_item, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

       // holder.userName.setText(list.get(position).getUserName());
        holder.date.setText(list.get(position).getCurrentDate());
        holder.time.setText(list.get(position).getCurrentTime());
        holder.price.setText(list.get(position).getProductPrice());
        holder.name.setText(list.get(position).getProductName());
        holder.totalPrice.setText(String.valueOf(list.get(position).getTotalPrice()));
        holder.totalQuantity.setText(list.get(position).getTotalQuantity());

      /*  //Total amount pass to Cart Activity
        totalAmount = totalAmount + list.get(position).getTotalPrice();
        Intent intent= new Intent("MyTotalAmount");
        intent.putExtra("totalAmount", totalAmount);

        LocalBroadcastManager.getInstance(context).sendBroadcast(intent);*/

/*
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
}*/

public class MyCartAdapter extends RecyclerView.Adapter<MyCartAdapter.ViewHolder> {
    private static final String TAG = "MyCartAdapter";

    private Context context;
    private List<MyCartModel> cartModelList;
    // data source for cart items
    private List<MyCartModel> cartItems = new ArrayList<>();

    public MyCartAdapter(Context context, List<MyCartModel> cartModelList) {
        this.context = context;
        this.cartModelList = cartModelList;
    }

    // Method to clear the cart items
    public void clearCartItems() {
        cartModelList.clear();
        notifyDataSetChanged();
    }
    public MyCartAdapter(CartActivity cartActivity, List<MyCartModel> cartModelList) {
    }
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.my_cart_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        MyCartModel cartItem = cartModelList.get(position);

        holder.tvCurrentDate.setText(cartItem.getCurrentDate());
        holder.tvCurrentTime.setText(cartItem.getCurrentTime());
        holder.tvProductName.setText(cartItem.getProductName());
        holder.tvProductPrice.setText(cartItem.getProductPrice());
        holder.tvTotalQuantity.setText(cartItem.getTotalQuantity());
        // holder.subTotal.setText(String.valueOf(cartItem.getSubTotal()));
        holder.tvTotalPrice.setText(String.valueOf(cartItem.getTotalPrice()));
    }

    @Override
    public int getItemCount() {
        if(cartModelList==null) return 0;
        return cartModelList.size();
    }
    public void setCartItems(List<MyCartModel> cartItems) {
        this.cartModelList = cartItems;
        notifyDataSetChanged();
    }


    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView tvCurrentDate, tvCurrentTime, tvProductName, tvProductPrice, tvTotalQuantity, tvTotalPrice, subTotal;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            tvCurrentDate = itemView.findViewById(R.id.current_date);
            tvCurrentTime = itemView.findViewById(R.id.current_time);
            tvProductName = itemView.findViewById(R.id.product_name);
            tvProductPrice = itemView.findViewById(R.id.product_price);
            //subTotal=itemView.findViewById(R.id.sub_total);
            tvTotalQuantity = itemView.findViewById(R.id.total_quantity);
            tvTotalPrice = itemView.findViewById(R.id.total_price);
        }
    }
}
