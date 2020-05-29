package com.juice.timetable;

import android.annotation.SuppressLint;
import android.content.pm.ActivityInfo;
import android.os.Build;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.appcompat.widget.Toolbar;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.google.android.material.navigation.NavigationView;
import com.juice.timetable.app.Constant;
import com.juice.timetable.data.bean.StuInfo;
import com.juice.timetable.data.viewmodel.StuInfoViewModel;
import com.juice.timetable.utils.BaseActivity;
import com.juice.timetable.utils.LogUtils;
import com.juice.timetable.utils.PreferencesUtils;
import com.juice.timetable.utils.UserInfoUtils;
import com.juice.timetable.widget.TodayWidget;

public class MainActivity extends BaseActivity {

    private AppBarConfiguration mAppBarConfiguration;

    @SuppressLint({"SourceLockedOrientationActivity", "ShowToast"})
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //屏幕固定为竖屏
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        // 设置状态栏为白底黑字
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            this.getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN | View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
        }
        setContentView(R.layout.activity_main);
        //toolbar
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        //主界面layoutID
        DrawerLayout drawer = findViewById(R.id.drawer_layout);

        //侧边栏
        NavigationView navigationView = findViewById(R.id.nav_view);
        // 初始化PreferencesUtils
        PreferencesUtils.init(getApplicationContext());

        // 初始化彩虹模式随机数
        Constant.RAINBOW_MODE_NUM = PreferencesUtils.getInt(Constant.PREF_RAINBOW_MODE_NUM, 0);
        // 初始化彩虹模式开关
        Constant.RAINBOW_MODE_ENABLED = PreferencesUtils.getBoolean(Constant.PREF_RAINBOW_MODE_ENABLED, false);


        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        //app bar与nav元素、navView绑定
        mAppBarConfiguration = new AppBarConfiguration.Builder(
                R.id.nav_course, R.id.nav_unsigned, R.id.nav_login, R.id.nav_about)
                .setDrawerLayout(drawer)
                .build();
        //NavController与界面绑定
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        //控制器与app bar绑定
        NavigationUI.setupActionBarWithNavController(this, navController, mAppBarConfiguration);
        NavigationUI.setupWithNavController(navigationView, navController);

        // 侧边栏橙汁图标
        NavigationView navigation = this.findViewById(R.id.nav_view);
//        navigation.inflateHeaderView(R.layout.nav_header_main);
        ImageView juiceIcon = navigationView.getHeaderView(0).findViewById(R.id.imageView);

        // 单击开启/关闭 彩虹模式
        juiceIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String toastStr = "开启彩虹模式";
                boolean enableRainbowMode = true;
                // 如果现在是开启就关闭
                if (Constant.RAINBOW_MODE_ENABLED) {
                    toastStr = "关闭彩虹模式";
                    enableRainbowMode = false;
                }
                Constant.RAINBOW_MODE_ENABLED = enableRainbowMode;
                PreferencesUtils.putBoolean(Constant.PREF_RAINBOW_MODE_ENABLED, enableRainbowMode);
                Toast.makeText(MainActivity.this, toastStr, Toast.LENGTH_SHORT).show();
            }
        });

        String luanchFragment = getIntent().getStringExtra("luanchFragment");

        // 调式模式：注入自己的账号密码，用于免登录调式
        StuInfoViewModel stuInfoViewModel = new ViewModelProvider(this).get(StuInfoViewModel.class);
        if (Constant.DEBUG_MODE) {
            final UserInfoUtils userInfoUtils = UserInfoUtils.getINSTANT(getApplicationContext());
            stuInfoViewModel.deleteStuInfo();
            StuInfo stuInfo = new StuInfo();
            stuInfo.setStuID(Integer.valueOf(userInfoUtils.getID()));
            stuInfo.setEduPassword(userInfoUtils.getEduPasswd());
            stuInfo.setLeavePassword(userInfoUtils.getLeavePasswd());
            stuInfoViewModel.insertStuInfo(stuInfo);
            LogUtils.getInstance().d("调试模式：注入学号密码结束");
        }
        // 用户校验 无用户进入初次登录界面

        StuInfo stu = stuInfoViewModel.selectStuInfo();

        // 在调试模式 或者是数据库中没有用户数据  进入首次登录界面
        if (stu == null || Constant.DEBUG_INIT_FRAGMENT) {
            Navigation.findNavController(this, R.id.nav_host_fragment).navigate(R.id.action_nav_course_to_initFragment);
        } else {
            if (luanchFragment == null) {
            } else if (luanchFragment.equals("UnsignedFragment")) {
                Navigation.findNavController(this, R.id.nav_host_fragment).navigate(R.id.nav_unsigned);
            } else if (luanchFragment.equals("EditFragment")) {
                Navigation.findNavController(this, R.id.nav_host_fragment).navigate(R.id.nav_login);
            } else if (luanchFragment.equals("AboutFragment")) {
                Navigation.findNavController(this, R.id.nav_host_fragment).navigate(R.id.nav_about);
            }
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
/*        // Inflate the menu; this adds items to the action bar if it is present.
        // 添加周的显示
        for (int i = 0; i < 25; i++) {
            menu.add(0, i, i, "第" + (i + 1) + "周");
        }*/
        // 填充toolBar的布局
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onSupportNavigateUp() {
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        return NavigationUI.navigateUp(navController, mAppBarConfiguration)
                || super.onSupportNavigateUp();
    }

    @Override
    protected void onDestroy() {
        TodayWidget.triggerUpdate(getApplicationContext());
        super.onDestroy();
    }
}
