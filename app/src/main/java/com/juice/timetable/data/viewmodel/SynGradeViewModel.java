package com.juice.timetable.data.viewmodel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.juice.timetable.data.bean.SynGrade;
import com.juice.timetable.data.repository.SynGradeRepository;

import java.util.List;


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
    public LiveData< List<SynGrade> > getAllSynGradeLive() {
        return synGradeRepository.getAllSynGradeLive();
    }

    public void insertSynGrade(SynGrade... synGrades) {
        synGradeRepository.insertSynGrade(synGrades);
    }
    public void deleteAllSynGrade(Void... Voids) {
        synGradeRepository.deleteAllSynGrade();
    }


}
