package com.example.nisargpatel.trainerhunt.activity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;
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
import com.example.nisargpatel.trainerhunt.Adapter.category_adapter;
import com.example.nisargpatel.trainerhunt.Adapter.subcategory_adapter;
import com.example.nisargpatel.trainerhunt.Pojo.category_pojo;
import com.example.nisargpatel.trainerhunt.Pojo.subcategory_pojo;
import com.example.nisargpatel.trainerhunt.constant;
import com.example.nisargpatel.trainerhunt.upload_photo;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import static com.example.nisargpatel.trainerhunt.activity.login.PREFS_NAME;

public class Trainer_form<id1> extends AppCompatActivity implements AdapterView.OnItemSelectedListener {
    EditText edtName,edtEmail,edtNumber,edtDegree,edtExperience,edtAddress1,edtAddress2,edtInfo;
    ImageView imgPROFILE;
    RadioGroup rGroup;

    String u_type;

    ProgressDialog progressDialog;
    Button btn_SUBMIT,demo_click;
    ArrayList<String> spinnerArray = new ArrayList<String>();
    ArrayList<String> spinnerArray2 = new ArrayList<String>();

    String texperienc;
    Spinner SpField,spSUbFIELD;
    int tfield,id;
    Object value;
   char id1;
   String tsubfield;
   SharedPreferences preferences;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_trainer_form);
        edtName=findViewById(R.id.edtName);
        edtEmail=findViewById(R.id.edtEmail);
        edtNumber=findViewById(R.id.edtnumber);
        edtDegree=findViewById(R.id.edtdegree);
        edtAddress1=findViewById(R.id.edtaddress1);
        edtAddress2=findViewById(R.id.edtaddress2);
        edtInfo=findViewById(R.id.edtinfo);
        demo_click=findViewById(R.id.Btn_submitted);
        imgPROFILE=findViewById(R.id.trainer_profile_IMG);
        imgPROFILE.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(Trainer_form.this, upload_photo.class);
            }
        });

        final Spinner spinner = (Spinner) findViewById(R.id.spinner_experience);

        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.experience, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
        //texperienc=spinner.getSelectedItem().toString();

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                texperienc=spinner.getSelectedItem().toString();

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        allEvent();
       // final Spinner SpField=new Spinner(this);
        SpField=(Spinner)findViewById(R.id.spinner_field);




 //selected item will look like a spinner set from XML


        SpField.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                tfield=SpField.getSelectedItemPosition();
                spinnerArray2.clear();// tfield=SpField.getSelectedItem().toString();
                subcat_id();


            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        spSUbFIELD=(Spinner)findViewById(R.id.spinner_subfield);
        spSUbFIELD.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                tsubfield=spSUbFIELD.getSelectedItem().toString();
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });



     demo_click.setOnClickListener(new View.OnClickListener() {
         @Override
         public void onClick(View v) {
            Intent intent=new Intent(Trainer_form.this,Main2Activity.class);
            startActivity(intent);
         }
     });
        btn_SUBMIT=findViewById(R.id.btn_profile_submit);

        btn_SUBMIT.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (edtName.getText().length() < 1) {
                    edtName.setError("Enter Full Name");
                } else if (edtEmail.getText().length() < 1) {
                    edtEmail.setError("Enter Email-id");
                } else if (edtDegree.getText().length() < 1) {
                    edtDegree.setError("Enter your degree");
                } else if (edtNumber.getText().length() < 1) {
                    edtNumber.setError("Enter Mobile number");
                }   else if (edtAddress1.getText().length() < 1) {
                    edtAddress1.setError("Enter Mobilenumber");
                }



                else {
                    signUp();
                    Intent intent=new Intent(Trainer_form.this,Main2Activity.class);
                    startActivity(intent);
                }
            }
        });


        rGroup = (RadioGroup)findViewById(R.id.radiogroup);

        rGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                RadioButton checkedRadioButton = (RadioButton)group.findViewById(checkedId);
                // This puts the value (true/false) into the variable
                boolean isChecked = checkedRadioButton.isChecked();
                // If the radiobutton that has changed in check state is now checked...
                if (isChecked)
                {
                    u_type = checkedRadioButton.getText().toString();
                    if (u_type.equals("male")){
                        u_type = "male";
                    }
                    else {
                        u_type = "female";
                    }

                }
            }
        });
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {




    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }

    public void signUp() {
        progressDialog = new ProgressDialog(this);
        progressDialog.setCancelable(false);
        progressDialog.setMessage("Wait");
        progressDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        progressDialog.show();
        progressDialog.setContentView(R.layout.ex_loader);
        StringRequest sr = new StringRequest(Request.Method.POST, constant.PATH +"Trainer_form.php",
                new  com.android.volley.Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.e("HttpClient", "success! response: " + response.toString());
                        try {
                            JSONObject object = new JSONObject(response.toString());
                            String code = object.getString("status");
                            if (code.equalsIgnoreCase("success")) {

                                String obj=object.getString("data");
                              JSONObject object1=new JSONObject(obj.toString());
                                Log.d("HttpClient", "success! response: object1...." +object1);
                              String tname=object1.getString("t_name");
                              int T_id=object1.getInt("t_id");
                                preferences=getApplicationContext().getSharedPreferences(PREFS_NAME,0);
                                SharedPreferences.Editor editor=preferences.edit();

                                editor.putInt("TT_Id", T_id);
                                editor.commit();
                                Log.d("HttpClient", "success! response: tname===..." +tname);

                                progressDialog.cancel();
                                //JSONObject obj=response.getJSONObject("data");
                                Log.d("HttpClient", "success! response: " + object.getJSONObject("data"));
                                Toast.makeText(Trainer_form.this, "Successfully Submitting form ", Toast.LENGTH_SHORT).show();

                                finish();
                            }
                        } catch (JSONException e) {
                            progressDialog.cancel();
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        progressDialog.cancel();
                        Log.e("HttpClient", "error: " + error.toString());
                    }
                }) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> object = new HashMap<String, String>();
                object.put("tname", edtName.getText().toString());
                object.put("tcontact", edtNumber.getText().toString());
                object.put("temail", edtEmail.getText().toString());
                object.put("tdegree", edtDegree.getText().toString());
                object.put("texperience", texperienc);
                object.put("tworkaddress1", edtAddress1.getText().toString());
                object.put("tinfo", edtInfo.getText().toString());
                object.put("tgender",u_type);
                object.put("tworkaddress2",edtAddress2.getText().toString());
                object.put("s_name",tsubfield);
                return object;
            }


        };

        final RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(sr);
        requestQueue.addRequestFinishedListener(new RequestQueue.RequestFinishedListener<Object>() {
            @Override
            public void onRequestFinished(Request<Object> request) {
                requestQueue.getCache().clear();
            }
        });
    }

    public void allEvent() {
        progressDialog = new ProgressDialog(this);
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

                            int C_Id = obj.getInt("id");
                            String C_Name = obj.getString("name");


                            Log.d("kush", "" + obj + C_Id+ C_Name);
                            spinnerArray.add(C_Name);
                            ArrayAdapter<String> spinnerArrayAdapter = new ArrayAdapter<String>
                                    (Trainer_form.this, android.R.layout.simple_spinner_item,spinnerArray);
                            spinnerArrayAdapter.setDropDownViewResource(android.R.layout
                                    .simple_spinner_dropdown_item);
                            SpField.setAdapter(spinnerArrayAdapter);


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
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(objectRequest);
    }
    public void subcat_id() {
        //progressDialog = new ProgressDialog(this);
        //progressDialog.setCancelable(false);
        //progressDialog.setMessage("Wait");
        //progressDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        //progressDialog.show();
        //progressDialog.setContentView(R.layout.ex_loader);

        id=tfield+3;

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
                            int s_Id = obj.getInt("sid");
                            String eventName = obj.getString("name");
                            Log.d("kush", "" + obj + eventName );

                            spinnerArray2.add(eventName);
                            ArrayAdapter<String> spinnerArrayAdapter = new ArrayAdapter<String>
                                    (Trainer_form.this, android.R.layout.simple_spinner_item,spinnerArray2);
                            spinnerArrayAdapter.setDropDownViewResource(android.R.layout
                                    .simple_spinner_dropdown_item);
                            spSUbFIELD.setAdapter(spinnerArrayAdapter);


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
        RequestQueue requestQueue = Volley.newRequestQueue(Trainer_form.this);
        requestQueue.add(objectRequest);
    }
}
