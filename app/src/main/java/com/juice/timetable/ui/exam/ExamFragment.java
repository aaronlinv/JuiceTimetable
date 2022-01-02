package com.juice.timetable.ui.exam;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;

import com.juice.timetable.R;
import com.juice.timetable.app.Constant;
import com.juice.timetable.data.bean.Exam;
import com.juice.timetable.data.http.ExamInfo;
import com.juice.timetable.data.parse.ParseExam;

import java.util.List;

public class ExamFragment extends Fragment {


    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        //隐藏 toolbar 的按钮 和星期下拉菜单按钮
        Toolbar toolbar = requireActivity().findViewById(R.id.toolbar);
        toolbar.findViewById(R.id.spinner).setVisibility(View.INVISIBLE);
        Menu menu = toolbar.getMenu();
        menu.setGroupVisible(0, false);

        View root = inflater.inflate(R.layout.exam_fragment, container, false);

        getExamData();

        return root;
    }

    private void findID(View contextView) {

    }

    //获取数据，然后插入数据库
    private void getExamData() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    List<Exam> examArrayList;
                    String pageSource = ExamInfo.getExamSource(Constant.URI_EXAM);

                    examArrayList = ParseExam.parseExam(pageSource);






                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }
}