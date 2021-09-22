package com.example.nisargpatel.trainerhunt.activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.nisargpatel.trainerhunt.activity.R;

import static com.example.nisargpatel.trainerhunt.activity.login.PREFS_NAME;

public class OTP_activity extends AppCompatActivity {
    EditText edtOTP;
    Button btConform;
    SharedPreferences sharedPreferences;
    String token;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_otp_activity);

        edtOTP=(EditText) findViewById(R.id.edt_otp);
        btConform=(Button) findViewById(R.id.bt_conform);
        sharedPreferences = this.getSharedPreferences(PREFS_NAME, 0);
         token= sharedPreferences.getString("code1",null);
      btConform.setOnClickListener(new View.OnClickListener() {
          @Override
          public void onClick(View v) {
              if (edtOTP.getText().toString().trim().equals(token))
              {
                  Intent intent=new Intent(OTP_activity.this,Repassword_forget.class);
                  startActivity(intent);
              }
              else
              {
                  Toast.makeText(OTP_activity.this, "verification code is wrong plz try again", Toast.LENGTH_SHORT).show();
              }
          }
      });

    }
}
