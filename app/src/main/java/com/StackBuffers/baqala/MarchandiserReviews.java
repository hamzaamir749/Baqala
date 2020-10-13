package com.StackBuffers.baqala;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;

public class MarchandiserReviews extends AppCompatActivity {

    Button btnview;
    Context context;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_marchandiser_reviews);

        btnview = findViewById(R.id.btn_reviewsmarchandisers);
        context=this;


        btnview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {




                final AlertDialog dialogBuilder = new AlertDialog.Builder(context).create();
                // LayoutInflater inflater = context.getApplicationContext().getLayoutInflater();
                LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                final View dialogView = inflater.inflate(R.layout.marchandiser_reviews_layout, null);
                dialogBuilder.setView(dialogView);
                dialogBuilder.setCanceledOnTouchOutside(true);
                /*final RatingBar ratingBar = dialogView.findViewById(R.id.review_rate);
                final EditText descrition = dialogView.findViewById(R.id.review_Decription);
                Button submit = dialogView.findViewById(R.id.review_submit);*/


                dialogBuilder.show();


            }
        });


    }
}
