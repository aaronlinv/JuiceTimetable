package com.juice.timetable.data.database.Repository;

import android.content.Context;
import android.os.AsyncTask;

import androidx.lifecycle.LiveData;

import com.juice.timetable.data.bean.AllWeekCourse;
import com.juice.timetable.data.database.Dao.AllWeekCourseDao;
import com.juice.timetable.data.database.JuiceDatabase;

import java.util.List;

/**
 * <pre>
 *     author : soreak
 *     e-mail : sorea1k@163.com
 *     time   : 2020/05/03
 *     desc   : nothing
 *     item   : juice
 *     version: 1.0
 * </pre>
 */
public class AllWeekCourseRepository {
    private LiveData<List<AllWeekCourse>> allWeekCourseLive;
    private AllWeekCourseDao allWeekCourseDao;

    public AllWeekCourseRepository(Context context) {
        JuiceDatabase juiceDatabase = JuiceDatabase.getDatabase(context);
        allWeekCourseDao = juiceDatabase.getAllCourseDao();
        allWeekCourseLive = allWeekCourseDao.getAllWeekCourseLive();
    }

    public LiveData<List<AllWeekCourse>> getAllWeekCourseLive() {
        return allWeekCourseLive;
    }

    //对应存储数据库AllWeekCourse表的接口
    public void insertAllWeekCourse(AllWeekCourse... allWeekCourses) {
        new AllWeekCourseRepository.InsertAsyncTask(allWeekCourseDao).execute(allWeekCourses);
    }

    //删除数据库AllWeekCourse表的接口
    public void deleteAllWeekCourse(Void... Voids) {
        new AllWeekCourseRepository.DeleteAsyncTask(allWeekCourseDao).execute();
    }


    //插入
    static class InsertAsyncTask extends AsyncTask<AllWeekCourse, Void, Void> {
        private AllWeekCourseDao allWeekCourseDao;


        public InsertAsyncTask(AllWeekCourseDao allWeekCourseDao) {
            this.allWeekCourseDao = allWeekCourseDao;
        }

        @Override
        protected Void doInBackground(AllWeekCourse... allWeekCourses) {
            allWeekCourseDao.insertAllWeekCourse(allWeekCourses);
            return null;
        }
    }

    //删除
    static class DeleteAsyncTask extends AsyncTask<Void, Void, Void> {
        private AllWeekCourseDao allWeekCourseDao;

        public DeleteAsyncTask(AllWeekCourseDao allWeekCourseDao) {
            this.allWeekCourseDao = allWeekCourseDao;
        }

        @Override
        protected Void doInBackground(Void... Voids) {
            allWeekCourseDao.deleteAllWeekCourse();
            return null;
        }
    }
}
