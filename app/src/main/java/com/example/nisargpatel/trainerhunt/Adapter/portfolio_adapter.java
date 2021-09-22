package com.example.nisargpatel.trainerhunt.Adapter;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.example.nisargpatel.trainerhunt.portfolio;
import com.example.nisargpatel.trainerhunt.Pojo.portfolio_pojo;
import com.example.nisargpatel.trainerhunt.constant;
import com.example.nisargpatel.trainerhunt.activity.R;
import com.squareup.picasso.Picasso;

import java.util.List;

public class portfolio_adapter extends RecyclerView.Adapter<portfolio_adapter.ViewHolder>{
    private Context aCtx;

    private List<portfolio_pojo> photoList;
    public portfolio_adapter(Context aCtx, List<portfolio_pojo> photoList) {
        this.aCtx = aCtx;
        this.photoList = photoList;
    }
    @NonNull
    @Override

    public portfolio_adapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        LayoutInflater inflater = LayoutInflater.from(aCtx);
        View view = inflater.inflate(R.layout.design_portfolio, null);
        return new portfolio_adapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull portfolio_adapter.ViewHolder holder, int position) {
        portfolio_pojo product = photoList.get(position);


        //binding the data with the viewholder views




        // holder.imageView.setImageDrawable(mCtx.getResources().getDrawable(product.getImage()));
        Picasso.with(aCtx).load(constant.IMAGE +product.getImage()).into(holder.imageView);
        Log.d("dh",constant.IMAGE+product.getImage());
        holder.itemView.setOnClickListener(new View.OnClickListener() {


            @Override
            public void onClick(View v) {

                Intent intent;
                intent=new Intent(aCtx, portfolio.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                aCtx.startActivity(intent);


            }
        });

    }

    @Override
    public int getItemCount() {
        return photoList.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        ImageView imageView;


        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            imageView=itemView.findViewById(R.id.album_id);
        }
    }
}
