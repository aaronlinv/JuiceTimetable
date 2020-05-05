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
public class UnsignedAdapter extends RecyclerView.Adapter<UnsignedAdapter.MyViewHolder> {
    private List<ClassNoSignedItem> allInfos = new ArrayList<>();

    void setAllInfos(List<ClassNoSignedItem> allInfos) {
        this.allInfos = allInfos;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View item = layoutInflater.inflate(R.layout.recycler, parent, false);
        return new MyViewHolder(item);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder,final int position) {
        ClassNoSignedItem classNoSignedItem = allInfos.get(position);
        holder.textViewID.setText(String.valueOf(classNoSignedItem.getSno()));
        holder.textViewName.setText(String.valueOf(classNoSignedItem.getSname()));
        holder.textViewNumber.setText(String.valueOf(position + 1));
    }

    @Override
    public int getItemCount() {
        return allInfos.size();
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
