package com.juice.timetable.data.viewmodel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;

import com.juice.timetable.data.bean.UniGrade;
import com.juice.timetable.data.repository.UniGradeRepository;

/**
 * <pre>
 *     author : wyx
 *     time   : 2021/5/22 14:09
 * </pre>
 */
public class UniGradeViewModel extends AndroidViewModel {
    private final UniGradeRepository uniGradeRepository;

    public UniGradeViewModel(@NonNull Application application) {
        super(application);
        uniGradeRepository = new UniGradeRepository(application);
    }
    public void insertUniGrade(UniGrade... uniGrades) {
        uniGradeRepository.insertUniGrade(uniGrades);
    }
    public void deleteUniGrade(Void... Voids) {
        uniGradeRepository.deleteAllUniGrade();
    }
}
