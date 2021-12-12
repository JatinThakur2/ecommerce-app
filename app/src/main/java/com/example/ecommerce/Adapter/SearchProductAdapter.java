package com.example.ecommerce.Adapter;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.ecommerce.Model.Product;
import com.example.ecommerce.User.ProductDetailsActivity;
import com.example.ecommerce.R;


import java.io.Serializable;
import java.util.List;

public class SearchProductAdapter extends RecyclerView.Adapter<SearchProductAdapter.ViewHolder> {

    public Context mContext;

    public List<Product> productList;
    public List<String> list;
    public List<String> listType;
    public boolean isProduct;

    public SearchProductAdapter(Context mContext, List<String> list, List<String> listType) { //product and category

        this.mContext = mContext;
        this.list = list;
        this.listType = listType;
        this.isProduct = true;
        Log.d("theartist", "SearchProductAdapter: with both");
    }

    public SearchProductAdapter(Context mContext, List<Product> productList,boolean isProduct) { //product and category

        this.mContext = mContext;
        this.productList = productList;
        this.isProduct = isProduct;
        Log.d("theartist", "SearchProductAdapter: with both");
    }

    public SearchProductAdapter(Context mContext, List<String> list) {      //only category
        this.mContext = mContext;
        this.list = list;
        this.isProduct = false;

        Log.d("theartist", "SearchProductAdapter: only category");
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.search_item, parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {




        if (isProduct){
//            holder.searchTitle.setText(list.get(position));
//            holder.searchType.setText(listType.get(position));
            final Product product = productList.get(position);

            holder.searchTitle.setText(product.getName());
            holder.searchType.setText(product.getCategory());
            Glide.with(mContext).load(product.getImageUrl()).into(holder.productImage);

            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    mContext.startActivity(new Intent(mContext, ProductDetailsActivity.class)
                            .putExtra("Productt", (Serializable) product));

                }
            });
        }else {
            holder.searchTitle.setText(list.get(position));
            holder.searchTitle.setTextColor(ContextCompat.getColor(mContext,R.color.colorAccent));
            holder.searchType.setText("");

            //TODO make category page
        }

    }

    @Override
    public int getItemCount() {
        if (isProduct){
            return productList.size()   ;
        }else {
            return list.size();
        }
    }

    public class  ViewHolder extends RecyclerView.ViewHolder{

        TextView searchTitle, searchType;
        ImageView productImage;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            searchTitle = itemView.findViewById(R.id.productTitle);
            searchType = itemView.findViewById(R.id.type);
            productImage = itemView.findViewById(R.id.productImage);
        }
    }
}
