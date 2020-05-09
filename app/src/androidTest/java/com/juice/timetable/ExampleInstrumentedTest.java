package com.juice.timetable;

import android.content.Context;

import androidx.lifecycle.LiveData;
import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.platform.app.InstrumentationRegistry;

import com.juice.timetable.data.JuiceDatabase;
import com.juice.timetable.data.bean.ClassNoSignedItem;
import com.juice.timetable.data.bean.Course;
import com.juice.timetable.data.bean.MyCheckIn;
import com.juice.timetable.data.bean.OneWeekCourse;
import com.juice.timetable.data.bean.StuInfo;
import com.juice.timetable.data.dao.AllWeekCourseDao;
import com.juice.timetable.data.dao.ClassNoSignedItemDao;
import com.juice.timetable.data.dao.MyCheckInDao;
import com.juice.timetable.data.dao.OneWeekCourseDao;
import com.juice.timetable.data.dao.StuInfoDao;
import com.juice.timetable.utils.LogUtils;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.ArrayList;
import java.util.List;

/**
 * Instrumented test, which will execute on an Android device.
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
@RunWith(AndroidJUnit4.class)
public class ExampleInstrumentedTest {
    private LiveData<List<OneWeekCourse>> courseLive;
    private List<ClassNoSignedItem> classNoSignedItems;
    private MyCheckIn myCheckIn;
    private StuInfo stuInfo;
    private LiveData<List<Course>> allWeekCourseLive;

    private OneWeekCourseDao CourseDao;
    private ClassNoSignedItemDao classNoSignedItemDao;
    private MyCheckInDao myCheckInDao;
    private StuInfoDao stuInfoDao;
    private AllWeekCourseDao allWeekCourseDao;
    private OneWeekCourseDao OneWeekCourseDao;

    @Before
    public void initDataBase() {
        // 初始化
        Context appContext = InstrumentationRegistry.getInstrumentation().getTargetContext();
        //生成数据库，如果数据库已有，则调用
        JuiceDatabase juiceDatabase = JuiceDatabase.getDatabase(appContext);
        //生成对应的Dao
        CourseDao = juiceDatabase.getOneWeekCourseDao();
        classNoSignedItemDao = juiceDatabase.getClassNoSignedItemDao();
        myCheckInDao = juiceDatabase.getMyCheckInDao();
        stuInfoDao = juiceDatabase.getStuInfoDao();
        allWeekCourseDao = juiceDatabase.getAllWeekCourseDao();
        OneWeekCourseDao = juiceDatabase.getOneWeekCourseDao();
    }

    public static String getMyCheckInStr() {
        return "<!doctype html public '-//w3c//dtd html 4.01 transitional//en' 'http://www.w3.org/tr/html4/loose.dtd' >\n" +
                "<html >\n" +
                "\t<head >\n" +
                "\t\t<meta content=\"text/html; charset=UTF-8\" http-equiv=\"Content-Type\" >\n" +
                "\t\t<title >签到记录 - 至诚信息管理系统</title>\n" +
                "\t\t<link type=\"text/css\" rel=\"stylesheet\" href=\"Public/css/common/common.css?ver=2.11\" >\n" +
                "\t\t<script type=\"text/javascript\" src=\"Public/js/common/jquery-1.11.1.min.js?ver=2.11\" ></script>\n" +
                "\t\t<script type=\"text/javascript\" src=\"Public/js/common/common.js?ver=2.11\" ></script>\n" +
                "\t</head>\n" +
                "\t<body >\n" +
                "\t\t<div class=\"place\" >\n" +
                "\t\t\t<span >位置：</span>\n" +
                "\t\t\t<ul class=\"placeul\" >\n" +
                "\t\t\t\t<li >\n" +
                "\t\t\t\t\t<a href=\"index.php?n=main&c=mainpage\" >首页</a>\n" +
                "\t\t\t\t</li>\n" +
                "\t\t\t\t<li >\n" +
                "\t\t\t\t\t<a href=\"\" ></a>\n" +
                "\t\t\t\t</li>\n" +
                "\t\t\t\t<li >\n" +
                "\t\t\t\t\t<a href=\"#\" >学生工作</a>\n" +
                "\t\t\t\t</li>\n" +
                "\t\t\t\t<li >\n" +
                "\t\t\t\t\t<a href=\"#\" >晚间查房</a>\n" +
                "\t\t\t\t</li>\n" +
                "\t\t\t\t<li >\n" +
                "\t\t\t\t\t<a href=\"index.php?n=stuwork-dormcheck-record-student&c=dormcheckrecordstudent\" >签到记录</a>\n" +
                "\t\t\t\t</li>\n" +
                "\t\t\t</ul>\n" +
                "\t\t</div>\n" +
                "\t\t<div class=\"wrapper\" >\n" +
                "\t\t\t<div class=\"formtitle\" >\n" +
                "\t\t\t\t<span >签到记录</span>\n" +
                "\t\t\t</div>\n" +
                "\t\t\t<table class=\"tablelist\" >\n" +
                "\t\t\t\t<thead >\n" +
                "\t\t\t\t\t<tr >\n" +
                "\t\t\t\t\t\t<th class=\"thSN\" >序号</th>\n" +
                "\t\t\t\t\t\t<th class=\"thDay\" >日期</th>\n" +
                "\t\t\t\t\t\t<th class=\"thTime\" >签到时间</th>\n" +
                "\t\t\t\t\t\t<th >未签原因</th>\n" +
                "\t\t\t\t\t</tr>\n" +
                "\t\t\t\t</thead>\n" +
                "\t\t\t\t<tbody >\n" +
                "\t\t\t\t\t<tr class=\"odd\" >\n" +
                "\t\t\t\t\t\t<td ></td>\n" +
                "\t\t\t\t\t\t<td >2020-04-28</td>\n" +
                "\t\t\t\t\t\t<td >未签</td>\n" +
                "\t\t\t\t\t\t<td ></td>\n" +
                "\t\t\t\t\t</tr>\n" +
                "\t\t\t\t\t<tr class=\"even\" >\n" +
                "\t\t\t\t\t\t<td ></td>\n" +
                "\t\t\t\t\t\t<td >2020-04-27</td>\n" +
                "\t\t\t\t\t\t<td >未签</td>\n" +
                "\t\t\t\t\t\t<td ></td>\n" +
                "\t\t\t\t\t</tr>\n" +
                "\t\t\t\t\t<tr class=\"odd\" >\n" +
                "\t\t\t\t\t\t<td ></td>\n" +
                "\t\t\t\t\t\t<td >2020-04-26</td>\n" +
                "\t\t\t\t\t\t<td >未签</td>\n" +
                "\t\t\t\t\t\t<td ></td>\n" +
                "\t\t\t\t\t</tr>\n" +
                "\t\t\t\t\t<tr class=\"even\" >\n" +
                "\t\t\t\t\t\t<td ></td>\n" +
                "\t\t\t\t\t\t<td >2020-04-25</td>\n" +
                "\t\t\t\t\t\t<td >未签</td>\n" +
                "\t\t\t\t\t\t<td ></td>\n" +
                "\t\t\t\t\t</tr>\n" +
                "\t\t\t\t\t<tr class=\"odd\" >\n" +
                "\t\t\t\t\t\t<td ></td>\n" +
                "\t\t\t\t\t\t<td >2020-04-24</td>\n" +
                "\t\t\t\t\t\t<td >未签</td>\n" +
                "\t\t\t\t\t\t<td ></td>\n" +
                "\t\t\t\t\t</tr>\n" +
                "\t\t\t\t\t<tr class=\"even\" >\n" +
                "\t\t\t\t\t\t<td ></td>\n" +
                "\t\t\t\t\t\t<td >2020-04-23</td>\n" +
                "\t\t\t\t\t\t<td >未签</td>\n" +
                "\t\t\t\t\t\t<td ></td>\n" +
                "\t\t\t\t\t</tr>\n" +
                "\t\t\t\t\t<tr class=\"odd\" >\n" +
                "\t\t\t\t\t\t<td ></td>\n" +
                "\t\t\t\t\t\t<td >2020-04-22</td>\n" +
                "\t\t\t\t\t\t<td >已签</td>\n" +
                "\t\t\t\t\t\t<td ></td>\n" +
                "\t\t\t\t\t</tr>\n" +
                "\t\t\t\t</tbody>\n" +
                "\t\t\t</table>\n" +
                "\t\t</div>\n" +
                "\t</body>\n" +
                "</html>\n";
    }

    @Test
   /*  public void useAppContext() {
        // Context of the app under test.
        Context appContext = InstrumentationRegistry.getInstrumentation().getTargetContext();

        assertEquals("com.juice.timetable", appContext.getPackageName());
        //生成数据库，如果数据库已有，则调用
        JuiceDatabase juiceDatabase = JuiceDatabase.getDatabase(appContext);
        //生成对应的Dao
        CourseDao = juiceDatabase.getOneWeekCourseDao();
        classNoSignedItemDao = juiceDatabase.getClassNoSignedItemDao();
        myCheckInDao = juiceDatabase.getMyCheckInDao();
        stuInfoDao = juiceDatabase.getStuInfoDao();
        allWeekCourseDao = juiceDatabase.getAllWeekCourseDao();
        //插入数据
        List<OneWeekCourse> a = ParseOneWeek.parseCourse(getStr());
        for (OneWeekCourse oneWeekCourse : a) {
            CourseDao.insertCourse(oneWeekCourse);
        }
       List<ClassNoSignedItem> b = ParseClassNoSignedItem.getClassUnSigned();
        for (ClassNoSignedItem classNoSignedItem : b) {
            classNoSignedItemDao.insertNoSignedItem(classNoSignedItem);
        }
        MyCheckIn myCheckIn1 = ParseCheckIn.getMySigned(getMyCheckInStr());
        myCheckInDao.insertMyCheckIn(myCheckIn1);
        StuInfo stuInfo1 = new StuInfo();
        stuInfo1.setStuID(211706162);
        stuInfo1.setEduPassword("123456");
        stuInfo1.setLeavePassword("123456");
        stuInfoDao.insertStuInfo(stuInfo1);
        List<Course> c = ParseAllWeek.parseAllCourse(getAllCourseStr2018());
        for (Course course : c) {
            allWeekCourseDao.insertAllWeekCourse(course);
        }
    }*/

    public static String getAllCourseStr() {
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
                "<div align=\"center\"><strong>福州大学至诚学院 2019下学期张三同学课程表</strong>(2020-4-28 14:45:12)</div>\n" +
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
                "          <td align=\"center\">01～14</td>\t\n" +
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
                "          <td  >&nbsp;软件工程 (1)班</td>\n" +
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
                "          <td align=\"center\">01～13</td>\t\n" +
                "        </tr>\n" +
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
                "          <td align=\"center\">00～00</td>\t\n" +
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
                "          <td rowspan=\"2\"  >&nbsp;中国古典诗词中的品格与修养 (121)班</td>\n" +
                "          <td align=\"center\"><a href=\"../kkgl/kcjd/dgjdb.asp?lx=dg&kkxq=2019%E4%B8%8B&kcdm=80000117&kkhm=2019%E4%B8%8B80000117121\" target=\"_blank\">大纲</a>\n" +
                "\t\t  /<a href=\"../kkgl/kcjd/dgjdb.asp?lx=jdb&kkxq=2019%E4%B8%8B&kcdm=80000117&kkhm=2019%E4%B8%8B80000117121\" target=\"_blank\">进度表</a></td>\t\t\t\n" +
                "          <td align=\"center\"></td>\n" +
                "\t\t  <td align=\"center\">院选课</td>\t\t  \n" +
                "\t\t  <td align=\"center\">正常考考试</td>\t\t\t\t\t  \n" +
                "\t\t  <td align=\"center\">院选课智慧树</td>\t\t\t\t\t  \n" +
                "\t\t  <td align=\"center\">2</td>\n" +
                "          <td align=\"center\">32</td>\n" +
                "          <td align=\"center\"></td>\n" +
                "          <td align=\"center\"></td>\n" +
                "          <td align=\"center\"></td>\t\n" +
                "        </tr>\n" +
                "       \n" +
                "        <tr height=\"25\"><td align=\"left\" colspan=\"10\">★备注：期末成绩=平时学习40%+学院组织的线下考试60%</td></tr>\n" +
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
                "          <td align=\"center\" rowspan=2 id=\"32\">形势与政策（六）(6)班<br>[网络教学]</td>\n" +
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
                "          <td align=\"center\" rowspan=2 id=\"51\">软件工程(1)班<br>[网络教学]</td>\n" +
                "          \n" +
                "          <td align=\"center\" rowspan=4 id=\"52\">大数据应用开发(1)班<br>[网络教学]<br>大数据应用开发实践(1)班<br>[网络教学]<br>大数据应用开发实践(1)班<br>[网络教学]<br>[5~6节]<br>(17-17周)</td>\n" +
                "          \n" +
                "          <td align=\"center\" rowspan=4 id=\"53\">软件工程(1)班<br>[网络教学]<br>(05-08周)<br>软件工程(1)班<br>[网络教学]<br>(10-10周)<br>云计算与数据中心(1)班<br>[网络教学]</td>\n" +
                "          \n" +
                "          <td align=\"center\" rowspan=4 id=\"54\">大数据综合应用案例实训(1)班<br>[网络教学]<br>大数据综合应用案例实训(1)班<br>[网络教学]<br>[5~6节]<br>(13-13周)</td>\n" +
                "          \n" +
                "          <td id=\"55\">&nbsp;</td>\n" +
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
                "        </tr>\n" +
                "        \n" +
                "        <tr id=\"tr8\"> \n" +
                "          <td align=\"center\">8</td>\n" +
                "          \n" +
                "          <td id=\"81\">&nbsp;</td>\n" +
                "          \n" +
                "          <td id=\"85\">&nbsp;</td>\n" +
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
                "          <td id=\"93\">&nbsp;</td>\n" +
                "          \n" +
                "          <td id=\"94\">&nbsp;</td>\n" +
                "          \n" +
                "          <td id=\"95\">&nbsp;</td>\n" +
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
                "          <td id=\"103\">&nbsp;</td>\n" +
                "          \n" +
                "          <td id=\"104\">&nbsp;</td>\n" +
                "          \n" +
                "          <td id=\"105\">&nbsp;</td>\n" +
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
                "        </tr>\n" +
                "        \n" +
                "      </table></td>\n" +
                "  </tr>\n" +
                "</table>\n" +
                "\t\n" +
                "</body>\n" +
                "</html>\n" +
                "\n";
    }

    public static String getAllCourseStr2018() {
        return "\n" +
                "\n" +
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
                "&nbsp;查询学期：<input name=\"xn\" type=\"text\" size=\"4\" value=\"2018\">学年 \n" +
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
                "<div align=\"center\"><strong>福州大学至诚学院 2018下学期蔡泽华同学课程表</strong>(2020-4-30 16:52:31)</div>\n" +
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
                "          <td  >&nbsp;大学英语（四） (35)班</td>\n" +
                "          <td align=\"center\"><a href=\"../kkgl/kcjd/dgjdb.asp?lx=dg&kkxq=2018%E4%B8%8B&kcdm=06000108&kkhm=2018%E4%B8%8B06000108035\" target=\"_blank\">大纲</a>\n" +
                "\t\t  /<a href=\"../kkgl/kcjd/dgjdb.asp?lx=jdb&kkxq=2018%E4%B8%8B&kcdm=06000108&kkhm=2018%E4%B8%8B06000108035\" target=\"_blank\">进度表</a></td>\t\t\t\n" +
                "          <td align=\"center\">林丽珍</td>\n" +
                "\t\t  <td align=\"center\">公共必修</td>\t\t  \n" +
                "\t\t  <td align=\"center\">正常考考试</td>\t\t\t\t\t  \n" +
                "\t\t  <td align=\"center\">35班</td>\t\t\t\t\t  \n" +
                "\t\t  <td align=\"center\">3</td>\n" +
                "          <td align=\"center\">48</td>\n" +
                "          <td align=\"center\">3</td>\n" +
                "          <td align=\"center\"></td>\n" +
                "          <td align=\"center\">01～16</td>\t\n" +
                "        </tr>\n" +
                "       \n" +
                "        <tr> \n" +
                "          <td  >&nbsp;概率论与数理统计 (10)班</td>\n" +
                "          <td align=\"center\"><a href=\"../kkgl/kcjd/dgjdb.asp?lx=dg&kkxq=2018%E4%B8%8B&kcdm=09000102&kkhm=2018%E4%B8%8B09000102010\" target=\"_blank\">大纲</a>\n" +
                "\t\t  /<a href=\"../kkgl/kcjd/dgjdb.asp?lx=jdb&kkxq=2018%E4%B8%8B&kcdm=09000102&kkhm=2018%E4%B8%8B09000102010\" target=\"_blank\">进度表</a></td>\t\t\t\n" +
                "          <td align=\"center\">陈江彬</td>\n" +
                "\t\t  <td align=\"center\">公共必修</td>\t\t  \n" +
                "\t\t  <td align=\"center\">正常考考试</td>\t\t\t\t\t  \n" +
                "\t\t  <td align=\"center\">10班</td>\t\t\t\t\t  \n" +
                "\t\t  <td align=\"center\">3</td>\n" +
                "          <td align=\"center\">54</td>\n" +
                "          <td align=\"center\">4</td>\n" +
                "          <td align=\"center\"></td>\n" +
                "          <td align=\"center\">01～15</td>\t\n" +
                "        </tr>\n" +
                "       \n" +
                "        <tr> \n" +
                "          <td  >&nbsp;马克思主义基本原理概论 (19)班</td>\n" +
                "          <td align=\"center\"><a href=\"../kkgl/kcjd/dgjdb.asp?lx=dg&kkxq=2018%E4%B8%8B&kcdm=07004113&kkhm=2018%E4%B8%8B07004113019\" target=\"_blank\">大纲</a>\n" +
                "\t\t  /<a href=\"../kkgl/kcjd/dgjdb.asp?lx=jdb&kkxq=2018%E4%B8%8B&kcdm=07004113&kkhm=2018%E4%B8%8B07004113019\" target=\"_blank\">进度表</a></td>\t\t\t\n" +
                "          <td align=\"center\">袁小云,郑夏妍</td>\n" +
                "\t\t  <td align=\"center\">公共必修</td>\t\t  \n" +
                "\t\t  <td align=\"center\">正常考考试</td>\t\t\t\t\t  \n" +
                "\t\t  <td align=\"center\">19班,周5 5~7</td>\t\t\t\t\t  \n" +
                "\t\t  <td align=\"center\">3</td>\n" +
                "          <td align=\"center\">48</td>\n" +
                "          <td align=\"center\"></td>\n" +
                "          <td align=\"center\"></td>\n" +
                "          <td align=\"center\">01～16</td>\t\n" +
                "        </tr>\n" +
                "       \n" +
                "        <tr> \n" +
                "          <td  >&nbsp;体育（四） (31)班</td>\n" +
                "          <td align=\"center\"><a href=\"../kkgl/kcjd/dgjdb.asp?lx=dg&kkxq=2018%E4%B8%8B&kcdm=06000125&kkhm=2018%E4%B8%8B06000125031\" target=\"_blank\">大纲</a>\n" +
                "\t\t  /<a href=\"../kkgl/kcjd/dgjdb.asp?lx=jdb&kkxq=2018%E4%B8%8B&kcdm=06000125&kkhm=2018%E4%B8%8B06000125031\" target=\"_blank\">进度表</a></td>\t\t\t\n" +
                "          <td align=\"center\">李粲</td>\n" +
                "\t\t  <td align=\"center\">公共必修</td>\t\t  \n" +
                "\t\t  <td align=\"center\">正常考考试</td>\t\t\t\t\t  \n" +
                "\t\t  <td align=\"center\">31班,周3,3-4节,排球（男）</td>\t\t\t\t\t  \n" +
                "\t\t  <td align=\"center\">1</td>\n" +
                "          <td align=\"center\">36</td>\n" +
                "          <td align=\"center\"></td>\n" +
                "          <td align=\"center\"></td>\n" +
                "          <td align=\"center\">02～15</td>\t\n" +
                "        </tr>\n" +
                "       \n" +
                "        <tr> \n" +
                "          <td  >&nbsp;形势与政策（四） (16)班</td>\n" +
                "          <td align=\"center\"><a href=\"../kkgl/kcjd/dgjdb.asp?lx=dg&kkxq=2018%E4%B8%8B&kcdm=08000106&kkhm=2018%E4%B8%8B08000106016\" target=\"_blank\">大纲</a>\n" +
                "\t\t  /<a href=\"../kkgl/kcjd/dgjdb.asp?lx=jdb&kkxq=2018%E4%B8%8B&kcdm=08000106&kkhm=2018%E4%B8%8B08000106016\" target=\"_blank\">进度表</a></td>\t\t\t\n" +
                "          <td align=\"center\">孙大为</td>\n" +
                "\t\t  <td align=\"center\">公共必修</td>\t\t  \n" +
                "\t\t  <td align=\"center\">正常考考试</td>\t\t\t\t\t  \n" +
                "\t\t  <td align=\"center\">16班,周四,1-2节</td>\t\t\t\t\t  \n" +
                "\t\t  <td align=\"center\">.5</td>\n" +
                "          <td align=\"center\">8</td>\n" +
                "          <td align=\"center\"></td>\n" +
                "          <td align=\"center\"></td>\n" +
                "          <td align=\"center\">15～18</td>\t\n" +
                "        </tr>\n" +
                "       \n" +
                "        <tr> \n" +
                "          <td  >&nbsp;计算机组成原理 (2)班</td>\n" +
                "          <td align=\"center\"><a href=\"../kkgl/kcjd/dgjdb.asp?lx=dg&kkxq=2018%E4%B8%8B&kcdm=10111203&kkhm=2018%E4%B8%8B10111203002\" target=\"_blank\">大纲</a>\n" +
                "\t\t  /<a href=\"../kkgl/kcjd/dgjdb.asp?lx=jdb&kkxq=2018%E4%B8%8B&kcdm=10111203&kkhm=2018%E4%B8%8B10111203002\" target=\"_blank\">进度表</a></td>\t\t\t\n" +
                "          <td align=\"center\">马云莺</td>\n" +
                "\t\t  <td align=\"center\">专业基础</td>\t\t  \n" +
                "\t\t  <td align=\"center\">正常考考试</td>\t\t\t\t\t  \n" +
                "\t\t  <td align=\"center\">2017级计算机2</td>\t\t\t\t\t  \n" +
                "\t\t  <td align=\"center\">4</td>\n" +
                "          <td align=\"center\">64</td>\n" +
                "          <td align=\"center\">4</td>\n" +
                "          <td align=\"center\">*</td>\n" +
                "          <td align=\"center\">01～17</td>\t\n" +
                "        </tr>\n" +
                "       \n" +
                "        <tr> \n" +
                "          <td  >&nbsp;算法与数据结构 (2)班</td>\n" +
                "          <td align=\"center\"><a href=\"../kkgl/kcjd/dgjdb.asp?lx=dg&kkxq=2018%E4%B8%8B&kcdm=06111206&kkhm=2018%E4%B8%8B06111206002\" target=\"_blank\">大纲</a>\n" +
                "\t\t  /<a href=\"../kkgl/kcjd/dgjdb.asp?lx=jdb&kkxq=2018%E4%B8%8B&kcdm=06111206&kkhm=2018%E4%B8%8B06111206002\" target=\"_blank\">进度表</a></td>\t\t\t\n" +
                "          <td align=\"center\">杨雄</td>\n" +
                "\t\t  <td align=\"center\">专业基础</td>\t\t  \n" +
                "\t\t  <td align=\"center\">正常考考试</td>\t\t\t\t\t  \n" +
                "\t\t  <td align=\"center\">2017级计算机2</td>\t\t\t\t\t  \n" +
                "\t\t  <td align=\"center\">4.5</td>\n" +
                "          <td align=\"center\">72</td>\n" +
                "          <td align=\"center\">5</td>\n" +
                "          <td align=\"center\">24</td>\n" +
                "          <td align=\"center\">01～16</td>\t\n" +
                "        </tr>\n" +
                "       \n" +
                "        <tr> \n" +
                "          <td  >&nbsp;面向对象程序设计 (1)班</td>\n" +
                "          <td align=\"center\"><a href=\"../kkgl/kcjd/dgjdb.asp?lx=dg&kkxq=2018%E4%B8%8B&kcdm=06111305&kkhm=2018%E4%B8%8B06111305001\" target=\"_blank\">大纲</a>\n" +
                "\t\t  /<a href=\"../kkgl/kcjd/dgjdb.asp?lx=jdb&kkxq=2018%E4%B8%8B&kcdm=06111305&kkhm=2018%E4%B8%8B06111305001\" target=\"_blank\">进度表</a></td>\t\t\t\n" +
                "          <td align=\"center\">杨晓花</td>\n" +
                "\t\t  <td align=\"center\">专业素质课</td>\t\t  \n" +
                "\t\t  <td align=\"center\">正常考考试</td>\t\t\t\t\t  \n" +
                "\t\t  <td align=\"center\">2017级计算机1</td>\t\t\t\t\t  \n" +
                "\t\t  <td align=\"center\">3</td>\n" +
                "          <td align=\"center\">48</td>\n" +
                "          <td align=\"center\">3</td>\n" +
                "          <td align=\"center\">16</td>\n" +
                "          <td align=\"center\">01～17</td>\t\n" +
                "        </tr>\n" +
                "       \n" +
                "        <tr> \n" +
                "          <td rowspan=\"2\"  >&nbsp;计算机组成原理实验 (2)班</td>\n" +
                "          <td align=\"center\"><a href=\"../kkgl/kcjd/dgjdb.asp?lx=dg&kkxq=2018%E4%B8%8B&kcdm=07102603&kkhm=2018%E4%B8%8B07102603002\" target=\"_blank\">大纲</a>\n" +
                "\t\t  /<a href=\"../kkgl/kcjd/dgjdb.asp?lx=jdb&kkxq=2018%E4%B8%8B&kcdm=07102603&kkhm=2018%E4%B8%8B07102603002\" target=\"_blank\">进度表</a></td>\t\t\t\n" +
                "          <td align=\"center\">马云莺</td>\n" +
                "\t\t  <td align=\"center\">实践环节</td>\t\t  \n" +
                "\t\t  <td align=\"center\">正常考考试</td>\t\t\t\t\t  \n" +
                "\t\t  <td align=\"center\">2017级计算机2</td>\t\t\t\t\t  \n" +
                "\t\t  <td align=\"center\">1</td>\n" +
                "          <td align=\"center\">24</td>\n" +
                "          <td align=\"center\">√</td>\n" +
                "          <td align=\"center\"></td>\n" +
                "          <td align=\"center\">10～15</td>\t\n" +
                "        </tr>\n" +
                "       \n" +
                "        <tr height=\"25\"><td align=\"left\" colspan=\"10\">★备注：必修，具体时间由任课教师分批进行</td></tr>\n" +
                "       \n" +
                "        <tr> \n" +
                "          <td  >&nbsp;面向对象程序设计实训 (1)班</td>\n" +
                "          <td align=\"center\"><a href=\"../kkgl/kcjd/dgjdb.asp?lx=dg&kkxq=2018%E4%B8%8B&kcdm=10111605&kkhm=2018%E4%B8%8B10111605003\" target=\"_blank\">大纲</a>\n" +
                "\t\t  /<a href=\"../kkgl/kcjd/dgjdb.asp?lx=jdb&kkxq=2018%E4%B8%8B&kcdm=10111605&kkhm=2018%E4%B8%8B10111605003\" target=\"_blank\">进度表</a></td>\t\t\t\n" +
                "          <td align=\"center\">杨晓花</td>\n" +
                "\t\t  <td align=\"center\">实践环节</td>\t\t  \n" +
                "\t\t  <td align=\"center\">正常考考试</td>\t\t\t\t\t  \n" +
                "\t\t  <td align=\"center\">2017级计算机1</td>\t\t\t\t\t  \n" +
                "\t\t  <td align=\"center\">1.5</td>\n" +
                "          <td align=\"center\">30</td>\n" +
                "          <td align=\"center\"></td>\n" +
                "          <td align=\"center\"></td>\n" +
                "          <td align=\"center\">08～15</td>\t\n" +
                "        </tr>\n" +
                "       \n" +
                "        <tr> \n" +
                "          <td  >&nbsp;思想政治理论课实践(二)</td>\n" +
                "          <td align=\"center\"><a href=\"../kkgl/kcjd/dgjdb.asp?lx=dg&kkxq=2018%E4%B8%8B&kcdm=07004608&kkhm=2018%E4%B8%8B07004608020\" target=\"_blank\">大纲</a>\n" +
                "\t\t  /<a href=\"../kkgl/kcjd/dgjdb.asp?lx=jdb&kkxq=2018%E4%B8%8B&kcdm=07004608&kkhm=2018%E4%B8%8B07004608020\" target=\"_blank\">进度表</a></td>\t\t\t\n" +
                "          <td align=\"center\">王海霞</td>\n" +
                "\t\t  <td align=\"center\">实践环节</td>\t\t  \n" +
                "\t\t  <td align=\"center\">正常考考试</td>\t\t\t\t\t  \n" +
                "\t\t  <td align=\"center\">2017计算机</td>\t\t\t\t\t  \n" +
                "\t\t  <td align=\"center\">1</td>\n" +
                "          <td align=\"center\"></td>\n" +
                "          <td align=\"center\"></td>\n" +
                "          <td align=\"center\"></td>\n" +
                "          <td align=\"center\"></td>\t\n" +
                "        </tr>\n" +
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
                "        </tr>\n" +
                "        \n" +
                "        \n" +
                "        <tr id=\"tr1\"> \n" +
                "          <td align=\"center\">1</td>\n" +
                "          \n" +
                "          <td id=\"11\">&nbsp;</td>\n" +
                "          \n" +
                "          <td align=\"center\" rowspan=2 id=\"12\">概率论与数理统计(10)班<br>[东教401]</td>\n" +
                "          \n" +
                "          <td align=\"center\" rowspan=2 id=\"13\">大学英语（四）(35)班<br>[东教108]</td>\n" +
                "          \n" +
                "          <td align=\"center\" rowspan=2 id=\"14\">概率论与数理统计(10)班<br>[化工314]<br>形势与政策（四）(16)班<br>[机北306]</td>\n" +
                "          \n" +
                "          <td id=\"15\">&nbsp;</td>\n" +
                "          \n" +
                "        </tr>\n" +
                "        \n" +
                "        <tr id=\"tr2\"> \n" +
                "          <td align=\"center\">2</td>\n" +
                "          \n" +
                "          <td id=\"21\">&nbsp;</td>\n" +
                "          \n" +
                "          <td id=\"25\">&nbsp;</td>\n" +
                "          \n" +
                "        </tr>\n" +
                "        \n" +
                "        <tr id=\"tr3\"> \n" +
                "          <td align=\"center\">3</td>\n" +
                "          \n" +
                "          <td align=\"center\" rowspan=2 id=\"31\">算法与数据结构(2)班<br>[轻工614]<br>(05-16周)</td>\n" +
                "          \n" +
                "          <td align=\"center\" rowspan=2 id=\"32\">大学英语（四）(35)班<br>[单][电机楼502]</td>\n" +
                "          \n" +
                "          <td align=\"center\" rowspan=2 id=\"33\">体育（四）(31)班<br>[体育场]</td>\n" +
                "          \n" +
                "          <td align=\"center\" rowspan=2 id=\"34\">算法与数据结构(2)班<br>[双][化工317]</td>\n" +
                "          \n" +
                "          <td align=\"center\" rowspan=2 id=\"35\">计算机组成原理(2)班<br>[化工317]</td>\n" +
                "          \n" +
                "        </tr>\n" +
                "        \n" +
                "        <tr id=\"tr4\"> \n" +
                "          <td align=\"center\">4</td>\n" +
                "          \n" +
                "        </tr>\n" +
                "        \n" +
                "        <tr id=\"tr5\"> \n" +
                "          <td align=\"center\">5</td>\n" +
                "          \n" +
                "          <td align=\"center\" rowspan=2 id=\"51\">面向对象程序设计(1)班<br>[化工304]</td>\n" +
                "          \n" +
                "          <td align=\"center\" rowspan=2 id=\"52\">计算机组成原理(2)班<br>[化工304]</td>\n" +
                "          \n" +
                "          <td align=\"center\" rowspan=2 id=\"53\">面向对象程序设计(1)班<br>[轻工616]<br>(04-07周)<br>面向对象程序设计(1)班<br>[化工314]<br>(01-03周)<br>面向对象程序设计实训(1)班<br>[双][轻工616]<br>[5~8节]<br>面向对象程序设计实训(1)班<br>[轻工613]<br>[5~8节]<br>(16-17周)</td>\n" +
                "          \n" +
                "          <td id=\"54\">&nbsp;</td>\n" +
                "          \n" +
                "          <td align=\"center\" rowspan=3 id=\"55\">马克思主义基本原理概论(19)班<br>[东教301]</td>\n" +
                "          \n" +
                "        </tr>\n" +
                "        \n" +
                "        <tr id=\"tr6\"> \n" +
                "          <td align=\"center\">6</td>\n" +
                "          \n" +
                "          <td id=\"64\">&nbsp;</td>\n" +
                "          \n" +
                "        </tr>\n" +
                "        \n" +
                "        <tr id=\"tr7\"> \n" +
                "          <td align=\"center\">7</td>\n" +
                "          \n" +
                "          <td id=\"71\">&nbsp;</td>\n" +
                "          \n" +
                "          <td align=\"center\" rowspan=2 id=\"72\">算法与数据结构(2)班<br>[化工317]</td>\n" +
                "          \n" +
                "          <td id=\"73\">&nbsp;</td>\n" +
                "          \n" +
                "          <td id=\"74\">&nbsp;</td>\n" +
                "          \n" +
                "        </tr>\n" +
                "        \n" +
                "        <tr id=\"tr8\"> \n" +
                "          <td align=\"center\">8</td>\n" +
                "          \n" +
                "          <td id=\"81\">&nbsp;</td>\n" +
                "          \n" +
                "          <td id=\"83\">&nbsp;</td>\n" +
                "          \n" +
                "          <td id=\"84\">&nbsp;</td>\n" +
                "          \n" +
                "          <td id=\"85\">&nbsp;</td>\n" +
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
                "          <td align=\"center\" rowspan=3 id=\"93\">面向对象程序设计实训(1)班<br>[单][轻工616]</td>\n" +
                "          \n" +
                "          <td id=\"94\">&nbsp;</td>\n" +
                "          \n" +
                "          <td align=\"center\" rowspan=3 id=\"95\">概率论与数理统计(10)班<br>[化工314]<br>(16-16周)</td>\n" +
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
                "        </tr>\n" +
                "        \n" +
                "        <tr id=\"tr11\"> \n" +
                "          <td align=\"center\">11</td>\n" +
                "          \n" +
                "          <td id=\"111\">&nbsp;</td>\n" +
                "          \n" +
                "          <td id=\"112\">&nbsp;</td>\n" +
                "          \n" +
                "          <td id=\"114\">&nbsp;</td>\n" +
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

    // 获取数据
    @Test
    public void Query() {
        //输出数据


        // 调用observe方法来获取ViewModel里的数据(请在UI线程里使用，在这里没有用，只是注释给你看)
        /*OneWeekCourseViewModel oneWeekCourseViewModel = new ViewModelProvider(this).get(OneWeekCourseViewModel.class);
        LiveData<List<OneWeekCourse>> oneWeekCourseLive = oneWeekCourseViewModel.getOneWeekCourseLive();
        oneWeekCourseLive.observe(requireActivity(), new Observer<List<OneWeekCourse>>() {
            @Override
            public void onChanged(List<OneWeekCourse> oneWeekCourses) {
                if(oneWeekCourses!=null){
                    LogUtils.getInstance().d("读取数据"+oneWeekCourses);
                }
            }
        });*/


        //非ViewModel的调用在这里↓
        LogUtils.getInstance().d("myCheckIn数据：");
        myCheckIn = myCheckInDao.getMyCheckIn();
        LogUtils.getInstance().d(myCheckIn.toString());

        LogUtils.getInstance().d("stuInfo数据：");
        stuInfo = stuInfoDao.getStuInfo();
        LogUtils.getInstance().d(stuInfo.toString());


    }

    @Test
    public void testDeleteOneWeekByWeek() {
        ArrayList<Integer> delList = new ArrayList<>();
        delList.add(14);
        delList.add(13);
        OneWeekCourseDao.deleteCourseByWeek(delList);

        List<OneWeekCourse> oneWeekCourse1 = OneWeekCourseDao.getOneWeekCourse();
        for (OneWeekCourse oneWeekCourse : oneWeekCourse1) {
            LogUtils.getInstance().d(oneWeekCourse.toString());

        }
    }

    public static String getStr() {
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
                "<BODY onbeforeprint=\"w.style.display='none';\"  onafterprint=\"w.style.display='';\">\n" +
                "\t \n" +
                "\n" +
                "  <table width=\"440\" border=\"0\" cellspacing=\"0\" id=\"w\" align=\"center\">\n" +
                "  <tr height=\"45\"><td align=\"center\"><input name=\"pweek\" type=\"button\" value=\"上一周 \" onClick=\"javascript:document.location='zkb_xs.asp?week1=10&kkxq=2019%E4%B8%8B';\" class=\"button\">   \t\t\t\t\t\t\n" +
                "&nbsp;&nbsp;&nbsp;&nbsp;<input name=\"allkb\" type=\"button\" value=\"完整课表\" onClick=\"javascript:document.location='kb_xs.asp';\" class=\"button\">&nbsp;&nbsp;&nbsp;&nbsp;   \n" +
                "\t<input name=\"nweek\" type=\"button\" value=\"下一周 \" onClick=\"javascript:document.location='zkb_xs.asp?week1=12&kkxq=2019%E4%B8%8B';\" class=\"button\">  \n" +
                "</td></tr>\n" +
                "<tr><td align=\"center\" class=\"td3\"><strong> 2019下学期第11周(2020-4-27-2020-5-3)，张三</strong> </td></tr>\n" +
                "</table>\t\n" +
                "\n" +
                "\n" +
                "<table  cellspacing=\"0\" cellpadding=\"0\" align=\"center\" border=\"0\" bordercolor=\"#111111\">\n" +
                "  <tr> \n" +
                "    <td valign=\"top\">\n" +
                "\n" +
                "\t<table class=\"table1\" width=\"440\" height=\"400\" cellspacing=\"0\" cellpadding=\"1\" align=\"center\" style=\"border-collapse: collapse\" border=\"1\" >\n" +
                "\t    <tr  height=\"30\"> \n" +
                "\t      <td align=\"center\" class=\"td1\">\n" +
                "\t        4月<br>\n" +
                "\t        节次 </td>\n" +
                "\t      <td align=\"center\" class=\"td1\">27<br />周一</td>\n" +
                "\t      <td align=\"center\" class=\"td11\">28<br />周二</td>\n" +
                "\t      <td align=\"center\" class=\"td1\">29<br />周三</td>\n" +
                "\t      <td align=\"center\" class=\"td1\">30<br />周四</td>\n" +
                "\t      <td align=\"center\" class=\"td1\">1<br />周五</td>\n" +
                "\t\t  \n" +
                "\t    </tr>\n" +
                "\n" +
                "\n" +
                "\u200B        \n" +
                "        <tr id=\"tr1\"> \n" +
                "          <td align=\"center\" class=\"td1\">1</td>\n" +
                "          \n" +
                "          <td align=\"center\" rowspan=4 id=\"11\"  class=\"td2\">高级数据库技术(1)班<br>[网络教学]</td>\n" +
                "          \n" +
                "          <td id=\"12\" class=\"td2\">&nbsp;</td>\n" +
                "          \n" +
                "          <td align=\"center\" rowspan=4 id=\"13\"  class=\"td2\">高级数据库技术(1)班<br>[网络教学]</td>\n" +
                "          \n" +
                "          <td id=\"14\" class=\"td2\">&nbsp;</td>\n" +
                "          \n" +
                "          <td id=\"15\" class=\"td2\">&nbsp;</td>\n" +
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
                "        </tr>\n" +
                "        \n" +
                "        <tr id=\"tr3\"> \n" +
                "          <td align=\"center\" class=\"td1\">3</td>\n" +
                "          \n" +
                "          <td align=\"center\" rowspan=2 id=\"32\"  class=\"td2\">高级数据库技术(1)班<br>[单][网络教学]</td>\n" +
                "          \n" +
                "          <td id=\"34\" class=\"td2\">&nbsp;</td>\n" +
                "          \n" +
                "          <td id=\"35\" class=\"td2\">&nbsp;</td>\n" +
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
                "        </tr>\n" +
                "        \n" +
                "        <tr id=\"tr5\"> \n" +
                "          <td align=\"center\" class=\"td1\">5</td>\n" +
                "          \n" +
                "          <td align=\"center\" rowspan=2 id=\"51\"  class=\"td2\">软件工程(1)班<br>[双][网络教学]</td>\n" +
                "          \n" +
                "          <td align=\"center\" rowspan=4 id=\"52\"  class=\"td2\">大数据应用开发实践(1)班<br>[网络教学]</td>\n" +
                "          \n" +
                "          <td id=\"53\" class=\"td2\">&nbsp;</td>\n" +
                "          \n" +
                "          <td align=\"center\" rowspan=4 id=\"54\"  class=\"td2\">大数据综合应用案例实训(1)班<br>[网络教学]</td>\n" +
                "          \n" +
                "          <td id=\"55\" class=\"td2\">&nbsp;</td>\n" +
                "          \n" +
                "        </tr>\n" +
                "        \n" +
                "        <tr id=\"tr6\"> \n" +
                "          <td align=\"center\" class=\"td1\">6</td>\n" +
                "          \n" +
                "          <td id=\"63\" class=\"td2\">&nbsp;</td>\n" +
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
                "          <td id=\"73\" class=\"td2\">&nbsp;</td>\n" +
                "          \n" +
                "          <td id=\"75\" class=\"td2\">&nbsp;</td>\n" +
                "          \n" +
                "        </tr>\n" +
                "        \n" +
                "        <tr id=\"tr8\"> \n" +
                "          <td align=\"center\" class=\"td1\">8</td>\n" +
                "          \n" +
                "          <td id=\"81\" class=\"td2\">&nbsp;</td>\n" +
                "          \n" +
                "          <td id=\"83\" class=\"td2\">&nbsp;</td>\n" +
                "          \n" +
                "          <td id=\"85\" class=\"td2\">&nbsp;</td>\n" +
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
                "          <td id=\"93\" class=\"td2\">&nbsp;</td>\n" +
                "          \n" +
                "          <td id=\"94\" class=\"td2\">&nbsp;</td>\n" +
                "          \n" +
                "          <td id=\"95\" class=\"td2\">&nbsp;</td>\n" +
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
                "          <td id=\"103\" class=\"td2\">&nbsp;</td>\n" +
                "          \n" +
                "          <td id=\"104\" class=\"td2\">&nbsp;</td>\n" +
                "          \n" +
                "          <td id=\"105\" class=\"td2\">&nbsp;</td>\n" +
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
                "        </tr>\n" +
                "        \n" +
                "      </table></td>\n" +
                "  </tr>\n" +
                "</table>\n" +
                "\t\n" +
                "</body>\n" +
                "</html>\n" +
                "\n";
    }
}
