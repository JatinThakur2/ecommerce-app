package com.example.ecommerce.User;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.ecommerce.Model.Product;
import com.example.ecommerce.R;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;

import java.io.Serializable;
import java.util.HashMap;

public class UserProductListActivity extends AppCompatActivity {


    private TextView categorytext;
    private String category;

    private  RecyclerView recyclerView;
    private  FirebaseRecyclerAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_product_list);

        category = getIntent().getStringExtra("category");

        categorytext = findViewById(R.id.category);
        categorytext.setText("Products Of Category : "+category);

        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));


        Query query= FirebaseDatabase.getInstance().getReference().child("Products").orderByChild("category")
                .equalTo(category);


        FirebaseRecyclerOptions<Product> options =
                new FirebaseRecyclerOptions.Builder<Product>()
                        .setQuery(query, Product.class)
                        .build();


        adapter = new FirebaseRecyclerAdapter<Product, MyUserViewHolder>(options) {
            @Override
            public MyUserViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
                View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.product_info_item, parent, false);
                return new MyUserViewHolder(view);
            }

            @Override
            protected void onBindViewHolder(MyUserViewHolder holder, int position, final Product product) {




                holder.productName.setText("Name : "+product.getName());
                holder.productId.setText("Product Id : "+product.getProductid());
                holder.productPrice.setText("Price : INR"+product.getPrice());
                holder.productDesc.setText("Description : "+product.getDescription());
                holder.productState.setText("Processing State : "+product.getState());

                Glide.with(UserProductListActivity.this).load(product.getImageUrl()).into(holder.productImage);

                holder.itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        startActivity(new Intent(UserProductListActivity.this, ProductDetailsActivity.class)
                                .putExtra("Productt", (Serializable) product));

                    }
                });
            }


        };

        recyclerView.setAdapter(adapter);

    }

    @Override
    public void onStart() {
        super.onStart();
        adapter.startListening();
        Log.d("theartist", "onStart: UProduct ");
        status("Online : Viewing ProductList");
    }

    @Override
    public void onPause() {
        super.onPause();
        adapter.stopListening();
        Log.d("theartist", "onPause: UProduct ");
        status("offline");
    }





    private void status(String status) {

        HashMap<String,Object> map = new HashMap<>();
        map.put("status",status);
        FirebaseDatabase.getInstance().getReference("Users").child(FirebaseAuth.getInstance().getUid()).updateChildren(map);
    }




}
class MyUserViewHolder extends RecyclerView.ViewHolder{

    TextView productName,productState,productId,productPrice,productDesc;
    ImageView productImage;

    public MyUserViewHolder(@NonNull View itemView) {
        super(itemView);
        productName = itemView.findViewById(R.id.productName);
        productState = itemView.findViewById(R.id.productState);
        productId = itemView.findViewById(R.id.productId);
        productPrice = itemView.findViewById(R.id.productPrice);
        productDesc = itemView.findViewById(R.id.productDescription);
        productImage = itemView.findViewById(R.id.productImage);
    }
}