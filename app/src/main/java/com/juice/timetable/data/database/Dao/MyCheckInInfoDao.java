package com.juice.timetable.data.database.Dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import com.juice.timetable.data.bean.MyCheckInInfo;

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
public interface MyCheckInInfoDao {
    @Insert
    void insertMyCheckInInfo(MyCheckInInfo... myCheckInInfos);

    @Query("Delete From MyCheckInInfo")
    void deleteMyCheckInInfo();

    @Query("Select * From MyCheckInInfo")
    LiveData<MyCheckInInfo> getMyCheckInInfoLive();
}
