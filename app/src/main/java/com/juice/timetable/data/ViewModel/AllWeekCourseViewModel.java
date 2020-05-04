package com.juice.timetable.data.ViewModel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.juice.timetable.data.Repository.AllWeekCourseRepository;
import com.juice.timetable.data.bean.Course;

import java.util.List;

/**
 * <pre>
 *     author : soreak
 *     e-mail : sorea1k@163.com
 *     time   : 2020/05/05
 *     desc   : nothing
 *     item   : juice
 *     version: 1.0
 * </pre>
 */
public class AllWeekCourseViewModel extends AndroidViewModel {
    private final AllWeekCourseRepository repository;

    public AllWeekCourseViewModel(@NonNull Application application) {
        super(application);
        repository = new AllWeekCourseRepository(application);
    }

    public LiveData<List<Course>> getAllWeekCourseLive() {
        return repository.getAllWeekCourseLive();
    }
}
