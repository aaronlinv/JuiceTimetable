package com.juice.timetable.data.ViewModel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.juice.timetable.data.Repository.OneWeekCourseRepository;
import com.juice.timetable.data.bean.OneWeekCourse;

import java.util.List;

/**
 * <pre>
 *     author : soreak
 *     e-mail : sorea1k@163.com
 *     time   : 2020/05/04
 *     desc   : nothing
 *     item   : juice
 *     version: 1.0
 * </pre>
 */
public class OneWeekCourseViewModel extends AndroidViewModel {

    private final OneWeekCourseRepository repository;

    public OneWeekCourseViewModel(@NonNull Application application) {
        super(application);
        repository = new OneWeekCourseRepository(application);
    }

    public LiveData<List<OneWeekCourse>> getOneWeekCourseLive() {
        return repository.getOneWeekCourseLive();
    }


}
