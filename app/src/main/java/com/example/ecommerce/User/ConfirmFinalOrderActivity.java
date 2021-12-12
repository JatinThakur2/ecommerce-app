package com.example.ecommerce.User;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.example.ecommerce.Model.Cart;
import com.example.ecommerce.Model.User;
import com.example.ecommerce.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;

public class ConfirmFinalOrderActivity extends AppCompatActivity {

    public static final String TAG_ACTIVITY_FROM = "WhichActivity";


    TextView name, address, number, price, totalPrice, delivery, city, state;
    RadioGroup radioGroup;
    RadioButton cod, online;

    Button addAdress, confirmOder;

    String paymentMode, productpricewithdelivery;
    String productprice;

    FirebaseUser fUser;

    User user;
//    String userid, username, userphone, useraddress, sellerid, productid, productimageUrl, productprice, quantity, paymentmode, status, totalamount;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_confirm_final_order);


        fUser = FirebaseAuth.getInstance().getCurrentUser();


        if (getIntent().getExtras() != null) {
            String activityFrom = getIntent().getStringExtra(TAG_ACTIVITY_FROM);
            if (activityFrom.equals("From Cart")) {
                productprice = getIntent().getStringExtra("totalamount");

            } else if (activityFrom.equals("From Address")) {
                productprice = getIntent().getStringExtra("totalamount");
            }

        }

        init();
        setInfo();

        addAdress.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(ConfirmFinalOrderActivity.this, UpdateUserInfoActivity.class)
                        .putExtra(UpdateUserInfoActivity.TAG_ACTIVITY_FROM, "From Address")
                        .putExtra("name", name.getText())
                        .putExtra("number", number.getText())
                        .putExtra("address", address.getText())
                        .putExtra("city", city.getText())
                        .putExtra("state", state.getText()));
                //  .putExtra("totalamount", productprice)
            }
        });


        confirmOder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getCart();
            }
        });

    }

    private void getUserInfo() {

    }

    private void getCart() {
        FirebaseDatabase.getInstance().getReference("Cart").child(fUser.getUid())
                .addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                            final Cart cart = snapshot.getValue(Cart.class);
                            Log.d("theartist", "onDataChange: " + cart.getProductid());

                            DatabaseReference userOrderRef = FirebaseDatabase.getInstance().getReference("Orders");
                            String orderid = userOrderRef.push().getKey();
                            HashMap<String, Object> map = new HashMap<>();
                            map.put("orderid", orderid);

                            map.put("userid", user.getId());
                            map.put("username", user.getName());
                            map.put("userphone", user.getNumber());
                            map.put("useraddress", user.getAddress());

                            map.put("sellerid", cart.getSellerid());
                            map.put("productname",cart.getProductname());
                            map.put("productid", cart.getProductid());
                            map.put("productprice", cart.getProductprice());
                            map.put("productimageUrl", cart.getProductimageUrl());
                            map.put("quantity", cart.getQuantity());
                            map.put("totalamount", cart.getTotalprice());
                            map.put("date", new SimpleDateFormat("yyyy-MM-dd").format(Calendar.getInstance().getTime()));
                            map.put("time",new SimpleDateFormat("HH:mm:ss").format(Calendar.getInstance().getTime()));

                            if (radioGroup.getCheckedRadioButtonId() == R.id.checkBox_paymentCOD) {
                                map.put("paymentmode", "cod");
                            } else if (radioGroup.getCheckedRadioButtonId() == R.id.checkBox_paymentOnline) {

                                map.put("paymentmode", "online");
                            } else {

                                map.put("paymentmode", "default");
                            }

                            map.put("status", "pending");

                            final String pid = cart.getProductid();


                            userOrderRef.child(orderid).setValue(map)
                                    .addOnCompleteListener(new OnCompleteListener<Void>() {
                                        @Override
                                        public void onComplete(@NonNull Task<Void> task) {
                                            if (task.isSuccessful()) {


                                                FirebaseDatabase.getInstance().getReference("Cart").child(fUser.getUid())
                                                        .child(pid).removeValue();
                                                Intent intent = new Intent(ConfirmFinalOrderActivity.this, UserMainActivity.class);
                                                intent.putExtra(UserMainActivity.TAG_ACTIVITY_FROM, "fromCFOA");

                                                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                                                startActivity(intent);
                                                finish();

                                            }
                                        }
                                    });


                        }


                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });

    }


    private void setInfo() {

        FirebaseDatabase.getInstance().getReference("Users").child(fUser.getUid()).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                user = dataSnapshot.getValue(User.class);
                name.setText("Name " + user.getName());
                address.setText("Address " + user.getAddress());
                city.setText("City " + user.getCity());
                state.setText("State " + user.getState());
                number.setText("Number " + user.getNumber());
                price.setText(productprice);
                totalPrice.setText(productprice);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }


    private void init() {
        name = findViewById(R.id.textView_userName);
        address = findViewById(R.id.textView_address);
        number = findViewById(R.id.textView_userPhoneNumber);
        city = findViewById(R.id.textView_city);
        state = findViewById(R.id.textView_state);
        price = findViewById(R.id.textView_price);
        delivery = findViewById(R.id.textView_delivery);
        totalPrice = findViewById(R.id.textView_totalAmount);
        cod = findViewById(R.id.checkBox_paymentCOD);
        online = findViewById(R.id.checkBox_paymentOnline);
        radioGroup = findViewById(R.id.radiogroup);
        confirmOder = findViewById(R.id.button_confirmOrder);
        addAdress = findViewById(R.id.button_changeAdress);
    }


    @Override
    protected void onStart() {
        super.onStart();
        Log.d("theartist", "onStart: Umain ");
        status("Online : Confirming Order");
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