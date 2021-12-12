package com.example.ecommerce.Seller;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
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
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;

public class SellerRegisterActivity extends AppCompatActivity {

    EditText etNumber,etEmail,etName,etPassword,etAddress;
    Button registerButton;
    TextView loginTextview;
    private DatabaseReference mRootRef;
    private FirebaseAuth mAuth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_seller_register);

        etEmail = findViewById(R.id.etEmail);
        etName    = findViewById(R.id.etName);
        etAddress = findViewById(R.id.etAddress);
        etNumber = findViewById(R.id.etPhoneNumber);
        etPassword = findViewById(R.id.etPassword);
        registerButton = findViewById(R.id.buttonSignup);
        loginTextview = findViewById(R.id.textSignin);

        mRootRef = FirebaseDatabase.getInstance().getReference();
        mAuth = FirebaseAuth.getInstance();



        registerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String name = etName.getText().toString();
                String number = etNumber.getText().toString();
                String email = etEmail.getText().toString();
                String address = etAddress.getText().toString();
                String password = etPassword.getText().toString();

                if (TextUtils.isEmpty(name) || TextUtils.isEmpty(number)|| TextUtils.isEmpty(address)  || TextUtils.isEmpty(email) || TextUtils.isEmpty(password)){
                    Toast.makeText(SellerRegisterActivity.this, "Fill all the credentials", Toast.LENGTH_SHORT).show();
                }else if (password.length()<6){
                    Toast.makeText(SellerRegisterActivity.this, "Lenght of Password should be greater than 6 digits", Toast.LENGTH_SHORT).show();
                }else{
                    registerUsers(name,email,address,number,password);
                }
            }
        });

        loginTextview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(SellerRegisterActivity.this , SellerLoginActivity.class));
            }
        });

    }

    private void registerUsers(final String name, final String email,final String address, final String number, String password) {
        final ProgressDialog pd = new ProgressDialog(this);
        pd.setMessage("Uploading");
        pd.show();

        mAuth.createUserWithEmailAndPassword(email,password)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {


                        HashMap<String ,Object> map = new HashMap<>();
                        map.put("name",name);
                        map.put("email",email);
                        map.put("number",number);
                        map.put("id",mAuth.getCurrentUser().getUid()  );
                        map.put("address",address);
                        map.put("city","default");
                        map.put("state","default");
                        map.put("imageUrl","default");
                        map.put("status","default");

                        mRootRef.child("Sellers").child(mAuth.getCurrentUser().getUid()).setValue(map).addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                Toast.makeText(SellerRegisterActivity.this, "Update the profile " +
                                        "for better expereince", Toast.LENGTH_SHORT).show();
                                pd.dismiss();
                                SharedPreferences sharedPreferences = getSharedPreferences("sharedPrefs", MODE_PRIVATE);
                                SharedPreferences.Editor editor = sharedPreferences.edit();
                                editor.putString("type", "Seller");
                                editor.apply();
                                startActivity(new Intent(SellerRegisterActivity.this, SellerMainActivity.class)
                                        .addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP));
                                finish();
                            }
                        }).addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                Toast.makeText(SellerRegisterActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                                pd.dismiss();
                            }
                        });
                    }
                }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {

                Toast.makeText(SellerRegisterActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                pd.dismiss();
            }
        });



    }
}

