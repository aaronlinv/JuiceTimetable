package com.juice.timetable.data.database.ViewModel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.juice.timetable.data.bean.StuInfo;
import com.juice.timetable.data.database.Dao.StuInfoDao;
import com.juice.timetable.data.database.JuiceDatabase;
import com.juice.timetable.data.database.Repository.StuInfoRepository;

/**
 * <pre>
 *     author : soreak
 *     e-mail : sorea1k@163.com
 *     time   : 2020/05/03
 *     desc   : nothing
 *     item   : juice
 *     version: 1.0
 * </pre>
 */
public class StuInfoViewModel extends ViewModel {
    private StuInfoDao stuInfoDao;
    private LiveData<StuInfo> stuInfoLive;
    private StuInfoRepository stuInfoRepository;

    public StuInfoViewModel(@NonNull Application application, StuInfoDao StuInfoDao) {
        super();
        JuiceDatabase juiceDatabase = JuiceDatabase.getDatabase(application);
        stuInfoDao = juiceDatabase.getStuInfoDao();
        stuInfoLive = stuInfoDao.getStuInfoLive();
        stuInfoRepository = new StuInfoRepository(application);

    }

    public LiveData<StuInfo> getStuInfoLive() {
        return stuInfoRepository.getStuInfoLive();
    }


    public void insertStuInfo(StuInfo... stuInfo) {
        stuInfoRepository.insertStuInfo(stuInfo);
    }


    public void deleteStuInfo(Void... Voids) {

        stuInfoRepository.deleteStuInfo();
    }
}
