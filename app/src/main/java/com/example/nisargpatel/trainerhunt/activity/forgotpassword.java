package com.example.nisargpatel.trainerhunt.activity;

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
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.nisargpatel.trainerhunt.activity.OTP_activity;
import com.example.nisargpatel.trainerhunt.constant;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import static com.example.nisargpatel.trainerhunt.activity.login.PREFS_NAME;

public class forgotpassword extends AppCompatActivity {
    Button btn_submit1;
    EditText edt_emailid;
    TextView textView_fp;
    SharedPreferences sharedPreferences;
    ProgressDialog progressDialog;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgotpassword);

        btn_submit1=(Button)findViewById(R.id.btn_submit1);
        edt_emailid=(EditText)findViewById(R.id.edit_emailid);
        textView_fp=(TextView)findViewById(R.id.text);
        btn_submit1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                    forgotpassword();

            }
        });

    }
    public void forgotpassword() {
        progressDialog = new ProgressDialog(this);
        progressDialog.setCancelable(false);
        progressDialog.setMessage("Wait");
        progressDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        progressDialog.show();
        progressDialog.setContentView(R.layout.ex_loader);
        StringRequest sr = new StringRequest(Request.Method.POST, constant.PATH +"forget_password.php",
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.e("HttpClient", "success! response: " + response.toString());
                        try {
                            JSONObject object = new JSONObject(response.toString());
                            String code = object.getString("status");
                            if (code.equalsIgnoreCase("1")) {
                                progressDialog.cancel();
                               String ver_code=object.getString("verifyToken");
                               String email_id=object.getString("email");
                               sharedPreferences=getApplicationContext().getSharedPreferences(PREFS_NAME,0);
                              SharedPreferences.Editor editor=sharedPreferences.edit();

                               editor.putString("email_ID",email_id);
                               editor.putString("code1",ver_code);
                               editor.commit();

                                Toast.makeText(forgotpassword.this, "verification code is sent on your email", Toast.LENGTH_LONG).show();
                                startActivity(new Intent(forgotpassword.this, OTP_activity.class));
                                finish();
                            }
                        } catch (JSONException e) {
                            Toast.makeText(forgotpassword.this,"Error",Toast.LENGTH_SHORT).show();
                            progressDialog.cancel();
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        progressDialog.cancel();
                        Toast.makeText(forgotpassword.this,"Error2",Toast.LENGTH_SHORT).show();
                        Log.e("HttpClient", "error: " + error.toString());
                    }
                }) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> object = new HashMap<String, String>();

                object.put("email", edt_emailid.getText().toString());

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
