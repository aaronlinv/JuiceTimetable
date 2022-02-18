package com.juice.timetable.ui.tools;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.RecyclerView;

import com.juice.timetable.R;

import java.util.ArrayList;
import java.util.List;

/**
 * <pre>
 *     author : wyx
 *     time   : 2022/2/18 13:25
 * </pre>
 */
public class ToolsRecycleViewAdapter extends RecyclerView.Adapter<ToolsRecycleViewAdapter.ToolsViewHolder> {
    //    String[] toolList = {"成绩查询","考场查询"};
    List<Tool> toolList = new ArrayList<>();

    public void setToolList(List<Tool> toolList) {
        this.toolList = toolList;
    }

    @NonNull
    @Override
    public ToolsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View itemView = layoutInflater.inflate(R.layout.cell_tool_item, parent, false);
        return new ToolsViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull ToolsViewHolder holder, int position) {
        Tool tool = toolList.get(position);
        holder.toolTextView.setText(String.valueOf(tool.getToolName()));
        holder.toolImageView.setImageResource(tool.getToolImageId());

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                NavController navController = Navigation.findNavController(v);
                if (tool.getToolName().equals("成绩查询")) {
                    navController.navigate(R.id.action_nav_tools_to_nav_grade);
                } else if (tool.getToolName().equals("考场查询")) {
                    navController.navigate(R.id.action_nav_tools_to_nav_exam);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return toolList.size();
    }

    static class ToolsViewHolder extends RecyclerView.ViewHolder {
        ImageView toolImageView;
        TextView toolTextView;

        public ToolsViewHolder(@NonNull View itemView) {
            super(itemView);
            toolImageView = itemView.findViewById(R.id.toolImageView);
            toolTextView = itemView.findViewById(R.id.toolTextView);
        }
    }
}
