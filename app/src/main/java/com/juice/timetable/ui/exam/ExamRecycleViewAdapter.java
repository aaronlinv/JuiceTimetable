package com.juice.timetable.ui.exam;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.juice.timetable.R;
import com.juice.timetable.data.bean.Exam;

import java.util.ArrayList;
import java.util.List;

import cn.pedant.SweetAlert.SweetAlertDialog;

/**
 * <pre>
 *     author : wyx
 *     time   : 2022/1/2 12:09
 * </pre>
 */
public class ExamRecycleViewAdapter extends RecyclerView.Adapter<ExamRecycleViewAdapter.ExamViewHolder> {
    private static final int VIEW_TYPE_EMPTY = 0;
    private static final int VIEW_TYPE_ITEM = 1;
    private List<Exam> examArrayList = new ArrayList<>();

    void setExamArrayList(List<Exam> examArrayList) {
        this.examArrayList = examArrayList;
    }

    @NonNull
    @Override
    public ExamViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if (viewType == VIEW_TYPE_EMPTY) {
            View emptyView = LayoutInflater.from(parent.getContext()).inflate(R.layout.empty_recyclerview, parent, false);
            return new ExamViewHolder(emptyView);
        }
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View itemView = layoutInflater.inflate(R.layout.cell_exam, parent, false);

        return new ExamViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull ExamViewHolder holder,final int position) {
        if (examArrayList != null && !examArrayList.isEmpty()) {
            Exam exam = examArrayList.get(position);
            holder.textViewExamSemester.setText(String.valueOf(exam.getSemester()));
            holder.textViewCourseName.setText(String.valueOf(exam.getCourseName()));
            holder.textViewArrangement.setText(String.valueOf(exam.getArrangement()));
            holder.textViewExamTime.setText(String.valueOf(exam.getExamTime()));

            //点击显示完成信息
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    new SweetAlertDialog(holder.itemView.getContext(), SweetAlertDialog.NORMAL_TYPE)
                            .setTitleText(exam.getCourseName())
                            .setContentText("开课学期：" + exam.getSemester()
                                    + "<br>考试类型：" + exam.getExamType()
                                    + "<br>考试类别：" + exam.getExamCategory()
                                    + "<br>考试时间：" + exam.getExamTime()
                                    + "<br>考场安排：" + exam.getArrangement()
                                    + "<br>班级：" + exam.getClassGrade())
                            .hideConfirmButton()
                            .show();
                }
            });
        }

    }

    @Override
    public int getItemViewType(int position) {
        if(examArrayList.size() == 0){
            return VIEW_TYPE_EMPTY;
        }
        return VIEW_TYPE_ITEM;
    }

    @Override
    public int getItemCount() {
        if(examArrayList.size() == 0){
            return 1;
        }
        return examArrayList.size();
    }

    static class ExamViewHolder extends RecyclerView.ViewHolder {
        TextView textViewExamSemester, textViewCourseName, textViewExamTime, textViewArrangement;

        public ExamViewHolder(@NonNull View itemView) {
            super(itemView);
            textViewExamSemester = itemView.findViewById(R.id.textViewExamSemester);
            textViewArrangement = itemView.findViewById(R.id.textViewArrangement);
            textViewCourseName = itemView.findViewById(R.id.textViewCourseName);
            textViewExamTime = itemView.findViewById(R.id.textViewExamTime);
        }
    }
}

