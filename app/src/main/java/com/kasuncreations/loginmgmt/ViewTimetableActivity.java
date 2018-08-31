package com.kasuncreations.loginmgmt;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TableLayout;
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

public class ViewTimetableActivity extends AppCompatActivity {
    private Spinner spinner;

    final String url = "http://nfcappnibm.mywebcommunity.org/timetable.php";
    private TableLayout mTableLayout;
    private int i=0;
    private String filtedusername="";
    private String filterbatch1st="";
    private String filterbatch2nd="";
    private String finalbatch="";

    List ALusername = new ArrayList<>();
    List ALtime = new ArrayList<>();
    List ALdate = new ArrayList<>();
    List ALbatch = new ArrayList<>();

    
    ProgressBar mProgressbar;
    int progressBarValue = 0;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_timetable);

        /*getting the username and filtering the batch*/
        Intent intent= getIntent();
        String username=intent.getStringExtra("username");
        filtedusername=username.substring(2,6);
        filterbatch1st=username.substring(6,8);
        filterbatch2nd=username.substring(8,9);
        //Toast.makeText(getApplicationContext(), "Data "+filtedusername, Toast.LENGTH_LONG).show();

            final ActionBar actionBar=getSupportActionBar();
            actionBar.setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
            TextView title=new TextView(actionBar.getThemedContext());
            title.setText(filtedusername+" "+filterbatch1st+"."+filterbatch2nd+ " Time Table");
            finalbatch=filtedusername+filterbatch1st+filterbatch2nd;
            title.setTextSize(TypedValue.COMPLEX_UNIT_DIP,24);
            actionBar.setCustomView(title);


        getSupportActionBar().setDisplayHomeAsUpEnabled(true);






        startLoadData();
        loadProducts();


    }
    @Override
    public boolean onSupportNavigateUp(){
        //code it to launch an intent to the activity you want
        finish();
        return true;
    }
    public void startLoadData() {

//        mProgressBar.setCancelable(false);
//        mProgressBar.setMessage("Fetching Data..");
//        mProgressBar.setProgressStyle(ProgressDialog.STYLE_SPINNER);
//        mProgressBar.show();
        while(progressBarValue < 100) {
            progressBarValue++;
        }
        new ViewTimetableActivity.LoadDataTask().execute(0);
    }
    class LoadDataTask extends AsyncTask<Integer, Integer, String> {
        @Override
        protected String doInBackground(Integer... params) {
            try {
                Thread.sleep(2000);

            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            return "Task Completed.";
        }
        @Override
        protected void onPostExecute(String result) {
          //mProgressBar.hide();
            loadTable();
        }
        @Override
        protected void onPreExecute() {
//            mProgressBar.setCancelable(false);
//        mProgressBar.setMessage("Fetching Data..");
//        mProgressBar.setProgressStyle(ProgressDialog.STYLE_SPINNER);
//        mProgressBar.show();
        }
        @Override
        protected void onProgressUpdate(Integer... values) {
        }
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

                                ALusername.add(product.getString("date"));
                                ALdate.add(product.getString("subject"));
                                ALtime.add(product.getString("lecture_hall"));
                                ALbatch.add(product.getString("batch"));

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


    public void loadTable(){
        TableLayout table = (TableLayout) findViewById(R.id.timetableLayout);

        TextView tvUname;
        TextView tvDate;
        TextView tvTime;
        TextView tvSub;
        String date="" ;
        String subject="" ;
        String lechall="" ;
        String batch="" ;
        mTableLayout=(TableLayout)findViewById(R.id.timetableLayout);
        mTableLayout.setStretchAllColumns(true);
        TextView textSpacer = null;
        for (int i=-1;i<ALusername.size();i++)
        {
            if (i > -1) {
                if (finalbatch.equals((String) ALbatch.get(i))) {
                    date = (String) ALusername.get(i);
                    subject = (String) ALdate.get(i);
                    lechall = (String) ALtime.get(i);
                }
                else
                {
                    continue;
                }
            }else {
                textSpacer = new TextView(this);
                textSpacer.setText("");
            }

            tvUname=new TextView(this);
            tvUname.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.WRAP_CONTENT,TableRow.LayoutParams.WRAP_CONTENT));
            tvUname.setGravity(Gravity.LEFT);
            tvUname.setPadding(5, 15, 0, 15);

            if (i == -1) {
                tvUname.setText("Date");
                tvUname.setBackgroundColor(Color.parseColor("#0D85FE"));
                tvUname.setTextSize(15);
            } else {
                tvUname.setBackgroundColor(Color.parseColor("#B9DCFF"));
                tvUname.setText(date);
                tvUname.setTextColor(Color.parseColor("#000000"));
                tvUname.setTextSize(12);
            }

            tvDate=new TextView(this);
            tvDate.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.WRAP_CONTENT,TableRow.LayoutParams.WRAP_CONTENT));
            tvDate.setGravity(Gravity.LEFT);
            tvDate.setPadding(5, 15, 0, 15);
            if (i == -1) {
                tvDate.setText("Subject");

                tvDate.setBackgroundColor(Color.parseColor("#0D85FE"));
                tvDate.setTextSize(15);
            } else {
                tvDate.setBackgroundColor(Color.parseColor("#CEE6FF"));
                tvDate.setTextColor(Color.parseColor("#000000"));
                tvDate.setText(subject);
                tvDate.setTextSize(12);
            }


            tvTime=new TextView(this);
            tvTime.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.WRAP_CONTENT,TableRow.LayoutParams.WRAP_CONTENT));
            tvTime.setGravity(Gravity.LEFT);
            tvTime.setPadding(5, 15, 0, 15);
            if (i == -1) {
                tvTime.setText("Lecture Hall");
                tvTime.setBackgroundColor(Color.parseColor("#0D85FE"));
                tvTime.setTextSize(15);
            } else {
                tvTime.setBackgroundColor(Color.parseColor("#B9DCFF"));
                tvTime.setText(lechall);
                tvTime.setTextColor(Color.parseColor("#000000"));
                tvTime.setTextSize(12);
            }



            // add table row
            final TableRow tr = new TableRow(this);
            tr.setId(i + 1);
            tr.setPadding(0,0,0,0);

            tr.addView(tvUname);
            tr.addView(tvDate);
            tr.addView(tvTime);

            table.addView(tr);

        }
    }



}