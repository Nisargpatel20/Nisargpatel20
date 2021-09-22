package com.example.nisargpatel.trainerhunt.Adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.nisargpatel.trainerhunt.Pojo.trainer_home_pojo;
import com.example.nisargpatel.trainerhunt.Pojo.trainerlist_pojo;
import com.example.nisargpatel.trainerhunt.activity.R;
import com.example.nisargpatel.trainerhunt.constant;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class trainer_home_adapter extends RecyclerView.Adapter<trainer_home_adapter.ViewHolder> {
    private Context mCtx;

    //we are storing all the products in a list
    private List<trainer_home_pojo> productList;

    public trainer_home_adapter(Context mCtx, ArrayList<trainer_home_pojo> productList) {
        this.mCtx = mCtx;
        this.productList = productList;
    }
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        LayoutInflater inflater = LayoutInflater.from(mCtx);
        View view = inflater.inflate(R.layout.recycle_traiener_home, null);
        return new trainer_home_adapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull trainer_home_adapter.ViewHolder holder, int position) {
        trainer_home_pojo product = productList.get(position);

        //binding the data with the viewholder views
        holder.textViewTitle.setText(product.getName());
        holder.textViewShortDesc.setText(product.getEmail());
     //   Picasso.with(mCtx).load(constant.IMAGE+product.getImage()).into(holder.imageView);
        // holder.textViewRating.setText(String.valueOf(product.getRati()));
        holder.textViewPrice.setText(String.valueOf(product.getNumber()));
       // final int tid=product.getId();

    }

    @Override
    public int getItemCount() {
        return productList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView textViewTitle, textViewShortDesc, textViewRating, textViewPrice;
        ImageView imageView,imagefav;
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
