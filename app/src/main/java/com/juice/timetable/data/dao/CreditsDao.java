package com.juice.timetable.data.dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import com.juice.timetable.data.bean.Credit;

import java.util.List;

@Dao
public interface CreditsDao {
    @Insert//增加
    void insertCredit(Credit... credits);

    @Query("DELETE FROM Credit")//删除
    void deleteAllCredits();

    @Query("SELECT * FROM Credit")//查询全表
    LiveData<List<Credit>> getALlCreditLive();
}
