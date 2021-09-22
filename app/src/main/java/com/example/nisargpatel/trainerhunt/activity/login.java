package com.example.nisargpatel.trainerhunt.activity;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.nisargpatel.trainerhunt.User_fragment.HomeFragment;
import com.example.nisargpatel.trainerhunt.constant;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;




public class login extends AppCompatActivity {
    Button btn_signin,btn_forgetpassword;
    EditText editText,editText1;
    TextView textView;
    SharedPreferences preferences;
    public static final String PREFS_NAME ="LoginPrefs";
    ProgressDialog  progressDialog;
       // ConnectionDetector detector;


    @SuppressLint("WrongConstant")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        btn_signin =(Button)findViewById(R.id.button_signin);
        editText1 =(EditText)findViewById(R.id.editpassword);
        editText =(EditText)findViewById(R.id.editemailid);
        textView =(TextView)findViewById(R.id.txtsignup);
        btn_forgetpassword =(Button)findViewById(R.id.btn_forgetpassword);
     //   detector = new ConnectionDetector(this);
        preferences=getSharedPreferences(PREFS_NAME,MODE_APPEND);
        if(preferences.getString("logged","").equals("logged"))
        {
              startActivity(new Intent(login.this,MainActivity.class));
              finish();
        }
     btn_signin.setOnClickListener(new View.OnClickListener() {
         @Override
         public void onClick(View v) {
             if (editText.getText().length()<1){
                 editText.setError("Please Enter Email-id");
             }else if (editText1.getText().length()<1){
                 editText1.setError("Please enter Password");
             } else {

                 checkLogin();
             }


         }
     });

     btn_forgetpassword.setOnClickListener(new View.OnClickListener() {
         @Override
         public void onClick(View v) {
             Intent intent = new Intent(login.this,forgotpassword.class);
             startActivity(intent);
         }
     });

     textView.setOnClickListener(new View.OnClickListener() {
         @Override
         public void onClick(View v) {
             Intent intent = new Intent(login.this,signup.class);
             startActivity(intent);
         }
     });

     editText.setOnClickListener(new View.OnClickListener() {
         @Override
         public void onClick(View v) {

         }
     });

     editText1.setOnClickListener(new View.OnClickListener() {
         @Override
         public void onClick(View v) {

         }
     });

    }


    private void checkLogin() {
        progressDialog = new ProgressDialog(login.this);
        progressDialog.setCancelable(false);
        progressDialog.setMessage("Wait");
        progressDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        progressDialog.show();
        progressDialog.setContentView(R.layout.ex_loader);

        StringRequest sr = new StringRequest(Request.Method.POST,constant.PATH +"login.php",
                new com.android.volley.Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.e("HttpClient", "success! response: " + response.toString());
                        try {
                            JSONObject object = new JSONObject(response.toString());
                            String code = object.getString("status");
                            if (code.equalsIgnoreCase("success")) {
                                JSONObject obj = object.getJSONObject("data");
                                Log.d("login",""+obj.toString());
                                int id = obj.getInt("id");
                                String fullname = obj.getString("full_name");
                                String mobNo = obj.getString("mobile_no");
                                String emailId = obj.getString("email_id");
                                String utype = obj.getString("type");
                                progressDialog.cancel();
                               preferences=getApplicationContext().getSharedPreferences(PREFS_NAME,0);
                                SharedPreferences.Editor editor=preferences.edit();
                                editor.putString("logged","logged");
                                editor.putInt("userId", id);
                                editor.putString("fullname",fullname);
                                editor.putString("mobNo",mobNo);
                                editor.putString("emailId",emailId);
                                editor.commit();
                                Toast.makeText(login.this, "Successfully Login", Toast.LENGTH_SHORT).show();

                                if(utype.equals("user"))                               {
                                    startActivity(new Intent(login.this, MainActivity.class));

                                }
                                else
                                    {

                                    startActivity(new Intent(login.this, Trainer_form.class));
                                }
                                finish();
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
                        progressDialog.cancel();
                        Toast.makeText(login.this, "emailid and password did not match", Toast.LENGTH_SHORT).show();
                        Log.e("HttpClient", "error: " + error.toString());
                    }
                }) {
            @Override
            protected Map<String, String> getParams() {

                Map<String, String> object = new HashMap<String, String>();
                object.put("email", editText.getText().toString());
                object.put("password", editText1.getText().toString());
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



