package com.juice.timetable.ui.grade;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.juice.timetable.R;
import com.juice.timetable.data.bean.SynGrade;

import java.util.ArrayList;
import java.util.List;

import cn.pedant.SweetAlert.SweetAlertDialog;

/**
 * <pre>
 *     author : wyx
 *     time   : 2021/5/23 13:05
 * </pre>
 */
public class SynGradeRecycleViewAdapter extends RecyclerView.Adapter<SynGradeRecycleViewAdapter.SynViewHolder> {
    private static final int VIEW_TYPE_EMPTY = 0;
    private static final int VIEW_TYPE_ITEM = 1;
    private List<SynGrade> synGradeList = new ArrayList<>();

    void setSynGradeList(List<SynGrade> synGradeList) {
        this.synGradeList = synGradeList;
    }

    @NonNull
    @Override
    public SynViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if (viewType == VIEW_TYPE_EMPTY) {
            View emptyView = LayoutInflater.from(parent.getContext()).inflate(R.layout.empty_recyclerview, parent, false);
            return new SynViewHolder(emptyView);
        }
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View itemView = layoutInflater.inflate(R.layout.cell_syngrade, parent, false);

        return new SynViewHolder(itemView);
    }

    @Override
    public int getItemViewType(int position) {
        if (synGradeList.size() == 0) {
            return VIEW_TYPE_EMPTY;
        }
        return VIEW_TYPE_ITEM;
    }

    @Override
    public void onBindViewHolder(@NonNull SynViewHolder holder, final int position) {
        if (synGradeList != null && !synGradeList.isEmpty()) {
            SynGrade synGrade = synGradeList.get(position);
            holder.textViewYear.setText(String.valueOf(synGrade.getCouYear()));
            holder.textViewGradeName.setText(String.valueOf(synGrade.getCouName()));
            holder.textViewSynGrade.setText(String.valueOf(synGrade.getCouGrade()));
            //点击显示完成信息
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    new SweetAlertDialog(holder.itemView.getContext(), SweetAlertDialog.NORMAL_TYPE)
                            .setTitleText(synGrade.getCouName())
                            .setContentText("成绩：" + synGrade.getCouGrade())
                            .hideConfirmButton()
                            .show();
                }
            });
        }

    }

    @Override
    public int getItemCount() {
        if (synGradeList.size() == 0) {
            return 1;
        }
        return synGradeList.size();
    }

    static class SynViewHolder extends RecyclerView.ViewHolder {
        TextView textViewYear, textViewGradeName, textViewSynGrade;

        public SynViewHolder(@NonNull View itemView) {
            super(itemView);
            textViewYear = itemView.findViewById(R.id.textViewYear);
            textViewGradeName = itemView.findViewById(R.id.textViewGradeName);
            textViewSynGrade = itemView.findViewById(R.id.textViewSynGrade);
        }
    }
}
