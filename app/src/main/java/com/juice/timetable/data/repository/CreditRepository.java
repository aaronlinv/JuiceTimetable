package com.juice.timetable.data.repository;

import android.content.Context;
import android.os.AsyncTask;

import androidx.lifecycle.LiveData;

import com.juice.timetable.data.JuiceDatabase;
import com.juice.timetable.data.bean.Credit;
import com.juice.timetable.data.dao.CreditsDao;

import java.util.List;
import java.util.concurrent.ExecutionException;

/**
 * <pre>
 *     author : wyx
 *     time   : 2022/3/15 12:31
 * </pre>
 */
public class CreditRepository {
    private CreditsDao creditsDao;

    public CreditRepository(Context context) {
        JuiceDatabase juiceDatabase = JuiceDatabase.getDatabase(context);
        creditsDao = juiceDatabase.getCreditsDao();
    }

    public void insertCredit(Credit... credits) {
        new InsertAsyncTask(creditsDao).execute(credits);
    }

    public void deleteALLCredit(Void... voids) {
        new DeleteAllAsyncTask(creditsDao).execute();
    }

    public LiveData<List<Credit>> getAllCreditLive(Void... voids) {
        LiveData<List<Credit>> creditListLiveData = null;
        AsyncTask<Void, Void, LiveData<List<Credit>>> execute = new SelectLiveDataAsyncTask(creditsDao).execute();
        try {
            creditListLiveData = execute.get();
        } catch (ExecutionException | InterruptedException e) {
            e.printStackTrace();
        }
        return creditListLiveData;
    }

    //查询(LiveData)AsyncTask
    static class SelectLiveDataAsyncTask extends AsyncTask<Void, Void, LiveData<List<Credit>>> {
        private CreditsDao creditsDao;

        public SelectLiveDataAsyncTask(CreditsDao creditsDao) {
            this.creditsDao = creditsDao;
        }

        @Override
        protected LiveData<List<Credit>> doInBackground(Void... voids) {
            return creditsDao.getALlCreditLive();
        }
    }

    //插入AsyncTask
    static class InsertAsyncTask extends AsyncTask<Credit, Void, Void> {
        private CreditsDao creditsDao;

        public InsertAsyncTask(CreditsDao creditsDao) {
            this.creditsDao = creditsDao;
        }

        @Override
        protected Void doInBackground(Credit... credits) {
            creditsDao.insertCredit(credits);
            return null;
        }
    }

    //清空全表AsyncTask
    static class DeleteAllAsyncTask extends AsyncTask<Void, Void, Void> {
        private CreditsDao creditsDao;

        public DeleteAllAsyncTask(CreditsDao creditsDao) {
            this.creditsDao = creditsDao;
        }

        @Override
        protected Void doInBackground(Void... voids) {
            creditsDao.deleteAllCredits();
            return null;
        }
    }
}
