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
        recyclerView.addItemDecoration(new UnsignedItemDecoration(requireContext()));
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
                classNoSignedItemDao.deleteNoSignedItem();
                List<ClassNoSignedItem> b = ParseClassNoSignedItem.getClassUnSigned();
                for (ClassNoSignedItem classNoSignedItem : b) {
                    classNoSignedItemDao.insertNoSignedItem(classNoSignedItem);
                }
                swipeRefreshLayout.setRefreshing(false);
            }
        });
        return root;
    }
    //JuiceDatabase juiceDatabase = JuiceDatabase.getDatabase(requireContext());
    //classNoSignedItemDao = juiceDatabase.getClassNoSignedItemDao();
        /*classNoSignedItem = classNoSignedItemDao.getNoSignedItem();
        swipeRefreshLayout = root.findViewById(R.id.swiperefreshlayout);
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                classNoSignedItemDao.deleteNoSignedItem();
                List<ClassNoSignedItem> b = ParseClassNoSignedItem.getClassUnSigned();
                for (ClassNoSignedItem classNoSignedItem : b) {
                    classNoSignedItemDao.insertNoSignedItem(classNoSignedItem);
                }
                classNoSignedItem = classNoSignedItemDao.getNoSignedItem();
                unsignedAdapter.setAllInfos(classNoSignedItem);
                unsignedAdapter.notifyDataSetChanged();
                swipeRefreshLayout.setRefreshing(false);
            }
        });
    }

    //@Override
    //public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
    //    super.onViewCreated(view, savedInstanceState);
        /*
        UserInfoUtils userInfoUtils = UserInfoUtils.getINSTANT(requireContext());
        final String id = userInfoUtils.getProperty("id");
        final String eduPasswd = userInfoUtils.getProperty("eduPasswd");
        final String leavePasswd = userInfoUtils.getProperty("leavePasswd");

        new Thread(new Runnable() {
            @Override
            public void run() {
                // 周课表
                String uri = "http://jwb.fdzcxy.com/kb/zkb_xs.asp";
                try {
                    EduInfo.getTimeTable(id, eduPasswd, uri, requireContext().getApplicationContext());
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

        }).start();//

        // 请假系统
        new Thread(new Runnable() {
            @Override
            public void run() {
                // 未签到列表
                // "http://mis.fdzcxy.com/index.php?n=stuwork-dormcheck-unsignin-cls&c=dormcheckclassunsignin"
                // 自己签到情况
                String uri = "http://mis.fdzcxy.com/index.php?n=stuwork-dormcheck-record-student&c=dormcheckrecordstudent";
                try {
                    LeaveInfo.getLeave(id, leavePasswd, uri);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

        }).start();
        */
    // }
}
