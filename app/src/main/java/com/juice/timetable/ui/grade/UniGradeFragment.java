package com.juice.timetable.ui.grade;

import static es.dmoral.toasty.Toasty.LENGTH_SHORT;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
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
import com.juice.timetable.app.Constant;
import com.juice.timetable.data.bean.UniGrade;
import com.juice.timetable.data.http.GradeInfo;
import com.juice.timetable.data.parse.ParseGrade;
import com.juice.timetable.data.viewmodel.UniGradeViewModel;

import java.util.List;

import es.dmoral.toasty.Toasty;

//统考成绩的fragment
public class UniGradeFragment extends Fragment {
    private UniGradeViewModel uniGradeViewModel;
    private UniGradeRecycleViewAdapter uniGradeRecycleViewAdapter;
    private RecyclerView uniRecyclerView;
    private SwipeRefreshLayout mSlRefresh;
    private Handler mHandler;
    private LiveData<List<UniGrade>> listUniGradeLive;
    private List<UniGrade> uniGradeArrayList;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_grade_uni, container, false);
        findID(root);
        uniGradeViewModel = new ViewModelProvider(requireActivity()).get(UniGradeViewModel.class);
        //布局管理器
        uniRecyclerView.setLayoutManager(new LinearLayoutManager(requireContext()));
        uniGradeRecycleViewAdapter = new UniGradeRecycleViewAdapter();
        uniRecyclerView.setAdapter(uniGradeRecycleViewAdapter);
        //获取数据
        getUniGradeData();
        // 下拉刷新
        Refresh();

        return root;
    }

    //获取数据，然后插入数据库
    private void getUniGradeData() {
        //新建线程
        new Thread(new Runnable() {
            @Override
            public void run() {
                Message message = new Message();
                try {
                    //获取成绩网页源码
                    String gradeSource = GradeInfo.getGradeSource(Constant.URI_UNIGRADE);
                    //利用爬虫获取成绩
                    uniGradeArrayList = ParseGrade.parseUniGrade(gradeSource);
                    //先清空表
                    uniGradeViewModel.deleteAllUniGrade();
                    //再插入数据库
                    for (UniGrade uniGrade : uniGradeArrayList) {
                        uniGradeViewModel.insertUniGrade(uniGrade);
                    }
                    mSlRefresh.setRefreshing(false);
                    message.what = Constant.MSG_PARSEUNI_SUCCESS;
                    mHandler.sendMessage(message);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }

    @Override
    public void onStart() {
        super.onStart();
        mHandler = new Handler(Looper.getMainLooper()) {
            @Override
            public void handleMessage(@NonNull Message msg) {
                super.handleMessage(msg);
                if (msg.what == Constant.MSG_PARSEUNI_SUCCESS) {
                    listUniGradeLive = uniGradeViewModel.getAllUniGradeLive();
                    listUniGradeLive.observe(requireActivity(), new Observer<List<UniGrade>>() {
                        @SuppressLint("NotifyDataSetChanged")
                        @Override
                        public void onChanged(List<UniGrade> uniGrades) {
                            uniGradeRecycleViewAdapter.setUniGradeList(uniGrades);
                            uniGradeRecycleViewAdapter.notifyDataSetChanged();
                        }
                    });
                }
            }
        };
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
                Toasty.custom(requireActivity(),
                        getResources().getString(R.string.refresh_success),
                        getResources().getDrawable(R.drawable.success, null),
                        getResources().getColor(R.color.green, null),
                        getResources().getColor(R.color.white, null),
                        LENGTH_SHORT, true, true).show();
                mSlRefresh.setRefreshing(false);
            }
        });
    }
}