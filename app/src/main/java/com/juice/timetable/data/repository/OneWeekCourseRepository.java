package com.juice.timetable.data.repository;

import android.content.Context;
import android.os.AsyncTask;

import androidx.lifecycle.LiveData;

import com.juice.timetable.data.JuiceDatabase;
import com.juice.timetable.data.bean.OneWeekCourse;
import com.juice.timetable.data.dao.OneWeekCourseDao;

import java.util.ArrayList;
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

    public void insertOneWeekCourse(OneWeekCourse... courses) {
        new InsertAsyncTask(oneWeekCourseDao).execute(courses);
    }

    public void deleteOneWeekCourse(Void... Voids) {
        new DeleteAsyncTask(oneWeekCourseDao).execute();
    }

    public List<OneWeekCourse> getOneWeekCourse() {
        List<OneWeekCourse> oneWeekCourses = null;
        AsyncTask<Void, Void, List<OneWeekCourse>> asyncTask = new SelectAsyncTask(oneWeekCourseDao).execute();
        try {
            oneWeekCourses = asyncTask.get();
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return oneWeekCourses;
    }

    public List<Integer> getWeek() {
        List<Integer> integers = null;
        AsyncTask<Void, Void, List<Integer>> asyncTask = new SelectWeekAsyncTask(oneWeekCourseDao).execute();
        try {
            integers = asyncTask.get();
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return integers;
    }

    public void deleteWeek(ArrayList<Integer>... arrayLists) {
        new DeleteWeekAsyncTask(oneWeekCourseDao).execute(arrayLists[0]);
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

    //查询
    static class SelectAsyncTask extends AsyncTask<Void, Void, List<OneWeekCourse>> {
        private OneWeekCourseDao oneWeekCourseDao;

        SelectAsyncTask(OneWeekCourseDao oneWeekCourseDao) {
            this.oneWeekCourseDao = oneWeekCourseDao;
        }

        @Override
        protected List<OneWeekCourse> doInBackground(Void... Voids) {
            return oneWeekCourseDao.getOneWeekCourse();
        }
    }

    //查询周数
    static class SelectWeekAsyncTask extends AsyncTask<Void, Void, List<Integer>> {
        private OneWeekCourseDao oneWeekCourseDao;

        SelectWeekAsyncTask(OneWeekCourseDao oneWeekCourseDao) {
            this.oneWeekCourseDao = oneWeekCourseDao;
        }

        @Override
        protected List<Integer> doInBackground(Void... Voids) {
            return oneWeekCourseDao.getInWeek();
        }
    }

    //删除指定周
    static class DeleteWeekAsyncTask extends AsyncTask<ArrayList<Integer>, Void, Void> {
        private OneWeekCourseDao oneWeekCourseDao;

        DeleteWeekAsyncTask(OneWeekCourseDao oneWeekCourseDao) {
            this.oneWeekCourseDao = oneWeekCourseDao;
        }


        @Override
        protected Void doInBackground(ArrayList<Integer>... arrayLists) {
            oneWeekCourseDao.deleteCourseByWeek(arrayLists[0]);
            return null;
        }

    }
}
