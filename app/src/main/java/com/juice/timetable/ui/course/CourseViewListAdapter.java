package com.juice.timetable.ui.course;

import android.graphics.Color;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.ListAdapter;
import androidx.recyclerview.widget.RecyclerView;

import com.juice.timetable.R;
import com.juice.timetable.app.Constant;
import com.juice.timetable.data.bean.CourseViewBean;
import com.juice.timetable.utils.LogUtils;
import com.juice.timetable.utils.PreferencesUtils;
import com.juice.timetable.utils.Utils;

import java.util.Calendar;

/**
 * <pre>
 *     author : Aaron
 *     time   : 2020/05/15
 *     desc   :
 *     version: 1.0
 * </pre>
 */
public class CourseViewListAdapter extends ListAdapter<CourseViewBean, CourseViewHolder> {
    private int NODE_WIDTH = 28;
    private int WEEK_TEXT_SIZE = 12;
    private int NODE_TEXT_SIZE = 11;
    private Integer mCurrentMonth = 5;

    protected CourseViewListAdapter() {
        super(new DiffUtil.ItemCallback<CourseViewBean>() {
            @Override
            public boolean areItemsTheSame(@NonNull CourseViewBean oldItem, @NonNull CourseViewBean newItem) {
                return oldItem == newItem;
            }

            @Override
            public boolean areContentsTheSame(@NonNull CourseViewBean oldItem, @NonNull CourseViewBean newItem) {
                return oldItem.getCurrentIndex() == newItem.getCurrentIndex();
            }
        });
    }

    @NonNull
    @Override
    public CourseViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        CourseViewHolder holder = new CourseViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.pager_course_view, parent, false));
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull CourseViewHolder holder, int position) {
        CourseView courseView = holder.itemView.findViewById(R.id.course_view);
        CourseViewBean item = getItem(position);
        LogUtils.getInstance().d("onBindViewHolder item -- > " + item);
        courseView.setCurrentIndex(item.getCurrentIndex());
        courseView.setSet(item.getWeekSet());
        courseView.setCourses(item.getAllWeekCourse());
        courseView.setOneWeekCourses(item.getOneWeekCourse());
        // 不重置，在切换不同周 会出现重叠情况
        courseView.resetView();
        // 获取第一周星期一的时间
        long firstWeekMondayTime = PreferencesUtils.getLong(Constant.PREF_FIRST_WEEK_MONDAY, -1);
        // 计算时间
        Calendar calendar = Calendar.getInstance();
        // 获取今天的日
        int curDay = calendar.get(Calendar.DATE);

        calendar.setTimeInMillis(firstWeekMondayTime);
        // 加上相隔的周
        calendar.add(Calendar.DATE, (item.getCurrentIndex() - 1) * 7);
        int month = calendar.get(Calendar.MONTH) + 1;
        int day = calendar.get(Calendar.DATE);
        LogUtils.getInstance().d("第" + item.getCurrentIndex() + "周 周一为 -- > " + month + "." + day);


        // 星期栏
        LinearLayout week = holder.itemView.findViewById(R.id.ll_week);
        week.removeAllViews();
        // -1 ：星期栏   0-6：星期 一 ...日
        for (int i = -1; i < 7; i++) {
            TextView textView = new TextView(holder.itemView.getContext());
            textView.setGravity(Gravity.CENTER);
            textView.setTextColor(Color.GRAY);
            textView.setWidth(0);
            LinearLayout.LayoutParams params;

            if (i == -1) {
                // 初始化月份
                params = new LinearLayout.LayoutParams(Utils.dip2px(holder.itemView.getContext().getApplicationContext(), NODE_WIDTH),
                        ViewGroup.LayoutParams.MATCH_PARENT);

                textView.setTextSize(NODE_TEXT_SIZE);

                StringBuilder monthStr = new StringBuilder();
                monthStr.append(month).append("\n月");
                textView.setText(monthStr);

            } else {
                // 初始化课程星期栏
                params = new LinearLayout.LayoutParams(10, ViewGroup.LayoutParams.MATCH_PARENT);
                params.weight = 10;
                textView.setTextSize(WEEK_TEXT_SIZE);

                // 获取当前天的 日（不可直接累加 会出现5月32号的情况）
                int weekDay = getWeekDay(calendar, day, i);

                StringBuilder weekStr = new StringBuilder();
                weekStr.append(Constant.WEEK_SINGLE[i]).append("\n").append(weekDay);

                textView.setText(weekStr);
                // 给今天 加深背景色 本周且为当日
                if (Constant.CUR_WEEK == item.getCurrentIndex() && weekDay == curDay) {
                    textView.setBackgroundColor(0xFFf0f0f0);
                }
            }
            //添加这个视图
            week.addView(textView, params);
        }
        LinearLayout node = holder.itemView.findViewById(R.id.ll_node);
        // 清除已有View 否则会导致切换时一直叠加新的View
        node.removeAllViews();
        //  课程节数栏
        int nodeItemHeight = Utils.dip2px(holder.itemView.getContext().getApplicationContext(), 55);
        for (int i = 1; i <= 11; i++) {
            TextView textView = new TextView(holder.itemView.getContext().getApplicationContext());
            textView.setTextSize(NODE_TEXT_SIZE);
            textView.setGravity(Gravity.CENTER);
            textView.setTextColor(Color.GRAY);
            textView.setText(String.valueOf(i));

            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                    ViewGroup.LayoutParams.MATCH_PARENT, nodeItemHeight);
            node.addView(textView, params);
        }

    }

    /**
     * 获取当前天的 日（不可直接累加 会出现5月32号的情况）
     *
     * @param calendar
     * @param day
     * @param index
     * @return
     */
    private int getWeekDay(Calendar calendar, int day, int index) {
        // 大于28 才需要判断
        if (day + index <= 28) {
            return day + index;
        }
        // 传入的是引用 不能直接用，修改会影响传入的Calendar对象
        Calendar cal = Calendar.getInstance();
        cal.setTimeInMillis(calendar.getTimeInMillis());

        cal.add(Calendar.DATE, index);
        return cal.get(Calendar.DATE);
    }

    @Override
    public int getItemCount() {
        return getCurrentList().size();
    }
}

class CourseViewHolder extends RecyclerView.ViewHolder {

    public CourseViewHolder(@NonNull View itemView) {
        super(itemView);
    }
}