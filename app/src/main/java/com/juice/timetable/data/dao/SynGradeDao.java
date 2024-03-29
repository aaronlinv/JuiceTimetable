package com.juice.timetable.data.dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import com.juice.timetable.data.bean.SynGrade;

import java.util.List;

@Dao
public interface SynGradeDao {
    @Insert//增加
    void insertSynGrade(SynGrade... synGrades);

    @Query("DELETE FROM SynGrade")//删除
    void deleteAllSynGrade();

    @Query("SELECT * FROM SynGrade")//查询全表
    LiveData<List<SynGrade>> getAllSynGradeLive();

    @Query("SELECT * FROM SynGrade WHERE couName LIKE :pattern")
    LiveData<List<SynGrade>> findNameWithPattern(String pattern);
}
