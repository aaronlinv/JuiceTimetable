package com.juice.timetable.ui.course;

import android.graphics.Color;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.navigation.fragment.NavHostFragment;

import com.juice.timetable.R;
import com.juice.timetable.app.Constant;
import com.juice.timetable.data.bean.Course;
import com.juice.timetable.data.testCourseData;
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
    // 调式Init界面，用于调试登录界面
    public static boolean debugInit = false;

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

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        // 进入首次登录界面
        if (debugInit) {
            NavHostFragment.findNavController(this).navigate(R.id.initFragment);

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
        List<Course> courses = testCourseData.getCourses();
        // 传入课表List 以显示
        binding.courseView.setCourses(courses);

    }


}
