package com.example.ecommerce.User;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.ecommerce.Fragments.ModelProfileFragment;
import com.example.ecommerce.Fragments.OrdersFragment;
import com.example.ecommerce.Fragments.UserHomeFragment;
import com.example.ecommerce.Model.User;
import com.example.ecommerce.R;
import com.example.ecommerce.Seller.SelectProductCategoryActivity;
import com.example.ecommerce.StartActivity;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.StorageTask;

import java.util.HashMap;

public class UserMainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {
    private DrawerLayout drawer;
    private NavigationView navigationView;
    private ImageView imageViewSearch,imageViewCart, imageProfile;
    private TextView tvName,tvEmail;

    public static final String TAG_ACTIVITY_FROM = "WhichActivity";
    private boolean isLoggedIn = false;


    private FirebaseUser fUser;
    private DatabaseReference mRef;
    private StorageTask storageTask;
    private  View headerView;

    private Uri mImageUri;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_main);


//        if (getIntent()==null) {
//        }else{
//
//          //  Toast.makeText(this, getIntent().getStringExtra("fromCFOA"), Toast.LENGTH_SHORT).show();
//           // intent.putExtra("fromCFOA","clearcart");
//
//           if (getIntent().getStringExtra(TAG_ACTIVITY_FROM).equals("fromCFOA")){
//                Toast.makeText(this, "clear", Toast.LENGTH_SHORT).show();
//                Log.d("theartist", "onCreate:mani ");
//            }
//
//        }

        fUser = FirebaseAuth.getInstance().getCurrentUser();
        init();
        setDrawer();
        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                    new UserHomeFragment()).commit();
            navigationView.setCheckedItem(R.id.nav_home);
        }
        setUserInfo();





        imageViewSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(UserMainActivity.this, SearchProductActivity.class));
            }
        });
        imageViewCart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
             startActivity(new Intent(UserMainActivity.this, CartActivity.class));
            }
        });



       // NavigationView navigationView = findViewById(R.id.nav_view);
//        navigationView.setNavigationItemSelectedListener(this);
//
//        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawer, toolbar,
//                R.string.navigation_drawer_open, R.string.navigation_drawer_close);
//        drawer.addDrawerListener(toggle);
//        toggle.syncState();
//        if (savedInstanceState == null) {
//            getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
//                    new MessageFragment()).commit();
//            navigationView.setCheckedItem(R.id.nav_message);
//        }







    }


    private void setUserInfo() {

       FirebaseDatabase.getInstance().getReference("Users")
               .child(fUser.getUid()).addValueEventListener(new ValueEventListener() {
           @Override
           public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
               User user = dataSnapshot.getValue(User.class);
               tvName.setText(user.getName());
               tvEmail.setText(user.getEmail());
               if (user.getImageUrl().equals("default")) {
                   Glide.with(UserMainActivity.this).load(R.mipmap.ic_launcher).into(imageProfile);
               } else {
                   Glide.with(UserMainActivity.this).load(user.getImageUrl()).into(imageProfile);
               }

           }

           @Override
           public void onCancelled(@NonNull DatabaseError databaseError) {

           }
       });

    }


    private void logoutUser(){

        FirebaseAuth.getInstance().signOut();

        SharedPreferences sharedPreferences = getSharedPreferences("sharedPrefs", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("type", "none");
        editor.apply();

        startActivity(new Intent(UserMainActivity.this, StartActivity.class)
                .addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP));

        finish();
    }

    private void setDrawer() {
        Toolbar toolbar = findViewById(R.id.toolbarMAin);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(UserMainActivity.this, drawer, toolbar,
                R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

     
    }

    private void init() {
        drawer = findViewById(R.id.drawer_layout);
        imageViewSearch = findViewById(R.id.image_search);
        imageViewCart =  findViewById(R.id.image_cart);

         navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
         headerView = navigationView.getHeaderView(0);
        tvName = headerView.findViewById(R.id.nav_header_name);
        tvEmail = headerView.findViewById(R.id.nav_header_email);
        imageProfile = headerView.findViewById(R.id.nav_header_profileimage);
    }



    @Override
    public void onBackPressed() {
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);

        }
        super.onBackPressed();
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.nav_home:
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                        new UserHomeFragment()).commit();


                break;
            case R.id.nav_cart:

                startActivity(new Intent(UserMainActivity.this, CartActivity.class));
                break;
            case R.id.nav_ordersUser:

                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                        new OrdersFragment()).commit();
                break;
            case R.id.nav_categories:
                startActivity(new Intent(UserMainActivity.this, SelectProductCategoryActivity.class));
                break;
            case R.id.nav_edit_profile:
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                        new ModelProfileFragment()).commit();
                break;
            case R.id.nav_logout:
                logoutUser();
                break;
            case R.id.nav_feedback:
                Toast.makeText(this, "nav_feedback", Toast.LENGTH_SHORT).show();
            case R.id.nav_contact:
                Toast.makeText(this, "nav_contact", Toast.LENGTH_SHORT).show();
//                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
//                        new UserContactFragment()).commit();
                break;
            case R.id.nav_aboutme:
                Toast.makeText(this, "nav_aboutme", Toast.LENGTH_SHORT).show();
                //                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
//                        new UserAboutmeFragment()).commit();
                break;
        }
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }


    @Override
    protected void onStart() {
        super.onStart();
        Log.d("theartist", "onStart: Umain ");
        status("Online : Viewing Main Page");
    }

    private void status(String status) {

        HashMap<String,Object> map = new HashMap<>();
        map.put("status",status);
        FirebaseDatabase.getInstance().getReference("Users").child(FirebaseAuth.getInstance().getUid()).updateChildren(map);
    }

    @Override
    protected void onPause() {
        super.onPause();

        Log.d("theartist", "onPause: Umain ");
        status("offline");
    }
}