package com.juice.timetable.ui.tools;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.juice.timetable.R;

import java.util.ArrayList;
import java.util.List;


public class ToolsFragment extends Fragment {
    private RecyclerView toolsRecyclerView;
    private ToolsRecycleViewAdapter toolsRecycleViewAdapter;
    List<Tool> toolList = new ArrayList<>();
    private boolean flag = false;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        //隐藏 toolbar 的按钮 和星期下拉菜单按钮
        Toolbar toolbar = requireActivity().findViewById(R.id.toolbar);
        toolbar.findViewById(R.id.spinner).setVisibility(View.INVISIBLE);

        View root = inflater.inflate(R.layout.fragment_tools, container, false);
        findID(root);


        toolsRecyclerView.setLayoutManager(new LinearLayoutManager(requireContext()));
        toolsRecycleViewAdapter = new ToolsRecycleViewAdapter();
        toolsRecyclerView.setAdapter(toolsRecycleViewAdapter);
        initTools();
        toolsRecycleViewAdapter.setToolList(toolList);

        return root;
    }

    private void findID(View root) {
        toolsRecyclerView = root.findViewById(R.id.toolsRecyclerView);
    }

    private void initTools() {
        if (!flag) {
            Tool grade = new Tool(getResources().getString(R.string.menu_grade), R.drawable.grade);
            Tool exam = new Tool(getResources().getString(R.string.menu_exam), R.drawable.exam);
            Tool expect = new Tool(getResources().getString(R.string.menu_expect),R.drawable.expect);
            toolList.add(grade);
            toolList.add(exam);
            toolList.add(expect);

            flag = true;
        }
    }
}