package com.example.topkartonlineshoppingapp.Activities;


import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import com.example.topkartonlineshoppingapp.R;
import androidx.annotation.NonNull;
import android.content.Intent;
import android.net.Uri;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import com.bumptech.glide.Glide;
import com.example.topkartonlineshoppingapp.models.UserModel;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.util.HashMap;
import java.util.Map;

import de.hdodenhof.circleimageview.CircleImageView;




public class ProfileActivity extends AppCompatActivity {
    private static final String TAG = "ProfileActivity";

    CircleImageView profileImg;
    EditText name, email, address, number;
    Button update;

    FirebaseAuth auth;
    FirebaseFirestore database;
    FirebaseStorage storage;

    FirebaseDatabase db;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        auth = FirebaseAuth.getInstance();
        database = FirebaseFirestore.getInstance();
        storage = FirebaseStorage.getInstance();
        db = FirebaseDatabase.getInstance();

        profileImg = findViewById(R.id.profile_img);
        name = findViewById(R.id.profile_name);
        // email = findViewById(R.id.profile_email);
        number = findViewById(R.id.profile_number);
        //  address = findViewById(R.id.profile_address);
        update = findViewById(R.id.update);


        FirebaseFirestore db = FirebaseFirestore.getInstance();
        DocumentReference userRef = db.collection("Users").document(FirebaseAuth.getInstance().getUid());
        userRef.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {
                if (documentSnapshot.exists()) {
                    UserModel userModel = documentSnapshot.toObject(UserModel.class);
                    Glide.with(getApplication())
                            .load(userModel.getProfileImg())
                            .into(profileImg);

                }
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                // Handle any errors here
            }
        });

        profileImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setAction(Intent.ACTION_GET_CONTENT);
                intent.setType("image/*");
                startActivityForResult(intent, 33);
            }
        });


        update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                updateUserProfile();
            }
        });

    }

    private void updateUserProfile() {
        FirebaseUser user = auth.getCurrentUser();
        String newName = name.getText().toString().trim();
        // String newEmail= email.getText().toString().trim();
        String newNumber = number.getText().toString().trim(); // Get the phone number value

        if (newName.isEmpty()) {
            name.setError("Name is required");
            // number.setError("Number is required");

            name.requestFocus();
            // number.requestFocus();
            return;
        }
        UserProfileChangeRequest profileUpdates = new UserProfileChangeRequest.Builder()
                .setDisplayName(newName)
                .build();
        user.updateProfile(profileUpdates)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()) {
                            Toast.makeText(ProfileActivity.this, "Profile updated", Toast.LENGTH_SHORT).show();
                        } else {
                            Toast.makeText(ProfileActivity.this, "Failed to update profile", Toast.LENGTH_SHORT).show();
                            Log.e(TAG, "Failed to update profile", task.getException());
                        }
                    }
                });

        // Update the phone number value in the Firestore database

        Map<String, Object> updates = new HashMap<>();
        updates.put("number", newNumber);
        updates.put("name", newName);

        FirebaseFirestore db = FirebaseFirestore.getInstance();
        DocumentReference userRef = db.collection("Users").document(FirebaseAuth.getInstance().getUid());

        userRef.update(updates).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {
                // Show a success message if the update is successful
                Toast.makeText(ProfileActivity.this, "Phone number updated", Toast.LENGTH_SHORT).show();
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                // Show an error message if the update fails
                Toast.makeText(ProfileActivity.this, "Failed to update phone number", Toast.LENGTH_SHORT).show();
                Log.e(TAG, "Failed to update phone number", e);
            }
        });

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (data.getData() != null) {
            Uri profileUri = data.getData();
            profileImg.setImageURI(profileUri);
            final StorageReference reference = storage.getReference().child("profile_picture")
                    .child(FirebaseAuth.getInstance().getUid());

            reference.putFile(profileUri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                    // Toast.makeText(ProfileActivity.this, "Updated", Toast.LENGTH_SHORT).show();

                    reference.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                        @Override
                        public void onSuccess(Uri uri) {

                            //Update profile picture
                            DocumentReference userRef = database.collection("Users").document(FirebaseAuth.getInstance().getUid());
                            userRef.update("profileImg", uri.toString());

                        }
                    });

                    //Update Name
                    String newName = name.getText().toString().trim();
                    DocumentReference userRef = database.collection("Users").document(FirebaseAuth.getInstance().getUid());
                    if (!newName.isEmpty()) {
                        userRef.update("name", newName);
                    }
                }
            });

            // Update number in Firestore
            String newNumber = number.getText().toString().trim();
            DocumentReference userRef = database.collection("Users").document(FirebaseAuth.getInstance().getUid());
            if (!newNumber.isEmpty()) {
                userRef.update("number", newNumber);
            }
        }

    }
}
