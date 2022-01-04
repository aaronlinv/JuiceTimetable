package com.juice.timetable.data.viewmodel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.juice.timetable.data.bean.Exam;
import com.juice.timetable.data.repository.ExamRepository;

import java.util.List;

/**
 * <pre>
 *     author : wyx
 *     time   : 2022/1/1 21:09
 * </pre>
 */
public class ExamViewModel extends AndroidViewModel{
    private final ExamRepository examRepository;

    public ExamViewModel(@NonNull Application application) {
        super(application);
        examRepository = new ExamRepository(application);

    }

    public LiveData<List<Exam>> getAllExamLive(){
        return examRepository.getAllExamLive();
    }

    public LiveData<List<Exam>> findNameWithPattern(String pattern){
        return examRepository.findNameWithPattern("%" + pattern + "%");
    }

    public void insertExam(Exam... exams){
        examRepository.insertExam(exams);
    }
    public void deleteAllExam(Void... voids){
        examRepository.deleteAllExam();
    }

}
