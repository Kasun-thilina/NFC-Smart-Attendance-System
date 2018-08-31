package com.kasuncreations.loginmgmt;

import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.TypedValue;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class AdminNFCActivity extends AppCompatActivity {

    public static final String TAG = MainActivity.class.getSimpleName();


    private EditText mEtMessage;
    //private Button mBtWrite;
    private Button mBtRead;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_nfc);

        final ActionBar actionBar=getSupportActionBar();

        actionBar.setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);

        TextView title=new TextView(actionBar.getThemedContext());

        title.setText("Mark Attendance");
        title.setTextSize(TypedValue.COMPLEX_UNIT_DIP,24);

        actionBar.setCustomView(title);

    }
}
