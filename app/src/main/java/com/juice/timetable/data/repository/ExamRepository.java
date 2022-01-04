package com.juice.timetable.data.repository;

import android.content.Context;
import android.os.AsyncTask;

import androidx.lifecycle.LiveData;

import com.juice.timetable.data.JuiceDatabase;
import com.juice.timetable.data.bean.Exam;
import com.juice.timetable.data.dao.ExamDao;

import java.util.List;
import java.util.concurrent.ExecutionException;

/**
 * <pre>
 *     author : wyx
 *     time   : 2022/1/1 21:10
 * </pre>
 */
public class ExamRepository {
    private ExamDao examDao;

    public ExamRepository(Context context) {
        JuiceDatabase juiceDatabase = JuiceDatabase.getDatabase(context);
        examDao = juiceDatabase.getExamDao();
    }

    //接口
    public void insertExam(Exam... exams) {
        new InsertAsyncTask(examDao).execute(exams);
    }

    public void deleteAllExam(Void... voids) {
        new DeleteAllAsyncTask(examDao).execute();
    }

    public LiveData<List<Exam>> getAllExamLive(Void... voids) {
        LiveData<List<Exam>> examListLiveData = null;
        AsyncTask<Void, Void, LiveData<List<Exam>>> execute = new SelectLiveDataAsyncTask(examDao).execute();
        try {
            examListLiveData = execute.get();
        } catch (ExecutionException | InterruptedException e) {
            e.printStackTrace();
        }
        return examListLiveData;

    }

    public LiveData<List<Exam>> findNameWithPattern(String pattern) {
        LiveData<List<Exam>> examListLiveData = null;
        AsyncTask<String, Void, LiveData<List<Exam>>> execute = new FindLiveDataAsyncTask(examDao).execute(pattern);
        try {
            examListLiveData = execute.get();
        } catch (ExecutionException | InterruptedException e) {
            e.printStackTrace();
        }
        return examListLiveData;
    }


    //插入AsyncTask
    static class InsertAsyncTask extends AsyncTask<Exam, Void, Void> {
        private ExamDao examDao;

        public InsertAsyncTask(ExamDao examDao) {
            this.examDao = examDao;
        }

        @Override
        protected Void doInBackground(Exam... exams) {
            examDao.insertExam(exams);
            return null;
        }
    }

    //清空全表AsyncTask
    static class DeleteAllAsyncTask extends AsyncTask<Void, Void, Void> {
        private ExamDao examDao;

        public DeleteAllAsyncTask(ExamDao examDao) {
            this.examDao = examDao;
        }

        @Override
        protected Void doInBackground(Void... voids) {
            examDao.deleteAllExam();
            return null;
        }
    }

    //查询(LiveData)AsyncTask
    static class SelectLiveDataAsyncTask extends AsyncTask<Void, Void, LiveData<List<Exam>>> {
        private ExamDao examDao;

        public SelectLiveDataAsyncTask(ExamDao examDao) {
            this.examDao = examDao;
        }

        @Override
        protected LiveData<List<Exam>> doInBackground(Void... voids) {
            return examDao.getAllExamLive();
        }
    }

    //模糊匹配(LiveData)AsyncTask
    static class FindLiveDataAsyncTask extends AsyncTask<String, Void, LiveData<List<Exam>>> {
        private ExamDao examDao;

        public FindLiveDataAsyncTask(ExamDao examDao) {
            this.examDao = examDao;
        }

        @Override
        protected LiveData<List<Exam>> doInBackground(String... strings) {
            return examDao.findNameWithPattern(strings[0]);
        }
    }
}
