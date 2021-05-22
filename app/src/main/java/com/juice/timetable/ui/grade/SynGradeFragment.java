package com.juice.timetable.ui.grade;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.juice.timetable.R;
import com.juice.timetable.app.Constant;
import com.juice.timetable.data.bean.ClassNoSignedItem;
import com.juice.timetable.data.bean.SynGrade;
import com.juice.timetable.data.http.GradeInfo;
import com.juice.timetable.data.parse.ParseClassNoSignedItem;
import com.juice.timetable.data.parse.ParseGrade;

import java.util.List;

//综合成绩的fragment
public class SynGradeFragment extends Fragment {

    public SynGradeFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        //新建网络线程
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    List<SynGrade> synGradeArrayList;
                    String str = GradeInfo.getGradeSource(Constant.URI_SYNGRADE);
                    synGradeArrayList = ParseGrade.parseSynGrade(str);

                    System.out.println(synGradeArrayList);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }){
        }.start();


        return inflater.inflate(R.layout.fragment_grade_syn, container, false);
    }
    private void getSynGrade(){
        SynGrade synGrade = new SynGrade();

    }

    @Override
    public void onResume() {
        super.onResume();
    }
}