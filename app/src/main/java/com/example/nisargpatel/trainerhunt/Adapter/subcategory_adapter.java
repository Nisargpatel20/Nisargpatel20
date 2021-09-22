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

import com.example.nisargpatel.trainerhunt.activity.R;
import com.example.nisargpatel.trainerhunt.Pojo.subcategory_pojo;
import com.example.nisargpatel.trainerhunt.activity.Subcategory;
import com.example.nisargpatel.trainerhunt.activity.Trainers_list;
import com.example.nisargpatel.trainerhunt.constant;
import com.squareup.picasso.Picasso;

import java.util.List;

public class subcategory_adapter extends RecyclerView.Adapter<subcategory_adapter.ViewHolder> {


    //this context we will use to inflate the layout
    private Context mCtx;

    //we are storing all the products in a list
    private List<subcategory_pojo> productList;

    public subcategory_adapter(Context mCtx, List<subcategory_pojo> productList) {
        this.mCtx = mCtx;
        this.productList = productList;
    }

    @NonNull
    @Override
    public subcategory_adapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        LayoutInflater inflater = LayoutInflater.from(mCtx);
        View view = inflater.inflate(R.layout.design_subcatogry, null);
        return new ViewHolder(view);
    }


    @Override
    public void onBindViewHolder(@NonNull final subcategory_adapter.ViewHolder holder, int position) {

        //getting the product of the specified position
        subcategory_pojo product = productList.get(position);


        //binding the data with the viewholder views
        holder.textViewTitle.setText(product.getName());
        Picasso.with(mCtx).load(constant.IMAGE+product.getImage()).into(holder.imageView);
        final int sid = product.getId();
        holder.itemView.setOnClickListener(new View.OnClickListener() {


            @Override
            public void onClick(View v) {
                Intent intent;
                intent = new Intent(mCtx, Trainers_list.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                intent.putExtra("sid", sid);
                mCtx.startActivity(intent);
                //Toast.makeText(mCtx,"adapter"+sid, Toast.LENGTH_SHORT).show();
            }
        });


    }

    @Override
    public int getItemCount() {
        return productList.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        TextView textViewTitle;
        ImageView imageView;

        public ViewHolder(View itemView) {
            super(itemView);

            textViewTitle = itemView.findViewById(R.id.textView_sub);
            imageView = itemView.findViewById(R.id.imageView_sub);
        }
    }
}
