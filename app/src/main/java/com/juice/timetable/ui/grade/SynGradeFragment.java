package com.juice.timetable.ui.grade;

import android.os.Bundle;
import android.os.Looper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.juice.timetable.R;
import com.juice.timetable.app.Constant;
import com.juice.timetable.data.bean.SynGrade;
import com.juice.timetable.data.http.GradeInfo;
import com.juice.timetable.data.parse.ParseGrade;
import com.juice.timetable.data.viewmodel.SynGradeViewModel;

import java.util.List;

import es.dmoral.toasty.Toasty;

import static es.dmoral.toasty.Toasty.LENGTH_SHORT;

//综合成绩的fragment
public class SynGradeFragment extends Fragment {
    private SynGradeViewModel synGradeViewModel;
    private RecyclerView synRecyclerView;
    private SynGradeRecycleViewAdapter synGradeRecycleViewAdapter;
    private List<SynGrade> synGradeArrayList;

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        SynGrade();
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

        return root;
    }

    //获取数据，然后插入数据库
    private void SynGrade() {
        //新建线程
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    //获取成绩网页源码
                    String pagesource = GradeInfo.getGradeSource(Constant.URI_SYNGRADE);
                    if(pagesource.contains("成绩测评后才能查询成绩")){
                        Looper.prepare();
                        Toasty.custom(requireActivity(), "成绩测评后才能查询成绩!",
                                getResources().getDrawable(R.drawable.grade),
                                getResources().getColor(R.color.green),
                                getResources().getColor(R.color.white),
                                LENGTH_SHORT, false, true).show();
                        Looper.loop();
                    }
                    //利用爬虫获取成绩
                    synGradeArrayList = ParseGrade.parseSynGrade(pagesource);

                    //先清空表
                    synGradeViewModel.deleteAllSynGrade();
                    //再插入数据库
                    for (SynGrade synGrade : synGradeArrayList) {
                        synGradeViewModel.insertSynGrade(synGrade);
                    }
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
            @Override
            public void onChanged(List<SynGrade> synGrades) {
                synGradeRecycleViewAdapter.setSynGradeList(synGrades);
                synGradeRecycleViewAdapter.notifyDataSetChanged();
            }
        });
    }

    private void findID(View root) {
        synRecyclerView = root.findViewById(R.id.synRecyclerView);
    }
}