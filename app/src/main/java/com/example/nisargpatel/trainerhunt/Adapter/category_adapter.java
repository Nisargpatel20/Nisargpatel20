package com.example.nisargpatel.trainerhunt.Adapter;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.nisargpatel.trainerhunt.Pojo.category_pojo;
import com.example.nisargpatel.trainerhunt.User_fragment.HomeFragment;
import com.example.nisargpatel.trainerhunt.activity.R;
import com.example.nisargpatel.trainerhunt.activity.Subcategory;
import com.example.nisargpatel.trainerhunt.constant;
import com.squareup.picasso.Picasso;

import java.util.List;

public class category_adapter extends RecyclerView.Adapter<category_adapter.ViewHolder>  {
     public static int s1;
    //this context we will use to inflate the layout
    private Context mCtx;

    //we are storing all the products in a list
    private List<category_pojo> productList;

    public category_adapter(Context mCtx, List<category_pojo> productList) {
            this.mCtx = mCtx;
        this.productList = productList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        LayoutInflater inflater = LayoutInflater.from(mCtx);
        View view = inflater.inflate(R.layout.recycle_products, null);
                return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder holder, final int position) {

        //getting the product of the specified position
        category_pojo product = productList.get(position);

        //binding the data with the viewholder views
        holder.textViewTitle.setText(product.getName());
        holder.textViewShortDesc.setText(product.getQuotes());
//        holder.textViewRating.setText(String.valueOf(product.getRating()));
//        holder.textViewPrice.setText(String.valueOf(product.getPrice()));
        Picasso.with(mCtx).load(constant.IMAGE+product.getImage()).into(holder.imageView);
        final int id = product.getId();
     // holder.imageView.setImageDrawable(mCtx.getResources().getDrawable(product.getImages()));


        holder.itemView.setOnClickListener(new View.OnClickListener() {


            @Override
            public void onClick(View v) {
                Intent intent;
                intent = new Intent(mCtx, Subcategory.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                intent.putExtra("id", id);
               // Toast.makeText(mCtx, "adapter"+id, Toast.LENGTH_SHORT).show();
                mCtx.startActivity(intent);

            }
        });


    }

    @Override
    public int getItemCount() {
        return productList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        TextView textViewTitle, textViewShortDesc, textViewRating, textViewPrice;
        ImageView imageView;

        public ViewHolder(View itemView) {
            super(itemView);

            textViewTitle = itemView.findViewById(R.id.textViewTitle);
            textViewShortDesc = itemView.findViewById(R.id.textViewShortDesc);
          //  textViewRating = itemView.findViewById(R.id.textViewRating);
            //textViewPrice = itemView.findViewById(R.id.textViewPrice);
            imageView = itemView.findViewById(R.id.imageView);


        }
    }

}


