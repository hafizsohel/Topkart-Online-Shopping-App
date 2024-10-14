
package com.example.topkartonlineshoppingapp.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.MenuItemCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.bumptech.glide.Glide;
import com.example.topkartonlineshoppingapp.R;
import com.example.topkartonlineshoppingapp.fragments.HomeFragment;
import com.example.topkartonlineshoppingapp.models.UserModel;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import de.hdodenhof.circleimageview.CircleImageView;

public class MainActivity extends AppCompatActivity {

    private Toolbar toolbar;
    private FirebaseAuth auth;
    private FirebaseUser user;
    private CircleImageView profileImg;
    BottomNavigationView bottom_nav;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Initialize Firebase Authentication
        auth = FirebaseAuth.getInstance();
        user = auth.getCurrentUser();
        bottom_nav=findViewById(R.id.bottom_nav);

        // Set up Toolbar
        toolbar = findViewById(R.id.home_toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(false);

        // Load Home Fragment by default
        loadFragment(new HomeFragment());
    }

    private void loadFragment(Fragment fragment) {
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.home_container, fragment);
        transaction.commit();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu, menu);

        // Load profile image into toolbar menu icon
        MenuItem menuItem = menu.findItem(R.id.profile);
        View view = MenuItemCompat.getActionView(menuItem);
        profileImg = view.findViewById(R.id.toolbar_profile_image);


        bottom_nav.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()){
                    case R.id.profile:
                       // if (user!=null){
                        Intent intent= new Intent(MainActivity.this, ProfileActivity.class);
                        startActivity(intent);
                        break;


                    case R.id.menu_my_cart:
                        Intent myIntent= new Intent(MainActivity.this, CartActivity.class);
                        startActivity(myIntent);
                        break;

                    case R.id.order_list:
                        Intent oIntent= new Intent(MainActivity.this, OrderList.class);
                        startActivity(oIntent);
                        break;


                    case R.id.menu_logout:
                        Intent mIntent= new Intent(MainActivity.this, LoginActivity.class);
                        startActivity(mIntent);
                        finish();
                        break;

                }
                return true;
            }
        });

        if (user != null) {
            // Load profile image from Firebase Firestore
            FirebaseFirestore db = FirebaseFirestore.getInstance();
            DocumentReference userRef = db.collection("Users").document(user.getUid());
            userRef.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                @Override
                public void onSuccess(DocumentSnapshot documentSnapshot) {
                    if (documentSnapshot.exists()) {
                        UserModel userModel = documentSnapshot.toObject(UserModel.class);
                        Glide.with(MainActivity.this)
                                .load(userModel.getProfileImg())
                                .placeholder(R.drawable.profile)
                                .into(profileImg);
                    }
                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    Toast.makeText(MainActivity.this, "Failed to load profile image", Toast.LENGTH_SHORT).show();
                }
            });


        }

        // Handle profile image click
        profileImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, ProfileDetails.class));
            }
        });

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.menu_logout) {
            auth.signOut();
            startActivity(new Intent(MainActivity.this, LoginActivity.class));
            finish();
        } else if (id == R.id.menu_my_cart) {
            startActivity(new Intent(MainActivity.this, CartActivity.class));
        }
        else if (id==R.id.order_list){
            startActivity(new Intent(this,OrderList.class));
        }
        else if (id==R.id.home){
            startActivity(new Intent(this, MainActivity.class));
        }
        return super.onOptionsItemSelected(item);
    }
}
