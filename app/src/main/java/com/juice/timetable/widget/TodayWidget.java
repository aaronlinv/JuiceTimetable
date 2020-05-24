package com.juice.timetable.widget;

import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.Context;
import android.content.Intent;
import android.widget.RemoteViews;

import com.juice.timetable.R;
import com.juice.timetable.data.JuiceDatabase;
import com.juice.timetable.data.bean.OneWeekCourse;
import com.juice.timetable.data.dao.OneWeekCourseDao;
import com.juice.timetable.service.ListViewService;

import java.util.List;

public class TodayWidget extends AppWidgetProvider {
    private RemoteViews mRemoteViews;
    public static final String ITEM_CLICK = "day.TYPE_LIST";

    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
        JuiceDatabase database = JuiceDatabase.getDatabase(context.getApplicationContext());
        OneWeekCourseDao oneWeekCourseDao = database.getOneWeekCourseDao();
        List<OneWeekCourse> oneWeekCourse = oneWeekCourseDao.getOneWeekCourse();
        mRemoteViews = new RemoteViews(context.getPackageName(), R.layout.today_widget);
        mRemoteViews.setTextViewText(R.id.widget_week, getWeekday());
        appWidgetManager.updateAppWidget(appWidgetIds, mRemoteViews);
        int len = oneWeekCourse.size();
        for (int i = 0; i < len; i++) {
            OneWeekCourse oneWeekCourse1 = oneWeekCourse.get(i);
            mRemoteViews = new RemoteViews(context.getPackageName(), R.layout.today_widget);
            Intent lvIntent = new Intent(context, ListViewService.class);
            mRemoteViews.setRemoteAdapter(R.id.lv_test, lvIntent);
            mRemoteViews.setEmptyView(R.id.lv_test, android.R.id.empty);
            Intent intent = new Intent(ITEM_CLICK);
            PendingIntent pendingIntent = PendingIntent.getBroadcast(context, 1, intent, PendingIntent.FLAG_UPDATE_CURRENT);
            mRemoteViews.setPendingIntentTemplate(R.id.lv_test, pendingIntent);
            appWidgetManager.updateAppWidget(oneWeekCourse1.getOnlyID(), mRemoteViews);
        }
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
        JuiceDatabase database = JuiceDatabase.getDatabase(context.getApplicationContext());
        OneWeekCourseDao oneWeekCourseDao = database.getOneWeekCourseDao();
        List<OneWeekCourse> oneWeekCourse = oneWeekCourseDao.getOneWeekCourse();
        AppWidgetManager appWidgetManager = AppWidgetManager.getInstance(context);
        mRemoteViews = new RemoteViews(context.getPackageName(), R.layout.today_widget);
        mRemoteViews.setTextViewText(R.id.widget_week, getWeekday());
        int len = oneWeekCourse.size();
        if (intent.getAction().equals(ITEM_CLICK)) {
            for (int i = 0; i < len; i++) {
                OneWeekCourse oneWeekCourse1 = oneWeekCourse.get(i);
                mRemoteViews = new RemoteViews(context.getPackageName(), R.layout.today_widget);
                Intent lvIntent = new Intent(context, ListViewService.class);
                mRemoteViews.setRemoteAdapter(R.id.lv_test, lvIntent);
                mRemoteViews.setEmptyView(R.id.lv_test, android.R.id.empty);
                Intent intent1 = new Intent(ITEM_CLICK);
                PendingIntent pendingIntent = PendingIntent.getBroadcast(context, 1, intent1, PendingIntent.FLAG_UPDATE_CURRENT);
                mRemoteViews.setPendingIntentTemplate(R.id.lv_test, pendingIntent);
                appWidgetManager.updateAppWidget(oneWeekCourse1.getOnlyID(), mRemoteViews);
            }
        }
    }
}
