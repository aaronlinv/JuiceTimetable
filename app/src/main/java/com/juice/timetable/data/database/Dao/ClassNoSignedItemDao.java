package com.juice.timetable.data.database.Dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import com.juice.timetable.data.bean.ClassNoSignedItem;

import java.util.List;

/**
 * <pre>
 *     author : soreak
 *     e-mail : sorea1k@163.com
 *     time   : 2020/05/02
 *     desc   : nothing
 *     item   : juice
 *     version: 1.0
 * </pre>
 */
@Dao
public interface ClassNoSignedItemDao {
    @Insert
    void insertClassNoSignedItem(ClassNoSignedItem... Items);

    @Query("Delete From ClassNoSignedItem")
    void deleteClassNoSignedItem();

    @Query("Select * From ClassNoSignedItem ORDER BY Sno Desc")
    LiveData<List<ClassNoSignedItem>> getClassNoSignedItemLive();
}
