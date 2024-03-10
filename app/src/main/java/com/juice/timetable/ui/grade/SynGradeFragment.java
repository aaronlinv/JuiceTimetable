package com.juice.timetable.ui.grade;

import static es.dmoral.toasty.Toasty.LENGTH_SHORT;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.SearchView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.juice.timetable.R;
import com.juice.timetable.app.Constant;
import com.juice.timetable.data.bean.Credit;
import com.juice.timetable.data.bean.SynGrade;
import com.juice.timetable.data.http.GradeInfo;
import com.juice.timetable.data.parse.ParseGrade;
import com.juice.timetable.data.viewmodel.CreditViewModel;
import com.juice.timetable.data.viewmodel.SynGradeViewModel;
import com.juice.timetable.utils.LogUtils;

import java.util.List;

import es.dmoral.toasty.Toasty;

//综合成绩的fragment
public class SynGradeFragment extends Fragment {
    private SynGradeViewModel synGradeViewModel;
    private CreditViewModel creditViewModel;
    private RecyclerView synRecyclerView;
    private SynGradeRecycleViewAdapter synGradeRecycleViewAdapter;
    private List<SynGrade> synGradeArrayList;
    public List<Credit> creditArrayList;
    private SwipeRefreshLayout mSlRefresh;
    private Handler mHandler;
    private LiveData<List<SynGrade>> filterSynList;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //获取数据
        getSynGradeData();
    }

    @SuppressLint("NotifyDataSetChanged")
    @Override
    public void onResume() {
        super.onResume();
        if (synGradeArrayList != null) {
            synGradeRecycleViewAdapter.setSynGradeList(synGradeArrayList);
            synGradeRecycleViewAdapter.notifyDataSetChanged();
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_grade_syn, container, false);
        findID(root);
        //初始化ViewModel
        synGradeViewModel = new ViewModelProvider(requireActivity()).get(SynGradeViewModel.class);
        creditViewModel = new ViewModelProvider(requireActivity()).get(CreditViewModel.class);
        //布局管理器
        synRecyclerView.setLayoutManager(new LinearLayoutManager(requireContext()));
        synGradeRecycleViewAdapter = new SynGradeRecycleViewAdapter();
        synRecyclerView.setAdapter(synGradeRecycleViewAdapter);
        //显示刷新按钮
        mSlRefresh.setRefreshing(true);
        //下拉刷新
        Refresh();
        //开启搜索
        setHasOptionsMenu(true);

        return root;
    }

    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.grade_bar, menu);
        SearchView searchView = (SearchView) menu.findItem(R.id.app_bar_grade_search).getActionView();
        searchView.setMaxWidth(1000);
        // 搜索按钮点击
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                String pattern = newText.trim();
                filterSynList = synGradeViewModel.findNameWithPattern(pattern);
                filterSynList.observe(requireActivity(), new Observer<List<SynGrade>>() {
                    @SuppressLint("NotifyDataSetChanged")
                    @Override
                    public void onChanged(List<SynGrade> synGrades) {
                        synGradeRecycleViewAdapter.setSynGradeList(synGrades);
                        synGradeRecycleViewAdapter.notifyDataSetChanged();
                    }
                });
                return true;
            }
        });
        // 学分按钮点击
        menu.findItem(R.id.app_bar_credits).setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                NavController controller = Navigation.findNavController(requireView());
                controller.navigate(R.id.action_nav_grade_to_nav_credit);
                return true;
            }
        });

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
                    String pageSource = GradeInfo.getGradeSource(Constant.URI_SYNGRADE);
                    String needEvaluationStr = getString(R.string.need_evaluation);
                    if (pageSource.contains(needEvaluationStr)) {
                        Looper.prepare();
                        mSlRefresh.setRefreshing(false);
                        Toasty.custom(requireActivity(),
                                getResources().getString(R.string.need_evaluation),
                                getResources().getDrawable(R.drawable.grade, null),
                                getResources().getColor(R.color.green, null),
                                getResources().getColor(R.color.white, null),
                                LENGTH_SHORT, false, true).show();
                        Looper.loop();
                        return;
                    }
                    //利用爬虫获取成绩
                    synGradeArrayList = ParseGrade.parseSynGrade(pageSource);
                    //先清空表
                    synGradeViewModel.deleteAllSynGrade();
                    creditViewModel.deleteAllCredit();
                    //再插入数据库
                    for (SynGrade synGrade : synGradeArrayList) {
                        synGradeViewModel.insertSynGrade(synGrade);
                    }
                    creditArrayList = ParseGrade.parseCredits(pageSource);
                    for (Credit credit : creditArrayList) {
                        creditViewModel.insertCredit(credit);
                    }
                    message.what = Constant.MSG_PARSESYN_SUCCESS;
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
                if (message.what == Constant.MSG_PARSESYN_SUCCESS) {
                    filterSynList = synGradeViewModel.getAllSynGradeLive();
                    filterSynList.observe(requireActivity(), new Observer<List<SynGrade>>() {
                        @SuppressLint("NotifyDataSetChanged")
                        @Override
                        public void onChanged(List<SynGrade> synGrades) {
                            synGradeRecycleViewAdapter.setSynGradeList(synGrades);
                            synGradeRecycleViewAdapter.notifyDataSetChanged();
                        }
                    });
                    mSlRefresh.setRefreshing(false);
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
        synRecyclerView = root.findViewById(R.id.synRecyclerView);
        mSlRefresh = root.findViewById(R.id.syn_refresh);
    }

    private void Refresh() {
        // 下拉刷新监听
        mSlRefresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                getSynGradeData();
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