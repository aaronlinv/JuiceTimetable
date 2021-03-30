package com.juice.timetable.ui.course;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.ViewConfiguration;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

/**
 * <pre>
 *     author : Aaron
 *     time   : 2020/05/27
 *     desc   : 重写SwipeRefreshLayout 解决左右滑动容易触发下拉刷新的问题
 *     version: 1.0
 * </pre>
 */
public class MySwipeRefreshLayout extends SwipeRefreshLayout {
    private int mTouchSlop;
    // 上一次触摸时的X坐标
    private float mPrevX;


    public MySwipeRefreshLayout(@NonNull Context context) {
        this(context, null);
    }

    public MySwipeRefreshLayout(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        // 触发移动事件的最短距离，如果小于这个距离就不触发移动控件
        mTouchSlop = ViewConfiguration.get(context).getScaledTouchSlop();
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        switch (ev.getAction()) {
            case MotionEvent.ACTION_DOWN:
                mPrevX = ev.getX();
                break;

            case MotionEvent.ACTION_MOVE:
                final float eventX = ev.getX();
                float xDiff = Math.abs(eventX - mPrevX);
                // Log.d("refresh" ,"move----" + eventX + "   " + mPrevX + "   " + mTouchSlop);
                // 增加60的容差，让下拉刷新在竖直滑动时就可以触发
                if (xDiff > mTouchSlop + 60) {
                    return false;
                }
        }
        return super.onInterceptTouchEvent(ev);
    }
}
