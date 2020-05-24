package com.juice.timetable.data.repository;


import android.content.Context;
import android.os.AsyncTask;

import com.juice.timetable.data.JuiceDatabase;
import com.juice.timetable.data.bean.StuInfo;
import com.juice.timetable.data.dao.StuInfoDao;

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
public class StuInfoRepository {
    private StuInfo stuInfo;
    private StuInfoDao stuInfoDao;

    public StuInfoRepository(Context context) {
        JuiceDatabase juiceDatabase = JuiceDatabase.getDatabase(context);
        stuInfoDao = juiceDatabase.getStuInfoDao();
    }

    public StuInfo getStuInfo(Void... voids) {
        StuInfo stuInfo = null;
        AsyncTask<Void, Void, StuInfo> asyncTask = new SelectAsyncTask(stuInfoDao).execute();
        try {
            stuInfo = asyncTask.get();
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return stuInfo;
    }


    public void insertStuInfo(StuInfo... stuInfo) {
        new InsertAsyncTask(stuInfoDao).execute(stuInfo);
    }

    public void deleteStuInfo(Void... Voids) {
        new DeleteAsyncTask(stuInfoDao).execute();
    }


    //插入
    static class InsertAsyncTask extends AsyncTask<StuInfo, Void, Void> {
        private StuInfoDao stuInfoDao;


        InsertAsyncTask(StuInfoDao stuInfoDao) {
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

        DeleteAsyncTask(StuInfoDao stuInfoDao) {
            this.stuInfoDao = stuInfoDao;
        }

        @Override
        protected Void doInBackground(Void... Voids) {
            stuInfoDao.deleteStuInfo();
            return null;
        }
    }

    //查询
    static class SelectAsyncTask extends AsyncTask<Void, Void, StuInfo> {
        private StuInfoDao stuInfoDao;

        SelectAsyncTask(StuInfoDao stuInfoDao) {
            this.stuInfoDao = stuInfoDao;
        }

        @Override
        protected StuInfo doInBackground(Void... Voids) {
            return stuInfoDao.getStuInfo();
        }
    }
}
