package com.juice.timetable.data.repository;


import android.content.Context;
import android.os.AsyncTask;

import com.juice.timetable.data.JuiceDatabase;
import com.juice.timetable.data.bean.StuInfo;
import com.juice.timetable.data.dao.StuInfoDao;
import com.juice.timetable.utils.AesCryptUtil;
import com.juice.timetable.utils.LogUtils;

import java.security.GeneralSecurityException;

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
public class StuInfoRepository {
    private StuInfoDao stuInfoDao;
    // 测试环境密匙
    private String secret = "G3YnGIaJ@A%4e945M2CxhIHwU*3@BC*o";

    public StuInfoRepository(Context context) {
        JuiceDatabase juiceDatabase = JuiceDatabase.getDatabase(context);
        stuInfoDao = juiceDatabase.getStuInfoDao();
    }

    public StuInfo getStuInfo(Void... voids) {
        StuInfo stuInfo = null;
        AsyncTask<Void, Void, StuInfo> asyncTask = new SelectAsyncTask(stuInfoDao).execute();
        try {
            stuInfo = asyncTask.get();
            // 解密
            LogUtils.getInstance().d("StuInfoRepository中解密前 -- > " + stuInfo);
            String eduPassword = null;
            String leavePassword = null;
            if (stuInfo.getEduPassword() != null) {
                eduPassword = AesCryptUtil.decrypt(secret, stuInfo.getEduPassword());
            }
            if (stuInfo.getLeavePassword() != null) {
                leavePassword = AesCryptUtil.decrypt(secret, stuInfo.getLeavePassword());
            }

            stuInfo.setEduPassword(eduPassword);
            stuInfo.setLeavePassword(leavePassword);
            LogUtils.getInstance().d("StuInfoRepository中进行解密 -- > " + stuInfo);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return stuInfo;
    }


    public void insertStuInfo(StuInfo... stuInfo) {
        // 加密
        String eduPassword = null;
        String leavePassword = null;
        StuInfo stu = stuInfo[0];
        LogUtils.getInstance().d("StuInfoRepository中加密前 -- > " + stu);

        try {
            if (stu.getEduPassword() != null) {
                eduPassword = AesCryptUtil.encrypt(secret, stu.getEduPassword());
            }
            if (stu.getLeavePassword() != null) {
                leavePassword = AesCryptUtil.encrypt(secret, stu.getLeavePassword());
            }
        } catch (GeneralSecurityException e) {
            e.printStackTrace();
        }
        stu.setEduPassword(eduPassword);
        stu.setLeavePassword(leavePassword);

        LogUtils.getInstance().d("StuInfoRepository中进行加密 -- > " + stu);
        new InsertAsyncTask(stuInfoDao).execute(stu);
    }

    public void deleteStuInfo(Void... Voids) {
        new DeleteAsyncTask(stuInfoDao).execute();
    }


    //插入
    static class InsertAsyncTask extends AsyncTask<StuInfo, Void, Void> {
        private StuInfoDao stuInfoDao;


        InsertAsyncTask(StuInfoDao stuInfoDao) {
            this.stuInfoDao = stuInfoDao;
        }

        @Override
        protected Void doInBackground(StuInfo... stuInfo) {
            stuInfoDao.insertStuInfo(stuInfo);
            return null;
        }
    }

    //删除
    static class DeleteAsyncTask extends AsyncTask<Void, Void, Void> {
        private StuInfoDao stuInfoDao;

        DeleteAsyncTask(StuInfoDao stuInfoDao) {
            this.stuInfoDao = stuInfoDao;
        }

        @Override
        protected Void doInBackground(Void... Voids) {
            stuInfoDao.deleteStuInfo();
            return null;
        }
    }

    //查询
    static class SelectAsyncTask extends AsyncTask<Void, Void, StuInfo> {
        private StuInfoDao stuInfoDao;

        SelectAsyncTask(StuInfoDao stuInfoDao) {
            this.stuInfoDao = stuInfoDao;
        }

        @Override
        protected StuInfo doInBackground(Void... Voids) {
            return stuInfoDao.getStuInfo();
        }
    }
}
