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

import com.example.nisargpatel.trainerhunt.Pojo.trainerlist_pojo;
import com.example.nisargpatel.trainerhunt.activity.R;
import com.example.nisargpatel.trainerhunt.activity.Trainer_profile;
import com.example.nisargpatel.trainerhunt.constant;
import com.squareup.picasso.Picasso;

import java.util.List;

public class wishlist_adapter extends RecyclerView.Adapter<wishlist_adapter.ViewHolder> {

    private Context mCtx;

    //we are storing all the products in a list
    private List<trainerlist_pojo> productList;
    public wishlist_adapter(Context mCtx, List<trainerlist_pojo> productList) {
        this.mCtx = mCtx;
        this.productList = productList;
    }
    @NonNull
    @Override
    public wishlist_adapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        LayoutInflater inflater = LayoutInflater.from(mCtx);
        View view = inflater.inflate(R.layout.wishlist_recycle_products, null);
        return new wishlist_adapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull wishlist_adapter.ViewHolder holder, int position) {
        final trainerlist_pojo product = productList.get(position);

        //binding the data with the viewholder views
        holder.textViewTitle.setText(product.getName());
        holder.textViewShortDesc.setText(product.getAddress());
       // holder.textViewRating.setText(""+product.getLikeCount());
        Picasso.with(mCtx).load(constant.IMAGE + product.getImage()).into(holder.imageView);
        // holder.textViewRating.setText(String.valueOf(product.getRati()));
        holder.textViewPrice.setText(String.valueOf(product.getPrice()));
        final int tid = product.getId();


        holder.itemView.setOnClickListener(new View.OnClickListener() {


            @Override
            public void onClick(View v) {
                Intent intent;
                intent = new Intent(mCtx, Trainer_profile.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                intent.putExtra("tid", tid);
                mCtx.startActivity(intent);
            }
        });

    }

    @Override
    public int getItemCount() {
        return productList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView textViewTitle, textViewShortDesc, textViewRating, textViewPrice,getting_id;
        ImageView imageView;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            textViewTitle = itemView.findViewById(R.id.textViewTitle_trainer);
            textViewShortDesc = itemView.findViewById(R.id.textViewShortDesc_trainer);
            textViewRating = itemView.findViewById(R.id.textViewRating_trainer);
            textViewPrice = itemView.findViewById(R.id.textViewPrice_trainer);
            imageView = itemView.findViewById(R.id.imageView_trainer);
        }
    }
}
