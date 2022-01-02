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
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.juice.timetable.R;
import com.juice.timetable.app.Constant;
import com.juice.timetable.data.bean.SynGrade;
import com.juice.timetable.data.http.GradeInfo;
import com.juice.timetable.data.parse.ParseGrade;
import com.juice.timetable.data.viewmodel.SynGradeViewModel;

import org.jetbrains.annotations.NotNull;

import java.util.List;

import es.dmoral.toasty.Toasty;

//综合成绩的fragment
public class SynGradeFragment extends Fragment {
    private SynGradeViewModel synGradeViewModel;
    private RecyclerView synRecyclerView;
    private SynGradeRecycleViewAdapter synGradeRecycleViewAdapter;
    private List<SynGrade> synGradeArrayList;
    private SwipeRefreshLayout mSlRefresh;
    private Handler mHandler;

    @Override
    public void onViewCreated(@NonNull @NotNull View view, @Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        Refresh();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_grade_syn, container, false);
        findID(root);
        //初始化ViewModel
        synGradeViewModel = new ViewModelProvider(requireActivity()).get(SynGradeViewModel.class);
        //布局管理器
        synRecyclerView.setLayoutManager(new LinearLayoutManager(requireContext()));
        synGradeRecycleViewAdapter = new SynGradeRecycleViewAdapter();
        synRecyclerView.setAdapter(synGradeRecycleViewAdapter);

        getSynGradeData();

        return root;
    }

    //获取数据，然后插入数据库
    private void getSynGradeData() {
        //新建线程
        new Thread(new Runnable() {
            @SuppressLint("UseCompatLoadingForDrawables")
            @Override
            public void run() {
                Message message = new Message();
                try {
                    //获取成绩网页源码
                    String pagesource = GradeInfo.getGradeSource(Constant.URI_SYNGRADE);
                    if (pagesource.contains("成绩测评后才能查询成绩")) {
                        mSlRefresh.setRefreshing(false);
                        Looper.prepare();
                        Toasty.custom(requireActivity(), "成绩测评后才能查询成绩!",
                                getResources().getDrawable(R.drawable.grade, null),
                                getResources().getColor(R.color.green, null),
                                getResources().getColor(R.color.white, null),
                                LENGTH_SHORT, false, true).show();
                        Looper.loop();
                        return;
                    }
                    //利用爬虫获取成绩
                    synGradeArrayList = ParseGrade.parseSynGrade(pagesource);
                    //先清空表
                    synGradeViewModel.deleteAllSynGrade();
                    //再插入数据库
                    for (SynGrade synGrade : synGradeArrayList) {
                        synGradeViewModel.insertSynGrade(synGrade);
                    }
                    mSlRefresh.setRefreshing(false);
                    message.what = Constant.MSG_PARSESYN_SUCCESS;
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
        LiveData<List<SynGrade>> listSynGradeLive = synGradeViewModel.getAllSynGradeLive();
        listSynGradeLive.observe(requireActivity(), new Observer<List<SynGrade>>() {
            @SuppressLint("NotifyDataSetChanged")
            @Override
            public void onChanged(List<SynGrade> synGrades) {
                mHandler = new Handler(Looper.getMainLooper()) {
                    public void handleMessage(@NonNull Message msg) {
                        super.handleMessage(msg);
                        if (msg.what == Constant.MSG_PARSESYN_SUCCESS) {
                            synGradeRecycleViewAdapter.setSynGradeList(synGrades);
                            synGradeRecycleViewAdapter.notifyDataSetChanged();
                        }
                    }
                };
            }
        });
    }


    private void findID(View root) {
        synRecyclerView = root.findViewById(R.id.synRecyclerView);
        mSlRefresh = root.findViewById(R.id.syn_refresh);
    }

    private void Refresh() {
        mSlRefresh.setRefreshing(true);
        // 下拉刷新监听
        mSlRefresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                getSynGradeData();
            }
        });
    }
}