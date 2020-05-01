package com.juice.timetable.ui.course;

import android.content.Context;
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

    public CourseView(@NonNull Context context) {
        super(context);
    }

    public CourseView(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public void addCourse(int x, int y) {
        View itemView = createCourseItem();
        // 暂时设置2节课
        FrameLayout.LayoutParams params = new FrameLayout.LayoutParams(mRowItemWidth,
                mColItemHeight * 2);
//        params.leftMargin = (course.getRow() - 1) * mRowItemWidth;
//        params.topMargin = (course.getCol() - 1) * mColItemHeight;
        params.leftMargin = (x - 1) * mRowItemWidth;
        params.topMargin = (y - 1) * mColItemHeight;


        itemView.setLayoutParams(params);

        addView(itemView);

    }

    /**
     * 单个课程模块
     *
     * @return
     */
    public View createCourseItem() {
        // 背景
        FrameLayout backgroundView = new FrameLayout(getContext());

        //TextView 暂时设置为 2节课
        final TextView tv = getCourseTextView(mColItemHeight * 2, mRowItemWidth);
        // 设置tv的边界
        FrameLayout.LayoutParams params = new FrameLayout.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.MATCH_PARENT);
        params.setMargins(textLRMargin, textTBMargin, textLRMargin, textTBMargin);
        tv.setLayoutParams(params);
        // 设置tv文本
        String showText = "高级数据库技术(1)班\n" +
                "[网络教学]";
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
}
