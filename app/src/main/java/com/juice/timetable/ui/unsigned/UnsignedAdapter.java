package com.juice.timetable.ui.unsigned;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.juice.timetable.R;
import com.juice.timetable.data.bean.ClassNoSignedItem;

import java.util.ArrayList;
import java.util.List;

/**
 * <pre>
 *     author : Administrator
 *     time   : 2020/05/03
 *     desc   :
 *     version: 1.0
 * </pre>
 */
public class UnsignedAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private static final int VIEW_TYPE_EMPTY = 0;
    public static final int VIEW_TYPE_ITEM = 1;
    private List<ClassNoSignedItem> Ifs = new ArrayList<>();
    void setIfs(List<ClassNoSignedItem> ifs) {
        this.Ifs = ifs;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if (viewType == VIEW_TYPE_EMPTY) {
            View emptyView = LayoutInflater.from(parent.getContext()).inflate(R.layout.empty_recyclerview, parent, false);
            return new RecyclerView.ViewHolder(emptyView) {

            };
        }
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View item = layoutInflater.inflate(R.layout.fragment_recycler, parent, false);
        return new MyViewHolder(item);
    }

    @Override
    public int getItemViewType(int position) {
        if (Ifs.size() == 0) {
            return VIEW_TYPE_EMPTY;
        }
        return VIEW_TYPE_ITEM;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, final int position) {
        if (holder instanceof MyViewHolder) {
            MyViewHolder myViewHolder = (MyViewHolder) holder;
            ClassNoSignedItem classNoSignedItem = Ifs.get(position);
            myViewHolder.textViewID.setText(String.valueOf(classNoSignedItem.getSno()));
            myViewHolder.textViewName.setText(String.valueOf(classNoSignedItem.getSname()));
            myViewHolder.textViewNumber.setText(String.valueOf(position + 1));
        }
    }

    @Override
    public int getItemCount() {
        if (Ifs.size() == 0) {
            return 1;
        }
        return Ifs.size();
    }


    static class MyViewHolder extends RecyclerView.ViewHolder {
        TextView textViewNumber, textViewName, textViewID;

        MyViewHolder(View itemView) {
            super(itemView);
            textViewNumber = itemView.findViewById(R.id.textViewNumber);
            textViewName = itemView.findViewById(R.id.textViewName);
            textViewID = itemView.findViewById(R.id.textViewID);
        }
    }
}
