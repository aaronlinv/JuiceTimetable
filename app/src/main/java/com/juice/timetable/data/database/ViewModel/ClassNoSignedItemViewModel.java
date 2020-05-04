package com.juice.timetable.data.database.ViewModel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.juice.timetable.data.bean.ClassNoSignedItem;
import com.juice.timetable.data.database.Dao.ClassNoSignedItemDao;
import com.juice.timetable.data.database.JuiceDatabase;
import com.juice.timetable.data.database.Repository.ClassNoSignedItemRepository;

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
public class ClassNoSignedItemViewModel extends ViewModel {
    private ClassNoSignedItemDao classNoSignedItemDao;
    private LiveData<List<ClassNoSignedItem>> classNoSignedItemLive;
    private ClassNoSignedItemRepository classNoSignedItemRepository;

    public ClassNoSignedItemViewModel(@NonNull Application application, ClassNoSignedItemDao classNoSignedItemDao) {
        super();
        JuiceDatabase juiceDatabase = JuiceDatabase.getDatabase(application);
        classNoSignedItemDao = (ClassNoSignedItemDao) juiceDatabase.getClassNoSignedItemDao();
        classNoSignedItemLive = classNoSignedItemDao.getClassNoSignedItemLive();
        classNoSignedItemRepository = new ClassNoSignedItemRepository(application);

    }

    public LiveData<List<ClassNoSignedItem>> getClassNoSignedItemLive() {
        return classNoSignedItemRepository.getClassNoSignedItemLive();
    }


    public void insertClassNoSignedItem(ClassNoSignedItem... classNoSignedItem) {
        classNoSignedItemRepository.insertClassNoSignedItem(classNoSignedItem);
    }


    public void deleteClassNoSignedItem(Void... Voids) {

        classNoSignedItemRepository.deleteClassNoSignedItem();
    }
}
