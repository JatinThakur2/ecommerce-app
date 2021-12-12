package com.example.ecommerce.Seller;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.webkit.MimeTypeMap;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.ecommerce.R;
import com.google.android.gms.tasks.Continuation;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.StorageTask;
import com.theartofdev.edmodo.cropper.CropImage;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;

public class SellerAddProductsActivity extends AppCompatActivity {


    private final String TAG = "theartist";

    private TextView mTitle;
    private  ImageView mProductImage;
    private  EditText mProductName, mProductPrice, mProductDesc;
    private  Button mAddProduct;
    private  String mCategory;

    private Uri imageUri;
    private String imageUrl ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_seller_add_products);

        mCategory = getIntent().getStringExtra("category");

        init();

        mTitle.setText("Category : "+mCategory);

        mAddProduct.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String pName =mProductName.getText().toString();
                String pPrice =mProductPrice.getText().toString();
                String pDesc =mProductDesc.getText().toString();
                if (TextUtils.isEmpty(pName) || TextUtils.isEmpty(pPrice)  || TextUtils.isEmpty(pDesc)){
                    Toast.makeText(SellerAddProductsActivity.this, "Fill all the information", Toast.LENGTH_SHORT).show();
                }else {
                    addProduct(pName,pPrice,pDesc);
                }

            }
        });

        mProductImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                CropImage.activity().start(SellerAddProductsActivity.this);

            }
        });

    }

    private void init() {
        mProductName = findViewById(R.id.productName);
        mProductPrice = findViewById(R.id.productPrice);
        mProductDesc = findViewById(R.id.productDesc);
        mTitle = findViewById(R.id.productTitle);
        mAddProduct = findViewById(R.id.addProduct);
        mProductImage = findViewById(R.id.productImage);
    }


    private void addProduct(final String name, final String price, final String desc) {

        final ProgressDialog pd = new ProgressDialog(this);
        pd.setMessage("Uploading");
        pd.show();


        if(imageUri!=null){
            final StorageReference filePath = FirebaseStorage.getInstance().getReference("Product Images")
                    .child(System.currentTimeMillis() + "." + myGetFileExtension(imageUri));

            StorageTask uploadtask = filePath.putFile(imageUri);
            uploadtask.continueWithTask(new Continuation() {
                @Override
                public Object then(@NonNull Task task) throws Exception {
                    if (!task.isSuccessful()){
                        throw task.getException();
                    }

                    return filePath.getDownloadUrl();
                }
            }).addOnCompleteListener(new OnCompleteListener<Uri>() {
                @Override
                public void onComplete(@NonNull Task<Uri> task) {
                    Uri downloadUri = task.getResult();
                    imageUrl = downloadUri.toString();


                    DatabaseReference productInfoRef = FirebaseDatabase.getInstance().getReference().child("Products");
                    String productId = productInfoRef.push().getKey();

                    HashMap<String ,Object> map = new HashMap<>();
                    map.put("state","pending");
                    map.put("sellerid", FirebaseAuth.getInstance().getUid());
                    map.put("productid",productId);
                    map.put("category",mCategory);
                    map.put("name",name);
                    map.put("price",price);
                    map.put("description",desc);
                    map.put("imageUrl",imageUrl);
                    map.put("date", new SimpleDateFormat("yyyy-MM-dd").format(Calendar.getInstance().getTime()));
                    map.put("time",new SimpleDateFormat("HH:mm:ss").format(Calendar.getInstance().getTime()));


                    productInfoRef.child(productId).setValue(map).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            Toast.makeText(SellerAddProductsActivity.this, "Product added", Toast.LENGTH_SHORT).show();
                            pd.dismiss();
                            startActivity(new Intent(SellerAddProductsActivity.this, SelectProductCategoryActivity.class)
                                       .addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK |Intent.FLAG_ACTIVITY_CLEAR_TOP));
                            finish();
                        }
                    });

                    pd.dismiss();
                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    Toast.makeText(SellerAddProductsActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                    pd.dismiss();

                }
            });

        }else {
            Toast.makeText(this, "No image was selected!", Toast.LENGTH_SHORT).show();
            pd.dismiss();
        }




    }

    private String getFileExtension(Uri uri) {
        return MimeTypeMap.getSingleton().getExtensionFromMimeType(this.getContentResolver().getType(uri));
    }

    private String myGetFileExtension(Uri uri) {
        String extension = uri.toString().substring(uri.toString().lastIndexOf("."));
        return extension;
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE && resultCode== RESULT_OK){
            CropImage.ActivityResult result = CropImage.getActivityResult(data);
            imageUri = result.getUri();
            Log.d(TAG, "onActivityResult: " + imageUri);
            Log.d(TAG, "onActivityResult: " + myGetFileExtension(imageUri));
//            Log.d(TAG, "onActivityResult: " + getFileExtension(data.getData()));

            mProductImage.setImageURI(imageUri);
        }else {
            Toast.makeText(this, "Try again!", Toast.LENGTH_SHORT).show();
            //startActivity(new Intent(AddProductsActivity.this , SelectProductCategoryActivity.class));
            finish();
        }
    }


    @Override
    protected void onStart() {
        super.onStart();
        status("Online :Adding Products");
    }

    private void status(String status) {

        HashMap<String,Object> map = new HashMap<>();
        map.put("status",status);
        FirebaseDatabase.getInstance().getReference("Sellers").child(FirebaseAuth.getInstance().getUid()).updateChildren(map);
    }

    @Override
    protected void onPause() {
        super.onPause();
        status("offline");
    }
}