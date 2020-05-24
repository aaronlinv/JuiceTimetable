package com.juice.timetable.utils;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.dyhdyh.widget.loading.factory.LoadingFactory;
import com.juice.timetable.R;

/**
 * Created by gyq on 2017/6/7 09:47
 */
public class CustomLoadingFactory implements LoadingFactory {
    @Override
    public View onCreateView(ViewGroup parent) {
        //View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.loading_process_dialog_color, parent, false);
        // View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.loading_process_dialog_icon, parent, false);
        
        return LayoutInflater.from(parent.getContext()).inflate(R.layout.loading_process_dialog, parent, false);
    }
}