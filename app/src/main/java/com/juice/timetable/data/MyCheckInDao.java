package com.juice.timetable.data;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import com.juice.timetable.data.bean.MyCheckIn;

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
public interface MyCheckInDao {
    @Insert
    void insertMyCheckIn(MyCheckIn... myCheckIns);

    @Query("Delete from MyCheckIn")
    void deleteMyCheckIn();

    @Query("Select * from MyCheckIn")
    MyCheckIn getMyCheckIn();
}
