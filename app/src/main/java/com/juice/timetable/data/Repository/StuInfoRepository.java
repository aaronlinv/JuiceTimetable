package com.juice.timetable.data.Repository;


import android.content.Context;
import android.os.AsyncTask;

import com.juice.timetable.data.Dao.StuInfoDao;
import com.juice.timetable.data.JuiceDatabase;
import com.juice.timetable.data.bean.StuInfo;

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
public class StuInfoRepository {
    private StuInfo stuInfo;
    private StuInfoDao stuInfoDao;

    public StuInfoRepository(Context context) {
        JuiceDatabase juiceDatabase = JuiceDatabase.getDatabase(context);
        stuInfoDao = juiceDatabase.getStuInfoDao();
        stuInfo = stuInfoDao.getStuInfo();
    }

    public StuInfo getStuInfo() {
        return stuInfo;
    }


    public void insertStuInfo(StuInfo... stuInfos) {
        new InsertAsyncTask(stuInfoDao).execute(stuInfos);
    }

    public void deleteStuInfo(Void... Voids) {
        new DeleteAsyncTask(stuInfoDao).execute();
    }


    //插入
    static class InsertAsyncTask extends AsyncTask<StuInfo, Void, Void> {
        private StuInfoDao stuInfoDao;


        public InsertAsyncTask(StuInfoDao stuInfoDao) {
            this.stuInfoDao = stuInfoDao;
        }

        @Override
        protected Void doInBackground(StuInfo... stuInfos) {
            stuInfoDao.insertStuInfo(stuInfos);
            return null;
        }
    }

    //删除
    static class DeleteAsyncTask extends AsyncTask<Void, Void, Void> {
        private StuInfoDao stuInfoDao;

        public DeleteAsyncTask(StuInfoDao stuInfoDao) {
            this.stuInfoDao = stuInfoDao;
        }

        @Override
        protected Void doInBackground(Void... Voids) {
            stuInfoDao.deleteStuInfo();
            return null;
        }
    }
}
