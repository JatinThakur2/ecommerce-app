package com.example.ecommerce.Fragments;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.support.v4.media.session.IMediaControllerCallback;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.ecommerce.Model.User;
import com.example.ecommerce.Model.User;
import com.example.ecommerce.R;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;


public class AdminViewUsersFragment extends Fragment {


    RecyclerView recyclerView;

    FirebaseRecyclerAdapter adapter;



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view =inflater.inflate(R.layout.fragment_admin_view_users, container, false);

        Toolbar toolbar = view.findViewById(R.id.toolbar);
        toolbar.setTitle("Users");
        toolbar.setTitleTextColor(getResources().getColor(R.color.colorWhite));

        recyclerView = view.findViewById(R.id.user_recycler_view);


        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        Query query= FirebaseDatabase.getInstance().getReference().child("Users");


        FirebaseRecyclerOptions<User> options =
                new FirebaseRecyclerOptions.Builder<User>()
                        .setQuery(query, User.class)
                        .build();

        adapter = new FirebaseRecyclerAdapter<User, UserViewHolder>(options) {

            @Override
            public UserViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
                View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.user_item, parent, false);

                return new UserViewHolder(view);
            }

            @Override
            protected void onBindViewHolder(UserViewHolder holder, int position, User user) {

                holder.userid.setText(user.getId());

                holder.username.setText("Userid : "+user.getName());
                holder.usernumber.setText(String.valueOf(user.getNumber()));
                holder.useremail.setText(user.getEmail());
                holder.useraddress.setText(user.getAddress());
                holder.usercity.setText(user.getCity());
                holder.userstate.setText(user.getState());
                holder.userstatus.setText(user.getStatus());


                Glide.with(getContext()).load(user.getImageUrl()).into(holder.userimage);




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

class UserViewHolder extends RecyclerView.ViewHolder{

    TextView userid,username,usernumber,useremail,useraddress,usercity,userstate,userstatus;
    ImageView userimage;


    public UserViewHolder(@NonNull View itemView) {
        super(itemView);
        userid = itemView.findViewById(R.id.userid);
        userimage = itemView.findViewById(R.id.userimage);
        username = itemView.findViewById(R.id.username);
        usernumber = itemView.findViewById(R.id.usernumber);
        useremail = itemView.findViewById(R.id.useremail);
        useraddress = itemView.findViewById(R.id.useraddress);
        usercity = itemView.findViewById(R.id.usercity);
        userstate = itemView.findViewById(R.id.userstate);
        userstatus = itemView.findViewById(R.id.userstatus);

    }
}
