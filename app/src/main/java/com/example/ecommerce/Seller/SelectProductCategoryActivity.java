package com.example.ecommerce.Seller;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.example.ecommerce.R;
import com.example.ecommerce.User.UserProductListActivity;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;

public class SelectProductCategoryActivity extends AppCompatActivity implements View.OnClickListener{

    private CardView cardCamera,cardPhone, cardLaptop,cardShoes, cardBooks,cardStationary,cardBeauty,cardHealth,cardMusic,cardToys,cardGaming,cardKitchen;

    private String accessType;
    private Class<?> cls;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_product_category);



        cardCamera = findViewById(R.id.cardCamera);
        cardPhone = findViewById(R.id.cardPhone);
        cardLaptop = findViewById(R.id.cardLaptop);
        cardShoes = findViewById(R.id.cardShoe);
        cardBooks = findViewById(R.id.cardBook);
        cardStationary = findViewById(R.id.cardStationary);
        cardBeauty = findViewById(R.id.cardBeauty);
        cardHealth = findViewById(R.id.cardHealth);
        cardMusic = findViewById(R.id.cardMusic);
        cardToys = findViewById(R.id.cardToys);
        cardKitchen = findViewById(R.id.cardKitchen);
        cardGaming = findViewById(R.id.cardGaming);


        cardPhone.setOnClickListener(this);
        cardCamera.setOnClickListener(this);
        cardLaptop.setOnClickListener(this);
        cardShoes.setOnClickListener(this);
        cardBooks.setOnClickListener(this);
        cardStationary.setOnClickListener(this);
        cardBeauty.setOnClickListener(this);
        cardHealth.setOnClickListener(this);
        cardMusic.setOnClickListener(this);
        cardToys.setOnClickListener(this);
        cardGaming.setOnClickListener(this);
        cardKitchen.setOnClickListener(this);


        SharedPreferences sharedPreferences = getSharedPreferences("sharedPrefs", Context.MODE_PRIVATE);
        String type = sharedPreferences.getString("type","");


        if (type.equals("User")){
            cls = UserProductListActivity.class;
        }else if (type.equals("Seller")){
            cls = SellerAddProductsActivity.class;
        }


    }

    @Override
    public void onClick(View view) {



        switch (view.getId()){
            case R.id.cardCamera:
//                startActivity(new Intent(SelectProductCategoryActivity.this, SellerAddProductsActivity.class)
//                        .putExtra("category","Camera"));
                                startActivity(new Intent(SelectProductCategoryActivity.this, cls)
                        .putExtra("category","Camera"));
                break;
            case R.id.cardPhone:
                startActivity(new Intent(SelectProductCategoryActivity.this,cls)
                        .putExtra("category","Phone"));
                break;
            case R.id.cardLaptop:
                startActivity(new Intent(SelectProductCategoryActivity.this, cls)
                        .putExtra("category","Laptop"));
                break;
            case R.id.cardShoe:
                startActivity(new Intent(SelectProductCategoryActivity.this, cls)
                        .putExtra("category","Shoes"));
                break;
            case R.id.cardBook:
//                startActivity(new Intent(SelectProductCategoryActivity.this, cls.class)
//                        .putExtra("category","Book"));
                startActivity(new Intent(SelectProductCategoryActivity.this, cls)
                        .putExtra("category","Book"));
                break;
            case R.id.cardStationary:
                startActivity(new Intent(SelectProductCategoryActivity.this, cls)
                        .putExtra("category","Stationary"));
                break;
            case R.id.cardBeauty:
                startActivity(new Intent(SelectProductCategoryActivity.this, cls)
                        .putExtra("category","Beauty"));
                break;
            case R.id.cardHealth:
                startActivity(new Intent(SelectProductCategoryActivity.this, cls)
                        .putExtra("category","Health"));
                break;
            case R.id.cardMusic:
                startActivity(new Intent(SelectProductCategoryActivity.this, cls)
                        .putExtra("category","Music"));
                break;
            case R.id.cardToys:
                startActivity(new Intent(SelectProductCategoryActivity.this, cls)
                        .putExtra("category","Toy"));
                break;
            case R.id.cardGaming:
                startActivity(new Intent(SelectProductCategoryActivity.this, cls)
                        .putExtra("category","Gaming"));
                break;
            case R.id.cardKitchen:
                startActivity(new Intent(SelectProductCategoryActivity.this, cls)
                        .putExtra("category","Kitchen"));
                break;

        }
    }



}