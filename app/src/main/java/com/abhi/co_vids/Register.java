package com.abhi.co_vids;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.common.util.NumberUtils;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

import io.opencensus.internal.StringUtil;

public class Register extends AppCompatActivity {

    private EditText email, password, passwordConfirm, phone, age, gender;
    private Button toLogin;
    private FirebaseAuth mAuth = FirebaseAuth.getInstance();
    private FirebaseFirestore fstore = FirebaseFirestore.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        email = findViewById(R.id.email);
        password = findViewById(R.id.password);
        passwordConfirm = findViewById(R.id.passwordConfirm);
        phone = findViewById(R.id.phoneNumber);
        age = findViewById(R.id.age);
        gender = findViewById(R.id.gender);
        if (mAuth.getCurrentUser() != null) {
            startActivity(new Intent(getApplicationContext(), MainActivity.class));
            finish();
        }
        findViewById(R.id.login).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String emailText = email.getText().toString().trim();
                String passwordText = password.getText().toString().trim();
                final String phoneText = phone.getText().toString().trim();
                final String ageText = age.getText().toString().trim();
                final String genderText = gender.getText().toString().trim().toLowerCase();

                if (!emailText.isEmpty() && !Patterns.EMAIL_ADDRESS.matcher(emailText).matches()) {
                    CharSequence message = "please enter a valid email";
                    Toast.makeText(getApplicationContext(), message, Toast.LENGTH_SHORT).show();
                } else if (!passwordText.equals(passwordConfirm.getText().toString())) {
                    CharSequence message = "Passwords don't match";
                    Toast.makeText(getApplicationContext(), message, Toast.LENGTH_SHORT).show();
                } else if (!phone.getText().toString().matches( "^[0-9]{10,13}$")) {
                    CharSequence text = "Please enter valid phone number";
                    Toast.makeText(getApplicationContext(), text, Toast.LENGTH_SHORT).show();
                } else if (!isNumeric(ageText) && Integer.parseInt(ageText) < 1 || Integer.parseInt(ageText) > 130) {
                    Toast.makeText(getApplicationContext(), "Please enter valid age", Toast.LENGTH_SHORT).show();
                } else if (!("female f".contains(genderText) || "male m".contains(genderText) || "other".equals(genderText))) {
                    Toast.makeText(getApplicationContext(), "Please enter either: male, female, or other", Toast.LENGTH_SHORT).show();
                } else {
                    mAuth.createUserWithEmailAndPassword(emailText, passwordText).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()) {
                                Toast.makeText(getApplicationContext(), "User Created", Toast.LENGTH_SHORT).show();
                                String uid = mAuth.getCurrentUser().getUid();
                                DocumentReference docRef = fstore.collection("users").document(uid);
                                Map<String, String> umap = new HashMap<>();
                                umap.put("phone", phoneText);
                                umap.put("email", emailText);
                                umap.put("gender", genderText);
                                umap.put("age", ageText);
                                docRef.set(umap).addOnSuccessListener(new OnSuccessListener<Void>() {
                                    @Override
                                    public void onSuccess(Void aVoid) {
                                        // nu: new user
                                        Log.d("nu", "New user created successfully");
                                    }
                                }).addOnFailureListener(new OnFailureListener() {
                                    @Override
                                    public void onFailure(@NonNull Exception e) {
                                        // fsf: Firestore Fail
                                        Log.e("fsf", "onFailure: " + e.getMessage(), e);
                                    }
                                });
                                startActivity(new Intent(getApplicationContext(), MainActivity.class));
                            } else {
                                Toast.makeText(getApplicationContext(), task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                            }
                        }
                    }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Log.d("of", "onFailure: " + e.getMessage());
                        }
                    });
                }
            }
        });
        findViewById(R.id.toLogin).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), Login.class));
            }
        });
    }

    public boolean isNumeric(String num) {
        try {
            Integer.parseInt(num);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }
}
