package com.example.test1.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.test1.R;
import com.example.test1.entity.PainRecord;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;

public class RegisterUser extends AppCompatActivity implements View.OnClickListener {

    private TextView banner;
    private Button registerUser;
    private EditText editTextFullName, editTextEmail, editTextPassword;
    private ProgressBar progressBar;

    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_user);

        mAuth = FirebaseAuth.getInstance();
        banner = (TextView) findViewById(R.id.banner);
        banner.setOnClickListener(this);

        registerUser = findViewById(R.id.regiserUserBtn);
        registerUser.setOnClickListener(this);
        editTextFullName = (EditText) findViewById(R.id.fullName);
        editTextEmail = (EditText) findViewById(R.id.registerEmail);
        editTextPassword = (EditText) findViewById(R.id.registerPwd);

        progressBar = (ProgressBar) findViewById(R.id.progressBar);
    }

    @Override
    public void onClick(View v) {
        if (v.getId()==R.id.banner){
            startActivity(new Intent(this, MainActivity.class));
        }else if (v.getId()==R.id.regiserUserBtn){
            registerUser();
        }


    }

    private void registerUser() {
        String email = editTextEmail.getText().toString().trim();
        String password = editTextPassword.getText().toString().trim();
        String fullName = editTextFullName.getText().toString().trim();

        if(fullName.isEmpty()){
            editTextFullName.setError("please enter your full name");
            editTextFullName.requestFocus();
            return;
        }

        if(email.isEmpty()){
            editTextEmail.setError("please enter your email");
            editTextEmail.requestFocus();
            return;
        }

        if(!Patterns.EMAIL_ADDRESS.matcher(email).matches()){
            editTextEmail.setError("please provide valid email");
            editTextEmail.requestFocus();
            return;
        }

        if(password.isEmpty()){
            editTextPassword.setError("please enter your passworrd");
            editTextPassword.requestFocus();
            return;
        }

        if(password.length()<6){
            editTextPassword.setError("min password length should be 6 characters");
            editTextPassword.requestFocus();
            return;
        }


        progressBar.setVisibility(View.VISIBLE);
        registerUser.setEnabled(false);

        mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(authTask -> {
                    if (!authTask.isSuccessful()) {
                        progressBar.setVisibility(View.GONE);
                        registerUser.setEnabled(true);
                        String errorMessage = authTask.getException() != null
                                ? authTask.getException().getMessage()
                                : "Unknown authentication error";
                        Toast.makeText(RegisterUser.this,
                                "Registration failed: " + errorMessage,
                                Toast.LENGTH_LONG).show();
                        return;
                    }

                    if (FirebaseAuth.getInstance().getCurrentUser() == null) {
                        progressBar.setVisibility(View.GONE);
                        registerUser.setEnabled(true);
                        Toast.makeText(RegisterUser.this,
                                "Registration failed: user was not created",
                                Toast.LENGTH_LONG).show();
                        return;
                    }

                    PainRecord painRecord = new PainRecord(email, 0, "", "", "");
                    String userId = FirebaseAuth.getInstance().getCurrentUser().getUid();

                    FirebaseDatabase.getInstance("https://myfirstapp-f0dd8-default-rtdb.firebaseio.com")
                            .getReference("Users")
                            .child(userId)
                            .setValue(painRecord)
                            .addOnCompleteListener(databaseTask -> {
                                progressBar.setVisibility(View.GONE);
                                registerUser.setEnabled(true);

                                if (databaseTask.isSuccessful()) {
                                    Toast.makeText(RegisterUser.this,
                                            "User registered successfully",
                                            Toast.LENGTH_LONG).show();
                                    Intent intent = new Intent(RegisterUser.this, MainActivity.class);
                                    startActivity(intent);
                                    finish();
                                } else {
                                    String errorMessage = databaseTask.getException() != null
                                            ? databaseTask.getException().getMessage()
                                            : "Unknown database error";
                                    Toast.makeText(RegisterUser.this,
                                            "Database error: " + errorMessage,
                                            Toast.LENGTH_LONG).show();
                                }
                            });
                });

    }
}