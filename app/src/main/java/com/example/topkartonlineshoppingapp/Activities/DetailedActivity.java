package com.example.topkartonlineshoppingapp.Activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.topkartonlineshoppingapp.Adapters.NewProductsAdapter;
import com.example.topkartonlineshoppingapp.Data.Databases.CartDatabase;
import com.example.topkartonlineshoppingapp.R;
import com.example.topkartonlineshoppingapp.models.MyCartModel;
import com.example.topkartonlineshoppingapp.models.NewProductsModel;
import com.example.topkartonlineshoppingapp.models.PopularProductsModel;
import com.example.topkartonlineshoppingapp.models.ShowAllModel;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;

public class DetailedActivity extends AppCompatActivity {
    private static final String TAG = "DetailedActivity";
    ImageView detailedImg, addItems, removeItems;
    TextView rating, name, description, price, quantity;
    Button addToCart, buyNow;

    Toolbar toolbar;
    int totalQuantity = 1;
    double totalPrice = 0.5;
    //New Products
    NewProductsModel newProductsModel = null;
    //Popular Products
    PopularProductsModel popularProductsModel = null;
    //Show ALL
    ShowAllModel showAllModel = null;
    FirebaseAuth auth;
    private FirebaseFirestore firestore;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detailed);

        toolbar = findViewById(R.id.detailed_toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        auth = FirebaseAuth.getInstance();
        firestore = FirebaseFirestore.getInstance();

        final Object obj = getIntent().getSerializableExtra("detailed");
        if (obj instanceof NewProductsModel) {
            newProductsModel = (NewProductsModel) obj;
        } else if (obj instanceof PopularProductsModel) {
            popularProductsModel = (PopularProductsModel) obj;
        } else if (obj instanceof ShowAllModel) {
            showAllModel = (ShowAllModel) obj;
        }
        quantity = findViewById(R.id.quantity);
        detailedImg = findViewById(R.id.detailed_img);
        name = findViewById(R.id.detailed_name);
        rating = findViewById(R.id.rating);
        description = findViewById(R.id.detailed_desc);
        price = findViewById(R.id.detailed_price);

        addToCart = findViewById(R.id.add_to_cart);
        buyNow = findViewById(R.id.buy_now);
        addItems = findViewById(R.id.add_item);
        removeItems = findViewById(R.id.remove_item);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        //New Products
        if (newProductsModel != null) {
            Glide.with(getApplicationContext()).load(newProductsModel.getImg_url()).into(detailedImg);
            name.setText(newProductsModel.getName());
            rating.setText(newProductsModel.getRating());
            description.setText(newProductsModel.getDescription());
            price.setText(String.valueOf(newProductsModel.getPrice()));

            totalPrice = newProductsModel.getPrice() * totalQuantity;
        }

        buyNow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(DetailedActivity.this, AddressActivity.class);

                // Calculate subtotal and add to cart map
                int qty = Integer.parseInt(quantity.getText().toString());
                double pricePerUnit = Double.parseDouble(price.getText().toString());
                double subtotal = qty * pricePerUnit;
                double shippingCost = 30.0;
                double totalPrice = subtotal;

                HashMap<String, Object> cartMap = new HashMap<>();
                cartMap.put("productName", name.getText().toString());
                cartMap.put("productPrice", price.getText().toString());
                cartMap.put("totalQuantity", quantity.getText().toString());
                cartMap.put("subTotal", subtotal);
                cartMap.put("shippingCost", shippingCost);
                cartMap.put("totalPrice", totalPrice);

// Pass the selected product's information to the AddressActivity
                if (newProductsModel != null) {
                    // Update the product price to the total price
                    newProductsModel.setPrice((int) totalPrice);
                    intent.putExtra("item", newProductsModel);
                } else if (popularProductsModel != null) {
                    intent.putExtra("item", popularProductsModel);
                } else if (showAllModel != null) {
                    intent.putExtra("item", showAllModel);
                }
                intent.putExtra("cartMap", cartMap);

                startActivity(intent);

                // Add the selected product to the user's cart in Firestore
                if (auth.getCurrentUser() != null) {
                    DocumentReference cartRef = firestore.collection("OrderInfo")
                            .document(auth.getCurrentUser().getUid()).collection("ProductInfo").document();
                    cartRef.set(cartMap).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if (task.isSuccessful()) {
                                //Toast.makeText(DetailedActivity.this, "Added To A Cart", Toast.LENGTH_SHORT).show();
                            } else {
                                Log.e(TAG, "Error adding to cart", task.getException());
                            }
                        }
                    });
                } else {
                    Toast.makeText(DetailedActivity.this, "Please sign in to add to cart", Toast.LENGTH_SHORT).show();
                }
            }

        });


        //Popular Products
        if (popularProductsModel != null) {
            Glide.with(getApplicationContext()).load(popularProductsModel.getImg_url()).into(detailedImg);
            name.setText(popularProductsModel.getName());
            rating.setText(popularProductsModel.getRating());
            description.setText(popularProductsModel.getDescription());
            price.setText(String.valueOf(popularProductsModel.getPrice()));

            totalPrice = popularProductsModel.getPrice() * totalQuantity;
        }

        //Show All Products
        if (showAllModel != null) {
            Glide.with(getApplicationContext()).load(showAllModel.getImg_url()).into(detailedImg);
            name.setText(showAllModel.getName());
            rating.setText(showAllModel.getRating());
            description.setText(showAllModel.getDescription());
            price.setText(String.valueOf(showAllModel.getPrice()));

            totalPrice = showAllModel.getPrice() * totalQuantity;
        }

        addToCart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addToCart();
            }
        });

        addItems.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (totalQuantity < 10) {
                    totalQuantity++;
                    quantity.setText(String.valueOf(totalQuantity));

                    if (newProductsModel != null) {
                        totalPrice = newProductsModel.getPrice() * totalQuantity;
                    }
                    if (popularProductsModel != null) {
                        totalPrice = popularProductsModel.getPrice() * totalQuantity;
                    }
                    if (showAllModel != null) {
                        totalPrice = showAllModel.getPrice() * totalQuantity;
                    }
                }
            }
        });
        removeItems.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (totalQuantity > 1) {
                    totalQuantity--;
                    quantity.setText(String.valueOf(totalQuantity));
                }
            }
        });
    }


    private void addToCart() {
        String saveCurrentTime, saveCurrentDate;
        Calendar calForDate = Calendar.getInstance();
        SimpleDateFormat currentDate = new SimpleDateFormat("MM/dd/yyyy");
        saveCurrentDate = currentDate.format(calForDate.getTime());

        SimpleDateFormat currentTime = new SimpleDateFormat("HH:mm:ss a");
        saveCurrentTime = currentTime.format(calForDate.getTime());

        // Create a MyCartModel object and set its fields
        MyCartModel cartItem = new MyCartModel();
        cartItem.setProductName(name.getText().toString());
        cartItem.setProductPrice(price.getText().toString());
        cartItem.setCurrentTime(saveCurrentTime);
        cartItem.setCurrentDate(saveCurrentDate);
        cartItem.setTotalQuantity(String.valueOf(Integer.parseInt(quantity.getText().toString())));
        cartItem.setTotalPrice(totalPrice);
       // cartItem.setSubTotal(Integer.parseInt(quantity.getText().toString()) * Double.parseDouble(price.getText().toString()));

        // Insert the cart item into the Room database
        CartDatabase.getInstance(this).myCartDao().insert(cartItem);

        Toast.makeText(DetailedActivity.this, "Added To A Cart", Toast.LENGTH_SHORT).show();
        finish();
    }

}