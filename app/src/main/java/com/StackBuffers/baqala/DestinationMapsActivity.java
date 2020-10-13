package com.StackBuffers.baqala;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.cardview.widget.CardView;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.FragmentActivity;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.location.Location;
import android.location.LocationManager;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.provider.Settings;
import android.util.Base64;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.StackBuffers.baqala.HelperClasses.SharedHelper;
import com.StackBuffers.baqala.HelperClasses.UserSessionManager;
import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.util.HashMap;
import java.util.Map;

public class DestinationMapsActivity extends AppCompatActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    private static final int REQUEST_LOCATION = 1;
    LocationManager locationManager;
    double latitude, longitude;
    Button checkin, cancel, cancelsubmit;
    Context context;
    Toolbar mToolBar;
    AlertDialog dialogBuilder;
    int PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION = 1000;
    RadioGroup radioGroup;
    //RadioButton r1,r2,r3,r4,r5;
    ImageView iv1, iv2, iv3;
    SharedHelper sharedHelper;
    Uri uri;
    String Img1, Img2, Img3, re;
    String routeid;

    boolean isSetLatLong = false;
    RadioButton rb1, rb2, rb3, rb4, rb5;
    double tasklats, taskLongs;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);

        setContentView(R.layout.activity_destination_maps);

        tasklats = getIntent().getDoubleExtra("lats", 0);
        taskLongs = getIntent().getDoubleExtra("longs", 0);
        context = this;

        checkin = findViewById(R.id.btn_checkin);
        cancel = findViewById(R.id.btn_cancel);


        sharedHelper = new SharedHelper();
        routeid = sharedHelper.getKey(context, "route_id");
        mToolBar = findViewById(R.id.destinationnearby_Toolbar);
        setSupportActionBar(mToolBar);
        getSupportActionBar().setTitle("Destination Near By");
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.LOLLIPOP) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
            getSupportActionBar().setHomeButtonEnabled(true);
        } else {
            getActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
            getActionBar().setHomeButtonEnabled(true);
        }

        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        if (!locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)) {
            buildAlertMessageNoGps();

        } else if (locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)) {
            getLocation();
        }
        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                OpenCancelDialog();

            }
        });

        checkin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ShowDialouge();

            }
        });


    }

    private void ShowDialouge() {
        final android.app.AlertDialog dialogBuilder = new android.app.AlertDialog.Builder(DestinationMapsActivity.this).create();
        // LayoutInflater inflater = context.getApplicationContext().getLayoutInflater();
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        final View dialogView = inflater.inflate(R.layout.map_check_item_layout, null);

        dialogBuilder.setView(dialogView);
        dialogBuilder.setCanceledOnTouchOutside(true);
        dialogBuilder.show();
        CardView direction = dialogView.findViewById(R.id.maps_check_directions);
        CardView startTask = dialogView.findViewById(R.id.maps_check_starttask);

        direction.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Uri gmmIntentUri = Uri.parse("google.navigation:q=" + tasklats + "," + taskLongs);
                Intent mapIntent = new Intent(Intent.ACTION_VIEW, gmmIntentUri);
                mapIntent.setPackage("com.google.android.apps.maps");
                context.startActivity(mapIntent);
            }
        });
        startTask.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                UserSessionManager userSessionManager=new UserSessionManager(DestinationMapsActivity.this);

                if (userSessionManager.isservicerunning()) {
                    if (!isSetLatLong) {
                        getLocation();
                    } else {
                        double radius = distance(latitude, longitude, tasklats, taskLongs);
                        if (radius <= 1) {
                            startActivity(new Intent(getApplicationContext(), CheckinActivity.class));
                        } else {
                            Toast.makeText(getApplicationContext(), "You Are Not Available In the range of Store Location", Toast.LENGTH_SHORT).show();
                        }
                    }
                }
                else
                {

                    Toast.makeText(context, "You can't start task without starting timer", Toast.LENGTH_SHORT).show();

                }
            }
        });

    }


    private void getLocation() {
        if (ActivityCompat.checkSelfPermission(
                DestinationMapsActivity.this, Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission
                (DestinationMapsActivity.this, Manifest.permission.ACCESS_COARSE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {

            ActivityCompat.requestPermissions(DestinationMapsActivity.this, new String[]
                    {Manifest.permission.ACCESS_FINE_LOCATION}, REQUEST_LOCATION);
        } else {
            Location location = locationManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);
            Location location1 = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
            Location location2 = locationManager.getLastKnownLocation(LocationManager.PASSIVE_PROVIDER);
            if (location != null) {
                latitude = location.getLatitude();
                longitude = location.getLongitude();
                isSetLatLong = true;

            } else if (location1 != null) {
                latitude = location1.getLatitude();
                longitude = location1.getLongitude();
                isSetLatLong = true;

            } else if (location2 != null) {
                latitude = location2.getLatitude();
                longitude = location2.getLongitude();
                isSetLatLong = true;

            } else {
                getLocation();
                Toast.makeText(this, "Please Wait While We Geting Your Location", Toast.LENGTH_SHORT).show();
            }
        }
    }

    protected void buildAlertMessageNoGps() {

        final AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("Please Turn ON your GPS Connection")
                .setCancelable(false)
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    public void onClick(final DialogInterface dialog, final int id) {
                        startActivity(new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS));
                    }
                })
                .setNegativeButton("No", new DialogInterface.OnClickListener() {
                    public void onClick(final DialogInterface dialog, final int id) {
                        dialog.cancel();
                    }
                });
        final AlertDialog alert = builder.create();
        alert.show();
    }


    private double distance(double lat1, double lon1, double lat2, double lon2) {
        double theta = lon1 - lon2;
        double dist = Math.sin(deg2rad(lat1))
                * Math.sin(deg2rad(lat2))
                + Math.cos(deg2rad(lat1))
                * Math.cos(deg2rad(lat2))
                * Math.cos(deg2rad(theta));
        dist = Math.acos(dist);
        dist = rad2deg(dist);
        dist = dist * 60 * 1.1515;
        return (dist);
    }

    private double deg2rad(double deg) {
        return (deg * Math.PI / 180.0);
    }

    private double rad2deg(double rad) {
        return (rad * 180.0 / Math.PI);
    }

    private void OpenCancelDialog() {
        dialogBuilder = new AlertDialog.Builder(context).create();
        // LayoutInflater inflater = context.getApplicationContext().getLayoutInflater();
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        final View dialogView = inflater.inflate(R.layout.oncancel, null);
        rb1 = dialogView.findViewById(R.id.reason1);
        rb2 = dialogView.findViewById(R.id.reason2);
        rb3 = dialogView.findViewById(R.id.reason3);
        rb4 = dialogView.findViewById(R.id.reason4);
        rb5 = dialogView.findViewById(R.id.reason5);

        dialogBuilder.setView(dialogView);
        dialogBuilder.setCanceledOnTouchOutside(true);
        iv1 = dialogView.findViewById(R.id.upload_img1_oncancel);
        iv2 = dialogView.findViewById(R.id.upload_img2_oncancel);
        iv3 = dialogView.findViewById(R.id.upload_img3_oncancel);
        cancelsubmit = dialogView.findViewById(R.id.oncancel_submit);
        dialogBuilder.show();


        cancelsubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                submitToServer();
            }
        });


        iv1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                GetImageFromGallery(1000);

            }
        });
        iv2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                GetImageFromGallery(2000);

            }
        });
        iv3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                GetImageFromGallery(3000);

            }
        });

    }

    private void submitToServer() {

        final ProgressDialog progressDialog = new ProgressDialog(DestinationMapsActivity.this);
        progressDialog.setCancelable(false);
        progressDialog.setMessage("Please Wait");
        progressDialog.show();

        if (rb1.isChecked()) {
            re = "Close On Arrival";
        } else if (rb2.isChecked()) {
            re = "Traffic Issue";
        } else if (rb3.isChecked()) {
            re = "Vehicle Malfunction";
        } else if (rb4.isChecked()) {
            re = "Client Refusal";
        } else if (rb5.isChecked()) {

            re = "Client Changes";
        }


        StringRequest stringRequest = new StringRequest(Request.Method.POST, "https://demonline.tech/app_admin/api/task_cancellation.php",

                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        progressDialog.dismiss();
                        try {
                            JSONObject object = new JSONObject(response);
                            String status = object.getString("success");
                            if (status.equals("1")) {

                                dialogBuilder.dismiss();
                                Intent intent = new Intent(getApplicationContext(), HomeActivity.class);
                                startActivity(intent);
                                finishAffinity();

                            } else {
                                Toast.makeText(getApplicationContext(),
                                        "Not Submit", Toast.LENGTH_SHORT).show();
                            }
                        } catch (JSONException e) {
                            Toast.makeText(context, "Exception: " + e.getMessage(), Toast.LENGTH_SHORT).show();
                        }


                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                progressDialog.dismiss();
                Toast.makeText(DestinationMapsActivity.this, "Server Response: " + error.getLocalizedMessage(), Toast.LENGTH_SHORT).show();

            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("img1", Img1);
                params.put("img2", Img2);
                params.put("img3", Img3);
                params.put("route_id", routeid);
                params.put("reason", re);
                return params;
            }
        };
        stringRequest.setRetryPolicy(new DefaultRetryPolicy(15000,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        Volley.newRequestQueue(DestinationMapsActivity.this).add(stringRequest);

    }


    private void GetImageFromGallery(int SELECT_PHOTO) {

      /*  Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent, "Select Picture"), SELECT_PHOTO);*/

        File outputFile = new File(Environment.getExternalStorageDirectory().toString());

        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        intent.putExtra(MediaStore.EXTRA_OUTPUT, outputFile);
        startActivityForResult(intent, SELECT_PHOTO);


    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {


        if (requestCode == 1000 && resultCode == RESULT_OK && data.getExtras().get("data") != null) {

            Bitmap bitmap = (Bitmap) data.getExtras().get("data");
            iv1.setImageBitmap(bitmap);
            Img1 = ImagerToString(bitmap);


        } else if (requestCode == 2000 && resultCode == RESULT_OK && data.getExtras().get("data") != null) {
            Bitmap bitmap = (Bitmap) data.getExtras().get("data");
            iv2.setImageBitmap(bitmap);
            Img2 = ImagerToString(bitmap);


        } else if (requestCode == 3000 && resultCode == RESULT_OK && data.getExtras().get("data") != null) {
            Bitmap bitmap = (Bitmap) data.getExtras().get("data");
            iv3.setImageBitmap(bitmap);
            Img3 = ImagerToString(bitmap);

        }

        super.onActivityResult(requestCode, resultCode, data);

    }

    private String ImagerToString(Bitmap bitmap) {

        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 70, stream);
        byte[] byteFormat = stream.toByteArray();
        // get the base 64 string
        String imgString = Base64.encodeToString(byteFormat, Base64.NO_WRAP);
        return imgString;

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            onBackPressed();
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        // Add a marker in Sydney and move the camera
        LatLng sydney = new LatLng(-34, 151);
        mMap.addMarker(new MarkerOptions().position(sydney).title("Marker in Sydney"));
        mMap.moveCamera(CameraUpdateFactory.newLatLng(sydney));
    }

    @Override
    public void onBackPressed() {

        super.onBackPressed();
    }
}
