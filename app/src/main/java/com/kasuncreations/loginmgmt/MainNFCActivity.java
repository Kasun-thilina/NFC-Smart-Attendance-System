package com.kasuncreations.loginmgmt;

import android.app.PendingIntent;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.nfc.NfcAdapter;
import android.nfc.Tag;
import android.nfc.tech.Ndef;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.graphics.Color;
import android.os.AsyncTask;
import android.view.Gravity;
import android.util.TypedValue;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
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

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import layout.NFCReadFragment;

public class MainNFCActivity extends AppCompatActivity implements Listener {
    final String url = "http://nfcappnibm.mywebcommunity.org/timetable.php";
    public static final String TAG = MainActivity.class.getSimpleName();
    public String name = "";
    public String username = "";
    public String course = "";
    public String time = "";
    public String date = "";
    public String subject="Loading.." ;
    public String lechall="Loading.." ;
    private String filtedusername="";
    private String filterbatch1st="";
    private String filterbatch2nd="";
    private String finalbatch="";
    List ALusername = new ArrayList<>();
    List ALtime = new ArrayList<>();
    List ALdate = new ArrayList<>();
    List ALbatch = new ArrayList<>();


    private EditText mEtMessage;
    //private Button mBtWrite;
    private Button mBtRead;

    //private NFCWriteFragment mNfcWriteFragment;
    private NFCReadFragment mNfcReadFragment;

    private boolean isDialogDisplayed = false;
    private boolean isWrite = false;

    private NfcAdapter mNfcAdapter;





    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_nfc);
        final TextView tvCourse=(TextView)findViewById(R.id.tvCourse);
        final TextView tvWelcome=(TextView)findViewById(R.id.tvWelcome);
        final TextView tvTime=(TextView)findViewById(R.id.tvTime);
        final TextView tvDate=(TextView)findViewById(R.id.tvDate);

        final Button viewTimetable=(Button)findViewById(R.id.btnTT);
        final Button logoutbtn=(Button)findViewById(R.id.btnLogout);



        Intent intent= getIntent();
        name=intent.getStringExtra("name");
        username=intent.getStringExtra("username");
        course=intent.getStringExtra("course");
//        filtedusername=username.substring(2,6);
//        filterbatch1st=username.substring(6,8);
//        filterbatch2nd=username.substring(8,9);
        finalbatch=filtedusername+filterbatch1st+filterbatch2nd;

        initViews();
        initNFC();

        viewTimetable.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent regIntent=new Intent(MainNFCActivity.this,ViewTimetableActivity.class);
                regIntent.putExtra("username",username);
                MainNFCActivity.this.startActivity(regIntent);
            }
        });
        logoutbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                SharedPreferences settings = getSharedPreferences("loginuser", 0);
//                SharedPreferences.Editor editor = settings.edit();
//                editor.putBoolean("LoggedInuser", false);
//                editor.commit();
                Intent logout=new Intent(MainNFCActivity.this,MainActivity.class);
                MainNFCActivity.this.startActivity(logout);
            }
        });




        /*top bar name */
        final ActionBar actionBar=getSupportActionBar();
        actionBar.setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
        TextView title=new TextView(actionBar.getThemedContext());
        title.setText("User Area");
        actionBar.setCustomView(title);
        //actionBar.setDisplayHomeAsUpEnabled(true);

        tvWelcome.setText("Welcome "+name+"!");
        title.setTextSize(TypedValue.COMPLEX_UNIT_DIP,18);
        tvCourse.setText(course);

        //setting date and time

        Thread t = new Thread() {

            @Override
            public void run() {
                try {
                    while (!isInterrupted()) {
                        Thread.sleep(1000);
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                Date currentTime = Calendar.getInstance().getTime();
                                Calendar calendar = Calendar.getInstance();
                                SimpleDateFormat mdformat = new SimpleDateFormat("HH:mm:ss");
                                time=mdformat.format(calendar.getTime());
                                tvTime.setText("" +time);
                            }
                        });
                    }
                } catch (InterruptedException e) {
                }
            }
        };

        t.start();

        Date currentDate = Calendar.getInstance().getTime();
        Calendar calendar = Calendar.getInstance();
        SimpleDateFormat mdformat = new SimpleDateFormat("yyyy-MM-DD");
        date= mdformat.format(calendar.getTime());
        tvDate.setText("" +date);

        startLoadData();
        loadProducts();


    }

    public void startLoadData() {

//        mProgressBar.setCancelable(false);
//        mProgressBar.setMessage("Fetching Invoices..");
//        mProgressBar.setProgressStyle(ProgressDialog.STYLE_SPINNER);
//        mProgressBar.show();
        new LoadDataTask().execute(0);
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
//            mProgressBar.hide();
            loadData();
        }
        @Override
        protected void onPreExecute() {
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

    public void loadData(){
        final TextView tvLect=(TextView)findViewById(R.id.tvLect);
        final TextView tvLhall=(TextView)findViewById(R.id.tvLhall);

        for (int i=1;i<ALusername.size();i++)
        {
            if (finalbatch.equals((String) ALbatch.get(i))) {
                String temp= (String) ALusername.get(i);
                if (temp.equals(date)) {
                    subject = (String) ALdate.get(i);
                    lechall = (String) ALtime.get(i);
                    tvLhall.setText(lechall);
                    tvLect.setText(subject);
                    Toast.makeText(getApplicationContext(), ""+date, Toast.LENGTH_LONG).show();
                }

            }
            else
            {
                continue;
            }
        }

    }




    public String[] getMyData() {
        return new String[]{username,date,time};


    }
    private void initViews() {

       // mEtMessage = (EditText) findViewById(R.id.et_message);
        //mBtWrite = (Button) findViewById(R.id.btn_write);
        mBtRead = (Button) findViewById(R.id.btn_read);

        //mBtWrite.setOnClickListener(view -> showWriteFragment());
        mBtRead.setOnClickListener(view -> showReadFragment());
    }

    private void initNFC(){

        mNfcAdapter = NfcAdapter.getDefaultAdapter(this);
    }


   /* private void showWriteFragment() {

        isWrite = true;

        mNfcWriteFragment = (NFCWriteFragment) getSupportFragmentManager().findFragmentByTag(NFCWriteFragment.TAG);

        if (mNfcWriteFragment == null) {

            mNfcWriteFragment = NFCWriteFragment.newInstance();
        }
        mNfcWriteFragment.show(getSupportFragmentManager(),NFCWriteFragment.TAG);

    }*/

    private void showReadFragment() {

        mNfcReadFragment = (NFCReadFragment) getSupportFragmentManager().findFragmentByTag(NFCReadFragment.TAG);

        if (mNfcReadFragment == null) {

            mNfcReadFragment = NFCReadFragment.newInstance();
        }
        mNfcReadFragment.show(getSupportFragmentManager(),NFCReadFragment.TAG);

    }

    @Override
    public void onDialogDisplayed() {

        isDialogDisplayed = true;
    }

    @Override
    public void onDialogDismissed() {

        isDialogDisplayed = false;
        isWrite = false;
    }

    @Override
    protected void onResume() {
        super.onResume();
        IntentFilter tagDetected = new IntentFilter(NfcAdapter.ACTION_TAG_DISCOVERED);
        IntentFilter ndefDetected = new IntentFilter(NfcAdapter.ACTION_NDEF_DISCOVERED);
        IntentFilter techDetected = new IntentFilter(NfcAdapter.ACTION_TECH_DISCOVERED);
        IntentFilter[] nfcIntentFilter = new IntentFilter[]{techDetected,tagDetected,ndefDetected};

        PendingIntent pendingIntent = PendingIntent.getActivity(
                this, 0, new Intent(this, getClass()).addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP), 0);
        if(mNfcAdapter!= null)
            mNfcAdapter.enableForegroundDispatch(this, pendingIntent, nfcIntentFilter, null);

    }

    @Override
    protected void onPause() {
        super.onPause();
        if(mNfcAdapter!= null)
            mNfcAdapter.disableForegroundDispatch(this);
    }

    @Override
    protected void onNewIntent(Intent intent) {
        Tag tag = intent.getParcelableExtra(NfcAdapter.EXTRA_TAG);

        //Log.d(TAG, "onNewIntent: "+intent.getAction());

        if(tag != null) {
            Toast.makeText(this, getString(R.string.message_tag_detected), Toast.LENGTH_SHORT).show();
            Ndef ndef = Ndef.get(tag);

            if (isDialogDisplayed) {

                if (isWrite) {

                   /* String messageToWrite = mEtMessage.getText().toString();
                    mNfcWriteFragment = (NFCWriteFragment) getSupportFragmentManager().findFragmentByTag(NFCWriteFragment.TAG);
                    mNfcWriteFragment.onNfcDetected(ndef,messageToWrite);*/

                } else {

                    mNfcReadFragment = (NFCReadFragment)getSupportFragmentManager().findFragmentByTag(NFCReadFragment.TAG);
                    mNfcReadFragment.onNfcDetected(ndef);
                }
            }
        }
    }




}
