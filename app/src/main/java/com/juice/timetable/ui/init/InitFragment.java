package com.juice.timetable.ui.init;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import com.dyhdyh.widget.loading.bar.LoadingBar;
import com.juice.timetable.R;
import com.juice.timetable.app.Constant;
import com.juice.timetable.data.JuiceDatabase;
import com.juice.timetable.data.bean.OneWeekCourse;
import com.juice.timetable.data.bean.StuInfo;
import com.juice.timetable.data.dao.StuInfoDao;
import com.juice.timetable.data.http.EduInfo;
import com.juice.timetable.data.http.LeaveInfo;
import com.juice.timetable.data.parse.ParseOneWeek;
import com.juice.timetable.databinding.FragmentInitBinding;
import com.juice.timetable.utils.AesCryptUtil;
import com.juice.timetable.utils.CustomLoadingFactory;
import com.juice.timetable.utils.LogUtils;
import com.juice.timetable.utils.Utils;

import java.security.GeneralSecurityException;
import java.util.List;
import java.util.Objects;


/**
 * A simple {@link Fragment} subclass.
 */

public class InitFragment extends Fragment {
    private FragmentInitBinding binding;
    private String sno;
    private String edu;
    private String leave;
    private StuInfo stuInfo;
    private JuiceDatabase juiceDatabase;
    private StuInfoDao stuInfoDao;
    private Handler mHandler;
    private DrawerLayout drawer;
    private LoadingBar mLoadingBar;


    public InitFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentInitBinding.inflate(getLayoutInflater());

        // 禁止侧滑打开抽屉
        drawer = requireActivity().findViewById(R.id.drawer_layout);
        drawer.setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_CLOSED);

        return binding.getRoot();

    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        // 初始化数据库和Dao
        juiceDatabase = JuiceDatabase.getDatabase(requireContext());
        stuInfoDao = juiceDatabase.getStuInfoDao();
        // 删除数据库原有账号密码
        stuInfoDao.deleteStuInfo();
        btnDialogClick();
        binding.btnGo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LogUtils.getInstance().d("InitFragment:按键点击事件");

                // 密码判断逻辑
                judgmentLogic();
            }
        });
    }


    @SuppressLint("HandlerLeak")
    private void judgmentLogic() {
        // 获取三个输入框的内容
        getInput();

        if (sno.isEmpty()) {
            Toast.makeText(requireActivity(), "请输入学号", Toast.LENGTH_SHORT).show();
        } else {
            if (sno.length() != 9) {
                Toast.makeText(requireActivity(), "请输入九位数的学号", Toast.LENGTH_SHORT).show();
            } else if (!sno.matches("21\\d{7}")) {
                Toast.makeText(requireActivity(), "请输入以21开头的学号", Toast.LENGTH_SHORT).show();
            } else {
                if (edu.isEmpty()) {
                    Toast.makeText(requireActivity(), "请输入教务网密码", Toast.LENGTH_SHORT).show();
                } else if (edu.length() < 6) {
                    Toast.makeText(requireActivity(), "请输入六位及以上的教务网密码", Toast.LENGTH_SHORT).show();
                } else {
                    // 键盘隐藏
                    hideSoftKeyboard(requireActivity());
                    // 开始后端校验
                    // 禁止登录界面点击
                    binding.btnGo.setClickable(false);
                    //设置登录按钮和用户条款按钮不可见
                    binding.btnGo.setVisibility(View.GONE);
                    binding.btnUserItem.setVisibility(View.GONE);
                    //loading显示
                    showLoading(binding.btnGo);

                    checkPassword();
                }

            }

        }
        mHandler = new Handler() {
            @Override
            public void handleMessage(@NonNull Message msg) {
                super.handleMessage(msg);
                switch (msg.what) {
                    // 登录成功跳转页面
                    case Constant.MSG_LOGIN_SUCCESS:
                        //关闭loading
                        if (mLoadingBar != null) {
                            mLoadingBar.cancel();
                        }
                        // TODO 跳转页面，并调用写入数据库的方法writeAllData()
                        LogUtils.getInstance().d("接受消息：开始写入数据库");
                        writeUser();
                        LogUtils.getInstance().d("查询数据库：" + stuInfoDao.getStuInfo());
                        // 跳转结束后将debugInit置为false否则死循环
                        Constant.DEBUG_INIT_FRAGMENT = false;
                        Navigation.findNavController(requireView()).popBackStack(R.id.initFragment, true);

                        // 设置首次登录，刷新数据
                        Constant.FIRST_LOGIN = true;

                        // 允许侧滑打开抽屉
                        drawer.setDrawerLockMode(DrawerLayout.LOCK_MODE_UNLOCKED);
                        // 显示Toolbar
                        ((AppCompatActivity) getActivity()).getSupportActionBar().show();
                        break;
                    // 登录失败
                    case Constant.MSG_LOGIN_FAIL:
                        // 恢复登录界面点击
                        binding.btnGo.setClickable(true);
                        //关闭loading
                        if (mLoadingBar != null) {
                            mLoadingBar.cancel();
                        }
                        //设置登录按钮和用户条款按钮可见
                        binding.btnGo.setVisibility(View.VISIBLE);
                        binding.btnUserItem.setVisibility(View.VISIBLE);
                        String errorStr = (String) msg.obj;
                        Toast.makeText(getActivity(), errorStr, Toast.LENGTH_SHORT).show();
                        break;
                }
            }
        };

    }

    /**
     * 校验密码
     */
    private void checkPassword() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                // 教务网验证
                String errorStr = "";
                LogUtils.getInstance().d("教务网前端验证成功");
                try {
                    String timeTable = EduInfo.getTimeTable(sno, edu, Constant.URI_CUR_WEEK, getContext().getApplicationContext());
                    // 初始化当前周
                    List<OneWeekCourse> oneWeekCourses = ParseOneWeek.parseCourse(timeTable);
                    if (!oneWeekCourses.isEmpty()) {
                        Integer currentWeek = oneWeekCourses.get(0).getInWeek();
                        Utils.setFirstWeekPref(currentWeek);
                        LogUtils.getInstance().d("初始化 设置当前周为：" + currentWeek);
                    }
                } catch (Exception e) {
                    errorStr = e.getMessage();
                    LogUtils.getInstance().d("errorText:" + errorStr);
                }
                LogUtils.getInstance().d("教务网密码验证结束");


                // 填写了请假系统，并且教务密码正确 校验请假系统密码
                assert errorStr != null;
                if (!leave.isEmpty() && errorStr.isEmpty()) {
                    // 请假系统验证
                    LogUtils.getInstance().d("请假系统前端验证成功");
                    try {
                        LeaveInfo.getLeave(sno, leave, Constant.URI_CHECK_IN, getContext().getApplicationContext());
                    } catch (Exception e) {
                        errorStr = e.getMessage();
                        LogUtils.getInstance().d("errorText:" + errorStr);
                    }

                }
                LogUtils.getInstance().d("教务网和请假系统密码验证结束");
                // 跳转到课表首页
                Message message = new Message();
                assert errorStr != null;
                if (errorStr.isEmpty()) {
                    message.what = Constant.MSG_LOGIN_SUCCESS;
                } else {
                    message.what = Constant.MSG_LOGIN_FAIL;
                    message.obj = errorStr;
                }

                mHandler.sendMessage(message);
            }
        }).start();

    }

    /**
     * 提取三个文本的内容
     */
    private void getInput() {
        sno = binding.etSno.getText().toString().trim();
        edu = binding.etEduPassword.getText().toString().trim();
        leave = binding.etLeavePassword.getText().toString().trim();
    }


    private void writeUser() {
        JuiceDatabase juiceDatabase = JuiceDatabase.getDatabase(getContext());
        stuInfoDao = juiceDatabase.getStuInfoDao();

        Integer snoStr = Integer.parseInt(sno);
        try {
            //AES加密
            String edupw = AesCryptUtil.encrypt("橙子app", edu);
            String leavepw = AesCryptUtil.encrypt("abc", leave);
            //AES解密
            String educy = AesCryptUtil.decrypt("橙子app", edupw);
            String leavecy = AesCryptUtil.decrypt("abc", leavepw);
            LogUtils.getInstance().d(educy);
            LogUtils.getInstance().d(leavecy);
            LogUtils.getInstance().d(edupw);
            LogUtils.getInstance().d(leavepw);
            StuInfo stuInfo1 = new StuInfo();
            stuInfo1.setEduPassword(edupw);
            stuInfo1.setLeavePassword(leavepw);
            stuInfo1.setStuID(snoStr);
            stuInfoDao.insertStuInfo(stuInfo1);
        } catch (GeneralSecurityException e) {
            e.printStackTrace();
        }


    }

    /**
     * 点击用户协议，弹出对话框
     */
    private void btnDialogClick() {
        binding.btnUserItem.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                onDialogClick(binding.btnUserItem);
            }
        });

    }

    /**
     * 对话框的一个实现
     *
     * @param v
     */
    private void onDialogClick(View v) {
        new AlertDialog.Builder(requireContext())
                .setTitle("用户条款")
                .setMessage(R.string.user_item)
                .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int whichButton) {
                        dialog.dismiss();
                    }
                }).create().show();

    }

    /**
     * 强制隐藏软键盘
     *
     * @param activity
     */
    private static void hideSoftKeyboard(Activity activity) {
        InputMethodManager imm = (InputMethodManager) activity.getSystemService(Context.INPUT_METHOD_SERVICE);
        assert imm != null;
        imm.hideSoftInputFromWindow(Objects.requireNonNull(activity.getCurrentFocus()).getWindowToken(), 0); //强制隐藏键盘
    }

    // 屏蔽返回按键
    /*@Override
    public void onResume() {
        super.onResume();
        getView().setFocusableInTouchMode(true);
        getView().requestFocus();
        getView().setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if (keyCode == KeyEvent.KEYCODE_BACK && event.getAction() == KeyEvent.ACTION_UP) {
                    //拦截到的返回事件
                    return true;
                }
                return false;
            }
        });
    }*/
    @Override
    public void onResume() {
        super.onResume();
        getView().setFocusableInTouchMode(true);
        getView().requestFocus();
        getView().setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if (keyCode == KeyEvent.KEYCODE_BACK && event.getAction() == KeyEvent.ACTION_UP) {
                    //拦截到的返回事件
                    LogUtils.getInstance().d("Init界面 拦截返回键");
                    // 返回 true 表示已经消耗了返回的时间，返回false表示没有消耗，依旧会执行返回。
                    // true 就等于按下返回 无反应
                    // 按下直接结束退出应用
                    getActivity().finish();
                    return false;
                }
                return false;
            }

        });
        // 隐藏Toolbar -1
        ((AppCompatActivity) getActivity()).getSupportActionBar().hide();
    }

    // 隐藏Toolbar -2
    /*@Override
    public void onStop() {
        super.onStop();
        ((AppCompatActivity) getActivity()).getSupportActionBar().show();
    }*/
    private void showLoading(View v) {
        CustomLoadingFactory factory = new CustomLoadingFactory();
        factory.setString("正在登录...");
        mLoadingBar = LoadingBar.make(v.getRootView(), factory);
        mLoadingBar.show();
    }

}