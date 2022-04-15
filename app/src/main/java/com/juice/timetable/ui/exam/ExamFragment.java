package com.juice.timetable.ui.exam;

import static es.dmoral.toasty.Toasty.LENGTH_SHORT;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.SearchView;
import android.widget.Spinner;
import android.widget.TextView;

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
import com.juice.timetable.data.viewmodel.StuInfoViewModel;
import com.juice.timetable.utils.LogUtils;
import com.juice.timetable.utils.PreferencesUtils;

import java.util.Collections;
import java.util.List;

import es.dmoral.toasty.Toasty;

public class ExamFragment extends Fragment {
    private SwipeRefreshLayout mSlRefresh;
    private ExamViewModel examViewModel;
    private StuInfoViewModel stuInfoViewModel;
    private RecyclerView examRecyclerView;
    private List<Exam> examArrayList;
    private Handler mHandler;
    private ExamRecycleViewAdapter examRecycleViewAdapter;
    private LiveData<List<Exam>> filterExamList;
    private Spinner spinnerExamYear, spinnerExamType;

    private String year;
    private String type;
    private String[] mItemsYear = new String[4];
    private String[] mItemsType = {"上", "下"};

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
        stuInfoViewModel = new ViewModelProvider(requireActivity()).get(StuInfoViewModel.class);

        //初始化spinner
        initSpinner();
        //切换学期
        shiftSemester();

        //布局管理器
        examRecyclerView.setLayoutManager(new LinearLayoutManager(requireContext()));
        examRecycleViewAdapter = new ExamRecycleViewAdapter();
        examRecyclerView.setAdapter(examRecycleViewAdapter);

        //开启搜索
        setHasOptionsMenu(true);

        return root;
    }

    //搜索功能
    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.exam_bar, menu);
        SearchView searchView = (SearchView) menu.findItem(R.id.app_bar_exam_search).getActionView();
        searchView.setMaxWidth(1000);
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            //确定时候改变
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            //输入时候改变
            @Override
            public boolean onQueryTextChange(String newText) {
                String pattern = newText.trim();
                filterExamList.removeObservers(requireActivity());
                filterExamList = examViewModel.findNameWithPattern(pattern);
                filterExamList.observe(requireActivity(), new Observer<List<Exam>>() {
                    @SuppressLint("NotifyDataSetChanged")
                    @Override
                    public void onChanged(List<Exam> exams) {
                        examRecycleViewAdapter.setExamArrayList(exams);
                        examRecycleViewAdapter.notifyDataSetChanged();
                    }
                });
                return true;
            }
        });
    }

    private void initSpinner() {
        //获取学号
        String stu_str = stuInfoViewModel.selectStuInfo().getStuID().toString().substring(2, 4);
        int stu = Integer.parseInt(stu_str);
        //初始化year、type
        year = PreferencesUtils.getString("CUR_SEMESTER", "20" + stu_str).substring(9, 13);
        type = PreferencesUtils.getString("CUR_SEMESTER", null).substring(13, 14);

        for (int i = 0; i < 4; i++) {
            mItemsYear[i] = "20" + stu;
            stu++;
        }
        ArrayAdapter<String> adapterYear = new ArrayAdapter<>(requireContext(), android.R.layout.simple_spinner_item, mItemsYear);
        adapterYear.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        ArrayAdapter<String> adapterType = new ArrayAdapter<>(requireContext(), android.R.layout.simple_spinner_item, mItemsType);
        adapterYear.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerExamYear.setAdapter(adapterYear);
        spinnerExamType.setAdapter(adapterType);

        //初始化时间为当前学期
        int location = 0;
        for (int i = 0; i < 4; i++) {
            if (mItemsYear[i].equals(year)) {
                location = i;
            }
        }
        spinnerExamYear.setSelection(location);

        for (int i = 0; i < 2; i++) {
            if (mItemsType[i].equals(type)) {
                location = i;
            }
        }
        spinnerExamType.setSelection(location);
    }

    // 学期学年切换
    private void shiftSemester() {
        spinnerExamYear.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                TextView tv = (TextView) view;
                tv.setTextSize(16f);
                year = mItemsYear[position];
                mSlRefresh.setRefreshing(true);
                getExamData(year, type);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });
        spinnerExamType.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                TextView tv = (TextView) view;
                tv.setTextSize(16f);
                type = mItemsType[position];
                mSlRefresh.setRefreshing(true);
                getExamData(year, type);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });
    }


    //获取数据，然后插入数据库
    private void getExamData(String year, String type) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                Message message = new Message();
                try {
                    String pageSource = ExamInfo.getExamSource(Constant.URI_EXAM, year, type);
                    //利用爬虫获取考场信息
                    examArrayList = ParseExam.parseExam(pageSource);
                    //先清空表
                    examViewModel.deleteAllExam();
                    //再插入数据库
                    //调转
                    Collections.reverse(examArrayList);
                    for (Exam exam : examArrayList) {
                        examViewModel.insertExam(exam);
                    }
                    mSlRefresh.setRefreshing(false);
                    message.what = Constant.MSG_PARSEEXAM_SUCCESS;
                    mHandler.sendMessage(message);
                } catch (Exception e) {
                    // 网络不好的情况
                    LogUtils.getInstance().d("setOnRefreshListener：" + e.getMessage());
                    if (e.getMessage().contains("Unable to resolve host")) {
                        message.obj = "网络好像不太好，请检查网络";
                        mHandler.sendMessage(message);
                    } else {
                        message.obj = e.getMessage();
                    }
                }
            }
        }).start();
    }

    @Override
    public void onStart() {
        super.onStart();

        mHandler = new Handler(Looper.getMainLooper()) {
            @Override
            public void handleMessage(@NonNull Message message) {
                super.handleMessage(message);
                if (message.what == Constant.MSG_PARSEEXAM_SUCCESS) {
                    filterExamList = examViewModel.getAllExamLive();
                    filterExamList.observe(requireActivity(), new Observer<List<Exam>>() {
                        @SuppressLint("NotifyDataSetChanged")
                        @Override
                        public void onChanged(List<Exam> exams) {
                            examRecycleViewAdapter.setExamArrayList(exams);
                            examRecycleViewAdapter.notifyDataSetChanged();
                        }
                    });
                } else if (message.obj == "网络好像不太好，请检查网络") {
                    Toasty.custom(requireActivity(),
                            message.obj.toString(),
                            getResources().getDrawable(R.drawable.ic_error, null),
                            getResources().getColor(R.color.red, null),
                            getResources().getColor(R.color.white, null),
                            LENGTH_SHORT, true, true).show();
                    mSlRefresh.setRefreshing(false);
                }
            }
        };
    }

    private void findID(View root) {
        examRecyclerView = root.findViewById(R.id.examRecyclerView);
        mSlRefresh = root.findViewById(R.id.exam_refresh);
        spinnerExamYear = root.findViewById(R.id.sp_exam_year);
        spinnerExamType = root.findViewById(R.id.sp_exam_type);
    }

    private void Refresh() {
        mSlRefresh.setRefreshing(true);
        // 下拉刷新监听
        mSlRefresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                getExamData(year, type);

                Toasty.custom(requireActivity(),
                        getResources().getString(R.string.refresh_success),
                        getResources().getDrawable(R.drawable.success, null),
                        getResources().getColor(R.color.green, null),
                        getResources().getColor(R.color.white, null),
                        LENGTH_SHORT, true, true).show();
            }
        });
    }
}