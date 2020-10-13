package com.StackBuffers.baqala;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.core.content.res.ResourcesCompat;

import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.shuhart.stepview.StepView;

import java.util.ArrayList;

public class StepsActivity extends AppCompatActivity {


    RelativeLayout relativeLayout1,relativeLayout2,relativeLayout3,relativeLayout4,relativeLayout5;
    LinearLayout linearLayout1,linearLayout2,linearLayout3;
    Button btnnext4,btnnext5;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_steps);


        relativeLayout1 = findViewById(R.id.rl1);
        relativeLayout2 = findViewById(R.id.rl2);
        relativeLayout3 = findViewById(R.id.rl3);
        relativeLayout4 = findViewById(R.id.rl4);
        relativeLayout5 = findViewById(R.id.rl5);

        linearLayout1 = findViewById(R.id.next_s1btn);
        linearLayout2 = findViewById(R.id.next_s2btn);
        linearLayout3 = findViewById(R.id.next_s3btn);
        btnnext4= findViewById(R.id.next_s4btn);
       btnnext5 = findViewById(R.id.btn_next5);




        linearLayout1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                relativeLayout1.setVisibility(view.GONE);
                relativeLayout2.setVisibility(view.VISIBLE);
            }
        });




        linearLayout2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                relativeLayout2.setVisibility(view.GONE);
                relativeLayout3.setVisibility(view.VISIBLE);
            }
        });




        linearLayout3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                relativeLayout3.setVisibility(view.GONE);
                relativeLayout4.setVisibility(view.VISIBLE);
            }
        });


        btnnext4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                relativeLayout4.setVisibility(view.GONE);
                relativeLayout5.setVisibility(view.VISIBLE);
            }
        });




}}
