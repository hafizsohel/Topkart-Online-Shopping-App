package com.example.topkartonlineshoppingapp.Adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.topkartonlineshoppingapp.Activities.OrderList;
import com.example.topkartonlineshoppingapp.R;
import com.example.topkartonlineshoppingapp.models.OrderListModel;

import java.util.List;

public class OrderAdapter extends RecyclerView.Adapter<OrderAdapter.OrderViewHolder> {
    private List<OrderListModel> orderList;

    public OrderAdapter(OrderList list, List<OrderListModel> orderList) {
        this.orderList = orderList;
    }

    @NonNull
    @Override
    public OrderViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.my_oder_item, parent, false);
        return new OrderViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull OrderViewHolder holder, int position) {
        OrderListModel order = orderList.get(position);
        //holder.orderID.setText(order.getOrderId());
        holder.subTotalTextView.setText(order.getSubTotal());
        holder.discountTextView.setText(order.getDiscount());
        holder.shippingCostTextView.setText(String.valueOf(order.getShippingCost()));
        holder.totalPriceTextView.setText(order.getTotalPrice());
    }

    @Override
    public int getItemCount() {
        return orderList.size();
    }

    public static class OrderViewHolder extends RecyclerView.ViewHolder {
        private TextView subTotalTextView;
        private TextView discountTextView;
        private TextView shippingCostTextView;
        private TextView totalPriceTextView;
        private TextView orderID;

        public OrderViewHolder(@NonNull View itemView) {
            super(itemView);
           // orderID=itemView.findViewById(R.id.order_ID);
            subTotalTextView = itemView.findViewById(R.id.subtotal_order);
            discountTextView = itemView.findViewById(R.id.cart_amount_order);
            shippingCostTextView = itemView.findViewById(R.id.shipping_cost_order);
            totalPriceTextView = itemView.findViewById(R.id.total_price_order);
        }
    }
}