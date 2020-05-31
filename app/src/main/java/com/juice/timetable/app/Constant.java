package com.juice.timetable.app;

/**
 * <pre>
 *     author : Aaron
 *     time   : 2020/04/29
 *     desc   : 存放常量
 *     version: 1.0
 * </pre>
 */
public class Constant {
    public static final String[] WEEK_SINGLE = {"一", "二", "三", "四", "五", "六", "日"};
    // 当前周 周课表
    public static final String URI_CUR_WEEK = "http://jwb.fdzcxy.com/kb/zkb_xs.asp";
    // 查询某一周的周课表（只能查询当前周和以及前后各两周） 后面要拼接需要查询的周
    public static final String URI_ONE_WEEK = "http://jwb.fdzcxy.com/kb/zkb_xs.asp?week1=";
    // 完整课表
    public static final String URI_WHOLE_COURSE = "http://jwb.fdzcxy.com/kb/kb_xs.asp";

    // 自己签到情况
    public static final String URI_CHECK_IN = "http://mis.fdzcxy.com/index.php?n=stuwork-dormcheck-record-student&c=dormcheckrecordstudent";
    // 班级未签
    public static final String URI_UNSIGNED_LIST = "http://mis.fdzcxy.com/index.php?n=stuwork-dormcheck-unsignin-cls&c=dormcheckclassunsignin";
    // 请假系统登录入口
    public static final String URI_LEAVE_LOGIN = "http://mis.fdzcxy.com/index.php?n=login&s=1001";

    // 登录成功
    public static final int MSG_LOGIN_SUCCESS = 2020;
    // 登录失败
    public static final int MSG_LOGIN_FAIL = 2021;

    // 未签到列表
    public static final int MSG_REFRESH = 2022;
    // 指纹签到成功
    public static final int MSG_CHECK_IN_SUCCESS = 2023;
    // 后去指纹签到失败
    public static final int MSG_CHECK_IN_FAIL = 2024;


    // 停止下拉刷新的动画
    public static final int STOP_REFRESH = 2025;

    // 彩虹模式随机数 Preferences
    public static final String PREF_RAINBOW_MODE_NUM = "PREF_RAINBOW_MODE_NUM";

    // 彩虹模式随机数 Preferences
    public static final String PREF_RAINBOW_MODE_ENABLED = "PREF_RAINBOW_MODE_ENABLED";

    // 开启签到显示 Preferences
    public static final String PREF_ENABLE_CHECK_IN = "PREF_ENABLE_CHECK_IN";

    // 开启慕课显示 Preferences
    public static final String PREF_ENABLE_SHOW_MOOC = "PREF_ENABLE_SHOW_MOOC";

    // 首次登录 Preferences 用于显示引导提示
    public static final String FIRST_LOGIN_GUIDE = "FIRST_LOGIN_GUIDE";

    // 当前学期 Preferences
    public static final String CUR_SEMESTER = "CUR_SEMESTER";

    // 当前学校周 从1开始计数 1代表第一周
    public static int CUR_WEEK = -1;
    // 最大周 默认25周
    public static final int MAX_WEEK = 25;

    //
    public static final String PREF_EDU_COOKIE = "PREF_EDU_COOKIE";
    public static final String PREF_LEAVE_COOKIE = "PREF_LEAVE_COOKIE";
    public static final String PREF_FIRST_WEEK_MONDAY = "PREF_FIRST_WEEK_MONDAY";

    // 注入自己的账号密码，用于免登录调式
    public static boolean DEBUG_MODE = true;
    // 调式Init界面，用于调试登录界面
    public static boolean DEBUG_INIT_FRAGMENT = false;
    // 调式签到提示栏
//    public static boolean DEBUG_CHECK_IN_TEXTVIEW = false;

    // 初次登录和修改认证后需要要刷新数据，每次打开也要
    public static boolean REFRESH_DATE = true;

    public static boolean FIRST_IN = true;

    // 彩虹模式的随机数 0 为关闭
    public static int RAINBOW_MODE_NUM = 10;

    // 彩虹模式开关
    public static boolean RAINBOW_MODE_ENABLED = true;

    // 签到显示 开关
    public static boolean ENABLE_CHECK_IN = true;

    // 慕课显示 开关
    public static boolean ENABLE_SHOW_MOOC = true;
}
