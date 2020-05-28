package com.juice.timetable.ui.login;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.widget.Toolbar;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;

import com.dyhdyh.widget.loading.bar.LoadingBar;
import com.juice.timetable.R;
import com.juice.timetable.app.Constant;
import com.juice.timetable.data.JuiceDatabase;
import com.juice.timetable.data.bean.StuInfo;
import com.juice.timetable.data.dao.StuInfoDao;
import com.juice.timetable.data.http.EduInfo;
import com.juice.timetable.data.http.LeaveInfo;
import com.juice.timetable.databinding.FragmentLoginBinding;
import com.juice.timetable.utils.CustomLoadingFactory;
import com.juice.timetable.utils.LogUtils;
import com.juice.timetable.utils.PreferencesUtils;

import java.util.Objects;

/**
 * 修改认证页面相应功能实现类
 */
public class LoginFragment extends Fragment {
    private FragmentLoginBinding binding;
    private LoginViewModel loginViewModel;
    private DrawerLayout drawer;
    private String sno;
    private String edu;
    private String leave;
    private StuInfo stuInfo;
    private JuiceDatabase juiceDatabase;
    private StuInfoDao stuInfoDao;
    private Handler mHandler;


    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        loginViewModel = new ViewModelProvider(this).get(LoginViewModel.class);

        binding = FragmentLoginBinding.inflate(getLayoutInflater());//binding初始化
//        binding = DataBindingUtil.setContentView(requireActivity(), R.layout.fragment_login);
        drawer = requireActivity().findViewById(R.id.drawer_layout);

        // 隐藏Toolbar的下拉菜单按钮
        Toolbar toolbar = requireActivity().findViewById(R.id.toolbar);
        Menu menu = toolbar.getMenu();
        menu.setGroupVisible(0, false);

        return binding.getRoot();
    }


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        JuiceDatabase juiceDatabase = JuiceDatabase.getDatabase(getContext());
        stuInfoDao = juiceDatabase.getStuInfoDao();
        // 为输入框填充学号
        StuInfo stuInfo = stuInfoDao.getStuInfo();
        String sno = stuInfo.getStuID().toString();
        binding.etSno.setText(sno);

        handler();
        btnDialogClick();
        btoGoListen();
        drawer.addDrawerListener(new DrawerLayout.DrawerListener() {
            @Override
            public void onDrawerSlide(@NonNull View drawerView, float slideOffset) {

            }

            @Override
            public void onDrawerOpened(@NonNull View drawerView) {
                if (drawerView == null) return;
                if (isAdded()) {
                    InputMethodManager inputMethodManager = (InputMethodManager) requireContext().getApplicationContext().getSystemService(Activity.INPUT_METHOD_SERVICE);
                    inputMethodManager.hideSoftInputFromWindow(drawerView.getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
                }
            }

            @Override
            public void onDrawerClosed(@NonNull View drawerView) {

            }

            @Override
            public void onDrawerStateChanged(int newState) {

            }
        });
    }


    /**
     * 登录按钮的监听事件
     */
    private void btoGoListen() {
        binding.btnGo.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                judgmentLogic();
            }
        });
    }

    private void judgmentLogic() {
        allStr();
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
                    // 隐藏键盘
                    hideSoftKeyboard(requireActivity());
                    // 禁止登录界面点击
                    binding.btnGo.setClickable(false);
                    //设置登录按钮和用户条款按钮不可见
                    binding.btnGo.setVisibility(View.GONE);
                    binding.btnUserItem.setVisibility(View.GONE);
                    //loading显示
                    showColor(binding.btnGo);
                    checkPassword();
                }

            }

        }

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
                // 避免冲突,删除Cookie
                PreferencesUtils.clear(Constant.PREF_EDU_COOKIE);
                PreferencesUtils.clear(Constant.PREF_LEAVE_COOKIE);

                try {
                    EduInfo.getTimeTable(sno, edu, Constant.URI_CUR_WEEK, getContext().getApplicationContext());
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
    private void allStr() {
        sno = binding.etSno.getText().toString().trim();
        edu = binding.etEduPassword.getText().toString().trim();
        leave = binding.etLeavePassword.getText().toString().trim();
    }


    /**
     * 更新用户账户密码数据
     */
    private void updateUser() {
        Integer snoStr = Integer.parseInt(sno);
        StuInfo stuInfo1 = new StuInfo();
        stuInfo1.setStuID(snoStr);
        stuInfo1.setEduPassword(edu);
        stuInfo1.setEduPassword(leave);
        stuInfoDao.updateStuInfo(stuInfo1);
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

    @SuppressLint("HandlerLeak")
    private void handler() {
        mHandler = new Handler() {
            @Override
            public void handleMessage(@NonNull Message msg) {
                super.handleMessage(msg);
                switch (msg.what) {
                    // 登录成功跳转页面
                    case Constant.MSG_LOGIN_SUCCESS:
                        LogUtils.getInstance().d("接受消息：开始写入数据库");
                        updateUser();
                        LogUtils.getInstance().d("查询数据库：" + stuInfoDao.getStuInfo());

                        // 成功就导航到课表页面
                        Navigation.findNavController(requireView()).popBackStack(R.id.nav_login, true);

                        // 设置首次登录，刷新数据
                        Constant.FIRST_LOGIN = true;
                        break;
                    // 登录失败
                    case Constant.MSG_LOGIN_FAIL:
                        // 恢复登录界面点击
                        binding.btnGo.setClickable(true);
                        //关闭loading
                        LoadingBar.cancel(binding.btnGo);
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

    public void showColor(View v) {
        CustomLoadingFactory factory = new CustomLoadingFactory();
        factory.setString("更新中...");
        LoadingBar.make(binding.btnGo, factory).show();
    }

}

