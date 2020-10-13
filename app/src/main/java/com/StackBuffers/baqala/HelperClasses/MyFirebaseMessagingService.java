package com.StackBuffers.baqala.HelperClasses;

import android.Manifest;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationManager;
import android.os.Build;
import android.util.Log;

import androidx.core.app.ActivityCompat;
import androidx.core.app.NotificationCompat;

import com.StackBuffers.baqala.R;
import com.StackBuffers.baqala.StartActivity;
import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class MyFirebaseMessagingService extends FirebaseMessagingService {
    private static final String TAG = "ABc";
    String id;
    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {
        Log.i("token1", remoteMessage.getData().get("message"));
        String msg = remoteMessage.getData().get("message");
        String title = remoteMessage.getData().get("title");
        id=remoteMessage.getData().get("id");
        getLocation();
        showNotification(msg, title);
    }

    private void getLocation() {
        LocationManager locationManager;
        String latitude, longitude;
        locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
        Location location = locationManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);

        Location location1 = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);

        Location location2 = locationManager.getLastKnownLocation(LocationManager. PASSIVE_PROVIDER);

        if (location != null) {
            double latti = location.getLatitude();
            double longi = location.getLongitude();

            StartThreadInBackground(String.valueOf(latti),String.valueOf(longi));

        } else  if (location1 != null) {
            double latti = location1.getLatitude();
            double longi = location1.getLongitude();

            StartThreadInBackground(String.valueOf(latti),String.valueOf(longi));


        } else  if (location2 != null) {
            double latti = location2.getLatitude();
            double longi = location2.getLongitude();

            StartThreadInBackground(String.valueOf(latti),String.valueOf(longi));


        }else{
            getLocation();
          Log.d("lo","not get");

        }
    }

    private void StartThreadInBackground(String lati,String longii) {
        JSONObject jsonObject = null;
         MediaType JSON
                = MediaType.get("application/json; charset=utf-8");

        OkHttpClient client = new OkHttpClient();
        try{
             jsonObject=new JSONObject();
            jsonObject.put("lat",lati);
            jsonObject.put("lng",longii);
            jsonObject.put("id",id);
        }
        catch (JSONException e)
        {
            e.printStackTrace();
        }
        RequestBody body = RequestBody.create(jsonObject.toString(), JSON);
        Log.d("OkHTTP","Request Created");
        Request request = new Request.Builder()
                .url("https://demonline.tech/app_admin/api/get_lat_long.php")
                .post(body)
                .build();
        try (Response response = client.newCall(request).execute()) {
            Log.d("OkHTTP",response.body().string());
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    private void showNotification(String title, String msg) {

        Intent i = new Intent(this, StartActivity.class);
        i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, i, PendingIntent.FLAG_UPDATE_CURRENT);
        NotificationManager notificationManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
        if (Build.VERSION.SDK_INT >= 26) {
            NotificationChannel channel = new NotificationChannel("default", "Just Delivero", NotificationManager.IMPORTANCE_HIGH);
            Notification notification = new Notification.Builder(this, "default")
                    .setContentTitle(title)
                    .setContentText(msg)
                    .setContentIntent(pendingIntent)
                    .setSmallIcon(R.drawable.ic_launcher_background)
                    .setAutoCancel(true)
                    .setTicker(title)
                    .setDefaults(Notification.DEFAULT_LIGHTS | Notification.DEFAULT_VIBRATE)
                    .build();
            if (notificationManager != null) {
                notificationManager.createNotificationChannel(channel);
                notificationManager.notify(0, notification);
            }
        } else {
            NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(getApplicationContext());
            mBuilder.setContentTitle(title)
                    .setContentText(msg)
                    .setContentIntent(pendingIntent)
                    .setSmallIcon(R.drawable.ic_launcher_background)
                    .setAutoCancel(true)
                    .setDefaults(Notification.DEFAULT_LIGHTS | Notification.DEFAULT_VIBRATE)
                    .setTicker(title);
            mBuilder.build();
            if (notificationManager != null) {
                notificationManager.notify(0, mBuilder.build());
            }
        }

    }
}
