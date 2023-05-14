package com.example.topkartonlineshoppingapp.Activities;

import android.os.Bundle;
import android.util.Log;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.example.topkartonlineshoppingapp.Adapters.OrderAdapter;
import com.example.topkartonlineshoppingapp.R;
import com.example.topkartonlineshoppingapp.models.OrderListModel;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import java.util.ArrayList;

public class OrderList extends AppCompatActivity {
    private static final String TAG = "OrderList";

    private RecyclerView orderRecyclerView;
    private OrderAdapter orderAdapter;
    private ArrayList<OrderListModel> orderList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_list);

        orderRecyclerView = findViewById(R.id.orderList_rec);
        orderRecyclerView.setLayoutManager(new LinearLayoutManager(this));

        orderList = new ArrayList<>();
        orderAdapter = new OrderAdapter(OrderList.this, orderList);
        orderRecyclerView.setAdapter(orderAdapter);

        FirebaseFirestore db = FirebaseFirestore.getInstance();

        String currentUserUid = FirebaseAuth.getInstance().getCurrentUser().getUid();

        db.collection("Order").document(currentUserUid).collection("MyOrder")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                OrderListModel order = document.toObject(OrderListModel.class);
                                orderList.add(order);
                            }
                            orderAdapter.notifyDataSetChanged();
                        } else {
                            Log.d("OrderListActivity", "Error getting documents: ", task.getException());
                        }
                    }
                });
    }
}