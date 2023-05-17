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

public class MyCartAdapter extends RecyclerView.Adapter<MyCartAdapter.ViewHolder> {
    private static final String TAG = "MyCartAdapter";

    private Context context;
    private List<MyCartModel> cartModelList;
    //List<MyCartModel> contactModelList = new ArrayList<>();
    // data source for cart items
    //private List<MyCartModel> cartItems = new ArrayList<>();

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

        //return cartModelList != null ? cartModelList.size() : 0;
    }
    public void setCartItems(List<MyCartModel> cartItems) {
        this.cartModelList = cartItems;
       // notifyDataSetChanged();
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
