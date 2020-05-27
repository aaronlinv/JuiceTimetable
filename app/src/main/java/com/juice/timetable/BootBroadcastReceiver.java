package com.juice.timetable;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

public class BootBroadcastReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        Intent thisIntent = new Intent(context, MainActivity.class);
        context.startActivity(thisIntent);
    }
}
