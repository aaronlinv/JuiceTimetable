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

import com.juice.timetable.R;
import com.juice.timetable.app.Constant;
import com.juice.timetable.data.JuiceDatabase;
import com.juice.timetable.data.bean.Course;
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
    private Integer mCurrentMonth = 4;

    private TextView mMonthTextView;

    private Handler mHandler;
    private JuiceDatabase database;
    private AllWeekCourseDao allWeekCourseDao;
    private OneWeekCourseDao oneWeekCourseDao;
    private StuInfoDao stuInfoDao;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        // dataBinding 用viewBinding的方式初始化也没问题
        binding = FragmentCourseBinding.inflate(getLayoutInflater());
//        homeViewModel = new ViewModelProvider(this).get(CourseViewModel.class);

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
        return binding.getRoot();
    }

    @SuppressLint("HandlerLeak")
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        // 进入首次登录界面
        if (Constant.DEBUG_INIT_FRAGMENT) {
            Navigation.findNavController(requireView()).navigate(R.id.action_nav_course_to_initFragment);
//            NavHostFragment.findNavController(this).navigate(R.id.action_initFragment_to_nav_course);

        }

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
/*        List<Course> courses = testCourseData.getCourses();
        JuiceDatabase juiceDatabase = JuiceDatabase.getDatabase(getContext().getApplicationContext());
        //生成对应的Dao
        AllWeekCourseDao allWeekCourseDao = juiceDatabase.getAllWeekCourseDao();
        allWeekCourseDao.deleteAllWeekCourse();
        for (Course cours : courses) {
            allWeekCourseDao.insertAllWeekCourse(cours);

        }*/

        // 初始化数据库和Dao

        database = JuiceDatabase.getDatabase(requireContext().getApplicationContext());
        allWeekCourseDao = database.getAllWeekCourseDao();
        oneWeekCourseDao = database.getOneWeekCourseDao();
        stuInfoDao = database.getStuInfoDao();
        if (Constant.DEBUG_MODE) {
            // 注入自己的账号密码，用于免登录调式
            final UserInfoUtils userInfoUtils = UserInfoUtils.getINSTANT(requireContext());
            stuInfoDao.deleteStuInfo();
            StuInfo stuInfo = new StuInfo();
            stuInfo.setStuID(Integer.valueOf(userInfoUtils.getID()));
            stuInfo.setEduPassword(userInfoUtils.getEduPasswd());
            stuInfo.setLeavePassword(userInfoUtils.getLeavePasswd());
            stuInfoDao.insertStuInfo(stuInfo);
        }


        // 加载课表
        // TODO: 2020/5/6 首次登录课表要做初始化
        new Thread(
                new Runnable() {
                    @Override
                    public void run() {
                        List<Course> allWeekCourse = allWeekCourseDao.getAllWeekCourse();
                        List<OneWeekCourse> oneWeekCourse = oneWeekCourseDao.getOneWeekCourse();
                        List<Integer> inWeek = oneWeekCourseDao.getInWeek();
                        binding.courseView.setCourses(allWeekCourse);
                        binding.courseView.setOneWeekCourses(oneWeekCourse);
                        binding.courseView.setSet(new HashSet<Integer>(inWeek));
                        binding.courseView.resetView();

                        LogUtils.getInstance().d("用户数据库信息：" + stuInfoDao.getStuInfo());
                        boolean hasLeavePwd = (stuInfoDao.getStuInfo().getEduPassword() != null);
                        // (签到时间或者调试模式)且数据库有请假系统密码  初始化签到信息
                        LogUtils.getInstance().d("有请假系统密码则开始获取签到信息");
                        if ((Utils.isCheckInTime() || Constant.DEBUG_CHECK_IN_TEXTVIEW) && hasLeavePwd) {
                            try {
                                String checkIn = LeaveInfo.getCheckIn(requireContext());
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
                }
        ).start();




/*        // 传入课表List 以显示
        final AllWeekCourseViewModel allWeekCou = new ViewModelProvider(requireActivity()).get(AllWeekCourseViewModel.class);
        allWeekCou.getAllWeekCourseLive().observe(getActivity(), new Observer<List<Course>>() {
            @Override
            public void onChanged(List<Course> courses) {
                //  一直观察，可能造成卡顿，检查数据发生了改变再改变
                if (courses != null && courses != binding.courseView.getCourses()) {
                    binding.courseView.setCourses(courses);
                    // 刷新结束后 resetView更好 这样颜色变动不会闪烁
//                    binding.courseView.resetView();
                }
            }
        });*/

/*
        final OneWeekCourseDao oneWeekCourseDao = database.getOneWeekCourseDao();
        oneWeekCourseDao.getOneWeekCourseLive().observe(getActivity(), new Observer<List<OneWeekCourse>>() {
            @Override
            public void onChanged(List<OneWeekCourse> oneWeekCourses) {
                if (oneWeekCourses != null && oneWeekCourses != binding.courseView.getOneWeekCourses()) {
                    binding.courseView.setOneWeekCourses(oneWeekCourses);
                    binding.courseView.resetView();
                }
            }
        });*/

/*        oneWeekCourseDao.getInWeekLive().observe(getActivity(), new Observer<List<Integer>>() {
            @Override
            public void onChanged(List<Integer> integers) {
                if (integers != null) {
                    HashSet<Integer> set = new HashSet<>(integers);
                    if (set != binding.courseView.getSet()) {
                        LogUtils.getInstance().d("获取数据库中 存在的周课表的周Set" + set);
                        binding.courseView.setSet(set);
                    }
                }
            }
        });*/


        binding.slRefresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                new Thread() {
                    @Override
                    public void run() {
                        super.run();
                        Message message = new Message();
                        message.what = Constant.MSG_REFRESH;
                        // 关闭刷新动画
                        binding.slRefresh.setRefreshing(false);

                        LogUtils.getInstance().d("setOnRefreshListener:开始刷新");
                        // 更新数据
                        // TODO: 2020/5/6
//                        allWeekCourseDao.deleteAllWeekCourse();
                        LogUtils.getInstance().d("setOnRefreshListener:删除数据库");
                        String allCourse = null;
                        try {
                            allCourse = EduInfo.getAllCourse(requireContext().getApplicationContext());
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
                            // 首次登录，完整课表为空
                            List<Course> allWeekCourse = allWeekCourseDao.getAllWeekCourse();
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
                                // 将课程置入课表界面
                                binding.courseView.setCourses(courses);

                            } else {
                                // TODO: 2020/5/6  非初次登录，更新数据没有写
                                // 将课程置入课表界面
                                binding.courseView.setCourses(allWeekCourse);
                            }

                            /*LogUtils.getInstance().d("查看 插入后的数据库情况");
                            LogUtils.getInstance().d(allWeekCourseDao.getAllWeekCourse().toString());*/


                            LogUtils.getInstance().d("setOnRefreshListener:完整课写入数据库表结束");
                            try {
                                getOneWeekCou();
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                            // 数据库有哪些周的周课表
                            List<Integer> inWeek = oneWeekCourseDao.getInWeek();
                            HashSet<Integer> weekSet = new HashSet<>(inWeek);
                            binding.courseView.setSet(weekSet);
                            // 设置周课表List
                            List<OneWeekCourse> oneWeekCourse = oneWeekCourseDao.getOneWeekCourse();
                            binding.courseView.setOneWeekCourses(oneWeekCourse);

                            LogUtils.getInstance().d("setOnRefreshListener:周课表写入数据库表结束");
                            message.obj = "ok";
                            mHandler.sendMessage(message);
                        }
                    }
                }.start();

            }
        });

        mHandler = new Handler() {
            @Override
            public void handleMessage(@NonNull Message msg) {
                super.handleMessage(msg);
                switch (msg.what) {
                    case Constant.MSG_REFRESH:
                        String msgStr = (String) msg.obj;
                        if (!"ok".equals(msgStr)) {
                            Toast.makeText(requireContext(), msgStr, Toast.LENGTH_SHORT).show();
                        } else {
                            Toast.makeText(requireContext(), "课表刷新成功", Toast.LENGTH_SHORT).show();

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
//                        ObjectAnimator text = ObjectAnimator.ofObject(binding.tvCheckIn, "text", new ArgbEvaluator(), "今日未签到", checkInStr);
//                        text.setDuration(5000);
//                        text.start();
//                        binding.tvCheckIn.setTextColor(0xFF101010);
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
                }

            }
        }

        ;
/*        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    getOneWeekCou();
                    List<Integer> inWeek = oneWeekCourseDao.getInWeek();
                    LogUtils.getInstance().d("查询数据库周：" + inWeek);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }).start();*/

    }

    /**
     * 解析课表 获取本周、上两周、下两周的周课表
     */
    private void getOneWeekCou() throws Exception {
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
            String oneWeekCourse = EduInfo.getOneWeekCourse(Constant.CUR_WEEK + week, requireContext());
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
            for (Course cou : binding.courseView.getCourses()) {
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
