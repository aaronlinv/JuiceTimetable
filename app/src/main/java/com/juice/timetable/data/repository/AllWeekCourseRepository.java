package com.juice.timetable.data.repository;

import android.content.Context;
import android.os.AsyncTask;

import com.juice.timetable.data.JuiceDatabase;
import com.juice.timetable.data.bean.Course;
import com.juice.timetable.data.dao.AllWeekCourseDao;

import java.util.List;
import java.util.concurrent.ExecutionException;

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

    private AllWeekCourseDao allWeekCourseDao;

    public AllWeekCourseRepository(Context context) {
        JuiceDatabase juiceDatabase = JuiceDatabase.getDatabase(context);
        allWeekCourseDao = juiceDatabase.getAllWeekCourseDao();
    }


    public void insertAllWeekCourse(Course... courses) {
        new InsertAsyncTask(allWeekCourseDao).execute(courses);
    }

    public void deleteAllWeekCourse(Void... Voids) {
        new DeleteAsyncTask(allWeekCourseDao).execute();
    }

    public List<Course> getAllWeekCourse(Void... voids) {
        List<Course> courses = null;
        AsyncTask<Void, Void, List<Course>> execute = new SelectAsyncTask(allWeekCourseDao).execute();
        try {
            courses = execute.get();
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return courses;
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

    //查询
    static class SelectAsyncTask extends AsyncTask<Void, Void, List<Course>> {
        private AllWeekCourseDao allWeekCourseDao;

        SelectAsyncTask(AllWeekCourseDao allWeekCourseDao) {
            this.allWeekCourseDao = allWeekCourseDao;
        }

        @Override
        protected List<Course> doInBackground(Void... Voids) {
            return allWeekCourseDao.getAllWeekCourse();
        }
    }

}
