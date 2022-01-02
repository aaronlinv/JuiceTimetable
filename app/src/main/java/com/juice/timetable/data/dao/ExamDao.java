package com.juice.timetable.data.dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import com.juice.timetable.data.bean.Exam;

import java.util.List;

@Dao
public interface ExamDao {
    @Insert//增加
    void insertExam(Exam... exams);

    @Query("DELETE FROM Exam")//删除
    void deleteAllExam();

    @Query("SELECT * FROM Exam")//查询全表
    LiveData<List<Exam>> getAllExamLive();
}
