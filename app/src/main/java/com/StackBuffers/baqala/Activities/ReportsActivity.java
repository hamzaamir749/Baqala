package com.StackBuffers.baqala.Activities;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.StackBuffers.baqala.CompetitorsDataReport;
import com.StackBuffers.baqala.MarchandiserReviews;
import com.StackBuffers.baqala.NearExpiry;
import com.StackBuffers.baqala.OutofStock;
import com.StackBuffers.baqala.Overstock;
import com.StackBuffers.baqala.R;
import com.StackBuffers.baqala.StoreKeeperReviews;
import com.StackBuffers.baqala.TaskStatusActivity;
import com.StackBuffers.baqala.TimeReport;

public class ReportsActivity extends AppCompatActivity {

    Context context;
    Toolbar mToolBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reports);
        context = this;
        mToolBar = findViewById(R.id.report_toolbar);

        setSupportActionBar(mToolBar);
        getSupportActionBar().setTitle("Reports");
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.LOLLIPOP) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
            getSupportActionBar().setHomeButtonEnabled(true);
        } else {
            getActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
            getActionBar().setHomeButtonEnabled(true);
        }


        LinearLayout timereport = findViewById(R.id.time_report);
        LinearLayout taskstatus = findViewById(R.id.task_status);
        LinearLayout outofstock = findViewById(R.id.out_of_stock);
        LinearLayout overstock = findViewById(R.id.over_stock);
        LinearLayout nearexpiry = findViewById(R.id.near_expiry);
        LinearLayout competitorsdata = findViewById(R.id.competitors_data_report);
        LinearLayout storekeeperreview = findViewById(R.id.store_keeper_review);
        LinearLayout marchandiserreview = findViewById(R.id.marchandiser_reviews);


        timereport.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(context.getApplicationContext(), TimeReport.class);
                startActivity(i);

            }
        });


        taskstatus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(context.getApplicationContext(), TaskStatusActivity.class);
                startActivity(i);

            }
        });


        outofstock.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(context.getApplicationContext(), OutofStock.class);
                startActivity(i);

            }
        });


        overstock.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(context.getApplicationContext(), Overstock.class);
                startActivity(i);

            }
        });


        nearexpiry.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(context.getApplicationContext(), NearExpiry.class);
                startActivity(i);

            }
        });


        competitorsdata.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(context.getApplicationContext(), CompetitorsDataReport.class);
                startActivity(i);

            }
        });


        storekeeperreview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(context.getApplicationContext(), StoreKeeperReviews.class);
                startActivity(i);

            }
        });


        marchandiserreview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(context.getApplicationContext(), MarchandiserReviews.class);
                startActivity(i);

            }
        });

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            onBackPressed();
        }
        return super.onOptionsItemSelected(item);
    }


}
