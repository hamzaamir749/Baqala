package com.StackBuffers.baqala;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.StackBuffers.baqala.HelperClasses.Session;
import com.StackBuffers.baqala.HelperClasses.UserSessionManager;
import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.InstanceIdResult;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class LogInActivity extends AppCompatActivity {

    Button loginbtn;
    EditText userEmail, userPassword;
    UserSessionManager userSessionManager;
    private String token="";


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_log_in);
        userEmail = findViewById(R.id.etmail);
        userPassword = findViewById(R.id.etpassword);
        loginbtn = findViewById(R.id.btn_login);
        getToken();

        loginbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Login();
            }
        });
    }

    private void getToken() {
        FirebaseInstanceId.getInstance().getInstanceId()
                .addOnCompleteListener(new OnCompleteListener<InstanceIdResult>() {
                    @Override
                    public void onComplete(@NonNull Task<InstanceIdResult> task) {
                        if (!task.isSuccessful()) {
                            //   Toast.makeText(PhoneAuthActivity.this, "Exception", Toast.LENGTH_SHORT).show();
                            return;
                        }

                        // Get new Instance ID token
                        token = task.getResult().getToken();


                        //    Toast.makeText(PhoneAuthActivity.this, token, Toast.LENGTH_SHORT).show();
                    }
                });
    }


    private void Login() {


        final String uName, uPassword;


        uName = userEmail.getText().toString();
        uPassword = userPassword.getText().toString();


        if (TextUtils.isEmpty(uName) && TextUtils.isEmpty(uPassword)) {

            userEmail.setError("Please Enter Name...");
            userPassword.setError("Please Enter password...");

        } else if (TextUtils.isEmpty(uName))
        {
            userEmail.setError("Please Enter Name...");
        } else if (TextUtils.isEmpty(uPassword)) {
            userPassword.setError("Please Enter Phone....");
        } else if (token.equals("")) {
            getToken();
            Toast.makeText(this, "Click Again", Toast.LENGTH_SHORT).show();
        } else {
            final ProgressDialog progressDialog = new ProgressDialog(LogInActivity.this);
            progressDialog.setCancelable(false);
            progressDialog.setMessage("Please Wait");
            progressDialog.show();
            StringRequest stringRequest = new StringRequest(Request.Method.POST, "https://demonline.tech/app_admin/api/login.php",

                    new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {

                            progressDialog.dismiss();

                            try {
                                JSONObject jsonObject = new JSONObject(response);
                                boolean status = jsonObject.getBoolean("success");
                                if (status) {
                                    String name = jsonObject.getString("user_name");
                                    String image = jsonObject.getString("user_image");
                                    String address = jsonObject.getString("address");
                                    int id = jsonObject.getInt("id");
                                  /*  int storeid=jsonObject.getInt("");
                                    int routeid=jsonObject.getInt("");*/

                                    Session session;
                                    UserSessionManager userSessionManager = new UserSessionManager(LogInActivity.this);
                                    session = new Session(id, image, name, address);
                                    userSessionManager.setLoggedIn(true);
                                    userSessionManager.setSessionDetails(session);
                                    startActivity(new Intent(getApplicationContext(), HomeActivity.class));
                                } else {
                                    Toast.makeText(getApplicationContext(),
                                            "Wrong UserName or Password", Toast.LENGTH_SHORT).show();
                                }
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                    }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    Toast.makeText(LogInActivity.this, "Server Response: " + error.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
                    Login();
                }

            }) {

                @Override
                protected Map<String, String> getParams() throws AuthFailureError {
                    Map<String, String> params = new HashMap<>();
                    params.put("user_name", userEmail.getText().toString());
                    params.put("password", userPassword.getText().toString());
                    params.put("token",token);
                    return params;
                }
            };
            stringRequest.setRetryPolicy(new DefaultRetryPolicy(
                    15000,
                    DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                    DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
            Volley.newRequestQueue(LogInActivity.this).add(stringRequest);

        }
    }
}