package com.example.nisargpatel.trainerhunt;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
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
import com.example.nisargpatel.trainerhunt.Adapter.portfolio_adapter;
import com.example.nisargpatel.trainerhunt.Pojo.portfolio_pojo;
import com.example.nisargpatel.trainerhunt.activity.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class portfolio extends AppCompatActivity {
    portfolio_pojo eventBean;
    ArrayList<portfolio_pojo> event;
    portfolio_adapter adapter;
    RecyclerView recyclerView;
    ProgressDialog progressDialog;
    SharedPreferences sharedPreferences;
    int vdid;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_portfolio);
        recyclerView = (RecyclerView)findViewById(R.id.trainer_recycleview);
        recyclerView.setHasFixedSize(true);
        event=new ArrayList<portfolio_pojo>();
        Intent intent=getIntent();
        vdid=intent.getIntExtra("vdid",0);
        Toast.makeText(this, "HELLO id is"+vdid, Toast.LENGTH_SHORT).show();

        allEvent();

        recyclerView.setAdapter(adapter);
    }
    public void allEvent() {
        progressDialog = new ProgressDialog(this);
        progressDialog.setCancelable(false);
        progressDialog.setMessage("Wait");
        progressDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        progressDialog.show();
        progressDialog.setContentView(R.layout.ex_loader);
        JsonObjectRequest objectRequest = new JsonObjectRequest(Request.Method.GET, constant.PATH +"portfolio.php?vdid="+vdid,
                null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                Log.d("event", response.toString());
                //  Toast.makeText(getActivity(), "" + response.toString(), Toast.LENGTH_SHORT).show();
                try {
                    int code = response.getInt("status");
                    if (code == 200) {
                        progressDialog.cancel();
                        JSONArray array = response.getJSONArray("result");
                        for (int n = 0; n < array.length(); n++) {
                            JSONObject obj = array.getJSONObject(n);
                            eventBean = new portfolio_pojo();

                            String eventBanner2=obj.getString("image");




                            eventBean.setImage(eventBanner2);
                            event.add(eventBean);
                            Log.d("kush", "" + obj  +  eventBanner2);
                        }
                        adapter = new portfolio_adapter(portfolio.this, event);
                        recyclerView.setAdapter(adapter);
                        final LinearLayoutManager layoutManager = new LinearLayoutManager(portfolio.this);
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
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(objectRequest);
    }


}
