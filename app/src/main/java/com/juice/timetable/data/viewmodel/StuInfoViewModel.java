package com.juice.timetable.data.viewmodel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;

import com.juice.timetable.data.bean.StuInfo;
import com.juice.timetable.data.repository.StuInfoRepository;

/**
 * <pre>
 *     author : soreak
 *     e-mail : sorea1k@163.com
 *     time   : 2020/05/22
 *     desc   : nothing
 *     item   : juice
 *     version: 1.0
 * </pre>
 */
public class StuInfoViewModel extends AndroidViewModel {
    private final StuInfoRepository repository;

    public StuInfoViewModel(@NonNull Application application) {
        super(application);
        repository = new StuInfoRepository(application);
    }

    public StuInfo selectStuInfo(Void... voids) {
        return repository.getStuInfo();
    }

    public void insertStuInfo(StuInfo... stuInfo) {
        repository.insertStuInfo(stuInfo);
    }

    public void deleteStuInfo(Void... Voids) {
        repository.deleteStuInfo();
    }

}
