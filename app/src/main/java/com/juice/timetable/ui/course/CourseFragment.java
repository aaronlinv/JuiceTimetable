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

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;
import androidx.viewpager2.widget.ViewPager2;

import com.github.florent37.viewtooltip.ViewTooltip;
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
import es.dmoral.toasty.Toasty;

import static android.animation.ObjectAnimator.ofObject;
import static es.dmoral.toasty.Toasty.LENGTH_LONG;
import static es.dmoral.toasty.Toasty.LENGTH_SHORT;

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
    int temp = 1;


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
        // 首次打开引导
        firstGuide();

        return binding.getRoot();
    }

    private void firstGuide() {
        boolean showGuide = PreferencesUtils.getBoolean(Constant.FIRST_LOGIN_GUIDE, false);
        if (showGuide) {
            LogUtils.getInstance().d("显示首次登录 引导提示");
            // 显示引导
            showGuideView();

            // 首次登录引导显示一次，然后就置为false 以后不显示
            PreferencesUtils.putBoolean(Constant.FIRST_LOGIN_GUIDE, false);
        }

    }

    private void showGuideView() {
        final int height = Utils.dip2px(requireActivity(), 40 + 4 * 55);
        // 打开抽屉的按钮，使用索引可能不太靠谱
        View views = toolbar.getChildAt(1);
        ViewTooltip.on(views)
                .position(ViewTooltip.Position.BOTTOM)
                .color(getResources().getColor(R.color.blue))
                .clickToHide(true)
                .text("打开抽屉 (点此消失)")
                .autoHide(true, 4000)
                .animation(new ViewTooltip.FadeTooltipAnimation(500))
                .onHide(new ViewTooltip.ListenerHide() {
                    @Override
                    public void onHide(View view) {
                        MaterialSpinner materialSpinner = requireActivity().findViewById(R.id.spinner);
                        ViewTooltip.on(materialSpinner)
                                .position(ViewTooltip.Position.BOTTOM)
                                .clickToHide(true)
                                .autoHide(true, 4000)
                                .color(getResources().getColor(R.color.blue))
                                .text("这里是周跳转")
                                .arrowSourceMargin(0)
                                .arrowTargetMargin(0)
                                .animation(new ViewTooltip.FadeTooltipAnimation(500))
                                .onHide(new ViewTooltip.ListenerHide() {
                                    @Override
                                    public void onHide(View view) {
                                        View item_go_current_week = requireActivity().findViewById(R.id.item_go_current_week);
                                        ViewTooltip.on(item_go_current_week)
                                                .autoHide(true, 4000)
                                                .position(ViewTooltip.Position.BOTTOM)
                                                .color(getResources().getColor(R.color.blue))
                                                .clickToHide(true)
                                                .text("回到当前周")
                                                .animation(new ViewTooltip.FadeTooltipAnimation(500))
                                                .onHide(new ViewTooltip.ListenerHide() {
                                                    @Override
                                                    public void onHide(View view) {
                                                        View item_more_option = requireActivity().findViewById(R.id.item_more_option);
                                                        ViewTooltip.on(item_more_option)
                                                                .position(ViewTooltip.Position.BOTTOM)
                                                                .autoHide(true, 4000)
                                                                .color(getResources().getColor(R.color.blue))
                                                                .clickToHide(true)
                                                                .text("更多设置")
                                                                .animation(new ViewTooltip.FadeTooltipAnimation(500))
                                                                .onHide(new ViewTooltip.ListenerHide() {
                                                                    @Override
                                                                    public void onHide(View view) {
                                                                        ViewTooltip.on(mSlRefresh)
                                                                                .position(ViewTooltip.Position.TOP)
                                                                                .autoHide(true, 5000)
                                                                                .align(ViewTooltip.ALIGN.CENTER)
                                                                                .color(getResources().getColor(R.color.blue))
                                                                                .clickToHide(true)
                                                                                .distanceWithView(-height)
                                                                                .text("下拉刷新课表，左右滑动切换周")
                                                                                .animation(new ViewTooltip.FadeTooltipAnimation(500))
                                                                                .show();
                                                                    }
                                                                })
                                                                .show();
                                                    }
                                                })
                                                .show();
                                    }
                                })
                                .show();
                    }
                }).show();

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
        if (stuInfo == null) {
            return false;
        }
        return (!stuInfo.getLeavePassword().isEmpty());
    }


    @SuppressLint("HandlerLeak")
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        handler();

        // 首次登录，获取数据并刷新界面
        if (Constant.REFRESH_DATE) {
            // 刷新动画
            mSlRefresh.setRefreshing(true);
            refreshData();
            // 设置首次登录为false
            Constant.REFRESH_DATE = false;
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
//        toolbar.hideOverflowMenu();
//        mSpinner.setVisibility(View.INVISIBLE);

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
            public void onClick(Course cou, List<Course> conflictList) {
                LogUtils.getInstance().d("课程被点击  -- > " + cou.getCouName() + " 冲突列表 --> " + conflictList);

                // 撞课处理 撞课列表大于0 为撞课
                if (conflictList.size() > 0) {
                    // 遍历周课表 查询所有撞课课程
                    StringBuilder sb = new StringBuilder();

                    for (int i = 0; i < conflictList.size(); i++) {
                        Course conflictCou = conflictList.get(i);
                        if (i > 0) {
                            sb.append("<br><br>");
                        }
                        sb.append(conflictCou.getCouName())
                                .append("<br>")
                                .append(getTeacherName(conflictCou.getCouID()))
                                .append("&nbsp;&nbsp;")
                                .append(conflictCou.getCouRoom())
                                .append("&nbsp;&nbsp;")
                                .append(conflictCou.getCouStartNode()).append("~").append(conflictCou.getCouEndNode()).append("节");
                    }


                    new SweetAlertDialog(requireActivity(), SweetAlertDialog.NORMAL_TYPE)
                            .setTitleText("<font color=\"red\">课程冲突</font>")
                            .setContentText(sb.toString())
                            .hideConfirmButton()
                            .show();
                    return;
                }

                // 周课表没有老师所以要填充
                String teacher = getTeacherName(cou.getCouID());
                // 不为空 换行
                if (!teacher.isEmpty()) {
                    teacher = teacher + "<br>";
                }
                String sb = teacher + cou.getCouRoom();

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
     *
     * @param couId
     * @return
     */
    private String getTeacherName(Long couId) {
        // 如果没有匹配到老师 就直接显示空 ""
        String teach = "";
        // 可能传入 null
        if (couId == null) {
            return teach;
        }
        List<Course> allWeekCourse = mCourseViewBeanList.get(0).getAllWeekCourse();
        for (Course course : allWeekCourse) {
            if (Objects.equals(course.getCouID(), couId)) {
                teach = course.getCouTeacher();
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
                        Toasty.custom(requireActivity(), "签到提示开启，会在签到时间段显示签到情况", getResources().getDrawable(R.drawable.course1), getResources().getColor(R.color.green), getResources().getColor(R.color.white), LENGTH_LONG, true, true).show();
                        Toasty.Config.reset();

                    } else {
                        Toasty.info(requireActivity(), "需要先在修改认证信息界面添加请假系统密码才可以开启哦", LENGTH_LONG).show();
                        isChecked = false;
                        switchCheckIn.setChecked(false);
                    }
                } else {
                    Toasty.custom(requireActivity(), "签到提示已关闭", getResources().getDrawable(R.drawable.course1), getResources().getColor(R.color.green), getResources().getColor(R.color.white), LENGTH_SHORT, true, true).show();
                    Toasty.Config.reset();
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
                    Toasty.custom(requireActivity(), "慕课显示开启，课表下方会显示所选慕课信息", getResources().getDrawable(R.drawable.course1), getResources().getColor(R.color.green), getResources().getColor(R.color.white), LENGTH_LONG, true, true).show();
                    Toasty.Config.reset();
                } else {
                    Toasty.custom(requireActivity(), "慕课显示已关闭", getResources().getDrawable(R.drawable.course1), getResources().getColor(R.color.green), getResources().getColor(R.color.white), LENGTH_SHORT, true, true).show();
                    Toasty.Config.reset();
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
                    allCourse = EduInfo.getTimeTable(stuInfo.getStuID().toString(), stuInfo.getEduPassword(), Constant.URI_WHOLE_COURSE, requireContext());
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
                    // 不为当前学期就删除 所有周课表避免冲突，完整课表下面已经删了，不用担心
                    String curSemester = PreferencesUtils.getString(Constant.CUR_SEMESTER, "");
                    LogUtils.getInstance().d("本地curSemester -- > " + curSemester);
                    LogUtils.getInstance().d("爬取curSemester -- > " + ParseAllWeek.getSemester());

                    // 爬取的学期信息与本地不同，清除周课表
                    if (!curSemester.equals(ParseAllWeek.getSemester())) {
                        mOneWeekCourseViewModel.deleteOneWeekCourse();
                        LogUtils.getInstance().d("curSemester 爬取的学期信息与本地不同，清除周课表结束");
                        // 写入学期信息
                        PreferencesUtils.putString(Constant.CUR_SEMESTER, ParseAllWeek.getSemester());
                    }

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
                            // 课表刷新有问题情况
                            Toasty.custom(requireActivity(), msgStr, getResources().getDrawable(R.drawable.ic_error), getResources().getColor(R.color.red), getResources().getColor(R.color.white), LENGTH_SHORT, true, true).show();
                            Toasty.Config.reset();
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
                            Toasty.custom(requireActivity(), "课表刷新成功", getResources().getDrawable(R.drawable.course1), getResources().getColor(R.color.green), getResources().getColor(R.color.white), LENGTH_SHORT, true, true).show();
                            Toasty.Config.reset();
                            mSlRefresh.setRefreshing(false);

                        }
                        break;
                    case Constant.MSG_CHECK_IN_SUCCESS:
                        String checkInTime = (String) msg.obj;
//                        final String checkInStr = "今天 " + checkInTime + " 已签到";
                        final String checkInStr = checkInTime + " 已签到";

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
                    case Constant.MSG_CHECK_IN_FAIL:
                        // 获取签到信息失败
                        LogUtils.getInstance().d("获取签到信息失败 开始提示Toasty");
                        // 修改通知栏文字
                        mTvCheckIn.setText("获取签到信息失败");
                        Toasty.custom(requireActivity(), "获取签到信息失败", getResources().getDrawable(R.drawable.ic_error), getResources().getColor(R.color.red), getResources().getColor(R.color.white), LENGTH_SHORT, true, true).show();
                        Toasty.Config.reset();
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
                    Message checkInMSG = new Message();
                    try {

                        String checkIn = LeaveInfo.getLeave(stuInfo.getStuID().toString(), stuInfo.getLeavePassword(), Constant.URI_CHECK_IN, requireContext());
                        LogUtils.getInstance().d("签到数据：" + checkIn);

                        MyCheckIn mySigned = ParseCheckIn.getMySigned(checkIn);
                        // 测试

//                        if (!mySigned.isCheckIn()) {
                        if (mySigned.isCheckIn()) {
                            String checkInTime = mySigned.getCheckTime();
//                            2020/5/7 需要更换为签到时间
//                            checkInTime = "21:50";
//                            checkInTime = "2020-05-31 21:50:30";

                            checkInMSG.what = Constant.MSG_CHECK_IN_SUCCESS;
                            checkInMSG.obj = checkInTime;
                            mHandler.sendMessage(checkInMSG);
                        }
//                            // 测试失败情况
//                            throw new Exception();

                    } catch (Exception e) {
                        LogUtils.getInstance().e("获取签到信息失败：" + e.getMessage());
                        checkInMSG.what = Constant.MSG_CHECK_IN_FAIL;
                        mHandler.sendMessage(checkInMSG);
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
        LogUtils.getInstance().d("allWeekCourse.size() -- > " + allWeekCourse.size());
        // 周课表课程存在完整课表中，就赋值上完整课表id
        for (OneWeekCourse oneWeekCourse : couList) {
            long color = -1;
            for (Course cou : allWeekCourse) {
                // 去除空格
                String wholeCouName = cou.getCouName().replace(" ", "");
                String oneCouName = oneWeekCourse.getCouName().replace(" ", "");
                if (wholeCouName.equals(oneCouName)) {
                    // 设置上课程id和颜色
                    oneWeekCourse.setCouID(cou.getCouID());
                    color = cou.getCouID();
                    LogUtils.getInstance().d("在完整课表中找到了该课程并修改：" + oneWeekCourse);
                    break;
                }
            }
            // 没有找到 可能是一些考试的显示
            // 取当前的完整课表的课数目为随机数
            if (color == -1) {
                LogUtils.getInstance().d("没有在完整课表中找到当前课程 -- > " + oneWeekCourse.getCouName());
                color = colorNum;
                colorNum++;
            }
            // 设置颜色
            oneWeekCourse.setColor((int) color);

            // 插入数据库
            mOneWeekCourseViewModel.insertOneWeekCourse(oneWeekCourse);
        }
        LogUtils.getInstance().d("解析本周、上两周、下两周的周课表 结束");
    }
}
