package com.juice.timetable.ui.course;

import android.annotation.SuppressLint;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.Gravity;
import android.view.LayoutInflater;
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
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.juice.timetable.R;
import com.juice.timetable.app.Constant;
import com.juice.timetable.data.Dao.AllWeekCourseDao;
import com.juice.timetable.data.JuiceDatabase;
import com.juice.timetable.data.ViewModel.AllWeekCourseViewModel;
import com.juice.timetable.data.bean.Course;
import com.juice.timetable.data.http.EduInfo;
import com.juice.timetable.data.parse.ParseAllWeek;
import com.juice.timetable.databinding.FragmentCourseBinding;
import com.juice.timetable.utils.LogUtils;
import com.juice.timetable.utils.Utils;

import java.util.List;

public class CourseFragment extends Fragment {
    private CourseViewModel homeViewModel;
    private FragmentCourseBinding binding;
    private Toolbar toolbar;
    private int WEEK_TEXT_SIZE = 12;
    private int NODE_TEXT_SIZE = 11;
    private int NODE_WIDTH = 28;
    private Integer mCurrentMonth = 4;
    private Integer mCurrentWeek = 11;

    private TextView mMonthTextView;

    private Handler mHandler;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        /*homeViewModel =
                new ViewModelProvider(this).get(HomeViewModel.class);
        View root = inflater.inflate(R.layout.fragment_course, container, false);
        final TextView textView = root.findViewById(R.id.text_home);
        homeViewModel.getText().observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
                textView.setText(s);
            }
        });
        return root;*/
        // dataBinding 用viewBinding的方式初始化也没问题
        binding = FragmentCourseBinding.inflate(getLayoutInflater());
//        homeViewModel = new ViewModelProvider(this).get(CourseViewModel.class);

        // 星期栏
        // 清除所有View
        binding.llWeek.removeAllViews();
        toolbar = requireActivity().findViewById(R.id.toolbar);


        // -1 ：星期栏   0-6：星期 一 ...日
        for (int i = -1; i < 7; i++) {
            TextView textView = new TextView(requireContext().getApplicationContext());
            textView.setGravity(Gravity.CENTER);
            textView.setTextColor(0xFF4B0082);
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
        toolbar.setTitle("第" + mCurrentWeek + "周");

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
/*        new Runnable() {
            @Override
            public void run() {
                // 通过外部数据 尝试添加课程
                // 未成功
                for (int x = 4; x <= 7; x++) {
                    for (int y = 1; y <= 10; y = y + 2) {
                        binding.courseView.addCourse(x, y);
                    }
                    LogUtils.getInstance().d("binding.courseView");

                }
            }
        };*/

        // 获取点击的周
        toolbar.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {

                LogUtils.getInstance().d("MenuItem <" + item.getItemId() + "> onMenuItemClick");

                if (item.getItemId() != mCurrentWeek) {

                    toolbar.setTitle("第" + item.getItemId() + "周 (非本周)");
                } else {
                    toolbar.setTitle("第" + mCurrentWeek + "周");

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
        // TODO: 2020/5/5 测试：手动写入账户密码
/*        UserInfoUtils userInfoUtils = UserInfoUtils.getINSTANT(requireContext());
        JuiceDatabase database = JuiceDatabase.getDatabase(requireContext());
        database.getStuInfoDao().deleteStuInfo();
        StuInfo stuInfo = new StuInfo();
        stuInfo.setStuID(Integer.valueOf(userInfoUtils.getID()));
        stuInfo.setEduPassword(userInfoUtils.getEduPasswd());
        database.getStuInfoDao().insertStuInfo(stuInfo);*/


        // 传入课表List 以显示
        final AllWeekCourseViewModel allWeekCou = new ViewModelProvider(requireActivity()).get(AllWeekCourseViewModel.class);
        allWeekCou.getAllWeekCourseLive().observe(getActivity(), new Observer<List<Course>>() {
            @Override
            public void onChanged(List<Course> courses) {
                //  一直观察，可能造成卡顿，检查数据发生了改变再改变
                if (courses != null && courses != binding.courseView.getCourses()) {
                    binding.courseView.setCourses(courses);
                    binding.courseView.resetView();
                }
            }
        });
        final AllWeekCourseDao allWeekCourseDao = JuiceDatabase.getDatabase(requireContext().getApplicationContext()).getAllWeekCourseDao();
        binding.slRefresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                new Thread() {
                    @Override
                    public void run() {
                        super.run();
                        Message message = new Message();
                        message.what = Constant.MSG_REFRESH;


                        LogUtils.getInstance().d("setOnRefreshListener:开始刷新");
                        // 更新数据
                        allWeekCourseDao.deleteAllWeekCourse();
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
                            LogUtils.getInstance().d("setOnRefreshListener:解析完整课表结束");
                            for (Course cours : courses) {
                                allWeekCourseDao.insertAllWeekCourse(cours);
                            }
                            LogUtils.getInstance().d("setOnRefreshListener:完整课写入数据库表结束");
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
                        // 关闭刷新动画
                        binding.slRefresh.setRefreshing(false);
                        String msgStr = (String) msg.obj;
                        if (!"ok".equals(msgStr)) {
                            Toast.makeText(requireContext(), msgStr, Toast.LENGTH_SHORT).show();
                        }

                        break;
                }

            }
        };
    }

}
