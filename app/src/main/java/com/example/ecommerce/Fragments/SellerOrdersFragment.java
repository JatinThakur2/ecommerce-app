package com.example.ecommerce.Fragments;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.ecommerce.Model.Order;
import com.example.ecommerce.Model.Product;
import com.example.ecommerce.R;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;

import static android.content.ContentValues.TAG;

public class SellerOrdersFragment extends Fragment {

    EditText searchbar;
    RecyclerView recyclerView;
    FirebaseRecyclerAdapter adapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_seller_orders, container, false);


        searchbar = view.findViewById(R.id.searchOrdersSeller);
        recyclerView = view.findViewById(R.id.orders_recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        Query query= FirebaseDatabase.getInstance().getReference().child("Orders")
                .orderByChild("sellerid")
                .equalTo(FirebaseAuth.getInstance().getUid());

        FirebaseRecyclerOptions<Order> options =
                new FirebaseRecyclerOptions.Builder<Order>()
                        .setQuery(query, Order.class)
                        .build();
         adapter = new FirebaseRecyclerAdapter<Order, OrderViewHolder>(options) {

            @Override
            public OrderViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
                View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.order_info_item, parent, false);

                return new OrderViewHolder(view);
            }

            @Override
            protected void onBindViewHolder(OrderViewHolder holder, int position, Order order) {
                // Bind the Chat object to the ChatHolder
                // ...
            //           Log.d("theartist", "onBindViewHolder: "+order.getPhone());
//               holder.orderid.setText(order.getOrderid().toString());
//               holder.paymentmode.setText(order.getPaymentmode());
//               holder.totalamount.setText(String.valueOf(order.getTotalamount()));
//               holder.productid.setText(order.getProductid());
        //         holder.productname.setText(order);
//               holder.productquantity.setText(String.valueOf(order.getQuantity()));
//               holder.userid.setText(order.getUserid());
//               holder.username.setText(order.getName());
//               holder.userphone.setText(order.getPhone());


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
class OrderViewHolder extends RecyclerView.ViewHolder{

    TextView orderid,paymentmode,totalamount,productid,productname,productquantity,userid,username,userphone;


    public OrderViewHolder(@NonNull View itemView) {
        super(itemView);
                 orderid = itemView.findViewById(R.id.orderid);
                paymentmode = itemView.findViewById(R.id.paymentmode);
                totalamount = itemView.findViewById(R.id.totalamount);
                productid = itemView.findViewById(R.id.productid);
                productname = itemView.findViewById(R.id.productname);
                productquantity = itemView.findViewById(R.id.productquantity);
                userid = itemView.findViewById(R.id.userid);
                username = itemView.findViewById(R.id.username);
                userphone = itemView.findViewById(R.id.userphone);


    }
}


//       Log.d("theartist", "onBindViewHolder: "+order.getPhone());
//               holder.orderid.setText(order.getOrderid());
//               holder.paymentmode.setText(order.getPaymentmode());
//               holder.totalamount.setText(order.getTotalamount());
//               holder.productid.setText(order.getProductid());
//               //  holder.productname.setText(order);
//               holder.productquantity.setText(order.getQuantity());
//               holder.userid.setText(order.getUserid());
//               holder.username.setText(order.getName());
//               holder.userphone.setText(order.getPhone());

