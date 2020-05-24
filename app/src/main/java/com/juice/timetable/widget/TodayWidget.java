package com.juice.timetable.widget;

import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.Context;
import android.content.Intent;
import android.widget.RemoteViews;

import com.juice.timetable.R;

public class TodayWidget extends AppWidgetProvider {
    public static final String ITEM_CLICK = "day.TYPE_LIST";
    private RemoteViews mRemoteViews;

    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
        mRemoteViews = new RemoteViews(context.getPackageName(), R.layout.today_widget);
        mRemoteViews.setTextViewText(R.id.widget_week, getWeekday());
        appWidgetManager.updateAppWidget(appWidgetIds, mRemoteViews);

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
    public void onDeleted(Context context, int[] appWidgetIds) {
        super.onDeleted(context, appWidgetIds);
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        super.onReceive(context, intent);
        mRemoteViews = new RemoteViews(context.getPackageName(), R.layout.today_widget);
        mRemoteViews.setTextViewText(R.id.widget_week, getWeekday());
    }
}
