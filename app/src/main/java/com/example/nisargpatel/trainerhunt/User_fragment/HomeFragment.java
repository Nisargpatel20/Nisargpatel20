package com.example.nisargpatel.trainerhunt.User_fragment;


import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;


import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.daimajia.slider.library.Animations.DescriptionAnimation;
import com.daimajia.slider.library.Indicators.PagerIndicator;
import com.daimajia.slider.library.SliderLayout;
import com.daimajia.slider.library.SliderTypes.BaseSliderView;
import com.daimajia.slider.library.SliderTypes.TextSliderView;
import com.daimajia.slider.library.Tricks.ViewPagerEx;
import com.example.nisargpatel.trainerhunt.Adapter.category_adapter;
import com.example.nisargpatel.trainerhunt.Pojo.category_pojo;
import com.example.nisargpatel.trainerhunt.activity.R;
import com.example.nisargpatel.trainerhunt.constant;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class HomeFragment extends Fragment{
    category_pojo eventBean;
    ArrayList<category_pojo> event;
    category_adapter adapter;
    public static int s1;
    ProgressDialog progressDialog;
    RecyclerView recyclerView;
    private SliderLayout mDemoSlider;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        List<category_pojo> productList;
        View view = inflater.inflate(R.layout.fragment_home, container, false);
        recyclerView = (RecyclerView) view.findViewById(R.id.Recycle_view);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        event = new ArrayList<category_pojo>();
        allEvent();
        recyclerView.setAdapter(adapter);
        return view;

    }

    public void allEvent() {
        progressDialog = new ProgressDialog(getContext());
        progressDialog.setCancelable(false);
        progressDialog.setMessage("Wait");
        progressDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        progressDialog.show();
        progressDialog.setContentView(R.layout.ex_loader);
        JsonObjectRequest objectRequest = new JsonObjectRequest(Request.Method.GET, constant.PATH+"category_api.php",
                null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                Log.d("event", response.toString());
                //Toast.makeText(getActivity(), "" + response.toString(), Toast.LENGTH_SHORT).show();
                try {
                    int code = response.getInt("status");
                    if (code == 200) {
                        progressDialog.cancel();
                        JSONArray array = response.getJSONArray("result");
                        for (int n = 0; n < array.length(); n++) {
                            JSONObject obj = array.getJSONObject(n);
                            eventBean = new category_pojo();

                            int eventId = obj.getInt("id");
                            String eventName = obj.getString("name");
                            String eventBanner = obj.getString("quotes");
                            String eventBanner2 = obj.getString("images");


                            eventBean.setId(eventId);
                           eventBean.setImage(eventBanner2);
                            eventBean.setName(eventName);
                            eventBean.setQuotes(eventBanner);
                            event.add(eventBean);
                            Log.d("kush", "" + obj + eventName + eventBanner);

                        }
                        adapter = new category_adapter(getActivity(), event);
                        recyclerView.setAdapter(adapter);
                        final LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
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
        RequestQueue requestQueue = Volley.newRequestQueue(getContext());
        requestQueue.add(objectRequest);
    }
}

