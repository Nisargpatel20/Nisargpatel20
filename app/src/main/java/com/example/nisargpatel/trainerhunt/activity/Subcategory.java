package com.example.nisargpatel.trainerhunt.activity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
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
import com.example.nisargpatel.trainerhunt.Adapter.subcategory_adapter;
import com.example.nisargpatel.trainerhunt.Pojo.subcategory_pojo;
import com.example.nisargpatel.trainerhunt.User_fragment.HomeFragment;
import com.example.nisargpatel.trainerhunt.constant;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;


public class Subcategory extends AppCompatActivity {
    subcategory_pojo eventBean;
    ArrayList<subcategory_pojo> event;
    subcategory_adapter adapter;
    int id;
    RecyclerView recyclerView;
    ProgressDialog progressDialog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_subcategory);
        List<subcategory_pojo> productList;

        recyclerView = (RecyclerView) findViewById(R.id.Recycle_view1);

        Intent intent = getIntent();
        id = intent.getIntExtra("id", 0);
        Toast.makeText(this, ""+id, Toast.LENGTH_SHORT).show();
        event = new ArrayList<subcategory_pojo>();
        allEvent();
        return;
    }
    public void allEvent() {
        progressDialog = new ProgressDialog(this);
        progressDialog.setCancelable(false);
        progressDialog.setMessage("Wait");
        progressDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        progressDialog.show();
        progressDialog.setContentView(R.layout.ex_loader);

        JsonObjectRequest objectRequest = new JsonObjectRequest(Request.Method.GET, constant.PATH+"subcat_api.php?id="+id,
                null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                Log.d("event", response.toString());
              //  Toast.makeText(Subcategory.this, "" + response.toString(), Toast.LENGTH_SHORT).show();
                try {
                    int code = response.getInt("status");
                    if (code == 200) {
                        progressDialog.cancel();
                        JSONArray array = response.getJSONArray("result");
                        for (int n = 0; n < array.length(); n++) {
                            JSONObject obj = array.getJSONObject(n);
                            eventBean = new subcategory_pojo();

                            String eventName = obj.getString("name");
                            String eventBanner2 = obj.getString("images");
                            int eventBanner3 = obj.getInt("sid");

                            eventBean.setId(eventBanner3);
                            eventBean.setImage(eventBanner2);
                            eventBean.setName(eventName);
                            event.add(eventBean);
                            Log.d("kush", "" + obj + eventName +eventBanner2);
                        }
                        adapter = new subcategory_adapter(getApplicationContext(), event);
                        recyclerView.setAdapter(adapter);
                        recyclerView.setHasFixedSize(true);// Declare in and out
                        final LinearLayoutManager layoutManager = new LinearLayoutManager(Subcategory.this);
                        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
                       // recyclerView.setLayoutManager(new GridLayoutManager(this,2));
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
        RequestQueue requestQueue = Volley.newRequestQueue(Subcategory.this);
        requestQueue.add(objectRequest);
    }
}

