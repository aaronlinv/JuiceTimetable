package com.juice.timetable.data.parse;

import org.junit.Assert;
import org.junit.Test;

/**
 * <pre>
 *     author : soreak
 *     e-mail : sorea1k@163.com
 *     time   : 2020/05/26
 *     desc   : nothing
 *     item   : juice
 *     version: 1.0
 * </pre>
 */
public class ParseAllWeekTest {

    /**
     * 计算机科学与技术
     */
    @Test
    public void parseAllCourse() {
        String question = ParseAllWeek.parseAllCourse(getAllCourseStr()).toString();

        String answer = getAllCourseAnswer();

        boolean isCheck = answer.equals(question);

        Assert.assertTrue(isCheck);
    }

    /**
     * 汉语言
     */
    @Test
    public void parseAllCourse_Chinese() {
        String question = ParseAllWeek.parseAllCourse(getChineseWholeTimetable()).toString();

        String answer = getChineseWholeTimetableAnswer();

        boolean isCheck = answer.equals(question);

        Assert.assertTrue(isCheck);

    }

    /**
     * 计算机科学与技术完整课表
     *
     * @return
     */
    private static String getAllCourseStr() {
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

    /**
     * 计算机科学与技术完整课表answer
     *
     * @return
     */
    private static String getAllCourseAnswer() {
        return "[Course{couID=1, onlyID=0, couName='高级数据库技术 (1)班', couRoom='网络教学', couTeacher='杨雄', couWeek=1, couStartNode=1, couEndNode=4, couWeekType=0, couStartWeek=1, couEndWeek=14, couColor=null}, " +
                "Course{couID=2, onlyID=1, couName='数据挖掘与分析 (1)班', couRoom='网络教学', couTeacher='杨雄', couWeek=3, couStartNode=1, couEndNode=4, couWeekType=0, couStartWeek=1, couEndWeek=8, couColor=null}, " +
                "Course{couID=10, onlyID=2, couName='数据挖掘应用实践 (1)班', couRoom='网络教学', couTeacher='杨雄', couWeek=3, couStartNode=1, couEndNode=4, couWeekType=0, couStartWeek=9, couEndWeek=15, couColor=null}, " +
                "Course{couID=10, onlyID=3, couName='数据挖掘应用实践 (1)班', couRoom='网络教学', couTeacher='杨雄', couWeek=3, couStartNode=1, couEndNode=4, couWeekType=0, couStartWeek=9, couEndWeek=15, couColor=null}, " +
                "Course{couID=6, onlyID=4, couName='云计算与数据中心 (1)班', couRoom='网络教学', couTeacher='林庆新', couWeek=5, couStartNode=1, couEndNode=4, couWeekType=0, couStartWeek=1, couEndWeek=4, couColor=null}, " +
                "Course{couID=0, onlyID=5, couName='形势与政策（六） (6)班', couRoom='网络教学', couTeacher='韩晞婷', couWeek=2, couStartNode=3, couEndNode=4, couWeekType=0, couStartWeek=11, couEndWeek=14, couColor=null}, " +
                "Course{couID=4, onlyID=6, couName='软件工程 (1)班', couRoom='网络教学', couTeacher='张栋', couWeek=1, couStartNode=5, couEndNode=6, couWeekType=0, couStartWeek=1, couEndWeek=13, couColor=null}, " +
                "Course{couID=3, onlyID=7, couName='大数据应用开发 (1)班', couRoom='网络教学', couTeacher='马云莺', couWeek=2, couStartNode=5, couEndNode=8, couWeekType=0, couStartWeek=1, couEndWeek=8, couColor=null}, " +
                "Course{couID=8, onlyID=8, couName='大数据应用开发实践 (1)班', couRoom='网络教学', couTeacher='马云莺', couWeek=2, couStartNode=5, couEndNode=8, couWeekType=0, couStartWeek=9, couEndWeek=16, couColor=null}, " +
                "Course{couID=8, onlyID=9, couName='大数据应用开发实践 (1)班', couRoom='网络教学', couTeacher='马云莺', couWeek=2, couStartNode=5, couEndNode=8, couWeekType=0, couStartWeek=9, couEndWeek=16, couColor=null}, " +
                "Course{couID=4, onlyID=10, couName='软件工程 (1)班', couRoom='网络教学', couTeacher='张栋', couWeek=3, couStartNode=5, couEndNode=8, couWeekType=0, couStartWeek=5, couEndWeek=8, couColor=null}, " +
                "Course{couID=4, onlyID=11, couName='软件工程 (1)班', couRoom='网络教学', couTeacher='张栋', couWeek=3, couStartNode=5, couEndNode=8, couWeekType=0, couStartWeek=10, couEndWeek=10, couColor=null}, " +
                "Course{couID=6, onlyID=12, couName='云计算与数据中心 (1)班', couRoom='网络教学', couTeacher='林庆新', couWeek=3, couStartNode=5, couEndNode=8, couWeekType=0, couStartWeek=1, couEndWeek=4, couColor=null}, " +
                "Course{couID=9, onlyID=13, couName='大数据综合应用案例实训 (1)班', couRoom='网络教学', couTeacher='马云莺', couWeek=4, couStartNode=5, couEndNode=8, couWeekType=0, couStartWeek=5, couEndWeek=11, couColor=null}, " +
                "Course{couID=9, onlyID=14, couName='大数据综合应用案例实训 (1)班', couRoom='网络教学', couTeacher='马云莺', couWeek=4, couStartNode=5, couEndNode=8, couWeekType=0, couStartWeek=5, couEndWeek=11, couColor=null}]";
    }

    /**
     * 汉语言完整课表
     *
     * @return
     */
    private static String getChineseWholeTimetable() {
        return "<META NAME=\"ROBOTS\" CONTENT=\"NOINDEX,NOFOLLOW\">\n" +
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
                "<div align=\"center\"><strong>福州大学至诚学院 2019下学期小易同学课程表</strong>(2020-5-10 22:28:36)</div>\n" +
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
                "          <td  >&nbsp;大学英语（四） (10)班</td>\n" +
                "          <td align=\"center\"><a href=\"../kkgl/kcjd/dgjdb.asp?lx=dg&kkxq=2019%E4%B8%8B&kcdm=06000108&kkhm=2019%E4%B8%8B06000108010\" target=\"_blank\">大纲</a>\n" +
                "\t\t  /<a href=\"../kkgl/kcjd/dgjdb.asp?lx=jdb&kkxq=2019%E4%B8%8B&kcdm=06000108&kkhm=2019%E4%B8%8B06000108010\" target=\"_blank\">进度表</a></td>\t\t\t\n" +
                "          <td align=\"center\">刘伟</td>\n" +
                "\t\t  <td align=\"center\">通识必修</td>\t\t  \n" +
                "\t\t  <td align=\"center\">正常考考试</td>\t\t\t\t\t  \n" +
                "\t\t  <td align=\"center\">10班</td>\t\t\t\t\t  \n" +
                "\t\t  <td align=\"center\">3</td>\n" +
                "          <td align=\"center\">48</td>\n" +
                "          <td align=\"center\">3</td>\n" +
                "          <td align=\"center\"></td>\n" +
                "          <td align=\"center\">01～17</td>\t\n" +
                "        </tr>\n" +
                "       \n" +
                "        <tr> \n" +
                "          <td  >&nbsp;毛泽东思想和中国特色社会主义理论体系概论 (19)班</td>\n" +
                "          <td align=\"center\"><a href=\"../kkgl/kcjd/dgjdb.asp?lx=dg&kkxq=2019%E4%B8%8B&kcdm=11000101&kkhm=2019%E4%B8%8B11000101019\" target=\"_blank\">大纲</a>\n" +
                "\t\t  /<a href=\"../kkgl/kcjd/dgjdb.asp?lx=jdb&kkxq=2019%E4%B8%8B&kcdm=11000101&kkhm=2019%E4%B8%8B11000101019\" target=\"_blank\">进度表</a></td>\t\t\t\n" +
                "          <td align=\"center\">叶钦</td>\n" +
                "\t\t  <td align=\"center\">通识必修</td>\t\t  \n" +
                "\t\t  <td align=\"center\">正常考考试</td>\t\t\t\t\t  \n" +
                "\t\t  <td align=\"center\">19班</td>\t\t\t\t\t  \n" +
                "\t\t  <td align=\"center\">4</td>\n" +
                "          <td align=\"center\">64</td>\n" +
                "          <td align=\"center\">4</td>\n" +
                "          <td align=\"center\"></td>\n" +
                "          <td align=\"center\">01～16</td>\t\n" +
                "        </tr>\n" +
                "       \n" +
                "        <tr> \n" +
                "          <td  >&nbsp;体育（四）</td>\n" +
                "          <td align=\"center\"><a href=\"../kkgl/kcjd/dgjdb.asp?lx=dg&kkxq=2019%E4%B8%8B&kcdm=06000125&kkhm=2019%E4%B8%8B06000125017\" target=\"_blank\">大纲</a>\n" +
                "\t\t  /<a href=\"../kkgl/kcjd/dgjdb.asp?lx=jdb&kkxq=2019%E4%B8%8B&kcdm=06000125&kkhm=2019%E4%B8%8B06000125017\" target=\"_blank\">进度表</a></td>\t\t\t\n" +
                "          <td align=\"center\">何语林</td>\n" +
                "\t\t  <td align=\"center\">通识必修</td>\t\t  \n" +
                "\t\t  <td align=\"center\">正常考考试</td>\t\t\t\t\t  \n" +
                "\t\t  <td align=\"center\">2018汉语言1</td>\t\t\t\t\t  \n" +
                "\t\t  <td align=\"center\">1</td>\n" +
                "          <td align=\"center\">36</td>\n" +
                "          <td align=\"center\"></td>\n" +
                "          <td align=\"center\"></td>\n" +
                "          <td align=\"center\">08～13</td>\t\n" +
                "        </tr>\n" +
                "       \n" +
                "        <tr> \n" +
                "          <td  >&nbsp;形势与政策（四） (2)班</td>\n" +
                "          <td align=\"center\"><a href=\"../kkgl/kcjd/dgjdb.asp?lx=dg&kkxq=2019%E4%B8%8B&kcdm=08000106&kkhm=2019%E4%B8%8B08000106002\" target=\"_blank\">大纲</a>\n" +
                "\t\t  /<a href=\"../kkgl/kcjd/dgjdb.asp?lx=jdb&kkxq=2019%E4%B8%8B&kcdm=08000106&kkhm=2019%E4%B8%8B08000106002\" target=\"_blank\">进度表</a></td>\t\t\t\n" +
                "          <td align=\"center\">郑声文</td>\n" +
                "\t\t  <td align=\"center\">通识必修</td>\t\t  \n" +
                "\t\t  <td align=\"center\">正常考考试</td>\t\t\t\t\t  \n" +
                "\t\t  <td align=\"center\">2班</td>\t\t\t\t\t  \n" +
                "\t\t  <td align=\"center\">.5</td>\n" +
                "          <td align=\"center\"></td>\n" +
                "          <td align=\"center\"></td>\n" +
                "          <td align=\"center\"></td>\n" +
                "          <td align=\"center\">01～01<br>03～03</td>\t\n" +
                "        </tr>\n" +
                "       \n" +
                "        <tr> \n" +
                "          <td  >&nbsp;古代汉语（下） (1)班</td>\n" +
                "          <td align=\"center\"><a href=\"../kkgl/kcjd/dgjdb.asp?lx=dg&kkxq=2019%E4%B8%8B&kcdm=06502204&kkhm=2019%E4%B8%8B06502204001\" target=\"_blank\">大纲</a>\n" +
                "\t\t  /<a href=\"../kkgl/kcjd/dgjdb.asp?lx=jdb&kkxq=2019%E4%B8%8B&kcdm=06502204&kkhm=2019%E4%B8%8B06502204001\" target=\"_blank\">进度表</a></td>\t\t\t\n" +
                "          <td align=\"center\">宋铁全</td>\n" +
                "\t\t  <td align=\"center\">专业基础</td>\t\t  \n" +
                "\t\t  <td align=\"center\">正常考考试</td>\t\t\t\t\t  \n" +
                "\t\t  <td align=\"center\">2018级汉语言1</td>\t\t\t\t\t  \n" +
                "\t\t  <td align=\"center\">3</td>\n" +
                "          <td align=\"center\">48</td>\n" +
                "          <td align=\"center\">3</td>\n" +
                "          <td align=\"center\"></td>\n" +
                "          <td align=\"center\">14～16</td>\t\n" +
                "        </tr>\n" +
                "       \n" +
                "        <tr> \n" +
                "          <td  >&nbsp;语言学概论 (1)班</td>\n" +
                "          <td align=\"center\"><a href=\"../kkgl/kcjd/dgjdb.asp?lx=dg&kkxq=2019%E4%B8%8B&kcdm=06502207&kkhm=2019%E4%B8%8B06502207001\" target=\"_blank\">大纲</a>\n" +
                "\t\t  /<a href=\"../kkgl/kcjd/dgjdb.asp?lx=jdb&kkxq=2019%E4%B8%8B&kcdm=06502207&kkhm=2019%E4%B8%8B06502207001\" target=\"_blank\">进度表</a></td>\t\t\t\n" +
                "          <td align=\"center\">卓星友,韩晞婷</td>\n" +
                "\t\t  <td align=\"center\">专业基础</td>\t\t  \n" +
                "\t\t  <td align=\"center\">正常考考试</td>\t\t\t\t\t  \n" +
                "\t\t  <td align=\"center\">2018级汉语言1</td>\t\t\t\t\t  \n" +
                "\t\t  <td align=\"center\">2</td>\n" +
                "          <td align=\"center\">32</td>\n" +
                "          <td align=\"center\">2</td>\n" +
                "          <td align=\"center\"></td>\n" +
                "          <td align=\"center\">00～00</td>\t\n" +
                "        </tr>\n" +
                "       \n" +
                "        <tr> \n" +
                "          <td  >&nbsp;中国古代文学（四） (1)班</td>\n" +
                "          <td align=\"center\"><a href=\"../kkgl/kcjd/dgjdb.asp?lx=dg&kkxq=2019%E4%B8%8B&kcdm=06502213&kkhm=2019%E4%B8%8B06502213001\" target=\"_blank\">大纲</a>\n" +
                "\t\t  /<a href=\"../kkgl/kcjd/dgjdb.asp?lx=jdb&kkxq=2019%E4%B8%8B&kcdm=06502213&kkhm=2019%E4%B8%8B06502213001\" target=\"_blank\">进度表</a></td>\t\t\t\n" +
                "          <td align=\"center\">苗健青</td>\n" +
                "\t\t  <td align=\"center\">专业基础</td>\t\t  \n" +
                "\t\t  <td align=\"center\">正常考考试</td>\t\t\t\t\t  \n" +
                "\t\t  <td align=\"center\">2018级汉语言1</td>\t\t\t\t\t  \n" +
                "\t\t  <td align=\"center\">3</td>\n" +
                "          <td align=\"center\">48</td>\n" +
                "          <td align=\"center\">3</td>\n" +
                "          <td align=\"center\"></td>\n" +
                "          <td align=\"center\">12～18</td>\t\n" +
                "        </tr>\n" +
                "       \n" +
                "        <tr> \n" +
                "          <td  >&nbsp;中国现当代文学（下） (1)班</td>\n" +
                "          <td align=\"center\"><a href=\"../kkgl/kcjd/dgjdb.asp?lx=dg&kkxq=2019%E4%B8%8B&kcdm=06502215&kkhm=2019%E4%B8%8B06502215001\" target=\"_blank\">大纲</a>\n" +
                "\t\t  /<a href=\"../kkgl/kcjd/dgjdb.asp?lx=jdb&kkxq=2019%E4%B8%8B&kcdm=06502215&kkhm=2019%E4%B8%8B06502215001\" target=\"_blank\">进度表</a></td>\t\t\t\n" +
                "          <td align=\"center\">牛康</td>\n" +
                "\t\t  <td align=\"center\">专业基础</td>\t\t  \n" +
                "\t\t  <td align=\"center\">正常考考试</td>\t\t\t\t\t  \n" +
                "\t\t  <td align=\"center\">2018级汉语言1</td>\t\t\t\t\t  \n" +
                "\t\t  <td align=\"center\">3</td>\n" +
                "          <td align=\"center\">48</td>\n" +
                "          <td align=\"center\">3</td>\n" +
                "          <td align=\"center\"></td>\n" +
                "          <td align=\"center\">14～16</td>\t\n" +
                "        </tr>\n" +
                "       \n" +
                "        <tr> \n" +
                "          <td rowspan=\"2\"  >&nbsp;生命安全与救援 (114)班</td>\n" +
                "          <td align=\"center\"><a href=\"../kkgl/kcjd/dgjdb.asp?lx=dg&kkxq=2019%E4%B8%8B&kcdm=80000112&kkhm=2019%E4%B8%8B80000112114\" target=\"_blank\">大纲</a>\n" +
                "\t\t  /<a href=\"../kkgl/kcjd/dgjdb.asp?lx=jdb&kkxq=2019%E4%B8%8B&kcdm=80000112&kkhm=2019%E4%B8%8B80000112114\" target=\"_blank\">进度表</a></td>\t\t\t\n" +
                "          <td align=\"center\"></td>\n" +
                "\t\t  <td align=\"center\">院选课</td>\t\t  \n" +
                "\t\t  <td align=\"center\">正常考考试</td>\t\t\t\t\t  \n" +
                "\t\t  <td align=\"center\">院选课超星尔雅</td>\t\t\t\t\t  \n" +
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
                "          <td  >&nbsp;思想政治理论课实践(二) (19)班</td>\n" +
                "          <td align=\"center\"><a href=\"../kkgl/kcjd/dgjdb.asp?lx=dg&kkxq=2019%E4%B8%8B&kcdm=07004608&kkhm=2019%E4%B8%8B07004608019\" target=\"_blank\">大纲</a>\n" +
                "\t\t  /<a href=\"../kkgl/kcjd/dgjdb.asp?lx=jdb&kkxq=2019%E4%B8%8B&kcdm=07004608&kkhm=2019%E4%B8%8B07004608019\" target=\"_blank\">进度表</a></td>\t\t\t\n" +
                "          <td align=\"center\">叶钦</td>\n" +
                "\t\t  <td align=\"center\">实践环节</td>\t\t  \n" +
                "\t\t  <td align=\"center\">正常考考试</td>\t\t\t\t\t  \n" +
                "\t\t  <td align=\"center\">19班</td>\t\t\t\t\t  \n" +
                "\t\t  <td align=\"center\">1</td>\n" +
                "          <td align=\"center\"></td>\n" +
                "          <td align=\"center\"></td>\n" +
                "          <td align=\"center\"></td>\n" +
                "          <td align=\"center\"></td>\t\n" +
                "        </tr>\n" +
                "       \n" +
                "        <tr> \n" +
                "          <td  >&nbsp;学科实践（四） (1)班</td>\n" +
                "          <td align=\"center\"><a href=\"../kkgl/kcjd/dgjdb.asp?lx=dg&kkxq=2019%E4%B8%8B&kcdm=06502604&kkhm=2019%E4%B8%8B06502604001\" target=\"_blank\">大纲</a>\n" +
                "\t\t  /<a href=\"../kkgl/kcjd/dgjdb.asp?lx=jdb&kkxq=2019%E4%B8%8B&kcdm=06502604&kkhm=2019%E4%B8%8B06502604001\" target=\"_blank\">进度表</a></td>\t\t\t\n" +
                "          <td align=\"center\">牛康</td>\n" +
                "\t\t  <td align=\"center\">实践环节</td>\t\t  \n" +
                "\t\t  <td align=\"center\">正常考考试</td>\t\t\t\t\t  \n" +
                "\t\t  <td align=\"center\">2018级汉语言1</td>\t\t\t\t\t  \n" +
                "\t\t  <td align=\"center\">2</td>\n" +
                "          <td align=\"center\"></td>\n" +
                "          <td align=\"center\">√</td>\n" +
                "          <td align=\"center\"></td>\n" +
                "          <td align=\"center\">00～00</td>\t\n" +
                "        </tr>\n" +
                "       \n" +
                "        <tr> \n" +
                "          <td  >&nbsp;学年论文（一） (1)班</td>\n" +
                "          <td align=\"center\"><a href=\"../kkgl/kcjd/dgjdb.asp?lx=dg&kkxq=2019%E4%B8%8B&kcdm=07502608&kkhm=2019%E4%B8%8B07502608001\" target=\"_blank\">大纲</a>\n" +
                "\t\t  /<a href=\"../kkgl/kcjd/dgjdb.asp?lx=jdb&kkxq=2019%E4%B8%8B&kcdm=07502608&kkhm=2019%E4%B8%8B07502608001\" target=\"_blank\">进度表</a></td>\t\t\t\n" +
                "          <td align=\"center\">牛康</td>\n" +
                "\t\t  <td align=\"center\">实践环节</td>\t\t  \n" +
                "\t\t  <td align=\"center\">正常考考试</td>\t\t\t\t\t  \n" +
                "\t\t  <td align=\"center\">2018级汉语言1</td>\t\t\t\t\t  \n" +
                "\t\t  <td align=\"center\">1.5</td>\n" +
                "          <td align=\"center\"></td>\n" +
                "          <td align=\"center\">√</td>\n" +
                "          <td align=\"center\"></td>\n" +
                "          <td align=\"center\">05～07</td>\t\n" +
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
                "          <td align=\"center\">六</td>\n" +
                "\t\t  \n" +
                "          <td align=\"center\">日</td>\n" +
                "\t\t  \n" +
                "        </tr>\n" +
                "        \n" +
                "        \n" +
                "        <tr id=\"tr1\"> \n" +
                "          <td align=\"center\">1</td>\n" +
                "          \n" +
                "          <td id=\"11\">&nbsp;</td>\n" +
                "          \n" +
                "          <td align=\"center\" rowspan=2 id=\"12\">毛泽东思想和中国特色社会主义理论体系概论(19)班<br>[网络教学]</td>\n" +
                "          \n" +
                "          <td id=\"13\">&nbsp;</td>\n" +
                "          \n" +
                "          <td id=\"14\">&nbsp;</td>\n" +
                "          \n" +
                "          <td id=\"15\">&nbsp;</td>\n" +
                "          \n" +
                "          <td id=\"16\">&nbsp;</td>\n" +
                "          \n" +
                "          <td align=\"center\" rowspan=2 id=\"17\">形势与政策（四）(2)班<br>[网络教学]</td>\n" +
                "          \n" +
                "        </tr>\n" +
                "        \n" +
                "        <tr id=\"tr2\"> \n" +
                "          <td align=\"center\">2</td>\n" +
                "          \n" +
                "          <td id=\"21\">&nbsp;</td>\n" +
                "          \n" +
                "          <td align=\"center\" rowspan=3 id=\"23\">古代汉语（下）(1)班<br>[机北406]<br>古代汉语（下）(1)班<br>[网络教学]<br>(01-16周)</td>\n" +
                "          \n" +
                "          <td id=\"24\">&nbsp;</td>\n" +
                "          \n" +
                "          <td align=\"center\" rowspan=3 id=\"25\">学年论文（一）(1)班<br>[网络教学]</td>\n" +
                "          \n" +
                "          <td id=\"26\">&nbsp;</td>\n" +
                "          \n" +
                "        </tr>\n" +
                "        \n" +
                "        <tr id=\"tr3\"> \n" +
                "          <td align=\"center\">3</td>\n" +
                "          \n" +
                "          <td align=\"center\" rowspan=2 id=\"31\">语言学概论(1)班<br>[网络教学]<br>(08-15周)</td>\n" +
                "          \n" +
                "          <td id=\"32\">&nbsp;</td>\n" +
                "          \n" +
                "          <td align=\"center\" rowspan=2 id=\"34\">大学英语（四）(10)班<br>[单][网络教学]</td>\n" +
                "          \n" +
                "          <td align=\"center\" rowspan=2 id=\"36\">形势与政策（四）(2)班<br>[网络教学]</td>\n" +
                "          \n" +
                "          <td id=\"37\">&nbsp;</td>\n" +
                "          \n" +
                "        </tr>\n" +
                "        \n" +
                "        <tr id=\"tr4\"> \n" +
                "          <td align=\"center\">4</td>\n" +
                "          \n" +
                "          <td id=\"42\">&nbsp;</td>\n" +
                "          \n" +
                "          <td id=\"47\">&nbsp;</td>\n" +
                "          \n" +
                "        </tr>\n" +
                "        \n" +
                "        <tr id=\"tr5\"> \n" +
                "          <td align=\"center\">5</td>\n" +
                "          \n" +
                "          <td align=\"center\" rowspan=2 id=\"51\">大学英语（四）(10)班<br>[网络教学]</td>\n" +
                "          \n" +
                "          <td align=\"center\" rowspan=2 id=\"52\">语言学概论(1)班<br>[网络教学]<br>(08-15周)</td>\n" +
                "          \n" +
                "          <td id=\"53\">&nbsp;</td>\n" +
                "          \n" +
                "          <td align=\"center\" rowspan=2 id=\"54\">毛泽东思想和中国特色社会主义理论体系概论(19)班<br>[网络教学]</td>\n" +
                "          \n" +
                "          <td id=\"55\">&nbsp;</td>\n" +
                "          \n" +
                "          <td id=\"56\">&nbsp;</td>\n" +
                "          \n" +
                "          <td id=\"57\">&nbsp;</td>\n" +
                "          \n" +
                "        </tr>\n" +
                "        \n" +
                "        <tr id=\"tr6\"> \n" +
                "          <td align=\"center\">6</td>\n" +
                "          \n" +
                "          <td id=\"63\">&nbsp;</td>\n" +
                "          \n" +
                "          <td id=\"65\">&nbsp;</td>\n" +
                "          \n" +
                "          <td id=\"66\">&nbsp;</td>\n" +
                "          \n" +
                "          <td id=\"67\">&nbsp;</td>\n" +
                "          \n" +
                "        </tr>\n" +
                "        \n" +
                "        <tr id=\"tr7\"> \n" +
                "          <td align=\"center\">7</td>\n" +
                "          \n" +
                "          <td id=\"71\">&nbsp;</td>\n" +
                "          \n" +
                "          <td id=\"72\">&nbsp;</td>\n" +
                "          \n" +
                "          <td id=\"73\">&nbsp;</td>\n" +
                "          \n" +
                "          <td id=\"74\">&nbsp;</td>\n" +
                "          \n" +
                "          <td id=\"75\">&nbsp;</td>\n" +
                "          \n" +
                "          <td id=\"76\">&nbsp;</td>\n" +
                "          \n" +
                "          <td id=\"77\">&nbsp;</td>\n" +
                "          \n" +
                "        </tr>\n" +
                "        \n" +
                "        <tr id=\"tr8\"> \n" +
                "          <td align=\"center\">8</td>\n" +
                "          \n" +
                "          <td id=\"81\">&nbsp;</td>\n" +
                "          \n" +
                "          <td id=\"82\">&nbsp;</td>\n" +
                "          \n" +
                "          <td id=\"83\">&nbsp;</td>\n" +
                "          \n" +
                "          <td id=\"84\">&nbsp;</td>\n" +
                "          \n" +
                "          <td id=\"85\">&nbsp;</td>\n" +
                "          \n" +
                "          <td id=\"86\">&nbsp;</td>\n" +
                "          \n" +
                "          <td id=\"87\">&nbsp;</td>\n" +
                "          \n" +
                "        </tr>\n" +
                "        \n" +
                "        <tr id=\"tr9\"> \n" +
                "          <td align=\"center\">9</td>\n" +
                "          \n" +
                "          <td align=\"center\" rowspan=3 id=\"91\">中国现当代文学（下）(1)班<br>[机北406]<br>中国现当代文学（下）(1)班<br>[网络教学]<br>(01-16周)</td>\n" +
                "          \n" +
                "          <td id=\"92\">&nbsp;</td>\n" +
                "          \n" +
                "          <td align=\"center\" rowspan=3 id=\"93\">中国古代文学（四）(1)班<br>[机北407]</td>\n" +
                "          \n" +
                "          <td id=\"94\">&nbsp;</td>\n" +
                "          \n" +
                "          <td id=\"95\">&nbsp;</td>\n" +
                "          \n" +
                "          <td id=\"96\">&nbsp;</td>\n" +
                "          \n" +
                "          <td id=\"97\">&nbsp;</td>\n" +
                "          \n" +
                "        </tr>\n" +
                "        \n" +
                "        <tr id=\"tr10\"> \n" +
                "          <td align=\"center\">10</td>\n" +
                "          \n" +
                "          <td id=\"102\">&nbsp;</td>\n" +
                "          \n" +
                "          <td id=\"104\">&nbsp;</td>\n" +
                "          \n" +
                "          <td id=\"105\">&nbsp;</td>\n" +
                "          \n" +
                "          <td id=\"106\">&nbsp;</td>\n" +
                "          \n" +
                "          <td id=\"107\">&nbsp;</td>\n" +
                "          \n" +
                "        </tr>\n" +
                "        \n" +
                "        <tr id=\"tr11\"> \n" +
                "          <td align=\"center\">11</td>\n" +
                "          \n" +
                "          <td id=\"112\">&nbsp;</td>\n" +
                "          \n" +
                "          <td id=\"114\">&nbsp;</td>\n" +
                "          \n" +
                "          <td id=\"115\">&nbsp;</td>\n" +
                "          \n" +
                "          <td id=\"116\">&nbsp;</td>\n" +
                "          \n" +
                "          <td id=\"117\">&nbsp;</td>\n" +
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

    /**
     * 汉语言完整课表answer
     *
     * @return
     */
    private static String getChineseWholeTimetableAnswer() {
        return "[Course{couID=1, onlyID=0, couName='毛泽东思想和中国特色社会主义理论体系概论 (19)班', couRoom='网络教学', couTeacher='叶钦', couWeek=2, couStartNode=1, couEndNode=2, couWeekType=0, couStartWeek=1, couEndWeek=16, couColor=null}, " +
                "Course{couID=3, onlyID=2, couName='形势与政策（四） (2)班', couRoom='网络教学', couTeacher='郑声文', couWeek=7, couStartNode=1, couEndNode=2, couWeekType=0, couStartWeek=3, couEndWeek=3, couColor=null}, " +
                "Course{couID=4, onlyID=3, couName='古代汉语（下） (1)班', couRoom='机北406', couTeacher='宋铁全', couWeek=3, couStartNode=2, couEndNode=4, couWeekType=0, couStartWeek=14, couEndWeek=16, couColor=null}, " +
                "Course{couID=4, onlyID=4, couName='古代汉语（下） (1)班', couRoom='网络教学', couTeacher='宋铁全', couWeek=3, couStartNode=2, couEndNode=4, couWeekType=0, couStartWeek=1, couEndWeek=16, couColor=null}, " +
                "Course{couID=11, onlyID=5, couName='学年论文（一） (1)班', couRoom='网络教学', couTeacher='牛康', couWeek=5, couStartNode=2, couEndNode=4, couWeekType=0, couStartWeek=5, couEndWeek=7, couColor=null}, " +
                "Course{couID=5, onlyID=6, couName='语言学概论 (1)班', couRoom='网络教学', couTeacher='卓星友,韩晞婷', couWeek=1, couStartNode=3, couEndNode=4, couWeekType=0, couStartWeek=8, couEndWeek=15, couColor=null}, " +
                "Course{couID=0, onlyID=7, couName='大学英语（四） (10)班', couRoom='网络教学', couTeacher='刘伟', couWeek=4, couStartNode=3, couEndNode=4, couWeekType=1, couStartWeek=1, couEndWeek=17, couColor=null}, " +
                "Course{couID=3, onlyID=9, couName='形势与政策（四） (2)班', couRoom='网络教学', couTeacher='郑声文', couWeek=6, couStartNode=3, couEndNode=4, couWeekType=0, couStartWeek=3, couEndWeek=3, couColor=null}, " +
                "Course{couID=0, onlyID=10, couName='大学英语（四） (10)班', couRoom='网络教学', couTeacher='刘伟', couWeek=1, couStartNode=5, couEndNode=6, couWeekType=0, couStartWeek=1, couEndWeek=17, couColor=null}, " +
                "Course{couID=5, onlyID=11, couName='语言学概论 (1)班', couRoom='网络教学', couTeacher='卓星友,韩晞婷', couWeek=2, couStartNode=5, couEndNode=6, couWeekType=0, couStartWeek=8, couEndWeek=15, couColor=null}, " +
                "Course{couID=1, onlyID=12, couName='毛泽东思想和中国特色社会主义理论体系概论 (19)班', couRoom='网络教学', couTeacher='叶钦', couWeek=4, couStartNode=5, couEndNode=6, couWeekType=0, couStartWeek=1, couEndWeek=16, couColor=null}, " +
                "Course{couID=7, onlyID=13, couName='中国现当代文学（下） (1)班', couRoom='机北406', couTeacher='牛康', couWeek=1, couStartNode=9, couEndNode=11, couWeekType=0, couStartWeek=14, couEndWeek=16, couColor=null}, " +
                "Course{couID=7, onlyID=14, couName='中国现当代文学（下） (1)班', couRoom='网络教学', couTeacher='牛康', couWeek=1, couStartNode=9, couEndNode=11, couWeekType=0, couStartWeek=1, couEndWeek=16, couColor=null}, " +
                "Course{couID=6, onlyID=15, couName='中国古代文学（四） (1)班', couRoom='机北407', couTeacher='苗健青', couWeek=3, couStartNode=9, couEndNode=11, couWeekType=0, couStartWeek=12, couEndWeek=18, couColor=null}]";
    }
}