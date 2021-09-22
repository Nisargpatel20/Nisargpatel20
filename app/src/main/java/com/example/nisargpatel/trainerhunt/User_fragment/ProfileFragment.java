package com.example.nisargpatel.trainerhunt.User_fragment;


import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.nisargpatel.trainerhunt.activity.R;
import com.example.nisargpatel.trainerhunt.activity.change_password;

import static com.example.nisargpatel.trainerhunt.activity.login.PREFS_NAME;

/**
 * A simple {@link Fragment} subclass.
 */
public class ProfileFragment extends Fragment {

    SharedPreferences sharedPreferences;
    int UserId;
    String fullname,mobNo,emailId;
    TextView txtFullname,txtEmailId,txtMobNo,txtChangePass;
    Button logout;

    public ProfileFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_profile, container, false);
        txtChangePass=(TextView) view.findViewById(R.id.txt_changepassword);
        txtFullname =(TextView)view.findViewById(R.id.txt_pr_name);
        txtEmailId = (TextView)view.findViewById(R.id.txt_pr_email);
        txtMobNo = (TextView)view.findViewById(R.id.txt_pr_phone);
        logout = (Button) view.findViewById(R.id.btn_logout);
        sharedPreferences = getActivity().getSharedPreferences(PREFS_NAME, 0);
        UserId = sharedPreferences.getInt("userId", 0);
        fullname = sharedPreferences.getString("fullname",null);
        mobNo = sharedPreferences.getString("mobNo",null);
        emailId = sharedPreferences.getString("emailId",null);
        //Toast.makeText(getActivity(), ""+UserId, Toast.LENGTH_SHORT).show();
        txtFullname.setText(""+fullname);
        txtMobNo.setText(""+mobNo);
        txtEmailId.setText(""+emailId);

        txtChangePass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getActivity(),change_password.class));
            }
        });


        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SharedPreferences settings = getActivity().getSharedPreferences(PREFS_NAME, 0);
                SharedPreferences.Editor editor = settings.edit();
                editor.remove("logged");
                editor.clear();
                editor.commit();
                getActivity().finish();
                System.exit(0);

            }
        });
        return view;
    }

}
