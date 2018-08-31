package com.kasuncreations.loginmgmt;

import android.app.ActionBar;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.InputFilter;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;


import org.json.JSONException;
import org.json.JSONObject;

public class MainActivity extends AppCompatActivity {



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_main);


        final EditText etUname=(EditText)findViewById(R.id.etUname);
        etUname.setFilters(new InputFilter[]{new InputFilter.AllCaps()});
        final EditText etPw=(EditText)findViewById(R.id.etPW);
        final Button btnLogin=(Button)findViewById(R.id.btnLogin);
        etUname.setText("");
        etPw.setText("");
        Intent intent=new Intent(MainActivity.this,MainNFCActivity.class);
        //Intent intent1=new Intent(MainActivity.this,AdminUserArea.class);
//        SharedPreferences settings = getSharedPreferences("login", 0);
//        boolean isLoggedIn = settings.getBoolean("LoggedIn", false);
//
//        SharedPreferences userlogin = getSharedPreferences("loginuser", 0);
//        boolean isLoggedInuser = userlogin.getBoolean("LoggedInuser", false);
//        if(isLoggedIn )
//        {
//            //Go directly to Homescreen.
//            MainActivity.this.startActivity(intent1);
//        }
//        else if(isLoggedInuser )
//        {
//            //Go directly to Homescreen.
//            MainActivity.this.startActivity(intent);
//        }









        /*final TextView reglink=(TextView)findViewById(R.id.tvReg);

        reglink.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent regIntent=new Intent(MainActivity.this,RegisterActivity.class);
                MainActivity.this.startActivity(regIntent);
            }
        });*/

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final String username=etUname.getText().toString();
                final String password=etPw.getText().toString();
                Response.Listener<String> responseListener=new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject jsonResponse=new JSONObject(response);
                            boolean success=jsonResponse.getBoolean("success");
                            if(success)
                            {
                                String name=jsonResponse.getString("name");
                                String course=jsonResponse.getString("course");

                                Intent intent=new Intent(MainActivity.this,MainNFCActivity.class);
                                Intent intent1=new Intent(MainActivity.this,AdminUserArea.class);

                                intent.putExtra("name",name);
                                intent.putExtra("username",username);
                                intent.putExtra("course",course);

                                if (username.equals("ADMIN"))
                                {
                                    MainActivity.this.startActivity(intent1);
                                    //SharedPreferences settings = getSharedPreferences("login", 0);
                                    //SharedPreferences.Editor editor = settings.edit();
                                    //editor.putBoolean("LoggedIn", true);
                                    //editor.commit();

                                }
                                else
                                {
                                    MainActivity.this.startActivity(intent);
                                    SharedPreferences settings = getSharedPreferences("loginuser", 0);
                                    SharedPreferences.Editor editor = settings.edit();
                                    editor.putBoolean("LoggedInuser", true);
                                    editor.commit();

                                }
                            }else
                            {
                                AlertDialog.Builder builder =new AlertDialog.Builder(MainActivity.this);
                                builder.setMessage("Login Failed")
                                        .setNegativeButton("Retry",null)
                                        .create()
                                        .show();
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                };


                LoginRequest loginRequest=new LoginRequest(username,password,responseListener);
                RequestQueue queue= Volley.newRequestQueue(MainActivity.this);
                queue.add(loginRequest);
            }
        });
    }
}
