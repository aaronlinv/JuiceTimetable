package com.juice.timetable.ui.unsigned;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.os.Looper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.juice.timetable.R;
import com.juice.timetable.data.JuiceDatabase;
import com.juice.timetable.data.bean.ClassNoSignedItem;
import com.juice.timetable.data.dao.ClassNoSignedItemDao;
import com.juice.timetable.data.http.LeaveInfo;
import com.juice.timetable.data.parse.ParseClassNoSignedItem;
import com.juice.timetable.data.viewmodel.ClassNoSignedItemViewModel;
import com.juice.timetable.databinding.FragmentUnsignedBinding;

import java.util.List;

public class UnsignedFragment extends Fragment {
    private UnsignedAdapter unsignedAdapter;
    private ClassNoSignedItemDao classNoSignedItemDao;
    private SwipeRefreshLayout swipeRefreshLayout;
    private FragmentUnsignedBinding fragmentUnsignedBinding;
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_unsigned, container, false);
        fragmentUnsignedBinding = FragmentUnsignedBinding.inflate(getLayoutInflater());
        RecyclerView recyclerView = root.findViewById(fragmentUnsignedBinding.recyclerview.getId());
        recyclerView.setLayoutManager(new LinearLayoutManager(requireContext()));
        unsignedAdapter = new UnsignedAdapter();
        recyclerView.setAdapter(unsignedAdapter);
        recyclerView.addItemDecoration(new UnsignedItemDecoration(requireContext()));
        /*GridLayoutManager gridLayoutManager = new GridLayoutManager(requireContext(),3);
        UnsignedItemDecoration divider = new UnsignedItemDecoration.Builder(requireContext())
                .setHorizontalSpan(R.dimen.activity_horizontal_margin)
                .setVerticalSpan(R.dimen.activity_vertical_margin)
                .setColorResource(R.color.dark_gray)
                .setShowLastLine(true)
                .build();
        recyclerView.addItemDecoration(divider);
        recyclerView.setLayoutManager(gridLayoutManager);

         */
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
        swipeRefreshLayout = root.findViewById(fragmentUnsignedBinding.swiperefreshlayout.getId());
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                new Thread(new Runnable() {
                    @SuppressLint("ShowToast")
                    @Override
                    public void run() {
                        // 模拟登录获取数据
                        List<ClassNoSignedItem> unsignedList;
                        try {
                            String unsigned = LeaveInfo.getUnsignedList(requireContext());
                            unsignedList = ParseClassNoSignedItem.getClassUnSigned(unsigned);
                            // 删除数据库
                            classNoSignedItemDao.deleteNoSignedItem();

                            // 插入数据
                            for (ClassNoSignedItem classNoSignedItem : unsignedList) {
                                classNoSignedItemDao.insertNoSignedItem(classNoSignedItem);
                            }
                            Looper.prepare();
                            Toast.makeText(requireContext(), "课表更新成功", Toast.LENGTH_SHORT).show();
                            Looper.loop();

                        } catch (Exception e) {
                            Looper.prepare();
                            Toast.makeText(requireContext(), "未输入请假系统密码或无网络", Toast.LENGTH_SHORT).show();
                            Looper.loop();
                            e.printStackTrace();
                        }
                    }
                }).start();
                swipeRefreshLayout.setRefreshing(false);
            }
        });
        return root;
    }
}
