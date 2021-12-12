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
import com.example.ecommerce.Model.Seller;
import com.example.ecommerce.Model.Seller;
import com.example.ecommerce.R;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;


public class AdminViewSellersFragment extends Fragment {


    RecyclerView recyclerView;

    FirebaseRecyclerAdapter adapter;



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view =  inflater.inflate(R.layout.fragment_admin_view_sellers, container, false);
        Toolbar toolbar = view.findViewById(R.id.toolbar);
        toolbar.setTitle("Sellers");
        toolbar.setTitleTextColor(getResources().getColor(R.color.colorWhite));
        recyclerView = view.findViewById(R.id.seller_recycler_view);


        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        Query query= FirebaseDatabase.getInstance().getReference().child("Sellers");



        FirebaseRecyclerOptions<Seller> options =
                new FirebaseRecyclerOptions.Builder<Seller>()
                        .setQuery(query, Seller.class)
                        .build();
        adapter = new FirebaseRecyclerAdapter<Seller, SellerViewHolder>(options) {

            @Override
            public SellerViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
                View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.seller_item, parent, false);

                return new SellerViewHolder(view);
            }

            @Override
            protected void onBindViewHolder(SellerViewHolder holder, int position, Seller seller) {

                holder.sellerid.setText(seller.getId());

                holder.sellername.setText(seller.getName());
                holder.sellernumber.setText(String.valueOf(seller.getNumber()));
                holder.selleremail.setText(seller.getEmail());
                holder.selleraddress.setText(seller.getAddress());
                holder.sellercity.setText(seller.getCity());
                holder.sellerstate.setText(seller.getState());

                Glide.with(getContext()).load(seller.getImageUrl()).into(holder.sellerimage);


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
class SellerViewHolder extends RecyclerView.ViewHolder{

    TextView sellerid,sellername,sellernumber,selleremail,selleraddress,sellercity,sellerstate;
ImageView sellerimage;

    public SellerViewHolder(@NonNull View itemView) {
        super(itemView);
        sellerid = itemView.findViewById(R.id.sellerid);
        sellerimage = itemView.findViewById(R.id.sellerimage);
        sellername = itemView.findViewById(R.id.sellername);
        sellernumber = itemView.findViewById(R.id.sellernumber);
        selleremail = itemView.findViewById(R.id.selleremail);
        selleraddress = itemView.findViewById(R.id.selleraddress);
        sellercity = itemView.findViewById(R.id.sellercity);
        sellerstate = itemView.findViewById(R.id.sellerstate);
     
    }
}
