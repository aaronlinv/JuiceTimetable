package com.juice.timetable.ui.login;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
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

import com.juice.timetable.R;
import com.juice.timetable.app.Constant;
import com.juice.timetable.data.JuiceDatabase;
import com.juice.timetable.data.bean.StuInfo;
import com.juice.timetable.data.dao.StuInfoDao;
import com.juice.timetable.databinding.FragmentLoginBinding;

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

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        loginViewModel = new ViewModelProvider(this).get(LoginViewModel.class);
//        View root = inflater.inflate(R.layout.fragment_login, container, false);
//        return root;
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
                    if (leave.isEmpty()) {
                        hideSoftKeyboard(requireActivity());
                        Constant.DEBUG_INIT_FRAGMENT = false;
                        Navigation.findNavController(requireView()).navigate(R.id.nav_course);
                        //Toast.makeText(getContext().getApplicationContext(), "教务网密码验证成功", Toast.LENGTH_SHORT).show();
                       /* new Thread(new Runnable() {
                            @Override
                            public void run() {

                                // 教务网验证
                                LogUtils.getInstance().d("教务网前端验证成功");
                                String uri = "http://jwb.fdzcxy.com/kb/zkb_xs.asp";
                                try {
                                    EduInfo.getTimeTable(sno, edu, uri, getContext().getApplicationContext());
                                } catch (Exception e) {
                                    String errorText = e.getMessage();
                                    LogUtils.getInstance().d("errorText:" + errorText);
                                    //解决在子线程中调用Toast的异常情况处理
                                    Looper.prepare();

                                    Toast.makeText(getContext().getApplicationContext(), errorText, Toast.LENGTH_SHORT).show();

                                    //解决在子线程中调用Toast的异常情况处理-结束需要添加这句
                                    Looper.loop();
                                }
                                LogUtils.getInstance().d("教务网密码验证成功");

                                // 跳转到课表首页
                                if (leave == "") {
                                    Looper.prepare();
                                    Toast.makeText(getContext().getApplicationContext(), "教务网密码验证成功", Toast.LENGTH_SHORT).show();
                                    Looper.loop();
                                    // 只填写了教务网密码 跳转课表首页

                                }
                            }
                        }).start();*/
                        // TODO 跳转页面，删除学号教务网密码并调用写入数据库的方法writeAllData()writeSnoEduData()

                    } else {
                        hideSoftKeyboard(requireActivity());
                        Constant.DEBUG_INIT_FRAGMENT = false;
                        Navigation.findNavController(requireView()).navigate(R.id.nav_course);

                        /*new Thread(new Runnable() {
                            @Override
                            public void run() {
                                // 教务网验证
                                LogUtils.getInstance().d("教务网前端验证成功");
                                String uri = "http://jwb.fdzcxy.com/kb/zkb_xs.asp";
                                try {
                                    EduInfo.getTimeTable(sno, edu, uri, getContext().getApplicationContext());
                                } catch (Exception e) {
                                    String errorText = e.getMessage();
                                    LogUtils.getInstance().d("errorText:" + errorText);
                                    //解决在子线程中调用Toast的异常情况处理
                                    Looper.prepare();

                                    Toast.makeText(getContext().getApplicationContext(), errorText, Toast.LENGTH_SHORT).show();

                                    //解决在子线程中调用Toast的异常情况处理-结束需要添加这句
                                    Looper.loop();
                                }
                                LogUtils.getInstance().d("教务网密码验证成功");
                                // 跳转到课表首页


                                // 请假系统验证
                                LogUtils.getInstance().d("请假系统前端验证成功");
                                uri = "http://mis.fdzcxy.com/index.php?n=stuwork-dormcheck-record-student&c=dormcheckrecordstudent";
                                try {
                                    LeaveInfo.getLeave(sno, leave, uri, getContext().getApplicationContext());
                                } catch (Exception e) {
                                    String errorText = e.getMessage();
                                    LogUtils.getInstance().d("errorText:" + errorText);
                                    //解决在子线程中调用Toast的异常情况处理
                                    Looper.prepare();
                                    Toast.makeText(getContext().getApplicationContext(), errorText, Toast.LENGTH_SHORT).show();
                                    //解决在子线程中调用Toast的异常情况处理-结束需要添加这句
                                    Looper.loop();
                                }
                                LogUtils.getInstance().d("教务网和请假系统密码均验证成功");
                                // 跳转到课表首页

                                Looper.prepare();
                                Toast.makeText(getContext().getApplicationContext(), "教务网和请假系统密码均验证成功", Toast.LENGTH_SHORT).show();
                                Looper.loop();

                            }
                        }).start();*/
                    }
                    // TODO 跳转页面，删除学号教务网密码请假系统密码，并调用写入数据库的方法writeAllData()
                    Constant.DEBUG_INIT_FRAGMENT = false;
                    Navigation.findNavController(requireView()).navigate(R.id.nav_course);
                }

            }

        }

    }

    /**
     * 提取三个文本的内容
     */
    private void allStr() {
        sno = binding.etSno.getText().toString().trim();
        edu = binding.etEduPassword.getText().toString().trim();
        leave = binding.etLeavePassword.getText().toString().trim();
    }

    private void writeSnoEduData() {
        JuiceDatabase juiceDatabase = JuiceDatabase.getDatabase(getContext());
        stuInfoDao = juiceDatabase.getStuInfoDao();
        Integer snoStr = Integer.parseInt(sno);
        StuInfo stuInfo1 = new StuInfo();
        stuInfo1.setStuID(snoStr);
        stuInfo1.setEduPassword(edu);
        stuInfoDao.insertStuInfo(stuInfo1);
    }

    private void writeAllData() {
        JuiceDatabase juiceDatabase = JuiceDatabase.getDatabase(getContext());
        stuInfoDao = juiceDatabase.getStuInfoDao();
        Integer snoStr = Integer.parseInt(sno);
        StuInfo stuInfo1 = new StuInfo();
        stuInfo1.setStuID(snoStr);
        stuInfo1.setEduPassword(edu);
        stuInfo1.setEduPassword(leave);
        stuInfoDao.insertStuInfo(stuInfo1);
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
                .setMessage("本应用深知个人信息对您的重要性，并会尽全力保护您的个人信息安全可靠。我们致力于维持您对我们的信任，恪守以下原则，保护您的个人信息：权责一致原则、目的明确原则、选择同意原则、最少够用原则、确保安全原则、主体参与原则、公开透明原则等。同时，我们承诺，我们将按业界成熟的安全标准，采取相应的安全保护措施来保护您的个人信息。 请在使用我们的产品（或服务）前，仔细阅读并了解本《隐私权条款》。\n" +
                        "一、我们如何收集和使用您的个人信息\n" +
                        "个人信息是指以电子或者其他方式记录的能够单独或者与其他信息结合识别特定自然人身份或者反映特定自然人活动情况的各种信息。 我们仅会出于本条款所述的以下目的，收集和使用您的个人信息：\n" +
                        "（一） 实际上我们不会收集您的任何个人信息，您的个人信息存在于本地数据库中。\n" +
                        "（二） 我们会对您的密码进行加密，防止数据泄露。\n" +
                        "二、我们如何使用 Cookie 和同类技术\n" +
                        "（一）Cookie\n" +
                        "为确保软件正常运转，我们会在您的移动设备上存储Cookie数据文件。Cookie 通常包含标识符、站点名称以及一些号码和字符。借助于 Cookie，软件能够存储您的个人账户信息等数据。\n" +
                        "我们不会将 Cookie 用于本条款所述目的之外的任何用途。您可根据自己的偏好管理或删除 Cookie。您可以清除设备上保存的所有 Cookie。\n" +
                        "三、我们如何共享、转让您的个人信息\n" +
                        "（一）共享\n" +
                        "我们不会向其他任何公司、组织和个人分享您的个人信息。\n" +
                        "（二）转让\n" +
                        "我们不会将您的个人信息转让给任何公司、组织和个人。\n" +
                        "四、我们如何保护您的个人信息\n" +
                        "（一）我们已使用符合业界标准的安全防护措施保护您提供的个人信息，防止数据遭到未经授权访问、公开披露、使用、修改、损坏或丢失。我们会采取一切合理可行的措施，保护您的个人信息。例如，我们会使用加密技术确保数据的保密性；我们会使用受信赖的保护机制防止数据遭到恶意攻击；我们会部署访问控制机制，确保只有用户自己才可访问个人信息。\n" +
                        "（二）我们会采取一切合理可行的措施，确保不收集无关的个人信息。我们只会在达成本条款所述目的所需的期限内保留您的个人信息，除非需要延长保留期或受到法律的允许。\n" +
                        "（三）互联网并非绝对安全的环境，而且电子邮件、即时通讯、及与其他我们用户的交流方式并未加密，我们强烈建议您不要通过此类方式发送个人信息。请使用复杂密码，增加账号的安全性。\n" +
                        "五、本隐私权条款如何更新\n" +
                        "我们可能适时会对本隐私权条款进行调整或变更，本隐私权条款的任何更新将以标注更新时间的方式公布在我们软件上，除法律法规或监管规定另有强制性规定外，经调整或变更的内容一经通知或公布后的7日后生效。如您在隐私权条款调整或变更后继续使用我们提供的任一服务或访问我们相关软件的，我们相信这代表您已充分阅读、理解并接受修改后的隐私权条款并受其约束。\n")
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


}

