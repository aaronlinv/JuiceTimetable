package com.juice.timetable.data.repository;

import android.content.Context;
import android.os.AsyncTask;

import androidx.lifecycle.LiveData;

import com.juice.timetable.data.JuiceDatabase;
import com.juice.timetable.data.bean.SynGrade;
import com.juice.timetable.data.bean.UniGrade;
import com.juice.timetable.data.dao.SynGradeDao;
import com.juice.timetable.data.dao.UniGradeDao;

import java.util.List;
import java.util.concurrent.ExecutionException;

/**
 * <pre>
 *     author : wyx
 *     time   : 2021/5/20 15:21
 * </pre>
 */
public class UniGradeRepository {
    private UniGradeDao uniGradeDao;
    private LiveData< List<UniGrade> > listUniGradeLive;

    public UniGradeRepository(Context context) {
        JuiceDatabase juiceDatabase = JuiceDatabase.getDatabase(context);
        uniGradeDao = juiceDatabase.getUniGradeDao();
        listUniGradeLive = uniGradeDao.getAllUniGradeLive();
    }

    public LiveData<List<UniGrade>> getAllUniGradeLive() {
        return listUniGradeLive;
    }

    //接口
    public void insertUniGrade(UniGrade... uniGrades) {
        new InsertAsyncTask(uniGradeDao).execute(uniGrades);
    }
    public void deleteAllUniGrade(Void... voids) {
        new DeleteAllAsyncTask(uniGradeDao).execute();
    }

    public LiveData<List<UniGrade>> getAllUniGradeLive(Void... Voids) {
        LiveData<List<UniGrade>> uniListLiveData = null;
        AsyncTask<Void, Void, LiveData<List<UniGrade>>> execute = new UniGradeRepository.SelectLiveDataAsyncTask(uniGradeDao).execute();
        try {
            uniListLiveData = execute.get();
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return uniListLiveData;
    }
    //插入AsyncTask
    static class InsertAsyncTask extends AsyncTask<UniGrade, Void, Void> {
        private UniGradeDao uniGradeDao;

        public InsertAsyncTask(UniGradeDao uniGradeDao) {
            this.uniGradeDao = uniGradeDao;
        }

        @Override
        protected Void doInBackground(UniGrade... uniGrades) {
            uniGradeDao.insertUniGrade(uniGrades);
            return null;
        }
    }

    //清空全表AsyncTask
    static class DeleteAllAsyncTask extends AsyncTask<Void, Void, Void> {
        private UniGradeDao uniGradeDao;


        public DeleteAllAsyncTask(UniGradeDao uniGradeDao) {
            this.uniGradeDao = uniGradeDao;
        }

        @Override
        protected Void doInBackground(Void... voids) {
            uniGradeDao.deleteAllUniGrade();
            return null;
        }
    }

    //查询(LiveData)AsyncTask
    static class SelectLiveDataAsyncTask extends AsyncTask<Void, Void, LiveData< List<UniGrade> >> {
        private UniGradeDao uniGradeDao;

        public SelectLiveDataAsyncTask(UniGradeDao uniGradeDao) {
            this.uniGradeDao = uniGradeDao;
        }

        @Override
        protected LiveData< List<UniGrade> > doInBackground(Void... voids) {
            return uniGradeDao.getAllUniGradeLive();

        }
    }
}
