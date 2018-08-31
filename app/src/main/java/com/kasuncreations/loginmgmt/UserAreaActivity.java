package com.kasuncreations.loginmgmt;

import android.content.Intent;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.TypedValue;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import layout.NFCReadFragment;

public class UserAreaActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_area);

        final TextView tvWelcome=(TextView)findViewById(R.id.tvWelcm);
        final TextView tvCourse=(TextView)findViewById(R.id.tvCourse);
        final TextView tvUname=(TextView)findViewById(R.id.tvUname);
        final Button btnNFC=(Button)findViewById(R.id.btnTogglenfc);
        final ActionBar actionBar=getSupportActionBar();

        actionBar.setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);

        TextView title=new TextView(actionBar.getThemedContext());





        Intent intent= getIntent();
        String name=intent.getStringExtra("name");
        String username=intent.getStringExtra("username");
        String course=intent.getStringExtra("course");

        title.setText("Welcome "+name);
        title.setTextSize(TypedValue.COMPLEX_UNIT_DIP,18);

        actionBar.setCustomView(title);
        String message="Welcome "+name;
        tvWelcome.setText(message);
        tvUname.setText(username);
        tvCourse.setText(course);

        btnNFC.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent regIntent=new Intent(UserAreaActivity.this, MainNFCActivity.class);

                intent.putExtra("username",username);

                UserAreaActivity.this.startActivity(regIntent);
            }
        });

    }

}
