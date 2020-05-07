package com.juice.timetable.data.repository;

import android.content.Context;
import android.os.AsyncTask;

import androidx.lifecycle.LiveData;

import com.juice.timetable.data.JuiceDatabase;
import com.juice.timetable.data.bean.OneWeekCourse;
import com.juice.timetable.data.dao.OneWeekCourseDao;

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
public class OneWeekCourseRepository {
    private LiveData<List<OneWeekCourse>> oneWeekCourseLive;
    private LiveData<List<Integer>> inWeekLive;
    private OneWeekCourseDao oneWeekCourseDao;

    public OneWeekCourseRepository(Context context) {
        JuiceDatabase wordDatabase = JuiceDatabase.getDatabase(context);
        oneWeekCourseDao = wordDatabase.getOneWeekCourseDao();
        oneWeekCourseLive = oneWeekCourseDao.getOneWeekCourseLive();
        inWeekLive = oneWeekCourseDao.getInWeekLive();
    }

    public LiveData<List<OneWeekCourse>> getOneWeekCourseLive() {
        return oneWeekCourseLive;
    }

    public LiveData<List<Integer>> getInWeekLive() {
        return inWeekLive;
    }

    public void insertOneWeekCourse(OneWeekCourse... courses) {
        new InsertAsyncTask(oneWeekCourseDao).execute(courses);
    }

    public void deleteOneWeekCourse(Void... Voids) {
        new DeleteAsyncTask(oneWeekCourseDao).execute();
    }


    //插入
    static class InsertAsyncTask extends AsyncTask<OneWeekCourse, Void, Void> {
        private OneWeekCourseDao oneWeekCourseDao;


        InsertAsyncTask(OneWeekCourseDao oneWeekCourseDao) {
            this.oneWeekCourseDao = oneWeekCourseDao;
        }

        @Override
        protected Void doInBackground(OneWeekCourse... Courses) {
            oneWeekCourseDao.insertCourse(Courses);
            return null;
        }
    }

    //删除
    static class DeleteAsyncTask extends AsyncTask<Void, Void, Void> {
        private OneWeekCourseDao oneWeekCourseDao;

        DeleteAsyncTask(OneWeekCourseDao oneWeekCourseDao) {
            this.oneWeekCourseDao = oneWeekCourseDao;
        }

        @Override
        protected Void doInBackground(Void... Voids) {
            oneWeekCourseDao.deleteCourse();
            return null;
        }
    }
}
