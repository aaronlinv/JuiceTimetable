package com.juice.timetable.data.repository;

import android.content.Context;
import android.os.AsyncTask;

import androidx.lifecycle.LiveData;

import com.juice.timetable.data.JuiceDatabase;
import com.juice.timetable.data.bean.SynGrade;
import com.juice.timetable.data.dao.SynGradeDao;


import java.util.List;
import java.util.concurrent.ExecutionException;

/**
 * <pre>
 *     author : wyx
 *     time   : 2021/5/20 15:21
 * </pre>
 */
public class SynGradeRepository {
    private SynGradeDao synGradeDao;

    public SynGradeRepository(Context context) {
        JuiceDatabase juiceDatabase = JuiceDatabase.getDatabase(context);
        synGradeDao = juiceDatabase.getSynGradeDao();
    }

    //接口
    public void insertSynGrade(SynGrade... synGrades) {
        new InsertAsyncTask(synGradeDao).execute(synGrades);
    }

    public void deleteAllSynGrade(Void... voids) {
        new DeleteAllAsyncTask(synGradeDao).execute();
    }

    public LiveData<List<SynGrade>> getAllSynGradeLive(Void... Voids) {
        LiveData<List<SynGrade>> synListLiveData = null;
        AsyncTask<Void, Void, LiveData<List<SynGrade>>> execute = new SelectLiveDataAsyncTask(synGradeDao).execute();
        try {
            synListLiveData = execute.get();
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return synListLiveData;
    }

    //插入AsyncTask
    static class InsertAsyncTask extends AsyncTask<SynGrade, Void, Void> {
        private SynGradeDao synGradeDao;

        public InsertAsyncTask(SynGradeDao synGradeDao) {
            this.synGradeDao = synGradeDao;
        }

        @Override
        protected Void doInBackground(SynGrade... synGrades) {
            synGradeDao.insertSynGrade(synGrades);
            return null;
        }
    }

    //清空全表AsyncTask
    static class DeleteAllAsyncTask extends AsyncTask<Void, Void, Void> {
        private SynGradeDao synGradeDao;

        public DeleteAllAsyncTask(SynGradeDao synGradeDao) {
            this.synGradeDao = synGradeDao;
        }

        @Override
        protected Void doInBackground(Void... voids) {
            synGradeDao.deleteAllSynGrade();
            return null;
        }
    }

    //查询(LiveData)AsyncTask
    static class SelectLiveDataAsyncTask extends AsyncTask<Void, Void, LiveData<List<SynGrade>>> {
        private SynGradeDao synGradeDao;

        public SelectLiveDataAsyncTask(SynGradeDao synGradeDao) {
            this.synGradeDao = synGradeDao;
        }

        @Override
        protected LiveData<List<SynGrade>> doInBackground(Void... voids) {
            return synGradeDao.getAllSynGradeLive();

        }
    }
}
