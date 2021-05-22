package com.juice.timetable.data.viewmodel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;

import com.juice.timetable.data.bean.SynGrade;
import com.juice.timetable.data.repository.SynGradeRepository;


/**
 * <pre>
 *     author : wyx
 *     time   : 2021/5/15 20:48
 * </pre>
 */
public class SynGradeViewModel extends AndroidViewModel {
    private final SynGradeRepository synGradeRepository;

    public SynGradeViewModel(@NonNull Application application) {
        super(application);
        synGradeRepository = new SynGradeRepository(application);

    }

    public void insertSynGrade(SynGrade... synGrades) {
        synGradeRepository.insertSynGrade(synGrades);
    }
    public void deleteSynGrade(Void... Voids) {
        synGradeRepository.deleteAllSynGrade();
    }

}
