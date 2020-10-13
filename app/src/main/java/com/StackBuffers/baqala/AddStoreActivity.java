package com.StackBuffers.baqala;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.provider.Settings;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.ActivityCompat;

import com.StackBuffers.baqala.Activities.AddnewstoreActivity;
import com.StackBuffers.baqala.HelperClasses.Session;
import com.StackBuffers.baqala.HelperClasses.UserSessionManager;
import com.android.volley.AuthFailureError;
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

public class AddStoreActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    EditText storename, clientname, number, saddress;
    Button addbtn;
    Context context;
    Toolbar mToolBar;
    Session session;
    UserSessionManager userSessionManager;
    int id;
    String[] storetypes = {"Key account", "Non key account", "Grocery", "Whole sale"};
    String sType;
    List<String> list;
    public Spinner itemsSpiner;
    AddnewstoreActivity activity;
    List<String> productitems;
    Spinner spin;
    private static final int REQUEST_LOCATION = 1;
    LocationManager locationManager;
    double latitude, longitude;
    String addressString = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_store);
        //getSupportActionBar().hide();
        context = this;
        addbtn = findViewById(R.id.addnewstore);
        storename = findViewById(R.id.et_addstorename);
        clientname = findViewById(R.id.et_addclientname);
        number = findViewById(R.id.et_addnumber);
        saddress = findViewById(R.id.et_addaddress);
        mToolBar = findViewById(R.id.addstore_toolbar);
        setSupportActionBar(mToolBar);
        itemsSpiner = findViewById(R.id.addstore_products_spiner);
        list = new ArrayList<>();
        userSessionManager = new UserSessionManager(context.getApplicationContext());
        session = userSessionManager.getSessionDetails();
        id = session.getId();
        locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        if (!locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)) {
            buildAlertMessageNoGps();

        } else if (locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)) {
            getLocation();
        }

        getSupportActionBar().setTitle("Add Store");
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.LOLLIPOP) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
            getSupportActionBar().setHomeButtonEnabled(true);
        } else {
            getActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
            getActionBar().setHomeButtonEnabled(true);
        }


        spin = findViewById(R.id.addstore_spinner);
        spin.setOnItemSelectedListener(this);
        userSessionManager = new UserSessionManager(context.getApplicationContext());
        session = userSessionManager.getSessionDetails();

        int id = session.getId();
        productshow(id);
//Creating the ArrayAdapter instance having the bank name list

        SetSpinners();
        addbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                if (saddress.getText().toString().isEmpty()) {
                    getLocation();
                    Toast.makeText(context, "Please Wait We Cant Pick Location", Toast.LENGTH_SHORT).show();
                }

                else {
                    addStore();
                }

            }
        });


    }

    private void getLocation() {
        if (ActivityCompat.checkSelfPermission(
                AddStoreActivity.this, Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission
                (AddStoreActivity.this, Manifest.permission.ACCESS_COARSE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {

            ActivityCompat.requestPermissions(AddStoreActivity.this, new String[]
                    {Manifest.permission.ACCESS_FINE_LOCATION}, REQUEST_LOCATION);
        } else {
            Location location = locationManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);
            Location location1 = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
            Location location2 = locationManager.getLastKnownLocation(LocationManager.PASSIVE_PROVIDER);
            if (location != null) {
                latitude = location.getLatitude();
                longitude = location.getLongitude();
                addressString = String.valueOf(latitude) + "," + String.valueOf(longitude);
                saddress.setText(addressString);
                Toast.makeText(AddStoreActivity.this, "Coordinates Added", Toast.LENGTH_SHORT).show();

            } else if (location1 != null) {
                latitude = location1.getLatitude();
                longitude = location1.getLongitude();
                addressString = String.valueOf(latitude) + "," + String.valueOf(longitude);
                saddress.setText(addressString);
                Toast.makeText(AddStoreActivity.this, "Coordinates Added", Toast.LENGTH_SHORT).show();

            } else if (location2 != null) {
                latitude = location2.getLatitude();
                longitude = location2.getLongitude();
                addressString = String.valueOf(latitude) + "," + String.valueOf(longitude);
                saddress.setText(addressString);
                Toast.makeText(AddStoreActivity.this, "Coordinates Added", Toast.LENGTH_SHORT).show();
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

    private void productshow(int id) {
        final ProgressDialog progressDialog = new ProgressDialog(context);
        progressDialog.setMessage("Loading...");
        progressDialog.show();
        productitems = new ArrayList<String>();
        StringRequest stringRequest = new StringRequest(Request.Method.POST, "https://demonline.tech/app_admin/api/get_items.php", new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                progressDialog.dismiss();
                try {
                    JSONObject jsonObject;
                    JSONObject object = new JSONObject(response);
                    boolean status = object.getBoolean("status");
                    if (status) {
                        JSONArray jsonArray = object.getJSONArray("items");
                        for (int i = 0; i < jsonArray.length(); i++) {

                            jsonObject = jsonArray.getJSONObject(i);
                            String item = jsonObject.getString("name");
                            productitems.add(item);
                        }
                        Toast.makeText(context, "Size" + String.valueOf(productitems.size()), Toast.LENGTH_SHORT).show();

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
                map.put("id", String.valueOf(id));
                return map;
            }
        };

        Volley.newRequestQueue(context.getApplicationContext()).add(stringRequest);


    }

    private void SetSpinners() {
        ArrayAdapter<String> aa = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_item, storetypes);

        aa.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
//Setting the ArrayAdapter data on the Spinner
        spin.setAdapter(aa);

    }


    private void addStore() {

        final ProgressDialog progressDialog = new ProgressDialog(AddStoreActivity.this);
        progressDialog.setCancelable(false);
        progressDialog.setMessage("Please Wait");
        progressDialog.show();
        StringRequest stringRequest = new StringRequest(Request.Method.POST, "https://demonline.tech/app_admin/api/add_store.php", new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                progressDialog.dismiss();

                try {
                    JSONObject jsonObject = new JSONObject(response);
                    String status = jsonObject.getString("success");
                    if (status.equals("1")) {

                        final AlertDialog dialogBuilder = new AlertDialog.Builder(context).create();
                        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                        final View dialogView = inflater.inflate(R.layout.dialogboxaddstore, null);
                        dialogBuilder.setView(dialogView);
                        dialogBuilder.setCanceledOnTouchOutside(true);
                        dialogBuilder.show();


                    } else {
                        Toast.makeText(getApplicationContext(),
                                "Response: " + response, Toast.LENGTH_SHORT).show();
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {


                Toast.makeText(AddStoreActivity.this, "Server Response: " + error.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
            }
        }


        ) {

            @Override
            protected Map<String, String> getParams() throws AuthFailureError {


                Map<String, String> params = new HashMap<>();


                params.put("store_name", storename.getText().toString());
                params.put("owner_name", clientname.getText().toString());
                params.put("mobile_number", number.getText().toString());
                params.put("address", addressString);
                params.put("store_type", String.valueOf(sType));
                params.put("user_id", String.valueOf(id));

                return params;
            }

        };

        Volley.newRequestQueue(AddStoreActivity.this).add(stringRequest);


    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            onBackPressed();
        }
        return super.onOptionsItemSelected(item);
    }


    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

     //   Toast.makeText(getApplicationContext(), storetypes[i], Toast.LENGTH_LONG).show();
        sType = storetypes[i];

    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }
}
