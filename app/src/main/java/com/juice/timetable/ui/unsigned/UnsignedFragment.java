package com.juice.timetable.ui.unsigned;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.Network;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.provider.Settings;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.google.android.material.snackbar.Snackbar;
import com.juice.timetable.R;
import com.juice.timetable.app.Constant;
import com.juice.timetable.data.bean.ClassNoSignedItem;
import com.juice.timetable.data.bean.StuInfo;
import com.juice.timetable.data.http.LeaveInfo;
import com.juice.timetable.data.parse.ParseClassNoSignedItem;
import com.juice.timetable.data.viewmodel.ClassNoSignedItemViewModel;
import com.juice.timetable.data.viewmodel.StuInfoViewModel;

import java.util.List;

import es.dmoral.toasty.Toasty;

import static es.dmoral.toasty.Toasty.LENGTH_SHORT;

public class UnsignedFragment extends Fragment {
    private UnsignedAdapter unsignedAdapter;
    private SwipeRefreshLayout swipeRefreshLayout;
    private Handler mHandler;
    private Toolbar toolbar;
    private RecyclerView recyclerView;
    private StuInfoViewModel mStuInfoViewModel;
    private ClassNoSignedItemViewModel classNoSignedItemViewModel;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        //寻找layout
        View root = inflater.inflate(R.layout.fragment_unsigned, container, false);
        findID(root);
        handler();
        // 隐藏 toolbar 的按钮 和星期下拉菜单按钮
        toolbar.findViewById(R.id.spinner).setVisibility(View.INVISIBLE);
        Menu menu = toolbar.getMenu();
        menu.setGroupVisible(0, false);
        //布局管理器
        recyclerView.setLayoutManager(new LinearLayoutManager(requireContext()));
        unsignedAdapter = new UnsignedAdapter();
        recyclerView.setAdapter(unsignedAdapter);
        recyclerView.addItemDecoration(new UnsignedItemDecoration(requireContext()));
        return root;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        getTable();
    }

    @Override
    public void onResume() {
        super.onResume();
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
    }

    private void findID(View root) {
        toolbar = requireActivity().findViewById(R.id.toolbar);
        recyclerView = root.findViewById(R.id.recyclerview);
        swipeRefreshLayout = root.findViewById(R.id.swipe_refresh_layout);
    }

    private void getTable() {
        classNoSignedItemViewModel = new ViewModelProvider(requireActivity()).get(ClassNoSignedItemViewModel.class);
        mStuInfoViewModel = new ViewModelProvider(requireActivity()).get(StuInfoViewModel.class);
        LiveData<List<ClassNoSignedItem>> classNoSignedItemLive = classNoSignedItemViewModel.getClassNoSignedItemLive();
        classNoSignedItemLive.observe(requireActivity(), new Observer<List<ClassNoSignedItem>>() {
            @Override
            public void onChanged(List<ClassNoSignedItem> classNoSignedItems) {
                unsignedAdapter.setIfs(classNoSignedItems);
                unsignedAdapter.notifyDataSetChanged();
            }
        });
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    private static boolean isNetworkConnected(Context context) {
        if (context != null) {
            ConnectivityManager mConnectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
            assert mConnectivityManager != null;
            Network network = mConnectivityManager.getActiveNetwork();
            return network != null;
        }
        return false;
    }

    private void fresh() {
        new Thread(new Runnable() {
            @RequiresApi(api = Build.VERSION_CODES.M)
            @SuppressLint("ShowToast")
            @Override
            public void run() {
                // 模拟登录获取数据
                List<ClassNoSignedItem> unsignedList;
                Message message = new Message();
                if (isNetworkConnected(requireContext())) {
                    try {
                        // 从数据库获取个人用户
                        StuInfo stuInfo = mStuInfoViewModel.selectStuInfo();
//
                        String unsigned = LeaveInfo.getLeave(stuInfo.getStuID().toString(), stuInfo.getLeavePassword(), Constant.URI_UNSIGNED_LIST, requireContext());
                        unsignedList = ParseClassNoSignedItem.getClassUnSigned(unsigned);
                        // 删除数据库
                        classNoSignedItemViewModel.deleteClassNoSignedItem();

                        // 插入数据
                        for (ClassNoSignedItem classNoSignedItem : unsignedList) {
                            classNoSignedItemViewModel.insertClassNoSignedItem(classNoSignedItem);
                        }
                        message.obj = "success";
                        mHandler.sendMessage(message);

                    } catch (Exception e) {
                        message.obj = "passwd";
                        mHandler.sendMessage(message);
                        classNoSignedItemViewModel.deleteClassNoSignedItem();
                        e.printStackTrace();
                    }
                } else {
                    message.obj = "network";
                    classNoSignedItemViewModel.deleteClassNoSignedItem();
                    mHandler.sendMessage(message);
                }
            }
        }).start();
    }

    private void network() {

    }

    @SuppressLint("HandlerLeak")
    private void handler() {
        mHandler = new Handler() {
            @Override
            public void handleMessage(@NonNull Message msg) {
                super.handleMessage(msg);
                String msgStr = (String) msg.obj;
                if (isAdded()) {
                    try {
                        if ("success".equals(msgStr)) {
                            Toasty.custom(requireActivity(), "未签名单更新成功", getResources().getDrawable(R.drawable.sign1), getResources().getColor(R.color.green), getResources().getColor(R.color.white), LENGTH_SHORT, true, true).show();
                            Toasty.Config.reset();
                        } else if ("passwd".equals(msgStr)) {
                            Snackbar.make(requireView(), "未输入请假系统密码", Snackbar.LENGTH_SHORT)
                                    .setAction("去加密码", new View.OnClickListener() {
                                        @Override
                                        public void onClick(View v) {
                                            Navigation.findNavController(requireActivity(), R.id.nav_host_fragment).navigate(R.id.nav_login);
                                        }
                                    }).show();
                        } else if ("network".equals(msgStr)) {
                            Snackbar.make(requireView(), "设备未联网", Snackbar.LENGTH_SHORT)
                                    .setAction("去联网", new View.OnClickListener() {
                                        @Override
                                        public void onClick(View v) {
                                            Intent intent = new Intent(Settings.ACTION_AIRPLANE_MODE_SETTINGS);
                                            startActivity(intent);
                                        }
                                    }).show();
                        }
                        swipeRefreshLayout.setRefreshing(false);
                    } catch (Exception e) {
                        Log.w("ERROR", "操作中断");
                        Toasty.custom(requireActivity(), "操作中断", getResources().getDrawable(R.drawable.ic_error), getResources().getColor(R.color.red), getResources().getColor(R.color.white), LENGTH_SHORT, true, true).show();
                        Toasty.Config.reset();
                        swipeRefreshLayout.setRefreshing(false);
                        e.printStackTrace();
                    }
                }
            }
        };
    }
}
