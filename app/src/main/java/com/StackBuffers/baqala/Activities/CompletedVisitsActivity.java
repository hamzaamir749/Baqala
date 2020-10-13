package com.StackBuffers.baqala.Activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.DialogFragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.DatePicker;
import android.widget.TextView;
import android.widget.Toast;

import com.StackBuffers.baqala.Adapters.CompletedTaskAdapter;
import com.StackBuffers.baqala.HelperClasses.DatePickerFragment;
import com.StackBuffers.baqala.HelperClasses.Session;
import com.StackBuffers.baqala.HelperClasses.UserSessionManager;
import com.StackBuffers.baqala.ModelClasses.CompletdTaskModel;
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

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CompletedVisitsActivity extends AppCompatActivity implements DatePickerDialog.OnDateSetListener {


    Context context;
    TextView date;
    private RecyclerView recyclerView;
    private LinearLayoutManager linearLayoutManager;
    private RecyclerView.Adapter adapter;
    List<CompletdTaskModel> list;
    UserSessionManager userSessionManager;
    Session session;
    Toolbar mToolBar;
    private String Date;
    int id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_completed_visits);
        context = this;


        mToolBar = findViewById(R.id.completedvisits_toolbar);
        date = findViewById(R.id.completedvisits_date);
        setSupportActionBar(mToolBar);
        getSupportActionBar().setTitle("Completed Visits");
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.LOLLIPOP) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
            getSupportActionBar().setHomeButtonEnabled(true);
        } else {
            getActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
            getActionBar().setHomeButtonEnabled(true);
        }
        Calendar calFordDate = Calendar.getInstance();
        SimpleDateFormat currentDate = new SimpleDateFormat("yyyy-MM-dd");
        Date = currentDate.format(calFordDate.getTime());
        date.setText(Date);
        date.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DialogFragment dialogFragment = new DatePickerFragment();
                dialogFragment.show(getSupportFragmentManager(), "Pick Date");
            }
        });
        recyclerView = findViewById(R.id.recycler_view_completed);
        linearLayoutManager = new LinearLayoutManager(context);
        recyclerView.setLayoutManager(linearLayoutManager);

        userSessionManager = new UserSessionManager(context.getApplicationContext());
        session = userSessionManager.getSessionDetails();
         id = session.getId();
        getCompletedData();

    }

    private void getCompletedData() {
        final ProgressDialog progressDialog = new ProgressDialog(context);
        progressDialog.setMessage("Loading...");
        progressDialog.show();
        list = new ArrayList<>();
        StringRequest stringRequest = new StringRequest(Request.Method.POST, "https://demonline.tech/app_admin/api/completed_tasks.php", new Response.Listener<String>() {
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
                            String cStatus = jsonObject.getString("visit_status");
                            Double lat = jsonObject.getDouble("lat");
                            Double lng = jsonObject.getDouble("long");
                            CompletdTaskModel model = new CompletdTaskModel(lat, lng, name, city, cStatus);
                            list.add(model);

                        }
                        adapter = new CompletedTaskAdapter(list, context.getApplicationContext());
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
                getCompletedData();
            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> map = new HashMap<>();
                map.put("user_id", String.valueOf(id));
                map.put("date", Date);
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
    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
        String monthstr = String.valueOf(month + 1);
        String day = String.valueOf(dayOfMonth);
        if (monthstr.length() == 1) {
            monthstr = "0" + monthstr;
        }
        if (day.length() == 1) {
            day = "0" + dayOfMonth;
        }
        Date = String.valueOf(year) + "-" + monthstr + "-" + day;
        date.setText(Date);
        getCompletedData();
    }

}
