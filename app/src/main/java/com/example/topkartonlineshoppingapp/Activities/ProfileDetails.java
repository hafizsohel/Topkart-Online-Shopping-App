package com.example.topkartonlineshoppingapp.Activities;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.media.Image;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.topkartonlineshoppingapp.R;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;

import de.hdodenhof.circleimageview.CircleImageView;

public class ProfileDetails extends AppCompatActivity {
    private static final String TAG = "ProfileDetails";

    TextView showName, showEmail, showNumber;
    CircleImageView userImage;
    Button logout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile_details);

        showName = findViewById(R.id.show_name);
        showEmail = findViewById(R.id.show_email);
        showNumber=findViewById(R.id.show_number);

        userImage = findViewById(R.id.profile_img1);
        //menuIcon = findViewById(R.id.menu_my_cart);

        logout = findViewById(R.id.logout_btn);
        FirebaseAuth auth = FirebaseAuth.getInstance();


        FirebaseFirestore db = FirebaseFirestore.getInstance();
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        String userID = user.getUid();

        //Show Profile Data from firebase

        DocumentReference userRef = db.collection("Users").document(userID);
        userRef.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {
                if (documentSnapshot.exists()) {
                    String name = documentSnapshot.getString("name");
                    String email = documentSnapshot.getString("email");
                    String number = documentSnapshot.getString("number");
                    String profileImgUrl = documentSnapshot.getString("profileImg");

                    showName.setText(name);
                    showEmail.setText(email);
                    showNumber.setText(number);

                    if (profileImgUrl != null && !profileImgUrl.isEmpty()) {
                        Glide.with(ProfileDetails.this)
                                .load(profileImgUrl)
                                .into(userImage);
                    }
                    // Load the user's profile image into the menu icon image view
                }

            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {

            }
        });

        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                auth.signOut();
                startActivity(new Intent(ProfileDetails.this, LoginActivity.class));
                finish();
            }
        });
    }
}
