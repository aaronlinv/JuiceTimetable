package com.juice.timetable.ui.grade;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;

import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.juice.timetable.R;
import com.juice.timetable.data.bean.Credit;
import com.juice.timetable.data.viewmodel.CreditViewModel;

import java.util.List;


public class CreditFragment extends Fragment {
    private CreditViewModel creditViewModel;
    private RecyclerView creditRecyclerView;
    private CreditRecycleViewAdapter creditRecycleViewAdapter;
    private LiveData<List<Credit>> creditListLive;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        //隐藏 toolbar 的按钮 和星期下拉菜单按钮
        Toolbar toolbar = requireActivity().findViewById(R.id.toolbar);
        toolbar.findViewById(R.id.spinner).setVisibility(View.INVISIBLE);
        Menu menu = toolbar.getMenu();
        menu.setGroupVisible(0, false);
        View root = inflater.inflate(R.layout.fragment_credit, container, false);

        findID(root);
        //初始化ViewModel
        creditViewModel = new ViewModelProvider(requireActivity()).get(CreditViewModel.class);

        creditRecyclerView.setLayoutManager(new LinearLayoutManager(requireContext()));
        creditRecycleViewAdapter = new CreditRecycleViewAdapter();
        creditRecyclerView.setAdapter(creditRecycleViewAdapter);


        return root;
    }

    @Override
    public void onStart() {
        super.onStart();
        creditListLive = creditViewModel.getAllCreditLive();
        creditListLive.observe(requireActivity(), new Observer<List<Credit>>() {
            @SuppressLint("NotifyDataSetChanged")
            @Override
            public void onChanged(List<Credit> credits) {
                creditRecycleViewAdapter.setCreditArrayList(credits);
                creditRecycleViewAdapter.notifyDataSetChanged();
            }
        });
    }

    public void findID(View root) {
        creditRecyclerView = root.findViewById(R.id.creditRecyclerView);
    }

}