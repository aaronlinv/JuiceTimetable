package com.juice.timetable;

import android.annotation.SuppressLint;
import android.content.pm.ActivityInfo;
import android.os.Build;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.google.android.material.navigation.NavigationView;
import com.juice.timetable.data.JuiceDatabase;
import com.juice.timetable.data.bean.StuInfo;
import com.juice.timetable.data.dao.StuInfoDao;
import com.juice.timetable.data.http.EduInfo;
import com.juice.timetable.ui.course.CourseView;
import com.juice.timetable.utils.LogUtils;
import com.juice.timetable.utils.PreferencesUtils;
import com.juice.timetable.utils.UserInfoUtils;

public class MainActivity extends AppCompatActivity {

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
        //课表
        CourseView courseView = findViewById(R.id.course_view);

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

        // 初始化PreferencesUtils
        PreferencesUtils.init(getApplicationContext());
        String luanchFragment = getIntent().getStringExtra("luanchFragment");
        JuiceDatabase database = JuiceDatabase.getDatabase(this.getApplicationContext());
        StuInfoDao stuInfoDao = database.getStuInfoDao();
        StuInfo stu = stuInfoDao.getStuInfo();
        if (stu == null) {
            Navigation.findNavController(this, R.id.nav_host_fragment).navigate(R.id.action_nav_course_to_initFragment);
        } else {
            if (luanchFragment == null) {
                Toast.makeText(MainActivity.this, "null", Toast.LENGTH_SHORT);
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
        // Inflate the menu; this adds items to the action bar if it is present.
        // 添加周的显示
        for (int i = 1; i <= 25; i++) {
            MenuItem add = menu.add(0, i, i - 1, "第" + i + "周");
            int itemId = add.getItemId();
        }

        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onSupportNavigateUp() {
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        return NavigationUI.navigateUp(navController, mAppBarConfiguration)
                || super.onSupportNavigateUp();
    }


    public void getTimeTable() {
        // 周课表
        String uri = "http://jwb.fdzcxy.com/kb/zkb_xs.asp";
        try {
            String timeTable = EduInfo.getTimeTable(getID(), getEduPasswd(), uri, getApplicationContext());
            LogUtils.getInstance().d("课表数据：" + timeTable);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public String getID() {
        return getUserInfo("id");
    }

    public String getEduPasswd() {
        return getUserInfo("eduPasswd");
    }

    public String getLeavePasswd() {
        return getUserInfo("leavePasswd");
    }

    public String getUserInfo(String info) {
        return UserInfoUtils.getINSTANT(getApplication()).getProperty(info);
    }

}
