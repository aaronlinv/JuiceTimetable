package com.juice.timetable.ui.unsigned;

import android.annotation.SuppressLint;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.juice.timetable.R;
import com.juice.timetable.app.Constant;
import com.juice.timetable.data.JuiceDatabase;
import com.juice.timetable.data.bean.ClassNoSignedItem;
import com.juice.timetable.data.bean.StuInfo;
import com.juice.timetable.data.dao.ClassNoSignedItemDao;
import com.juice.timetable.data.dao.StuInfoDao;
import com.juice.timetable.data.http.LeaveInfo;
import com.juice.timetable.data.parse.ParseClassNoSignedItem;
import com.juice.timetable.data.viewmodel.ClassNoSignedItemViewModel;

import java.util.List;

public class UnsignedFragment extends Fragment {
    private UnsignedAdapter unsignedAdapter;
    private ClassNoSignedItemDao classNoSignedItemDao;
    private SwipeRefreshLayout swipeRefreshLayout;
    private Handler mHandler;
    private Toolbar toolbar;
    private RecyclerView recyclerView;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        //寻找layout
        View root = inflater.inflate(R.layout.fragment_unsigned, container, false);
        findID(root);
        handler();
        // 隐藏Toolbar的下拉菜单按钮
        Menu menu = toolbar.getMenu();
        menu.setGroupVisible(0, false);
        //布局管理器
        recyclerView.setLayoutManager(new LinearLayoutManager(requireContext()));
        unsignedAdapter = new UnsignedAdapter();
        recyclerView.setAdapter(unsignedAdapter);
        recyclerView.addItemDecoration(new UnsignedItemDecoration(requireContext()));
        getTable();
        if (Constant.FIRST_IN) {
            // 刷新动画
            // 通过调用控件的引用调用post方法，在run方法中更新ui界面
            swipeRefreshLayout.post(new Runnable() {
                @Override
                public void run() {
                    swipeRefreshLayout.setRefreshing(true);
                }
            });
            fresh();
            // 设置首次登录为false
            Constant.FIRST_IN = false;
        }
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                fresh();
            }
        });
        return root;
    }

    private void findID(View root) {
        toolbar = requireActivity().findViewById(R.id.toolbar);
        recyclerView = root.findViewById(R.id.recyclerview);
        swipeRefreshLayout = root.findViewById(R.id.swiperefreshlayout);
    }

    private void getTable() {
        JuiceDatabase juiceDatabase = JuiceDatabase.getDatabase(requireContext());
        classNoSignedItemDao = juiceDatabase.getClassNoSignedItemDao();
        ClassNoSignedItemViewModel classNoSignedItemViewModel = new ViewModelProvider(requireActivity()).get(ClassNoSignedItemViewModel.class);
        LiveData<List<ClassNoSignedItem>> classNoSignedItemLive = classNoSignedItemViewModel.getClassNoSignedItemLive();
        classNoSignedItemLive.observe(requireActivity(), new Observer<List<ClassNoSignedItem>>() {
            @Override
            public void onChanged(List<ClassNoSignedItem> classNoSignedItems) {
                unsignedAdapter.setIfs(classNoSignedItems);
                unsignedAdapter.notifyDataSetChanged();
            }
        });
    }
    private static boolean isNetworkConnected(Context context) {
        if (context != null) {
            ConnectivityManager mConnectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
            assert mConnectivityManager != null;
            NetworkInfo mNetworkInfo = mConnectivityManager.getActiveNetworkInfo();
            return mNetworkInfo != null;
        }
        return false;
    }

    private void fresh() {
        new Thread(new Runnable() {
            @SuppressLint("ShowToast")
            @Override
            public void run() {
                // 模拟登录获取数据
                List<ClassNoSignedItem> unsignedList;
                Message message = new Message();
                if (isNetworkConnected(requireContext())) {
                    try {
                        // 从数据库获取个人用户
                        JuiceDatabase database = JuiceDatabase.getDatabase(requireContext());
                        StuInfoDao stuInfoDao = database.getStuInfoDao();
                        StuInfo stuInfo = stuInfoDao.getStuInfo();
//
                        String unsigned = LeaveInfo.getLeave(stuInfo.getStuID().toString(), stuInfo.getLeavePassword(), Constant.URI_UNSIGNED_LIST, requireContext());
                        unsignedList = ParseClassNoSignedItem.getClassUnSigned(unsigned);
                        // 删除数据库
                        classNoSignedItemDao.deleteNoSignedItem();

                        // 插入数据
                        for (ClassNoSignedItem classNoSignedItem : unsignedList) {
                            classNoSignedItemDao.insertNoSignedItem(classNoSignedItem);
                        }
                        message.obj = "success";
                        mHandler.sendMessage(message);

                    } catch (Exception e) {
                        message.obj = "passwd";
                        mHandler.sendMessage(message);
                        e.printStackTrace();
                    }
                } else {
                    message.obj = "network";
                    mHandler.sendMessage(message);
                }
            }
        }).start();
    }

    @SuppressLint("HandlerLeak")
    private void handler() {
        mHandler = new Handler() {
            @Override
            public void handleMessage(@NonNull Message msg) {
                super.handleMessage(msg);
                String msgStr = (String) msg.obj;
                if ("success".equals(msgStr)) {
                    Toast.makeText(requireActivity(), "未签名单更新成功", Toast.LENGTH_SHORT).show();
                } else if ("passwd".equals(msgStr)) {
                    Toast.makeText(requireActivity(), "未输入请假系统密码", Toast.LENGTH_SHORT).show();
                } else if ("network".equals(msgStr)) {
                    Toast.makeText(requireActivity(), "设备未联网", Toast.LENGTH_SHORT).show();
                }
                swipeRefreshLayout.setRefreshing(false);
            }
        };
    }
}
