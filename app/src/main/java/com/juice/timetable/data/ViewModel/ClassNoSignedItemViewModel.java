package com.juice.timetable.data.ViewModel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.juice.timetable.data.Repository.ClassNoSignedItemRepositroy;
import com.juice.timetable.data.bean.ClassNoSignedItem;

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

    public LiveData<List<ClassNoSignedItem>> getClassNoSignedItemLive() {
        return repository.getClassNoSignedItemLive();
    }
}
