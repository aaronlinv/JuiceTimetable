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
    // 未签到列表
    public static final int MSG_REFRESH = 2020;
    // 登录成功
    public static final int MSG_LOGIN_SUCCESS = 2021;

    // 指纹签到成功
    public static final int MSG_CHECK_IN_SUCCESS = 2022;

    // TODO: 2020/5/6 判断当前周
    // 当前学校周
    public static final int CUR_WEEK = 12;

    // 调式Init界面，用于调试登录界面
    public static boolean DEBUG_INIT_FRAGMENT = false;
    // 调式签到提示栏
    public static boolean DEBUG_CHECK_IN_TEXTVIEW = true;


}
