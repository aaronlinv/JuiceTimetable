package com.juice.timetable.widget;

import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.Context;
import android.content.Intent;
import android.widget.RemoteViews;
import android.widget.Toast;

import com.juice.timetable.MainActivity;
import com.juice.timetable.R;
import com.juice.timetable.service.ListViewService;

public class TodayWidget extends AppWidgetProvider {
    private RemoteViews mRemoteViews;
    public static final String ITEM_CLICK = "day.TYPE_LIST";

    public void updateAppWidget(Context context, AppWidgetManager appWidgetManager, int appWidgetId) {
        //创建一个remoteView
        mRemoteViews = new RemoteViews(context.getPackageName(), R.layout.today_widget);
        mRemoteViews.setTextViewText(R.id.widget_week, getWeekday());
        //把这个widget绑定到RemoteViewService
        Intent intent = new Intent(context, ListViewService.class);
        //设置适配器
        mRemoteViews.setRemoteAdapter(R.id.lv_test, intent);
        //设置列表点击触发事件
        Intent clickIntent = new Intent(context, MainActivity.class);
//        clickIntent.setAction("clickAction");
//        clickIntent.putExtra(appWidgetManager.EXTRA_APPWIDGET_ID, appWidgetId);
//        clickIntent.setData(Uri.parse(clickIntent.toUri(Intent.URI_INTENT_SCHEME)));
        Intent intent1 = new Intent(ITEM_CLICK);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(context, 1, intent1, PendingIntent.FLAG_UPDATE_CURRENT);
        //PendingIntent pendingIntent = PendingIntent.getActivity(context, 0, clickIntent, 0);
        mRemoteViews.setPendingIntentTemplate(R.id.lv_test, pendingIntent);
        appWidgetManager.notifyAppWidgetViewDataChanged(appWidgetId, R.id.lv_test);
        appWidgetManager.updateAppWidget(appWidgetId, mRemoteViews);
    }

    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
        for (int appWidgetId : appWidgetIds) {
            updateAppWidget(context, appWidgetManager, appWidgetId);
        }
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

    @Override
    public void onEnabled(Context context) {
        super.onEnabled(context);
        Toast.makeText(context, "可以添加Widget", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onDeleted(Context context, int[] appWidgetIds) {
        super.onDeleted(context, appWidgetIds);
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        super.onReceive(context, intent);
        String action = intent.getAction();
        if (action.equals("clickAction")) {
            Toast.makeText(context, "click", Toast.LENGTH_SHORT).show();
        }
        AppWidgetManager appWidgetManager = AppWidgetManager.getInstance(context);
        mRemoteViews = new RemoteViews(context.getPackageName(), R.layout.today_widget);
        mRemoteViews.setTextViewText(R.id.widget_week, getWeekday());
        Intent intent2 = new Intent(context, ListViewService.class);
        //设置适配器
        mRemoteViews.setRemoteAdapter(R.id.lv_test, intent2);
        //设置列表点击触发事件
        Intent clickIntent = new Intent(context, MainActivity.class);
//        clickIntent.setAction("clickAction");
//        clickIntent.putExtra(appWidgetManager.EXTRA_APPWIDGET_ID, appWidgetId);
//        clickIntent.setData(Uri.parse(clickIntent.toUri(Intent.URI_INTENT_SCHEME)));
        Intent intent1 = new Intent(ITEM_CLICK);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(context, 1, intent1, PendingIntent.FLAG_UPDATE_CURRENT);
        //PendingIntent pendingIntent = PendingIntent.getActivity(context, 0, clickIntent, 0);
        mRemoteViews.setPendingIntentTemplate(R.id.lv_test, pendingIntent);
    }
}
