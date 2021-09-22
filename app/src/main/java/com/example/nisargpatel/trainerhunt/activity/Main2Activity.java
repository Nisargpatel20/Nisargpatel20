package com.example.nisargpatel.trainerhunt.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;

import com.example.nisargpatel.trainerhunt.Trainer_fragment.Trainer_HomeFragment;
import com.example.nisargpatel.trainerhunt.Trainer_fragment.Trainer_ProfileFragment;
import com.example.nisargpatel.trainerhunt.Trainer_fragment.Trainer_gallleryFragment;

//implement the interface OnNavigationItemSelectedListener in your activity class
public class Main2Activity extends AppCompatActivity implements BottomNavigationView.OnNavigationItemSelectedListener {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        //loading the default fragment
        loadFragment(new Trainer_HomeFragment());

        //getting bottom navigation view and attaching the listener
        BottomNavigationView navigation = findViewById(R.id.navigation2);
        navigation.setOnNavigationItemSelectedListener(Main2Activity.this);
    }

    Fragment currentFragment = null;
    FragmentTransaction ft;

    public boolean onNavigationItemSelected(@NonNull MenuItem item) {

        Fragment fragment = null;

        switch (item.getItemId()) {
            case R.id.navigation2_home:
                fragment = new Trainer_HomeFragment();
                break;


            case R.id.navigation2_profile:
                fragment = new Trainer_ProfileFragment();
                //Intent intent=new Intent(this,Trainer_form.class);
               // startActivity(intent);
                break;
        }

        return loadFragment(fragment);
    }

    private boolean loadFragment(Fragment fragment) {
        //switching fragment
        if (fragment != null) {
            getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.fragment_container2, fragment)
                    .commit();
            return true;
        }
        return false;
    }
}
