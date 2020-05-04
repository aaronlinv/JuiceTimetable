package com.juice.timetable.ui.init;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import com.juice.timetable.R;
import com.juice.timetable.databinding.FragmentInitBinding;
import com.juice.timetable.ui.course.CourseFragment;
import com.juice.timetable.utils.LogUtils;

/**
 * A simple {@link Fragment} subclass.
 */
public class InitFragment extends Fragment {
    private FragmentInitBinding binding;

    public InitFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
//        return inflater.inflate(R.layout.fragment_init, container, false);
        binding = FragmentInitBinding.inflate(getLayoutInflater());
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
//        NavHostFragment.findNavController(this).navigate(R.id.nav_course);
        binding.btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LogUtils.getInstance().d("InitFragment:按键点击事件");

                // 跳转结束后将debugInit置为false否则死循环
                CourseFragment.debugInit = false;
//                Navigation.findNavController(v).navigate(R.id.action_initFragment_to_nav_course);
                Navigation.findNavController(v).navigate(R.id.nav_course);


            }
        });
    }
}
