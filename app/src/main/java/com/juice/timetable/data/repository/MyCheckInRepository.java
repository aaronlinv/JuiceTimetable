package com.juice.timetable.data.repository;

import android.content.Context;
import android.os.AsyncTask;

import com.juice.timetable.data.JuiceDatabase;
import com.juice.timetable.data.bean.MyCheckIn;
import com.juice.timetable.data.dao.MyCheckInDao;

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
public class MyCheckInRepository {
    private MyCheckIn myCheckIn;
    private MyCheckInDao myCheckInDao;

    public MyCheckInRepository(Context context) {
        JuiceDatabase juiceDatabase = JuiceDatabase.getDatabase(context);
        myCheckInDao = juiceDatabase.getMyCheckInDao();
        myCheckIn = myCheckInDao.getMyCheckIn();
    }

    public MyCheckIn getMyCheckIn() {
        return myCheckIn;
    }


    public void insertMyCheckIn(MyCheckIn... myCheckIns) {
        new InsertAsyncTask(myCheckInDao).execute(myCheckIns);
    }

    public void deleteMyCheckIn(Void... Voids) {
        new DeleteAsyncTask(myCheckInDao).execute();
    }


    //插入
    static class InsertAsyncTask extends AsyncTask<MyCheckIn, Void, Void> {
        private MyCheckInDao myCheckInDao;


        InsertAsyncTask(MyCheckInDao myCheckInDao) {
            this.myCheckInDao = myCheckInDao;
        }

        @Override
        protected Void doInBackground(MyCheckIn... myCheckIns) {
            myCheckInDao.insertMyCheckIn(myCheckIns);
            return null;
        }
    }

    //删除
    static class DeleteAsyncTask extends AsyncTask<Void, Void, Void> {
        private MyCheckInDao myCheckInDao;

        DeleteAsyncTask(MyCheckInDao myCheckInDao) {
            this.myCheckInDao = myCheckInDao;
        }

        @Override
        protected Void doInBackground(Void... Voids) {
            myCheckInDao.deleteMyCheckIn();
            return null;
        }
    }
}
