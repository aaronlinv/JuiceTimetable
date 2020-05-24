package com.juice.timetable.data.viewmodel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.juice.timetable.data.bean.ClassNoSignedItem;
import com.juice.timetable.data.repository.ClassNoSignedItemRepositroy;

import java.util.List;

/**
 * <pre>
 *     author : soreak
 *     e-mail : sorea1k@163.com
 *     time   : 2020/05/05
 *     desc   : nothing
 *     item   : juice
 *     version: 1.0
 * </pre>
 */
public class ClassNoSignedItemViewModel extends AndroidViewModel {
    private final ClassNoSignedItemRepositroy repository;

    public ClassNoSignedItemViewModel(@NonNull Application application) {
        super(application);
        repository = new ClassNoSignedItemRepositroy(application);
    }

    public void insertClassNoSignedItem(ClassNoSignedItem... classNoSignedItems) {
        repository.insertClassNoSignedItem(classNoSignedItems);
    }

    public void deleteClassNoSignedItem(Void... Voids) {
        repository.deleteClassNoSignedItem();
    }

    public LiveData<List<ClassNoSignedItem>> getClassNoSignedItemLive(Void... Voids) {
        return repository.getClassNoSignedItemLive();

    }
}
