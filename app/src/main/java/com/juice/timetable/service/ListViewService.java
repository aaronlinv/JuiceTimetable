package com.juice.timetable.service;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.widget.RemoteViews;
import android.widget.RemoteViewsService;

import com.juice.timetable.R;
import com.juice.timetable.app.Constant;
import com.juice.timetable.data.bean.Course;
import com.juice.timetable.data.bean.OneWeekCourse;
import com.juice.timetable.data.repository.AllWeekCourseRepository;
import com.juice.timetable.data.repository.OneWeekCourseRepository;

import java.util.ArrayList;
import java.util.List;


public class ListViewService extends RemoteViewsService {

    private List<OneWeekCourse> mList;

    @Override
    public RemoteViewsFactory onGetViewFactory(Intent intent) {
        return new ListRemoteViewsFactory(this.getApplicationContext(), intent);
    }

    private class ListRemoteViewsFactory implements RemoteViewsService.RemoteViewsFactory {

        private Context mContext;
        private List<OneWeekCourse> oneWeekCourses;
        private List<Course> courses;

        private List<OneWeekCourse> mList = new ArrayList<>();

        /**
         * 构造函数
         *
         * @param context
         * @param intent
         */
        public ListRemoteViewsFactory(Context context, Intent intent) {
            mContext = context;
        }

        @Override
        public void onCreate() {
        }

        @Override
        public void onDataSetChanged() {
            mList = new ArrayList<>();
            OneWeekCourseRepository repository = new OneWeekCourseRepository(mContext);
            oneWeekCourses = repository.getOneWeekCourse();
            for (OneWeekCourse oneWeekCours : oneWeekCourses) {
                if (oneWeekCours.getInWeek().equals(Constant.CUR_WEEK) && oneWeekCours.getDayOfWeek() == (getWeekdayNumber())) {
                    mList.add(oneWeekCours);
                }
            }
            AllWeekCourseRepository allWeekCourseRepository = new AllWeekCourseRepository(mContext);
            courses = allWeekCourseRepository.getAllWeekCourse();
        }

        /**
         * 清理资源，释放内存
         */
        @Override
        public void onDestroy() {
        }

        /**
         * 返回集合视图数量
         * @return
         */
        @Override
        public int getCount() {
            return mList.size();
        }

        /**
         * 创建并且填充，在指定索引位置显示的View
         * @param position
         * @return
         */
        @Override
        public RemoteViews getViewAt(int position) {
            if (position < 0) {
                return null;
            }
            RemoteViews views = new RemoteViews(mContext.getPackageName(), R.layout.item_today_widget);
            OneWeekCourse oneWeekCourse = mList.get(position);
            for (Course cours : courses) {
                if (oneWeekCourse.getCouName().replace(" ", "").equals(cours.getCouName().replace(" ", ""))) {
                    views.setTextViewText(R.id.widget_teacher, cours.getCouTeacher());
                    break;
                }
            }
            views.setTextViewText(R.id.widget_name, oneWeekCourse.getCouName());
            views.setTextViewText(R.id.widget_room, oneWeekCourse.getCouRoom());
            views.setTextViewText(R.id.tv_start, String.valueOf(oneWeekCourse.getStartNode()));
            views.setTextViewText(R.id.tv_end, String.valueOf(oneWeekCourse.getEndNode()));
            // 填充Intent，填充在AppWdigetProvider中创建的PendingIntent
            return views;

        }

        /* 在更新界面的时候如果耗时就会显示 正在加载... 的默认字样，但是你可以更改这个界面
         * 如果返回null 显示默认界面
         * 否则 加载自定义的，返回RemoteViews
         */
        @Override
        public RemoteViews getLoadingView() {
            return null;
        }

        @Override
        public int getViewTypeCount() {
            return 1;
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public boolean hasStableIds() {
            return false;
        }

        public int getWeekdayNumber() {
            int weekDay = java.util.Calendar.getInstance().get(java.util.Calendar.DAY_OF_WEEK);
            if (weekDay == 1) {
                weekDay = 7;
            } else {
                weekDay = weekDay - 1;
            }
            Log.d("星期", weekDay + "");
            return weekDay;
        }

    }

}
