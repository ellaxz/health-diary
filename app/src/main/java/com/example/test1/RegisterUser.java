package com.example.test1;

import androidx.annotation.NonNull;
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

import com.example.test1.entity.PainRecord;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
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
        switch (v.getId()){
            case R.id.banner:
                startActivity(new Intent(this, MainActivity.class));
                break;
            case R.id.regiserUserBtn:
                registerUser();
                break;
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


        progressBar.setVisibility(View.GONE);
        registerUser.setEnabled(true);

        if (FirebaseAuth.getInstance().getCurrentUser() != null) {
            FirebaseAuth.getInstance()
                    .getCurrentUser()
                    .sendEmailVerification()
                    .addOnCompleteListener(verificationTask -> {
                        if (verificationTask.isSuccessful()) {
                            Toast.makeText(
                                    RegisterUser.this,
                                    "Registration successful. Please check your email.",
                                    Toast.LENGTH_LONG
                            ).show();
                        } else {
                            Toast.makeText(
                                    RegisterUser.this,
                                    "Registered, but verification email could not be sent.",
                                    Toast.LENGTH_LONG
                            ).show();
                        }

                        FirebaseAuth.getInstance().signOut();

                        Intent intent = new Intent(
                                RegisterUser.this,
                                MainActivity.class
                        );
                        startActivity(intent);
                        finish();
                    });
        }
    }
}