package com.juice.timetable.ui.course;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.DashPathEffect;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.drawable.StateListDrawable;
import android.text.TextPaint;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.juice.timetable.app.Constant;
import com.juice.timetable.data.bean.Course;
import com.juice.timetable.data.bean.OneWeekCourse;
import com.juice.timetable.utils.LogUtils;
import com.juice.timetable.utils.Utils;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;

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
    private int mColCount = 11;

    private boolean mFirstDraw = false;

    // 行item的宽度根据view的总宽度自动平均分配
    private boolean mRowItemWidthAuto = true;

    // 显示分割线
    private boolean mShowVerticalLine = true;
    private boolean mShowHorizontalLine = true;
    private OnItemClickListener mItemClickListener;


    private Paint mLinePaint;
    private Path mLinePath = new Path();

    public CourseView(@NonNull Context context) {
        super(context);
    }

    public CourseView(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        initView();
    }

    /**
     * 初始化 分割线
     */
    private void initView() {
        mLinePaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mLinePaint.setColor(Color.LTGRAY);
        mLinePaint.setStrokeWidth(1);
        mLinePaint.setStyle(Paint.Style.FILL_AND_STROKE);
        mLinePaint.setPathEffect(new DashPathEffect(new float[]{5, 10}, 0));
    }

    private void drawLine(Canvas canvas) {
        //横线
        if (mShowHorizontalLine) {
            for (int i = 1; i < mColCount; i++) {
                mLinePath.reset();
                mLinePath.moveTo(0, i * mColItemHeight);
                mLinePath.lineTo(mWidth, i * mColItemHeight);
                canvas.drawPath(mLinePath, mLinePaint);
            }
        }

        //竖线
        if (mShowVerticalLine) {
            for (int i = 1; i < mRowCount; i++) {
                mLinePath.reset();
                mLinePath.moveTo(i * mRowItemWidth, 0);
                mLinePath.lineTo(i * mRowItemWidth, mHeight);
                canvas.drawPath(mLinePath, mLinePaint);
            }
        }
    }

    public int getCurrentIndex() {
        return mCurrentIndex;
    }

    private int mCurrentIndex = Constant.CUR_WEEK;

    public List<Course> getCourses() {
        return courses;
    }

    private List<Course> courses = null;

    public List<OneWeekCourse> getOneWeekCourses() {
        return oneWeekCourses;
    }

    public void setOneWeekCourses(List<OneWeekCourse> oneWeekCourses) {
        this.oneWeekCourses = oneWeekCourses;
    }

    private List<OneWeekCourse> oneWeekCourses = null;
    HashSet<Integer> set = new HashSet<>();

    public HashSet<Integer> getSet() {
        return set;
    }

    public void setSet(HashSet<Integer> set) {
        this.set = set;
    }


    public void addCourse(Course course) {

        // 冲突课程集合
        List<Course> conflictList = findConflictCourse(course);
        String str = "";
        if (conflictList.size() > 0) {
            for (Course course1 : conflictList) {
                str = str + "<--->" + course1.getCouName();
            }
            LogUtils.getInstance().d("查找冲突课程" + course.getCouName() + " -- > " + str);
        }


        View itemView = createCourseItem(course, conflictList);
        // 节课节数
        int row = course.getCouEndNode() - course.getCouStartNode() + 1;
        FrameLayout.LayoutParams params = new FrameLayout.LayoutParams(mRowItemWidth,
                mColItemHeight * row);
        params.leftMargin = (course.getCouWeek() - 1) * mRowItemWidth;
        params.topMargin = (course.getCouStartNode() - 1) * mColItemHeight;


        itemView.setLayoutParams(params);

        addView(itemView);

    }

    // 只用于周课表 判断是否为当前显示周的课
    private boolean isActiveStatus(Course course) {

        // 课为空 return回去 不显示
        if (course == null) {
            return false;
        }
        // 单周课程，当前不为单周 返回
        if (course.getCouWeekType() != null && course.getCouWeekType() == 1 && mCurrentIndex % 2 != 1) {
            return false;
        }
        // 双周课程，当前不为双周 返回
        if (course.getCouWeekType() != null && course.getCouWeekType() == 2 && mCurrentIndex % 2 != 0) {
            return false;
        }
        // 起或止周不存在
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
    public View createCourseItem(Course course, List<Course> conflictList) {
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
        // 撞课 在前面 添加[课程冲突]


        String showText = "";
//        if (course.getCouWeekType() == 4) {
//            showText = "[课程冲突]";
//        }
        if (conflictList.size() > 0) {
            showText = "[课程冲突]";
        }
        showText = showText + course.getCouName() + "\n" + course.getCouRoom();
        tv.setText(showText);

        int backgroundColor = Utils.getColor(course.getCouColor() + Constant.RAINBOW_MODE_NUM);
        tv.setBackgroundColor(backgroundColor);

        // 背景图层
        backgroundView.addView(tv);
        // 设置点击的背景色
        setItemViewBackground(tv, backgroundColor);
        // 点击事件
        initEvent(tv, course);
        return backgroundView;


    }

    private void setItemViewBackground(TextView tv, int color) {
        StateListDrawable drawable;
        drawable = getShowBgDrawable(color, color & 0x80FFFFFF);
        tv.setBackground(drawable);
    }

    private StateListDrawable getShowBgDrawable(int color, int color2) {
        return Utils.getPressedSelector(getContext(),
                // 0 不圆角矩形
                color, color2, 0);
    }

    /**
     * 课程点击事件
     *
     * @param tv
     * @param course
     */
    private void initEvent(TextView tv, final Course course) {
        tv.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                // 通知ViewPager
                if (mItemClickListener != null) {
                    mItemClickListener.onClick(course);
                }
            }
        });

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
        drawLine(canvas);
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

        LogUtils.getInstance().d("initCourseItemView执行");
        // 通过Dao层获取课程数据 添加课程到课程界面
        if (courses == null) {
            courses = new ArrayList<>();
        }
        // 需要被添加的课
        List<Course> addCouList = new ArrayList<>();
        // 数据库有存当前需要显示的周课表 那就都显示
        if (set.contains(mCurrentIndex)) {
            for (OneWeekCourse oneCou : oneWeekCourses) {
                if (oneCou.getInWeek().equals(mCurrentIndex)) {
                    // 封装一个Course对象
                    Course course = new Course();
                    course.setCouName(oneCou.getCouName());
                    course.setCouRoom(oneCou.getCouRoom());
                    course.setCouStartNode(oneCou.getStartNode());
                    course.setCouEndNode(oneCou.getEndNode());
                    course.setCouColor(oneCou.getColor());
                    course.setCouWeek(oneCou.getDayOfWeek());
                    course.setOnlyID(oneCou.getOnlyID());
                    course.setCouID(oneCou.getCouID());
                    course.setCouWeekType(oneCou.getCourseType());
                    // 用于撞课的当前周判断
                    // 把起始周都赋值为当前周
                    course.setCouStartWeek(mCurrentIndex);
                    course.setCouEndWeek(mCurrentIndex);
                    addCouList.add(course);
                }
            }

        } else {
            // 如果不存在周课表就要筛选需要显示的完整课表
            for (Course cou : courses) {
                if (isActiveStatus(cou)) {
                    addCouList.add(cou);
                }
            }
        }
        // 添加课程
        courses = addCouList;
        for (Course cou : courses) {
            addCourse(cou);
        }

    }

    /**
     * 根据课程 id 和 起止节 查找冲突的课程
     *
     * @param cou
     * @return
     */
    private List<Course> findConflictCourse(Course cou) {
        // 上面已经处理好了 这里courses 就是单前周应该添加的所有课程（不管是周课表还是完整课表）
        List<Course> conflictCouList = new ArrayList<>();
        for (Course findCou : courses) {
            // 课不相同 周相同
            if (!Objects.equals(findCou.getCouID(), cou.getCouID())) {
                // 星期相同 起始结束节有碰到就为冲突
                if (Objects.equals(findCou.getCouWeek(), cou.getCouWeek())
                        && (Objects.equals(findCou.getCouStartNode(), cou.getCouStartNode()) ||
                        Objects.equals(findCou.getCouEndNode(), cou.getCouEndNode()))) {
                    conflictCouList.add(findCou);
                }
            }
        }
        return conflictCouList;
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

    interface OnItemClickListener {
        void onClick(Course cou);
    }

    public OnItemClickListener getItemClickListener() {
        return mItemClickListener;
    }

    public void setItemClickListener(OnItemClickListener itemClickListener) {
        mItemClickListener = itemClickListener;
    }
}
