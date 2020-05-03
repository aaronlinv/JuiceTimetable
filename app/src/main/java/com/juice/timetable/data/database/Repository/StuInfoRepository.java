package com.juice.timetable.data.database.Repository;

import android.content.Context;
import android.os.AsyncTask;

import androidx.lifecycle.LiveData;

import com.juice.timetable.data.bean.StuInfo;
import com.juice.timetable.data.database.Dao.StuInfoDao;
import com.juice.timetable.data.database.JuiceDatabase;

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
public class StuInfoRepository {
    private LiveData<StuInfo> stuInfoLive;
    private StuInfoDao stuInfoDao;

    public StuInfoRepository(Context context) {
        JuiceDatabase juiceDatabase = JuiceDatabase.getDatabase(context);
        stuInfoDao = juiceDatabase.getStuInfoDao();
        stuInfoLive = stuInfoDao.getStuInfoLive();
    }

    public LiveData<StuInfo> getStuInfoLive() {
        return stuInfoLive;
    }


    public void insertStuInfo(StuInfo... stuInfo) {
        new StuInfoRepository.InsertAsyncTask(stuInfoDao).execute(stuInfo);
    }

    public void deleteStuInfo(Void... Voids) {
        new StuInfoRepository.DeleteAsyncTask(stuInfoDao).execute();
    }


    //插入
    static class InsertAsyncTask extends AsyncTask<StuInfo, Void, Void> {
        private StuInfoDao stuInfoDao;


        public InsertAsyncTask(StuInfoDao stuInfoDao) {
            this.stuInfoDao = stuInfoDao;
        }

        @Override
        protected Void doInBackground(StuInfo... stuInfo) {
            stuInfoDao.insertStuInfo(stuInfo);
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
