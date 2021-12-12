package com.example.ecommerce.Fragments;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.ecommerce.Model.Product;
import com.example.ecommerce.R;
import com.example.ecommerce.Seller.SelectProductCategoryActivity;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;

import static android.content.ContentValues.TAG;

public class SellerProductsFragment extends Fragment {



    EditText searchbar;
    private Button addproduct;
    RecyclerView recyclerView;
    FirebaseRecyclerAdapter adapter;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_seller_products, container, false);





        addproduct = view.findViewById(R.id.addProductSeller);
        searchbar = view.findViewById(R.id.searchProductsSeller);
        recyclerView = view.findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        addproduct.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                  startActivity(new Intent(getContext(), SelectProductCategoryActivity.class));
            }
        });


        Query query=FirebaseDatabase.getInstance().getReference().child("Products").orderByChild("sellerid")
                .equalTo(FirebaseAuth.getInstance().getUid());



        FirebaseRecyclerOptions<Product> options =
                new FirebaseRecyclerOptions.Builder<Product>()
                        .setQuery(query, Product.class)
                        .build();


         adapter = new FirebaseRecyclerAdapter<Product, MyViewHolder>(options) {
            @Override
            public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
                View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.product_info_item, parent, false);
                return new MyViewHolder(view);
            }

            @Override
            protected void onBindViewHolder(MyViewHolder holder, int position, Product product) {

                holder.productName.setText("Name : "+product.getName());
                holder.productId.setText("Product Id : "+product.getProductid());
                holder.productPrice.setText("Price : INR"+product.getPrice());
                holder.productDesc.setText("Description : "+product.getDescription());
                holder.productState.setText("Processing State : "+product.getState());

                Glide.with(getContext()).load(product.getImageUrl()).into(holder.productImage);
            }


        };

        recyclerView.setAdapter(adapter);





        return  view;
    }

    @Override
    public void onStart() {
        super.onStart();
        adapter.startListening();
    }

    @Override
    public void onPause() {
        super.onPause();
        adapter.stopListening();
    }
}

 class MyViewHolder extends RecyclerView.ViewHolder{

    TextView productName,productState,productId,productPrice,productDesc;
    ImageView productImage;

    public MyViewHolder(@NonNull View itemView) {
        super(itemView);
        productName = itemView.findViewById(R.id.productName);
        productState = itemView.findViewById(R.id.productState);
        productId = itemView.findViewById(R.id.productId);
        productPrice = itemView.findViewById(R.id.productPrice);
        productDesc = itemView.findViewById(R.id.productDescription);
        productImage = itemView.findViewById(R.id.productImage);
    }
}