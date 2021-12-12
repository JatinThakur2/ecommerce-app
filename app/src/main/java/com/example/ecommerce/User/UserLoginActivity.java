package com.example.ecommerce.User;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.ecommerce.R;
import com.example.ecommerce.Seller.SellerMainActivity;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class UserLoginActivity extends AppCompatActivity {

    private EditText etEmail, etPassword;
    private Button buttonLogin;
    private TextView tvRegister;
    private FirebaseAuth mAuth;


   // public static final String SHARED_PREFS = "sharedPrefs";
    //public static final String TYPE = "type";
   // public static final String SWITCH1 = "switch1";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_login);

        etEmail = findViewById(R.id.etEmail);
        etPassword = findViewById(R.id.etPassword);
        buttonLogin = findViewById(R.id.buttonLogin);
        tvRegister = findViewById(R.id.textSignup);

        mAuth = FirebaseAuth.getInstance();

        tvRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(UserLoginActivity.this , UserRegisterActivity.class)
                        .addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK));
            }
        });


        buttonLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String txt_email = etEmail.getText().toString();
                String txt_password = etPassword.getText().toString();

                if (TextUtils.isEmpty(txt_email) || TextUtils.isEmpty(txt_password)){
                    Toast.makeText(UserLoginActivity.this, "Empty Credentials!", Toast.LENGTH_SHORT).show();
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
                    Toast.makeText(UserLoginActivity.this, "Update the profile " +
                            "for better expereince", Toast.LENGTH_SHORT).show();

                    SharedPreferences sharedPreferences = getSharedPreferences("sharedPrefs", MODE_PRIVATE);
                    SharedPreferences.Editor editor = sharedPreferences.edit();
                    editor.putString("type", "User");
                    editor.apply();


                    startActivity(new Intent(UserLoginActivity.this , UserMainActivity.class)
                            .addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP));
                    finish();
                }
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(UserLoginActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });

    }
}