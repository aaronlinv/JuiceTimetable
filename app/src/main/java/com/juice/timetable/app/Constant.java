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

    // 停止下拉刷新的动画
    public static final int STOP_REFRESH = 2024;
    // 成功加载完整课表 周课表 weekSet 数据
    public static final int LOAD_DATA_SUCCESS = 2025;

    // TODO: 2020/5/6 判断当前周
    // 当前学校周
    public static int CUR_WEEK = 0;

    //
    public static final String PREF_EDU_COOKIE = "PREF_EDU_COOKIE";
    public static final String PREF_LEAVE_COOKIE = "PREF_LEAVE_COOKIE";
    public static final String PREF_FIRST_WEEK_MONDAY = "PREF_FIRST_WEEK_MONDAY";

    // 注入自己的账号密码，用于免登录调式
    public static boolean DEBUG_MODE = false;
    // 调式Init界面，用于调试登录界面
    public static boolean DEBUG_INIT_FRAGMENT = true;
    // 调式签到提示栏
    public static boolean DEBUG_CHECK_IN_TEXTVIEW = true;

    // 首次登录
    public static boolean FIRST_LOGIN = false;

    public static boolean FIRST_IN = true;
}
