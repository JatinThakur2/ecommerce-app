package com.example.ecommerce.Fragments;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.model.stream.BaseGlideUrlLoader;
import com.example.ecommerce.Model.Product;
import com.example.ecommerce.Model.Seller;
import com.example.ecommerce.R;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;


public class AdminVerifyProductsFragment extends Fragment {

    EditText searchbar;
    RecyclerView recyclerView;

    FirebaseRecyclerAdapter adapter;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_admin_verify_products, container, false);

        Toolbar toolbar = view.findViewById(R.id.toolbar);
        toolbar.setTitle("Products");
        toolbar.setTitleTextColor(getResources().getColor(R.color.colorWhite));

        recyclerView = view.findViewById(R.id.recyclerView);


        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        Query query= FirebaseDatabase.getInstance().getReference().child("Products");

        FirebaseRecyclerOptions<Product> options =
                new FirebaseRecyclerOptions.Builder<Product>()
                        .setQuery(query, Product.class)
                        .build();
        adapter = new FirebaseRecyclerAdapter<Product, ProductViewHolder>(options) {

            @Override
            public ProductViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
                View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.product_info_item, parent, false);

                return new ProductViewHolder(view);
            }

            @Override
            protected void onBindViewHolder(ProductViewHolder holder, int position, Product product) {

                holder.productName.setText(product.getName());

                holder.productId.setText(product.getProductid());
                holder.productState.setText(String.valueOf(product.getState()));
                holder.productPrice.setText(product.getPrice());
                holder.productDescription.setText(product.getPrice());

                Glide.with(getContext()).load(product.getImageUrl()).into(holder.productImage);


            }
        };


        recyclerView.setAdapter(adapter);


        return view;
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


class ProductViewHolder extends RecyclerView.ViewHolder{

    TextView productName,productId,productState,productPrice,productDescription;

    ImageView productImage;

    public ProductViewHolder(@NonNull View itemView) {
        super(itemView);
        productName = itemView.findViewById(R.id.productName);
        productId = itemView.findViewById(R.id.productId);
        productState = itemView.findViewById(R.id.productState);
        productPrice = itemView.findViewById(R.id.productPrice);
        productDescription = itemView.findViewById(R.id.productDescription);
        productImage = itemView.findViewById(R.id.productImage);

    }
}
