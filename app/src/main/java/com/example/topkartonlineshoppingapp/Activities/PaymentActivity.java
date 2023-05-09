package com.example.topkartonlineshoppingapp.Activities;
/*
import androidx.annotation.NonNull;
import  androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.topkartonlineshoppingapp.Adapters.OrderAdapter;
import com.example.topkartonlineshoppingapp.R;
import com.example.topkartonlineshoppingapp.models.OrderModel;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class PaymentActivity extends AppCompatActivity {
    private static final String TAG = "PaymentActivity";

    double amount = 0.0;
    List<OrderModel> orderList;
    OrderAdapter orderAdapter;
    Toolbar toolbar;
    TextView subTotal, discount, shipping, total;
    Button paymentBtn;
    FirebaseAuth auth;
    FirebaseFirestore firestore;

    private HashMap<String, Object> cartMap = new HashMap<>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_payment);

        //ToolBar
        toolbar = findViewById(R.id.payment_toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        firestore = FirebaseFirestore.getInstance();
        auth=FirebaseAuth.getInstance();

        double amount = getIntent().getDoubleExtra("amount", 0.0);

        subTotal = findViewById(R.id.sub_total);
        discount = findViewById(R.id.textView17);
        shipping = findViewById(R.id.textView18);
        total = findViewById(R.id.total_amt);
        paymentBtn = findViewById(R.id.pay_btn);

        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        subTotal.setText(amount + " ৳");

        double shippingCost = 30.0;
        double totalAmount = amount + shippingCost;

        shipping.setText(shippingCost + " ৳");
        total.setText(totalAmount + " ৳");
        paymentBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                paymentMethod();
            }
        });


    }

    private void paymentMethod() {
        Toast.makeText(this, "Order placed successfully!", Toast.LENGTH_SHORT).show();
        Intent intent = new Intent(this, OrderActivity.class);
        startActivity(intent);
        finish();
        // Add the selected product to the user's cart in Firestore
        if (auth.getCurrentUser() != null) {
            // Add your data to cartMap here
            Map<String, Object> cartMap = new HashMap<>();
            cartMap.put("subTotal", subTotal);
            cartMap.put("discount", discount);
            cartMap.put("totalPrice", total);
           // cartMap.put("shippingCost", 30.0); // Add a fixed shipping cost of 30

            firestore.collection("Order")
                    .document(auth.getCurrentUser().getUid())
                    .collection("MyOrder")
                    .add(cartMap)
                    .addOnCompleteListener(new OnCompleteListener<DocumentReference>() {
                        @Override
                        public void onComplete(@NonNull Task<DocumentReference> task) {
                            if (task.isSuccessful()) {
                                //Toast.makeText(DetailedActivity.this, "Added To A Cart", Toast.LENGTH_SHORT).show();
                            } else {
                                Log.e(TAG, "Error adding to cart", task.getException());
                            }
                        }
                    });
        } else {
            Toast.makeText(PaymentActivity.this, "Please sign in to add to cart", Toast.LENGTH_SHORT).show();
        }
    }



}*/

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.topkartonlineshoppingapp.Adapters.OrderAdapter;
import com.example.topkartonlineshoppingapp.R;
import com.example.topkartonlineshoppingapp.models.OrderModel;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class PaymentActivity extends AppCompatActivity {
    private static final String TAG = "PaymentActivity";

    double amount = 0.0;
    OrderAdapter orderAdapter;
    Toolbar toolbar;
    TextView subTotal, discount, shipping, total;
    Button paymentBtn;
    FirebaseAuth auth;
    FirebaseFirestore firestore;

    private HashMap<String, Object> cartMap = new HashMap<>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_payment);

        //ToolBar
        toolbar = findViewById(R.id.payment_toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        firestore = FirebaseFirestore.getInstance();
        auth=FirebaseAuth.getInstance();

        double amount = getIntent().getDoubleExtra("amount", 0.0);


        Intent intent = getIntent();
        double subTotals = intent.getDoubleExtra("subTotal", 0.0);



        subTotal = findViewById(R.id.sub_total);
        discount = findViewById(R.id.textView17);
        shipping = findViewById(R.id.textView18);
        total = findViewById(R.id.total_amt);
        paymentBtn = findViewById(R.id.pay_btn);



        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        subTotal.setText(amount + " ৳");

        double shippingCost = 30.0;
        double totalAmount = amount + shippingCost;
        double totalAmounts = subTotals + shippingCost;

        shipping.setText(shippingCost + " ৳");
        total.setText(totalAmount + " ৳");
        subTotal.setText(subTotals+" ৳");
        total.setText(totalAmounts + " ৳");
        paymentBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                paymentMethod();
            }
        });


    }

    private void paymentMethod() {
        Toast.makeText(this, "Order placed successfully!", Toast.LENGTH_SHORT).show();
        Intent intent = new Intent(this, OrderActivity.class);
        startActivity(intent);
        finish();
        // Add the selected product to the user's cart in Firestore
        if (auth.getCurrentUser() != null) {
            // Add your data to cartMap here
            cartMap.put("subTotal", subTotal.getText().toString());
            cartMap.put("discount", discount.getText().toString());
            cartMap.put("totalPrice", total.getText().toString());
            cartMap.put("shippingCost", 30.0); // Add a fixed shipping cost of 30

            firestore.collection("Order")
                    .document(auth.getCurrentUser().getUid())
                    .collection("MyOrder")
                    .add(cartMap)
                    .addOnCompleteListener(new OnCompleteListener<DocumentReference>() {
                        @Override
                        public void onComplete(@NonNull Task<DocumentReference> task) {
                            if (task.isSuccessful()) {
                                //Toast.makeText(DetailedActivity.this, "Added To A Cart", Toast.LENGTH_SHORT).show();
                            } else {
                                Log.e(TAG, "Error adding to cart", task.getException());
                            }
                        }
                    });
        } else {
            Toast.makeText(PaymentActivity.this, "Please sign in for payment", Toast.LENGTH_SHORT).show();
        }
    }
}
