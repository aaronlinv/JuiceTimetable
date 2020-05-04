package com.juice.timetable.data.database.ViewModel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.juice.timetable.data.bean.OneWeekCourse;
import com.juice.timetable.data.database.Dao.OneWeekCourseDao;
import com.juice.timetable.data.database.JuiceDatabase;
import com.juice.timetable.data.database.Repository.OneWeekCourseRepository;

import java.util.List;

/**
 * <pre>
 *     author : soreak
 *     e-mail : sorea1k@163.com
 *     time   : 2020/05/02
 *     desc   : nothing
 *     item   : juice
 *     version: 1.0
 * </pre>
 */
public class OneWeekCourseViewModel extends AndroidViewModel {
    private OneWeekCourseDao oneWeekCourseDao;
    private LiveData<List<OneWeekCourse>> oneWeekCourseLive;
    private OneWeekCourseRepository oneWeekCourseRepository;

    public OneWeekCourseViewModel(@NonNull Application application, OneWeekCourseDao oneWeekCourseDao) {
        super(application);
        JuiceDatabase juiceDatabase = JuiceDatabase.getDatabase(application);
        oneWeekCourseDao = juiceDatabase.getOneCourseDao();
        oneWeekCourseLive = oneWeekCourseDao.getOneWeekCourseLive();
        oneWeekCourseRepository = new OneWeekCourseRepository(application);

    }

    public LiveData<List<OneWeekCourse>> getOneWeekCourseLive() {
        return oneWeekCourseRepository.getOneWeekCourseLive();
    }

    //对应存储数据库OneWeekCourse表的接口
    public void insertOneWeekCourse(OneWeekCourse... oneWeekCourses) {
        oneWeekCourseRepository.insertOneWeekCourse(oneWeekCourses);
    }

    //删除数据库OneWeekCourse表的接口
    public void deleteOneWeekCourse(Void... Voids) {

        oneWeekCourseRepository.deleteOneWeekCourse();
    }


}
