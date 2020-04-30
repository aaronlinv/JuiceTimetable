package com.juice.timetable.ui.login;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.juice.timetable.R;
import com.juice.timetable.databinding.FragmentSlideshowBinding;

public class LoginFragment extends Fragment {
    private FragmentSlideshowBinding binding;
    private LoginViewModel loginViewModel;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        loginViewModel = new ViewModelProvider(this).get(LoginViewModel.class);
        binding = FragmentSlideshowBinding.inflate(getLayoutInflater());//binding初始化
        View root = inflater.inflate(R.layout.fragment_slideshow, container, false);
        return root;

    }

}
