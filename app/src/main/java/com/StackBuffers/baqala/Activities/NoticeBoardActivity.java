package com.StackBuffers.baqala.Activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.Toast;

import com.StackBuffers.baqala.Adapters.NoticeBoardAdapter;
import com.StackBuffers.baqala.HelperClasses.Session;
import com.StackBuffers.baqala.HelperClasses.UserSessionManager;
import com.StackBuffers.baqala.ModelClasses.NoticeBoardModel;
import com.StackBuffers.baqala.R;
import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class NoticeBoardActivity extends AppCompatActivity {

    Context context;
    List<NoticeBoardModel> noticeBoardModels;
    RecyclerView recyclerView;
    LinearLayoutManager linearLayoutManager;
    RecyclerView.Adapter adapter;
    Toolbar mToolBar;
    UserSessionManager userSessionManager;
    Session session;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notice_board);
        context = this;
        recyclerView = findViewById(R.id.noticboard_recyclerview);
        linearLayoutManager = new LinearLayoutManager(context);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(linearLayoutManager);

        mToolBar = findViewById(R.id.noticeboard_toolbar);
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
        userSessionManager = new UserSessionManager(this);
        session = userSessionManager.getSessionDetails();

        getData();


    }

    private void getData() {
        final ProgressDialog progressDialog = new ProgressDialog(context);
        progressDialog.setMessage("Loading...");
        progressDialog.show();


        noticeBoardModels = new ArrayList<>();


        StringRequest stringRequest = new StringRequest(Request.Method.POST, "https://demonline.tech/app_admin/api/notice_board.php", new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                progressDialog.dismiss();

                try {
                    JSONObject jsonObject;
                    JSONObject object = new JSONObject(response);

                    String status = object.getString("success");

                    if (status.equals("1")) {
                        JSONArray jsonArray = object.getJSONArray("data");
                        for (int i = 0; i < jsonArray.length(); i++) {

                            jsonObject = jsonArray.getJSONObject(i);
                            String title = jsonObject.getString("title_news");
                            String description = jsonObject.getString("description_news");
                            String time = jsonObject.getString("time");

                            NoticeBoardModel model = new NoticeBoardModel(title, description, time);
                            noticeBoardModels.add(model);
                        }
                        adapter = new NoticeBoardAdapter(noticeBoardModels, context.getApplicationContext());
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
                getData();
            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> map = new HashMap<>();
                map.put("id", String.valueOf(session.getId()));
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


}
