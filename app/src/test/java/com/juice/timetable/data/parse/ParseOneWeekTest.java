package com.juice.timetable.data.parse;

import org.junit.Test;

/**
 * <pre>
 *     author : soreak
 *     e-mail : sorea1k@163.com
 *     time   : 2020/05/25
 *     desc   : nothing
 *     item   : juice
 *     version: 1.0
 * </pre>
 */
public class ParseOneWeekTest {
    @Test
    public void parseCourse() {
        String question = ParseOneWeek.parseCourse(getStr()).toString();
        String answer = "[OneWeekCourse{couID=null, onlyID=null, couName='高级数据库技术(1)班', couRoom='网络教学', dayOfWeek=1, typeOfWeek=0, startNode=1, endNode=4, InWeek=11, Color=null}, " +
                "OneWeekCourse{couID=null, onlyID=null, couName='高级数据库技术(1)班', couRoom='网络教学', dayOfWeek=3, typeOfWeek=0, startNode=1, endNode=4, InWeek=11, Color=null}, " +
                "OneWeekCourse{couID=null, onlyID=null, couName='高级数据库技术(1)班', couRoom='网络教学', dayOfWeek=2, typeOfWeek=1, startNode=3, endNode=4, InWeek=11, Color=null}, " +
                "OneWeekCourse{couID=null, onlyID=null, couName='软件工程(1)班', couRoom='网络教学', dayOfWeek=1, typeOfWeek=2, startNode=5, endNode=6, InWeek=11, Color=null}, " +
                "OneWeekCourse{couID=null, onlyID=null, couName='大数据应用开发实践(1)班', couRoom='网络教学', dayOfWeek=2, typeOfWeek=0, startNode=5, endNode=8, InWeek=11, Color=null}, " +
                "OneWeekCourse{couID=null, onlyID=null, couName='大数据综合应用案例实训(1)班', couRoom='网络教学', dayOfWeek=4, typeOfWeek=0, startNode=5, endNode=8, InWeek=11, Color=null}]";
        if (answer.equals(question)) {
            System.out.println("周课表解析结果正确");
        } else {
            System.out.println("周课表解析结果错误");
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