package com.juice.timetable.ui.course;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.ListAdapter;
import androidx.recyclerview.widget.RecyclerView;

import com.juice.timetable.R;

/**
 * <pre>
 *     author : Aaron
 *     time   : 2020/05/15
 *     desc   :
 *     version: 1.0
 * </pre>
 */
public class CourseViewListAdapter extends ListAdapter<CourseView, CourseViewHolder> {
    protected CourseViewListAdapter() {
        super(new DiffUtil.ItemCallback<CourseView>() {
            @Override
            public boolean areItemsTheSame(@NonNull CourseView oldItem, @NonNull CourseView newItem) {
                return oldItem == newItem;
            }

            @Override
            public boolean areContentsTheSame(@NonNull CourseView oldItem, @NonNull CourseView newItem) {
                return oldItem.getCurrentIndex() == newItem.getCurrentIndex();
            }
        });
    }

    @NonNull
    @Override
    public CourseViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        CourseViewHolder holder = new CourseViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.pager_course_view, parent, false));
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull CourseViewHolder holder, int position) {
        CourseView courseView = holder.itemView.findViewById(R.id.course_view_pager);

//        courseView.resetView();
    }
}

class CourseViewHolder extends RecyclerView.ViewHolder {

    public CourseViewHolder(@NonNull View itemView) {
        super(itemView);
    }
}