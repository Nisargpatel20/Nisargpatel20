package com.example.nisargpatel.trainerhunt.activity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
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

import java.util.HashMap;
import java.util.Map;

import static com.example.nisargpatel.trainerhunt.activity.login.PREFS_NAME;

public class change_password extends AppCompatActivity {

    EditText edtcurrentpassword, edtnewpassword, edtreenterpassword;
    Button btnchangesave, btnchangecancel;
    ProgressDialog progressDialog;
    SharedPreferences sharedPreferences;
    int UserId;
    String email;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cpassword);
        edtcurrentpassword = (EditText) findViewById(R.id.currentpassword);
        edtnewpassword = (EditText) findViewById(R.id.newpassword);
        edtreenterpassword = (EditText) findViewById(R.id.reenterpassword);

//        preferences = getApplicationContext().getSharedPreferences(PREFS_NAME, 0);
//        UserId = preferences.getInt("userId", 0);

        btnchangesave = (Button) findViewById(R.id.btnChangeSave);
        btnchangecancel = (Button) findViewById(R.id.btnChangeCancel);
        sharedPreferences = this.getSharedPreferences(PREFS_NAME, 0);
        email = sharedPreferences.getString("emailId",null);
        //Toast.makeText(change_password.this, ""+email, Toast.LENGTH_SHORT).show();

        btnchangecancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
        btnchangesave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (edtcurrentpassword.getText().length() < 1) {
                    edtcurrentpassword.setError("Please Enter The Current Password");
                } else {
                    if (edtnewpassword.getText().toString().trim().equals(edtreenterpassword.getText().toString().trim())) {
                        change_password1();
                    } else {
                        Toast.makeText(change_password.this, "does not match the password", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });
    }
    public void change_password1() {
        progressDialog = new ProgressDialog(this);
        progressDialog.setCancelable(false);
        progressDialog.setMessage("Wait");
        progressDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        progressDialog.show();
        progressDialog.setContentView(R.layout.ex_loader);
        StringRequest sr = new StringRequest(Request.Method.POST, constant.PATH +"change_password.php",
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.e("HttpClient", "success! response: " + response.toString());
                        try {
                            JSONObject object = new JSONObject(response.toString());
                            int code = object.getInt("status");
                            if (code == (1)) {
                                progressDialog.cancel();
                                Toast.makeText(change_password.this, "Successfully password is changed ", Toast.LENGTH_SHORT).show();
                                startActivity(new Intent(change_password.this, login.class));
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
                object.put("old_password",edtcurrentpassword.getText().toString());
                object.put("new_password", edtnewpassword.getText().toString());
                object.put("email",email);
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


