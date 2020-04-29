package com.juice.timetable.data.bean;

/**
 * <pre>
 *     author : Aaron
 *     time   : 2020/04/28
 *     desc   :
 *     version: 1.0
 * </pre>
 */
public class Course {

    private Long couID;

    private String couName;
    private String couRoom;
    private String couTeacher;

    // 这门课是在星期几上
    private Integer couWeek;

    private Integer couStartNode;
    private Integer couNodeCount;

    private String couAllWeek;

    // 这门课是从当天的第几节课开始
    private Integer couStartNodes;
    // 这门课是从当天的第几节课结束
    private Integer couEndNodes;
    // 单双周的判断
    private Integer couWeekType;
    // 这门课程开始于第几周
    private Integer couStartWeek;
    // 这门课结束于第几周
    private Integer couEndWeek;

    private Integer couColor;

}
