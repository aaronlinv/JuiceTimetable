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
public class ParseCheckInTest {

    @Test
    public void getMySigned() {
        String question = ParseCheckIn.getMySigned(getMyCheckInStr()).toString();
        String answer = getMyCheckInAnswer();
        boolean isCheck = answer.equals(question);

        Assert.assertTrue(isCheck);

    }

    /**
     * 学生自身签到情况
     *
     * @return
     */
    private static String getMyCheckInStr() {
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

    /**
     * 自签answer
     *
     * @return
     */
    private static String getMyCheckInAnswer() {
        return "MyCheckIn{Cno=null, isCheckIn=false, checkTime='未签'}";
    }


}