package com.example.ecommerce.User;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.ecommerce.Model.Cart;
import com.example.ecommerce.R;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;

public class CartActivity extends AppCompatActivity {

    private Button placeOrder;
    private TextView totalAmount;
    private ImageView backButton;

    private RecyclerView recyclerView;

    private FirebaseUser fUser;
    private DatabaseReference userCartRef;
    private int finalAmount;

    private FirebaseRecyclerAdapter adapter;

    int stotalAmount;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cart);

        Toolbar toolbar = findViewById(R.id.toolbar);
        toolbar.setTitle("Your Cart");
        toolbar.setTitleTextColor(getResources().getColor(R.color.colorWhite));

        setSupportActionBar(toolbar);

        init();

        placeOrder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(CartActivity.this, ConfirmFinalOrderActivity.class)
                        .putExtra(ConfirmFinalOrderActivity.TAG_ACTIVITY_FROM,"From Cart")
                        .putExtra("totalamount",totalAmount.getText()));
            }
        });


        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        fUser = FirebaseAuth.getInstance().getCurrentUser();
        userCartRef = FirebaseDatabase.getInstance().getReference("Cart").child(fUser.getUid());
        final Query query = FirebaseDatabase.getInstance().getReference().child("Cart").child(fUser.getUid());


        FirebaseRecyclerOptions<Cart> options =
                new FirebaseRecyclerOptions.Builder<Cart>()
                        .setQuery(query, Cart.class)
                        .build();


        adapter = new FirebaseRecyclerAdapter<Cart, MyCartViewHolder>(options) {
            @Override
            protected void onBindViewHolder(@NonNull final MyCartViewHolder holder, int position, @NonNull final Cart cart) {

                Glide.with(CartActivity.this).load(cart.getProductimageUrl()).into(holder.productImage);


                holder.productName.setText(cart.getProductname());
                holder.productQuantity.setText(cart.getQuantity());
                holder.productPrice.setText("INR" + cart.getProductprice() + "per piece");

                final String productid = cart.getProductid();


                if (cart.getQuantity().equals("0")) {
                    removeProduct(productid);
                }else {


                    int tp = Integer.parseInt(cart.getProductprice()) * Integer.parseInt(cart.getQuantity());
                    FirebaseDatabase.getInstance().getReference().child("Cart")
                            .child(fUser.getUid()).child(productid).child("totalprice").setValue(String.valueOf(tp));

                    calculateTotalAmount();
                }





                holder.removeProduct.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        removeProduct(productid);
                    }
                });

                holder.incQuantity.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {

                        FirebaseDatabase.getInstance().getReference().child("Cart")
                                .child(fUser.getUid()).child(productid).child("quantity")
                                .setValue(String.valueOf(Integer.parseInt(holder.productQuantity.getText().toString()) + 1));

                    }
                });
                holder.decQuantity.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        //   if ((Hol))




                        FirebaseDatabase.getInstance().getReference().child("Cart")
                                .child(fUser.getUid()).child(productid).child("quantity")
                                .setValue(String.valueOf(Integer.parseInt(holder.productQuantity.getText().toString()) - 1));



                    }
                });


            }

            @Override
            public MyCartViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
                View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.cart_item, parent, false);
                return new MyCartViewHolder(view);
            }


        };

        recyclerView.setAdapter(adapter);


    }

    private void calculateTotalAmount() {
        FirebaseDatabase.getInstance().getReference("Cart").child(fUser.getUid())
                .addValueEventListener(new ValueEventListener() {

                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        finalAmount=0;
                        for (DataSnapshot snapshot : dataSnapshot.getChildren()) {

                            int productPrice =  Integer.parseInt(snapshot.child("productprice").getValue(String.class))*Integer.parseInt(snapshot.child("quantity").getValue(String.class));
                            finalAmount = finalAmount +productPrice;
                            totalAmount.setText("   INR "+String.valueOf(finalAmount));


                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });
    }

    private void removeProduct(String pid) {
        FirebaseDatabase.getInstance().getReference().child("Cart")
                .child(fUser.getUid()).child(pid).removeValue();
    }


    private void init() {
        placeOrder = findViewById(R.id.button_placeOrder);
        totalAmount = findViewById(R.id.textView_totalAmount);
        backButton = findViewById(R.id.image_back);
        recyclerView = findViewById(R.id.recyclerView);
    }

    @Override
    public void onStart() {
        super.onStart();
        adapter.startListening();
        Log.d("theartist", "onStart: Ucart ");
        status("Online : Viewing Cart");
    }

    @Override
    public void onPause() {
        super.onPause();
        adapter.stopListening();

        Log.d("theartist", "onPause: Ucart ");
        status("offilne");
    }


    private void status(String status) {

        HashMap<String,Object> map = new HashMap<>();
        map.put("status",status);
        FirebaseDatabase.getInstance().getReference("Users").child(FirebaseAuth.getInstance().getUid()).updateChildren(map);
    }



}

class MyCartViewHolder extends RecyclerView.ViewHolder {
    ImageView productImage;
    TextView productName, productPrice, productQuantity;
    Button removeProduct, incQuantity, decQuantity;

    public MyCartViewHolder(@NonNull View itemView) {
        super(itemView);
        productImage = itemView.findViewById(R.id.image_productImage);
        productName = itemView.findViewById(R.id.textView_productName);
        productPrice = itemView.findViewById(R.id.textView_productSeller);
        productQuantity = itemView.findViewById(R.id.textView_productQuantity);
        removeProduct = itemView.findViewById(R.id.button_remove);
        incQuantity = itemView.findViewById(R.id.button_inc);
        decQuantity = itemView.findViewById(R.id.button_dec);
    }



}