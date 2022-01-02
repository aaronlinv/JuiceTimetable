package com.juice.timetable.ui.exam;

import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
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
import com.juice.timetable.data.bean.Exam;
import com.juice.timetable.data.http.ExamInfo;
import com.juice.timetable.data.parse.ParseExam;
import com.juice.timetable.data.viewmodel.ExamViewModel;

import java.util.List;

public class ExamFragment extends Fragment {
    private SwipeRefreshLayout mSlRefresh;
    private ExamViewModel examViewModel;
    private RecyclerView examRecyclerView;
    private List<Exam> examArrayList;
    private Handler mHandler;
    private ExamRecycleViewAdapter examRecycleViewAdapter;


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        Refresh();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        //隐藏 toolbar 的按钮 和星期下拉菜单按钮
        Toolbar toolbar = requireActivity().findViewById(R.id.toolbar);
        toolbar.findViewById(R.id.spinner).setVisibility(View.INVISIBLE);
        Menu menu = toolbar.getMenu();
        menu.setGroupVisible(0, false);

        View root = inflater.inflate(R.layout.fragment_exam, container, false);

        findID(root);
        //初始化ViewModel
        examViewModel = new ViewModelProvider(requireActivity()).get(ExamViewModel.class);
        //布局管理器
        examRecyclerView.setLayoutManager(new LinearLayoutManager(requireContext()));
        examRecycleViewAdapter = new ExamRecycleViewAdapter();
        examRecyclerView.setAdapter(examRecycleViewAdapter);

        getExamData();

        return root;
    }

    //获取数据，然后插入数据库
    private void getExamData() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                Message message = new Message();
                try {
                    String pageSource = ExamInfo.getExamSource(Constant.URI_EXAM);
                    //利用爬虫获取考场信息
                    examArrayList = ParseExam.parseExam(pageSource);
                    //先清空表
                    examViewModel.deleteAllExam();
                    //再插入数据库
                    for (Exam exam : examArrayList) {
                        examViewModel.insertExam(exam);
                    }
                    mSlRefresh.setRefreshing(false);
                    message.what = Constant.MSG_PARSEEXAM_SUCCESS;
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
        LiveData<List<Exam>> listExamLiveData = examViewModel.getAllExamLive();
        listExamLiveData.observe(requireActivity(), new Observer<List<Exam>>() {
            @Override
            public void onChanged(List<Exam> exams) {
                mHandler = new Handler(Looper.getMainLooper()) {
                    public void handleMessage(@NonNull Message msg) {
                        super.handleMessage(msg);
                        if (msg.what == Constant.MSG_PARSEEXAM_SUCCESS) {
                            examRecycleViewAdapter.setExamArrayList(exams);
                            examRecycleViewAdapter.notifyItemChanged(R.id.exam_refresh);
                        }
                    }
                };
            }
        });
    }

    private void findID(View root) {
        examRecyclerView = root.findViewById(R.id.examRecyclerView);
        mSlRefresh = root.findViewById(R.id.exam_refresh);
    }

    private void Refresh() {
        mSlRefresh.setRefreshing(true);
        // 下拉刷新监听
        mSlRefresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                getExamData();
            }
        });
    }
}