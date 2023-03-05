package com.example.bankingsystem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class SignUp extends AppCompatActivity {

    DatabaseReference myRef =FirebaseDatabase.getInstance().getReferenceFromUrl("https://banking-system-25de3-default-rtdb.firebaseio.com/");

    EditText etEmail, etPassword;
    TextView btnLogin;
    Button btnSignup;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        etEmail = findViewById(R.id.etEmail);
        etPassword = findViewById(R.id.etPassword);
        btnLogin = findViewById(R.id.btnLogin);
        btnSignup = findViewById(R.id.btnSignup);

        btnSignup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String getEmail = etEmail.getText().toString();
                String getPass = etPassword.getText().toString();
                String email = etEmail.getText().toString();
                String name = email.replaceAll("@.*","").replaceAll("[^a-zA-Z] + ", " ").trim();

                if(getEmail.equals("") && getPass.equals("")) {
                    if(getEmail.equals("")) {
                        Toast.makeText(getApplicationContext(), "Fill up Email.", Toast.LENGTH_LONG).show();
                    } else if(getPass.equals("")) {
                        Toast.makeText(getApplicationContext(), "Fill up Password.", Toast.LENGTH_LONG).show();
                    }
                    Toast.makeText(getApplicationContext(), "Please fill up the field.", Toast.LENGTH_LONG).show();
                } else {
                    myRef.child("users").addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                            myRef.child("users").child(name).child("email").setValue(getEmail);
                            myRef.child("users").child(name).child("password").setValue(getPass);

                            Toast.makeText(getApplicationContext(), name + " registered successfully", Toast.LENGTH_LONG).show();
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {

                        }
                    });
                }
            }
        });

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                login();
            }
        });
    }
    public void login() {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
        finish();
    }
}