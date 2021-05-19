package com.juice.timetable.ui.grade;

import android.os.Bundle;

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
import com.juice.timetable.databinding.FragmentGradeBinding;

import java.util.ArrayList;

public class GradeFragment extends Fragment {
//    private FragmentGradeBinding binding;
//    private Toolbar toolbar;
//    private Unbinder unbinder;
//    @BindView(R.id.vp_grade)
//    ViewPager2 vpgrade;
//    @BindView(R.id.tabgrade)
//    TabLayout tabgrade;
//
//
//    public GradeFragment() {
//        // Required empty public constructor
//
//    }
//    @Override
//    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
//
//        binding = FragmentGradeBinding.inflate(getLayoutInflater());
//
//        View view = inflater.inflate(R.layout.fragment_grade,null); //使用ButterKnife
//        ButterKnife.bind(this,view);
//
//        //tablayout+viewpager2 统考成绩与综合成绩的页面切换
//        vpgrade.setAdapter(new GradeSlidePagerAdapter(this));
//        new TabLayoutMediator(tabgrade, vpgrade, true,new TabLayoutMediator.TabConfigurationStrategy(){
//
//            @Override
//            public void onConfigureTab(@NonNull TabLayout.Tab tab, int position) {
//                tab.setText("hello"+position);
//            }
//        }).attach();
//
//        // 隐藏 toolbar 的按钮 和星期下拉菜单按钮
//        toolbar = requireActivity().findViewById(R.id.toolbar);
//        toolbar.findViewById(R.id.spinner).setVisibility(View.INVISIBLE);
//        Menu menu = toolbar.getMenu();
//        menu.setGroupVisible(0, false);
//
//
//        return binding.getRoot();
//
//    }
//
//    //一定要提前判断unbinder 是否为空，因为若是在异步回调的时候页面可能已经销毁，此时调用unbind()可能引发空指针异常
//    @Override
//    public void onDestroyView() {
//        if (unbinder != null) {
//            unbinder.unbind();
//        }
//        super.onDestroyView();
//    }
//
    private FragmentGradeBinding binding;
    private Toolbar toolbar;

    //以下为测试demo
    private View contextView;// 总视图
    private TabLayout tabLayout;
    private ViewPager viewpager;
    ArrayList fragmentList = new ArrayList<Fragment>();
    String[] temp = {"综合成绩","统考成绩"};

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = FragmentGradeBinding.inflate(getLayoutInflater());
//         隐藏 toolbar 的按钮 和星期下拉菜单按钮
        toolbar = requireActivity().findViewById(R.id.toolbar);
        toolbar.findViewById(R.id.spinner).setVisibility(View.INVISIBLE);
        Menu menu = toolbar.getMenu();
        menu.setGroupVisible(0, false);

        //tablayout+viewpager 统考成绩与综合成绩的页面切换
        contextView = inflater.inflate(R.layout.fragment_grade, container, false);
        tabLayout = contextView.findViewById(R.id.tabgrade);
        viewpager = contextView.findViewById(R.id.vp_grade);
        return contextView;
    }
    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        // fragment中嵌套fragment, Manager需要用(getChildFragmentManager())
        MPagerAdapter mPagerAdapter = new MPagerAdapter(getChildFragmentManager());
        initFragment();
        tabLayout.setupWithViewPager(viewpager);
        viewpager.setAdapter(mPagerAdapter);
    }
    private void initFragment() {
        fragmentList.add(new SynGradeFragment());
        fragmentList.add(new UniGradeFragment());
    }

    class MPagerAdapter extends FragmentPagerAdapter {


        public MPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            return (Fragment) fragmentList.get(position);
        }

        @Override
        public int getCount() {
            return fragmentList.size();
        }

        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            return super.instantiateItem(container, position);
        }

        //返回tablayout的标题文字;
        @Override
        public CharSequence getPageTitle(int position) {
            return temp[position];
        }
    }


}