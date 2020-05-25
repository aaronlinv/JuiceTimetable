package com.juice.timetable.ui.course;

import android.animation.ArgbEvaluator;
import android.animation.ObjectAnimator;
import android.annotation.SuppressLint;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;
import androidx.viewpager2.widget.ViewPager2;

import com.juice.timetable.R;
import com.juice.timetable.app.Constant;
import com.juice.timetable.data.bean.Course;
import com.juice.timetable.data.bean.CourseViewBean;
import com.juice.timetable.data.bean.MyCheckIn;
import com.juice.timetable.data.bean.OneWeekCourse;
import com.juice.timetable.data.bean.StuInfo;
import com.juice.timetable.data.http.EduInfo;
import com.juice.timetable.data.http.LeaveInfo;
import com.juice.timetable.data.parse.ParseAllWeek;
import com.juice.timetable.data.parse.ParseCheckIn;
import com.juice.timetable.data.parse.ParseOneWeek;
import com.juice.timetable.data.viewmodel.AllWeekCourseViewModel;
import com.juice.timetable.data.viewmodel.OneWeekCourseViewModel;
import com.juice.timetable.data.viewmodel.StuInfoViewModel;
import com.juice.timetable.databinding.FragmentCourseBinding;
import com.juice.timetable.utils.LogUtils;
import com.juice.timetable.utils.Utils;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

import static android.animation.ObjectAnimator.ofObject;

public class CourseFragment extends Fragment {
    private FragmentCourseBinding binding;
    private Toolbar toolbar;
    private Handler mHandler;
    private ViewPager2 mVpCourse;
    private AllWeekCourseViewModel mAllWeekCourseViewModel;
    private OneWeekCourseViewModel mOneWeekCourseViewModel;
    private StuInfoViewModel mStuInfoViewModel;
    private SwipeRefreshLayout mSlRefresh;
    private TextView mTvCheckIn;
    private CourseViewListAdapter mCourseViewListAdapter;
    private List<CourseViewBean> mCourseViewBeanList = new ArrayList<>();
    private int mCurViewPagerNum;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentCourseBinding.inflate(getLayoutInflater());
        mVpCourse = binding.vpCourse;
        mSlRefresh = binding.slRefresh;
        mTvCheckIn = binding.tvCheckIn;

        mAllWeekCourseViewModel = new ViewModelProvider(requireActivity()).get(AllWeekCourseViewModel.class);
        mOneWeekCourseViewModel = new ViewModelProvider(requireActivity()).get(OneWeekCourseViewModel.class);
        mStuInfoViewModel = new ViewModelProvider(requireActivity()).get(StuInfoViewModel.class);
        List<Course> allWeekCourse = mAllWeekCourseViewModel.getAllWeekCourse();
        LogUtils.getInstance().d("mAllWeekCourseViewModel.getAllWeekCourse() -- > " + allWeekCourse);

        initCurrentWeek();
        initView();
        initCourse();
        return binding.getRoot();
    }

    @SuppressLint("HandlerLeak")
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        handler();

        // 首次登录，获取数据并刷新界面
        if (Constant.FIRST_LOGIN) {
            // 刷新动画
            mSlRefresh.setRefreshing(true);
            refreshData();
            // 设置首次登录为false
            Constant.FIRST_LOGIN = false;
        }

        getCheckIn();
        initEvent();
    }

    private void initEvent() {
        // 下拉菜单 获取点击的周
        toolbar.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                int week = item.getItemId() + 1;
                LogUtils.getInstance().d("MenuItem <" + week + "> onMenuItemClick");

                if (week != Constant.CUR_WEEK) {
                    toolbar.setTitle("第" + week + "周 (非本周)");
                } else {
                    toolbar.setTitle("第" + Constant.CUR_WEEK + "周");
                }

                mVpCourse.setCurrentItem(item.getItemId(), true);

                return false;
            }
        });

        // 下拉刷新监听
        mSlRefresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                refreshData();

            }
        });

        // 翻页显示 当前周
        mVpCourse.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
            @Override
            public void onPageSelected(int position) {
                mCurViewPagerNum = position;
                super.onPageSelected(position);
                int week = position + 1;
                if (week != Constant.CUR_WEEK) {
                    toolbar.setTitle("第" + week + "周 (非本周)");
                } else {
                    toolbar.setTitle("第" + Constant.CUR_WEEK + "周");
                }
            }
        });

    }

    /**
     * 初始化课程数据
     */
    private void initCourse() {
        mCourseViewListAdapter = new CourseViewListAdapter();
        mCourseViewListAdapter.submitList(mCourseViewBeanList);
        updateCourse();
        mVpCourse.setAdapter(mCourseViewListAdapter);
        // 打开主页 跳转当前周
        mVpCourse.setCurrentItem(Constant.CUR_WEEK - 1, false);
    }

    /**
     * 读取数据库 完整课表和周课表 更新到适配器
     */
    private void updateCourse() {
        List<Course> allWeekCourse = mAllWeekCourseViewModel.getAllWeekCourse();
        List<OneWeekCourse> oneWeekCourse = mOneWeekCourseViewModel.getOneWeekCourse();
        List<CourseViewBean> tempList = new ArrayList<>();
        for (int i = 1; i <= 25; i++) {
            CourseViewBean courseViewBean = new CourseViewBean();
            courseViewBean.setAllWeekCourse(allWeekCourse);
            courseViewBean.setCurrentIndex(i);
            courseViewBean.setOneWeekCourse(oneWeekCourse);
//            courseViewBean.setWeekSet(weekSet);
            courseViewBean.setWeekSet(new HashSet<Integer>());
            tempList.add(courseViewBean);
            LogUtils.getInstance().d("mCourseViewBeanList size -- > " + mCourseViewBeanList.size());
        }
        // 先清空原有的数据 再写入新数据
        mCourseViewBeanList.clear();
        mCourseViewBeanList.addAll(tempList);

        // 原来的思路是每次从数据库获取 到新的BeanList 直接submitList
        // mCourseViewListAdapter.submitList(mCourseViewBeanList);
        // 这样带来的问题，每次下拉刷新，ViewPager都会强制跳到第一页，无论怎么etCurrentItem

        // 通知数据已经修改
        mCourseViewListAdapter.notifyDataSetChanged();
        // 跳到刷新之前所在的周，不然会跳转到第一周
        mVpCourse.setCurrentItem(mCurViewPagerNum, false);
    }

    /**
     * 初始化当前周
     */
    private void initCurrentWeek() {
        Constant.CUR_WEEK = Utils.getCurrentWeek();
        mCurViewPagerNum = Constant.CUR_WEEK - 1;
    }

    /**
     * 初始化界面
     */
    private void initView() {
        toolbar = requireActivity().findViewById(R.id.toolbar);

        // 显示Toolbar的下拉菜单按钮
        Toolbar toolbar = requireActivity().findViewById(R.id.toolbar);
        Menu menu = toolbar.getMenu();
        menu.setGroupVisible(0, true);

        // 初始化标题栏
        toolbar.setTitle("第" + Constant.CUR_WEEK + "周");

        // 不在签到时间并且不在调试模式 隐藏签到提示栏
        if (!Utils.isCheckInTime() && !Constant.DEBUG_CHECK_IN_TEXTVIEW) {
            mTvCheckIn.setVisibility(TextView.GONE);
        }
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
                String allCourse = null;
                StuInfo stuInfo = mStuInfoViewModel.selectStuInfo();
                try {
                    allCourse = EduInfo.getTimeTable(stuInfo.getStuID().toString(), stuInfo.getEduPassword(), Constant.URI_WHOLE_COURSE, requireContext());
                } catch (Exception e) {
                    LogUtils.getInstance().d("setOnRefreshListener：" + e.getMessage());
                    // 可能密码错误
                    message.obj = e.getMessage();
                }
                LogUtils.getInstance().d("setOnRefreshListener:模拟登录获取完整课表结束");
                if (allCourse == null) {
//                    message.obj = "网络好像不太好，再试一次";
                    mHandler.sendMessage(message);

                } else {
                    List<Course> courses = ParseAllWeek.parseAllCourse(allCourse);
                    LogUtils.getInstance().d("setOnRefreshListener:解析完整课表结束");
                    if (courses.isEmpty()) {
                        message.obj = "解析完整课表失败";
                        mHandler.sendMessage(message);
                        return;
                    }
                    // 先删除数据库 完整课表
                    mAllWeekCourseViewModel.deleteAllWeekCourse();
                    // 加载完整课表填充颜色
                    for (Course cours : courses) {
                        if (cours.getCouColor() == null) {
                            // 这里的courses是模拟登录获取的，所有color为null，所以每次都刷新颜色
                            cours.setCouColor(Utils.getColor(cours.getCouID().intValue()));
                        }
                        // 填充完颜色将课程写入数据库
                        mAllWeekCourseViewModel.insertAllWeekCourse(cours);
                    }


                    try {
                        // 传入完整课表 用来匹配颜色和课程信息
                        getOneWeekCou(courses);
                    } catch (Exception e) {
                        message.obj = e.getMessage();
                    }
                    LogUtils.getInstance().d("setOnRefreshListener:获取完整课表和周课表 写入数据库结束");
                    message.obj = "ok";
                    mHandler.sendMessage(message);
                }
            }
        }.start();
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
                            mSlRefresh.setRefreshing(false);
                        } else {
                            updateCourse();
                            Toast.makeText(requireActivity(), "课表刷新成功", Toast.LENGTH_SHORT).show();
                            mSlRefresh.setRefreshing(false);

                        }
                        break;
                    case Constant.MSG_CHECK_IN_SUCCESS:
                        String checkInTime = (String) msg.obj;
                        final String checkInStr = "今天 " + checkInTime + " 已签到";

//                        mTvCheckIn.setBackgroundColor(0xFFe6e6e6);
                        ObjectAnimator backgroundColor = ofObject(mTvCheckIn, "backgroundColor", new ArgbEvaluator(), 0xFFec6b6b, 0xFFe6e6e6);
                        backgroundColor.setDuration(1000);
                        backgroundColor.start();
                        ObjectAnimator textColor = ofObject(mTvCheckIn, "textColor", new ArgbEvaluator(), 0xFFFFFFFF, 0xFF101010);
                        textColor.setDuration(1000);
                        textColor.start();

                        mHandler.postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                mTvCheckIn.setText(checkInStr);
                            }
                        }, 500);
                        break;
                    case Constant.STOP_REFRESH:
                        mSlRefresh.setRefreshing(false);
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
                StuInfo stuInfo = mStuInfoViewModel.selectStuInfo();
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
     *
     * @param allWeekCourse
     */
    private void getOneWeekCou(List<Course> allWeekCourse) throws Exception {
        // 获取用户数据
        StuInfo stuInfo = mStuInfoViewModel.selectStuInfo();
        // 解析的周课表的List
        ArrayList<OneWeekCourse> couList = new ArrayList<>();
        // 要获取的周课表，0为当前周
        int week = 0;
        // 要删除数据库中的周
        ArrayList<Integer> delList = new ArrayList<>();

        // 获取数据库中存了哪些周的周课表
        List<Integer> inWeek = mOneWeekCourseViewModel.getWeek();
        HashSet<Integer> set = new HashSet<>(inWeek);
        LogUtils.getInstance().d("数据库周课表已存在的周 -- > " + set);
        // 数据库为空 需要爬取上两周课程
        if (set.isEmpty()) {
            week = -2;
        }
        // 模拟登录获取课表数据
        for (; week <= 2; week++) {
            String oneWeekCourse = EduInfo.getTimeTable(stuInfo.getStuID().toString(), stuInfo.getEduPassword(), Constant.URI_ONE_WEEK + (Constant.CUR_WEEK + week), requireContext());
            List<OneWeekCourse> oneWeekCourses = ParseOneWeek.parseCourse(oneWeekCourse);
            LogUtils.getInstance().d("获取第 <" + (Constant.CUR_WEEK + week) + "> 周课表 -- > " + oneWeekCourses);
            couList.addAll(oneWeekCourses);
            // 删除该数据库中 单前周和后两周的课表，避免冲突
            if (week >= 0) {
                delList.add((Constant.CUR_WEEK + week));
            }
        }

        // 执行删除
        mOneWeekCourseViewModel.deleteWeek(delList);
        LogUtils.getInstance().d("删除周课表的周 -- > " + delList);

        // 颜色的随机数(从完整课表总课程数开始)
        int colorNum = allWeekCourse.size();
        // 周课表课程存在完整课表中，就赋值上完整课表id
        for (OneWeekCourse oneWeekCourse : couList) {
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
                } else {
                    // 没有找到 可能是一些考试的显示
                    // 取当前的完整课表的课数目为随机数
                    oneWeekCourse.setColor(Utils.getColor(colorNum++));

                }
            }
            // 插入数据库
            mOneWeekCourseViewModel.insertOneWeekCourse(oneWeekCourse);
        }
        LogUtils.getInstance().d("解析本周、上两周、下两周的周课表 结束");
    }
}
