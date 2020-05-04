package com.juice.timetable.ui.course;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.text.TextPaint;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.juice.timetable.data.bean.Course;
import com.juice.timetable.utils.LogUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * <pre>
 *     author : Aaron
 *     time   : 2020/04/29
 *     desc   :
 *     version: 1.0
 * </pre>
 */
public class CourseView extends FrameLayout {
    private int mRowItemWidth = dip2px(50);
    private int mColItemHeight = dip2px(55);
    private int mTextLRPadding = dip2px(2);
    private int mTextTBPadding = dip2px(4);
    private int textTBMargin = dip2px(3);
    private int textLRMargin = textTBMargin;
    private int mTextColor = Color.WHITE;
    private int mTextSize = 12;


    private int mWidth;
    private int mHeight;

    private int mRowCount = 7;
    private int mColCount = 16;

    private boolean mFirstDraw = false;

    /**
     * 行item的宽度根据view的总宽度自动平均分配
     */
    private boolean mRowItemWidthAuto = true;
    private int mCurrentIndex = 10;
    private List<Course> courses = null;

    public CourseView(@NonNull Context context) {
        super(context);
    }

    public CourseView(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public void addCourse(Course course) {
        // 课为空，或这不是当前显示周的课，就return回去 不显示
        if (course == null || !isActiveStatus(course)) {
            return;
        }

        View itemView = createCourseItem(course);
        // 节课节数
        int row = course.getCouEndNode() - course.getCouStartNode() + 1;
        FrameLayout.LayoutParams params = new FrameLayout.LayoutParams(mRowItemWidth,
                mColItemHeight * row);
        params.leftMargin = (course.getCouWeek() - 1) * mRowItemWidth;
        params.topMargin = (course.getCouStartNode() - 1) * mColItemHeight;


        itemView.setLayoutParams(params);

        addView(itemView);

    }

    // 是否为当前显示周的课
    private boolean isActiveStatus(Course course) {
        LogUtils.getInstance().d("isActiveStatus:" + course.toString());
        if ((course.getCouStartWeek() == null) || (course.getCouEndWeek() == null)) {
            return false;
        }

        return course.getCouStartWeek() <= mCurrentIndex && mCurrentIndex <= course.getCouEndWeek();
    }

    /**
     * 单个课程模块
     *
     * @return
     */
    public View createCourseItem(Course course) {
        // 背景
        FrameLayout backgroundView = new FrameLayout(getContext());

        //TextView
        LogUtils.getInstance().d(course.toString());
        int row = course.getCouEndNode() - course.getCouStartNode() + 1;
        final TextView tv = getCourseTextView(mColItemHeight * row, mRowItemWidth);
        // 设置tv的边界
        FrameLayout.LayoutParams params = new FrameLayout.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.MATCH_PARENT);
        params.setMargins(textLRMargin, textTBMargin, textLRMargin, textTBMargin);
        tv.setLayoutParams(params);
        // 设置tv文本
        String showText = course.getCouName() + "\n" + course.getCouRoom();
        tv.setText(showText);
        tv.setBackgroundColor(0x80Fbadac);

        // 背景图层
        backgroundView.addView(tv);
//        setItemViewBackground(course, tv);
        //
//        itemEvent(course, bgLayout, tv);

        return backgroundView;


    }

    private TextView getCourseTextView(int h, int w) {
        TextView tv = new TextView(getContext());
        FrameLayout.LayoutParams params = new FrameLayout.LayoutParams(w, h);
        tv.setLayoutParams(params);

        tv.setTextColor(mTextColor);
        tv.setLineSpacing(-2, 1);
        tv.setPadding(mTextLRPadding, mTextTBPadding, mTextLRPadding, mTextTBPadding);
        tv.setTextColor(mTextColor);
        tv.setTextSize(TypedValue.COMPLEX_UNIT_SP, mTextSize);
        tv.setFocusable(true);
        tv.setClickable(true);
        //bold
        TextPaint tp = tv.getPaint();
        tp.setFakeBoldText(true);
        return tv;
    }

    public int dip2px(float dpValue) {
        return (int) (0.5f + dpValue * getContext().getResources().getDisplayMetrics().density);
    }

    @Override
    protected void dispatchDraw(Canvas canvas) {
        super.dispatchDraw(canvas);


        if (!mFirstDraw) {
            // 通过这个添加 ，保证了课表宽度正常
            initCourseItemView();
            mFirstDraw = true;
        }
    }

    /**
     * 把数组中的数据全部添加到界面
     */
    private void initCourseItemView() {
        // 移除课表界面所有课程
        removeAllViews();

        LogUtils.getInstance().d("initCourseItemView执行了");
        // 通过Dao层获取课程数据 添加课程到课程界面
        if (courses == null) {
//            courses = testCourseData.getCourses();
            courses = new ArrayList<>();
        }
        for (Course cou : courses) {
            addCourse(cou);
        }

    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        if (mRowItemWidthAuto) {
            mWidth = w;
            mRowItemWidth = mWidth / mRowCount;
        } else {
            mWidth = mRowItemWidth * mRowCount;
        }
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        LogUtils.getInstance().d("调用onMeasure");

        mHeight = mColItemHeight * mColCount;
        int heightResult = MeasureSpec.makeMeasureSpec(mHeight, MeasureSpec.EXACTLY);

        setMeasuredDimension(widthMeasureSpec, heightResult);
    }

    // 设置当前周
    public CourseView setCurrentIndex(int currentIndex) {
        this.mCurrentIndex = currentIndex;
        LogUtils.getInstance().d("setCurrentIndex:" + currentIndex);
        postInvalidate();
        return this;
    }

    // 设置当前周后重新刷新课表界面
    public void resetView() {
        initCourseItemView();
    }

    public void setCourses(List<Course> courses) {
        this.courses = courses;
    }

}
