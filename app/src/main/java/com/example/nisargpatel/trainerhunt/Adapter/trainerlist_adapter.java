package com.example.nisargpatel.trainerhunt.Adapter;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.StrictMode;
import android.provider.SyncStateContract;
import android.support.annotation.NonNull;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.ImageSwitcher;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ToggleButton;

import com.example.nisargpatel.trainerhunt.Pojo.trainerlist_pojo;
import com.example.nisargpatel.trainerhunt.activity.R;
import com.example.nisargpatel.trainerhunt.activity.Trainer_profile;
import com.example.nisargpatel.trainerhunt.constant;
import com.like.LikeButton;
import com.like.OnLikeListener;
import com.squareup.picasso.Picasso;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import static com.example.nisargpatel.trainerhunt.activity.login.PREFS_NAME;

public class trainerlist_adapter extends RecyclerView.Adapter<trainerlist_adapter.ViewHolder> {
    private Context mCtx;

    //we are storing all the products in a list
    private List<trainerlist_pojo> productList;

    public trainerlist_adapter(Context mCtx, List<trainerlist_pojo> productList) {
        this.mCtx = mCtx;
        this.productList = productList;
    }

    @NonNull
    @Override
    public trainerlist_adapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        LayoutInflater inflater = LayoutInflater.from(mCtx);
        View view = inflater.inflate(R.layout.trainer_list_recycle, null);
        return new trainerlist_adapter.ViewHolder(view);
    }

    public void onBindViewHolder(@NonNull final trainerlist_adapter.ViewHolder holder, int position) {

        //getting the product of the specified position
        final trainerlist_pojo product = productList.get(position);

        //binding the data with the viewholder views
        holder.textViewTitle.setText(product.getName());
        holder.textViewShortDesc.setText(product.getAddress());
        holder.textViewRating.setText(""+product.getLikeCount());
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
        holder.tgButton.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {


                if (isChecked) {
                    Integer likee = Integer.valueOf(product.getLikeCount());
                    int lik1 = likee + 1;
                    holder.textViewRating.setText(Integer.toString(lik1) + " Likes");

                    String s = "1";


                    if (android.os.Build.VERSION.SDK_INT > 9) {
                        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
                        StrictMode.setThreadPolicy(policy);
                    }
                    InputStream is = null;
                    String result = null;
                    String line = null;
                    int code;

                    // String pidd=(pro.get(position).getPro_id());


                    SharedPreferences sharedPreferences = mCtx.getSharedPreferences(PREFS_NAME, 0);

                    int uidd = sharedPreferences.getInt("userId",0);
                    ArrayList<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
                    nameValuePairs.add(new BasicNameValuePair("like_unlike", s));
                    nameValuePairs.add(new BasicNameValuePair("trainer_id", Integer.toString(tid)));
                    nameValuePairs.add(new BasicNameValuePair("user_id", Integer.toString(uidd)));

                    Log.e("userid",""+uidd);

                    try {
                        HttpClient httpclient = new DefaultHttpClient();
                        HttpPost httppost = new HttpPost(constant.PATH + "like_unlike.php");
                        httppost.setEntity(new UrlEncodedFormEntity(nameValuePairs));
                        HttpResponse response = httpclient.execute(httppost);
                        HttpEntity entity = response.getEntity();
                        is = entity.getContent();
                        Log.e("pass 1", "connection success ");
                    } catch (Exception e) {
                        Log.e("Fail 1", e.toString());
                       /* Toast.makeText(context, "Invalid IP Address",
                                Toast.LENGTH_LONG).show();*/
                    }

                    try {
                        BufferedReader reader = new BufferedReader
                                (new InputStreamReader(is, "iso-8859-1"), 8);
                        StringBuilder sb = new StringBuilder();
                        while ((line = reader.readLine()) != null) {
                            sb.append(line + "\n");
                        }
                        is.close();
                        result = sb.toString();
                        Log.e("pass 2", "connection success ");
                    } catch (Exception e) {
                        Log.e("Fail 2", e.toString());
                    }

                    try {
                        JSONObject json_data = new JSONObject(result);
                        code = (json_data.getInt("status"));

                        if (code == 1) {

                            Integer like = Integer.valueOf(product.getLikeCount());
                            int lik = like + 1;
                            holder.textViewRating.setText(Integer.toString(lik) + " Likes");


                        } else {
                            Toast.makeText(mCtx, "Sorry, Try Again",
                                    Toast.LENGTH_LONG).show();
                        }
                    } catch (Exception e) {
                        Log.e("Fail 3", e.toString());
                    }

                    holder.tgButton.setBackgroundDrawable(ContextCompat.getDrawable(mCtx.getApplicationContext(), R.drawable.ic_favorite_black_24dp));

                } else if (!isChecked) {


                    if (android.os.Build.VERSION.SDK_INT > 9) {
                        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
                        StrictMode.setThreadPolicy(policy);
                    }
                    InputStream is = null;
                    String result = null;
                    String line = null;
                    int code;

                    // String pidd=(pro.get(position).getPro_id());
                    SharedPreferences sharedPreferences = mCtx.getSharedPreferences(PREFS_NAME, 0);

                    Integer uidd = sharedPreferences.getInt("userId", 0);
                    ArrayList<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();

                    nameValuePairs.add(new BasicNameValuePair("like_unlike", "0 "));
                    nameValuePairs.add(new BasicNameValuePair("trainer_id", Integer.toString(tid)));
                    nameValuePairs.add(new BasicNameValuePair("user_id", Integer.toString(uidd)));

                    Log.e("userid",""+uidd);

                    try {
                        HttpClient httpclient = new DefaultHttpClient();
                        HttpPost httppost = new HttpPost(constant.PATH + "unlike.php");
                        httppost.setEntity(new UrlEncodedFormEntity(nameValuePairs));
                        org.apache.http.HttpResponse response = httpclient.execute(httppost);
                        HttpEntity entity = response.getEntity();
                        is = entity.getContent();
                        Log.e("pass 1", "connection success ");
                    } catch (Exception e) {
                        Log.e("Fail 1", e.toString());
                       /* Toast.makeText(context, "Invalid IP Address",
                                Toast.LENGTH_LONG).show();*/
                    }

                    try {
                        BufferedReader reader = new BufferedReader
                                (new InputStreamReader(is, "iso-8859-1"), 8);
                        StringBuilder sb = new StringBuilder();
                        while ((line = reader.readLine()) != null) {
                            sb.append(line + "\n");
                        }
                        is.close();
                        result = sb.toString();
                        Log.e("pass 2", "connection success ");
                    } catch (Exception e) {
                        Log.e("Fail 2", e.toString());
                    }

                    try {
                        JSONObject json_data = new JSONObject(result);
                        code = (json_data.getInt("status"));

                        if (code == 1) {


                        } else {
                            Toast.makeText(mCtx, "Sorry, Try Again",
                                    Toast.LENGTH_LONG).show();
                        }
                    } catch (Exception e) {
                        Log.e("Fail 3", e.toString());
                    }
                    holder.tgButton.setBackgroundDrawable(ContextCompat.getDrawable(mCtx.getApplicationContext(), R.drawable.ic_favorite_border_black_24dp));
                } else
                    holder.tgButton.setBackgroundDrawable(ContextCompat.getDrawable(mCtx.getApplicationContext(), R.drawable.ic_favorite_black_24dp));
            }
        });
    }

    @Override
    public int getItemCount() {
        return productList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        public ImageSwitcher like;
        TextView textViewTitle, textViewShortDesc, textViewRating, textViewPrice,getting_id;
        ImageView imageView;
        ToggleButton tgButton;

        public ViewHolder(View itemView) {
            super(itemView);

            textViewTitle = itemView.findViewById(R.id.textViewTitle_trainer);
            textViewShortDesc = itemView.findViewById(R.id.textViewShortDesc_trainer);
            textViewRating = itemView.findViewById(R.id.textViewRating_trainer);
            textViewPrice = itemView.findViewById(R.id.textViewPrice_trainer);
            imageView = itemView.findViewById(R.id.imageView_trainer);
            tgButton = itemView.findViewById(R.id.img_like);
            getting_id=itemView.findViewById(R.id.getting_id);

        }
    }
}
