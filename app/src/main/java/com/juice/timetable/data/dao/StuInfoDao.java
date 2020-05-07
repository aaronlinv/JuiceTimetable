package com.juice.timetable.data.dao;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.juice.timetable.data.bean.StuInfo;

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
public interface StuInfoDao {
    @Insert
    void insertStuInfo(StuInfo... stuInfos);

    @Query("Delete from StuInfo")
    void deleteStuInfo();

    @Update
    void updateWords(StuInfo... stuInfos);

    @Query("Select * from StuInfo")
    StuInfo getStuInfo();
}
