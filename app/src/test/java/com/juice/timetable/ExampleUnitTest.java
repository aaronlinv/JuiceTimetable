package com.juice.timetable;

import com.juice.timetable.data.ParseAllWeek;
import com.juice.timetable.data.ParseCheckIn;
import com.juice.timetable.data.ParseOneWeek;

import org.junit.Test;

import java.text.ParseException;

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

        ParseAllWeek.parseAllCourse();
    }

    @Test
    public void OneCourseTest() {
        ParseOneWeek.parseCourse();

    }

    @Test
    public void leaveTest() throws ParseException {
        ParseCheckIn.getMySigned();
        ParseCheckIn.getClassUnSigned();
    }

}