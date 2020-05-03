package com.juice.timetable.data.database.Repository;

import android.content.Context;
import android.os.AsyncTask;

import androidx.lifecycle.LiveData;

import com.juice.timetable.data.bean.OneWeekCourse;
import com.juice.timetable.data.database.Dao.OneWeekCourseDao;
import com.juice.timetable.data.database.JuiceDatabase;

import java.util.List;

/**
 * <pre>
 *     author : soreak
 *     e-mail : sorea1k@163.com
 *     time   : 2020/05/02
 *     desc   : nothing
 *     item   : juice
 *     version: 1.0
 * </pre>
 */
public class OneWeekCourseRepository {
    private LiveData<List<OneWeekCourse>> oneWeekCourseLive;
    private OneWeekCourseDao oneWeekCourseDao;
    public OneWeekCourseRepository(Context context) {
        JuiceDatabase juiceDatabase = JuiceDatabase.getDatabase(context);
        oneWeekCourseDao = juiceDatabase.getCourseDao();
        oneWeekCourseLive = oneWeekCourseDao.getOneWeekCourseLive();
    }

    public LiveData<List<OneWeekCourse>> getOneWeekCourseLive() {
        return oneWeekCourseLive;
    }

    //对应存储数据库OneWeekCourse表的接口
    public void insertOneWeekCourse(OneWeekCourse... oneWeekCourses){
        new InsertAsyncTask(oneWeekCourseDao).execute(oneWeekCourses);
    }
    //删除数据库OneWeekCourse表的接口
    public void deleteOneWeekCourse(Void... Voids){
        new DeleteAsyncTask(oneWeekCourseDao).execute();
    }


    //插入
    static class InsertAsyncTask extends AsyncTask<OneWeekCourse,Void,Void> {
        private OneWeekCourseDao oneWeekCourseDao;


        public InsertAsyncTask(OneWeekCourseDao oneWeekCourseDao){
            this.oneWeekCourseDao = oneWeekCourseDao;
        }
        @Override
        protected Void doInBackground(OneWeekCourse... oneWeekCourses) {
            oneWeekCourseDao.insertOneWeekCourse(oneWeekCourses);
            return null;
        }
    }
    //删除
    static class DeleteAsyncTask extends AsyncTask<Void,Void,Void>{
        private OneWeekCourseDao oneWeekCourseDao;
        public DeleteAsyncTask(OneWeekCourseDao oneWeekCourseDao){
            this.oneWeekCourseDao = oneWeekCourseDao;
        }
        @Override
        protected Void doInBackground(Void... Voids) {
            oneWeekCourseDao.deleteOneWeekCourse();
            return null;
        }
    }
}
