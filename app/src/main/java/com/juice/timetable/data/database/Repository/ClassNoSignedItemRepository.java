package com.juice.timetable.data.database.Repository;

import android.content.Context;
import android.os.AsyncTask;

import androidx.lifecycle.LiveData;

import com.juice.timetable.data.bean.ClassNoSignedItem;
import com.juice.timetable.data.database.Dao.ClassNoSignedItemDao;
import com.juice.timetable.data.database.JuiceDatabase;

import java.util.List;

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
public class ClassNoSignedItemRepository {
    private LiveData<List<ClassNoSignedItem>> classNoSignedItemLive;
    private ClassNoSignedItemDao classNoSignedItemDao;

    public ClassNoSignedItemRepository(Context context) {
        JuiceDatabase juiceDatabase = JuiceDatabase.getDatabase(context);
        classNoSignedItemDao = (ClassNoSignedItemDao) juiceDatabase.getClassNoSignedItemDao();
        classNoSignedItemLive = classNoSignedItemDao.getClassNoSignedItemLive();
    }

    public LiveData<List<ClassNoSignedItem>> getClassNoSignedItemLive() {
        return classNoSignedItemLive;
    }


    public void insertClassNoSignedItem(ClassNoSignedItem... classNoSignedItem) {
        new ClassNoSignedItemRepository.InsertAsyncTask(classNoSignedItemDao).execute(classNoSignedItem);
    }

    public void deleteClassNoSignedItem(Void... Voids) {
        new ClassNoSignedItemRepository.DeleteAsyncTask(classNoSignedItemDao).execute();
    }


    //插入
    static class InsertAsyncTask extends AsyncTask<ClassNoSignedItem, Void, Void> {
        private ClassNoSignedItemDao classNoSignedItemDao;


        public InsertAsyncTask(ClassNoSignedItemDao classNoSignedItemDao) {
            this.classNoSignedItemDao = classNoSignedItemDao;
        }

        @Override
        protected Void doInBackground(ClassNoSignedItem... classNoSignedItems) {
            classNoSignedItemDao.insertClassNoSignedItem(classNoSignedItems);
            return null;
        }
    }

    //删除
    static class DeleteAsyncTask extends AsyncTask<Void, Void, Void> {
        private ClassNoSignedItemDao classNoSignedItemDao;

        public DeleteAsyncTask(ClassNoSignedItemDao classNoSignedItemDao) {
            this.classNoSignedItemDao = classNoSignedItemDao;
        }

        @Override
        protected Void doInBackground(Void... Voids) {
            classNoSignedItemDao.deleteClassNoSignedItem();
            return null;
        }
    }
}
