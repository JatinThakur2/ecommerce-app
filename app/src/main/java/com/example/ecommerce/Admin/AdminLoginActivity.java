package com.example.ecommerce.Admin;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.ecommerce.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class AdminLoginActivity extends AppCompatActivity {


    private EditText mEmail;
    private EditText mPassword;
    private Button mLoginButton;

    FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_login);

        mEmail = findViewById(R.id.etEmail);
        mPassword = findViewById(R.id.etPassword);
        mLoginButton = findViewById(R.id.buttonLogin);

        mAuth = FirebaseAuth.getInstance();


        mLoginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String password = mPassword.getText().toString();
                String email = mEmail.getText().toString();

                if ((TextUtils.isEmpty(email)||TextUtils.isEmpty(password))){
                    Toast.makeText(AdminLoginActivity.this, "Incomplete credentials", Toast.LENGTH_SHORT).show();
                }else {
                    loginAdmin(email, password);
                }
            }
        });

    }

    private void loginAdmin(String email, String password) {
        mAuth.signInWithEmailAndPassword(email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()){
                    Toast.makeText(AdminLoginActivity.this, "Update the profile " +
                            "for better expereince", Toast.LENGTH_SHORT).show();
                    SharedPreferences sharedPreferences = getSharedPreferences("sharedPrefs", MODE_PRIVATE);
                    SharedPreferences.Editor editor = sharedPreferences.edit();
                    editor.putString("type", "Admin");
                    editor.apply();
                    startActivity(new Intent(AdminLoginActivity.this,AdminPanelActivity.class).addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK |Intent.FLAG_ACTIVITY_CLEAR_TOP));
                    finish();
                }
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(AdminLoginActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
}