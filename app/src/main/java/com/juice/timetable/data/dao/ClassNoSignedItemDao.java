package com.juice.timetable.data.dao;

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
 *     time   : 2020/05/04
 *     desc   : nothing
 *     item   : juice
 *     version: 1.0
 * </pre>
 */
@Dao
public interface ClassNoSignedItemDao {
    @Insert
    void insertNoSignedItem(ClassNoSignedItem... classNoSignedItems);

    @Query("Delete From ClassNoSignedItem")
    void deleteNoSignedItem();

    @Query("Select * from ClassNoSignedItem order by CNo DESC")
    LiveData<List<ClassNoSignedItem>> getClassNoSignedItemLive();

    @Query("Select * from ClassNoSignedItem")
    List<ClassNoSignedItem> getClassNoSignedItem();
}
