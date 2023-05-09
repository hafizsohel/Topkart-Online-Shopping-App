package com.example.topkartonlineshoppingapp.Activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.provider.Telephony;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.topkartonlineshoppingapp.Adapters.AddressAdapter;
import com.example.topkartonlineshoppingapp.R;
import com.example.topkartonlineshoppingapp.models.AddressModel;
import com.example.topkartonlineshoppingapp.models.MyCartModel;
import com.example.topkartonlineshoppingapp.models.NewProductsModel;
import com.example.topkartonlineshoppingapp.models.PopularProductsModel;
import com.example.topkartonlineshoppingapp.models.ShowAllModel;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

public class AddressActivity extends AppCompatActivity implements AddressAdapter.SelectedAddress {
    private static final String TAG = "AddressActivity";
    Button addAddress;
    RecyclerView recyclerView;
    private List<AddressModel>addressModelList;
    private AddressAdapter addressAdapter;
    FirebaseAuth auth;
    FirebaseFirestore firestore;
    Button addAddressBtn,paymentBtn;
    Toolbar toolbar;
    String mAddress= "";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_address);

        toolbar=findViewById(R.id.address_toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        //get Data from detailed Activity
        Object obj=getIntent().getSerializableExtra("item");
        Log.d(TAG, "item: "+obj);


        firestore=FirebaseFirestore.getInstance();
        auth=FirebaseAuth.getInstance();

        recyclerView=findViewById(R.id.address_recycler);
        paymentBtn=findViewById(R.id.payment_btn);
        addAddressBtn=findViewById(R.id.add_address_btn);

        recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        addressModelList= new ArrayList<>();
        addressAdapter= new AddressAdapter(getApplication(),addressModelList,this);
        recyclerView.setAdapter(addressAdapter);

        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        firestore.collection("CurrentUser").document(auth.getCurrentUser().getUid())
                        .collection("Address").get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {

                        if (task.isSuccessful()) {
                            for (DocumentSnapshot doc : task.getResult().getDocuments()) {
                                AddressModel addressModel = doc.toObject(AddressModel.class);
                                addressModelList.add(addressModel);
                                addressAdapter.notifyDataSetChanged();
                            }
                        }
                    }
                });

        paymentBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                double amount=0.0;
               if (obj instanceof NewProductsModel){
                   NewProductsModel newProductsModel=(NewProductsModel) obj;
                   amount= newProductsModel.getPrice();
               }
                if (obj instanceof PopularProductsModel){
                    PopularProductsModel popularProductsModel=(PopularProductsModel) obj;
                    amount= popularProductsModel.getPrice();
                }
                if (obj instanceof ShowAllModel){
                    ShowAllModel showAllModel=(ShowAllModel) obj;
                    amount= showAllModel.getPrice();
                }
                if(mAddress.isEmpty()){
                    Toast.makeText(AddressActivity.this, "Please select an address", Toast.LENGTH_SHORT).show();
                } else {
                    Intent intent= new Intent(AddressActivity.this, PaymentActivity.class);
                    intent.putExtra("amount", amount);
                    startActivity(intent);
                }
            }
        });


        addAddress=findViewById(R.id.add_address_btn);
        addAddress.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(AddressActivity.this, OrderActivity.class));

            }
        });
    }

    @Override
    public void setAddress(String address) {

        mAddress=address;
        //Toast.makeText(AddressActivity.this, "Cash on Delivery selected", Toast.LENGTH_SHORT).show();
    }
}