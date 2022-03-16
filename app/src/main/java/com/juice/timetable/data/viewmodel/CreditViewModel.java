package com.juice.timetable.data.viewmodel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.juice.timetable.data.bean.Credit;
import com.juice.timetable.data.repository.CreditRepository;

import java.util.List;

/**
 * <pre>
 *     author : wyx
 *     time   : 2022/3/15 13:02
 * </pre>
 */
public class CreditViewModel extends AndroidViewModel {
    private final CreditRepository creditRepository;

    public CreditViewModel(@NonNull Application application) {
        super(application);
        creditRepository = new CreditRepository(application);
    }

    public LiveData<List<Credit>> getAllCreditLive() {
        return creditRepository.getAllCreditLive();
    }

    public void insertCredit(Credit... credits) {
        creditRepository.insertCredit(credits);
    }

    public void deleteAllCredit(Void... voids) {
        creditRepository.deleteALLCredit();
    }
}
