package com.example.ecommerce.Seller;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
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
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;

public class SellerLoginActivity extends AppCompatActivity {


    private EditText etEmail, etPassword;
    private Button buttonLogin;
    private TextView tvRegister;
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_seller_login);

        etEmail = findViewById(R.id.etEmailSeller);
        etPassword = findViewById(R.id.etPasswordSeller);
        buttonLogin = findViewById(R.id.buttonLoginSeller);
        tvRegister = findViewById(R.id.textSignupSeller);

        mAuth = FirebaseAuth.getInstance();

        tvRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(SellerLoginActivity.this , SellerRegisterActivity.class)
                        .addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK));
            }
        });


        buttonLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String txt_email = etEmail.getText().toString();
                String txt_password = etPassword.getText().toString();

                if (TextUtils.isEmpty(txt_email) || TextUtils.isEmpty(txt_password)){
                    Toast.makeText(SellerLoginActivity.this, "Empty Credentials!", Toast.LENGTH_SHORT).show();
                } else {
                    loginUser(txt_email , txt_password);
                }
            }
        });


    }

    private void loginUser(String email, String password) {
        mAuth.signInWithEmailAndPassword(email , password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()){
                    Toast.makeText(SellerLoginActivity.this, "Update the profile " +
                            "for better expereince", Toast.LENGTH_SHORT).show();

                    SharedPreferences sharedPreferences = getSharedPreferences("sharedPrefs", MODE_PRIVATE);
                    SharedPreferences.Editor editor = sharedPreferences.edit();
                    editor.putString("type", "Seller");
                    editor.apply();

                    startActivity(new Intent(SellerLoginActivity.this , SellerMainActivity.class)
                            .addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP));
                    finish();
                }
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(SellerLoginActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });

    }


}