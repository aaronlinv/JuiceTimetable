package com.juice.timetable.ui.about;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;

import com.juice.timetable.R;

public class AboutFragment extends Fragment {
    private Toolbar toolbar;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        findID();
        View root = inflater.inflate(R.layout.fragment_about, container, false);
        Menu menu = toolbar.getMenu();
        menu.setGroupVisible(0, false);
        return root;
    }

    private void findID() {
        toolbar = requireActivity().findViewById(R.id.toolbar);
    }
}
