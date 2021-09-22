package com.example.nisargpatel.trainerhunt.activity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.RadioGroup;
import android.widget.Toast;


import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;


import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.nisargpatel.trainerhunt.constant;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;



import java.security.PublicKey;
import java.util.HashMap;
import java.util.Map;

public class signup extends AppCompatActivity {

    private static final String TAG =null;
    Button btn_submit;
    EditText editText3,editText4,editText5,editText6,editText7;
    ProgressDialog progressDialog;
   RadioGroup rGroup;
   String u_type;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        btn_submit =(Button)findViewById(R.id.btn_submit);
        editText3 =(EditText)findViewById(R.id.edit_name);
        editText4 =(EditText)findViewById(R.id.edit_email);
        editText5 =(EditText)findViewById(R.id.edit_password);
        final String email = editText4.getText().toString().trim();
        final String emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\\\.+[a-z]+";
        editText6 =(EditText)findViewById(R.id.edit_mobilenumber);
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
                    if (u_type.equals("I am trainer")){
                        u_type = "trainer";
                    }
                    else {
                        u_type = "user";
                    }
                    Toast.makeText(signup.this, ""+u_type, Toast.LENGTH_SHORT).show();
                }
            }
        });


        btn_submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (editText3.getText().length() < 1) {
                    editText3.setError("Enter First Name");
                } else if (editText4.getText().length() < 1) {
                    editText4.setError("Enter Email-id");
                } else if (editText5.getText().length() < 1) {
                    editText5.setError("Enter Password");
                } else if (editText6.getText().length() < 1) {
                    editText6.setError("Enter Mobilenumber");
                } else {
                    signUp();
                }
            }
        });
    }
    public void signUp() {
        progressDialog = new ProgressDialog(this);
        progressDialog.setCancelable(false);
        progressDialog.setMessage("Wait");
        progressDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        progressDialog.show();
        progressDialog.setContentView(R.layout.ex_loader);
        StringRequest sr = new StringRequest(Request.Method.POST, constant.PATH +"signup.php",
                new com.android.volley.Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.d("HttpClient", "success! response: "+response.toString());
                        try {
                            JSONObject object = new JSONObject(response.toString());
                            String code = object.getString("status");

                                progressDialog.cancel();
                                Toast.makeText(signup.this, "Successfully Singing up ", Toast.LENGTH_SHORT).show();
                                startActivity(new Intent(signup.this, login.class));
                                finish();

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
                object.put("fname", editText3.getText().toString());
                object.put("email", editText4.getText().toString());
                object.put("contact", editText6.getText().toString());
                object.put("password", editText5.getText().toString());
               object.put("type",u_type);
                return object;
            }

            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> object = new HashMap<String, String>();
                object.put("Content-Type", "application/x-www-form-urlencoded");

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
    }


