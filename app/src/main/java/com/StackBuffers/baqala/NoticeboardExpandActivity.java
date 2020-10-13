package com.StackBuffers.baqala;

import android.os.Bundle;
import android.view.MenuItem;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

public class NoticeboardExpandActivity extends AppCompatActivity {

    Toolbar mToolBar;
    String title, description, datetime;
    TextView nTitle, nDescription, nTime;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_noticeboard_expand);

        nTitle = findViewById(R.id.notice_title);
        nDescription = findViewById(R.id.notice_desc);
        nTime = findViewById(R.id.notic_time);

        title = getIntent().getExtras().get("title").toString();
        description = getIntent().getExtras().get("description").toString();
        datetime = getIntent().getExtras().get("datetime").toString();


        mToolBar = findViewById(R.id.noticboardexpand_Toolbar);
        setSupportActionBar(mToolBar);
        getSupportActionBar().setTitle("Notice Board");
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.LOLLIPOP) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
            getSupportActionBar().setHomeButtonEnabled(true);
        } else {
            getActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
            getActionBar().setHomeButtonEnabled(true);
        }


        nTitle.setText(title);
        nDescription.setText(description);
        nTime.setText(datetime);


    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            onBackPressed();
        }
        return super.onOptionsItemSelected(item);
    }


}
