package com.example.nisargpatel.trainerhunt.User_fragment;


import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.v4.app.Fragment;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;


import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.nisargpatel.trainerhunt.Adapter.trainerlist_adapter;
import com.example.nisargpatel.trainerhunt.Pojo.trainerlist_pojo;
import com.example.nisargpatel.trainerhunt.activity.Main2Activity;
import com.example.nisargpatel.trainerhunt.activity.MainActivity;
import com.example.nisargpatel.trainerhunt.activity.OTP_activity;
import com.example.nisargpatel.trainerhunt.activity.R;
import com.example.nisargpatel.trainerhunt.activity.Trainer_form;
import com.example.nisargpatel.trainerhunt.activity.Trainer_profile;
import com.example.nisargpatel.trainerhunt.activity.Trainers_list;
import com.example.nisargpatel.trainerhunt.activity.forgotpassword;
import com.example.nisargpatel.trainerhunt.activity.login;
import com.example.nisargpatel.trainerhunt.constant;
import com.miguelcatalan.materialsearchview.MaterialSearchView;

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
public class SearchFragment extends Fragment implements SearchView.OnQueryTextListener {
    private Toolbar toolbar;
    ProgressDialog progressDialog;
    trainerlist_pojo eventBean;
    ArrayList<trainerlist_pojo> event;
    trainerlist_adapter adapter;
    RecyclerView recyclerView;
    String demostring = "K";
    SearchView searchView;


    public SearchFragment() {
        // Required empty public constructor
    }


    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override

    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_search, container, false);
        toolbar = view.findViewById(R.id.toolbar);
        recyclerView = (RecyclerView) view.findViewById(R.id.trainer_recycleview);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        event = new ArrayList<trainerlist_pojo>();
        // getSupportActionBar().hide();
        ((AppCompatActivity) getContext()).setSupportActionBar(toolbar);

        setHasOptionsMenu(true);
       // checkLogin();

        //recyclerView.setAdapter(adapter);
        //  searchView.setOnQueryTextListener(this);


        //setSupportActionBar(toolbar);
        //  getsupportActionBar().setTitle("Material Search");
        //toolbar.setTitleTextColor(Color.parseColor("#ffffff"));
        //  searchView = (MaterialSearchView) view.findViewById(R.id.search_view);
        return view;
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        menu.clear();
        inflater.inflate(R.menu.menu_item, menu);
        MenuItem item = menu.findItem(R.id.search_item);
        searchView = new SearchView(((MainActivity) getActivity()).getSupportActionBar().getThemedContext());
        MenuItemCompat.setShowAsAction(item, MenuItemCompat.SHOW_AS_ACTION_COLLAPSE_ACTION_VIEW | MenuItemCompat.SHOW_AS_ACTION_IF_ROOM);
        MenuItemCompat.setActionView(item, searchView);
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {


            @Override
            public boolean onQueryTextSubmit(String s) {

                Toast.makeText(getActivity(), "search demo" + s, Toast.LENGTH_SHORT).show();
                // String tempitem = s;
                // search();
                event.clear();
                temp(s);

                return true;
            }

            @Override
            public boolean onQueryTextChange(String s) {
                return false;
            }
        });

        searchView.setOnClickListener(new View.OnClickListener() {
                                          @Override
                                          public void onClick(View v) {
                                              Toast.makeText(getActivity(), "hello search", Toast.LENGTH_SHORT).show();

                                          }
                                      }
        );

    }


    @Override
    public boolean onQueryTextSubmit(String s) {
        return false;
    }

    @Override
    public boolean onQueryTextChange(String s) {
        return false;
    }


    public void temp(final String s){
        progressDialog = new ProgressDialog(getContext());
        progressDialog.setCancelable(false);
        progressDialog.setMessage("Wait");
        progressDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        progressDialog.show();
        progressDialog.setContentView(R.layout.ex_loader);
        StringRequest sr = new StringRequest(Request.Method.POST,constant.PATH +"search.php",
                new com.android.volley.Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.e("HttpClient", "success! response: " + response.toString());
                        try {
                            progressDialog.cancel();
                           JSONObject object=new JSONObject(response);
                           int count=object.getInt("count");
                            Log.d("HttpClient", "success! response: " + object.getInt("count"));
                            for (int i = 0; i <count; i++) {
                                JSONObject index=object.getJSONObject(String.valueOf(i));
                                JSONObject data=index.getJSONObject("data");
                                Log.d("HttpClient", "success! response: " + data.getString("t_name"));
                                eventBean = new trainerlist_pojo();
                                int eventid = data.getInt("t_id");
                                String eventName = data.getString("t_name");
                                String eventBanner = data.getString("t_workaddress1");
                                String eventBanner2 = data.getString("t_profile_image");
                                String eventBanner3 = data.getString("t_price");
                               // Toast.makeText(getActivity(), "Details"+eventName+eventBanner, Toast.LENGTH_SHORT).show();
                               // String eventBanner4 = data.getString("price");

                                eventBean.setId(eventid);
                                eventBean.setImage(eventBanner2);
                                eventBean.setName(eventName);
                                eventBean.setAddress(eventBanner);
                                eventBean.setPrice(eventBanner3);
                                event.add(eventBean);
                                Log.d("kush", "" + data + eventName + eventBanner);



                            }

                            adapter = new trainerlist_adapter(getContext(), event);
                            recyclerView.setAdapter(adapter);
                            final LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
                            layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
                            recyclerView.setLayoutManager(layoutManager);


                        } catch (JSONException e) {
                           // progressDialog.cancel();
                            e.printStackTrace();
                        }
                    }
                },
                new com.android.volley.Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.e("HttpClient", "error: " + error.toString());
                    }
                }) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> object = new HashMap<String, String>();
                object.put("cat_name",s);
              //  object.put("password", editText1.getText().toString());
                return object;
            }

        };

        final RequestQueue requestQueue = Volley.newRequestQueue(getContext());
        requestQueue.add(sr);
        requestQueue.addRequestFinishedListener(new RequestQueue.RequestFinishedListener<Object>() {
            @Override
            public void onRequestFinished(Request<Object> request) {
                requestQueue.getCache().clear();
            }
        });


    }





/*   private void checkLogin() {
        progressDialog = new ProgressDialog(getContext());
        progressDialog.setCancelable(false);
       progressDialog.setMessage("Wait");
        progressDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        progressDialog.show();
       progressDialog.setContentView(R.layout.ex_loader);

        StringRequest sr = new StringRequest(Request.Method.POST,constant.PATH +"search.php",
               new com.android.volley.Response.Listener<String>() {
                  @Override
                    public void onResponse(String response) {
                      Log.e("HttpClient", "success! response: " + response.toString());
                        try {
                            JSONObject object = new JSONObject(response.toString());
                            int code = object.getInt("status");
                            if (code==1) {
                                progressDialog.cancel();
                                JSONObject obj = object.getJSONObject("data");
                                Log.d("login",""+obj.toString());

                                for (int n = 0; n <10; n++) {
                                    JSONObject obj = getJSONObject(n);
                                    eventBean = new trainerlist_pojo();
                                    int eventid = obj.getInt("tid");
                                    String eventName = obj.getString("name");
                                    String eventBanner = obj.getString("shortinfo");
                                    String eventBanner2 = obj.getString("images");
                                    String eventBanner3 = obj.getString("price");
                                    eventBean.setId(eventid);
                                    eventBean.setImage(eventBanner2);
                                    eventBean.setName(eventName);
                                    eventBean.setAddress(eventBanner);
                                    eventBean.setPrice(eventBanner3);
                                    event.add(eventBean);
                                    Log.d("kush", "" + obj + eventName + eventBanner);

                                    //int id = obj.getInt("id");




                              preferences=getApplicationContext().getSharedPreferences(PREFS_NAME,0);
                                SharedPreferences.Editor editor=preferences.edit();
                                editor.putString("logged","logged");
                                editor.putInt("userId", id);
                                editor.putString("fullname",fullname);
                                editor.putString("mobNo",mobNo);
                                editor.putString("emailId",emailId);
                                editor.commit();
                                Toast.makeText(getActivity(), "Successfully Login", Toast.LENGTH_SHORT).show();


                                    //startActivity(new Intent(getActivity(), MainActivity.class));

                                    //startActivity(new Intent(getActivity(), Trainer_form.class));

                            }
                        } catch (JSONException e) {
                            progressDialog.cancel();
                            e.printStackTrace();
                        }
                    }
                },
                new com.android.volley.Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.e("HttpClient", "error: " + error.toString());
                    }
                }) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> object = new HashMap<String, String>();
                object.put("t_name","K");
              //  object.put("password", editText1.getText().toString());
                return object;
            }

        *//*    @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> object = new HashMap<String, String>();
                object.put("Content-Type", "application/x-www-form-urlencoded");
                return object;
            }*//*
        };

        final RequestQueue requestQueue = Volley.newRequestQueue(getContext());
        requestQueue.add(sr);
        requestQueue.addRequestFinishedListener(new RequestQueue.RequestFinishedListener<Object>() {
            @Override
            public void onRequestFinished(Request<Object> request) {
                requestQueue.getCache().clear();
            }
        });

    }*/
  /* public void search() {
        progressDialog = new ProgressDialog(getContext());
        progressDialog.setCancelable(false);
        progressDialog.setMessage("Wait");
        progressDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        progressDialog.show();
        progressDialog.setContentView(R.layout.ex_loader);
        StringRequest sr = new StringRequest(Request.Method.POST, constant.PATH +"search.php?cat_name"+"Cricket",
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.e("HttpClient", "success! response: " + response.toString());
                        try {
                            JSONObject object = new JSONObject(response.toString());
                            String code = object.getString("status");
                            if (code.equalsIgnoreCase("1")) {
                                progressDialog.cancel();
                                eventBean = new trainerlist_pojo();
                                int eventid = object.getInt("tid");
                                String eventName = object.getString("name");
                                String eventBanner = object.getString("shortinfo");
                                String eventBanner2 = object.getString("images");
                                String eventBanner3 = object.getString("price");
                                eventBean.setId(eventid);
                                eventBean.setImage(eventBanner2);
                                eventBean.setName(eventName);
                                eventBean.setAddress(eventBanner);
                                eventBean.setPrice(eventBanner3);
                                event.add(eventBean);
                                Log.d("kush", "" + object + eventName + eventBanner);


                                Toast.makeText(getActivity(), ""+eventName+eventBanner3, Toast.LENGTH_LONG).show();
                                startActivity(new Intent(getActivity(), Trainer_profile.class));
                            }
                        } catch (JSONException e) {
                            Toast.makeText(getActivity(),"Error",Toast.LENGTH_SHORT).show();
                            progressDialog.cancel();
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        progressDialog.cancel();
                        Toast.makeText(getActivity(),"Error2",Toast.LENGTH_SHORT).show();
                        Log.e("HttpClient", "error: " + error.toString());
                    }
                }) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> object = new HashMap<String, String>();

                object.put("cat_name","Cricket");

                return object;
            }

            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> object = new HashMap<String, String>();
                object.put("Content-Type", "application/x-www-form-urlencoded");

                return object;
            }
        };

        final RequestQueue requestQueue = Volley.newRequestQueue(getContext());
        requestQueue.add(sr);
        requestQueue.addRequestFinishedListener(new RequestQueue.RequestFinishedListener<Object>() {
            @Override
            public void onRequestFinished(Request<Object> request) {
                requestQueue.getCache().clear();
            }
        });
    }*/

}

