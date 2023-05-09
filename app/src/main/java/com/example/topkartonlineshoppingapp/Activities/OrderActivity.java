package com.example.topkartonlineshoppingapp.Activities;


import static android.content.ContentValues.TAG;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.topkartonlineshoppingapp.Adapters.OrderAdapter;
import com.example.topkartonlineshoppingapp.R;
import com.example.topkartonlineshoppingapp.models.MyCartModel;
import com.example.topkartonlineshoppingapp.models.OrderModel;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class OrderActivity extends AppCompatActivity {
    //Toolbar toolbar;
     RecyclerView recyclerView;
     List<OrderModel> orderList;
    OrderAdapter orderAdapter;
    private FirebaseAuth auth;
    private FirebaseFirestore firestore;
    TextView overAllAmount;
    private Button continueShoppingButton;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order);
        auth = FirebaseAuth.getInstance();
        firestore = FirebaseFirestore.getInstance();
        continueShoppingButton = findViewById(R.id.continue_shopping_button);
        continueShoppingButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(OrderActivity.this, MainActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
                startActivity(intent);
                finish();
            }
        });


        List<MyCartModel>list= (ArrayList<MyCartModel>) getIntent().getSerializableExtra("itemList");

        if (list!=null && list.size()>0){

            for (MyCartModel model:list){
                final HashMap<String, Object> cartMap= new HashMap<>();
                cartMap.put("productName",model.getProductName());
                cartMap.put("productPrice",model.getProductPrice());
                cartMap.put("currentTime",model.getCurrentTime());
                cartMap.put("currentDate",model.getCurrentDate());
                cartMap.put("totalQuantity",model.getTotalQuantity());
                cartMap.put("totalPrice",model.getTotalPrice());

                firestore.collection("AddToCart").document(auth.getCurrentUser().getUid())
                        .collection("MyOrder").get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                            @Override
                            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                if (task.isSuccessful()) {
                                    /*for (DocumentSnapshot doc : task.getResult().getDocuments()) {
                                        OrderModel myOrderModel = doc.toObject(OrderModel.class);
                                        orderList.add(myOrderModel);
                                        orderAdapter.notifyDataSetChanged();*/
                                    Toast.makeText(OrderActivity.this, "Add To A Cart", Toast.LENGTH_SHORT).show();

                                }
                            }
                        });
             /*    BroadcastReceiver mMassageReceiver= new BroadcastReceiver() {
                    @Override
                    public void onReceive(Context context, Intent intent) {
                        // Get the total amount of the order from the intent
                        int totalBill = intent.getIntExtra("totalAmount", 0);
                        // Display the total amount of the order in a TextView
                        overAllAmount.setText("Total Amount : "+totalBill+"৳");
                    }
                };
              firestore.collection("CurrentUser").document(auth.getCurrentUser().getUid()).collection("AddToCart").document()
                        .collection("MyOrder").add(cartMap).addOnCompleteListener(new OnCompleteListener<DocumentReference>() {
                            @Override
                            public void onComplete(@NonNull Task<DocumentReference> task) {
                                Toast.makeText(OrderActivity.this, "Your Order Has Been Placed", Toast.LENGTH_SHORT).show();
                            }
                        });*/



            }
        }
    }
}
