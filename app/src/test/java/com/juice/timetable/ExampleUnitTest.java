package com.juice.timetable;

import androidx.lifecycle.LiveData;

import com.juice.timetable.data.bean.OneWeekCourse;
import com.juice.timetable.data.database.Dao.OneWeekCourseDao;
import com.juice.timetable.data.database.JuiceDatabase;
import com.juice.timetable.data.parse.ParseAllWeek;
import com.juice.timetable.data.parse.ParseCheckIn;
import com.juice.timetable.data.parse.ParseOneWeek;

import org.junit.Test;

import java.text.ParseException;
import java.util.List;

import static org.junit.Assert.assertEquals;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
public class ExampleUnitTest {
    static OneWeekCourseDao oneWeekCourseDao;
    static JuiceDatabase juiceDatabase;
    static LiveData<List<OneWeekCourse>> OneWeekCourseLive;

    @Test
    public void addition_isCorrect() {
        assertEquals(4, 2 + 2);
    }

    @Test
    public void AllCourseTest() {

        ParseAllWeek.parseAllCourse();
    }

    @Test
    public void OneCourseTest() {
        List<OneWeekCourse> oneWeekCourses = ParseOneWeek.parseCourse();
        System.out.println(oneWeekCourses);

    }

    @Test
    public void leaveTest() throws ParseException {
        ParseCheckIn.getMySigned();
        ParseCheckIn.getClassNoSigned();
    }




}