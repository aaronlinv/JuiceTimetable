package com.juice.timetable.data.Repository;

import android.content.Context;
import android.os.AsyncTask;

import com.juice.timetable.data.Dao.AllWeekCourseDao;
import com.juice.timetable.data.JuiceDatabase;
import com.juice.timetable.data.bean.Course;

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
    private List<Course> allWeekCourseList;
    private AllWeekCourseDao allWeekCourseDao;

    public AllWeekCourseRepository(Context context) {
        JuiceDatabase juiceDatabase = JuiceDatabase.getDatabase(context);
        allWeekCourseDao = juiceDatabase.getAllWeekCourseDao();
        allWeekCourseList = allWeekCourseDao.getAllWeekCourse();
    }

    public List<Course> getClassNoSignedItem() {
        return allWeekCourseList;
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


        public InsertAsyncTask(AllWeekCourseDao allWeekCourseDao) {
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
