package com.example.topkartonlineshoppingapp.Activities;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.core.view.MenuItemCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.transition.Transition;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.Request;
import com.bumptech.glide.request.target.SizeReadyCallback;
import com.bumptech.glide.request.target.Target;
import com.example.topkartonlineshoppingapp.R;
import com.example.topkartonlineshoppingapp.fragments.HomeFragment;
import com.example.topkartonlineshoppingapp.models.UserModel;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.Objects;

import de.hdodenhof.circleimageview.CircleImageView;

public class MainActivity extends AppCompatActivity {

    Fragment homeFragment;
    Toolbar toolbar;
    FirebaseAuth auth;
    FirebaseUser user;
    BottomNavigationView bottom_nav;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //  getSupportActionBar().hide();
        //  getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        auth = FirebaseAuth.getInstance();
        toolbar = findViewById(R.id.home_toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(false);
       // getSupportActionBar().setHomeAsUpIndicator(R.drawable.baseline_menu_24);
        bottom_nav=findViewById(R.id.bottom_nav);

        bottom_nav.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()){
                    case R.id.profile:
                        if (user!=null){
                        Intent intent= new Intent(MainActivity.this, ProfileActivity.class);
                        startActivity(intent);
                        break;}

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

        homeFragment = new HomeFragment();
        loadFragment(homeFragment);
    }

    private void loadFragment(Fragment homeFragment) {
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.home_container, homeFragment);
        transaction.commit();
    }
/*
    @Override
    protected void onStart() {
        super.onStart();
        user=FirebaseAuth.getInstance().getCurrentUser();
    }
*/
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu, menu);

        MenuItem menuItem = menu.findItem(R.id.profile);
        View view = MenuItemCompat.getActionView(menuItem);
        CircleImageView profileImage = view.findViewById(R.id.toolbar_profile_image);
        //loadProfileImageIntoMenuIcon();

        //Glide
         //       .with(this)
           //            .load("https://firebasestorage.googleapis.com/v0/b/topkart-online-shopping-app.appspot.com/o/profile_picture%2F3eoHl8lkpucBPM5DiC9R0vdbgGP2?alt=media&token=f7d95225-c044-4124-99da-fb599e273453") .into(profileImage);


        profileImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent= new Intent(MainActivity.this, ProfileDetails.class);
                startActivity(intent);
                //Toast.makeText(MainActivity.this, "Profile Clicked", Toast.LENGTH_SHORT).show();
            }
        });
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.menu_logout) {
            auth.signOut();
            startActivity(new Intent(MainActivity.this, RegistrationActivity.class));
            finish();
        } else if (id == R.id.menu_my_cart) {
            startActivity(new Intent(MainActivity.this, CartActivity.class));
        } else if (id==R.id.order_list){
            startActivity(new Intent(this,OrderList.class));
        }
        else if (id==R.id.home){
            startActivity(new Intent(this, MainActivity.class));
        }
        return true;
    }
}
