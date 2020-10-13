package com.StackBuffers.baqala;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

public class Overstock extends AppCompatActivity {


    Button overstockbtn;
    Context context;
    Toolbar mToolBar;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_overstock);


        overstockbtn=findViewById(R.id.btn_overstock_views);
        context = this;



        mToolBar = findViewById(R.id.overstock_Toolbar);
        setSupportActionBar(mToolBar);
        getSupportActionBar().setTitle("Over Stock");
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.LOLLIPOP) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
            getSupportActionBar().setHomeButtonEnabled(true);
        } else {
            getActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
            getActionBar().setHomeButtonEnabled(true);
        }






        overstockbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                final AlertDialog dialogBuilder = new AlertDialog.Builder(context).create();
                // LayoutInflater inflater = context.getApplicationContext().getLayoutInflater();
                LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                final View dialogView = inflater.inflate(R.layout.overstock_expand, null);
                dialogBuilder.setView(dialogView);
                dialogBuilder.setCanceledOnTouchOutside(true);
                /*final RatingBar ratingBar = dialogView.findViewById(R.id.review_rate);
                final EditText descrition = dialogView.findViewById(R.id.review_Decription);
                Button submit = dialogView.findViewById(R.id.review_submit);*/


                dialogBuilder.show();


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
