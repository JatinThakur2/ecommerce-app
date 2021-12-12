package com.example.ecommerce.Admin;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;


import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.ecommerce.Fragments.AdminHomeFragment;
import com.example.ecommerce.Fragments.AdminVerifyProductsFragment;
import com.example.ecommerce.Fragments.AdminViewSellersFragment;
import com.example.ecommerce.Fragments.AdminViewUsersFragment;
import com.example.ecommerce.Fragments.OrdersFragment;
import com.example.ecommerce.Fragments.UserHomeFragment;
import com.example.ecommerce.R;
import com.example.ecommerce.StartActivity;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;

public class AdminPanelActivity extends AppCompatActivity {


    private final String TAG ="theartist";
    DatabaseReference mRootRef;
    FirebaseAuth mAuth;
    //Texte

    private BottomNavigationView bottomNavigationView;
    private Fragment selectorFragment;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_panel);


        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                    new AdminHomeFragment()).commit();
        }

        bottomNavigationView = findViewById(R.id.bottom_navigation);
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.nav_home:
                        selectorFragment = new AdminHomeFragment();
                        break;
                    case R.id.nav_order:
                        selectorFragment = new OrdersFragment();
                        break;
                    case R.id.nav_product:
                        selectorFragment = new AdminVerifyProductsFragment();
                        break;
                    case R.id.nav_seller:
                       selectorFragment = new AdminViewSellersFragment();
                        break;
                    case R.id.nav_user:
                    selectorFragment = new AdminViewUsersFragment();
                        break;
                }
                if (selectorFragment !=null){
                    getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,selectorFragment).commit();
                }
                return true;
            }
        });

//
//        Bundle intent = getIntent().getExtras();
//        if(intent!=null){
//            String profileId = intent.getString("publisherId");
//            getSharedPreferences("PROFILE",MODE_PRIVATE).edit().putString("profileId",profileId).apply();
//            getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,new ProfileFragment()).commit();
//            bottomNavigationView.setSelectedItemId(R.id.person);
//        }else {
//
//            getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new HomeFragment()).commit();
//        }

    }
}


//        total orders this month
//        current online users
//todays slaes and transition hightligts