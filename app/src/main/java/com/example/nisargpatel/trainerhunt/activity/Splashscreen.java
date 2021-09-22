package com.example.nisargpatel.trainerhunt.activity;


import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;


public class Splashscreen extends AppCompatActivity {
    private Handler mWaitHandler = new Handler();

//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_splashscreen);
//
//        mWaitHandler.postDelayed(new Runnable() {
//
//            @Override
//            public void run() {
//
//                //The following code will execute after the 5 seconds.
//
//                try {
//
//                    //Go to next page i.e, start the next activity.
//                    Intent intent = new Intent(getApplicationContext(), login.class);
//                    startActivity(intent);
//
//                    //Let's Finish Splash Activity since we don't want to show this when user press back button.
//                    finish();
//                } catch (Exception ignored) {
//                    ignored.printStackTrace();
//                }
//            }
//        }, 5000);  // Give a 5 seconds delay.
//    }
//
//    @Override
//    public void onDestroy() {
//        super.onDestroy();
//
//        //Remove all the callbacks otherwise navigation will execute even after activity is killed or closed.
//        mWaitHandler.removeCallbacksAndMessages(null);
//    }
//}

 @Override
protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_splashscreen);
    new Handler().postDelayed(new Runnable() {
        @Override
        public void run() {
            try {
                startActivity(new Intent(Splashscreen.this,login.class));
                finish();
            } catch (Exception e) {
                e.printStackTrace();
            }


        }
    },3000);
}
}

