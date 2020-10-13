package com.StackBuffers.baqala;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.StackBuffers.baqala.Adapters.Taskdetail_productsAdapter;
import com.StackBuffers.baqala.HelperClasses.Session;
import com.StackBuffers.baqala.HelperClasses.SharedHelper;
import com.StackBuffers.baqala.HelperClasses.UserSessionManager;
import com.StackBuffers.baqala.ModelClasses.Taskdetail_productsModel;
import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class TaskDetailActivity extends AppCompatActivity {
    Context context;

    ImageView adbimg1, adbimg2, adbimg3, adbimg4, adbimg5, adbimg6, adbimg7, adbimg8, adbimg9, adbimg10, adbimg11, adafimg1, adafimg2, adafimg3, adafimg4, adafimg5, adafimg6, adafimg7, adafimg8, adafimg9, adafimg10, adafimg11, comp_img1, comp_img2, comp_img3;

    RatingBar ratingBar;
    TextView clientcomments, compititorsdata;
    Toolbar mToolBar;


    private RecyclerView recyclerView;
    private LinearLayoutManager linearLayoutManager;
    Taskdetail_productsAdapter adapter;
    List<Taskdetail_productsModel> list;
    UserSessionManager userSessionManager;
    Session session;
    int id;
    ProgressDialog progressDialog;
    SharedHelper sharedHelper;
    String routeid;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_task_detail);
        routeid = getIntent().getExtras().getString("route_id");
        context = this;
        userSessionManager = new UserSessionManager(TaskDetailActivity.this);
        session = userSessionManager.getSessionDetails();


        mToolBar = findViewById(R.id.taskdetail_toolbar);
        setSupportActionBar(mToolBar);
        getSupportActionBar().setTitle("Task Detail");
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.LOLLIPOP) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
            getSupportActionBar().setHomeButtonEnabled(true);
        } else {
            getActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
            getActionBar().setHomeButtonEnabled(true);
        }


        progressDialog = new ProgressDialog(this);


        clientcomments = findViewById(R.id.taskdetail_client_comments);
        compititorsdata = findViewById(R.id.taskdetail_comptitiors_data);
        ratingBar = findViewById(R.id.taskdetail_client_Rating);


        //add Before Images
        adbimg1 = findViewById(R.id.taskdetail_addbefore_1);
        adbimg2 = findViewById(R.id.taskdetail_addbefore_2);
        adbimg3 = findViewById(R.id.taskdetail_addbefore_3);
        adbimg4 = findViewById(R.id.taskdetail_addbefore_4);
        adbimg5 = findViewById(R.id.taskdetail_addbefore_5);
        adbimg6 = findViewById(R.id.taskdetail_addbefore_6);
        adbimg7 = findViewById(R.id.taskdetail_addbefore_7);
        adbimg8 = findViewById(R.id.taskdetail_addbefore_8);
        adbimg9 = findViewById(R.id.taskdetail_addbefore_9);
        adbimg10 = findViewById(R.id.taskdetail_addbefore_10);
        adbimg11 = findViewById(R.id.taskdetail_addbefore_11);
        //add After Images
        adafimg1 = findViewById(R.id.taskdetail_addafter_1);
        adafimg2 = findViewById(R.id.taskdetail_addafter_2);
        adafimg3 = findViewById(R.id.taskdetail_addafter_3);
        adafimg4 = findViewById(R.id.taskdetail_addafter_4);
        adafimg5 = findViewById(R.id.taskdetail_addafter_5);
        adafimg6 = findViewById(R.id.taskdetail_addafter_6);
        adafimg7 = findViewById(R.id.taskdetail_addafter_7);
        adafimg8 = findViewById(R.id.taskdetail_addafter_8);
        adafimg9 = findViewById(R.id.taskdetail_addafter_9);
        adafimg10 = findViewById(R.id.taskdetail_addafter_10);
        adafimg11 = findViewById(R.id.taskdetail_addafter_11);


        // Compititors Data images
        comp_img1 = findViewById(R.id.taskdetail_comp_1);
        comp_img2 = findViewById(R.id.taskdetail_comp_2);
        comp_img3 = findViewById(R.id.taskdetail_comp_3);


        recyclerView = findViewById(R.id.taskdetail_product_recycler);
        linearLayoutManager = new LinearLayoutManager(context);
        recyclerView.setLayoutManager(linearLayoutManager);
        sharedHelper = new SharedHelper();

        int id = session.getId();


        showProductsData(id);


    }

    public void showProductsData(final int id) {


        progressDialog.setMessage("Loading...");
        progressDialog.show();


        list = new ArrayList<>();


        StringRequest stringRequest = new StringRequest(Request.Method.POST, "https://demonline.tech/app_admin/api/today_task_history_detail.php", new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                progressDialog.dismiss();
                try {
                    JSONObject jsonObject;
                    JSONObject object = new JSONObject(response);

                    boolean status = object.getBoolean("status");


                    if (status) {
                        Picasso.with(TaskDetailActivity.this).load("https://demonline.tech/app_admin/" + object.getString("before_img1")).placeholder(R.drawable.baqala).into(adbimg1);
                   //     Toast.makeText(context, "https://demonline.tech/app_admin/" + object.getString("before_img1"), Toast.LENGTH_SHORT).show();
                        Picasso.with(TaskDetailActivity.this).load("https://demonline.tech/app_admin/" + object.getString("before_img2")).placeholder(R.drawable.baqala).into(adbimg2);
                        Picasso.with(TaskDetailActivity.this).load("https://demonline.tech/app_admin/" + object.getString("before_img3")).placeholder(R.drawable.baqala).into(adbimg3);
                        Picasso.with(TaskDetailActivity.this).load("https://demonline.tech/app_admin/" + object.getString("before_img4")).placeholder(R.drawable.baqala).into(adbimg4);
                        Picasso.with(TaskDetailActivity.this).load("https://demonline.tech/app_admin/" + object.getString("before_img5")).placeholder(R.drawable.baqala).into(adbimg5);
                        Picasso.with(TaskDetailActivity.this).load("https://demonline.tech/app_admin/" + object.getString("before_img6")).placeholder(R.drawable.baqala).into(adbimg6);
                        Picasso.with(TaskDetailActivity.this).load("https://demonline.tech/app_admin/" + object.getString("before_img7")).placeholder(R.drawable.baqala).into(adbimg7);

                        Picasso.with(TaskDetailActivity.this).load("https://demonline.tech/app_admin/" + object.getString("before_img8")).placeholder(R.drawable.baqala).into(adbimg8);
                        Picasso.with(TaskDetailActivity.this).load("https://demonline.tech/app_admin/" + object.getString("before_img9")).placeholder(R.drawable.baqala).into(adbimg9);
                        Picasso.with(TaskDetailActivity.this).load("https://demonline.tech/app_admin/" + object.getString("before_img10")).placeholder(R.drawable.baqala).into(adbimg10);
                        Picasso.with(TaskDetailActivity.this).load("https://demonline.tech/app_admin/" + object.getString("before_img11")).placeholder(R.drawable.baqala).into(adbimg11);


                        Picasso.with(TaskDetailActivity.this).load("https://demonline.tech/app_admin/" + object.getString("after_img1")).placeholder(R.drawable.baqala).into(adafimg1);
                        Picasso.with(TaskDetailActivity.this).load("https://demonline.tech/app_admin/" + object.getString("after_img2")).placeholder(R.drawable.baqala).into(adafimg2);
                        Picasso.with(TaskDetailActivity.this).load("https://demonline.tech/app_admin/" + object.getString("after_img3")).placeholder(R.drawable.baqala).into(adafimg3);
                        Picasso.with(TaskDetailActivity.this).load("https://demonline.tech/app_admin/" + object.getString("after_img4")).placeholder(R.drawable.baqala).into(adafimg4);
                        Picasso.with(TaskDetailActivity.this).load("https://demonline.tech/app_admin/" + object.getString("after_img5")).placeholder(R.drawable.baqala).into(adafimg5);
                        Picasso.with(TaskDetailActivity.this).load("https://demonline.tech/app_admin/" + object.getString("after_img6")).placeholder(R.drawable.baqala).into(adafimg6);
                        Picasso.with(TaskDetailActivity.this).load("https://demonline.tech/app_admin/" + object.getString("after_img7")).placeholder(R.drawable.baqala).into(adafimg7);

                        Picasso.with(TaskDetailActivity.this).load("https://demonline.tech/app_admin/" + object.getString("after_img8")).placeholder(R.drawable.baqala).into(adafimg8);
                        Picasso.with(TaskDetailActivity.this).load("https://demonline.tech/app_admin/" + object.getString("after_img9")).placeholder(R.drawable.baqala).into(adafimg9);
                        Picasso.with(TaskDetailActivity.this).load("https://demonline.tech/app_admin/" + object.getString("after_img10")).placeholder(R.drawable.baqala).into(adafimg10);
                        Picasso.with(TaskDetailActivity.this).load("https://demonline.tech/app_admin/" + object.getString("after_img11")).placeholder(R.drawable.baqala).into(adafimg11);


                        Picasso.with(TaskDetailActivity.this).load("https://demonline.tech/app_admin/" + object.getString("competitor_img1")).placeholder(R.drawable.baqala).into(comp_img1);
                        Picasso.with(TaskDetailActivity.this).load("https://demonline.tech/app_admin/" + object.getString("competitor_img2")).placeholder(R.drawable.baqala).into(comp_img2);
                        Picasso.with(TaskDetailActivity.this).load("https://demonline.tech/app_admin/" + object.getString("competitor_img3")).placeholder(R.drawable.baqala).into(comp_img3);


                        clientcomments.setText(object.getString("client_comments"));
                        compititorsdata.setText(object.getString("competitor_data"));
                        ratingBar.setRating(Float.parseFloat(object.getString("client_ratings")));


                        JSONArray jsonArray = object.getJSONArray("items");
                        for (int i = 0; i < jsonArray.length(); i++) {

                            jsonObject = jsonArray.getJSONObject(i);


                            String productsName = jsonObject.getString("product_name");


                            Taskdetail_productsModel model = new Taskdetail_productsModel(productsName);
                            list.add(model);

                        }

                        adapter = new Taskdetail_productsAdapter(list, context);
                        recyclerView.setAdapter(adapter);


                    } else {
                        Toast.makeText(context.getApplicationContext(),
                                "No Data Found", Toast.LENGTH_SHORT).show();
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

                progressDialog.dismiss();
                Toast.makeText(context.getApplicationContext(), "Server Response: " + error.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
                showProductsData(id);
            }


        }) {

            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> map = new HashMap<>();
                map.put("user_id", String.valueOf(id));
                map.put("route_id", String.valueOf(routeid));
                return map;
            }
        };
        stringRequest.setRetryPolicy(new DefaultRetryPolicy(
                15000,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));

        Volley.newRequestQueue(context.getApplicationContext()).add(stringRequest);


    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            onBackPressed();
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }
}
