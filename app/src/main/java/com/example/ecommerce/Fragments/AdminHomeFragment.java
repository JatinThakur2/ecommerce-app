package com.example.ecommerce.Fragments;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.ecommerce.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.text.SimpleDateFormat;
import java.util.Calendar;


public class AdminHomeFragment extends Fragment {



    TextView onlineUsers,onlineSellers,ordersToday,salesToday;
    TextView totalUsers,totalSellers,totalProducts,totalOrders;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_admin_home, container, false);

        Toolbar toolbar = view.findViewById(R.id.toolbar);
        toolbar.setTitle("Home");
        toolbar.setTitleTextColor(getResources().getColor(R.color.colorWhite));

        onlineSellers = view.findViewById(R.id.onlineSellers);
        onlineUsers = view.findViewById(R.id.onlineUsers);
        ordersToday = view.findViewById(R.id.ordersToday);
        salesToday = view.findViewById(R.id.salesToday);
        totalUsers = view.findViewById(R.id.totalUsers);
        totalSellers = view.findViewById(R.id.totalSellers);
        totalProducts = view.findViewById(R.id.totalProducts);
        totalOrders = view.findViewById(R.id.totalOrders);


        final DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference();

        databaseReference.child("Users").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                long count= dataSnapshot.getChildrenCount();
                totalUsers.setText(count+"");


            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        databaseReference.child("Sellers").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                long count= dataSnapshot.getChildrenCount();
             totalSellers.setText(count+"");


            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
        databaseReference.child("Orders").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                long count= dataSnapshot.getChildrenCount();
                totalOrders.setText(count+"");

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
        databaseReference.child("Products").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                long count= dataSnapshot.getChildrenCount();
                totalProducts.setText(count+"");

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });


        Query onlineUserQuery =FirebaseDatabase.getInstance().getReference().child("Users")
                .orderByChild("status")
                .equalTo("online");

        onlineUserQuery.addValueEventListener(new ValueEventListener() {

            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                long numberOfUsers = dataSnapshot.getChildrenCount();
                onlineUsers.setText(numberOfUsers+"");

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });


        Query onlineSellerQuery =FirebaseDatabase.getInstance().getReference().child("Sellers")
                .orderByChild("status")
                .equalTo("online");

        onlineSellerQuery.addValueEventListener(new ValueEventListener() {

            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                onlineSellers.setText(dataSnapshot.getChildrenCount()+"");

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        Query ordersTodayQuery =FirebaseDatabase.getInstance().getReference().child("Orders")
                .orderByChild("date")
                .equalTo(new SimpleDateFormat("yyyy-MM-dd").format(Calendar.getInstance().getTime()));



        ordersTodayQuery.addValueEventListener(new ValueEventListener() {

            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                ordersToday.setText(dataSnapshot.getChildrenCount()+"");


            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });



        return  view;
    }
}