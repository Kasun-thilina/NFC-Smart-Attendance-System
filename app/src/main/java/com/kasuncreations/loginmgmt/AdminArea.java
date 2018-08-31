package com.kasuncreations.loginmgmt;

import android.content.Intent;
import android.graphics.Color;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.NetworkError;
import com.android.volley.NoConnectionError;
import com.android.volley.ParseError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.ServerError;
import com.android.volley.TimeoutError;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import de.codecrafters.tableview.TableView;

public class AdminArea extends AppCompatActivity implements AdapterView.OnItemSelectedListener{

    //TableView<String[]> tableView = (TableView<String[]>) findViewById(R.id.tableView);
    private Spinner spinner;
    private static final String[]paths={"None Selected","DCSD 16.2","HDSE 17.1","HDBM 16.2"};
    final String url = "http://nfcappnibm.mywebcommunity.org/adminread.php";
    private String finalbatch="";


    List ALusername = new ArrayList<>();
    List ALtime = new ArrayList<>();
    List ALdate = new ArrayList<>();
    List Alsubject = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_area);

        final Button regButton=(Button)findViewById(R.id.btnReg);
        final Button logoutbtn=(Button)findViewById(R.id.btnlogoutadmin);

        final ActionBar actionBar=getSupportActionBar();
        actionBar.setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
        TextView title=new TextView(actionBar.getThemedContext());
        title.setText("Admin Control Panel");
        title.setTextSize(TypedValue.COMPLEX_UNIT_DIP,24);
        actionBar.setCustomView(title);

        spinner= findViewById(R.id.spinner);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(AdminArea.this,
                android.R.layout.simple_spinner_item,paths);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
        spinner.setOnItemSelectedListener((AdapterView.OnItemSelectedListener) this);


        regButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent regIntent=new Intent(AdminArea.this,RegisterActivity.class);
                AdminArea.this.startActivity(regIntent);
            }
        });
        logoutbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                SharedPreferences settings = getSharedPreferences("login", 0);
//                SharedPreferences.Editor editor = settings.edit();
//                editor.putBoolean("LoggedIn", false);
//                editor.commit();
                Intent logout = new Intent(AdminArea.this, MainActivity.class);
                AdminArea.this.startActivity(logout);
            }
        });
    }
    public void onItemSelected(AdapterView<?> parent, View v, int position, long id) {

//
//        //TableLayout table = (TableLayout) findViewById(R.id.tableLayout);
//
//        TextView tvUname;
//        TextView tvDate;
//        TextView tvTime;
//        TextView tvSub;
//        String username="" ;
//        String date="" ;
//        String time="" ;
//        String sub="" ;
//        String finalbatch="",temp="",temp2="";
//       // mTableLayout=(TableLayout)findViewById(R.id.tableLayout);
//        //tableView.setStretchAllColumns(true);
//        TextView textSpacer = null;
//
//        switch (position) {
//            case 1:
//                Toast.makeText(getApplicationContext(), "DCSD 16.2 Batch selected", Toast.LENGTH_SHORT).show();
//                finalbatch="DCSD";
//
//                tableView.removeAllViews();
//                for (int i=-1;i<ALusername.size();i++)
//                {
//                        if (i > -1) {
//                        username = (String) ALusername.get(i);
//                        temp=username.substring(2,6);
//                            if (finalbatch.equals(temp)) {
//                                date = (String) ALdate.get(i);
//                                time = (String) ALtime.get(i);
//                                sub = (String) Alsubject.get(i);
//                        }else {
//                                continue;
//                            }
//
//                        }
//                        else {
//                        textSpacer = new TextView(this);
//                        textSpacer.setText("");
//                         }
//                    tvUname=new TextView(this);
//                    tvUname.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.WRAP_CONTENT,TableRow.LayoutParams.WRAP_CONTENT));
//                    tvUname.setGravity(Gravity.LEFT);
//                    tvUname.setPadding(5, 15, 0, 15);
//                    if (i == -1) {
//                        tvUname.setText("Username");
//                        tvUname.setBackgroundColor(Color.parseColor("#0D85FE"));
//                        tvUname.setTextSize(15);
//                    } else {
//                        tvUname.setBackgroundColor(Color.parseColor("#B9DCFF"));
//                        tvUname.setText(username);
//                        tvUname.setTextColor(Color.parseColor("#000000"));
//                        tvUname.setTextSize(12);
//                    }
//
//                    tvDate=new TextView(this);
//                    tvDate.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.WRAP_CONTENT,TableRow.LayoutParams.WRAP_CONTENT));
//                    tvDate.setGravity(Gravity.LEFT);
//                    tvDate.setPadding(5, 15, 0, 15);
//                    if (i == -1) {
//                        tvDate.setText("Date");
//                        tvDate.setBackgroundColor(Color.parseColor("#0D85FE"));
//                        tvDate.setTextSize(15);
//                    } else {
//                        tvDate.setBackgroundColor(Color.parseColor("#CEE6FF"));
//                        tvDate.setTextColor(Color.parseColor("#000000"));
//                        tvDate.setText(date);
//                        tvDate.setTextSize(12);
//                    }
//
//
//                    tvTime=new TextView(this);
//                    tvTime.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.WRAP_CONTENT,TableRow.LayoutParams.WRAP_CONTENT));
//                    tvTime.setGravity(Gravity.LEFT);
//                    tvTime.setPadding(5, 15, 0, 15);
//                    if (i == -1) {
//                        tvTime.setText("Time");
//                        tvTime.setBackgroundColor(Color.parseColor("#0D85FE"));
//                        tvTime.setTextSize(15);
//                    } else {
//                        tvTime.setBackgroundColor(Color.parseColor("#CEE6FF"));
//                        tvTime.setText(time);
//                        tvTime.setTextColor(Color.parseColor("#000000"));
//                        tvTime.setTextSize(12);
//                    }
//
//                    tvSub=new TextView(this);
//                    tvSub.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.WRAP_CONTENT,TableRow.LayoutParams.WRAP_CONTENT));
//                    tvSub.setGravity(Gravity.LEFT);
//                    tvSub.setPadding(5, 15, 0, 15);
//                    if (i == -1) {
//                        tvSub.setText("Subject");
//                        tvSub.setBackgroundColor(Color.parseColor("#0D85FE"));
//                        tvSub.setTextSize(15);
//                    } else {
//                        tvSub.setBackgroundColor(Color.parseColor("#CEE6FF"));
//                        tvSub.setText(sub);
//                        tvSub.setTextColor(Color.parseColor("#000000"));
//                        tvSub.setTextSize(12);
//                    }
//
//                    // add table row
//                    final TableRow tr = new TableRow(this);
//                    tr.setId(i + 1);
//                    tr.setPadding(0,0,0,0);
//
//                    tr.addView(tvUname);
//                    tr.addView(tvDate);
//                    tr.addView(tvTime);
//                    tr.addView(tvSub);
//                    tr.addView(tr);
//
//                }
//
//                break;
//            case 2:
//                Toast.makeText(getApplicationContext(), "HDSE 17.1 batch selected", Toast.LENGTH_SHORT).show();
//                finalbatch="HDSE";
//                tableView.removeAllViews();
//                for (int i=-1;i<ALusername.size();i++)
//                {
//                    if (i > -1) {
//                        username = (String) ALusername.get(i);
//                        temp=username.substring(2,6);
//                        if (finalbatch.equals(temp)) {
//                            date = (String) ALdate.get(i);
//                            time = (String) ALtime.get(i);
//                            sub = (String) Alsubject.get(i);
//                        }else {
//                            continue;
//                        }
//
//                    }
//                    else {
//                        textSpacer = new TextView(this);
//                        textSpacer.setText("");
//                    }
//                    tvUname=new TextView(this);
//                    tvUname.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.WRAP_CONTENT,TableRow.LayoutParams.WRAP_CONTENT));
//                    tvUname.setGravity(Gravity.LEFT);
//                    tvUname.setPadding(5, 15, 0, 15);
//                    if (i == -1) {
//                        tvUname.setText("Username");
//                        tvUname.setBackgroundColor(Color.parseColor("#0D85FE"));
//                        tvUname.setTextSize(15);
//                    } else {
//                        tvUname.setBackgroundColor(Color.parseColor("#B9DCFF"));
//                        tvUname.setText(username);
//                        tvUname.setTextColor(Color.parseColor("#000000"));
//                        tvUname.setTextSize(12);
//                    }
//
//                    tvDate=new TextView(this);
//                    tvDate.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.WRAP_CONTENT,TableRow.LayoutParams.WRAP_CONTENT));
//                    tvDate.setGravity(Gravity.LEFT);
//                    tvDate.setPadding(5, 15, 0, 15);
//                    if (i == -1) {
//                        tvDate.setText("Date");
//                        tvDate.setBackgroundColor(Color.parseColor("#0D85FE"));
//                        tvDate.setTextSize(15);
//                    } else {
//                        tvDate.setBackgroundColor(Color.parseColor("#CEE6FF"));
//                        tvDate.setTextColor(Color.parseColor("#000000"));
//                        tvDate.setText(date);
//                        tvDate.setTextSize(12);
//                    }
//
//
//                    tvTime=new TextView(this);
//                    tvTime.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.WRAP_CONTENT,TableRow.LayoutParams.WRAP_CONTENT));
//                    tvTime.setGravity(Gravity.LEFT);
//                    tvTime.setPadding(5, 15, 0, 15);
//                    if (i == -1) {
//                        tvTime.setText("Time");
//                        tvTime.setBackgroundColor(Color.parseColor("#0D85FE"));
//                        tvTime.setTextSize(15);
//                    } else {
//                        tvTime.setBackgroundColor(Color.parseColor("#CEE6FF"));
//                        tvTime.setText(time);
//                        tvTime.setTextColor(Color.parseColor("#000000"));
//                        tvTime.setTextSize(12);
//                    }
//
//                    tvSub=new TextView(this);
//                    tvSub.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.WRAP_CONTENT,TableRow.LayoutParams.WRAP_CONTENT));
//                    tvSub.setGravity(Gravity.LEFT);
//                    tvSub.setPadding(5, 15, 0, 15);
//                    if (i == -1) {
//                        tvSub.setText("Subject");
//                        tvSub.setBackgroundColor(Color.parseColor("#0D85FE"));
//                        tvSub.setTextSize(15);
//                    } else {
//                        tvSub.setBackgroundColor(Color.parseColor("#CEE6FF"));
//                        tvSub.setText(sub);
//                        tvSub.setTextColor(Color.parseColor("#000000"));
//                        tvSub.setTextSize(12);
//                    }
//
//                    // add table row
//                    final TableRow tr = new TableRow(this);
//                    tr.setId(i + 1);
//                    tr.setPadding(0,0,0,0);
//
//                    tr.addView(tvUname);
//                    tr.addView(tvDate);
//                    tr.addView(tvTime);
//                    tr.addView(tvSub);
//                    tr.addView(tr);
//
//                }
//                break;
//            case 3:
//                Toast.makeText(getApplicationContext(), "HDBM 16.2 batch selected", Toast.LENGTH_SHORT).show();
//                  finalbatch="HDBM";
//                tableView.removeAllViews();
//                for (int i=-1;i<ALusername.size();i++)
//                {
//                    if (i > -1) {
//                        username = (String) ALusername.get(i);
//                        temp=username.substring(2,6);
//                        if (finalbatch.equals(temp)) {
//                            date = (String) ALdate.get(i);
//                            time = (String) ALtime.get(i);
//                            sub = (String) Alsubject.get(i);
//                        }else {
//                            continue;
//                        }
//
//                    }
//                    else {
//                        textSpacer = new TextView(this);
//                        textSpacer.setText("");
//                    }
//                    tvUname=new TextView(this);
//                    tvUname.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.WRAP_CONTENT,TableRow.LayoutParams.WRAP_CONTENT));
//                    tvUname.setGravity(Gravity.LEFT);
//                    tvUname.setPadding(5, 15, 0, 15);
//                    if (i == -1) {
//                        tvUname.setText("Username");
//                        tvUname.setBackgroundColor(Color.parseColor("#0D85FE"));
//                        tvUname.setTextSize(15);
//                    } else {
//                        tvUname.setBackgroundColor(Color.parseColor("#B9DCFF"));
//                        tvUname.setText(username);
//                        tvUname.setTextColor(Color.parseColor("#000000"));
//                        tvUname.setTextSize(12);
//                    }
//
//                    tvDate=new TextView(this);
//                    tvDate.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.WRAP_CONTENT,TableRow.LayoutParams.WRAP_CONTENT));
//                    tvDate.setGravity(Gravity.LEFT);
//                    tvDate.setPadding(5, 15, 0, 15);
//                    if (i == -1) {
//                        tvDate.setText("Date");
//                        tvDate.setBackgroundColor(Color.parseColor("#0D85FE"));
//                        tvDate.setTextSize(15);
//                    } else {
//                        tvDate.setBackgroundColor(Color.parseColor("#CEE6FF"));
//                        tvDate.setTextColor(Color.parseColor("#000000"));
//                        tvDate.setText(date);
//                        tvDate.setTextSize(12);
//                    }
//
//
//                    tvTime=new TextView(this);
//                    tvTime.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.WRAP_CONTENT,TableRow.LayoutParams.WRAP_CONTENT));
//                    tvTime.setGravity(Gravity.LEFT);
//                    tvTime.setPadding(5, 15, 0, 15);
//                    if (i == -1) {
//                        tvTime.setText("Time");
//                        tvTime.setBackgroundColor(Color.parseColor("#0D85FE"));
//                        tvTime.setTextSize(15);
//                    } else {
//                        tvTime.setBackgroundColor(Color.parseColor("#CEE6FF"));
//                        tvTime.setText(time);
//                        tvTime.setTextColor(Color.parseColor("#000000"));
//                        tvTime.setTextSize(12);
//                    }
//
//                    tvSub=new TextView(this);
//                    tvSub.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.WRAP_CONTENT,TableRow.LayoutParams.WRAP_CONTENT));
//                    tvSub.setGravity(Gravity.LEFT);
//                    tvSub.setPadding(5, 15, 0, 15);
//                    if (i == -1) {
//                        tvSub.setText("Subject");
//                        tvSub.setBackgroundColor(Color.parseColor("#0D85FE"));
//                        tvSub.setTextSize(15);
//                    } else {
//                        tvSub.setBackgroundColor(Color.parseColor("#CEE6FF"));
//                        tvSub.setText(sub);
//                        tvSub.setTextColor(Color.parseColor("#000000"));
//                        tvSub.setTextSize(12);
//                    }
//
//                    // add table row
//                    TableRow tr = new TableRow(this);
//
//                    tr.setLayoutParams(new TableRow.LayoutParams(
//                            ViewGroup.LayoutParams.WRAP_CONTENT,
//                            ViewGroup.LayoutParams.WRAP_CONTENT));
//                    tr.setId(i + 1);
//                    tr.setPadding(0,0,0,0);
//
//                    tr.addView(tvUname);
//                    tr.addView(tvDate);
//                    tr.addView(tvTime);
//                    tr.addView(tvSub);
//                    tr.addView(tr);
//
//                }
//                break;
//
//        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }
    private void loadProducts() {
        StringRequest stringRequest = new StringRequest(Request.Method.GET,url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            //converting the string to json array object
                            JSONArray array = new JSONArray(response);

                            //traversing through all the object
                            for (int i = 0; i < array.length(); i++) {

                                //getting product object from json array
                                JSONObject product = array.getJSONObject(i);

                                ALusername.add(product.getString("username"));
                                ALdate.add(product.getString("date"));
                                ALtime.add(product.getString("time"));
                                Alsubject.add(product.getString("subject"));

                            };


                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        if (error instanceof TimeoutError || error instanceof NoConnectionError) {
                            Toast.makeText(getApplicationContext(), "Communication Error!", Toast.LENGTH_SHORT).show();

                        } else if (error instanceof AuthFailureError) {
                            Toast.makeText(getApplicationContext(), "Authentication Error!", Toast.LENGTH_SHORT).show();
                        } else if (error instanceof ServerError) {
                            Toast.makeText(getApplicationContext(), "Server Side Error!", Toast.LENGTH_SHORT).show();
                        } else if (error instanceof NetworkError) {
                            Toast.makeText(getApplicationContext(), "Network Error!", Toast.LENGTH_SHORT).show();
                        } else if (error instanceof ParseError) {
                            Toast.makeText(getApplicationContext(), "Parse Error!", Toast.LENGTH_SHORT).show();
                        }
                    }
                }){};

        //adding our stringrequest to queue
        Volley.newRequestQueue(this).add(stringRequest);
    }





}

