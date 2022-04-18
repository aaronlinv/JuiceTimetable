package com.juice.timetable.service;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;

import androidx.annotation.Nullable;


/**
 * <pre>
 *     author : wyx
 *     time   : 2022/3/30 21:37
 * </pre>
 */
public class NotificationService extends Service {
    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {


        System.out.println("NotificationService onStartCommand");
        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    public void onCreate() {
        super.onCreate();

        System.out.println("NotificationService running");
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        System.out.println("NotificationService Destroy");
    }
}
