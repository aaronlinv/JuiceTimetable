package com.juice.timetable.data.viewmodel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;

import com.juice.timetable.data.bean.Course;
import com.juice.timetable.data.repository.AllWeekCourseRepository;

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

    public void insertAllWeekCourse(Course... courses) {
        repository.insertAllWeekCourse(courses);
    }

    public void deleteAllWeekCourse(Void... Voids) {
        repository.deleteAllWeekCourse();
    }

    public List<Course> getAllWeekCourse(Void... voids) {
        return repository.getAllWeekCourse();
    }

}
