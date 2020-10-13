package com.StackBuffers.baqala;

import android.Manifest;
import android.app.ActivityManager;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Rect;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.SystemClock;
import android.text.Html;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.Chronometer;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.StackBuffers.baqala.Activities.AddnewstoreActivity;
import com.StackBuffers.baqala.Activities.CancelledVisitsActivity;
import com.StackBuffers.baqala.Activities.CompletedVisitsActivity;
import com.StackBuffers.baqala.Activities.HistoryActivity;
import com.StackBuffers.baqala.Activities.NoticeBoardActivity;
import com.StackBuffers.baqala.Activities.PendingVisitsActivity;
import com.StackBuffers.baqala.Activities.ReportsActivity;
import com.StackBuffers.baqala.Adapters.HomeAdapter;
import com.StackBuffers.baqala.HelperClasses.Session;
import com.StackBuffers.baqala.HelperClasses.SharedHelper;
import com.StackBuffers.baqala.HelperClasses.UserSessionManager;
import com.StackBuffers.baqala.ModelClasses.BitmapList;
import com.StackBuffers.baqala.ModelClasses.HomeModel;
import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.navigation.NavigationView;
import com.google.gson.JsonArray;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import de.hdodenhof.circleimageview.CircleImageView;
import pub.devrel.easypermissions.AfterPermissionGranted;
import pub.devrel.easypermissions.AppSettingsDialog;
import pub.devrel.easypermissions.EasyPermissions;

public class HomeActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener, EasyPermissions.PermissionCallbacks {
    Chronometer mChronometer;
    long timeWhenStopped = 0;
    TextView mChronometerHome;
    long timeWhenStoppedHome = 0;
    private AppBarConfiguration mAppBarConfiguration;
    Context context;
    TextView uname, uaddress;
    CircleImageView profiedp;
    ImageButton stopcounter;
    Session session;
    ProgressDialog pd;
    UserSessionManager userSessionManager;
    DrawerLayout drawerLayout;
    boolean ispop = false;
    private RecyclerView recyclerView;
    BigDecimal hours, minutes, seconds;

    private LinearLayoutManager linearLayoutManager;
    private RecyclerView.Adapter adapter;
    List<HomeModel> list;
    int id;
    SharedHelper sharedHelper;
    TextView homepop_name, homepop_address;

    FloatingActionButton homepop_next;
    List<Bitmap> listImages;
    TextView txtTasks;
    SwipeRefreshLayout refreshLayout;
    boolean isPermit = false;
    ImageButton cStart, cStop;
    String status;

    CountDownTimer countDownTimer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
     /*   Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);*/

        RequestPerMissions();

        stopcounter = findViewById(R.id.stopcounter);
        context = this;
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        NavigationView navigationView = findViewById(R.id.nav_view);

        recyclerView = findViewById(R.id.recycler_view_home);
        linearLayoutManager = new LinearLayoutManager(context);
        recyclerView.setLayoutManager(linearLayoutManager);
        mChronometerHome = findViewById(R.id.homechronometerExample);
        cStart = findViewById(R.id.homestarttime);
        cStop = findViewById(R.id.homestoptime);
        status = new SharedHelper().getKey(HomeActivity.this, "timestart");
        if (status.equals("start")) {
            homeStart();
        }

        cStart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                homeStart();


                if (!isMyServiceRunning(MyCounterService.class)) {
                    startService(new Intent(HomeActivity.this, MyCounterService.class));
                    userSessionManager.setservicerunning(true);
                    Log.d("thisruns", "yesss");
                } else {
                    userSessionManager.setservicerunning(true);
                    Toast.makeText(context, "Timer resumed", Toast.LENGTH_SHORT).show();
                }

                cStop.setVisibility(View.VISIBLE);
            }
        });

        cStop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                homeStop();
                timeWhenStoppedHome = 0;
                if (isMyServiceRunning(MyCounterService.class)) {
                    userSessionManager.setservicerunning(false);
                    Toast.makeText(context, "Timer Paused", Toast.LENGTH_SHORT).show();
                }


            }
        });

        stopcounter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                gettodaytask("https://demonline.tech/app_admin/api/today_tasks.php");
            }
        });
        ImageView ivnav = findViewById(R.id.iv_nav);
        CircleImageView dp1 = findViewById(R.id.userdp);
        TextView username1 = findViewById(R.id.user_name);
        txtTasks = findViewById(R.id.hometotal_task);
        refreshLayout = findViewById(R.id.refresh_main_page);
        refreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                getData();
                refreshLayout.setRefreshing(false);

            }
        });

        drawerLayout = findViewById(R.id.drawer_layout);
        ivnav.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                drawerLayout.openDrawer(GravityCompat.START);
            }
        });
        userSessionManager = new UserSessionManager(context.getApplicationContext());
        session = userSessionManager.getSessionDetails();

        userSessionManager.bitmapList = new BitmapList();
        listImages = new ArrayList<>();
        listImages = userSessionManager.bitmapList.getList();
        if (listImages.size() > 0) {
            timeWhenStopped = getIntent().getLongExtra("time", 24);
            showPOPUP();
        }
        String namee = session.getName();
        String imagee = session.getImage();
        id = session.getId();
        sharedHelper = new SharedHelper();

        username1.setText(namee);
        Picasso.with(context.getApplicationContext())
                .load(imagee)
                .resize(80, 80)
                .centerCrop()
                .into(dp1);


        getData();


        View view = navigationView.getHeaderView(0);

        if (getSupportActionBar() != null) {
            getSupportActionBar().setTitle("Home");
        }
        navigationView.setNavigationItemSelectedListener(this);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();
        uname = view.findViewById(R.id.tvnameheader);
        uaddress = view.findViewById(R.id.tvcity);
        profiedp = view.findViewById(R.id.profilepic);

        String name = session.getName();
        String image = session.getImage();
        String address = session.getAddress();

        uname.setText(name);
        uaddress.setText(address);

        Picasso.with(context)
                .load(image)
                .resize(80, 80)
                .centerCrop()
                .placeholder(R.mipmap.ic_launcher)
                .into(profiedp);


        countDownTimer = new CountDownTimer(3000, 500) {
            @Override
            public void onTick(long millisUntilFinished) {
                mChronometerHome.setText(getDurationString(userSessionManager.getcounter()));
            }

            @Override
            public void onFinish() {

                countDownTimer.start();
            }
        }.start();


        if (userSessionManager.isservicerunning()) {
            cStop.setVisibility(View.VISIBLE);
        }
    }

    @AfterPermissionGranted(123)
    private void RequestPerMissions() {
        String[] parms = {Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.CAMERA, Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.ACCESS_COARSE_LOCATION, Manifest.permission.VIBRATE, Manifest.permission.ACCESS_LOCATION_EXTRA_COMMANDS};
        if (!isPermit) {
            EasyPermissions.requestPermissions(this, "We Need Some Permissions", 123, parms);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        EasyPermissions.onRequestPermissionsResult(requestCode, permissions, grantResults, this);
    }

    private void showPOPUP() {

        final AlertDialog dialogBuilder = new AlertDialog.Builder(context, R.style.CustomAlertDialog).create();
        // LayoutInflater inflater = context.getApplicationContext().getLayoutInflater();
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        final View dialogView = inflater.inflate(R.layout.home_popup_layout, null);
        dialogBuilder.setView(dialogView);
        dialogBuilder.setCanceledOnTouchOutside(false);
        Window window = dialogBuilder.getWindow();
        WindowManager.LayoutParams wlp = window.getAttributes();
        ispop = true;
        wlp.gravity = Gravity.BOTTOM;
        wlp.flags &= ~WindowManager.LayoutParams.FLAG_DIM_BEHIND;
        window.setAttributes(wlp);
        homepop_name = dialogView.findViewById(R.id.homepop_name);
        homepop_address = dialogView.findViewById(R.id.homepop_address);
        mChronometer = (Chronometer) dialogView.findViewById(R.id.chronometerExample2);
        Start();
        homepop_next = dialogView.findViewById(R.id.homepop_next);
        homepop_name.setText(userSessionManager.bitmapList.getName());
        homepop_address.setText(userSessionManager.bitmapList.getAddress());
        SharedHelper sharedHelper = new SharedHelper();
        dialogBuilder.getWindow().getAttributes().windowAnimations = R.style.SlidingDialogAnimation;
        dialogBuilder.show();


        homepop_next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sharedHelper.putKey(context, "homeboolean", "false");
                ispop = false;
                Stop();
                Intent intent = new Intent(getApplicationContext(), CheckinActivity.class);
                intent.putExtra("time", timeWhenStopped);
                startActivity(intent);
            }
        });
    }


    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.nav_history) {
            startActivity(new Intent(getApplicationContext(), HistoryActivity.class));
        } else if (id == R.id.nav_cancelvisit) {
            startActivity(new Intent(getApplicationContext(), CancelledVisitsActivity.class));
        } else if (id == R.id.nav_pendingvisit) {
            startActivity(new Intent(getApplicationContext(), PendingVisitsActivity.class));
        } else if (id == R.id.nav_completedvisit) {
            startActivity(new Intent(getApplicationContext(), CompletedVisitsActivity.class));
        } else if (id == R.id.nav_home) {
          //  ShowDialouge();
        } else if (id == R.id.nav_logout) {
            LogOut();
        } else if (id == R.id.nav_addnewstore) {
            startActivity(new Intent(getApplicationContext(), AddnewstoreActivity.class));
        } else if (id == R.id.nav_noticeboard) {
            startActivity(new Intent(getApplicationContext(), NoticeBoardActivity.class));
        } else if (id == R.id.nav_reports) {
            startActivity(new Intent(getApplicationContext(), ReportsActivity.class));
        }
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    private void ShowDialouge() {
        Rect displayRectangle = new Rect();
        Window window = HomeActivity.this.getWindow();
        window.getDecorView().getWindowVisibleDisplayFrame(displayRectangle);
        final AlertDialog dialogBuilder = new AlertDialog.Builder(HomeActivity.this,R.style.CustomAlertDialog2).create();
        // LayoutInflater inflater = context.getApplicationContext().getLayoutInflater();
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        final View dialogView = inflater.inflate(R.layout.checkout_dialouge_layout, null);
        dialogView.setMinimumWidth((int) (displayRectangle.width() * 1f));
        dialogView.setMinimumHeight((int) (displayRectangle.height() * 1f));
        dialogBuilder.setView(dialogView);
        dialogBuilder.setCanceledOnTouchOutside(true);
        TextView textView = dialogView.findViewById(R.id.checkout_DialougeText);
        Button yes = dialogView.findViewById(R.id.checkoutDialouge_yes);
        Button no = dialogView.findViewById(R.id.checkoutDialouge_no);
        TextView timeText = dialogView.findViewById(R.id.cdl_time);
        dialogBuilder.show();
        no.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialogBuilder.dismiss();
            }
        });
        yes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            }
        });
    }

    private void LogOut() {

        final ProgressDialog progressDialog = new ProgressDialog(HomeActivity.this);
        progressDialog.setCancelable(false);
        progressDialog.setMessage("Please Wait");
        progressDialog.show();

        StringRequest stringRequest = new StringRequest(Request.Method.POST, "https://demonline.tech/app_admin/api/logout.php",

                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        progressDialog.dismiss();

                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            boolean status = jsonObject.getBoolean("status");
                            if (status) {
                                userSessionManager.setLoggedIn(false);
                                userSessionManager.clearSessionData();
                                startActivity(new Intent(getApplicationContext(), LogInActivity.class));
                                finishAffinity();
                            } else {
                                Toast.makeText(getApplicationContext(),
                                        "Try Again", Toast.LENGTH_SHORT).show();
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }


                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                progressDialog.dismiss();
                Toast.makeText(HomeActivity.this, "Server Response: " + error.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
                LogOut();
            }

        }) {

            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("user_id", String.valueOf(session.getId()));
                return params;
            }
        };

        Volley.newRequestQueue(HomeActivity.this).add(stringRequest);

    }


    private void getData() {
        final ProgressDialog progressDialog = new ProgressDialog(context);
        progressDialog.setMessage("Loading...");
        progressDialog.show();


        list = new ArrayList<>();


        StringRequest stringRequest = new StringRequest(Request.Method.POST, "https://demonline.tech/app_admin/api/today_tasks.php", new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                progressDialog.dismiss();
                //Toast.makeText(context.getApplicationContext(),response,Toast.LENGTH_LONG).show();
                try {
                    JSONObject jsonObject;
                    JSONObject object = new JSONObject(response);
                    int count = 0;
                    String status = object.getString("success");

                    if (status.equals("1")) {
                        JSONArray jsonArray = object.getJSONArray("data");
                        for (int i = 0; i < jsonArray.length(); i++) {

                            jsonObject = jsonArray.getJSONObject(i);
                            int storeid = jsonObject.getInt("store_id");
                            int routeid = jsonObject.getInt("route_id");
                            String sName = jsonObject.getString("store_name");
                            String sAddress = jsonObject.getString("address");
                            Double latitude = jsonObject.getDouble("lat");
                            Double longitude = jsonObject.getDouble("long");

                            sharedHelper.putKey(context.getApplicationContext(), "lat", String.valueOf(latitude));
                            sharedHelper.putKey(context.getApplicationContext(), "lng", String.valueOf(longitude));
                            sharedHelper.putKey(context.getApplicationContext(), "storeid", String.valueOf(storeid));
                            sharedHelper.putKey(context.getApplicationContext(), "routeid", String.valueOf(routeid));

                            HomeModel model2 = new HomeModel(latitude, longitude, storeid, routeid, sName, sAddress);
                            list.add(model2);
                            count++;
                        }
                        txtTasks.setText("You have " + String.valueOf(count) + " task Today");
                        adapter = new HomeAdapter(list, context.getApplicationContext());
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
            }
        }


        ) {
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

    public void homeStop() {
        //    timeWhenStoppedHome = mChronometer.getBase() - SystemClock.elapsedRealtime();
        //  mChronometer.stop();
    }

    public void homeStart() {
//        mChronometer.setBase(SystemClock.elapsedRealtime() + timeWhenStoppedHome);
        //      mChronometer.start();
    }

    @Override
    protected void onStop() {
        homeStop();
        new SharedHelper().putLong(HomeActivity.this, "hometime", timeWhenStoppedHome);
        super.onStop();
    }

    @Override
    protected void onPause() {
        homeStop();
        new SharedHelper().putLong(HomeActivity.this, "hometime", timeWhenStoppedHome);
        super.onPause();
    }

    @Override
    protected void onDestroy() {
        homeStop();
        new SharedHelper().putLong(HomeActivity.this, "hometime", timeWhenStoppedHome);
        super.onDestroy();
    }

    @Override
    protected void onResume() {
        timeWhenStoppedHome = new SharedHelper().getLong(HomeActivity.this, "hometime");
        homeStart();
        // new SharedHelper().putLong(HomeActivity.this,"hometime",timeWhenStoppedHome);
        super.onResume();
    }

    @Override
    protected void onRestart() {
        timeWhenStoppedHome = new SharedHelper().getLong(HomeActivity.this, "hometime");
        homeStart();
        super.onRestart();
    }

    public void Stop() {
        status = new SharedHelper().getKey(HomeActivity.this, "timestart");
        if (status.equals("start")) {
            timeWhenStopped = mChronometer.getBase() - SystemClock.elapsedRealtime();
            mChronometer.stop();

        }

    }

    public void Start() {
        status = new SharedHelper().getKey(HomeActivity.this, "timestart");
        if (status.equals("start")) {

            mChronometer.setBase(SystemClock.elapsedRealtime() + timeWhenStopped);
            mChronometer.start();
        }
    }

    @Override
    public void onBackPressed() {
        /* super.onBackPressed();*/
        if (ispop) {
            Toast.makeText(context, "Your Task is Started. Please complete it", Toast.LENGTH_SHORT).show();
        } else {
            if (drawerLayout.isDrawerOpen(GravityCompat.START)) {
                drawerLayout.closeDrawer(GravityCompat.START);
            } else {
                finish();
            }
        }
    }

    @Override
    public void onPermissionsGranted(int requestCode, @NonNull List<String> perms) {
        isPermit = true;
    }

    @Override
    public void onPermissionsDenied(int requestCode, @NonNull List<String> perms) {
        finish();
    }

    private String getDurationString(int seconds) {

        int hours = seconds / 3600;
        int minutes = (seconds % 3600) / 60;
        seconds = seconds % 60;

        Log.d("secondsval", String.valueOf(seconds));

        return twoDigitString(hours) + " : " + twoDigitString(minutes) + " : " + twoDigitString(seconds);
    }

    private String twoDigitString(int number) {

        if (number == 0) {
            return "00";
        }

        if (number / 10 == 0) {
            return "0" + number;
        }

        return String.valueOf(number);
    }

    private boolean isMyServiceRunning(Class<?> serviceClass) {
        ActivityManager manager = (ActivityManager) getSystemService(Context.ACTIVITY_SERVICE);
        for (ActivityManager.RunningServiceInfo service : manager.getRunningServices(Integer.MAX_VALUE)) {
            if (serviceClass.getName().equals(service.service.getClassName())) {
                return true;
            }
        }
        return false;
    }


    public void gettodaytask(String url) {

        pd = new ProgressDialog(this);
        pd.setMessage("Signing in....");

        pd.setMessage(Html.fromHtml("<font color='#000000'>Wait for a moment</font>"));
        pd.getWindow().setBackgroundDrawableResource(R.color.colorWhite);

        StringRequest request = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                Log.d("taskresponse", response);
                try {
                    JSONObject object = new JSONObject(response);

                    JSONArray jsonArray = object.getJSONArray("data");

                    if (jsonArray.length() > 0) {
                        Toast.makeText(context, "You have " + jsonArray.length() + " task(s) pending", Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(context, "Tasks done , timer stopped", Toast.LENGTH_SHORT).show();
                        savetimerindB("https://demonline.tech/app_admin/api/set_timer.php");
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }
                pd.dismiss();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                pd.dismiss();
                Log.d("loginError : ", error.getMessage() + "");
            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                HashMap<String, String> map = new HashMap<>();
                map.put("user_id", String.valueOf(session.getId()));
                return map;
            }
        };

        RequestQueue queue = Volley.newRequestQueue(this);
        pd.show();
        queue.add(request);


    }


    public void savetimerindB(String url) {

        pd = new ProgressDialog(this);
        pd.setMessage("Signing in....");

        pd.setMessage(Html.fromHtml("<font color='#000000'>Wait for a moment</font>"));
        pd.getWindow().setBackgroundDrawableResource(R.color.colorWhite);

        StringRequest request = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                Log.d("taskresponse", response);

                userSessionManager.setservicerunning(false);
                userSessionManager.clearcounter();

                mChronometerHome.setText(getDurationString(userSessionManager.getcounter()));

                pd.dismiss();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                pd.dismiss();
                Log.d("loginError : ", error.getMessage() + "");
            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                HashMap<String, String> map = new HashMap<>();
                map.put("id", String.valueOf(session.getId()));
                map.put("time", String.valueOf(getDurationString(userSessionManager.getcounter())));
                Date today = new Date();
                SimpleDateFormat format = new SimpleDateFormat("dd-MM-yyyy");
                String dateToStr = format.format(today);

                map.put("date", String.valueOf(dateToStr));

                return map;
            }
        };

        RequestQueue queue = Volley.newRequestQueue(this);
        pd.show();
        queue.add(request);


    }

}
