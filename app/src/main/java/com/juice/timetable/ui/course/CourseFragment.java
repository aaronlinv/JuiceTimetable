package com.juice.timetable.ui.course;

import android.animation.ArgbEvaluator;
import android.animation.ObjectAnimator;
import android.annotation.SuppressLint;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.PopupWindow;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;
import androidx.viewpager2.widget.ViewPager2;

import com.jaredrummler.materialspinner.MaterialSpinner;
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
import com.juice.timetable.utils.PreferencesUtils;
import com.juice.timetable.utils.Utils;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Random;

import cn.pedant.SweetAlert.SweetAlertDialog;

import static android.animation.ObjectAnimator.ofObject;

@SuppressWarnings("unchecked")
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
    private MaterialSpinner mSpinner;


    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentCourseBinding.inflate(getLayoutInflater());
        mVpCourse = binding.vpCourse;
        mSlRefresh = binding.slRefresh;
        mTvCheckIn = binding.tvCheckIn;

        mAllWeekCourseViewModel = new ViewModelProvider(requireActivity()).get(AllWeekCourseViewModel.class);
        mOneWeekCourseViewModel = new ViewModelProvider(requireActivity()).get(OneWeekCourseViewModel.class);
        mStuInfoViewModel = new ViewModelProvider(requireActivity()).get(StuInfoViewModel.class);

        initConfig();
        initCurrentWeek();
        initView();
        initCourse();
        return binding.getRoot();
    }

    /**
     * 初始化配置
     */
    private void initConfig() {
        // 是否开启签到提示
        Constant.ENABLE_CHECK_IN = PreferencesUtils.getBoolean(Constant.PREF_ENABLE_CHECK_IN, true);
        // 不存请假系统密码就关掉
        if (!hasLeavePwd()) {
            Constant.ENABLE_CHECK_IN = false;
        }

        // 是否开启慕课显示
        Constant.ENABLE_SHOW_MOOC = PreferencesUtils.getBoolean(Constant.PREF_ENABLE_SHOW_MOOC, true);
    }

    /**
     * 用户存在请假系统密码
     */
    private boolean hasLeavePwd() {
        StuInfo stuInfo = mStuInfoViewModel.selectStuInfo();
        LogUtils.getInstance().d("用户数据库信息：" + stuInfo);
        return (!stuInfo.getLeavePassword().isEmpty());
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
        // 开启签到显示 且 在签到时间刷新签到情况
        if (Constant.ENABLE_CHECK_IN && Utils.isCheckInTime()) {
            getCheckIn();
        }
        initEvent();
    }

    @Override
    public void onResume() {
        super.onResume();
        // 设置为可见 切换到其他界面会隐藏，所以这样要设置回可见
        mSpinner.setVisibility(View.VISIBLE);
    }

    @Override
    public void onStop() {
        super.onStop();
        toolbar.hideOverflowMenu();
        mSpinner.setVisibility(View.INVISIBLE);

    }

    private void initEvent() {
        // 下拉菜单 获取点击的周 设置标题栏
        toolbar.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {
            @SuppressLint("RtlHardcoded")
            @Override
            public boolean onMenuItemClick(MenuItem item) {
/*                int week = item.getItemId() + 1;
                LogUtils.getInstance().d("MenuItem <" + week + "> onMenuItemClick");

                if (week != Constant.CUR_WEEK) {
                    toolbar.setTitle("第" + week + "周 (非本周)");
                } else {
                    toolbar.setTitle("第" + Constant.CUR_WEEK + "周");
                }

                mVpCourse.setCurrentItem(item.getItemId(), true);*/
                // 跳转当前周 图标监听
                if (item.getItemId() == R.id.item_go_current_week) {
                    mVpCourse.setCurrentItem(Constant.CUR_WEEK - 1, true);
                    LogUtils.getInstance().d("点击了 跳转到当前周图标 -- > " + (Constant.CUR_WEEK - 1));
                }
                if (item.getItemId() == R.id.item_more_option) {
                    popupWindowEvent();
                }
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
                super.onPageSelected(position);
                mCurViewPagerNum = position;
                // 设置 toolbar 显示当前周
                mSpinner.setSelectedIndex(position);

/*                int week = position + 1;
                if (week != Constant.CUR_WEEK) {
                    toolbar.setTitle("第" + week + "周 (非本周)");
                } else {
                    toolbar.setTitle("第" + week + "周");
                }*/
            }
        });

        // ViewPager 课程被点击事件
        mCourseViewListAdapter.setItemClickListener(new CourseViewListAdapter.OnItemClickListener() {
            @Override
            public void onClick(Course cou) {
                LogUtils.getInstance().d("课程被点击  -- > " + cou);

                // 撞课处理 类型4 为撞课
                if (cou.getCouWeekType() == 4) {
                    // 遍历周课表 查询所有撞课课程
                    StringBuilder sb = new StringBuilder();

                    List<OneWeekCourse> oneWeekCourse = mCourseViewBeanList.get(0).getOneWeekCourse();
                    int i = 0;
                    for (OneWeekCourse weekCourse : oneWeekCourse) {
                        // 星期相同 且（起或止节数 相同）
                        if (Objects.equals(weekCourse.getDayOfWeek(), cou.getCouWeek()) && (Objects.equals(weekCourse.getStartNode(), cou.getCouStartNode()) || Objects.equals(weekCourse.getEndNode(), cou.getCouEndNode()))) {
                            if (i > 0) {
                                sb.append("<br><br>");
                            }
                            sb.append(weekCourse.getCouName()).append("<br>")
                                    .append(getTeacherName(weekCourse.getCouID()))
                                    .append(weekCourse.getCouRoom());
                            i++;
                        }
                    }


                    new SweetAlertDialog(requireActivity(), SweetAlertDialog.NORMAL_TYPE)
                            .setTitleText("<font color=\"red\">课程冲突</font>")
                            .setContentText(sb.toString())
//                        .setContentTextSize(18)
                            .hideConfirmButton()
                            .show();
                    return;
                }

                // 周课表没有老师所以要填充
                String teach = getTeacherName(cou.getCouID());


                String sb = teach + cou.getCouRoom();

                new SweetAlertDialog(requireActivity(), SweetAlertDialog.NORMAL_TYPE)
                        .setTitleText(cou.getCouName())
                        .setContentText(sb)
//                        .setContentTextSize(18)
                        .hideConfirmButton()
                        .show();
            }
        });

        // 第几周的下拉监听
        mSpinner.setOnItemSelectedListener(new MaterialSpinner.OnItemSelectedListener<String>() {
            @Override
            public void onItemSelected(MaterialSpinner view, int position, long id, String item) {
                // 跳转到对应周
                mVpCourse.setCurrentItem(position, true);
                LogUtils.getInstance().d("点击了 下拉菜单 跳转到对应周索引 -- > " + position);
            }
        });
    }

    /**
     * 获取老师名字，没匹配到返回 "" 所以不需要 在后面加 br
     * 找到自带br
     *
     * @param couId
     * @return
     */
    private String getTeacherName(long couId) {
        // 如果没有匹配到老师 就直接显示空 ""
        String teach = "";
        List<Course> allWeekCourse = mCourseViewBeanList.get(0).getAllWeekCourse();
        for (Course course : allWeekCourse) {
            if (Objects.equals(course.getCouID(), couId)) {
                teach = course.getCouTeacher() + "<br>";
                break;
            }
        }
        return teach;
    }

    /**
     * popupWindow的监听事件
     */
    private void popupWindowEvent() {
        // 必须设置宽度
        final PopupWindow popupWindow = new PopupWindow(requireView(),
                ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT, true);
        popupWindow.setAnimationStyle(R.anim.nav_default_pop_enter_anim);

        // 添加阴影
        popupWindow.setElevation(100);

        // 点击其他区域 PopUpWindow消失
        popupWindow.setTouchable(true);
        popupWindow.setTouchInterceptor(new View.OnTouchListener() {
            @SuppressLint("ClickableViewAccessibility")
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                // false 不拦截这个事件
                // 拦截了PopUpWindow 的onTouchEvent就不会被调用
//                            popupWindow.dismiss();
                return false;
            }
        });
        popupWindow.setBackgroundDrawable(new ColorDrawable(0xfffafafa));
        View contentView = LayoutInflater.from(getActivity()).inflate(R.layout.popupwindow, null);
        popupWindow.setContentView(contentView);
        popupWindow.showAsDropDown(toolbar, 0, 0, Gravity.RIGHT);
        final Switch switchCheckIn = contentView.findViewById(R.id.switch_check_in);
        Switch switchShowMooc = contentView.findViewById(R.id.switch_show_mooc);
        // 初始化开关状态
        switchCheckIn.setChecked(Constant.ENABLE_CHECK_IN);
        switchShowMooc.setChecked(Constant.ENABLE_SHOW_MOOC);

        switchCheckIn.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                LogUtils.getInstance().d("签到提示按钮 -- > " + isChecked);
                if (isChecked) {
                    if (hasLeavePwd()) {
                        Toast.makeText(getActivity(), "签到提示开启，会在签到时间段显示签到情况", Toast.LENGTH_LONG).show();

                    } else {
                        Toast.makeText(getActivity(), "需要先在修改认证信息界面添加请假系统密码才可以开启哦", Toast.LENGTH_LONG).show();
                        isChecked = false;
                        switchCheckIn.setChecked(false);
                    }
                } else {

                    Toast.makeText(getActivity(), "签到提示已关闭", Toast.LENGTH_SHORT).show();
                }
                // 在签到时间内 就是显示签到通知条
                Constant.ENABLE_CHECK_IN = isChecked;

                if (Utils.isCheckInTime() && isChecked) {
                    mTvCheckIn.setVisibility(TextView.VISIBLE);
                    // 通知刷新数据
                    getCheckIn();
                } else {
                    mTvCheckIn.setVisibility(TextView.GONE);
                    // 通知ViewPager 重新调整布局，不然下方会有空隙
                    mCourseViewListAdapter.notifyDataSetChanged();
                }

                // 持久化
                PreferencesUtils.putBoolean(Constant.PREF_ENABLE_CHECK_IN, isChecked);
            }
        });
        switchShowMooc.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                LogUtils.getInstance().d("慕课显示按钮 -- > " + isChecked);
                if (isChecked) {
                    Toast.makeText(getActivity(), "慕课显示开启，课表下方会显示所选慕课信息", Toast.LENGTH_LONG).show();
                } else {
                    Toast.makeText(getActivity(), "慕课显示已关闭", Toast.LENGTH_SHORT).show();
                }
                Constant.ENABLE_SHOW_MOOC = isChecked;

                // 通知ViewPager 重新调整布局，不然慕课显示还会在
                mCourseViewListAdapter.notifyDataSetChanged();
                // 持久化
                PreferencesUtils.putBoolean(Constant.PREF_ENABLE_SHOW_MOOC, isChecked);
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


        // 本地不存在 会返回-1
        int curWeek = Constant.CUR_WEEK;
        // 不在周范围 显示第一周
        if (curWeek < 1 || curWeek > Constant.MAX_WEEK) {
            curWeek = 1;
        }


        mVpCourse.setCurrentItem(curWeek - 1, false);
        // 设置 toolbar 显示当前周
        LogUtils.getInstance().d("spinner 设置当前周 -- > " + curWeek);

        mSpinner.setSelectedIndex(curWeek - 1);
/*        // 显示标题栏

        if ((mCurViewPagerNum + 1) != Constant.CUR_WEEK) {
            toolbar.setTitle("第" + Constant.CUR_WEEK + "周 (非本周)");
        } else {
            toolbar.setTitle("第" + Constant.CUR_WEEK + "周");
        }*/
    }

    /**
     * 读取数据库 完整课表和周课表 更新到适配器
     */
    private void updateCourse() {
        List<Course> allWeekCourse = mAllWeekCourseViewModel.getAllWeekCourse();
        List<OneWeekCourse> oneWeekCourse = mOneWeekCourseViewModel.getOneWeekCourse();
        List<Integer> week = mOneWeekCourseViewModel.getWeek();
        HashSet<Integer> weekSet = new HashSet<>(week);
        List<CourseViewBean> tempList = new ArrayList<>();

        // 遍历得到慕课列表 慕课类型为3
        List<Course> moocCourse = new ArrayList<>();
        for (Course course : allWeekCourse) {
            if (course.getCouWeekType() == 3) {
                moocCourse.add(course);
            }
        }

        for (int i = 1; i <= 25; i++) {
            CourseViewBean courseViewBean = new CourseViewBean();
            courseViewBean.setAllWeekCourse(allWeekCourse);
            courseViewBean.setCurrentIndex(i);
            courseViewBean.setOneWeekCourse(oneWeekCourse);
            courseViewBean.setWeekSet(weekSet);
            courseViewBean.setMoocCourse(moocCourse);
            tempList.add(courseViewBean);
        }
        LogUtils.getInstance().d("mCourseViewBeanList size -- > " + mCourseViewBeanList.size());
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
        // 本地不存在 会返回-1
        Constant.CUR_WEEK = Utils.getCurrentWeek();
        int curWeek = Constant.CUR_WEEK;
        LogUtils.getInstance().d("initCurrentWeek -- > " + curWeek);
        // 不在周范围 显示第一周
        if (curWeek < 1 || curWeek > Constant.MAX_WEEK) {
            mCurViewPagerNum = 0;
        } else {
            mCurViewPagerNum = Constant.CUR_WEEK - 1;
        }
    }

    /**
     * 初始化界面
     */
    private void initView() {
        toolbar = requireActivity().findViewById(R.id.toolbar);

//         显示Toolbar的右侧菜单按钮
        Menu menu = toolbar.getMenu();
        menu.setGroupVisible(0, true);

        // 移除原有标题
        toolbar.setTitle("");
        // toolbar spinner 下拉菜单
        mSpinner = toolbar.findViewById(R.id.spinner);

        String[] weekArr = new String[Constant.MAX_WEEK];
        for (int i = 0; i < Constant.MAX_WEEK; i++) {
            weekArr[i] = "第 " + (i + 1) + " 周";
        }

        mSpinner.setItems(weekArr);
        mSpinner.setBackgroundColor(getResources().getColor(R.color.colorPrimaryDark));
        mSpinner.setTextColor(0xFF000000);
        mSpinner.setTextSize(20);
        mSpinner.setDropdownMaxHeight(700);

        // 初始化标题栏 只在 registerOnPageChangeCallback 中初始化 从后台切回标题栏不会显示周
        // 在 updateCourse 中初始

        // 没有开启签到显示 或 不在签到时间并 隐藏签到提示栏
        if (!Constant.ENABLE_CHECK_IN || !Utils.isCheckInTime()) {
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
//                    allCourse = EduInfo.getTimeTable(stuInfo.getStuID().toString(), stuInfo.getEduPassword(), Constant.URI_WHOLE_COURSE, requireContext());
                    // TODO: 2020/5/29
                    allCourse = getTeacherMaAllWeekCourse();
                } catch (Exception e) {
                    LogUtils.getInstance().d("setOnRefreshListener：" + e.getMessage());
                    // 可能密码错误
                    if (e.getMessage().contains("Unable to resolve host")) {
                        message.obj = "网络好像不太好，请检查网络";
                    } else {
                        message.obj = e.getMessage();
                    }
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
                    for (Course cou : courses) {
                        if (cou.getCouColor() == null) {
                            // 这里的courses是模拟登录获取的，所有color为null，所以每次都刷新颜色
                            cou.setCouColor(cou.getCouID().intValue());
                        }
                        // 填充完颜色将课程写入数据库
                        mAllWeekCourseViewModel.insertAllWeekCourse(cou);
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
                            // 如果开启了彩虹模式 随机一个数
                            if (Constant.RAINBOW_MODE_ENABLED) {
                                Random random = new Random();
                                // 随机一个1开始的数， 0代表关闭彩虹模式
                                int rainbowModeNum = random.nextInt(Utils.getColorCount() + 1);
                                Constant.RAINBOW_MODE_NUM = rainbowModeNum;
                                // 写入本地Preferences
                                PreferencesUtils.putInt(Constant.PREF_RAINBOW_MODE_NUM, rainbowModeNum);
                            }
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
                // 数据库有请假系统密码  初始化签到信息
                LogUtils.getInstance().d("有请假系统密码则开始获取签到信息");
                if (hasLeavePwd) {
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
     * 解析课表 获取本周、上两周、下两周的周课表 同时设置当前周
     *
     * @param allWeekCourse
     */
    private void getOneWeekCou(List<Course> allWeekCourse) throws Exception {
        // 获取用户数据
        StuInfo stuInfo = mStuInfoViewModel.selectStuInfo();
        // 解析的周课表的List
        List<OneWeekCourse> oneWeekCourList;

        // 先获取当前周课课程
        String oneWeekCouStr = EduInfo.getTimeTable(stuInfo.getStuID().toString(), stuInfo.getEduPassword(), Constant.URI_CUR_WEEK, requireContext());
        if (oneWeekCouStr.contains("只能查最近几周的课表")) {
            throw new Exception("没有查询到周课表信息");
        }
        oneWeekCourList = ParseOneWeek.parseCourse(oneWeekCouStr);
        if (oneWeekCouStr.isEmpty()) {
            throw new Exception("没有查询到周课表信息");
        }
        // 当前周 第13周 curWeek就是13
        int curWeek = oneWeekCourList.get(0).getInWeek();
        LogUtils.getInstance().d("获取第 <" + curWeek + "> 周课表 -- > " + oneWeekCourList);
        // 存储所有周课表
        List<OneWeekCourse> couList = new ArrayList<>(oneWeekCourList);

        // 设置当前周
        Utils.setFirstWeekPref(curWeek);
        LogUtils.getInstance().d("设置当前周为 -- > " + curWeek);

        // 获取数据库中存了哪些周的周课表
        List<Integer> inWeek = mOneWeekCourseViewModel.getWeek();
        HashSet<Integer> set = new HashSet<>(inWeek);
        LogUtils.getInstance().d("数据库周课表已存在的周 -- > " + set);


        // 要获取的周课表，0为当前周
        int week = 1;

        // 数据库不包含上两周就解析上两周
        if (!set.contains(curWeek - 1) || !set.contains(curWeek - 2)) {
            week = -2;
        }

        // 要删除数据库中的周
        ArrayList<Integer> delList = new ArrayList<>();

        // 模拟登录获取课表数据
        for (; week <= 2; week++) {
            // 当前周跳过
            if (week == 0) {
                continue;
            }
            oneWeekCouStr = EduInfo.getTimeTable(
                    stuInfo.getStuID().toString(),
                    stuInfo.getEduPassword(),
                    Constant.URI_ONE_WEEK + (curWeek + week),
                    requireContext());

            oneWeekCourList = ParseOneWeek.parseCourse(oneWeekCouStr);
            LogUtils.getInstance().d("获取第 <" + (curWeek + week) + "> 周课表 -- > " + oneWeekCourList);
            couList.addAll(oneWeekCourList);
            // 删除该数据库中 单前周和后两周的课表，避免冲突
            if (week > 0) {
                delList.add((curWeek + week));
            }
        }
        // 添加当前周
        delList.add(curWeek);
        // 执行删除
        mOneWeekCourseViewModel.deleteWeek(delList);
        LogUtils.getInstance().d("删除周课表的周 -- > " + delList);

        // 颜色的随机数(从完整课表总课程数开始)
        int colorNum = allWeekCourse.size();
        // 周课表课程存在完整课表中，就赋值上完整课表id
        // TODO: 2020/5/29
        couList.clear();
        couList.addAll(ParseOneWeek.parseCourse(getTeacherMaOneWeek()));
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
                    oneWeekCourse.setColor(colorNum++);

                }
            }
            // 插入数据库
            mOneWeekCourseViewModel.insertOneWeekCourse(oneWeekCourse);
        }
        LogUtils.getInstance().d("解析本周、上两周、下两周的周课表 结束");
    }

    // TODO: 2020/5/29

    /**
     * TeacherMa完整课表
     *
     * @return String
     */
    private static String getTeacherMaAllWeekCourse() {
        return "\n" +
                "<META NAME=\"ROBOTS\" CONTENT=\"NOINDEX,NOFOLLOW\">\n" +
                "<META HTTP-EQUIV=\"pragma\" CONTENT=\"no-cache\">\n" +
                "<META http-equiv=\"cache-control\" content=\"no-cache\">\n" +
                "<META HTTP-EQUIV=\"expires\" CONTENT=\"0\">\n" +
                "<META http-equiv=\"Content-Type\" content=\"text/html; charset=UTF-8\">\n" +
                " \n" +
                "\n" +
                "<html>\n" +
                "<head>\n" +
                "<title>福州大学至诚学院课程表</title>\n" +
                "<meta http-equiv=\"Content-Type\" content=\"text/html; Charset=UTF-8\">\n" +
                "<style media=print>\n" +
                ".Noprint{display:none;}\n" +
                ".PageNext{page-break-after: always;}\n" +
                "</style>\n" +
                "\n" +
                "<style>\n" +
                "<!-- \n" +
                "table {\n" +
                "\tfont-size: 10pt;\n" +
                "}\n" +
                "td {\n" +
                "\tfont-size: 9pt;\n" +
                "}\n" +
                ".button {font-family: \"宋体\";font-size: 9pt;color: #00006a; height: 19}\n" +
                "-->\n" +
                "</style>\n" +
                "\n" +
                "<link href=\"../inc/style.css\" rel=\"stylesheet\" type=\"text/css\">\n" +
                "</head>\n" +
                "\n" +
                "<BODY onbeforeprint=\"w.style.display='none';\"  onafterprint=\"w.style.display='';\">\n" +
                "  <table width=\"100%\" border=\"0\" cellspacing=\"0\" id=\"w\" class=\"Noprint\">\n" +
                "  <tr><td align=\"center\">\n" +
                "<form name=\"form\" method=\"post\" action=\"kb_xs.asp\">\n" +
                "\t <input name=\"menu_no\" type=\"hidden\" value=\"\">\n" +
                "\t \n" +
                "&nbsp;查询学期：<input name=\"xn\" type=\"text\" size=\"4\" value=\"2019\">学年 \n" +
                "\t\t<select name=\"xq\">\n" +
                "\t\t\t<option value=\"下\">下</option> \n" +
                "\t\t\t<option value=\"\"></option>  \t\n" +
                "\t\t\t<option value=\"上\">上</option>\n" +
                "\t\t\t<option value=\"下\">下</option>\n" +
                "\t\t</select>\n" +
                "\t\t学期 <input type=\"submit\" name=\"Submit\" value=\"查询\" class=\"button\">\n" +
                "\t\t&nbsp;&nbsp;<input name=\"print\" type=\"button\" value=\" 打印 \" onClick=\"javascript:window.print();\" class=\"button\">\t\t\t \n" +
                "</form>\n" +
                "</td></tr></table>\t\n" +
                "\n" +
                "<div align=\"center\"><strong>福州大学至诚学院 2019下学期马骏同学课程表</strong>(2020-5-28 20:10:38)</div>\n" +
                "<table width=\"880\" cellspacing=\"0\" cellpadding=\"0\" align=\"center\" border=\"0\" bordercolor=\"#111111\">\n" +
                "  <tr> \n" +
                "<!--      <td valign=\"top\"><table width=\"440\" height=\"400\" cellspacing=\"0\" cellpadding=\"1\" align=\"center\" style=\"border-collapse: collapse\" border=\"1\" bordercolor=\"#111111\">-->\n" +
                "    <td valign=\"top\"><table width=\"100%\" height=\"400\" cellspacing=\"0\" cellpadding=\"1\" align=\"center\" style=\"border-collapse: collapse\" border=\"1\" bordercolor=\"#111111\">\n" +
                "        <tr  height=\"30\"> \n" +
                "          <td align=\"center\">课程名称</td>\n" +
                "          <td align=\"center\">大纲/进度表</td>\n" +
                "          <td align=\"center\">任课教师</td>\n" +
                "\t\t  <td align=\"center\">选修类型</td>\n" +
                "\t\t  <td align=\"center\">考试类别</td>\t\t  \n" +
                "\t\t  <td align=\"center\">班级</td>\t\t  \n" +
                "\t\t  <td align=\"center\">学分</td>\n" +
                "          <td align=\"center\">总<br>学<br>时 </td>\n" +
                "          <td align=\"center\">周<br>学<br>时 </td>\n" +
                "          <td align=\"center\">实<br>验<br>学<br>时 </td>\n" +
                "          <td align=\"center\">起讫时间<br>周序<br>(星期)</td>\n" +
                "        </tr>\n" +
                "        \n" +
                "        <tr> \n" +
                "          <td  >&nbsp;大学物理（上） (15)班</td>\n" +
                "          <td align=\"center\"><a href=\"../kkgl/kcjd/dgjdb.asp?lx=dg&kkxq=2019%E4%B8%8B&kcdm=06000118&kkhm=2019%E4%B8%8B06000118015\" target=\"_blank\">大纲</a>\n" +
                "\t\t  /<a href=\"../kkgl/kcjd/dgjdb.asp?lx=jdb&kkxq=2019%E4%B8%8B&kcdm=06000118&kkhm=2019%E4%B8%8B06000118015\" target=\"_blank\">进度表</a></td>\t\t\t\n" +
                "          <td align=\"center\">孙钦钦</td>\n" +
                "\t\t  <td align=\"center\">公共必修</td>\t\t  \n" +
                "\t\t  <td align=\"center\">第一次重修</td>\t\t\t\t\t  \n" +
                "\t\t  <td align=\"center\">15班</td>\t\t\t\t\t  \n" +
                "\t\t  <td align=\"center\">1.5</td>\n" +
                "          <td align=\"center\">30</td>\n" +
                "          <td align=\"center\">2</td>\n" +
                "          <td align=\"center\"></td>\n" +
                "          <td align=\"center\">01～17</td>\t\n" +
                "        </tr>\n" +
                "       \n" +
                "        <tr> \n" +
                "          <td rowspan=\"2\"  >&nbsp;概率论与数理统计 (普10)班</td>\n" +
                "          <td align=\"center\"><a href=\"../kkgl/kcjd/dgjdb.asp?lx=dg&kkxq=2019%E4%B8%8B&kcdm=09000102&kkhm=2019%E4%B8%8B09000102010\" target=\"_blank\">大纲</a>\n" +
                "\t\t  /<a href=\"../kkgl/kcjd/dgjdb.asp?lx=jdb&kkxq=2019%E4%B8%8B&kcdm=09000102&kkhm=2019%E4%B8%8B09000102010\" target=\"_blank\">进度表</a></td>\t\t\t\n" +
                "          <td align=\"center\">杨培鉴</td>\n" +
                "\t\t  <td align=\"center\">公共必修</td>\t\t  \n" +
                "\t\t  <td align=\"center\">第一次重修</td>\t\t\t\t\t  \n" +
                "\t\t  <td align=\"center\">普10</td>\t\t\t\t\t  \n" +
                "\t\t  <td align=\"center\">3</td>\n" +
                "          <td align=\"center\">54</td>\n" +
                "          <td align=\"center\">4</td>\n" +
                "          <td align=\"center\"></td>\n" +
                "          <td align=\"center\">01～15</td>\t\n" +
                "        </tr>\n" +
                "       \n" +
                "        <tr height=\"25\"><td align=\"left\" colspan=\"10\">★备注：周三晚答疑时间18:30~20:00</td></tr>\n" +
                "       \n" +
                "        <tr> \n" +
                "          <td  >&nbsp;形势与政策（六） (6)班</td>\n" +
                "          <td align=\"center\"><a href=\"../kkgl/kcjd/dgjdb.asp?lx=dg&kkxq=2019%E4%B8%8B&kcdm=18000106&kkhm=2019%E4%B8%8B18000106010\" target=\"_blank\">大纲</a>\n" +
                "\t\t  /<a href=\"../kkgl/kcjd/dgjdb.asp?lx=jdb&kkxq=2019%E4%B8%8B&kcdm=18000106&kkhm=2019%E4%B8%8B18000106010\" target=\"_blank\">进度表</a></td>\t\t\t\n" +
                "          <td align=\"center\">韩晞婷</td>\n" +
                "\t\t  <td align=\"center\">公共必修</td>\t\t  \n" +
                "\t\t  <td align=\"center\">正常考考试</td>\t\t\t\t\t  \n" +
                "\t\t  <td align=\"center\">6班</td>\t\t\t\t\t  \n" +
                "\t\t  <td align=\"center\">.5</td>\n" +
                "          <td align=\"center\">8</td>\n" +
                "          <td align=\"center\">2</td>\n" +
                "          <td align=\"center\"></td>\n" +
                "          <td align=\"center\">11～14</td>\t\n" +
                "        </tr>\n" +
                "       \n" +
                "        <tr> \n" +
                "          <td rowspan=\"2\"  >&nbsp;高级数据库技术 (1)班</td>\n" +
                "          <td align=\"center\"><a href=\"../kkgl/kcjd/dgjdb.asp?lx=dg&kkxq=2019%E4%B8%8B&kcdm=16111306&kkhm=2019%E4%B8%8B16111306001\" target=\"_blank\">大纲</a>\n" +
                "\t\t  /<a href=\"../kkgl/kcjd/dgjdb.asp?lx=jdb&kkxq=2019%E4%B8%8B&kcdm=16111306&kkhm=2019%E4%B8%8B16111306001\" target=\"_blank\">进度表</a></td>\t\t\t\n" +
                "          <td align=\"center\">杨雄</td>\n" +
                "\t\t  <td align=\"center\">专业方向2</td>\t\t  \n" +
                "\t\t  <td align=\"center\">正常考考试</td>\t\t\t\t\t  \n" +
                "\t\t  <td align=\"center\">2017级计算机1</td>\t\t\t\t\t  \n" +
                "\t\t  <td align=\"center\">3</td>\n" +
                "          <td align=\"center\">48</td>\n" +
                "          <td align=\"center\">4</td>\n" +
                "          <td align=\"center\">24</td>\n" +
                "          <td align=\"center\">01～13</td>\t\n" +
                "        </tr>\n" +
                "       \n" +
                "        <tr height=\"25\"><td align=\"left\" colspan=\"10\">★备注：022</td></tr>\n" +
                "       \n" +
                "        <tr> \n" +
                "          <td rowspan=\"2\"  >&nbsp;数据挖掘与分析 (1)班</td>\n" +
                "          <td align=\"center\"><a href=\"../kkgl/kcjd/dgjdb.asp?lx=dg&kkxq=2019%E4%B8%8B&kcdm=16111405&kkhm=2019%E4%B8%8B16111405001\" target=\"_blank\">大纲</a>\n" +
                "\t\t  /<a href=\"../kkgl/kcjd/dgjdb.asp?lx=jdb&kkxq=2019%E4%B8%8B&kcdm=16111405&kkhm=2019%E4%B8%8B16111405001\" target=\"_blank\">进度表</a></td>\t\t\t\n" +
                "          <td align=\"center\">杨雄</td>\n" +
                "\t\t  <td align=\"center\">专业方向2</td>\t\t  \n" +
                "\t\t  <td align=\"center\">正常考考试</td>\t\t\t\t\t  \n" +
                "\t\t  <td align=\"center\">2017级计算机1</td>\t\t\t\t\t  \n" +
                "\t\t  <td align=\"center\">2</td>\n" +
                "          <td align=\"center\">32</td>\n" +
                "          <td align=\"center\">4</td>\n" +
                "          <td align=\"center\">10</td>\n" +
                "          <td align=\"center\">01～08</td>\t\n" +
                "        </tr>\n" +
                "       \n" +
                "        <tr height=\"25\"><td align=\"left\" colspan=\"10\">★备注：022</td></tr>\n" +
                "       \n" +
                "        <tr> \n" +
                "          <td  >&nbsp;大数据应用开发 (1)班</td>\n" +
                "          <td align=\"center\"><a href=\"../kkgl/kcjd/dgjdb.asp?lx=dg&kkxq=2019%E4%B8%8B&kcdm=16111308&kkhm=2019%E4%B8%8B16111308001\" target=\"_blank\">大纲</a>\n" +
                "\t\t  /<a href=\"../kkgl/kcjd/dgjdb.asp?lx=jdb&kkxq=2019%E4%B8%8B&kcdm=16111308&kkhm=2019%E4%B8%8B16111308001\" target=\"_blank\">进度表</a></td>\t\t\t\n" +
                "          <td align=\"center\">马云莺</td>\n" +
                "\t\t  <td align=\"center\">专业素质课</td>\t\t  \n" +
                "\t\t  <td align=\"center\">正常考考试</td>\t\t\t\t\t  \n" +
                "\t\t  <td align=\"center\">2017级计算机1</td>\t\t\t\t\t  \n" +
                "\t\t  <td align=\"center\">2</td>\n" +
                "          <td align=\"center\">32</td>\n" +
                "          <td align=\"center\">4</td>\n" +
                "          <td align=\"center\">16</td>\n" +
                "          <td align=\"center\">01～08</td>\t\n" +
                "        </tr>\n" +
                "       \n" +
                "        <tr> \n" +
                "          <td rowspan=\"2\"  >&nbsp;软件工程 (1)班</td>\n" +
                "          <td align=\"center\"><a href=\"../kkgl/kcjd/dgjdb.asp?lx=dg&kkxq=2019%E4%B8%8B&kcdm=06111303&kkhm=2019%E4%B8%8B06111303001\" target=\"_blank\">大纲</a>\n" +
                "\t\t  /<a href=\"../kkgl/kcjd/dgjdb.asp?lx=jdb&kkxq=2019%E4%B8%8B&kcdm=06111303&kkhm=2019%E4%B8%8B06111303001\" target=\"_blank\">进度表</a></td>\t\t\t\n" +
                "          <td align=\"center\">张栋</td>\n" +
                "\t\t  <td align=\"center\">专业素质课</td>\t\t  \n" +
                "\t\t  <td align=\"center\">正常考考试</td>\t\t\t\t\t  \n" +
                "\t\t  <td align=\"center\">2017级计算机1</td>\t\t\t\t\t  \n" +
                "\t\t  <td align=\"center\">3</td>\n" +
                "          <td align=\"center\">48</td>\n" +
                "          <td align=\"center\">2</td>\n" +
                "          <td align=\"center\">18</td>\n" +
                "          <td align=\"center\">01～12</td>\t\n" +
                "        </tr>\n" +
                "       \n" +
                "        <tr height=\"25\"><td align=\"left\" colspan=\"10\">★备注：留两学时线下</td></tr>\n" +
                "       \n" +
                "        <tr> \n" +
                "          <td  >&nbsp;数据可视化与可视分析 (1)班</td>\n" +
                "          <td align=\"center\"><a href=\"../kkgl/kcjd/dgjdb.asp?lx=dg&kkxq=2019%E4%B8%8B&kcdm=17111403&kkhm=2019%E4%B8%8B17111403001\" target=\"_blank\">大纲</a>\n" +
                "\t\t  /<a href=\"../kkgl/kcjd/dgjdb.asp?lx=jdb&kkxq=2019%E4%B8%8B&kcdm=17111403&kkhm=2019%E4%B8%8B17111403001\" target=\"_blank\">进度表</a></td>\t\t\t\n" +
                "          <td align=\"center\">沈炎斌</td>\n" +
                "\t\t  <td align=\"center\">专业素质课</td>\t\t  \n" +
                "\t\t  <td align=\"center\">正常考考试</td>\t\t\t\t\t  \n" +
                "\t\t  <td align=\"center\">2017级计算机1</td>\t\t\t\t\t  \n" +
                "\t\t  <td align=\"center\">2</td>\n" +
                "          <td align=\"center\">32</td>\n" +
                "          <td align=\"center\">2</td>\n" +
                "          <td align=\"center\">16</td>\n" +
                "          <td align=\"center\">15～18</td>\t\n" +
                "        </tr>\n" +
                "       \n" +
                "        <tr> \n" +
                "          <td  >&nbsp;云计算与数据中心 (1)班</td>\n" +
                "          <td align=\"center\"><a href=\"../kkgl/kcjd/dgjdb.asp?lx=dg&kkxq=2019%E4%B8%8B&kcdm=16111407&kkhm=2019%E4%B8%8B16111407002\" target=\"_blank\">大纲</a>\n" +
                "\t\t  /<a href=\"../kkgl/kcjd/dgjdb.asp?lx=jdb&kkxq=2019%E4%B8%8B&kcdm=16111407&kkhm=2019%E4%B8%8B16111407002\" target=\"_blank\">进度表</a></td>\t\t\t\n" +
                "          <td align=\"center\">林庆新</td>\n" +
                "\t\t  <td align=\"center\">专业素质课</td>\t\t  \n" +
                "\t\t  <td align=\"center\">正常考考试</td>\t\t\t\t\t  \n" +
                "\t\t  <td align=\"center\">2017级计算机1</td>\t\t\t\t\t  \n" +
                "\t\t  <td align=\"center\">2</td>\n" +
                "          <td align=\"center\">32</td>\n" +
                "          <td align=\"center\">8</td>\n" +
                "          <td align=\"center\">12</td>\n" +
                "          <td align=\"center\">01～04</td>\t\n" +
                "        </tr>\n" +
                "       \n" +
                "        <tr> \n" +
                "          <td rowspan=\"2\"  >&nbsp;大数据应用开发实践 (1)班</td>\n" +
                "          <td align=\"center\"><a href=\"../kkgl/kcjd/dgjdb.asp?lx=dg&kkxq=2019%E4%B8%8B&kcdm=16111603&kkhm=2019%E4%B8%8B16111603001\" target=\"_blank\">大纲</a>\n" +
                "\t\t  /<a href=\"../kkgl/kcjd/dgjdb.asp?lx=jdb&kkxq=2019%E4%B8%8B&kcdm=16111603&kkhm=2019%E4%B8%8B16111603001\" target=\"_blank\">进度表</a></td>\t\t\t\n" +
                "          <td align=\"center\">马云莺</td>\n" +
                "\t\t  <td align=\"center\">实践环节</td>\t\t  \n" +
                "\t\t  <td align=\"center\">正常考考试</td>\t\t\t\t\t  \n" +
                "\t\t  <td align=\"center\">2017级计算机1</td>\t\t\t\t\t  \n" +
                "\t\t  <td align=\"center\">1.5</td>\n" +
                "          <td align=\"center\">30</td>\n" +
                "          <td align=\"center\">4</td>\n" +
                "          <td align=\"center\"></td>\n" +
                "          <td align=\"center\">09～16</td>\t\n" +
                "        </tr>\n" +
                "       \n" +
                "        <tr height=\"25\"><td align=\"left\" colspan=\"10\">★备注：选修</td></tr>\n" +
                "       \n" +
                "        <tr> \n" +
                "          <td rowspan=\"2\"  >&nbsp;大数据综合应用案例实训 (1)班</td>\n" +
                "          <td align=\"center\"><a href=\"../kkgl/kcjd/dgjdb.asp?lx=dg&kkxq=2019%E4%B8%8B&kcdm=17111607&kkhm=2019%E4%B8%8B17111607001\" target=\"_blank\">大纲</a>\n" +
                "\t\t  /<a href=\"../kkgl/kcjd/dgjdb.asp?lx=jdb&kkxq=2019%E4%B8%8B&kcdm=17111607&kkhm=2019%E4%B8%8B17111607001\" target=\"_blank\">进度表</a></td>\t\t\t\n" +
                "          <td align=\"center\">马云莺</td>\n" +
                "\t\t  <td align=\"center\">实践环节</td>\t\t  \n" +
                "\t\t  <td align=\"center\">正常考考试</td>\t\t\t\t\t  \n" +
                "\t\t  <td align=\"center\">2017级计算机1</td>\t\t\t\t\t  \n" +
                "\t\t  <td align=\"center\">1.5</td>\n" +
                "          <td align=\"center\">30</td>\n" +
                "          <td align=\"center\">4</td>\n" +
                "          <td align=\"center\"></td>\n" +
                "          <td align=\"center\">05～11</td>\t\n" +
                "        </tr>\n" +
                "       \n" +
                "        <tr height=\"25\"><td align=\"left\" colspan=\"10\">★备注：选修</td></tr>\n" +
                "       \n" +
                "        <tr> \n" +
                "          <td rowspan=\"2\"  >&nbsp;数据挖掘应用实践 (1)班</td>\n" +
                "          <td align=\"center\"><a href=\"../kkgl/kcjd/dgjdb.asp?lx=dg&kkxq=2019%E4%B8%8B&kcdm=17111606&kkhm=2019%E4%B8%8B17111606001\" target=\"_blank\">大纲</a>\n" +
                "\t\t  /<a href=\"../kkgl/kcjd/dgjdb.asp?lx=jdb&kkxq=2019%E4%B8%8B&kcdm=17111606&kkhm=2019%E4%B8%8B17111606001\" target=\"_blank\">进度表</a></td>\t\t\t\n" +
                "          <td align=\"center\">杨雄</td>\n" +
                "\t\t  <td align=\"center\">实践环节</td>\t\t  \n" +
                "\t\t  <td align=\"center\">正常考考试</td>\t\t\t\t\t  \n" +
                "\t\t  <td align=\"center\">2017级计算机1</td>\t\t\t\t\t  \n" +
                "\t\t  <td align=\"center\">1.5</td>\n" +
                "          <td align=\"center\">30</td>\n" +
                "          <td align=\"center\">√</td>\n" +
                "          <td align=\"center\">4</td>\n" +
                "          <td align=\"center\">09～15</td>\t\n" +
                "        </tr>\n" +
                "       \n" +
                "        <tr height=\"25\"><td align=\"left\" colspan=\"10\">★备注：选修</td></tr>\n" +
                "       \n" +
                "      </table><font color=\"#CC3333\">\n" +
                "\t  </font>\n" +
                "\t  </td>\n" +
                "    <td valign=\"top\">\n" +
                "\t\n" +
                "\t<table width=\"440\" height=\"400\" cellspacing=\"0\" cellpadding=\"1\" align=\"center\" style=\"border-collapse: collapse\" border=\"1\" bordercolor=\"#111111\">\n" +
                "        <tr  height=\"30\"> \n" +
                "          <td align=\"center\">\n" +
                "            星期<br>\n" +
                "            节次 </td>\n" +
                "          <td align=\"center\">一</td>\n" +
                "          <td align=\"center\">二</td>\n" +
                "          <td align=\"center\">三</td>\n" +
                "          <td align=\"center\">四</td>\n" +
                "          <td align=\"center\">五</td>\n" +
                "\t\t  \n" +
                "          <td align=\"center\">六</td>\n" +
                "\t\t  \n" +
                "        </tr>\n" +
                "        \n" +
                "        \n" +
                "        <tr id=\"tr1\"> \n" +
                "          <td align=\"center\">1</td>\n" +
                "          \n" +
                "          <td align=\"center\" rowspan=4 id=\"11\">高级数据库技术(1)班<br>[网络教学]</td>\n" +
                "          \n" +
                "          <td id=\"12\">&nbsp;</td>\n" +
                "          \n" +
                "          <td align=\"center\" rowspan=4 id=\"13\">数据挖掘与分析(1)班<br>[网络教学]<br>数据挖掘应用实践(1)班<br>[网络教学]<br>数据挖掘应用实践(1)班<br>[网络教学]<br>[1~2节]<br>(16-16周)</td>\n" +
                "          \n" +
                "          <td id=\"14\">&nbsp;</td>\n" +
                "          \n" +
                "          <td align=\"center\" rowspan=4 id=\"15\">云计算与数据中心(1)班<br>[网络教学]</td>\n" +
                "          \n" +
                "          <td align=\"center\" rowspan=4 id=\"16\">数据可视化与可视分析(1)班<br>[网络教学]</td>\n" +
                "          \n" +
                "        </tr>\n" +
                "        \n" +
                "        <tr id=\"tr2\"> \n" +
                "          <td align=\"center\">2</td>\n" +
                "          \n" +
                "          <td id=\"22\">&nbsp;</td>\n" +
                "          \n" +
                "          <td id=\"24\">&nbsp;</td>\n" +
                "          \n" +
                "        </tr>\n" +
                "        \n" +
                "        <tr id=\"tr3\"> \n" +
                "          <td align=\"center\">3</td>\n" +
                "          \n" +
                "          <td align=\"center\" rowspan=2 id=\"32\">大学物理（上）(15)班<br>[网络教学]<br>形势与政策（六）(6)班<br>[网络教学]</td>\n" +
                "          \n" +
                "          <td id=\"34\">&nbsp;</td>\n" +
                "          \n" +
                "        </tr>\n" +
                "        \n" +
                "        <tr id=\"tr4\"> \n" +
                "          <td align=\"center\">4</td>\n" +
                "          \n" +
                "          <td id=\"44\">&nbsp;</td>\n" +
                "          \n" +
                "        </tr>\n" +
                "        \n" +
                "        <tr id=\"tr5\"> \n" +
                "          <td align=\"center\">5</td>\n" +
                "          \n" +
                "          <td align=\"center\" rowspan=2 id=\"51\">软件工程(1)班<br>[网络教学]<br>数据可视化与可视分析(1)班<br>[网络教学]<br>[5~8节]</td>\n" +
                "          \n" +
                "          <td align=\"center\" rowspan=4 id=\"52\">大数据应用开发(1)班<br>[网络教学]<br>大数据应用开发实践(1)班<br>[网络教学]<br>大数据应用开发实践(1)班<br>[网络教学]<br>[5~6节]<br>(17-17周)</td>\n" +
                "          \n" +
                "          <td align=\"center\" rowspan=4 id=\"53\">软件工程(1)班<br>[网络教学]<br>(05-08周)<br>软件工程(1)班<br>[网络教学]<br>(10-10周)<br>软件工程(1)班<br>[网络教学]<br>(13-13周)<br>云计算与数据中心(1)班<br>[网络教学]</td>\n" +
                "          \n" +
                "          <td align=\"center\" rowspan=4 id=\"54\">大数据综合应用案例实训(1)班<br>[网络教学]<br>大数据综合应用案例实训(1)班<br>[网络教学]<br>[5~6节]<br>(12-12周)</td>\n" +
                "          \n" +
                "          <td id=\"55\">&nbsp;</td>\n" +
                "          \n" +
                "          <td align=\"center\" rowspan=2 id=\"56\">概率论与数理统计(普10)班<br>[网络教学]</td>\n" +
                "          \n" +
                "        </tr>\n" +
                "        \n" +
                "        <tr id=\"tr6\"> \n" +
                "          <td align=\"center\">6</td>\n" +
                "          \n" +
                "          <td id=\"65\">&nbsp;</td>\n" +
                "          \n" +
                "        </tr>\n" +
                "        \n" +
                "        <tr id=\"tr7\"> \n" +
                "          <td align=\"center\">7</td>\n" +
                "          \n" +
                "          <td id=\"71\">&nbsp;</td>\n" +
                "          \n" +
                "          <td id=\"75\">&nbsp;</td>\n" +
                "          \n" +
                "          <td id=\"76\">&nbsp;</td>\n" +
                "          \n" +
                "        </tr>\n" +
                "        \n" +
                "        <tr id=\"tr8\"> \n" +
                "          <td align=\"center\">8</td>\n" +
                "          \n" +
                "          <td id=\"81\">&nbsp;</td>\n" +
                "          \n" +
                "          <td id=\"85\">&nbsp;</td>\n" +
                "          \n" +
                "          <td id=\"86\">&nbsp;</td>\n" +
                "          \n" +
                "        </tr>\n" +
                "        \n" +
                "        <tr id=\"tr9\"> \n" +
                "          <td align=\"center\">9</td>\n" +
                "          \n" +
                "          <td id=\"91\">&nbsp;</td>\n" +
                "          \n" +
                "          <td id=\"92\">&nbsp;</td>\n" +
                "          \n" +
                "          <td align=\"center\" rowspan=2 id=\"93\">概率论与数理统计(普10)班<br>[网络教学]</td>\n" +
                "          \n" +
                "          <td id=\"94\">&nbsp;</td>\n" +
                "          \n" +
                "          <td id=\"95\">&nbsp;</td>\n" +
                "          \n" +
                "          <td id=\"96\">&nbsp;</td>\n" +
                "          \n" +
                "        </tr>\n" +
                "        \n" +
                "        <tr id=\"tr10\"> \n" +
                "          <td align=\"center\">10</td>\n" +
                "          \n" +
                "          <td id=\"101\">&nbsp;</td>\n" +
                "          \n" +
                "          <td id=\"102\">&nbsp;</td>\n" +
                "          \n" +
                "          <td id=\"104\">&nbsp;</td>\n" +
                "          \n" +
                "          <td id=\"105\">&nbsp;</td>\n" +
                "          \n" +
                "          <td id=\"106\">&nbsp;</td>\n" +
                "          \n" +
                "        </tr>\n" +
                "        \n" +
                "        <tr id=\"tr11\"> \n" +
                "          <td align=\"center\">11</td>\n" +
                "          \n" +
                "          <td id=\"111\">&nbsp;</td>\n" +
                "          \n" +
                "          <td id=\"112\">&nbsp;</td>\n" +
                "          \n" +
                "          <td id=\"113\">&nbsp;</td>\n" +
                "          \n" +
                "          <td id=\"114\">&nbsp;</td>\n" +
                "          \n" +
                "          <td id=\"115\">&nbsp;</td>\n" +
                "          \n" +
                "          <td id=\"116\">&nbsp;</td>\n" +
                "          \n" +
                "        </tr>\n" +
                "        \n" +
                "      </table></td>\n" +
                "  </tr>\n" +
                "</table>\n" +
                "\t\n" +
                "</body>\n" +
                "</html>\n";
    }

    /**
     * 马老师周课表
     *
     * @return String
     */
    private static String getTeacherMaOneWeek() {
        return "\n" +
                "<META NAME=\"ROBOTS\" CONTENT=\"NOINDEX,NOFOLLOW\">\n" +
                "<META HTTP-EQUIV=\"pragma\" CONTENT=\"no-cache\">\n" +
                "<META http-equiv=\"cache-control\" content=\"no-cache\">\n" +
                "<META HTTP-EQUIV=\"expires\" CONTENT=\"0\">\n" +
                "<META http-equiv=\"Content-Type\" content=\"text/html; charset=UTF-8\">\n" +
                " \n" +
                "\n" +
                "<html>\n" +
                "<head>\n" +
                "<title>福州大学至诚学院课程表</title>\n" +
                "<meta http-equiv=\"Content-Type\" content=\"text/html; Charset=UTF-8\">\n" +
                "<link rel=\"stylesheet\" href=\"../inc/kbstyle.css\">\n" +
                "</head>\n" +
                "\n" +
                "<BODY onbeforeprint=\"w.style.display='none';\"  onafterprint=\"w.style.display='';\">\n" +
                "\t \n" +
                "  <table width=\"440\" border=\"0\" cellspacing=\"0\" id=\"w\" align=\"center\">\n" +
                "  <tr height=\"45\"><td align=\"center\"><input name=\"pweek\" type=\"button\" value=\"上一周 \" onClick=\"javascript:document.location='zkb_xs.asp?week1=12&kkxq=2019%E4%B8%8B';\" class=\"button\">   \t\t\t\t\t\t\n" +
                "&nbsp;&nbsp;&nbsp;&nbsp;<input name=\"allkb\" type=\"button\" value=\"完整课表\" onClick=\"javascript:document.location='kb_xs.asp';\" class=\"button\">&nbsp;&nbsp;&nbsp;&nbsp;   \n" +
                "\t<input name=\"nweek\" type=\"button\" value=\"下一周 \" onClick=\"javascript:document.location='zkb_xs.asp?week1=14&kkxq=2019%E4%B8%8B';\" class=\"button\">  \n" +
                "  \n" +
                "</td></tr>\n" +
                "<tr><td align=\"center\" class=\"td3\"><strong> 2019下学期第13周(2020-5-11-2020-5-17)，马骏</strong> </td></tr>\n" +
                "</table>\t\n" +
                "\n" +
                "\n" +
                "<table  cellspacing=\"0\" cellpadding=\"0\" align=\"center\" border=\"0\" bordercolor=\"#111111\">\n" +
                "  <tr> \n" +
                "    <td valign=\"top\">\n" +
                "\t\n" +
                "\t<table class=\"table1\" width=\"440\" height=\"400\" cellspacing=\"0\" cellpadding=\"1\" align=\"center\" style=\"border-collapse: collapse\" border=\"1\" >\n" +
                "        <tr  height=\"30\"> \n" +
                "          <td align=\"center\" class=\"td1\">\n" +
                "            5月<br>\n" +
                "            节次 </td>\n" +
                "          <td align=\"center\" class=\"td1\">11<br />周一</td>\n" +
                "          <td align=\"center\" class=\"td1\">12<br />周二</td>\n" +
                "          <td align=\"center\" class=\"td1\">13<br />周三</td>\n" +
                "          <td align=\"center\" class=\"td1\">14<br />周四</td>\n" +
                "          <td align=\"center\" class=\"td1\">15<br />周五</td>\n" +
                "\t\t  \n" +
                "          <td align=\"center\" class=\"td1\">16<br />周六</td>\n" +
                "\t\t  \n" +
                "        </tr>\n" +
                "        \n" +
                "        \n" +
                "        <tr id=\"tr1\"> \n" +
                "          <td align=\"center\" class=\"td1\">1</td>\n" +
                "          \n" +
                "          <td align=\"center\" rowspan=4 id=\"11\"  class=\"td2\">高级数据库技术(1)班<br>[网络教学]</td>\n" +
                "          \n" +
                "          <td id=\"12\" class=\"td2\">&nbsp;</td>\n" +
                "          \n" +
                "          <td align=\"center\" rowspan=4 id=\"13\"  class=\"td2\">数据挖掘应用实践(1)班<br>[网络教学]</td>\n" +
                "          \n" +
                "          <td id=\"14\" class=\"td2\">&nbsp;</td>\n" +
                "          \n" +
                "          <td id=\"15\" class=\"td2\">&nbsp;</td>\n" +
                "          \n" +
                "          <td id=\"16\" class=\"td2\">&nbsp;</td>\n" +
                "          \n" +
                "        </tr>\n" +
                "        \n" +
                "        <tr id=\"tr2\"> \n" +
                "          <td align=\"center\" class=\"td1\">2</td>\n" +
                "          \n" +
                "          <td id=\"22\" class=\"td2\">&nbsp;</td>\n" +
                "          \n" +
                "          <td id=\"24\" class=\"td2\">&nbsp;</td>\n" +
                "          \n" +
                "          <td id=\"25\" class=\"td2\">&nbsp;</td>\n" +
                "          \n" +
                "          <td id=\"26\" class=\"td2\">&nbsp;</td>\n" +
                "          \n" +
                "        </tr>\n" +
                "        \n" +
                "        <tr id=\"tr3\"> \n" +
                "          <td align=\"center\" class=\"td1\">3</td>\n" +
                "          \n" +
                "          <td align=\"center\" rowspan=2 id=\"32\"  class=\"td2\">大学物理（上）(15)班<br>[网络教学]<br>形势与政策（六）(6)班<br>[网络教学]</td>\n" +
                "          \n" +
                "          <td id=\"34\" class=\"td2\">&nbsp;</td>\n" +
                "          \n" +
                "          <td id=\"35\" class=\"td2\">&nbsp;</td>\n" +
                "          \n" +
                "          <td id=\"36\" class=\"td2\">&nbsp;</td>\n" +
                "          \n" +
                "        </tr>\n" +
                "        \n" +
                "        <tr id=\"tr4\"> \n" +
                "          <td align=\"center\" class=\"td1\">4</td>\n" +
                "          \n" +
                "          <td id=\"44\" class=\"td2\">&nbsp;</td>\n" +
                "          \n" +
                "          <td id=\"45\" class=\"td2\">&nbsp;</td>\n" +
                "          \n" +
                "          <td id=\"46\" class=\"td2\">&nbsp;</td>\n" +
                "          \n" +
                "        </tr>\n" +
                "        \n" +
                "        <tr id=\"tr5\"> \n" +
                "          <td align=\"center\" class=\"td1\">5</td>\n" +
                "          \n" +
                "          <td id=\"51\" class=\"td2\">&nbsp;</td>\n" +
                "          \n" +
                "          <td align=\"center\" rowspan=4 id=\"52\"  class=\"td2\">大数据应用开发实践(1)班<br>[网络教学]</td>\n" +
                "          \n" +
                "          <td align=\"center\" rowspan=4 id=\"53\"  class=\"td2\">软件工程(1)班<br>[网络教学]</td>\n" +
                "          \n" +
                "          <td id=\"54\" class=\"td2\">&nbsp;</td>\n" +
                "          \n" +
                "          <td id=\"55\" class=\"td2\">&nbsp;</td>\n" +
                "          \n" +
                "          <td align=\"center\" rowspan=2 id=\"56\"  class=\"td2\">概率论与数理统计(普10)班<br>[网络教学]</td>\n" +
                "          \n" +
                "        </tr>\n" +
                "        \n" +
                "        <tr id=\"tr6\"> \n" +
                "          <td align=\"center\" class=\"td1\">6</td>\n" +
                "          \n" +
                "          <td id=\"61\" class=\"td2\">&nbsp;</td>\n" +
                "          \n" +
                "          <td id=\"64\" class=\"td2\">&nbsp;</td>\n" +
                "          \n" +
                "          <td id=\"65\" class=\"td2\">&nbsp;</td>\n" +
                "          \n" +
                "        </tr>\n" +
                "        \n" +
                "        <tr id=\"tr7\"> \n" +
                "          <td align=\"center\" class=\"td1\">7</td>\n" +
                "          \n" +
                "          <td id=\"71\" class=\"td2\">&nbsp;</td>\n" +
                "          \n" +
                "          <td id=\"74\" class=\"td2\">&nbsp;</td>\n" +
                "          \n" +
                "          <td id=\"75\" class=\"td2\">&nbsp;</td>\n" +
                "          \n" +
                "          <td id=\"76\" class=\"td2\">&nbsp;</td>\n" +
                "          \n" +
                "        </tr>\n" +
                "        \n" +
                "        <tr id=\"tr8\"> \n" +
                "          <td align=\"center\" class=\"td1\">8</td>\n" +
                "          \n" +
                "          <td id=\"81\" class=\"td2\">&nbsp;</td>\n" +
                "          \n" +
                "          <td id=\"84\" class=\"td2\">&nbsp;</td>\n" +
                "          \n" +
                "          <td id=\"85\" class=\"td2\">&nbsp;</td>\n" +
                "          \n" +
                "          <td id=\"86\" class=\"td2\">&nbsp;</td>\n" +
                "          \n" +
                "        </tr>\n" +
                "        \n" +
                "        <tr id=\"tr9\"> \n" +
                "          <td align=\"center\" class=\"td1\">9</td>\n" +
                "          \n" +
                "          <td id=\"91\" class=\"td2\">&nbsp;</td>\n" +
                "          \n" +
                "          <td id=\"92\" class=\"td2\">&nbsp;</td>\n" +
                "          \n" +
                "          <td align=\"center\" rowspan=2 id=\"93\"  class=\"td2\">概率论与数理统计(普10)班<br>[网络教学]</td>\n" +
                "          \n" +
                "          <td id=\"94\" class=\"td2\">&nbsp;</td>\n" +
                "          \n" +
                "          <td id=\"95\" class=\"td2\">&nbsp;</td>\n" +
                "          \n" +
                "          <td id=\"96\" class=\"td2\">&nbsp;</td>\n" +
                "          \n" +
                "        </tr>\n" +
                "        \n" +
                "        <tr id=\"tr10\"> \n" +
                "          <td align=\"center\" class=\"td1\">10</td>\n" +
                "          \n" +
                "          <td id=\"101\" class=\"td2\">&nbsp;</td>\n" +
                "          \n" +
                "          <td id=\"102\" class=\"td2\">&nbsp;</td>\n" +
                "          \n" +
                "          <td id=\"104\" class=\"td2\">&nbsp;</td>\n" +
                "          \n" +
                "          <td id=\"105\" class=\"td2\">&nbsp;</td>\n" +
                "          \n" +
                "          <td id=\"106\" class=\"td2\">&nbsp;</td>\n" +
                "          \n" +
                "        </tr>\n" +
                "        \n" +
                "        <tr id=\"tr11\"> \n" +
                "          <td align=\"center\" class=\"td1\">11</td>\n" +
                "          \n" +
                "          <td id=\"111\" class=\"td2\">&nbsp;</td>\n" +
                "          \n" +
                "          <td id=\"112\" class=\"td2\">&nbsp;</td>\n" +
                "          \n" +
                "          <td id=\"113\" class=\"td2\">&nbsp;</td>\n" +
                "          \n" +
                "          <td id=\"114\" class=\"td2\">&nbsp;</td>\n" +
                "          \n" +
                "          <td id=\"115\" class=\"td2\">&nbsp;</td>\n" +
                "          \n" +
                "          <td id=\"116\" class=\"td2\">&nbsp;</td>\n" +
                "          \n" +
                "        </tr>\n" +
                "        \n" +
                "      </table></td>\n" +
                "  </tr>\n" +
                "</table>\n" +
                "\t\n" +
                "</body>\n" +
                "</html>\n";
    }
}
