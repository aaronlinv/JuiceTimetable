package com.juice.timetable.ui.unsigned;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.juice.timetable.R;
import com.juice.timetable.data.Dao.ClassNoSignedItemDao;
import com.juice.timetable.data.JuiceDatabase;
import com.juice.timetable.data.ViewModel.ClassNoSignedItemViewModel;
import com.juice.timetable.data.bean.ClassNoSignedItem;
import com.juice.timetable.data.http.LeaveInfo;
import com.juice.timetable.data.parse.ParseClassNoSignedItem;

import java.util.List;

public class UnsignedFragment extends Fragment {
    private UnsignedAdapter unsignedAdapter;
    private ClassNoSignedItemDao classNoSignedItemDao;
    private SwipeRefreshLayout swipeRefreshLayout;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_unsigned, container, false);
        RecyclerView recyclerView = root.findViewById(R.id.recyclerview);
        recyclerView.setLayoutManager(new LinearLayoutManager(requireContext()));
        unsignedAdapter = new UnsignedAdapter();
        recyclerView.setAdapter(unsignedAdapter);
        JuiceDatabase juiceDatabase = JuiceDatabase.getDatabase(requireContext());
        classNoSignedItemDao = juiceDatabase.getClassNoSignedItemDao();
        ClassNoSignedItemViewModel classNoSignedItemViewModel = new ViewModelProvider(requireActivity()).get(ClassNoSignedItemViewModel.class);
        LiveData<List<ClassNoSignedItem>> classNoSignedItemLive = classNoSignedItemViewModel.getClassNoSignedItemLive();
        classNoSignedItemLive.observe(requireActivity(), new Observer<List<ClassNoSignedItem>>() {
            @Override
            public void onChanged(List<ClassNoSignedItem> classNoSignedItems) {
                unsignedAdapter.setAllInfos(classNoSignedItems);
                unsignedAdapter.notifyDataSetChanged();
            }
        });
        swipeRefreshLayout = root.findViewById(R.id.swiperefreshlayout);
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        // 模拟登录获取数据
                        List<ClassNoSignedItem> unsignedList = null;
                        try {
                            String unsigned = LeaveInfo.getUnsignedList(requireContext());
                            unsignedList = ParseClassNoSignedItem.getClassUnSigned(unsigned);

                        } catch (Exception e) {
                            e.printStackTrace();
                        }

                        // 删除数据库
                        classNoSignedItemDao.deleteNoSignedItem();

                        // 插入数据
                        for (ClassNoSignedItem classNoSignedItem : unsignedList) {
                            classNoSignedItemDao.insertNoSignedItem(classNoSignedItem);
                        }
                    }
                }).start();
                swipeRefreshLayout.setRefreshing(false);
            }
        });
        return root;
    }
}
