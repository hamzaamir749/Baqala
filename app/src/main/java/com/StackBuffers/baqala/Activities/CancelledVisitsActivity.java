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

import com.StackBuffers.baqala.Adapters.CancelledTaskAdapter;
import com.StackBuffers.baqala.HelperClasses.Session;
import com.StackBuffers.baqala.HelperClasses.UserSessionManager;
import com.StackBuffers.baqala.ModelClasses.CancelledTaskModel;
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

public class CancelledVisitsActivity extends AppCompatActivity {


    Context context;

    private RecyclerView recyclerView;
    private LinearLayoutManager linearLayoutManager;
    private RecyclerView.Adapter adapter;
    List<CancelledTaskModel> list;
    UserSessionManager userSessionManager;
    Session session;
    Toolbar mToolBar;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cancelled_visits);
        context = this;
        mToolBar = findViewById(R.id.cancelled_toolbar);
        setSupportActionBar(mToolBar);
        getSupportActionBar().setTitle("Cancelled Visits");
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.LOLLIPOP) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
            getSupportActionBar().setHomeButtonEnabled(true);
        } else {
            getActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
            getActionBar().setHomeButtonEnabled(true);
        }


        recyclerView = findViewById(R.id.recycler_view_cancelled);
        linearLayoutManager = new LinearLayoutManager(context);
        recyclerView.setLayoutManager(linearLayoutManager);

        userSessionManager = new UserSessionManager(context.getApplicationContext());
        session = userSessionManager.getSessionDetails();
        int id = session.getId();
        getCancelledData(id);

    }

    private void getCancelledData(int id) {


        final ProgressDialog progressDialog = new ProgressDialog(context);
        progressDialog.setMessage("Loading...");
        progressDialog.show();

        list = new ArrayList<>();


        StringRequest stringRequest = new StringRequest(Request.Method.POST, "https://demonline.tech/app_admin/api/cancelled_tasks.php", new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                progressDialog.dismiss();
                //   Toast.makeText(context.getApplicationContext(),response,Toast.LENGTH_LONG).show();
                try {
                    JSONObject jsonObject;
                    JSONObject object = new JSONObject(response);

                    String status = object.getString("success");

                    if (status.equals("1")) {
                        JSONArray jsonArray = object.getJSONArray("data");
                        for (int i = 0; i < jsonArray.length(); i++) {

                            jsonObject = jsonArray.getJSONObject(i);
                            String name = jsonObject.getString("store_name");
                            String city = jsonObject.getString("address");
                            String canStatus = jsonObject.getString("visit_status");
                            Double lat = jsonObject.getDouble("lat");
                            Double lng = jsonObject.getDouble("long");


                            CancelledTaskModel model = new CancelledTaskModel(lat, lng, name, city, canStatus);
                            list.add(model);

                        }
                        adapter = new CancelledTaskAdapter(list, context.getApplicationContext());
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
                getCancelledData(id);
            }


        }) {

            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> map = new HashMap<>();
                map.put("user_id", String.valueOf(id));
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
