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
public class ParseClassNoSignedItemTest {

    @Test
    public void getClassUnSigned() {
        String question = ParseClassNoSignedItem.getClassUnSigned(getNoSignedItemStr()).toString();
        String answer = "[ClassNoSignrdItem{, sno='211606173', sname='王震豪'}, ClassNoSignrdItem{, sno='211706219', sname='郑达'}, ClassNoSignrdItem{, sno='211706218', sname='赵帅'}, " +
                "ClassNoSignrdItem{, sno='211706217', sname='张宇'}, ClassNoSignrdItem{, sno='211706216', sname='张体杰'}, ClassNoSignrdItem{, sno='211706215', sname='曾伟奇'}, " +
                "ClassNoSignrdItem{, sno='211706214', sname='俞伟建'}, ClassNoSignrdItem{, sno='211706213', sname='于佳宁'}, ClassNoSignrdItem{, sno='211706212', sname='杨银泉'}, " +
                "ClassNoSignrdItem{, sno='211706211', sname='谢文基'}, ClassNoSignrdItem{, sno='211706210', sname='肖俊贤'}, ClassNoSignrdItem{, sno='211706208', sname='吴伟凯'}, " +
                "ClassNoSignrdItem{, sno='211706207', sname='吴婷婷'}, ClassNoSignrdItem{, sno='211706206', sname='吴秋悦'}, ClassNoSignrdItem{, sno='211706205', sname='吴娉婷'}, " +
                "ClassNoSignrdItem{, sno='211706204', sname='吴佳卉'}, ClassNoSignrdItem{, sno='211706203', sname='魏忠杰'}, ClassNoSignrdItem{, sno='211706202', sname='魏鹏辉'}, " +
                "ClassNoSignrdItem{, sno='211706201', sname='王永乐'}, ClassNoSignrdItem{, sno='211706200', sname='王彤烨'}, ClassNoSignrdItem{, sno='211706199', sname='王力杰'}, " +
                "ClassNoSignrdItem{, sno='211706197', sname='汪钰莹'}, ClassNoSignrdItem{, sno='211706196', sname='陶佳杰'}, ClassNoSignrdItem{, sno='211706194', sname='苏滨涛'}, " +
                "ClassNoSignrdItem{, sno='211706192', sname='卢健霖'}, ClassNoSignrdItem{, sno='211706191', sname='刘雨昂'}, ClassNoSignrdItem{, sno='211706190', sname='林涛'}, " +
                "ClassNoSignrdItem{, sno='211706188', sname='林铭智'}, ClassNoSignrdItem{, sno='211706187', sname='林连坤'}, ClassNoSignrdItem{, sno='211706185', sname='梁梓银'}, " +
                "ClassNoSignrdItem{, sno='211706184', sname='连辛集'}, ClassNoSignrdItem{, sno='211706183', sname='连捷'}, ClassNoSignrdItem{, sno='211706181', sname='李英梦'}, " +
                "ClassNoSignrdItem{, sno='211706180', sname='李晓宇'}, ClassNoSignrdItem{, sno='211706179', sname='李思锦'}, ClassNoSignrdItem{, sno='211706178', sname='江李悦'}, " +
                "ClassNoSignrdItem{, sno='211706177', sname='黄希敏'}, ClassNoSignrdItem{, sno='211706176', sname='黄婷婷'}, ClassNoSignrdItem{, sno='211706174', sname='洪成龙'}, " +
                "ClassNoSignrdItem{, sno='211706173', sname='何子聪'}, ClassNoSignrdItem{, sno='211706172', sname='何鑫'}, ClassNoSignrdItem{, sno='211706171', sname='郭文昱'}, " +
                "ClassNoSignrdItem{, sno='211706170', sname='冯志成'}, ClassNoSignrdItem{, sno='211706169', sname='方介斌'}, ClassNoSignrdItem{, sno='211706168', sname='邓慧'}, " +
                "ClassNoSignrdItem{, sno='211706167', sname='程昱'}, ClassNoSignrdItem{, sno='211706166', sname='程顺明'}, ClassNoSignrdItem{, sno='211706165', sname='陈仙杰'}, " +
                "ClassNoSignrdItem{, sno='211706164', sname='陈德渠'}, ClassNoSignrdItem{, sno='211706163', sname='曹淦淇'}, ClassNoSignrdItem{, sno='211706162', sname='蔡泽华'}, " +
                "ClassNoSignrdItem{, sno='211706161', sname='蔡雨婷'}]";

        if (answer.equals(question)) {
            System.out.println("班级未签解析结果正确");
        } else {
            System.out.println("班级未签解析结果错误");
        }
    }

    private static String getNoSignedItemStr() {
        return "<!doctype html public '-//w3c//dtd html 4.01 transitional//en' 'http://www.w3.org/tr/html4/loose.dtd' >\n" +
                "<html >\n" +
                "\t<head >\n" +
                "\t\t<meta content=\"text/html; charset=UTF-8\" http-equiv=\"Content-Type\" >\n" +
                "\t\t<title >班级未签 - 至诚信息管理系统</title>\n" +
                "\t\t<link type=\"text/css\" rel=\"stylesheet\" href=\"Public/css/common/common.css?ver=2.11\" >\n" +
                "\t\t<link type=\"text/css\" rel=\"stylesheet\" href=\"Public/css/stuwork/dormcheck/unsignin/main.css?ver=2.11\" >\n" +
                "\t\t<link type=\"text/css\" rel=\"stylesheet\" href=\"Public/css/common/jquery-ui.min-1.11.4.css?ver=2.11\" >\n" +
                "\t\t<link type=\"text/css\" rel=\"stylesheet\" href=\"Public/css/common/jquery-ui.structure.min.css?ver=2.11\" >\n" +
                "\t\t<link type=\"text/css\" rel=\"stylesheet\" href=\"Public/css/common/jquery-ui.theme.min.css?ver=2.11\" >\n" +
                "\t\t<script type=\"text/javascript\" src=\"Public/js/common/jquery-1.11.1.min.js?ver=2.11\" ></script>\n" +
                "\t\t<script type=\"text/javascript\" src=\"Public/js/common/common.js?ver=2.11\" ></script>\n" +
                "\t\t<script type=\"text/javascript\" src=\"Public/js/stuwork/dormcheck/unsignin/cls.js?ver=2.11\" ></script>\n" +
                "\t\t<script type=\"text/javascript\" src=\"Public/js/common/jquery-ui.min-1.11.4.js?ver=2.11\" ></script>\n" +
                "\t\t<script type=\"text/javascript\" src=\"Public/js/common/jquery.ui.datepicker-zh-cn.js?ver=2.11\" ></script>\n" +
                "\t\t<script type=\"text/javascript\" src=\"Public/js/common/jquery.tablesorter.js?ver=2.11\" ></script>\n" +
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
                "\t\t\t\t\t<a href=\"index.php?n=stuwork-dormcheck-unsignin-cls&c=dormcheckclassunsignin\" >班级未签</a>\n" +
                "\t\t\t\t</li>\n" +
                "\t\t\t</ul>\n" +
                "\t\t</div>\n" +
                "\t\t<div class=\"wrapper\" >\n" +
                "\t\t\t<div class=\"formtitle\" >\n" +
                "\t\t\t\t<span >班级未签</span>\n" +
                "\t\t\t</div>\n" +
                "\t\t\t<div class=\"condition\" >\n" +
                "\t\t\t\t<span >日期</span>\n" +
                "\t\t\t\t<input type=\"text\" id=\"datepicker\" class=\"dfinput\" value=\"2020-04-27\" readonly=\"readonly\" >\n" +
                "\t\t\t\t<a class=\"enter\" id=\"btSearch\" href=\"#\" >查找</a>\n" +
                "\t\t\t</div>\n" +
                "\t\t\t<table class=\"tablelist\" >\n" +
                "\t\t\t\t<thead >\n" +
                "\t\t\t\t\t<tr >\n" +
                "\t\t\t\t\t\t<th class=\"thSN\" >序号</th>\n" +
                "\t\t\t\t\t\t<th class=\"thClassInfo\" >专业</th>\n" +
                "\t\t\t\t\t\t<th class=\"thNo\" >学号</th>\n" +
                "\t\t\t\t\t\t<th class=\"thName\" >姓名</th>\n" +
                "\t\t\t\t\t\t<th class=\"thSex\" >性别</th>\n" +
                "\t\t\t\t\t\t<th class=\"thDormInfo\" >宿舍</th>\n" +
                "\t\t\t\t\t</tr>\n" +
                "\t\t\t\t</thead>\n" +
                "\t\t\t\t<tbody >\n" +
                "\t\t\t\t\t<tr class=\"odd\" >\n" +
                "\t\t\t\t\t\t<td class=\"tdSN\" ></td>\n" +
                "\t\t\t\t\t\t<td class=\"tdClassInfo\" >2017计算机科学与技术2班</td>\n" +
                "\t\t\t\t\t\t<td class=\"tdNo\" >211606173</td>\n" +
                "\t\t\t\t\t\t<td class=\"tdName\" >王震豪</td>\n" +
                "\t\t\t\t\t\t<td class=\"tdSex\" >男</td>\n" +
                "\t\t\t\t\t\t<td class=\"tdDormInfo\" >中区2#楼-201-A</td>\n" +
                "\t\t\t\t\t</tr>\n" +
                "\t\t\t\t\t<tr class=\"even\" >\n" +
                "\t\t\t\t\t\t<td class=\"tdSN\" ></td>\n" +
                "\t\t\t\t\t\t<td class=\"tdClassInfo\" >2017计算机科学与技术2班</td>\n" +
                "\t\t\t\t\t\t<td class=\"tdNo\" >211706219</td>\n" +
                "\t\t\t\t\t\t<td class=\"tdName\" >郑达</td>\n" +
                "\t\t\t\t\t\t<td class=\"tdSex\" >男</td>\n" +
                "\t\t\t\t\t\t<td class=\"tdDormInfo\" >中区2#楼-108-C</td>\n" +
                "\t\t\t\t\t</tr>\n" +
                "\t\t\t\t\t<tr class=\"odd\" >\n" +
                "\t\t\t\t\t\t<td class=\"tdSN\" ></td>\n" +
                "\t\t\t\t\t\t<td class=\"tdClassInfo\" >2017计算机科学与技术2班</td>\n" +
                "\t\t\t\t\t\t<td class=\"tdNo\" >211706218</td>\n" +
                "\t\t\t\t\t\t<td class=\"tdName\" >赵帅</td>\n" +
                "\t\t\t\t\t\t<td class=\"tdSex\" >男</td>\n" +
                "\t\t\t\t\t\t<td class=\"tdDormInfo\" >中区2#楼-114-D</td>\n" +
                "\t\t\t\t\t</tr>\n" +
                "\t\t\t\t\t<tr class=\"even\" >\n" +
                "\t\t\t\t\t\t<td class=\"tdSN\" ></td>\n" +
                "\t\t\t\t\t\t<td class=\"tdClassInfo\" >2017计算机科学与技术2班</td>\n" +
                "\t\t\t\t\t\t<td class=\"tdNo\" >211706217</td>\n" +
                "\t\t\t\t\t\t<td class=\"tdName\" >张宇</td>\n" +
                "\t\t\t\t\t\t<td class=\"tdSex\" >男</td>\n" +
                "\t\t\t\t\t\t<td class=\"tdDormInfo\" >中区2#楼-117-C</td>\n" +
                "\t\t\t\t\t</tr>\n" +
                "\t\t\t\t\t<tr class=\"odd\" >\n" +
                "\t\t\t\t\t\t<td class=\"tdSN\" ></td>\n" +
                "\t\t\t\t\t\t<td class=\"tdClassInfo\" >2017计算机科学与技术2班</td>\n" +
                "\t\t\t\t\t\t<td class=\"tdNo\" >211706216</td>\n" +
                "\t\t\t\t\t\t<td class=\"tdName\" >张体杰</td>\n" +
                "\t\t\t\t\t\t<td class=\"tdSex\" >男</td>\n" +
                "\t\t\t\t\t\t<td class=\"tdDormInfo\" >中区2#楼-105-A</td>\n" +
                "\t\t\t\t\t</tr>\n" +
                "\t\t\t\t\t<tr class=\"even\" >\n" +
                "\t\t\t\t\t\t<td class=\"tdSN\" ></td>\n" +
                "\t\t\t\t\t\t<td class=\"tdClassInfo\" >2017计算机科学与技术2班</td>\n" +
                "\t\t\t\t\t\t<td class=\"tdNo\" >211706215</td>\n" +
                "\t\t\t\t\t\t<td class=\"tdName\" >曾伟奇</td>\n" +
                "\t\t\t\t\t\t<td class=\"tdSex\" >男</td>\n" +
                "\t\t\t\t\t\t<td class=\"tdDormInfo\" >中区2#楼-203-C</td>\n" +
                "\t\t\t\t\t</tr>\n" +
                "\t\t\t\t\t<tr class=\"odd\" >\n" +
                "\t\t\t\t\t\t<td class=\"tdSN\" ></td>\n" +
                "\t\t\t\t\t\t<td class=\"tdClassInfo\" >2017计算机科学与技术2班</td>\n" +
                "\t\t\t\t\t\t<td class=\"tdNo\" >211706214</td>\n" +
                "\t\t\t\t\t\t<td class=\"tdName\" >俞伟建</td>\n" +
                "\t\t\t\t\t\t<td class=\"tdSex\" >男</td>\n" +
                "\t\t\t\t\t\t<td class=\"tdDormInfo\" >中区2#楼-116-B</td>\n" +
                "\t\t\t\t\t</tr>\n" +
                "\t\t\t\t\t<tr class=\"even\" >\n" +
                "\t\t\t\t\t\t<td class=\"tdSN\" ></td>\n" +
                "\t\t\t\t\t\t<td class=\"tdClassInfo\" >2017计算机科学与技术2班</td>\n" +
                "\t\t\t\t\t\t<td class=\"tdNo\" >211706213</td>\n" +
                "\t\t\t\t\t\t<td class=\"tdName\" >于佳宁</td>\n" +
                "\t\t\t\t\t\t<td class=\"tdSex\" >女</td>\n" +
                "\t\t\t\t\t\t<td class=\"tdDormInfo\" >东区2#楼-414-C</td>\n" +
                "\t\t\t\t\t</tr>\n" +
                "\t\t\t\t\t<tr class=\"odd\" >\n" +
                "\t\t\t\t\t\t<td class=\"tdSN\" ></td>\n" +
                "\t\t\t\t\t\t<td class=\"tdClassInfo\" >2017计算机科学与技术2班</td>\n" +
                "\t\t\t\t\t\t<td class=\"tdNo\" >211706212</td>\n" +
                "\t\t\t\t\t\t<td class=\"tdName\" >杨银泉</td>\n" +
                "\t\t\t\t\t\t<td class=\"tdSex\" >男</td>\n" +
                "\t\t\t\t\t\t<td class=\"tdDormInfo\" >中区2#楼-104-A</td>\n" +
                "\t\t\t\t\t</tr>\n" +
                "\t\t\t\t\t<tr class=\"even\" >\n" +
                "\t\t\t\t\t\t<td class=\"tdSN\" ></td>\n" +
                "\t\t\t\t\t\t<td class=\"tdClassInfo\" >2017计算机科学与技术2班</td>\n" +
                "\t\t\t\t\t\t<td class=\"tdNo\" >211706211</td>\n" +
                "\t\t\t\t\t\t<td class=\"tdName\" >谢文基</td>\n" +
                "\t\t\t\t\t\t<td class=\"tdSex\" >男</td>\n" +
                "\t\t\t\t\t\t<td class=\"tdDormInfo\" >中区2#楼-119-A</td>\n" +
                "\t\t\t\t\t</tr>\n" +
                "\t\t\t\t\t<tr class=\"odd\" >\n" +
                "\t\t\t\t\t\t<td class=\"tdSN\" ></td>\n" +
                "\t\t\t\t\t\t<td class=\"tdClassInfo\" >2017计算机科学与技术2班</td>\n" +
                "\t\t\t\t\t\t<td class=\"tdNo\" >211706210</td>\n" +
                "\t\t\t\t\t\t<td class=\"tdName\" >肖俊贤</td>\n" +
                "\t\t\t\t\t\t<td class=\"tdSex\" >男</td>\n" +
                "\t\t\t\t\t\t<td class=\"tdDormInfo\" >中区2#楼-108-A</td>\n" +
                "\t\t\t\t\t</tr>\n" +
                "\t\t\t\t\t<tr class=\"even\" >\n" +
                "\t\t\t\t\t\t<td class=\"tdSN\" ></td>\n" +
                "\t\t\t\t\t\t<td class=\"tdClassInfo\" >2017计算机科学与技术2班</td>\n" +
                "\t\t\t\t\t\t<td class=\"tdNo\" >211706208</td>\n" +
                "\t\t\t\t\t\t<td class=\"tdName\" >吴伟凯</td>\n" +
                "\t\t\t\t\t\t<td class=\"tdSex\" >男</td>\n" +
                "\t\t\t\t\t\t<td class=\"tdDormInfo\" >中区2#楼-203-B</td>\n" +
                "\t\t\t\t\t</tr>\n" +
                "\t\t\t\t\t<tr class=\"odd\" >\n" +
                "\t\t\t\t\t\t<td class=\"tdSN\" ></td>\n" +
                "\t\t\t\t\t\t<td class=\"tdClassInfo\" >2017计算机科学与技术2班</td>\n" +
                "\t\t\t\t\t\t<td class=\"tdNo\" >211706207</td>\n" +
                "\t\t\t\t\t\t<td class=\"tdName\" >吴婷婷</td>\n" +
                "\t\t\t\t\t\t<td class=\"tdSex\" >女</td>\n" +
                "\t\t\t\t\t\t<td class=\"tdDormInfo\" >东区2#楼-413-D</td>\n" +
                "\t\t\t\t\t</tr>\n" +
                "\t\t\t\t\t<tr class=\"even\" >\n" +
                "\t\t\t\t\t\t<td class=\"tdSN\" ></td>\n" +
                "\t\t\t\t\t\t<td class=\"tdClassInfo\" >2017计算机科学与技术2班</td>\n" +
                "\t\t\t\t\t\t<td class=\"tdNo\" >211706206</td>\n" +
                "\t\t\t\t\t\t<td class=\"tdName\" >吴秋悦</td>\n" +
                "\t\t\t\t\t\t<td class=\"tdSex\" >女</td>\n" +
                "\t\t\t\t\t\t<td class=\"tdDormInfo\" >东区2#楼-410-D</td>\n" +
                "\t\t\t\t\t</tr>\n" +
                "\t\t\t\t\t<tr class=\"odd\" >\n" +
                "\t\t\t\t\t\t<td class=\"tdSN\" ></td>\n" +
                "\t\t\t\t\t\t<td class=\"tdClassInfo\" >2017计算机科学与技术2班</td>\n" +
                "\t\t\t\t\t\t<td class=\"tdNo\" >211706205</td>\n" +
                "\t\t\t\t\t\t<td class=\"tdName\" >吴娉婷</td>\n" +
                "\t\t\t\t\t\t<td class=\"tdSex\" >女</td>\n" +
                "\t\t\t\t\t\t<td class=\"tdDormInfo\" >东区2#楼-410-A</td>\n" +
                "\t\t\t\t\t</tr>\n" +
                "\t\t\t\t\t<tr class=\"even\" >\n" +
                "\t\t\t\t\t\t<td class=\"tdSN\" ></td>\n" +
                "\t\t\t\t\t\t<td class=\"tdClassInfo\" >2017计算机科学与技术2班</td>\n" +
                "\t\t\t\t\t\t<td class=\"tdNo\" >211706204</td>\n" +
                "\t\t\t\t\t\t<td class=\"tdName\" >吴佳卉</td>\n" +
                "\t\t\t\t\t\t<td class=\"tdSex\" >女</td>\n" +
                "\t\t\t\t\t\t<td class=\"tdDormInfo\" >东区2#楼-415-D</td>\n" +
                "\t\t\t\t\t</tr>\n" +
                "\t\t\t\t\t<tr class=\"odd\" >\n" +
                "\t\t\t\t\t\t<td class=\"tdSN\" ></td>\n" +
                "\t\t\t\t\t\t<td class=\"tdClassInfo\" >2017计算机科学与技术2班</td>\n" +
                "\t\t\t\t\t\t<td class=\"tdNo\" >211706203</td>\n" +
                "\t\t\t\t\t\t<td class=\"tdName\" >魏忠杰</td>\n" +
                "\t\t\t\t\t\t<td class=\"tdSex\" >男</td>\n" +
                "\t\t\t\t\t\t<td class=\"tdDormInfo\" >中区2#楼-122-B</td>\n" +
                "\t\t\t\t\t</tr>\n" +
                "\t\t\t\t\t<tr class=\"even\" >\n" +
                "\t\t\t\t\t\t<td class=\"tdSN\" ></td>\n" +
                "\t\t\t\t\t\t<td class=\"tdClassInfo\" >2017计算机科学与技术2班</td>\n" +
                "\t\t\t\t\t\t<td class=\"tdNo\" >211706202</td>\n" +
                "\t\t\t\t\t\t<td class=\"tdName\" >魏鹏辉</td>\n" +
                "\t\t\t\t\t\t<td class=\"tdSex\" >男</td>\n" +
                "\t\t\t\t\t\t<td class=\"tdDormInfo\" >中区2#楼-104-B</td>\n" +
                "\t\t\t\t\t</tr>\n" +
                "\t\t\t\t\t<tr class=\"odd\" >\n" +
                "\t\t\t\t\t\t<td class=\"tdSN\" ></td>\n" +
                "\t\t\t\t\t\t<td class=\"tdClassInfo\" >2017计算机科学与技术2班</td>\n" +
                "\t\t\t\t\t\t<td class=\"tdNo\" >211706201</td>\n" +
                "\t\t\t\t\t\t<td class=\"tdName\" >王永乐</td>\n" +
                "\t\t\t\t\t\t<td class=\"tdSex\" >男</td>\n" +
                "\t\t\t\t\t\t<td class=\"tdDormInfo\" >中区2#楼-119-D</td>\n" +
                "\t\t\t\t\t</tr>\n" +
                "\t\t\t\t\t<tr class=\"even\" >\n" +
                "\t\t\t\t\t\t<td class=\"tdSN\" ></td>\n" +
                "\t\t\t\t\t\t<td class=\"tdClassInfo\" >2017计算机科学与技术2班</td>\n" +
                "\t\t\t\t\t\t<td class=\"tdNo\" >211706200</td>\n" +
                "\t\t\t\t\t\t<td class=\"tdName\" >王彤烨</td>\n" +
                "\t\t\t\t\t\t<td class=\"tdSex\" >女</td>\n" +
                "\t\t\t\t\t\t<td class=\"tdDormInfo\" >东区2#楼-411-B</td>\n" +
                "\t\t\t\t\t</tr>\n" +
                "\t\t\t\t\t<tr class=\"odd\" >\n" +
                "\t\t\t\t\t\t<td class=\"tdSN\" ></td>\n" +
                "\t\t\t\t\t\t<td class=\"tdClassInfo\" >2017计算机科学与技术2班</td>\n" +
                "\t\t\t\t\t\t<td class=\"tdNo\" >211706199</td>\n" +
                "\t\t\t\t\t\t<td class=\"tdName\" >王力杰</td>\n" +
                "\t\t\t\t\t\t<td class=\"tdSex\" >男</td>\n" +
                "\t\t\t\t\t\t<td class=\"tdDormInfo\" >中区2#楼-113-D</td>\n" +
                "\t\t\t\t\t</tr>\n" +
                "\t\t\t\t\t<tr class=\"even\" >\n" +
                "\t\t\t\t\t\t<td class=\"tdSN\" ></td>\n" +
                "\t\t\t\t\t\t<td class=\"tdClassInfo\" >2017计算机科学与技术2班</td>\n" +
                "\t\t\t\t\t\t<td class=\"tdNo\" >211706197</td>\n" +
                "\t\t\t\t\t\t<td class=\"tdName\" >汪钰莹</td>\n" +
                "\t\t\t\t\t\t<td class=\"tdSex\" >女</td>\n" +
                "\t\t\t\t\t\t<td class=\"tdDormInfo\" >东区2#楼-411-C</td>\n" +
                "\t\t\t\t\t</tr>\n" +
                "\t\t\t\t\t<tr class=\"odd\" >\n" +
                "\t\t\t\t\t\t<td class=\"tdSN\" ></td>\n" +
                "\t\t\t\t\t\t<td class=\"tdClassInfo\" >2017计算机科学与技术2班</td>\n" +
                "\t\t\t\t\t\t<td class=\"tdNo\" >211706196</td>\n" +
                "\t\t\t\t\t\t<td class=\"tdName\" >陶佳杰</td>\n" +
                "\t\t\t\t\t\t<td class=\"tdSex\" >男</td>\n" +
                "\t\t\t\t\t\t<td class=\"tdDormInfo\" >中区2#楼-117-D</td>\n" +
                "\t\t\t\t\t</tr>\n" +
                "\t\t\t\t\t<tr class=\"even\" >\n" +
                "\t\t\t\t\t\t<td class=\"tdSN\" ></td>\n" +
                "\t\t\t\t\t\t<td class=\"tdClassInfo\" >2017计算机科学与技术2班</td>\n" +
                "\t\t\t\t\t\t<td class=\"tdNo\" >211706194</td>\n" +
                "\t\t\t\t\t\t<td class=\"tdName\" >苏滨涛</td>\n" +
                "\t\t\t\t\t\t<td class=\"tdSex\" >男</td>\n" +
                "\t\t\t\t\t\t<td class=\"tdDormInfo\" >中区2#楼-118-D</td>\n" +
                "\t\t\t\t\t</tr>\n" +
                "\t\t\t\t\t<tr class=\"odd\" >\n" +
                "\t\t\t\t\t\t<td class=\"tdSN\" ></td>\n" +
                "\t\t\t\t\t\t<td class=\"tdClassInfo\" >2017计算机科学与技术2班</td>\n" +
                "\t\t\t\t\t\t<td class=\"tdNo\" >211706192</td>\n" +
                "\t\t\t\t\t\t<td class=\"tdName\" >卢健霖</td>\n" +
                "\t\t\t\t\t\t<td class=\"tdSex\" >男</td>\n" +
                "\t\t\t\t\t\t<td class=\"tdDormInfo\" >中区2#楼-204-D</td>\n" +
                "\t\t\t\t\t</tr>\n" +
                "\t\t\t\t\t<tr class=\"even\" >\n" +
                "\t\t\t\t\t\t<td class=\"tdSN\" ></td>\n" +
                "\t\t\t\t\t\t<td class=\"tdClassInfo\" >2017计算机科学与技术2班</td>\n" +
                "\t\t\t\t\t\t<td class=\"tdNo\" >211706191</td>\n" +
                "\t\t\t\t\t\t<td class=\"tdName\" >刘雨昂</td>\n" +
                "\t\t\t\t\t\t<td class=\"tdSex\" >男</td>\n" +
                "\t\t\t\t\t\t<td class=\"tdDormInfo\" >中区2#楼-106-D</td>\n" +
                "\t\t\t\t\t</tr>\n" +
                "\t\t\t\t\t<tr class=\"odd\" >\n" +
                "\t\t\t\t\t\t<td class=\"tdSN\" ></td>\n" +
                "\t\t\t\t\t\t<td class=\"tdClassInfo\" >2017计算机科学与技术2班</td>\n" +
                "\t\t\t\t\t\t<td class=\"tdNo\" >211706190</td>\n" +
                "\t\t\t\t\t\t<td class=\"tdName\" >林涛</td>\n" +
                "\t\t\t\t\t\t<td class=\"tdSex\" >男</td>\n" +
                "\t\t\t\t\t\t<td class=\"tdDormInfo\" >中区2#楼-113-B</td>\n" +
                "\t\t\t\t\t</tr>\n" +
                "\t\t\t\t\t<tr class=\"even\" >\n" +
                "\t\t\t\t\t\t<td class=\"tdSN\" ></td>\n" +
                "\t\t\t\t\t\t<td class=\"tdClassInfo\" >2017计算机科学与技术2班</td>\n" +
                "\t\t\t\t\t\t<td class=\"tdNo\" >211706188</td>\n" +
                "\t\t\t\t\t\t<td class=\"tdName\" >林铭智</td>\n" +
                "\t\t\t\t\t\t<td class=\"tdSex\" >男</td>\n" +
                "\t\t\t\t\t\t<td class=\"tdDormInfo\" >中区2#楼-119-B</td>\n" +
                "\t\t\t\t\t</tr>\n" +
                "\t\t\t\t\t<tr class=\"odd\" >\n" +
                "\t\t\t\t\t\t<td class=\"tdSN\" ></td>\n" +
                "\t\t\t\t\t\t<td class=\"tdClassInfo\" >2017计算机科学与技术2班</td>\n" +
                "\t\t\t\t\t\t<td class=\"tdNo\" >211706187</td>\n" +
                "\t\t\t\t\t\t<td class=\"tdName\" >林连坤</td>\n" +
                "\t\t\t\t\t\t<td class=\"tdSex\" >男</td>\n" +
                "\t\t\t\t\t\t<td class=\"tdDormInfo\" >中区2#楼-202-D</td>\n" +
                "\t\t\t\t\t</tr>\n" +
                "\t\t\t\t\t<tr class=\"even\" >\n" +
                "\t\t\t\t\t\t<td class=\"tdSN\" ></td>\n" +
                "\t\t\t\t\t\t<td class=\"tdClassInfo\" >2017计算机科学与技术2班</td>\n" +
                "\t\t\t\t\t\t<td class=\"tdNo\" >211706185</td>\n" +
                "\t\t\t\t\t\t<td class=\"tdName\" >梁梓银</td>\n" +
                "\t\t\t\t\t\t<td class=\"tdSex\" >男</td>\n" +
                "\t\t\t\t\t\t<td class=\"tdDormInfo\" >中区2#楼-202-A</td>\n" +
                "\t\t\t\t\t</tr>\n" +
                "\t\t\t\t\t<tr class=\"odd\" >\n" +
                "\t\t\t\t\t\t<td class=\"tdSN\" ></td>\n" +
                "\t\t\t\t\t\t<td class=\"tdClassInfo\" >2017计算机科学与技术2班</td>\n" +
                "\t\t\t\t\t\t<td class=\"tdNo\" >211706184</td>\n" +
                "\t\t\t\t\t\t<td class=\"tdName\" >连辛集</td>\n" +
                "\t\t\t\t\t\t<td class=\"tdSex\" >男</td>\n" +
                "\t\t\t\t\t\t<td class=\"tdDormInfo\" >中区2#楼-108-B</td>\n" +
                "\t\t\t\t\t</tr>\n" +
                "\t\t\t\t\t<tr class=\"even\" >\n" +
                "\t\t\t\t\t\t<td class=\"tdSN\" ></td>\n" +
                "\t\t\t\t\t\t<td class=\"tdClassInfo\" >2017计算机科学与技术2班</td>\n" +
                "\t\t\t\t\t\t<td class=\"tdNo\" >211706183</td>\n" +
                "\t\t\t\t\t\t<td class=\"tdName\" >连捷</td>\n" +
                "\t\t\t\t\t\t<td class=\"tdSex\" >男</td>\n" +
                "\t\t\t\t\t\t<td class=\"tdDormInfo\" >中区2#楼-122-C</td>\n" +
                "\t\t\t\t\t</tr>\n" +
                "\t\t\t\t\t<tr class=\"odd\" >\n" +
                "\t\t\t\t\t\t<td class=\"tdSN\" ></td>\n" +
                "\t\t\t\t\t\t<td class=\"tdClassInfo\" >2017计算机科学与技术2班</td>\n" +
                "\t\t\t\t\t\t<td class=\"tdNo\" >211706181</td>\n" +
                "\t\t\t\t\t\t<td class=\"tdName\" >李英梦</td>\n" +
                "\t\t\t\t\t\t<td class=\"tdSex\" >男</td>\n" +
                "\t\t\t\t\t\t<td class=\"tdDormInfo\" >中区2#楼-114-C</td>\n" +
                "\t\t\t\t\t</tr>\n" +
                "\t\t\t\t\t<tr class=\"even\" >\n" +
                "\t\t\t\t\t\t<td class=\"tdSN\" ></td>\n" +
                "\t\t\t\t\t\t<td class=\"tdClassInfo\" >2017计算机科学与技术2班</td>\n" +
                "\t\t\t\t\t\t<td class=\"tdNo\" >211706180</td>\n" +
                "\t\t\t\t\t\t<td class=\"tdName\" >李晓宇</td>\n" +
                "\t\t\t\t\t\t<td class=\"tdSex\" >男</td>\n" +
                "\t\t\t\t\t\t<td class=\"tdDormInfo\" >中区2#楼-108-D</td>\n" +
                "\t\t\t\t\t</tr>\n" +
                "\t\t\t\t\t<tr class=\"odd\" >\n" +
                "\t\t\t\t\t\t<td class=\"tdSN\" ></td>\n" +
                "\t\t\t\t\t\t<td class=\"tdClassInfo\" >2017计算机科学与技术2班</td>\n" +
                "\t\t\t\t\t\t<td class=\"tdNo\" >211706179</td>\n" +
                "\t\t\t\t\t\t<td class=\"tdName\" >李思锦</td>\n" +
                "\t\t\t\t\t\t<td class=\"tdSex\" >女</td>\n" +
                "\t\t\t\t\t\t<td class=\"tdDormInfo\" >东区2#楼-415-B</td>\n" +
                "\t\t\t\t\t</tr>\n" +
                "\t\t\t\t\t<tr class=\"even\" >\n" +
                "\t\t\t\t\t\t<td class=\"tdSN\" ></td>\n" +
                "\t\t\t\t\t\t<td class=\"tdClassInfo\" >2017计算机科学与技术2班</td>\n" +
                "\t\t\t\t\t\t<td class=\"tdNo\" >211706178</td>\n" +
                "\t\t\t\t\t\t<td class=\"tdName\" >江李悦</td>\n" +
                "\t\t\t\t\t\t<td class=\"tdSex\" >男</td>\n" +
                "\t\t\t\t\t\t<td class=\"tdDormInfo\" >中区2#楼-107-B</td>\n" +
                "\t\t\t\t\t</tr>\n" +
                "\t\t\t\t\t<tr class=\"odd\" >\n" +
                "\t\t\t\t\t\t<td class=\"tdSN\" ></td>\n" +
                "\t\t\t\t\t\t<td class=\"tdClassInfo\" >2017计算机科学与技术2班</td>\n" +
                "\t\t\t\t\t\t<td class=\"tdNo\" >211706177</td>\n" +
                "\t\t\t\t\t\t<td class=\"tdName\" >黄希敏</td>\n" +
                "\t\t\t\t\t\t<td class=\"tdSex\" >男</td>\n" +
                "\t\t\t\t\t\t<td class=\"tdDormInfo\" >中区2#楼-116-A</td>\n" +
                "\t\t\t\t\t</tr>\n" +
                "\t\t\t\t\t<tr class=\"even\" >\n" +
                "\t\t\t\t\t\t<td class=\"tdSN\" ></td>\n" +
                "\t\t\t\t\t\t<td class=\"tdClassInfo\" >2017计算机科学与技术2班</td>\n" +
                "\t\t\t\t\t\t<td class=\"tdNo\" >211706176</td>\n" +
                "\t\t\t\t\t\t<td class=\"tdName\" >黄婷婷</td>\n" +
                "\t\t\t\t\t\t<td class=\"tdSex\" >女</td>\n" +
                "\t\t\t\t\t\t<td class=\"tdDormInfo\" >东区2#楼-413-A</td>\n" +
                "\t\t\t\t\t</tr>\n" +
                "\t\t\t\t\t<tr class=\"odd\" >\n" +
                "\t\t\t\t\t\t<td class=\"tdSN\" ></td>\n" +
                "\t\t\t\t\t\t<td class=\"tdClassInfo\" >2017计算机科学与技术2班</td>\n" +
                "\t\t\t\t\t\t<td class=\"tdNo\" >211706174</td>\n" +
                "\t\t\t\t\t\t<td class=\"tdName\" >洪成龙</td>\n" +
                "\t\t\t\t\t\t<td class=\"tdSex\" >男</td>\n" +
                "\t\t\t\t\t\t<td class=\"tdDormInfo\" >中区2#楼-203-D</td>\n" +
                "\t\t\t\t\t</tr>\n" +
                "\t\t\t\t\t<tr class=\"even\" >\n" +
                "\t\t\t\t\t\t<td class=\"tdSN\" ></td>\n" +
                "\t\t\t\t\t\t<td class=\"tdClassInfo\" >2017计算机科学与技术2班</td>\n" +
                "\t\t\t\t\t\t<td class=\"tdNo\" >211706173</td>\n" +
                "\t\t\t\t\t\t<td class=\"tdName\" >何子聪</td>\n" +
                "\t\t\t\t\t\t<td class=\"tdSex\" >男</td>\n" +
                "\t\t\t\t\t\t<td class=\"tdDormInfo\" >中区2#楼-201-B</td>\n" +
                "\t\t\t\t\t</tr>\n" +
                "\t\t\t\t\t<tr class=\"odd\" >\n" +
                "\t\t\t\t\t\t<td class=\"tdSN\" ></td>\n" +
                "\t\t\t\t\t\t<td class=\"tdClassInfo\" >2017计算机科学与技术2班</td>\n" +
                "\t\t\t\t\t\t<td class=\"tdNo\" >211706172</td>\n" +
                "\t\t\t\t\t\t<td class=\"tdName\" >何鑫</td>\n" +
                "\t\t\t\t\t\t<td class=\"tdSex\" >男</td>\n" +
                "\t\t\t\t\t\t<td class=\"tdDormInfo\" >中区2#楼-112-B</td>\n" +
                "\t\t\t\t\t</tr>\n" +
                "\t\t\t\t\t<tr class=\"even\" >\n" +
                "\t\t\t\t\t\t<td class=\"tdSN\" ></td>\n" +
                "\t\t\t\t\t\t<td class=\"tdClassInfo\" >2017计算机科学与技术2班</td>\n" +
                "\t\t\t\t\t\t<td class=\"tdNo\" >211706171</td>\n" +
                "\t\t\t\t\t\t<td class=\"tdName\" >郭文昱</td>\n" +
                "\t\t\t\t\t\t<td class=\"tdSex\" >女</td>\n" +
                "\t\t\t\t\t\t<td class=\"tdDormInfo\" >东区2#楼-410-C</td>\n" +
                "\t\t\t\t\t</tr>\n" +
                "\t\t\t\t\t<tr class=\"odd\" >\n" +
                "\t\t\t\t\t\t<td class=\"tdSN\" ></td>\n" +
                "\t\t\t\t\t\t<td class=\"tdClassInfo\" >2017计算机科学与技术2班</td>\n" +
                "\t\t\t\t\t\t<td class=\"tdNo\" >211706170</td>\n" +
                "\t\t\t\t\t\t<td class=\"tdName\" >冯志成</td>\n" +
                "\t\t\t\t\t\t<td class=\"tdSex\" >男</td>\n" +
                "\t\t\t\t\t\t<td class=\"tdDormInfo\" >中区2#楼-118-B</td>\n" +
                "\t\t\t\t\t</tr>\n" +
                "\t\t\t\t\t<tr class=\"even\" >\n" +
                "\t\t\t\t\t\t<td class=\"tdSN\" ></td>\n" +
                "\t\t\t\t\t\t<td class=\"tdClassInfo\" >2017计算机科学与技术2班</td>\n" +
                "\t\t\t\t\t\t<td class=\"tdNo\" >211706169</td>\n" +
                "\t\t\t\t\t\t<td class=\"tdName\" >方介斌</td>\n" +
                "\t\t\t\t\t\t<td class=\"tdSex\" >男</td>\n" +
                "\t\t\t\t\t\t<td class=\"tdDormInfo\" >中区2#楼-107-D</td>\n" +
                "\t\t\t\t\t</tr>\n" +
                "\t\t\t\t\t<tr class=\"odd\" >\n" +
                "\t\t\t\t\t\t<td class=\"tdSN\" ></td>\n" +
                "\t\t\t\t\t\t<td class=\"tdClassInfo\" >2017计算机科学与技术2班</td>\n" +
                "\t\t\t\t\t\t<td class=\"tdNo\" >211706168</td>\n" +
                "\t\t\t\t\t\t<td class=\"tdName\" >邓慧</td>\n" +
                "\t\t\t\t\t\t<td class=\"tdSex\" >男</td>\n" +
                "\t\t\t\t\t\t<td class=\"tdDormInfo\" >中区2#楼-104-C</td>\n" +
                "\t\t\t\t\t</tr>\n" +
                "\t\t\t\t\t<tr class=\"even\" >\n" +
                "\t\t\t\t\t\t<td class=\"tdSN\" ></td>\n" +
                "\t\t\t\t\t\t<td class=\"tdClassInfo\" >2017计算机科学与技术2班</td>\n" +
                "\t\t\t\t\t\t<td class=\"tdNo\" >211706167</td>\n" +
                "\t\t\t\t\t\t<td class=\"tdName\" >程昱</td>\n" +
                "\t\t\t\t\t\t<td class=\"tdSex\" >男</td>\n" +
                "\t\t\t\t\t\t<td class=\"tdDormInfo\" >中区2#楼-205-A</td>\n" +
                "\t\t\t\t\t</tr>\n" +
                "\t\t\t\t\t<tr class=\"odd\" >\n" +
                "\t\t\t\t\t\t<td class=\"tdSN\" ></td>\n" +
                "\t\t\t\t\t\t<td class=\"tdClassInfo\" >2017计算机科学与技术2班</td>\n" +
                "\t\t\t\t\t\t<td class=\"tdNo\" >211706166</td>\n" +
                "\t\t\t\t\t\t<td class=\"tdName\" >程顺明</td>\n" +
                "\t\t\t\t\t\t<td class=\"tdSex\" >男</td>\n" +
                "\t\t\t\t\t\t<td class=\"tdDormInfo\" >中区2#楼-111-C</td>\n" +
                "\t\t\t\t\t</tr>\n" +
                "\t\t\t\t\t<tr class=\"even\" >\n" +
                "\t\t\t\t\t\t<td class=\"tdSN\" ></td>\n" +
                "\t\t\t\t\t\t<td class=\"tdClassInfo\" >2017计算机科学与技术2班</td>\n" +
                "\t\t\t\t\t\t<td class=\"tdNo\" >211706165</td>\n" +
                "\t\t\t\t\t\t<td class=\"tdName\" >陈仙杰</td>\n" +
                "\t\t\t\t\t\t<td class=\"tdSex\" >男</td>\n" +
                "\t\t\t\t\t\t<td class=\"tdDormInfo\" >中区2#楼-105-D</td>\n" +
                "\t\t\t\t\t</tr>\n" +
                "\t\t\t\t\t<tr class=\"odd\" >\n" +
                "\t\t\t\t\t\t<td class=\"tdSN\" ></td>\n" +
                "\t\t\t\t\t\t<td class=\"tdClassInfo\" >2017计算机科学与技术2班</td>\n" +
                "\t\t\t\t\t\t<td class=\"tdNo\" >211706164</td>\n" +
                "\t\t\t\t\t\t<td class=\"tdName\" >陈德渠</td>\n" +
                "\t\t\t\t\t\t<td class=\"tdSex\" >男</td>\n" +
                "\t\t\t\t\t\t<td class=\"tdDormInfo\" >中区2#楼-112-C</td>\n" +
                "\t\t\t\t\t</tr>\n" +
                "\t\t\t\t\t<tr class=\"even\" >\n" +
                "\t\t\t\t\t\t<td class=\"tdSN\" ></td>\n" +
                "\t\t\t\t\t\t<td class=\"tdClassInfo\" >2017计算机科学与技术2班</td>\n" +
                "\t\t\t\t\t\t<td class=\"tdNo\" >211706163</td>\n" +
                "\t\t\t\t\t\t<td class=\"tdName\" >曹淦淇</td>\n" +
                "\t\t\t\t\t\t<td class=\"tdSex\" >男</td>\n" +
                "\t\t\t\t\t\t<td class=\"tdDormInfo\" >中区2#楼-201-D</td>\n" +
                "\t\t\t\t\t</tr>\n" +
                "\t\t\t\t\t<tr class=\"odd\" >\n" +
                "\t\t\t\t\t\t<td class=\"tdSN\" ></td>\n" +
                "\t\t\t\t\t\t<td class=\"tdClassInfo\" >2017计算机科学与技术2班</td>\n" +
                "\t\t\t\t\t\t<td class=\"tdNo\" >211706162</td>\n" +
                "\t\t\t\t\t\t<td class=\"tdName\" >蔡泽华</td>\n" +
                "\t\t\t\t\t\t<td class=\"tdSex\" >男</td>\n" +
                "\t\t\t\t\t\t<td class=\"tdDormInfo\" >中区2#楼-112-D</td>\n" +
                "\t\t\t\t\t</tr>\n" +
                "\t\t\t\t\t<tr class=\"even\" >\n" +
                "\t\t\t\t\t\t<td class=\"tdSN\" ></td>\n" +
                "\t\t\t\t\t\t<td class=\"tdClassInfo\" >2017计算机科学与技术2班</td>\n" +
                "\t\t\t\t\t\t<td class=\"tdNo\" >211706161</td>\n" +
                "\t\t\t\t\t\t<td class=\"tdName\" >蔡雨婷</td>\n" +
                "\t\t\t\t\t\t<td class=\"tdSex\" >女</td>\n" +
                "\t\t\t\t\t\t<td class=\"tdDormInfo\" >东区2#楼-411-D</td>\n" +
                "\t\t\t\t\t</tr>\n" +
                "\t\t\t\t</tbody>\n" +
                "\t\t\t</table>\n" +
                "\t\t</div>\n" +
                "\t</body>\n" +
                "</html>\n";
    }
}