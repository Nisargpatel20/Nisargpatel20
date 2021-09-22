package com.example.nisargpatel.trainerhunt.User_fragment;


import android.app.ProgressDialog;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.nisargpatel.trainerhunt.Adapter.trainer_home_adapter;
import com.example.nisargpatel.trainerhunt.Adapter.trainerlist_adapter;
import com.example.nisargpatel.trainerhunt.Adapter.wishlist_adapter;
import com.example.nisargpatel.trainerhunt.Pojo.trainer_home_pojo;
import com.example.nisargpatel.trainerhunt.Pojo.trainerlist_pojo;
import com.example.nisargpatel.trainerhunt.activity.R;
import com.example.nisargpatel.trainerhunt.constant;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import static com.example.nisargpatel.trainerhunt.activity.login.PREFS_NAME;

/**
 * A simple {@link Fragment} subclass.
 */
public class WishlistFragment extends Fragment {
    trainerlist_pojo eventBean;
    ArrayList<trainerlist_pojo> event;
    wishlist_adapter adapter;
    RecyclerView recyclerView;

    ProgressDialog progressDialog;
    SharedPreferences sharedPreferences;
    int UserId;

    public WishlistFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_wishlist, container, false);
        recyclerView = (RecyclerView) view.findViewById(R.id.trainer_recycleview);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        event = new ArrayList<trainerlist_pojo>();
        sharedPreferences = getActivity().getSharedPreferences(PREFS_NAME, 0);
        UserId = sharedPreferences.getInt("userId", 0);
        allEvent();

        return view;
    }
    public void allEvent() {
        progressDialog = new ProgressDialog(getContext());
        progressDialog.setCancelable(false);
        progressDialog.setMessage("Wait");
        progressDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        progressDialog.show();
        progressDialog.setContentView(R.layout.ex_loader);
        StringRequest objectRequest = new StringRequest(Request.Method.POST, constant.PATH + "wishlist.php",
                new com.android.volley.Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.d("event", response.toString());
                        //Toast.makeText(getActivity(), "" + response.toString(), Toast.LENGTH_SHORT).show();
                        try {

                            JSONArray array1=new JSONArray(response);
                            JSONObject object=array1.getJSONObject(0);
                            int code = object.getInt("status");
                            if (code == 200) {
                                progressDialog.cancel();
                                JSONArray array = object.getJSONArray("result");
                                for (int n = 0; n < array.length(); n++) {
                                    JSONObject obj = array.getJSONObject(n);
                                    eventBean = new trainerlist_pojo();

                                    int eventid = obj.getInt("t_id");
                                    String eventName = obj.getString("t_name");
                                    String eventBanner = obj.getString("t_workaddress1");
                                    String eventBanner2 = obj.getString("t_profile_image");
                                    String eventBanner3 = obj.getString("t_price");
                                   // String eventBanner4 = obj.getString("price");

                                    eventBean.setId(eventid);
                                    eventBean.setImage(eventBanner2);
                                    eventBean.setName(eventName);
                                    eventBean.setAddress(eventBanner);
                                    eventBean.setPrice(eventBanner3);

                                    event.add(eventBean);
                                    Log.d("kush", "" + obj + eventName + eventBanner);

                                }
                                adapter = new wishlist_adapter(getActivity(), event);
                                recyclerView.setAdapter(adapter);
                                final LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
                                layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
                                recyclerView.setLayoutManager(layoutManager);
                            }
                        } catch (JSONException e) {
                            progressDialog.cancel();
                           // Toast.makeText(getActivity(), "error", Toast.LENGTH_SHORT).show();
                            e.printStackTrace();
                        }

                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                progressDialog.cancel();
                Log.d("event", error.toString());

            }
        }) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> object = new HashMap<String, String>();
                object.put("user_id", String.valueOf(UserId));
                //  object.put("password", editText1.getText().toString());
                return object;
            }
        };

        objectRequest.setRetryPolicy(new DefaultRetryPolicy(
                3000,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        RequestQueue requestQueue = Volley.newRequestQueue(getContext());
        requestQueue.add(objectRequest);
    }


}
