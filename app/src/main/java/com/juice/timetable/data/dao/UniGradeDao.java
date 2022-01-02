package com.juice.timetable.data.dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import com.juice.timetable.data.bean.UniGrade;

import java.util.List;

@Dao
public interface UniGradeDao {
    @Insert//增加
    void insertUniGrade(UniGrade... uniGrades);

    @Query("DELETE FROM UniGrade")//删除
    void deleteAllUniGrade();

    @Query("SELECT * FROM UniGrade")//查询全表
    LiveData<List<UniGrade>> getAllUniGradeLive();
}
