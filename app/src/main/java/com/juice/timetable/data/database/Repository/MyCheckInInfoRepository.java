package com.juice.timetable.data.database.Repository;

import android.content.Context;
import android.os.AsyncTask;

import androidx.lifecycle.LiveData;

import com.juice.timetable.data.bean.MyCheckInInfo;
import com.juice.timetable.data.database.Dao.MyCheckInInfoDao;
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
public class MyCheckInInfoRepository {
    private LiveData<MyCheckInInfo> myCheckInInfoLive;
    private MyCheckInInfoDao myCheckInInfoDao;

    public MyCheckInInfoRepository(Context context) {
        JuiceDatabase juiceDatabase = JuiceDatabase.getDatabase(context);
        myCheckInInfoDao = juiceDatabase.getMyCheckInInfoDao();
        myCheckInInfoLive = myCheckInInfoDao.getMyCheckInInfoLive();
    }

    public LiveData<MyCheckInInfo> getMyCheckInInfoLive() {
        return myCheckInInfoLive;
    }


    public void insertMyCheckInInfo(MyCheckInInfo... myCheckInInfo) {
        new MyCheckInInfoRepository.InsertAsyncTask(myCheckInInfoDao).execute(myCheckInInfo);
    }

    public void deleteMyCheckInInfo(Void... Voids) {
        new MyCheckInInfoRepository.DeleteAsyncTask(myCheckInInfoDao).execute();
    }


    //插入
    static class InsertAsyncTask extends AsyncTask<MyCheckInInfo, Void, Void> {
        private MyCheckInInfoDao myCheckInInfoDao;


        public InsertAsyncTask(MyCheckInInfoDao myCheckInInfoDao) {
            this.myCheckInInfoDao = myCheckInInfoDao;
        }

        @Override
        protected Void doInBackground(MyCheckInInfo... myCheckInInfo) {
            myCheckInInfoDao.insertMyCheckInInfo(myCheckInInfo);
            return null;
        }
    }

    //删除
    static class DeleteAsyncTask extends AsyncTask<Void, Void, Void> {
        private MyCheckInInfoDao myCheckInInfoDao;

        public DeleteAsyncTask(MyCheckInInfoDao myCheckInInfoDao) {
            this.myCheckInInfoDao = myCheckInInfoDao;
        }

        @Override
        protected Void doInBackground(Void... Voids) {
            myCheckInInfoDao.deleteMyCheckInInfo();
            return null;
        }
    }
}
