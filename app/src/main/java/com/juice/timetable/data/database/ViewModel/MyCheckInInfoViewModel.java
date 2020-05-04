package com.juice.timetable.data.database.ViewModel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.juice.timetable.data.bean.MyCheckInInfo;
import com.juice.timetable.data.database.Dao.MyCheckInInfoDao;
import com.juice.timetable.data.database.JuiceDatabase;
import com.juice.timetable.data.database.Repository.MyCheckInInfoRepository;

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
public class MyCheckInInfoViewModel extends ViewModel {
    private LiveData<MyCheckInInfo> myCheckInInfoLive;
    private MyCheckInInfoDao myCheckInInfoDao;
    private MyCheckInInfoRepository myCheckInInfoRepository;

    public MyCheckInInfoViewModel(@NonNull Application application, MyCheckInInfoDao myCheckInInfoDao) {
        super();
        JuiceDatabase juiceDatabase = JuiceDatabase.getDatabase(application);
        myCheckInInfoDao = juiceDatabase.getMyCheckInInfoDao();
        myCheckInInfoLive = myCheckInInfoDao.getMyCheckInInfoLive();
        myCheckInInfoRepository = new MyCheckInInfoRepository(application);

    }

    public LiveData<MyCheckInInfo> getMyCheckInInfoLive() {
        return myCheckInInfoRepository.getMyCheckInInfoLive();
    }


    public void insertMyCheckInInfo(MyCheckInInfo... myCheckInInfo) {
        myCheckInInfoRepository.insertMyCheckInInfo(myCheckInInfo);
    }

    public void deleteMyCheckInInfo(Void... Voids) {
        myCheckInInfoRepository.deleteMyCheckInInfo();
    }
}
