package com.juice.timetable.data.viewmodel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;

import com.juice.timetable.data.bean.MyCheckIn;
import com.juice.timetable.data.repository.MyCheckInRepository;

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
public class MyCheckInViewModel extends AndroidViewModel {
    private final MyCheckInRepository repository;

    public MyCheckInViewModel(@NonNull Application application) {
        super(application);
        repository = new MyCheckInRepository(application);
    }

    public void insertMyCheckIn(MyCheckIn... myCheckIns) {
        repository.insertMyCheckIn(myCheckIns);
    }

    public void deleteMyCheckIn(Void... Voids) {
        repository.deleteMyCheckIn();
    }

    public MyCheckIn getMyCheckIn(Void... voids) {
        return repository.getMyCheckIn();
    }
}
