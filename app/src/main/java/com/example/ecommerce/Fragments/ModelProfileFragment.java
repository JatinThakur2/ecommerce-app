package com.example.ecommerce.Fragments;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.ecommerce.Model.User;
import com.example.ecommerce.R;
import com.example.ecommerce.StartActivity;
import com.example.ecommerce.User.UserMainActivity;
import com.google.android.gms.tasks.Continuation;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.StorageTask;
import com.rengwuxian.materialedittext.MaterialEditText;
import com.theartofdev.edmodo.cropper.CropImage;

import java.util.HashMap;

import de.hdodenhof.circleimageview.CircleImageView;

import static android.app.Activity.RESULT_OK;
import static android.content.Context.MODE_PRIVATE;


public class ModelProfileFragment extends Fragment {


    private CircleImageView imageProfile;
    private Button updateButton,logout;
    private TextView changePhoto;
    private ImageView changePhotoImage;
    private TextView email;
    private MaterialEditText fullname;
    private MaterialEditText phone;
    private MaterialEditText address;
    private MaterialEditText city;
    private  MaterialEditText state;


    private FirebaseUser fUser;
    private StorageReference storageReference;
    private StorageTask storageTask;
    private DatabaseReference sellerRef;
    private String accessType;


    private Uri mImageUri;
   private String mImageUrl;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view= inflater.inflate(R.layout.fragment_model_profile, container, false);
        init(view);

        SharedPreferences sharedPreferences = getActivity().getSharedPreferences("sharedPrefs", Context.MODE_PRIVATE);


        String type = sharedPreferences.getString("type","");
        if (type.equals("User")){
            accessType ="Users";
        }else {
            accessType ="Sellers";

        }


        fUser = FirebaseAuth.getInstance().getCurrentUser();
        storageReference = FirebaseStorage.getInstance().getReference().child("Profile Images");

        sellerRef = FirebaseDatabase.getInstance().getReference(accessType).child(fUser.getUid());

        sellerRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                User user = dataSnapshot.getValue(User.class);
                email.setText(user.getEmail());
                fullname.setText(user.getName());
               phone.setText(user.getNumber());
               address.setText(user.getAddress());
                if (getActivity() == null) {
                    return;
                }

                if (user.getCity().equals("default"))  { city.setText(" "); }
                else {city.setText(user.getCity());}

                if (user.getState().equals("default"))  { state.setText(" "); }
                else {state.setText(user.getState());}

                if (user.getImageUrl().equals("default")) {
                    Glide.with(getActivity()).load(R.mipmap.ic_launcher).into(imageProfile);
                 //   mImageUrl ="default";
                } else {
                    Glide.with(getActivity()).load(user.getImageUrl()).into(imageProfile);
                }


            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        changePhotoImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                CropImage.activity().setAspectRatio(1,1).start(getActivity(), ModelProfileFragment.this);
            }
        });
        changePhoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                CropImage.activity().setAspectRatio(1,1).start(getActivity(), ModelProfileFragment.this);
            }
        });


        updateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String sname = fullname.getText().toString();
                String sphone =  phone.getText().toString();
                String saddress = address.getText().toString();
                String scity = city.getText().toString();
                String sstate =   state.getText().toString();

                if (TextUtils.isEmpty(sname) || TextUtils.isEmpty(sphone)  || TextUtils.isEmpty(saddress)
                        || TextUtils.isEmpty(scity)  || TextUtils.isEmpty(sstate)){
                    Toast.makeText(getContext(), "Fill all the credentials", Toast.LENGTH_SHORT).show();
                }else {
                    updateInfo(sname,sphone,saddress,scity,sstate);
                }


            }
        });
        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                FirebaseAuth.getInstance().signOut();

                SharedPreferences sharedPreferences = getActivity().getSharedPreferences("sharedPrefs", MODE_PRIVATE);
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.putString("type", "none");
                editor.apply();

                startActivity(new Intent(getContext(), StartActivity.class)
                        .addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP));

                getActivity().finish();
            }
        });


        return view;
    }

    private void updateInfo(String sname, String sphone, String saddress, String scity, String sstate) {
        HashMap<String, Object> map = new HashMap<>();
        map.put("name", sname);
        map.put("number",sphone);
        map.put("address",saddress );
        map.put("city",scity);
        map.put("state",sstate);
//        if(!mImageUrl.equals("default")){
//            map.put("imageUrl", mImageUrl);
//        }
        FirebaseDatabase.getInstance().getReference().child(accessType).child(fUser.getUid()).updateChildren(map).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                Toast.makeText(getContext(), "Profile Updated", Toast.LENGTH_SHORT).show();
//                ViewPager viewPager = getView().findViewById(R.id.view_pager);
//               viewPager.setCurrentItem( 1 );
            }
        });
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE && resultCode == RESULT_OK) {
            CropImage.ActivityResult result = CropImage.getActivityResult(data);
            mImageUri = result.getUri();

            uploadImage();

        } else {
            Toast.makeText(getContext(), "Something went wrong!", Toast.LENGTH_SHORT).show();
        }
    }

    private void uploadImage() {
        final ProgressDialog progressDialog = new ProgressDialog(getContext());
        progressDialog.setTitle("Updating profile image");
        progressDialog.show();

        if(mImageUri!=null){
            final StorageReference mFileRef = FirebaseStorage.getInstance().getReference()
                    .child("Uploads").child(System.currentTimeMillis()+"jpeg");

            storageTask = mFileRef.putFile(mImageUri);

            storageTask.continueWithTask(new Continuation() {
                @Override
                public Object then(@NonNull Task task) throws Exception {
                    if(!task.isSuccessful()){
                        throw task.getException();
                    }
                    return mFileRef.getDownloadUrl();
                }
            }).addOnCompleteListener(new OnCompleteListener<Uri>() {
                @Override
                public void onComplete(@NonNull Task<Uri> task) {
                    if (task.isSuccessful()) {
                        Uri downloadUri = task.getResult();
                       String mImageUrl = downloadUri.toString();
                       FirebaseDatabase.getInstance().getReference().child(accessType).child(fUser.getUid()).child("imageUrl").setValue(mImageUrl);
                        Glide.with(getContext()).load(mImageUrl).into(imageProfile);
                        progressDialog.dismiss();

                    } else {
                        Toast.makeText(getContext(), "Upload failed!", Toast.LENGTH_SHORT).show();
                        progressDialog.dismiss();
                    }
                }
            });





        }else{
            Toast.makeText(getContext(), "No image was selected!", Toast.LENGTH_SHORT).show();
            progressDialog.dismiss();
        }
    }

    private void init(View view) {

        fullname = view.findViewById(R.id.sellerName);
        phone = view.findViewById(R.id.sellerPhone);
        address = view.findViewById(R.id.sellerAddress);
        city = view.findViewById(R.id.sellerCity);
        state = view.findViewById(R.id.sellerState);
        email = view.findViewById(R.id.sellerEmail);
        imageProfile = view.findViewById(R.id.image_profile);
        updateButton = view.findViewById(R.id.updateSellerProfile);
        logout = view.findViewById(R.id.logoutSeller);
        changePhoto = view.findViewById(R.id.change_photo);
        changePhotoImage = view.findViewById(R.id.image_profile);
    }



}