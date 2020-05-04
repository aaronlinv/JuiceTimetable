package com.juice.timetable.data.Repository;

import android.content.Context;
import android.os.AsyncTask;

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
    private List<ClassNoSignedItem> classNoSignedItems;
    private ClassNoSignedItemDao classNoSignedItemDao;

    public ClassNoSignedItemRepositroy(Context context) {
        JuiceDatabase juiceDatabase = JuiceDatabase.getDatabase(context);
        classNoSignedItemDao = juiceDatabase.getClassNoSignedItemDao();
        classNoSignedItems = classNoSignedItemDao.getNoSignedItem();
    }

    public List<ClassNoSignedItem> getClassNoSignedItem() {
        return classNoSignedItems;
    }


    public void insertClassNoSignedItem(ClassNoSignedItem... classNoSignedItems) {
        new InsertAsyncTask(classNoSignedItemDao).execute(classNoSignedItems);
    }

    public void deleteClassNoSignedItem(Void... Voids) {
        new DeleteAsyncTask(classNoSignedItemDao).execute();
    }


    //插入
    static class InsertAsyncTask extends AsyncTask<ClassNoSignedItem, Void, Void> {
        private ClassNoSignedItemDao classNoSignedItemDao;


        public InsertAsyncTask(ClassNoSignedItemDao classNoSignedItemDao) {
            this.classNoSignedItemDao = classNoSignedItemDao;
        }

        @Override
        protected Void doInBackground(ClassNoSignedItem... classNoSignedItems) {
            classNoSignedItemDao.insertNoSignedItem(classNoSignedItems);
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
            classNoSignedItemDao.deleteNoSignedItem();
            return null;
        }
    }
}
