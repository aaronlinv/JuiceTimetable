package com.juice.timetable.ui.grade;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.juice.timetable.R;
import com.juice.timetable.data.bean.UniGrade;

import java.util.ArrayList;
import java.util.List;

import cn.pedant.SweetAlert.SweetAlertDialog;
/**
 * <pre>
 *     author : wyx
 *     time   : 2021/5/25 23:09
 * </pre>
 */
public class UniGradeRecycleViewAdapter extends RecyclerView.Adapter<UniGradeRecycleViewAdapter.UniViewHolder> {
    private static final int VIEW_TYPE_EMPTY = 0;
    private static final int VIEW_TYPE_ITEM = 1;
    private List<UniGrade> uniGradeList = new ArrayList<>();

    public void setUniGradeList(List<UniGrade> uniGradeList) {
        this.uniGradeList = uniGradeList;
    }

    @NonNull
    @Override
    public UniViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if (viewType == VIEW_TYPE_EMPTY) {
            View emptyView = LayoutInflater.from(parent.getContext()).inflate(R.layout.empty_recyclerview, parent, false);
            return new UniViewHolder(emptyView);
        }
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View itemView = layoutInflater.inflate(R.layout.cell_unigrade, parent, false);

        return new UniViewHolder(itemView);
    }

    @Override
    public int getItemViewType(int position) {
        if (uniGradeList.size() == 0) {
            return VIEW_TYPE_EMPTY;
        }
        return VIEW_TYPE_ITEM;
    }

    @Override
    public void onBindViewHolder(@NonNull UniViewHolder holder, final int position) {
        if (uniGradeList != null && !uniGradeList.isEmpty()) {
            UniGrade uniGrade = uniGradeList.get(position);
            holder.textViewUniYear.setText(String.valueOf(uniGrade.getuYear()));
            holder.textViewUniName.setText(String.valueOf(uniGrade.getuName()));
            holder.textViewUniGrade.setText(String.valueOf(uniGrade.getuGrade()));
            holder.textViewRemark.setText(String.valueOf(uniGrade.getuRemarks()));
            //点击显示完成信息
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    new SweetAlertDialog(holder.itemView.getContext(), SweetAlertDialog.NORMAL_TYPE)
                            .setTitleText(uniGrade.getuName())
                            .setContentText("成绩：" + uniGrade.getuGrade() + " 备注：" + uniGrade.getuRemarks())
                            .hideConfirmButton()
                            .show();
                }
            });
        }
    }

    static class UniViewHolder extends RecyclerView.ViewHolder {
        TextView textViewUniYear, textViewRemark, textViewUniGrade, textViewUniName;

        public UniViewHolder(@NonNull View itemView) {
            super(itemView);
            textViewUniYear = itemView.findViewById(R.id.textViewUniYear);
            textViewUniGrade = itemView.findViewById(R.id.textViewUniGrade);
            textViewUniName = itemView.findViewById(R.id.textViewUniName);
            textViewRemark = itemView.findViewById(R.id.textViewRemark);
        }
    }

    @Override
    public int getItemCount() {
        if (uniGradeList.size() == 0) {
            return 1;
        }
        return uniGradeList.size();
    }
}
