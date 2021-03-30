package com.juice.timetable.data.repository;

import android.content.Context;
import android.os.AsyncTask;

import androidx.lifecycle.LiveData;

import com.juice.timetable.data.JuiceDatabase;
import com.juice.timetable.data.bean.ClassNoSignedItem;
import com.juice.timetable.data.dao.ClassNoSignedItemDao;

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
public class ClassNoSignedItemRepositroy {
    private ClassNoSignedItemDao classNoSignedItemDao;

    public ClassNoSignedItemRepositroy(Context context) {
        JuiceDatabase juiceDatabase = JuiceDatabase.getDatabase(context);
        classNoSignedItemDao = juiceDatabase.getClassNoSignedItemDao();
    }



    public void insertClassNoSignedItem(ClassNoSignedItem... classNoSignedItems) {
        new InsertAsyncTask(classNoSignedItemDao).execute(classNoSignedItems);
    }

    public void deleteClassNoSignedItem(Void... Voids) {
        new DeleteAsyncTask(classNoSignedItemDao).execute();
    }

    public LiveData<List<ClassNoSignedItem>> getClassNoSignedItemLive(Void... Voids) {
        LiveData<List<ClassNoSignedItem>> cnsi = null;
        AsyncTask<Void, Void, LiveData<List<ClassNoSignedItem>>> execute = new SelectLiveDataAsyncTask(classNoSignedItemDao).execute();
        try {
            cnsi = execute.get();
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return cnsi;
    }

    public List<ClassNoSignedItem> getClassNoSignedItem(Void... Voids) {
        List<ClassNoSignedItem> cnsi = null;
        AsyncTask<Void, Void, List<ClassNoSignedItem>> execute = new SelectListAsyncTask(classNoSignedItemDao).execute();
        try {
            cnsi = execute.get();
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return cnsi;
    }

    //插入
    static class InsertAsyncTask extends AsyncTask<ClassNoSignedItem, Void, Void> {
        private ClassNoSignedItemDao classNoSignedItemDao;


        InsertAsyncTask(ClassNoSignedItemDao classNoSignedItemDao) {
            this.classNoSignedItemDao = classNoSignedItemDao;
        }

        @Override
        protected Void doInBackground(ClassNoSignedItem... classNoSignedItems) {
            classNoSignedItemDao.insertNoSignedItem(classNoSignedItems);
            return null;
        }
    }

    //查询(LiveData)
    static class SelectLiveDataAsyncTask extends AsyncTask<Void, Void, LiveData<List<ClassNoSignedItem>>> {
        private ClassNoSignedItemDao classNoSignedItemDao;


        SelectLiveDataAsyncTask(ClassNoSignedItemDao classNoSignedItemDao) {
            this.classNoSignedItemDao = classNoSignedItemDao;
        }

        @Override
        protected LiveData<List<ClassNoSignedItem>> doInBackground(Void... voids) {
            return classNoSignedItemDao.getClassNoSignedItemLive();

        }
    }

    //查询(List)
    static class SelectListAsyncTask extends AsyncTask<Void, Void, List<ClassNoSignedItem>> {
        private ClassNoSignedItemDao classNoSignedItemDao;


        SelectListAsyncTask(ClassNoSignedItemDao classNoSignedItemDao) {
            this.classNoSignedItemDao = classNoSignedItemDao;
        }

        @Override
        protected List<ClassNoSignedItem> doInBackground(Void... voids) {
            return classNoSignedItemDao.getClassNoSignedItem();

        }
    }

    //删除
    static class DeleteAsyncTask extends AsyncTask<Void, Void, Void> {
        private ClassNoSignedItemDao classNoSignedItemDao;

        DeleteAsyncTask(ClassNoSignedItemDao classNoSignedItemDao) {
            this.classNoSignedItemDao = classNoSignedItemDao;
        }

        @Override
        protected Void doInBackground(Void... Voids) {
            classNoSignedItemDao.deleteNoSignedItem();
            return null;
        }
    }
}
