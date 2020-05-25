package com.juice.timetable.widget;

import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.widget.RemoteViews;
import android.widget.Toast;

import com.juice.timetable.MainActivity;
import com.juice.timetable.R;
import com.juice.timetable.service.ListViewService;

public class TodayWidget extends AppWidgetProvider {
    private RemoteViews mRemoteViews;
    public static final String ITEM_CLICK = "day.TYPE_LIST";

    private int[] getAppwidgetIds(Context context) {
        AppWidgetManager appWidgetManager = AppWidgetManager.getInstance(context);
        ComponentName componentName = new ComponentName(context, TodayWidget.class);
        return appWidgetManager.getAppWidgetIds(componentName);
    }

    public void triggerUpdate(Context context) {
        Intent intent = new Intent(AppWidgetManager.ACTION_APPWIDGET_UPDATE);
        intent.putExtra(AppWidgetManager.EXTRA_APPWIDGET_IDS, getAppwidgetIds(context));
        context.sendBroadcast(intent);
    }


    public void updateAppWidget(Context context, AppWidgetManager appWidgetManager, int appWidgetId) {
        //创建一个remoteView
        mRemoteViews = new RemoteViews(context.getPackageName(), R.layout.today_widget);
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
        appWidgetManager.notifyAppWidgetViewDataChanged(appWidgetId, R.id.widget_week);
        appWidgetManager.updateAppWidget(appWidgetId, mRemoteViews);
    }

    @Override
    public void onEnabled(Context context) {
        super.onEnabled(context);
        onUpdate(context);
        Toast.makeText(context, "可以添加Widget", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onAppWidgetOptionsChanged(Context context, AppWidgetManager appWidgetManager, int appWidgetId, Bundle newOptions) {
        super.onAppWidgetOptionsChanged(context, appWidgetManager, appWidgetId, newOptions);
        onUpdate(context, appWidgetManager, new int[]{appWidgetId});
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
        mRemoteViews = new RemoteViews(context.getPackageName(), R.layout.today_widget);
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
