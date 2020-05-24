package com.juice.timetable.data.repository;

import android.content.Context;
import android.os.AsyncTask;

import com.juice.timetable.data.JuiceDatabase;
import com.juice.timetable.data.bean.MyCheckIn;
import com.juice.timetable.data.dao.MyCheckInDao;

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
public class MyCheckInRepository {
    private MyCheckIn myCheckIn;
    private MyCheckInDao myCheckInDao;

    public MyCheckInRepository(Context context) {
        JuiceDatabase juiceDatabase = JuiceDatabase.getDatabase(context);
        myCheckInDao = juiceDatabase.getMyCheckInDao();
        myCheckIn = myCheckInDao.getMyCheckIn();
    }


    public void insertMyCheckIn(MyCheckIn... myCheckIns) {
        new InsertAsyncTask(myCheckInDao).execute(myCheckIns);
    }

    public void deleteMyCheckIn(Void... Voids) {
        new DeleteAsyncTask(myCheckInDao).execute();
    }

    public MyCheckIn getMyCheckIn(Void... voids) {
        MyCheckIn myCheckIn = null;
        AsyncTask<Void, Void, MyCheckIn> execute = new SelectAsyncTask(myCheckInDao).execute();
        try {
            myCheckIn = execute.get();
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return myCheckIn;
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

    //查询
    static class SelectAsyncTask extends AsyncTask<Void, Void, MyCheckIn> {
        private MyCheckInDao myCheckInDao;

        SelectAsyncTask(MyCheckInDao myCheckInDao) {
            this.myCheckInDao = myCheckInDao;
        }

        @Override
        protected MyCheckIn doInBackground(Void... Voids) {
            return myCheckInDao.getMyCheckIn();
        }
    }
}
