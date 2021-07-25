package com.juice.timetable.ui.grade;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.juice.timetable.R;
import com.juice.timetable.app.Constant;
import com.juice.timetable.data.bean.UniGrade;
import com.juice.timetable.data.http.GradeInfo;
import com.juice.timetable.data.parse.ParseGrade;
import com.juice.timetable.data.viewmodel.UniGradeViewModel;

import java.util.List;

//统考成绩的fragment
public class UniGradeFragment extends Fragment {
    private UniGradeViewModel uniGradeViewModel;
    private UniGradeRecycleViewAdapter uniGradeRecycleViewAdapter;
    private RecyclerView uniRecyclerView;
    private SwipeRefreshLayout mSlRefresh;

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        Refresh();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_grade_uni, container, false);
        findID(root);
        //初始化ViewModel
        uniGradeViewModel = new ViewModelProvider(getActivity()).get(UniGradeViewModel.class);
        //布局管理器
        uniRecyclerView.setLayoutManager(new LinearLayoutManager(requireContext()));
        uniGradeRecycleViewAdapter = new UniGradeRecycleViewAdapter();
        uniRecyclerView.setAdapter(uniGradeRecycleViewAdapter);

        getUniGradeData();

        return root;
    }

    //获取数据，然后插入数据库
    private void getUniGradeData() {
        //新建线程
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    List<UniGrade> uniGradeArrayList;
                    //获取成绩网页源码
                    String pagesource = GradeInfo.getGradeSource(Constant.URI_UNIGRADE);
                    //利用爬虫获取成绩
                    uniGradeArrayList = ParseGrade.parseUniGrade(pagesource);
                    //先清空表
                    uniGradeViewModel.deleteAllUniGrade();
                    //再插入数据库
                    for (UniGrade uniGrade : uniGradeArrayList) {
                        uniGradeViewModel.insertUniGrade(uniGrade);
                    }
                    mSlRefresh.setRefreshing(false);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }) {
        }.start();
    }

    @Override
    public void onStart() {
        super.onStart();
        LiveData<List<UniGrade>> listUniGradeLive = uniGradeViewModel.getAllUniGradeLive();
        listUniGradeLive.observe(requireActivity(), new Observer<List<UniGrade>>() {
            @Override
            public void onChanged(List<UniGrade> uniGrades) {
                uniGradeRecycleViewAdapter.setUniGradeList(uniGrades);
                uniGradeRecycleViewAdapter.notifyDataSetChanged();
            }
        });
    }

    private void findID(View root) {
        uniRecyclerView = root.findViewById(R.id.uniRecyclerView);
        mSlRefresh = root.findViewById(R.id.uni_refresh);
    }

    private void Refresh() {

        // 下拉刷新监听
        mSlRefresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                getUniGradeData();
            }
        });
    }
}