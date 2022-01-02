package com.juice.timetable.data.viewmodel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;

import com.juice.timetable.data.bean.OneWeekCourse;
import com.juice.timetable.data.repository.OneWeekCourseRepository;

import java.util.ArrayList;
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

    public void insertOneWeekCourse(OneWeekCourse... courses) {
        repository.insertOneWeekCourse(courses);
    }

    public void deleteOneWeekCourse(Void... voids) {
        repository.deleteOneWeekCourse();
    }

    public List<OneWeekCourse> getOneWeekCourse() {
        return repository.getOneWeekCourse();
    }

    public List<Integer> getWeek() {
        return repository.getWeek();
    }

    public void deleteWeek(ArrayList<Integer>... arrayLists) {
        repository.deleteWeek(arrayLists[0]);
    }


}
