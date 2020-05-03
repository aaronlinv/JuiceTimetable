package com.juice.timetable;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.room.DatabaseConfiguration;
import androidx.room.InvalidationTracker;
import androidx.sqlite.db.SupportSQLiteOpenHelper;

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
        ParseCheckIn.getClassUnSigned();
    }


    @Test
    public void test() {
/*        new ViewModelProvider
        List<OneWeekCourse> oneWeekCourses = ParseOneWeek.parseCourse();
        oneWeekCourseDao = JuiceDatabase.getCourseDao();
        OneWeekCourseLive = oneWeekCourseDao.getOneWeekCourseLive();
        OneWeekCourseLive.observe(this, new Observer<List<OneWeekCourse>>() {
            @Override
            public void onChanged(List<OneWeekCourse> oneWeekCourses) {

            }
        });
        System.out.println(oneWeekCourses);
        oneWeekCourseDao.insertOneWeekCourse(oneWeekCourses);

    }
    static class InsertAsyncTask extends AsyncTask<OneWeekCourse,void,void>{


        @Override
        protected void doInBackground(OneWeekCourse... oneWeekCourses) {
            oneWeekCourseDao.insertOneWeekCourse();
        }*/
    }

}