package com.juice.timetable.ui.course;

import android.animation.ArgbEvaluator;
import android.animation.ObjectAnimator;
import android.annotation.SuppressLint;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;
import androidx.viewpager2.widget.ViewPager2;

import com.juice.timetable.R;
import com.juice.timetable.app.Constant;
import com.juice.timetable.data.JuiceDatabase;
import com.juice.timetable.data.bean.Course;
import com.juice.timetable.data.bean.CourseViewBean;
import com.juice.timetable.data.bean.MyCheckIn;
import com.juice.timetable.data.bean.OneWeekCourse;
import com.juice.timetable.data.bean.StuInfo;
import com.juice.timetable.data.dao.AllWeekCourseDao;
import com.juice.timetable.data.dao.OneWeekCourseDao;
import com.juice.timetable.data.dao.StuInfoDao;
import com.juice.timetable.data.http.EduInfo;
import com.juice.timetable.data.http.LeaveInfo;
import com.juice.timetable.data.parse.ParseAllWeek;
import com.juice.timetable.data.parse.ParseCheckIn;
import com.juice.timetable.data.parse.ParseOneWeek;
import com.juice.timetable.databinding.FragmentCourseBinding;
import com.juice.timetable.utils.LogUtils;
import com.juice.timetable.utils.UserInfoUtils;
import com.juice.timetable.utils.Utils;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

import static android.animation.ObjectAnimator.ofObject;

public class CourseFragment extends Fragment {
    private CourseViewModel homeViewModel;
    private FragmentCourseBinding binding;
    private Toolbar toolbar;
    private int WEEK_TEXT_SIZE = 12;
    private int NODE_TEXT_SIZE = 11;
    private int NODE_WIDTH = 28;
    private Integer mCurrentMonth = 5;

    private TextView mMonthTextView;

    private Handler mHandler;
    private JuiceDatabase database;
    private AllWeekCourseDao allWeekCourseDao;
    private OneWeekCourseDao oneWeekCourseDao;
    private StuInfoDao stuInfoDao;
    private List<Course> allWeekCourse;
    private HashSet<Integer> weekSet;
    private List<OneWeekCourse> oneWeekCourse;
    private ViewPager2 mVpCourse;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        // dataBinding 用viewBinding的方式初始化也没问题
        binding = FragmentCourseBinding.inflate(getLayoutInflater());
        mVpCourse = binding.vpCourse;

        initCurrentWeek();
        init();
        initDatabase();
        initCourse();
        return binding.getRoot();
    }

    private void initCourse() {
        CourseViewListAdapter courseViewListAdapter = new CourseViewListAdapter();
        List<CourseViewBean> courseViewBeanList = new ArrayList<>();
        List<Course> allWeekCourse = allWeekCourseDao.getAllWeekCourse();
        List<OneWeekCourse> oneWeekCourse = oneWeekCourseDao.getOneWeekCourse();
        for (int i = 1; i <= 25; i++) {
            CourseViewBean courseViewBean = new CourseViewBean();
            courseViewBean.setAllWeekCourse(allWeekCourse);
            courseViewBean.setCurrentIndex(i);
            courseViewBean.setOneWeekCourse(oneWeekCourse);
//            courseViewBean.setWeekSet(weekSet);
            courseViewBean.setWeekSet(new HashSet<Integer>());

            courseViewBeanList.add(courseViewBean);
        }
        courseViewListAdapter.submitList(courseViewBeanList);
        mVpCourse.setAdapter(courseViewListAdapter);
    }

    /**
     * 初始化当前周
     */
    private void initCurrentWeek() {

        Constant.CUR_WEEK = Utils.getCurrentWeek();
        // 也要给CourseView也设置上
        binding.courseView.setCurrentIndex(Constant.CUR_WEEK);
    }

    @SuppressLint("HandlerLeak")
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        // 调式模式：注入自己的账号密码，用于免登录调式
        if (Constant.DEBUG_MODE) {
            final UserInfoUtils userInfoUtils = UserInfoUtils.getINSTANT(requireContext());
            stuInfoDao.deleteStuInfo();
            StuInfo stuInfo = new StuInfo();
            stuInfo.setStuID(Integer.valueOf(userInfoUtils.getID()));
            stuInfo.setEduPassword(userInfoUtils.getEduPasswd());
            stuInfo.setLeavePassword(userInfoUtils.getLeavePasswd());
            stuInfoDao.insertStuInfo(stuInfo);
            LogUtils.getInstance().d("调试模式：注入学号密码结束");
        }

        StuInfo stu = stuInfoDao.getStuInfo();
        // 在调试模式 或者是数据库中没有用户数据  进入首次登录界面
        if (Constant.DEBUG_INIT_FRAGMENT) {
            Navigation.findNavController(requireView()).navigate(R.id.action_nav_course_to_initFragment);
        } else {
            handler();
            menuListener();
            refreshListener();


            // 首次登录，获取数据并刷新界面
            if (Constant.FIRST_LOGIN) {
                // 刷新动画
                // 通过调用控件的引用调用post方法，在run方法中更新ui界面
                binding.slRefresh.post(new Runnable() {
                    @Override
                    public void run() {
                        binding.slRefresh.setRefreshing(true);
                    }
                });
                refreshData();
                // 设置首次登录为false
                Constant.FIRST_LOGIN = false;
            } else {
                initTimetable();
            }

            getCheckIn();
        }

    }


    /**
     * 初始化界面
     */
    private void init() {
        // 星期栏
        // 清除所有View
        binding.llWeek.removeAllViews();
        toolbar = requireActivity().findViewById(R.id.toolbar);

        // 显示Toolbar的下拉菜单按钮
        Toolbar toolbar = requireActivity().findViewById(R.id.toolbar);
        Menu menu = toolbar.getMenu();
        menu.setGroupVisible(0, true);

        // -1 ：星期栏   0-6：星期 一 ...日
        for (int i = -1; i < 7; i++) {
            TextView textView = new TextView(requireContext().getApplicationContext());
            textView.setGravity(Gravity.CENTER);
            textView.setTextColor(Color.GRAY);
            textView.setWidth(0);
            LinearLayout.LayoutParams params;

            if (i == -1) {
                // 初始化月份
                params = new LinearLayout.LayoutParams(Utils.dip2px(requireContext().getApplicationContext(), NODE_WIDTH),
                        ViewGroup.LayoutParams.MATCH_PARENT);

                textView.setTextSize(NODE_TEXT_SIZE);
                textView.setText(mCurrentMonth + "\n月");

                mMonthTextView = textView;
            } else {
                // 初始化课程星期栏
                params = new LinearLayout.LayoutParams(10, ViewGroup.LayoutParams.MATCH_PARENT);
                params.weight = 10;
                textView.setTextSize(WEEK_TEXT_SIZE);
                textView.setText(Constant.WEEK_SINGLE[i]);
                LogUtils.getInstance().d("星期：" + i);
            }
            //添加这个视图
            binding.llWeek.addView(textView, params);
        }

        //  课程节数栏
        int nodeItemHeight = Utils.dip2px(requireContext().getApplicationContext(), 55);
        for (int i = 1; i <= 11; i++) {
            TextView textView = new TextView(requireContext().getApplicationContext());
            textView.setTextSize(NODE_TEXT_SIZE);
            textView.setGravity(Gravity.CENTER);
            textView.setTextColor(Color.GRAY);
            textView.setText(String.valueOf(i));

            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                    ViewGroup.LayoutParams.MATCH_PARENT, nodeItemHeight);
            binding.llNode.addView(textView, params);
        }
        // 初始化标题栏
        toolbar.setTitle("第" + Constant.CUR_WEEK + "周");

        // 不在签到时间并且不在调试模式 隐藏签到提示栏
        if (!Utils.isCheckInTime() && !Constant.DEBUG_CHECK_IN_TEXTVIEW) {
            binding.tvCheckIn.setVisibility(TextView.GONE);
        }
    }

    /**
     * 初始化数据库
     */
    private void initDatabase() {
        database = JuiceDatabase.getDatabase(requireContext().getApplicationContext());
        allWeekCourseDao = database.getAllWeekCourseDao();
        oneWeekCourseDao = database.getOneWeekCourseDao();
        stuInfoDao = database.getStuInfoDao();
    }

    /**
     * 下拉菜单监听
     */
    private void menuListener() {
        // 获取点击的周
        toolbar.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {

                LogUtils.getInstance().d("MenuItem <" + item.getItemId() + "> onMenuItemClick");

                if (item.getItemId() != Constant.CUR_WEEK) {

                    toolbar.setTitle("第" + item.getItemId() + "周 (非本周)");
                } else {
                    toolbar.setTitle("第" + Constant.CUR_WEEK + "周");

                }


                binding.courseView.setCurrentIndex(item.getItemId());
                binding.courseView.resetView();
                return false;
            }
        });
    }

    /**
     * 下拉刷新监听
     */
    private void refreshListener() {
        binding.slRefresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                refreshData();

            }
        });
    }

    /**
     * 开始刷新数据，结束刷新动画
     */
    private void refreshData() {
        new Thread() {
            @Override
            public void run() {
                super.run();
                Message message = new Message();
                message.what = Constant.MSG_REFRESH;

                LogUtils.getInstance().d("setOnRefreshListener:开始刷新");
                // 更新数据
                // TODO: 2020/5/6
//                        allWeekCourseDao.deleteAllWeekCourse();
                LogUtils.getInstance().d("setOnRefreshListener:删除数据库");
                String allCourse = null;
                StuInfoDao stuInfoDao = database.getStuInfoDao();
                StuInfo stuInfo = stuInfoDao.getStuInfo();

                try {
                    allCourse = EduInfo.getTimeTable(stuInfo.getStuID().toString(), stuInfo.getEduPassword(), Constant.URI_WHOLE_COURSE, requireContext());
                } catch (Exception e) {
                    LogUtils.getInstance().d("setOnRefreshListener：" + e.getMessage());
                }
                LogUtils.getInstance().d("setOnRefreshListener:模拟登录获取完整课表结束");
                if (allCourse == null) {
                    message.obj = "网络好像不太好，再试一次";
                    mHandler.sendMessage(message);

                } else {
                    List<Course> courses = ParseAllWeek.parseAllCourse(allCourse);
                    // TODO: 2020/5/6 处理获取的完成课表 颜色
                    LogUtils.getInstance().d("setOnRefreshListener:解析完整课表结束");

                    // TODO: 2020/5/6  非初次登录，更新数据没有写
                    // 首次登录，完整课表为空 加载数据填充颜色；数据库不为空，就使用数据库数据
                    allWeekCourse = allWeekCourseDao.getAllWeekCourse();
                    if (allWeekCourse.isEmpty()) {
                        for (Course cours : courses) {
                            if (cours.getCouColor() == null) {

                                // 这里的courses是模拟登录获取的，所有color为null，所以每次都刷新颜色
                                cours.setCouColor(Utils.getColor(cours.getCouID().intValue()));
                            }
                            allWeekCourseDao.insertAllWeekCourse(cours);
                            // 测试单双周显示
/*
                                    if (cours.getCouID() == 1) {
                                        cours.setCouWeekType(1);
                                    }
                                    if (cours.getCouID() == 2) {
                                        cours.setCouWeekType(2);
                                    }
*/
                        }
                        // 填充完颜色将课程装回 allWeekCourse
                        allWeekCourse = courses;
                        LogUtils.getInstance().d("setOnRefreshListener:完整课写入数据库表结束");
                    }
                    try {
                        getOneWeekCou();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    // 数据库有哪些周的周课表
                    List<Integer> inWeek = oneWeekCourseDao.getInWeek();
                    weekSet = new HashSet<>(inWeek);

                    // 设置周课表List
                    oneWeekCourse = oneWeekCourseDao.getOneWeekCourse();

                    LogUtils.getInstance().d("setOnRefreshListener:周课表写入数据库表结束");
                    message.obj = "ok";
                    mHandler.sendMessage(message);
                }
            }
        }.start();
    }

    /**
     * 初始化课表（完整课表和周课表）
     */
    private void initTimetable() {
        new Thread(
                new Runnable() {
                    @Override
                    public void run() {
                        // TODO: 2020/5/8 这里使用成员变量，刷新数据也是用成员变量，可能冲突
                        allWeekCourse = allWeekCourseDao.getAllWeekCourse();
                        oneWeekCourse = oneWeekCourseDao.getOneWeekCourse();
                        List<Integer> inWeek = oneWeekCourseDao.getInWeek();
                        weekSet = new HashSet<Integer>(inWeek);

                        Message loadDataSuccess = new Message();
                        loadDataSuccess.what = Constant.LOAD_DATA_SUCCESS;
                        mHandler.sendMessage(loadDataSuccess);

                    }
                }
        ).start();
    }

    /**
     * Handler接受message
     */
    @SuppressLint("HandlerLeak")
    private void handler() {
        mHandler = new Handler() {
            @Override
            public void handleMessage(@NonNull Message msg) {
                super.handleMessage(msg);
                switch (msg.what) {
                    case Constant.MSG_REFRESH:
                        String msgStr = (String) msg.obj;
                        if (!"ok".equals(msgStr)) {
                            Toast.makeText(getActivity(), msgStr, Toast.LENGTH_SHORT).show();
                            binding.slRefresh.setRefreshing(false);
                        } else {
                            binding.courseView.setCourses(allWeekCourse);
                            binding.courseView.setSet(weekSet);
                            binding.courseView.setOneWeekCourses(oneWeekCourse);

                            Toast.makeText(requireActivity(), "课表刷新成功", Toast.LENGTH_SHORT).show();
                            binding.slRefresh.setRefreshing(false);

                        }
                        binding.courseView.resetView();
                        break;
                    case Constant.MSG_CHECK_IN_SUCCESS:
                        String checkInTime = (String) msg.obj;
                        final String checkInStr = "今天 " + checkInTime + " 已签到";

//                        binding.tvCheckIn.setBackgroundColor(0xFFe6e6e6);
                        ObjectAnimator backgroundColor = ofObject(binding.tvCheckIn, "backgroundColor", new ArgbEvaluator(), 0xFFec6b6b, 0xFFe6e6e6);
                        backgroundColor.setDuration(1000);
                        backgroundColor.start();
                        ObjectAnimator textColor = ofObject(binding.tvCheckIn, "textColor", new ArgbEvaluator(), 0xFFFFFFFF, 0xFF101010);
                        textColor.setDuration(1000);
                        textColor.start();

                        mHandler.postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                binding.tvCheckIn.setText(checkInStr);
                            }
                        }, 500);
                        break;
                    case Constant.STOP_REFRESH:
                        binding.slRefresh.setRefreshing(false);
                        break;

                    case Constant.LOAD_DATA_SUCCESS:
                        binding.courseView.setCourses(allWeekCourse);
                        binding.courseView.setOneWeekCourses(oneWeekCourse);
                        binding.courseView.setSet(weekSet);
                        binding.courseView.resetView();
                        break;
                }

            }
        };
    }


    /**
     * 获取签到情况
     */
    private void getCheckIn() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                StuInfo stuInfo = stuInfoDao.getStuInfo();
                LogUtils.getInstance().d("用户数据库信息：" + stuInfo);
                boolean hasLeavePwd = (stuInfo.getEduPassword() != null);
                // (签到时间或者调试模式)且数据库有请假系统密码  初始化签到信息
                LogUtils.getInstance().d("有请假系统密码则开始获取签到信息");
                if ((Utils.isCheckInTime() || Constant.DEBUG_CHECK_IN_TEXTVIEW) && hasLeavePwd) {
                    try {
                        String checkIn = LeaveInfo.getLeave(stuInfo.getStuID().toString(), stuInfo.getLeavePassword(), Constant.URI_CHECK_IN, requireContext());
                        LogUtils.getInstance().d("签到数据：" + checkIn);

                        MyCheckIn mySigned = ParseCheckIn.getMySigned(checkIn);

                        if (!mySigned.isCheckIn()) {
                            String checkInTime = mySigned.getCheckTime();
                            // TODO: 2020/5/7 需要更换为签到时间
                            checkInTime = "21:50";

                            Message checkInMSG = new Message();
                            checkInMSG.what = Constant.MSG_CHECK_IN_SUCCESS;
                            checkInMSG.obj = checkInTime;
                            mHandler.sendMessage(checkInMSG);
                        }

                    } catch (Exception e) {
                        LogUtils.getInstance().e("获取签到信息失败：" + e.getMessage());
                        e.printStackTrace();
                    }
                }
            }
        }).start();
    }


    /**
     * 解析课表 获取本周、上两周、下两周的周课表
     */
    private void getOneWeekCou() throws Exception {
        // 获取用户数据
        StuInfoDao stuInfoDao = database.getStuInfoDao();
        StuInfo stuInfo = stuInfoDao.getStuInfo();
        // 解析的周课表的List
        ArrayList<OneWeekCourse> couList = new ArrayList<>();
        // 要获取的周课表，0为当前周
        int week = 0;
        // 要删除数据库中的周
        ArrayList<Integer> delList = new ArrayList<>();
// TODO: 2020/5/6 删除数据库
//        oneWeekCourseDao.deleteCourse();
        List<Integer> inWeek = oneWeekCourseDao.getInWeek();
        // 获取数据库中存了哪些周的周课表
        HashSet<Integer> set = new HashSet<>(inWeek);
        LogUtils.getInstance().d("周课表set:" + set);
        // 数据库为空 需要爬取上两周课程
        if (set.isEmpty()) {
            week = -2;
        }
        // 模拟登录获取课表数据
        for (; week <= 2; week++) {
            String oneWeekCourse = EduInfo.getTimeTable(stuInfo.getStuID().toString(), stuInfo.getEduPassword(), Constant.URI_ONE_WEEK + (Constant.CUR_WEEK + week), requireContext());
            List<OneWeekCourse> oneWeekCourses = ParseOneWeek.parseCourse(oneWeekCourse);
            LogUtils.getInstance().d("获取第 <" + (Constant.CUR_WEEK + week) + "> 周课表");
            LogUtils.getInstance().d("获取第 <" + (Constant.CUR_WEEK + week) + "> 周课表 获取前List:" + oneWeekCourses);
            for (OneWeekCourse oneWeekCours : oneWeekCourses) {
                LogUtils.getInstance().d("获取第 <" + (Constant.CUR_WEEK + week) + "> 周课表:" + oneWeekCours);
            }
            couList.addAll(oneWeekCourses);
            // 删除该数据库中 但前周和后两周的课表，避免冲突
            if (week >= 0) {
                delList.add((Constant.CUR_WEEK + week));
            }
        }

        // 执行删除
        oneWeekCourseDao.deleteCourseByWeek(delList);
        LogUtils.getInstance().d("删除周：" + delList);
        List<OneWeekCourse> oneWeekCourse1 = oneWeekCourseDao.getOneWeekCourse();
/*        for (OneWeekCourse oneWeekCourse : oneWeekCourse1) {
            LogUtils.getInstance().d("数据库删除后的情况：" + oneWeekCourse.toString());
        }*/
        // 颜色的随机数
        int colorNum = 0;
        for (OneWeekCourse oneWeekCourse : couList) {
//            LogUtils.getInstance().d("整理后的周课表插入数据库" + oneWeekCourse.toString());

            // 周课表课程存在完整课表中，就赋值上完整课表id
            for (Course cou : allWeekCourse) {
                // 去除空格
                String wholeCouName = cou.getCouName().replace(" ", "");
                String oneCouName = oneWeekCourse.getCouName().replace(" ", "");
                if (wholeCouName.equals(oneCouName)) {
                    // 设置上课程id和颜色
                    oneWeekCourse.setCouID(cou.getCouID());
                    oneWeekCourse.setColor(cou.getCouColor());
                    LogUtils.getInstance().d("在完整课表中找到了该课程并修改：" + oneWeekCourse);
                    break;
                }
                // 没有找到 可能是一些考试的显示
                // 取当前的完整课表的课数目为随机数
                oneWeekCourse.setColor(Utils.getColor(binding.courseView.getCourses().size() + colorNum));
                colorNum++;
            }
            // 插入数据库
            oneWeekCourseDao.insertCourse(oneWeekCourse);
        }
        LogUtils.getInstance().d("解析本周、上两周、下两周的周课表 结束");
    }
}
