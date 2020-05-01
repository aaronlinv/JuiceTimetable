package com.juice.timetable.ui.login;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.juice.timetable.databinding.FragmentSlideshowBinding;
import com.juice.timetable.utils.LogUtils;

/**
 *修改认证页面相应功能实现类
 */
public class LoginFragment extends Fragment {
    private FragmentSlideshowBinding binding;
    private LoginViewModel loginViewModel;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        loginViewModel = new ViewModelProvider(this).get(LoginViewModel.class);
//        View root = inflater.inflate(R.layout.fragment_slideshow, container, false);
//        return root;
        binding = FragmentSlideshowBinding.inflate(getLayoutInflater());//binding初始化
//        binding = DataBindingUtil.setContentView(requireActivity(), R.layout.fragment_slideshow);
        return binding.getRoot();
    }


    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        JudgeNotInput();
    }


    private void JudgeNotInput() {
        allNull();
        btnPromptNull();
    }

    /**
     * 在什么都没输入时，提示学号未输入
     */
    private void allNull() {
        String sno = binding.etSno.getText().toString().trim();
        String edu = binding.etEduPassword.getText().toString().trim();
        String leave = binding.etLeavePassword.getText().toString().trim();
        if (sno.isEmpty() && edu.isEmpty() && leave.isEmpty()) {
            snoNull();
        }
    }

    /**
     * 监听文本内容，对学号，教务网密码，请假系统密码相应的判断
     */
    private void btnPromptNull() {
        TextWatcher textWatcher = new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                judgmentIsEmpty();
            }

            @Override
            public void afterTextChanged(Editable s) {
                LogUtils.getInstance().d("");
            }
        };
        binding.etSno.addTextChangedListener(textWatcher);//监听里面的内容
        binding.etEduPassword.addTextChangedListener(textWatcher);//监听里面的内容
        binding.etLeavePassword.addTextChangedListener(textWatcher);//监听里面的内容
    }

    private void judgmentIsEmpty() {
        String sno = binding.etSno.getText().toString().trim();
        final String edu = binding.etEduPassword.getText().toString().trim();
        final String leave = binding.etLeavePassword.getText().toString().trim();

        if (sno.isEmpty() && edu.isEmpty() && leave.isEmpty()) {
            snoNull();
        }
        if (sno.isEmpty() && edu.isEmpty() && !leave.isEmpty()) {
            snoNull();
        }
        if (sno.isEmpty() && !edu.isEmpty() && leave.isEmpty()) {
            snoNull();
        }
        if (sno.isEmpty() && !edu.isEmpty() && !leave.isEmpty()) {
            snoNull();
        }
        if (!sno.isEmpty() && edu.isEmpty() && leave.isEmpty()) {
            eduPasswordNull();
        }
        if (!sno.isEmpty() && edu.isEmpty() && !leave.isEmpty()) {
            eduPasswordNull();
        }
        if (!sno.isEmpty() && !edu.isEmpty() && leave.isEmpty()) {
            leavePasswordNull();
        }
        if (!sno.isEmpty() && !edu.isEmpty() && !leave.isEmpty()) {
            binding.btnGo.setOnClickListener(new View.OnClickListener() {
                public void onClick(View v) {
                    judgmentContent();

                }
            });
        }

    }
    /**
     * 点击按钮，提示未输入学号
     */
    private void snoNull() {
        binding.btnGo.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Toast.makeText(requireActivity(), "未输入学号", Toast.LENGTH_SHORT).show();
            }
        });
    }

    /**
     * 点击按钮，提示未输入教务网密码
     */
    private void eduPasswordNull() {
        binding.btnGo.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Toast.makeText(requireActivity(), "未输入教务网密码", Toast.LENGTH_SHORT).show();
            }
        });
    }

    /**
     * 点击按钮，提示未输入请假系统密码
     */
    private void leavePasswordNull() {
        binding.btnGo.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Toast.makeText(requireActivity(), "未输入请假系统密码", Toast.LENGTH_SHORT).show();
            }
        });
    }

    /**
     * 判断学号，教务网密码，请假系统密码，并弹出提示框相应的内容
     */
    private void judgmentContent() {
        int sno = Integer.parseInt(binding.etSno.getText().toString());
        final String edu = binding.etEduPassword.getText().toString().trim();
        final String leave = binding.etLeavePassword.getText().toString().trim();
        if (sno != transferSno(211706160) && !edu.equals(transferEduPassword("123"))
                && !leave.equals(transferLeavePassword("123"))) {
            snoError();
        }
        if (sno != transferSno(211706160) && !edu.equals(transferEduPassword("123"))
                && leave.equals(transferLeavePassword("123"))) {
            snoError();
        }
        if (sno != transferSno(211706160) && edu.equals(transferEduPassword("123"))
                && !leave.equals(transferLeavePassword("123"))) {
            snoError();
        }
        if (sno != transferSno(211706160) && edu.equals(transferEduPassword("123"))
                && leave.equals(transferLeavePassword("123"))) {
            snoError();
        }
        if (sno == transferSno(211706160) && !edu.equals(transferEduPassword("123"))
                && !leave.equals(transferLeavePassword("123"))) {
            eduPasswordError();
        }
        if (sno == transferSno(211706160) && !edu.equals(transferEduPassword("123"))
                && leave.equals(transferLeavePassword("123"))) {
            eduPasswordError();
        }
        if (sno == transferSno(211706160) && edu.equals(transferEduPassword("123"))
                && !leave.equals(transferLeavePassword("123"))) {
            leavePasswordError();
        }
        if (sno == transferSno(211706160) && edu.equals(transferEduPassword("123"))
                && leave.equals(transferLeavePassword("123"))) {
            Toast.makeText(requireActivity(), "验证成功", Toast.LENGTH_SHORT).show();
        }
    }

    /**
     * 提示框，提示“学号错误”
     */
    private void snoError() {
        Toast.makeText(requireActivity(), "学号错误", Toast.LENGTH_SHORT).show();
    }

    /**
     * 提示框，提示“教务网密码错误”
     */
    private void eduPasswordError() {
        Toast.makeText(requireActivity(), "教务网密码错误", Toast.LENGTH_SHORT).show();
    }

    /**
     * 提示框，提示“请假系统密码错误”
     */
    private void leavePasswordError() {
        Toast.makeText(requireActivity(), "请假系统密码错误", Toast.LENGTH_SHORT).show();
    }

    /**
     * @param i 数值型的学号
     * @return 数值型学号
     */
    private Integer transferSno(Integer i) {
        return i;
    }

    private String transferEduPassword(String str) {
        return str;
    }

    private String transferLeavePassword(String str) {
        return str;
    }

}
