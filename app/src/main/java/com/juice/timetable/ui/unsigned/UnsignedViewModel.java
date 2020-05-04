package com.juice.timetable.ui.unsigned;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.juice.timetable.data.ClassNoSignedItemDao;
import com.juice.timetable.data.ClassNoSignedItemRepositroy;
import com.juice.timetable.data.JuiceDatabase;
import com.juice.timetable.data.bean.ClassNoSignedItem;
import com.juice.timetable.data.parse.ParseClassNoSignedItem;

import java.util.List;

public class UnsignedViewModel extends AndroidViewModel {
    private ClassNoSignedItemRepositroy classNoSignedItemRepository;
    public UnsignedViewModel(@NonNull Application application) {
        super(application);
        classNoSignedItemRepository = new ClassNoSignedItemRepositroy(application);
    }

    List<ClassNoSignedItem> getClassNoSignedItemList() {
        return classNoSignedItemRepository.getClassNoSignedItem();
    }
}