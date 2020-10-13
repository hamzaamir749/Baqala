package com.StackBuffers.baqala;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.os.IBinder;
import android.util.Log;
import android.widget.Toast;

import com.StackBuffers.baqala.HelperClasses.UserSessionManager;

import java.text.SimpleDateFormat;
import java.util.Date;

public class MyCounterService extends Service {

    public Context context = this;
    public Handler handler = null;
    public static Runnable runnable = null;
    String lastdate="";

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onCreate() {

        Toast.makeText(context, "Timer is running !", Toast.LENGTH_SHORT).show();

        handler = new Handler();
        runnable = new Runnable() {
            public void run() {


                Log.d("mydet1","servicerun");
                UserSessionManager userSessionManager=new UserSessionManager(MyCounterService.this);

                Date today = new Date();
                SimpleDateFormat format = new SimpleDateFormat("dd-MM-yyyy");
                String dateToStr = format.format(today);

                lastdate=userSessionManager.getdate();
                if(!dateToStr.equals(lastdate))
                {
                    userSessionManager.clearcounter();
                    Log.d("mydet2","servicerun2 "+lastdate);

                    userSessionManager.setdate(dateToStr);
                }
                else
                {
                    Log.d("mydet2","servicerun3");

                    if(userSessionManager.isservicerunning())
                    userSessionManager.addcounter();


                }




                handler.postDelayed(runnable, 1000);
            }
        };

        handler.postDelayed(runnable, 3000);
    }

    @Override
    public void onDestroy() {
        /* IF YOU WANT THIS SERVICE KILLED WITH THE APP THEN UNCOMMENT THE FOLLOWING LINE */
        //handler.removeCallbacks(runnable);


        Toast.makeText(this, "Counter Stopped", Toast.LENGTH_LONG).show();

    }

    @Override
    public void onStart(Intent intent, int startid) {

        //Toast.makeText(context, "Timers starts !", Toast.LENGTH_SHORT).show();

    }
}