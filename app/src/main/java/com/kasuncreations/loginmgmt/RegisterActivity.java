package com.kasuncreations.loginmgmt;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.InputFilter;
import android.util.Log;
import android.util.TypedValue;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class RegisterActivity extends AppCompatActivity {
    private TextInputLayout inputName;
    private TextInputLayout inputUname;
    private TextInputLayout inputPw;
    private TextInputLayout inputBatch;
    private ProgressDialog progress;
    EditText etRegName;
    EditText etRegUname;
    EditText etRegPw;
    EditText etRegCourse;
    Button btnReg;
    private static final String TAG = "MainActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        inputName=findViewById(R.id.input_name);
        inputUname=findViewById(R.id.input_uname);
        inputPw=findViewById(R.id.input_pw);
        inputBatch=findViewById(R.id.input_batch);
        etRegName=(EditText)findViewById(R.id.etRName);
        etRegUname=(EditText)findViewById(R.id.etRUname);
        etRegUname.setFilters(new InputFilter[]{new InputFilter.AllCaps(),new InputFilter.LengthFilter(12)});



        etRegPw=(EditText)findViewById(R.id.etRPW);
        etRegCourse=(EditText)findViewById(R.id.etRCourse);
        btnReg=(Button) findViewById(R.id.btnReg);

        final ActionBar actionBar=getSupportActionBar();

        actionBar.setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        TextView title=new TextView(actionBar.getThemedContext());

        title.setText("Register Student");
        title.setTextSize(TypedValue.COMPLEX_UNIT_DIP,24);

        actionBar.setCustomView(title);

        btnReg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                confirmInput();
            }
        });
    }
    private boolean validateName(){
        String name=inputName.getEditText().getText().toString().trim();
        if (name.isEmpty()){
            inputName.setError("Field Cannot Be Empty !");
            return false;
        }
        else {
            inputName.setError(null);
            //inputName.setErrorEnabled(false);
            return true;
        }

    }
    private boolean validateUsername(){
        String name=inputUname.getEditText().getText().toString().trim();
        if (name.isEmpty()){
            inputUname.setError("Field Cannot Be Empty !");
            return false;
        }
        else {
            inputUname.setError(null);
            //inputName.setErrorEnabled(false);
            return true;
        }

    }
    private boolean validatePw(){
        String name=inputPw.getEditText().getText().toString().trim();
        if (name.isEmpty()){
            inputPw.setError("Field Cannot Be Empty !");
            return false;
        }
        else {
            inputPw.setError(null);
            //inputName.setErrorEnabled(false);
            return true;
        }
    }

    private boolean validateBatch() {
        String name = inputBatch.getEditText().getText().toString().trim();
        if (name.isEmpty()) {
            inputBatch.setError("Field Cannot Be Empty !");
            return false;
        } else {
            inputBatch.setError(null);
            //inputName.setErrorEnabled(false);
            return true;
        }
    }
    public void confirmInput(){
        if(!validateName()|!validateUsername()|!validatePw()|!validateBatch()){
            return;
        }
        new MyAsyncTask().execute();
    }

    @Override
    public boolean onSupportNavigateUp(){
        //code it to launch an intent to the activity you want
        finish();
        return true;

    }
    class MyAsyncTask extends AsyncTask<String ,Integer,String > {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progress=new ProgressDialog(RegisterActivity.this);
            progress.setTitle("Registering New User");
            progress.setMessage("Saving...");
            progress.show();
        }

        @Override
        protected String doInBackground(String... strings) {

            String val="";
            final String name=etRegName.getText().toString();
            final String username=etRegUname.getText().toString();
            final String password=etRegPw.getText().toString();
            final String course=etRegCourse.getText().toString();
            Response.Listener<String> responseListener=new Response.Listener<String>()
            {

                @Override
                public void onResponse(String response)
                {
                    try
                    {
                        Log.i("tagconvertstr", "["+response+"]");
                        JSONObject jsonResponse=new JSONObject(response);
                        boolean success= jsonResponse.getBoolean("success");
                        boolean error= jsonResponse.getBoolean("error");

                        if (success)
                        {
                            if(error) {
                                AlertDialog.Builder builder =new AlertDialog.Builder(RegisterActivity.this);
                                builder.setMessage("Error!Username already exists in database")
                                        .setNegativeButton("Retry",null)
                                        .create()
                                        .show();
                            }
                            else {
                                Toast.makeText(getApplicationContext(), "New User " + name + " Registered Successfully",
                                        Toast.LENGTH_SHORT).show();
                            }
                        }

                    }catch (JSONException e)
                    {
                        Toast.makeText(getApplicationContext(), "gfgh" +e ,
                                Toast.LENGTH_SHORT).show();
                        Log.i(TAG, "onResponse: "+e);
                        AlertDialog.Builder builder =new AlertDialog.Builder(RegisterActivity.this);
                        builder.setMessage("Register Failed")
                                .setNegativeButton("Retry",null)
                                .create()
                                .show();
                        e.printStackTrace();
                    }
                }
            };


            RegisterRequest registerRequest=new RegisterRequest(name,username,password,course,responseListener);
            RequestQueue queue= Volley.newRequestQueue(RegisterActivity.this);
            queue.add(registerRequest);
            try {
                Thread.sleep(3500);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            return val;
        }

        @Override
        protected void onProgressUpdate(Integer... values) {
           // super.onProgressUpdate(values);
          //  int val=values[0];
            progress.setMessage("Saving... ");
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);

          //  String val2=s;
            progress.dismiss();
            Intent intent=new Intent(RegisterActivity.this,AdminUserArea.class);
            RegisterActivity.this.startActivity(intent);
           // txt.setText(val2);
        }
    }

}
