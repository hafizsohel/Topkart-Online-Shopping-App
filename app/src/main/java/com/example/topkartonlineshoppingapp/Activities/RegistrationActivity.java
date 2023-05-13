package com.example.topkartonlineshoppingapp.Activities;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.example.topkartonlineshoppingapp.R;
import com.example.topkartonlineshoppingapp.models.UserModel;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthProvider;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class RegistrationActivity extends AppCompatActivity {

    EditText name, email, password;
    TextView textView;

    SharedPreferences sharedPreferences;

    FirebaseFirestore database;
    FirebaseAuth auth;
    String userID;
    private GoogleSignInClient client;
    private static int RC_SIGN_IN=100;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);
        //getSupportActionBar().hide();

        auth = FirebaseAuth.getInstance();
        database = FirebaseFirestore.getInstance();


        if (auth.getCurrentUser() != null) {
            startActivity(new Intent(RegistrationActivity.this, MainActivity.class));
            finish();
        }

        name = findViewById(R.id.name);
        email = findViewById(R.id.email);
        password = findViewById(R.id.password);
        textView = findViewById(R.id.signInWithGoogle);
        /*GoogleSignInOptions options = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.default_web_client_id))
                .requestEmail()
                .setAccountChooserEnabled(true) // enable account chooser
                .build();*/

        GoogleSignInOptions options = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.default_web_client_id))
                .requestEmail()
                .build();
        client = GoogleSignIn.getClient(this, options);



        textView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = client.getSignInIntent();
                startActivityForResult(i, 1234);

            }
        });


        sharedPreferences = getSharedPreferences("onBoardingScreen", MODE_PRIVATE);
        boolean isFirstTime = sharedPreferences.getBoolean("firstTime", true);
        if (isFirstTime) {
            SharedPreferences.Editor editor= sharedPreferences.edit();
            editor.putBoolean("firstTime", false);
            editor.commit();
            Intent intent = new Intent(RegistrationActivity.this, OnBoardingActivity.class);
            startActivity(intent);
            finish();
        }

    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode==1234) {
            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
            try {
                GoogleSignInAccount account = task.getResult(ApiException.class);
                AuthCredential credential = GoogleAuthProvider.getCredential(account.getIdToken(), null);
                FirebaseAuth.getInstance().signInWithCredential(credential)
                        .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                if (task.isSuccessful()){
                                    FirebaseUser user = auth.getCurrentUser();
                                    // Show a popup with the user's email address
                                    String email = user.getEmail();
                                    Toast.makeText(RegistrationActivity.this, "Logged in as: " + email, Toast.LENGTH_SHORT).show();
                                    Intent intent= new Intent(getApplicationContext(), MainActivity.class);
                                    startActivity(intent);
                                    
                                }else {
                                    Toast.makeText(RegistrationActivity.this, task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                                }
                            }
                        });
            } catch (ApiException e) {
                throw new RuntimeException(e);
            }

        }


    }


    public void signup(View view) {

        String userName = name.getText().toString();
        String userEmail = email.getText().toString();
        String userPassword = password.getText().toString();

        if (TextUtils.isEmpty(userName)) {
            Toast.makeText(this, "Enter the Name!", Toast.LENGTH_SHORT).show();
            return;
        }
        if (TextUtils.isEmpty(userEmail)) {
            Toast.makeText(this, "Enter the Email!", Toast.LENGTH_SHORT).show();
            return;
        }

        if (TextUtils.isEmpty(userPassword)) {
            Toast.makeText(this, "Enter the Password!", Toast.LENGTH_SHORT).show();
            return;
        }
        if (userPassword.length() < 6) {
            Toast.makeText(this, "Password too short!, Enter minimum 6 characters!", Toast.LENGTH_SHORT).show();
            return;
        }

        //Create User
        auth.createUserWithEmailAndPassword(userEmail, userPassword)
                .addOnCompleteListener(RegistrationActivity.this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {

                            UserModel userModel= new UserModel(userName, userEmail, userPassword);
                            String id= task.getResult().getUser().getUid();
                            DocumentReference userRef = database.collection("Users").document(id);

                            // set the data of the document to the UserModel object
                            userRef.set(userModel)
                                    .addOnSuccessListener(new OnSuccessListener<Void>() {
                                        @Override
                                        public void onSuccess(Void aVoid) {
                                            // data successfully saved
                                        }
                                    })
                                    .addOnFailureListener(new OnFailureListener() {
                                        @Override
                                        public void onFailure(@NonNull Exception e) {
                                            // an error occurred while saving the data
                                        }
                                    });
                            Toast.makeText(RegistrationActivity.this, "Successfully Register!", Toast.LENGTH_SHORT).show();
                            startActivity(new Intent(RegistrationActivity.this, MainActivity.class));
                            finish();
                        } else {
                            Toast.makeText(RegistrationActivity.this, "Registration Failed!" + task.getException(), Toast.LENGTH_SHORT).show();
                        }

                    }
                });
        //
    }

    public void signin(View view) {
       startActivity(new Intent(RegistrationActivity.this, LoginActivity.class));
        finish();

        // Sign out from Google
      /* client.signOut()
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        // Redirect to login activity
                        Intent intent = new Intent(getApplicationContext(), RegistrationActivity.class);
                        startActivity(intent);
                        finish();
                    }
                });*/
    }
}
