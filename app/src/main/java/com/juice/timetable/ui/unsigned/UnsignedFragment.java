package com.juice.timetable.ui.unsigned;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.juice.timetable.R;
import com.juice.timetable.data.ClassNoSignedItemDao;
import com.juice.timetable.data.ClassNoSignedItemRepositroy;
import com.juice.timetable.data.JuiceDatabase;
import com.juice.timetable.data.JuiceDatabase_Impl;
import com.juice.timetable.data.bean.ClassNoSignedItem;

import java.util.List;

public class UnsignedFragment extends Fragment {
    private UnsignedViewModel unsignedViewModel;
    private ClassNoSignedItemRepositroy classNoSignedItemRepository;
    RecyclerView recyclerView;
    UnsignedAdapter unsignedAdapter;
    private ClassNoSignedItemDao classNoSignedItemDao;
    private List<ClassNoSignedItem> classNoSignedItem;
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_unsign, container, false);
        recyclerView = root.findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(requireContext()));
        unsignedAdapter = new UnsignedAdapter();
        recyclerView.setAdapter(unsignedAdapter);
        //unsignedViewModel = new ViewModelProvider(requireActivity()).get(UnsignedViewModel.class);
        //classNoSignedItem = unsignedViewModel.getClassNoSignedItemList();
        JuiceDatabase juiceDatabase = JuiceDatabase.getDatabase(requireContext());
        classNoSignedItemDao = juiceDatabase.getClassNoSignedItemDao();
        classNoSignedItem = classNoSignedItemDao.getNoSignedItem();
        unsignedAdapter.setAllInfos(classNoSignedItem);
        unsignedAdapter.notifyDataSetChanged();
        return root;
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
