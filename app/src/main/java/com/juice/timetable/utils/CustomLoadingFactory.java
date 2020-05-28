package com.juice.timetable.utils;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.dyhdyh.widget.loading.factory.LoadingFactory;
import com.juice.timetable.R;

/**
 * Created by gyq on 2017/6/7 09:47
 */
public class CustomLoadingFactory implements LoadingFactory {
    private String mString = "";

    @Override
    public View onCreateView(ViewGroup parent) {
        //View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.loading_process_dialog_color, parent, false);
        // View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.loading_process_dialog_icon, parent, false);
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.loading_process_dialog, parent, false);
        TextView textView = view.findViewById(R.id.loading);
        textView.setText(mString);
        return view;

    }

    public void setString(String str) {
        this.mString = str;
    }


}