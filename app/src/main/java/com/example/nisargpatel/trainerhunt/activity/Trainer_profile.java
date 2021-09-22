package com.example.nisargpatel.trainerhunt.activity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.support.v7.widget.LinearLayoutManager;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.nisargpatel.trainerhunt.Adapter.category_adapter;
import com.example.nisargpatel.trainerhunt.Pojo.category_pojo;
import com.example.nisargpatel.trainerhunt.constant;
import com.example.nisargpatel.trainerhunt.portfolio;
import com.like.LikeButton;
import com.like.OnAnimationEndListener;
import com.like.OnLikeListener;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import static com.example.nisargpatel.trainerhunt.activity.login.PREFS_NAME;

public class Trainer_profile extends AppCompatActivity {
TextView textMaininfo,textDegree,textExp,textMobileno,textEmailid,textAddress1,textPrice,textName,textaddress2;
ImageView imgProfile,imgPortfolio;
ProgressDialog progressDialog;
CardView card1,addresscard,card2,contact_card;
SharedPreferences sharedPreferences;
ImageView imagefav;
int tid;
int id1;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_trainer_profile);
       // textShortinfo=findViewById(R.id.trainer_shortinfo);
        textMaininfo=findViewById(R.id.trainer_maininfo);
        textDegree=findViewById(R.id.trainer_degree);
        textExp=findViewById(R.id.trainer_experience);
        textMobileno=findViewById(R.id.trainer_mobileno);
        textEmailid=findViewById(R.id.trainer_emaild);
       textAddress1=findViewById(R.id.trainer_address);
       textPrice=findViewById(R.id.trainer_price);
       imgProfile=findViewById(R.id.trainer_profilepic);
       textName=findViewById(R.id.trainer_name);
       textaddress2=findViewById(R.id.trainer_address2);

       card1=(CardView)findViewById(R.id.profile_emailcard);
       addresscard=(CardView)findViewById(R.id.profie_addresscard);
        addresscard.setCardBackgroundColor(Color.TRANSPARENT);
        card1.setBackgroundResource(R.drawable.cardview_border);
        imgPortfolio=(ImageView) findViewById(R.id.card_photo);
        sharedPreferences=this.getSharedPreferences(PREFS_NAME,0);
        id1 =sharedPreferences.getInt("VDID", 0);
        contact_card=(CardView) findViewById(R.id.contact_card);
       // imagefav =(ImageView)findViewById(R.id.fav_image);
      /*  imgPortfolio.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(Trainer_profile.this, portfolio.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                Toast.makeText(Trainer_profile.this, "ID"+T_id, Toast.LENGTH_SHORT).show();
                intent.putExtra("vdid",T_id);
                startActivity(intent);
            }
        });*/

        addresscard.setCardElevation(0);
        card2=(CardView)findViewById(R.id.cardview1) ;
        card1.setCardBackgroundColor(Color.TRANSPARENT);
        card2.setCardBackgroundColor(Color.TRANSPARENT);
        card2.setCardElevation(0);


        Intent intent = getIntent();

        tid = intent.getIntExtra("tid", 0);
        Trainer_profile();

    }




    public void Trainer_profile()
    {
        progressDialog = new ProgressDialog(Trainer_profile.this);
        progressDialog.setCancelable(false);
        progressDialog.setMessage("Wait");
        progressDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        progressDialog.show();
        progressDialog.setContentView(R.layout.ex_loader);
        JsonObjectRequest objectRequest = new JsonObjectRequest(Request.Method.GET, constant.PATH+"trainer_details_api.php?tid="+tid,
                null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                Log.d("event", response.toString());
                try {
                    int code = response.getInt("status");
                    if (code == 200) {
                        progressDialog.cancel();
                        JSONArray array = response.getJSONArray("result");

                        for (int n = 0; n < array.length(); n++) {
                            JSONObject obj = array.getJSONObject(n);
                            Log.d("kush", "" +obj.toString());
                            final int T_id=obj.getInt("id");
                          String T_profile=obj.getString("profileimage");
                            String T_Name=obj.getString("name");
                           // String T_shortinfo=obj.getString("shortinfo");
                            String T_maininfo=obj.getString("maininfo");
                            String T_Degree=obj.getString("degree");
                            final String T_mobileno=obj.getString("mobileno");
                            String T_exp=obj.getString("experience");
                            final String T_emailid=obj.getString("email");
                            String T_address=obj.getString("address1");
                            String T_address2=obj.getString("address2");
                            String T_price=obj.getString("price");
                           // Toast.makeText(Trainer_profile.this, "ID IS"+T_id, Toast.LENGTH_SHORT).show();
                            //sharedPreferences =getApplicationContext().getSharedPreferences(PREFS_NAME,0);
                           // SharedPreferences.Editor editor =sharedPreferences.edit();
                          //  editor.putInt("VDID",T_id);
                           // editor.commit();
                            textName.setText(T_Name);
                           // textShortinfo.setText(T_shortinfo);
                            textMaininfo.setText(T_maininfo);
                            textDegree.setText(T_Degree);
                            textExp.setText(T_exp);
                            textEmailid.setText(T_emailid);
                            textAddress1.setText(T_address);
                            textaddress2.setText(T_address2);
                            textMobileno.setText(T_mobileno);
                            textPrice.setText(T_price);
                            imgPortfolio.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    Intent intent=new Intent(Trainer_profile.this, portfolio.class);
                                    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                                   // Toast.makeText(Trainer_profile.this, "ID"+T_id, Toast.LENGTH_SHORT).show();
                                    intent.putExtra("vdid",T_id);
                                    startActivity(intent);

                                }
                            });
                            contact_card.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {

                                    Intent callIntent = new Intent(Intent.ACTION_DIAL);
                                    callIntent.setData(Uri.parse("tel:"+T_mobileno));
                                    startActivity(callIntent);

                                }
                            });
                            card1.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    Intent email=new Intent(Intent.ACTION_SEND);
                                    email.putExtra(Intent.EXTRA_EMAIL, new String[]{T_emailid});
                                    email.putExtra(Intent.EXTRA_SUBJECT,"THE EVENTALIST");
                                    // email.putExtra(Intent.EXTRA_TEXT, "");
                                    email.setType("message/rfc822");
                                    startActivity(Intent.createChooser(email, "Choose an Email client :"));
                                }
                            });


                            Picasso.with(Trainer_profile.this).load(constant.IMAGE + T_profile).into(imgProfile);
                        }

                    }
                } catch (JSONException e) {
                    progressDialog.cancel();
                    e.printStackTrace();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d("event", "error");
            }
        });
        objectRequest.setRetryPolicy(new DefaultRetryPolicy(
                3000,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        RequestQueue requestQueue = Volley.newRequestQueue(Trainer_profile.this);
        requestQueue.add(objectRequest);
    }



}
