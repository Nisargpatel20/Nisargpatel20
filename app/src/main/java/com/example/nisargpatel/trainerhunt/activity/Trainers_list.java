package com.example.nisargpatel.trainerhunt.activity;



import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.widget.Toast;


import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.nisargpatel.trainerhunt.Adapter.category_adapter;
import com.example.nisargpatel.trainerhunt.Adapter.trainerlist_adapter;
import com.example.nisargpatel.trainerhunt.Pojo.category_pojo;
import com.example.nisargpatel.trainerhunt.Pojo.trainerlist_pojo;
import com.example.nisargpatel.trainerhunt.constant;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;


public class Trainers_list extends AppCompatActivity {
    trainerlist_pojo eventBean;
    ArrayList<trainerlist_pojo> event;
    trainerlist_adapter adapter;
    RecyclerView recyclerView;

    int sid;
    ProgressDialog progressDialog;

    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_trainers_list);
        List<trainerlist_pojo> productList1;


        recyclerView = (RecyclerView) findViewById(R.id.trainer_recycleview);
        recyclerView.setHasFixedSize(true);
        Intent intent = getIntent();
        sid = intent.getIntExtra("sid", 0);
        //recyclerView.setLayoutManager(new LinearLayoutManager(this));
        event = new ArrayList<>();
        allEvent();
        //initializing the productlist
      //  productList1 = new ArrayList<>();
        //adding some items to our list
    }
    public void allEvent() {
        progressDialog = new ProgressDialog(this);
        progressDialog.setCancelable(false);
        progressDialog.setMessage("Wait");
        progressDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        progressDialog.show();
        progressDialog.setContentView(R.layout.ex_loader);
        JsonObjectRequest objectRequest = new JsonObjectRequest(Request.Method.GET, constant.PATH+"trainerlist_api.php?sid="+sid,
                null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                Log.d("event", response.toString());
               // Toast.makeText(Trainers_list.this, "" + response.toString(), Toast.LENGTH_SHORT).show();
                try {
                    int code = response.getInt("status");
                    if (code == 200) {
                        progressDialog.cancel();
                        JSONArray array = response.getJSONArray("result");
                        for (int n = 0; n < array.length(); n++) {
                            JSONObject obj = array.getJSONObject(n);
                            eventBean = new trainerlist_pojo();
                            int eventid = obj.getInt("tid");
                            String eventName = obj.getString("name");
                            String eventBanner = obj.getString("shortinfo");
                            String eventBanner2 = obj.getString("images");
                            String eventBanner3 = obj.getString("price");
                            String eventBanner4 = obj.getString("price");

                            eventBean.setId(eventid);
                            eventBean.setImage(eventBanner2);
                            eventBean.setName(eventName);
                            eventBean.setAddress(eventBanner);
                            eventBean.setPrice(eventBanner3);

                            event.add(eventBean);
                            Log.d("kush", "" + obj + eventName + eventBanner);

                        }
                        adapter = new trainerlist_adapter(getApplicationContext(), event);
                        recyclerView.setAdapter(adapter);
                        final LinearLayoutManager layoutManager = new LinearLayoutManager(Trainers_list.this);
                        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
                        recyclerView.setLayoutManager(layoutManager);
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
        RequestQueue requestQueue = Volley.newRequestQueue(Trainers_list.this);
        requestQueue.add(objectRequest);
    }
}
