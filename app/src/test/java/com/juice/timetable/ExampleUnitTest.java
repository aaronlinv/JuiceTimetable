package com.juice.timetable;

import com.juice.timetable.data.parse.ParseAllWeek;
import com.juice.timetable.data.parse.ParseOneWeek;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
public class ExampleUnitTest {
    @Test
    public void addition_isCorrect() {
        assertEquals(4, 2 + 2);
    }

    @Test
    public void AllCourseTest() {

        ParseAllWeek.parseAllCourse(getChineseWholeTimetable());
    }

    @Test
    public void OneCourseTest() {
        ParseOneWeek.parseCourse(getChineseTimetable());

    }

    @Test
    public void leaveTest() {
//        ParseCheckIn.getMySigned();
    }

    /**
     * 汉语言完整课表
     *
     * @return
     */
    public String getChineseWholeTimetable() {
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
     * 汉语言周课表
     *
     * @return
     */
    public String getChineseTimetable() {
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
                "<link rel=\"stylesheet\" href=\"../inc/kbstyle.css\">\n" +
                "</head>\n" +
                "\n" +
                "<BODY onbeforeprint=\"w.style.display='none';\"  onafterprint=\"w.style.display='';\">\n" +
                "\t \n" +
                "  <table width=\"440\" border=\"0\" cellspacing=\"0\" id=\"w\" align=\"center\">\n" +
                "  <tr height=\"45\"><td align=\"center\"><input name=\"pweek\" type=\"button\" value=\"上一周 \" onClick=\"javascript:document.location='zkb_xs.asp?week1=11&kkxq=2019%E4%B8%8B';\" class=\"button\">   \t\t\t\t\t\t\n" +
                "&nbsp;&nbsp;&nbsp;&nbsp;<input name=\"allkb\" type=\"button\" value=\"完整课表\" onClick=\"javascript:document.location='kb_xs.asp';\" class=\"button\">&nbsp;&nbsp;&nbsp;&nbsp;   \n" +
                "\t<input name=\"nweek\" type=\"button\" value=\"下一周 \" onClick=\"javascript:document.location='zkb_xs.asp?week1=13&kkxq=2019%E4%B8%8B';\" class=\"button\">  \n" +
                "  \n" +
                "</td></tr>\n" +
                "<tr><td align=\"center\" class=\"td3\"><strong> 2019下学期第12周(2020-5-4-2020-5-10)，小易</strong> </td></tr>\n" +
                "</table>\t\n" +
                "\n" +
                "\n" +
                "<table  cellspacing=\"0\" cellpadding=\"0\" align=\"center\" border=\"0\" bordercolor=\"#111111\">\n" +
                "  <tr> \n" +
                "    <td valign=\"top\">\n" +
                "\t\n" +
                "\t<table class=\"table1\" width=\"440\" height=\"400\" cellspacing=\"0\" cellpadding=\"1\" align=\"center\" style=\"border-collapse: collapse\" border=\"1\" >\n" +
                "        <tr  height=\"30\"> \n" +
                "          <td align=\"center\" class=\"td1\">\n" +
                "            5月<br>\n" +
                "            节次 </td>\n" +
                "          <td align=\"center\" class=\"td1\">4<br />周一</td>\n" +
                "          <td align=\"center\" class=\"td1\">5<br />周二</td>\n" +
                "          <td align=\"center\" class=\"td1\">6<br />周三</td>\n" +
                "          <td align=\"center\" class=\"td1\">7<br />周四</td>\n" +
                "          <td align=\"center\" class=\"td1\">8<br />周五</td>\n" +
                "\t\t  \n" +
                "        </tr>\n" +
                "        \n" +
                "        \n" +
                "        <tr id=\"tr1\"> \n" +
                "          <td align=\"center\" class=\"td1\">1</td>\n" +
                "          \n" +
                "          <td id=\"11\" class=\"td2\">&nbsp;</td>\n" +
                "          \n" +
                "          <td align=\"center\" rowspan=2 id=\"12\"  class=\"td2\">毛泽东思想和中国特色社会主义理论体系概论(19)班<br>[网络教学]</td>\n" +
                "          \n" +
                "          <td id=\"13\" class=\"td2\">&nbsp;</td>\n" +
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
                "          <td id=\"21\" class=\"td2\">&nbsp;</td>\n" +
                "          \n" +
                "          <td align=\"center\" rowspan=3 id=\"23\"  class=\"td2\">古代汉语（下）(1)班<br>[网络教学]</td>\n" +
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
                "          <td align=\"center\" rowspan=2 id=\"31\"  class=\"td2\">语言学概论(1)班<br>[网络教学]</td>\n" +
                "          \n" +
                "          <td id=\"32\" class=\"td2\">&nbsp;</td>\n" +
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
                "          <td id=\"42\" class=\"td2\">&nbsp;</td>\n" +
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
                "          <td align=\"center\" rowspan=2 id=\"51\"  class=\"td2\">大学英语（四）(10)班<br>[网络教学]</td>\n" +
                "          \n" +
                "          <td align=\"center\" rowspan=2 id=\"52\"  class=\"td2\">语言学概论(1)班<br>[网络教学]</td>\n" +
                "          \n" +
                "          <td id=\"53\" class=\"td2\">&nbsp;</td>\n" +
                "          \n" +
                "          <td align=\"center\" rowspan=2 id=\"54\"  class=\"td2\">毛泽东思想和中国特色社会主义理论体系概论(19)班<br>[网络教学]</td>\n" +
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
                "          <td id=\"72\" class=\"td2\">&nbsp;</td>\n" +
                "          \n" +
                "          <td id=\"73\" class=\"td2\">&nbsp;</td>\n" +
                "          \n" +
                "          <td id=\"74\" class=\"td2\">&nbsp;</td>\n" +
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
                "          <td id=\"82\" class=\"td2\">&nbsp;</td>\n" +
                "          \n" +
                "          <td id=\"83\" class=\"td2\">&nbsp;</td>\n" +
                "          \n" +
                "          <td id=\"84\" class=\"td2\">&nbsp;</td>\n" +
                "          \n" +
                "          <td id=\"85\" class=\"td2\">&nbsp;</td>\n" +
                "          \n" +
                "        </tr>\n" +
                "        \n" +
                "        <tr id=\"tr9\"> \n" +
                "          <td align=\"center\" class=\"td1\">9</td>\n" +
                "          \n" +
                "          <td align=\"center\" rowspan=3 id=\"91\"  class=\"td2\">中国现当代文学（下）(1)班<br>[网络教学]</td>\n" +
                "          \n" +
                "          <td id=\"92\" class=\"td2\">&nbsp;</td>\n" +
                "          \n" +
                "          <td align=\"center\" rowspan=3 id=\"93\"  class=\"td2\">中国古代文学（四）(1)班<br>[机北407]</td>\n" +
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
                "          <td id=\"102\" class=\"td2\">&nbsp;</td>\n" +
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
                "          <td id=\"112\" class=\"td2\">&nbsp;</td>\n" +
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
                "\t\t<hr style=\" height:2px;border:none;border-top:2px dotted #185598;\" />\n" +
                "\t\t<font size='+1' color=\"#0000CC\">&nbsp;&nbsp;注意：2020-5-9放假调课补2020-5-5,第12周周2的课</font>\n" +
                "\t\t<hr style=\" height:2px;border:none;border-top:2px dotted #185598;\" />\n" +
                "\n" +
                "</body>\n" +
                "</html>\n";
    }
}