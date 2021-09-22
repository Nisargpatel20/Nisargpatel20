package com.example.nisargpatel.trainerhunt.Trainer_fragment;


import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.CardView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.nisargpatel.trainerhunt.User_fragment.HomeFragment;
import com.example.nisargpatel.trainerhunt.activity.R;
import com.example.nisargpatel.trainerhunt.activity.Trainer_profile;
import com.example.nisargpatel.trainerhunt.constant;
import com.example.nisargpatel.trainerhunt.portfolio;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import static com.example.nisargpatel.trainerhunt.activity.login.PREFS_NAME;

/**
 * A simple {@link Fragment} subclass.
 */
public class Trainer_ProfileFragment extends Fragment {
    TextView textMaininfo,textDegree,textExp,textMobileno,textEmailid,textAddress1,textPrice,textName,textaddress2;
    ProgressDialog progressDialog;
    CardView card1,addresscard,card2,contact_card;
    SharedPreferences sharedPreferences;
    int id;
    public Trainer_ProfileFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view=inflater.inflate(R.layout.fragment_trainer__profile, container, false);
        textMaininfo=(TextView)view.findViewById(R.id.trainer_maininfo);
        textDegree=view.findViewById(R.id.trainer_degree);
        textExp=view.findViewById(R.id.trainer_experience);
        textMobileno=view.findViewById(R.id.trainer_mobileno);
        textEmailid=view.findViewById(R.id.trainer_emaild);
        textAddress1=view.findViewById(R.id.trainer_address);
        textPrice=view.findViewById(R.id.trainer_price);
        textName=view.findViewById(R.id.trainer_name);
        textaddress2=view.findViewById(R.id.trainer_address2);
        card1=(CardView)view.findViewById(R.id.profile_emailcard);
        addresscard=(CardView)view.findViewById(R.id.profie_addresscard);
        addresscard.setCardBackgroundColor(Color.TRANSPARENT);
        card1.setBackgroundResource(R.drawable.cardview_border);
        sharedPreferences = getActivity().getSharedPreferences(PREFS_NAME, 0);
        id = sharedPreferences.getInt("TT_Id", 0);
        contact_card=(CardView) view.findViewById(R.id.contact_card);
        addresscard.setCardElevation(0);
        card2=(CardView)view.findViewById(R.id.cardview1) ;
        card1.setCardBackgroundColor(Color.TRANSPARENT);
        card2.setCardBackgroundColor(Color.TRANSPARENT);
        card2.setCardElevation(0);
        Toast.makeText(getActivity(), "Shared id is"+id, Toast.LENGTH_SHORT).show();

        Trainer_profile();
        return view;
    }
    public void Trainer_profile()
    {
        progressDialog = new ProgressDialog(getContext());
        progressDialog.setCancelable(false);
        progressDialog.setMessage("Wait");
        progressDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        progressDialog.show();
        progressDialog.setContentView(R.layout.ex_loader);
        JsonObjectRequest objectRequest = new JsonObjectRequest(Request.Method.GET, constant.PATH+"trainer_details_api.php?tid="+id,
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
                           /* imgPortfolio.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    Intent intent=new Intent(getActivity(), portfolio.class);
                                    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                                    // Toast.makeText(Trainer_profile.this, "ID"+T_id, Toast.LENGTH_SHORT).show();
                                    intent.putExtra("vdid",T_id);
                                    startActivity(intent);

                                }
                            });*/
          /*                  contact_card.setOnClickListener(new View.OnClickListener() {
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
                            });*/


                           // Picasso.with(getActivity()).load(constant.IMAGE + T_profile).into(imgProfile);
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
        RequestQueue requestQueue = Volley.newRequestQueue(getContext());
        requestQueue.add(objectRequest);
    }



}
