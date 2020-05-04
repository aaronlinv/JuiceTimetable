package com.juice.timetable.ui.unsigned;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.juice.timetable.R;
import com.juice.timetable.utils.UserInfoUtils;

public class UnsignedFragment extends Fragment {

    private UnsignedViewModel unsignedViewModel;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        unsignedViewModel =
                ViewModelProviders.of(this).get(UnsignedViewModel.class);
        View root = inflater.inflate(R.layout.fragment_gallery, container, false);
        final TextView textView = root.findViewById(R.id.text_gallery);
        unsignedViewModel.getText().observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
                textView.setText(s);
            }
        });
        return root;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        UserInfoUtils userInfoUtils = UserInfoUtils.getINSTANT(requireContext());
        final String id = userInfoUtils.getProperty("id");
        final String eduPasswd = userInfoUtils.getProperty("eduPasswd");
        final String leavePasswd = userInfoUtils.getProperty("leavePasswd");
/*
        new Thread(new Runnable() {
            @Override
            public void run() {
                // 周课表
                String uri = "http://jwb.fdzcxy.com/kb/zkb_xs.asp";
                try {
                    EduInfo.getTimeTable(id, eduPasswd, uri, requireContext().getApplicationContext());
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

        }).start();//

        // 请假系统
        new Thread(new Runnable() {
            @Override
            public void run() {
                // 未签到列表
                // "http://mis.fdzcxy.com/index.php?n=stuwork-dormcheck-unsignin-cls&c=dormcheckclassunsignin"
                // 自己签到情况
                String uri = "http://mis.fdzcxy.com/index.php?n=stuwork-dormcheck-record-student&c=dormcheckrecordstudent";
                try {
                    LeaveInfo.getLeave(id, leavePasswd, uri);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

        }).start();

        // 调用observe方法来获取ViewModel里的数据
        /*OneWeekCourseViewModel oneWeekCourseViewModel = new ViewModelProvider(this).get(OneWeekCourseViewModel.class);
        LiveData<List<OneWeekCourse>> oneWeekCourseLive = oneWeekCourseViewModel.getOneWeekCourseLive();
        oneWeekCourseLive.observe(requireActivity(), new Observer<List<OneWeekCourse>>() {
            @Override
            public void onChanged(List<OneWeekCourse> oneWeekCourses) {
                if(oneWeekCourses!=null){
                    LogUtils.getInstance().d("读取数据"+oneWeekCourses);
                }
            }
        });*/
    }
}
