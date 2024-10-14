package com.example.topkartonlineshoppingapp.Activities;// imports...

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.topkartonlineshoppingapp.Adapters.MyCartAdapter;
import com.example.topkartonlineshoppingapp.Data.Databases.CartDatabase;
import com.example.topkartonlineshoppingapp.Data.Databases.MyCartDao;
import com.example.topkartonlineshoppingapp.R;
import com.example.topkartonlineshoppingapp.models.MyCartModel;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class CartActivity extends AppCompatActivity {
    private static final String TAG = "CartActivity";

    private TextView overAllAmount;
    private Toolbar toolbar;
    private RecyclerView recyclerView;
    private List<MyCartModel> cartModelList;
    private MyCartAdapter cartAdapter;
    private Button buyNow;
    private ExecutorService executorService;
    private MyCartModel myCartModel;
    private boolean isOrderPlaced = false;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cart);

        toolbar = findViewById(R.id.my_cart_toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        Activity activity = this;
        if (activity != null) {
            activity.getWindow().setStatusBarColor(ContextCompat.getColor(activity, R.color.pink));
        }

        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        overAllAmount = findViewById(R.id.total);
        recyclerView = findViewById(R.id.cart_rec);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        cartModelList = new ArrayList<>();
        cartAdapter = new MyCartAdapter(this, cartModelList);
        recyclerView.setAdapter(cartAdapter);

        // Load cart items from the database only for the first creation of the activity
        loadCartItemsFromDatabase();

        buyNow = findViewById(R.id.buy_now_cart);
        buyNow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (cartModelList.isEmpty()) {
                    Toast.makeText(CartActivity.this, "Cart is empty", Toast.LENGTH_SHORT).show();
                    return;
                }
                // Calculate total amount
                double totalAmount = 0;
                for (MyCartModel cartModel : cartModelList) {
                    totalAmount += cartModel.getTotalPrice();
                }
                // Pass subTotal amount and cart items to the payment activity
                Intent intent = new Intent(CartActivity.this, PaymentActivity.class);
                intent.putExtra("subTotal", totalAmount);
                intent.putExtra("cartItems", (Serializable) cartModelList);
                startActivity(intent);
                finish(); // Add this line to finish the current activity
            }
        });
    }

    private void loadCartItemsFromDatabase() {
        executorService = Executors.newSingleThreadExecutor();
        executorService.execute(new Runnable() {
            @Override
            public void run() {
                MyCartDao myCartDao = CartDatabase.getInstance(CartActivity.this).myCartDao();
                List<MyCartModel> items = myCartDao.getAllCartItems();
                if (items != null && !items.isEmpty()) {
                    cartModelList.addAll(items);
                }
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        cartAdapter.setCartItems(cartModelList);
                        calculateTotalAmount(cartModelList);
                    }
                });
            }
        });
    }

    private void calculateTotalAmount(List<MyCartModel> cartModelList) {
        double totalAmount = 0.0;
        for (MyCartModel myCartModel : cartModelList) {
            totalAmount += myCartModel.getTotalPrice();
        }
        overAllAmount.setText("Total Amount: " + totalAmount);
    }

}
