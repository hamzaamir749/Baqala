package com.StackBuffers.baqala.HelperClasses;


import android.content.Intent;
import android.os.IBinder;
import android.os.SystemClock;

import androidx.annotation.Nullable;

public class Service extends android.app.Service {
    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        return super.onStartCommand(intent, flags, startId);
    }
    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }
}
