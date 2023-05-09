package com.example.topkartonlineshoppingapp.Activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.topkartonlineshoppingapp.R;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.firestore.FirebaseFirestore;

public class OrderConfirmationActivity extends AppCompatActivity {

    private TextView orderNumberTextView;
    private TextView orderStatusTextView;
    private Button continueShoppingButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_confirmation);

       /* FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference reference = database.getReference();

        String orderId = "ORDER_ID"; // Replace with the ID of the order that you want to update
        DatabaseReference orderRef = reference.child("orders").child(orderId);

        orderRef.child("payment").setValue("cash on delivery")
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        // Payment updated successfully
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        // Failed to update payment
                    }
                });*/


        // Initialize views
        orderNumberTextView = findViewById(R.id.order_number_textview);
        orderStatusTextView = findViewById(R.id.order_status_textview);
        continueShoppingButton = findViewById(R.id.continue_shopping_button);

        // Get the order number from intent
        Intent intent = getIntent();
       // String orderNumber = intent.getStringExtra("order_number");
        int orderNumber = getIntent().getIntExtra("order_number", 123456);


        // Set the order number and status textviews
        orderNumberTextView.setText(String.valueOf(orderNumber));
        orderStatusTextView.setText("Order Confirmed");

        // Continue shopping button click listener
        continueShoppingButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(OrderConfirmationActivity.this, MainActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
                startActivity(intent);
                finish();
            }
        });
    }
}

 //app:showAsAction="always"
