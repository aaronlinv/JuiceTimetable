package com.juice.timetable.ui.grade;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.juice.timetable.R;
import com.juice.timetable.data.bean.Credit;

import java.util.ArrayList;
import java.util.List;

/**
 * <pre>
 *     author : wyx
 *     time   : 2022/3/15 23:33
 * </pre>
 */
public class CreditRecycleViewAdapter extends RecyclerView.Adapter<CreditRecycleViewAdapter.CreditViewHolder> {
    private List<Credit> creditArrayList = new ArrayList<>();

    public void setCreditArrayList(List<Credit> creditArrayList) {
        this.creditArrayList = creditArrayList;
    }

    @NonNull
    @Override
    public CreditViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View itemView = layoutInflater.inflate(R.layout.cell_credit, parent, false);

        return new CreditViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull CreditViewHolder holder, int position) {
        if (creditArrayList != null && !creditArrayList.isEmpty()) {
            Credit credit = creditArrayList.get(position);
            holder.optionalCourseTypeCredits.setText(credit.getOptionalCourseTypeCredits());
            holder.coursesCompletedCredits.setText(credit.getCoursesCompletedCredits());
            holder.takeHomeCredits.setText(credit.getTakeHomeCredits());
        }

    }

    @Override
    public int getItemViewType(int position) {
        if (creditArrayList.size() == 0) {
            return 0;
        }
        return 1;
    }

    @Override
    public int getItemCount() {
        if (creditArrayList.size() == 0) {
            return 1;
        }
        return creditArrayList.size();
    }

    static class CreditViewHolder extends RecyclerView.ViewHolder {
        TextView optionalCourseTypeCredits, coursesCompletedCredits, takeHomeCredits;

        public CreditViewHolder(@NonNull View itemView) {
            super(itemView);
            optionalCourseTypeCredits = itemView.findViewById(R.id.tv_type_credits);
            coursesCompletedCredits = itemView.findViewById(R.id.tv_completed_credits);
            takeHomeCredits = itemView.findViewById(R.id.tv_take_Credits);
        }
    }

}

