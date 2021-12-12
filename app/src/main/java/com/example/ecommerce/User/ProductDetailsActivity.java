package com.example.ecommerce.User;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.ecommerce.Model.Product;
import com.example.ecommerce.Model.Seller;
import com.example.ecommerce.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;

public class ProductDetailsActivity extends AppCompatActivity {

    TextView productName, productCategory, productPrice, productDescription,productId;
    TextView sellerName,sellerId;
    Button addToCart, buyNow;
    ImageView productImage, backImage, cartImage;

    FirebaseUser fUser;
    DatabaseReference rootRef;
    Product product;
    Seller seller;

    String productid;
    String sellername;
    int productprice;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_details);




         product = (Product) getIntent().getSerializableExtra("Productt");

        productid = product.getProductid();
//        productprice = Integer.parseInt(product.getPrice());
////                productprice


        fUser = FirebaseAuth.getInstance().getCurrentUser();
        rootRef = FirebaseDatabase.getInstance().getReference();

        init();
        setProductDetails(productid);
        //can also send product object  for saving queries instead fetching all the data again

        addToCart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //checkCart();
                addToCart(productid);
            }
        });

        buyNow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addToCart(productid);
            }
        });

    }


    private void addToCart(final String productid) {

        final ProgressDialog pd = new ProgressDialog(this);
        pd.setMessage("Uploading");
        pd.show();


        DatabaseReference cartUserRef = FirebaseDatabase.getInstance().getReference()
                .child("Cart").child(fUser.getUid());


        HashMap<String, Object> map = new HashMap<>();
        map.put("totalprice", "1");
        map.put("quantity","1");
        map.put("productid", productid);
        map.put("productname",product.getName());
        map.put("productimageUrl",product.getImageUrl());
        map.put("productprice",product.getPrice());
        map.put("sellerid",product.getSellerid());
        map.put("userid",FirebaseAuth.getInstance().getUid());


        cartUserRef.child(productid).setValue(map).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                Toast.makeText(ProductDetailsActivity.this, "Updating Cart..", Toast.LENGTH_SHORT).show();
                pd.dismiss();
                startActivity(new Intent(ProductDetailsActivity.this, CartActivity.class));
                               // .putExtra("Productt", (Serializable) product));

            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(ProductDetailsActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                pd.dismiss();
            }
        });
    }


    private void setProductDetails(final String productid) {

        rootRef.child("Products").child(productid).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                Product product = dataSnapshot.getValue(Product.class);





                Glide.with(ProductDetailsActivity.this).load(product.getImageUrl()).into(productImage);
                productName.setText(product.getName());
                productCategory.setText("Category "+product.getCategory());
                productPrice.setText("INR " + product.getPrice());
                productDescription.setText("Description : " + product.getDescription());
                sellerId.setText("Seller ID : "+product.getSellerid());
            //   sellerName.setText(sellername);
                productId.setText("Product Id"+ productid);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }

    private void init() {
        productName = findViewById(R.id.textView_productName);
        productCategory = findViewById(R.id.textView_productCategory);
        productPrice = findViewById(R.id.textView_productPrice);
        productDescription = findViewById(R.id.textView_productDescription);
        backImage = findViewById(R.id.image_back);
        cartImage = findViewById(R.id.image_cart);
        productImage = findViewById(R.id.image_product);
        addToCart = findViewById(R.id.button_AddToCart);
        buyNow = findViewById(R.id.button_Buy);
        sellerId = findViewById(R.id.sellerName);
        sellerName = findViewById(R.id.sellerId);
        productId = findViewById(R.id.productid);
    }
}