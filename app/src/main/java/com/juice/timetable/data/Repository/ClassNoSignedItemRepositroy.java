package com.juice.timetable.data.Repository;

import android.content.Context;
import android.os.AsyncTask;

import androidx.lifecycle.LiveData;

import com.juice.timetable.data.Dao.ClassNoSignedItemDao;
import com.juice.timetable.data.JuiceDatabase;
import com.juice.timetable.data.bean.ClassNoSignedItem;

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
public class ClassNoSignedItemRepositroy {
    private LiveData<List<ClassNoSignedItem>> classNoSignedItemsLive;
    private ClassNoSignedItemDao classNoSignedItemDao;

    public ClassNoSignedItemRepositroy(Context context) {
        JuiceDatabase juiceDatabase = JuiceDatabase.getDatabase(context);
        classNoSignedItemDao = juiceDatabase.getClassNoSignedItemDao();
        classNoSignedItemsLive = classNoSignedItemDao.getClassNoSignedItemLive();
    }

    public LiveData<List<ClassNoSignedItem>> getClassNoSignedItemLive() {
        return classNoSignedItemsLive;
    }


    public void insertClassNoSignedItem(ClassNoSignedItem... classNoSignedItems) {
        new InsertAsyncTask(classNoSignedItemDao).execute(classNoSignedItems);
    }

    public void deleteClassNoSignedItem(Void... Voids) {
        new DeleteAsyncTask(classNoSignedItemDao).execute();
    }

    public void selectClassNoSignedItem(Void... Voids) {
        new SelectAsyncTask(classNoSignedItemDao).execute();
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

    //查询
    static class SelectAsyncTask extends AsyncTask<Void, Void, Void> {
        private ClassNoSignedItemDao classNoSignedItemDao;


        SelectAsyncTask(ClassNoSignedItemDao classNoSignedItemDao) {
            this.classNoSignedItemDao = classNoSignedItemDao;
        }

        @Override
        protected Void doInBackground(Void... voids) {
            classNoSignedItemDao.getClassNoSignedItemLive();
            return null;
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
