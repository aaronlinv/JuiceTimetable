package com.juice.timetable.widget;

import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.widget.RemoteViews;

import com.juice.timetable.MainActivity;
import com.juice.timetable.R;
import com.juice.timetable.service.ListViewService;

public class TodayWidget extends AppWidgetProvider {
    private RemoteViews mRemoteViews;
    public static final String ITEM_CLICK = "day.TYPE_LIST";
    private final String ACTION_UPDATE_ALL = "com.lyl.widget.UPDATE_ALL";
    private final Intent EXAMPLE_SERVICE_INTENT = new Intent("android.appwidget.action.EXAMPLE_APP_WIDGET_SERVICE");

    private static int[] getAppwidgetIds(Context context) {
        AppWidgetManager appWidgetManager = AppWidgetManager.getInstance(context);
        ComponentName componentName = new ComponentName(context, TodayWidget.class);
        return appWidgetManager.getAppWidgetIds(componentName);
    }


    public void updateAppWidget(Context context, AppWidgetManager appWidgetManager, int appWidgetId) {
        //创建一个remoteView
        mRemoteViews = new RemoteViews(context.getPackageName(), R.layout.today_widget);
        //把这个widget绑定到RemoteViewService
        Intent intent = new Intent(context, ListViewService.class);
        mRemoteViews.setRemoteAdapter(R.id.lv_test, intent);
        Intent intent1 = new Intent(ITEM_CLICK);
        mRemoteViews.setTextViewText(R.id.widget_week, getWeekday());
        PendingIntent pendingIntent = PendingIntent.getBroadcast(context, 1, intent1, PendingIntent.FLAG_UPDATE_CURRENT);
        //PendingIntent pendingIntent = PendingIntent.getActivity(context, 0, clickIntent, 0);
        mRemoteViews.setPendingIntentTemplate(R.id.lv_test, pendingIntent);
        mRemoteViews.setOnClickPendingIntent(R.id.week, getOpenPendingIntent(context));
        appWidgetManager.notifyAppWidgetViewDataChanged(appWidgetId, R.id.lv_test);
        appWidgetManager.notifyAppWidgetViewDataChanged(appWidgetId, R.id.widget_week);
        appWidgetManager.updateAppWidget(appWidgetId, mRemoteViews);
    }

    @Override
    public void onAppWidgetOptionsChanged(Context context, AppWidgetManager appWidgetManager, int appWidgetId, Bundle newOptions) {
        super.onAppWidgetOptionsChanged(context, appWidgetManager, appWidgetId, newOptions);
        onUpdate(context, appWidgetManager, new int[]{appWidgetId});
    }

    @Override
    public void onEnabled(Context context) {
        onUpdate(context);
        super.onEnabled(context);
    }

    private void onUpdate(Context context) {
        AppWidgetManager widgetManager = AppWidgetManager.getInstance(context);
        int[] widgetIds = getAppwidgetIds(context);
        onUpdate(context, widgetManager, widgetIds);
    }
    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
        for (int appWidgetId : appWidgetIds) {
            updateAppWidget(context, appWidgetManager, appWidgetId);
        }
    }

    public static void triggerUpdate(Context context) {
        Intent intent = new Intent(AppWidgetManager.ACTION_APPWIDGET_UPDATE);
        intent.putExtra(AppWidgetManager.EXTRA_APPWIDGET_IDS, getAppwidgetIds(context));
        context.sendBroadcast(intent);
    }
    @Override
    public void onReceive(Context context, Intent intent) {
        super.onReceive(context, intent);
        //onUpdate(context);
        mRemoteViews = new RemoteViews(context.getPackageName(), R.layout.today_widget);
        Intent intent2 = new Intent(context, ListViewService.class);
        //设置适配器
        mRemoteViews.setRemoteAdapter(R.id.lv_test, intent2);
        mRemoteViews.setTextViewText(R.id.widget_week, getWeekday());
        Intent intent1 = new Intent(ITEM_CLICK);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(context, 1, intent1, PendingIntent.FLAG_UPDATE_CURRENT);
        mRemoteViews.setPendingIntentTemplate(R.id.lv_test, pendingIntent);
        mRemoteViews.setOnClickPendingIntent(R.id.week, getOpenPendingIntent(context));
    }

    private PendingIntent getOpenPendingIntent(Context context) {
        Intent intent = new Intent();
        intent.setClass(context, MainActivity.class);
        intent.putExtra("main", "开始");
        PendingIntent pendingIntent = PendingIntent.getActivity(context, 0, intent, 0);
        return pendingIntent;
    }

    public String getWeekday() {
        String str = "";
        int weekDay = java.util.Calendar.getInstance().get(java.util.Calendar.DAY_OF_WEEK);
        if (weekDay == 1) {
            weekDay = 7;
        } else {
            weekDay = weekDay - 1;
        }
        switch (weekDay) {
            case 1:
                str = "周一";
                break;
            case 2:
                str = "周二";
                break;
            case 3:
                str = "周三";
                break;
            case 4:
                str = "周四";
                break;
            case 5:
                str = "周五";
                break;
            case 6:
                str = "周六";
                break;
            case 7:
                str = "周日";
                break;
        }
        return str;
    }
}
