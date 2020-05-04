package com.juice.timetable.data.database.ViewModel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.juice.timetable.data.bean.AllWeekCourse;
import com.juice.timetable.data.database.Dao.AllWeekCourseDao;
import com.juice.timetable.data.database.JuiceDatabase;
import com.juice.timetable.data.database.Repository.AllWeekCourseRepository;

import java.util.List;

/**
 * <pre>
 *     author : soreak
 *     e-mail : sorea1k@163.com
 *     time   : 2020/05/03
 *     desc   : nothing
 *     item   : juice
 *     version: 1.0
 * </pre>
 */
public class AllWeekCourseViewModel extends ViewModel {
    private AllWeekCourseDao allWeekCourseDao;
    private LiveData<List<AllWeekCourse>> allWeekCourseLive;
    private AllWeekCourseRepository allWeekCourseRepository;

    public AllWeekCourseViewModel(@NonNull Application application) {
        super();
        JuiceDatabase juiceDatabase = JuiceDatabase.getDatabase(application);
        allWeekCourseDao = juiceDatabase.getAllCourseDao();
        allWeekCourseLive = allWeekCourseDao.getAllWeekCourseLive();
        allWeekCourseRepository = new AllWeekCourseRepository(application);
    }

    public LiveData<List<AllWeekCourse>> getAllWeekCourseLive() {
        return allWeekCourseRepository.getAllWeekCourseLive();
    }

    //对应存储数据库AllWeekCourse表的接口
    public void insertAllWeekCourse(AllWeekCourse... allWeekCourses) {
        allWeekCourseRepository.insertAllWeekCourse(allWeekCourses);
    }

    //删除数据库OneWeekCourse表的接口
    public void deleteAllWeekCourse(Void... Voids) {

        allWeekCourseRepository.deleteAllWeekCourse();
    }
}
