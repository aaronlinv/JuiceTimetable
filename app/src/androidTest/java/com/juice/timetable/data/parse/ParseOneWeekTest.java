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
public class ParseOneWeekTest {

    /**
     * 17计科
     */
    @Test
    public void parseCourse() {
        String question = ParseOneWeek.parseCourse(getOneWeekStr()).toString();
        String answer = getOneWeekAnswer();
        boolean isCheck = answer.equals(question);

        Assert.assertTrue(isCheck);
    }

    /**
     * 汉语言
     */
    @Test
    public void parseChineseCourse() {
        String question = ParseOneWeek.parseCourse(getChineseTimetable()).toString();

        String answer = getChineseTimetableAnswer();
        boolean isCheck = answer.equals(question);

        Assert.assertTrue(isCheck);
    }

    /**
     * 17计科周课表
     *
     * @return
     */
    private static String getOneWeekStr() {
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
                "          <td align=\"center\" rowspan=2 id=\"32\"  class=\"td2\">高级数据库技术(1)班<br>[网络教学]</td>\n" +
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
                "          <td align=\"center\" rowspan=2 id=\"51\"  class=\"td2\">软件工程(1)班<br>[网络教学]</td>\n" +
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

    /**
     * 17计科周课表answer
     *
     * @return
     */
    private static String getOneWeekAnswer() {
        return "[OneWeekCourse{onlyID=null, couID=null, couName='高级数据库技术(1)班', couRoom='网络教学', dayOfWeek=1, startNode=1, endNode=4, InWeek=11, Color=null}, " +
                "OneWeekCourse{onlyID=null, couID=null, couName='高级数据库技术(1)班', couRoom='网络教学', dayOfWeek=3, startNode=1, endNode=4, InWeek=11, Color=null}, " +
                "OneWeekCourse{onlyID=null, couID=null, couName='高级数据库技术(1)班', couRoom='网络教学', dayOfWeek=2, startNode=3, endNode=4, InWeek=11, Color=null}, " +
                "OneWeekCourse{onlyID=null, couID=null, couName='软件工程(1)班', couRoom='网络教学', dayOfWeek=1, startNode=5, endNode=6, InWeek=11, Color=null}, " +
                "OneWeekCourse{onlyID=null, couID=null, couName='大数据应用开发实践(1)班', couRoom='网络教学', dayOfWeek=2, startNode=5, endNode=8, InWeek=11, Color=null}, " +
                "OneWeekCourse{onlyID=null, couID=null, couName='大数据综合应用案例实训(1)班', couRoom='网络教学', dayOfWeek=4, startNode=5, endNode=8, InWeek=11, Color=null}]";
    }

    /**
     * 汉语言周课表
     *
     * @return
     */
    private static String getChineseTimetable() {
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

    /**
     * 汉语言周课表answer
     *
     * @return
     */
    private static String getChineseTimetableAnswer() {
        return "[OneWeekCourse{onlyID=null, couID=null, couName='毛泽东思想和中国特色社会主义理论体系概论(19)班', couRoom='网络教学', dayOfWeek=2, startNode=1, endNode=2, InWeek=12, Color=null}, " +
                "OneWeekCourse{onlyID=null, couID=null, couName='古代汉语（下）(1)班', couRoom='网络教学', dayOfWeek=3, startNode=2, endNode=4, InWeek=12, Color=null}, " +
                "OneWeekCourse{onlyID=null, couID=null, couName='语言学概论(1)班', couRoom='网络教学', dayOfWeek=1, startNode=3, endNode=4, InWeek=12, Color=null}, " +
                "OneWeekCourse{onlyID=null, couID=null, couName='大学英语（四）(10)班', couRoom='网络教学', dayOfWeek=1, startNode=5, endNode=6, InWeek=12, Color=null}, " +
                "OneWeekCourse{onlyID=null, couID=null, couName='语言学概论(1)班', couRoom='网络教学', dayOfWeek=2, startNode=5, endNode=6, InWeek=12, Color=null}, " +
                "OneWeekCourse{onlyID=null, couID=null, couName='毛泽东思想和中国特色社会主义理论体系概论(19)班', couRoom='网络教学', dayOfWeek=4, startNode=5, endNode=6, InWeek=12, Color=null}, " +
                "OneWeekCourse{onlyID=null, couID=null, couName='中国现当代文学（下）(1)班', couRoom='网络教学', dayOfWeek=1, startNode=9, endNode=11, InWeek=12, Color=null}, " +
                "OneWeekCourse{onlyID=null, couID=null, couName='中国古代文学（四）(1)班', couRoom='机北407', dayOfWeek=3, startNode=9, endNode=11, InWeek=12, Color=null}]";
    }
}