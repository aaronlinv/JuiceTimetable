package com.juice.timetable.data.repository;

import android.content.Context;
import android.os.AsyncTask;

import androidx.lifecycle.LiveData;

import com.juice.timetable.data.JuiceDatabase;
import com.juice.timetable.data.bean.Course;
import com.juice.timetable.data.dao.AllWeekCourseDao;

import java.util.List;

/**
 * <pre>
 *     author : soreak
 *     e-mail : sorea1k@163.com
 *     time   : 2020/05/04
 *     desc   : nothing
 *     item   : juice
 *     version: 1.0
 * </pre>
 */
public class AllWeekCourseRepository {
    private LiveData<List<Course>> allWeekCourseLive;
    private AllWeekCourseDao allWeekCourseDao;

    public AllWeekCourseRepository(Context context) {
        JuiceDatabase juiceDatabase = JuiceDatabase.getDatabase(context);
        allWeekCourseDao = juiceDatabase.getAllWeekCourseDao();
        allWeekCourseLive = allWeekCourseDao.getAllWeekCourseLive();
    }

    public LiveData<List<Course>> getAllWeekCourseLive() {
        return allWeekCourseLive;
    }


    public void insertAllWeekCourse(Course... courses) {
        new InsertAsyncTask(allWeekCourseDao).execute(courses);
    }

    public void deleteAllWeekCourse(Void... Voids) {
        new DeleteAsyncTask(allWeekCourseDao).execute();
    }


    //插入
    static class InsertAsyncTask extends AsyncTask<Course, Void, Void> {
        private AllWeekCourseDao allWeekCourseDao;


        InsertAsyncTask(AllWeekCourseDao allWeekCourseDao) {
            this.allWeekCourseDao = allWeekCourseDao;
        }

        @Override
        protected Void doInBackground(Course... courses) {
            allWeekCourseDao.insertAllWeekCourse(courses);
            return null;
        }
    }

    //删除
    static class DeleteAsyncTask extends AsyncTask<Void, Void, Void> {
        private AllWeekCourseDao allWeekCourseDao;

        DeleteAsyncTask(AllWeekCourseDao allWeekCourseDao) {
            this.allWeekCourseDao = allWeekCourseDao;
        }

        @Override
        protected Void doInBackground(Void... Voids) {
            allWeekCourseDao.deleteAllWeekCourse();
            return null;
        }
    }
}
