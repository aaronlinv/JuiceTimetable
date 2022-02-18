package com.juice.timetable.ui.grade;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;

import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;

import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.material.tabs.TabLayout;
import com.juice.timetable.R;

import java.util.ArrayList;

public class GradeFragment extends Fragment {
    private TabLayout tabLayout;
    private ViewPager viewpager;
    ArrayList fragmentList = new ArrayList<Fragment>();
    String[] temp = {"综合成绩", "统考成绩"};
    private boolean flag = false;

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        //隐藏 toolbar 的按钮 和星期下拉菜单按钮
        Toolbar toolbar = requireActivity().findViewById(R.id.toolbar);
        toolbar.findViewById(R.id.spinner).setVisibility(View.INVISIBLE);
        Menu menu = toolbar.getMenu();
        menu.setGroupVisible(0, false);

        //tablayout+viewpager 统考成绩与综合成绩的页面切换
        View root = inflater.inflate(R.layout.fragment_grade, container, false);
        findID(root);

        return root;

    }

    private void findID(View root) {
        tabLayout = root.findViewById(R.id.tabgrade);
        viewpager = root.findViewById(R.id.vp_grade);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        // fragment中嵌套fragment, Manager需要用(getChildFragmentManager())
        MPagerAdapter mPagerAdapter = new MPagerAdapter(getChildFragmentManager());
        initFragment();
        tabLayout.setupWithViewPager(viewpager);
        viewpager.setAdapter(mPagerAdapter);
    }

    private void initFragment() {
        //只需要创建一遍
        if (!flag) {
            fragmentList.add(new SynGradeFragment());
            fragmentList.add(new UniGradeFragment());
            flag = true;
        }
    }

    class MPagerAdapter extends FragmentPagerAdapter {
        public MPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @NonNull
        @Override
        public Fragment getItem(int position) {
            return (Fragment) fragmentList.get(position);
        }

        @Override
        public int getCount() {
            return fragmentList.size();
        }

        @NonNull
        @Override
        public Object instantiateItem(@NonNull ViewGroup container, int position) {
            return super.instantiateItem(container, position);
        }

        //返回tablayout的标题文字;
        @Override
        public CharSequence getPageTitle(int position) {
            return temp[position];
        }
    }
}