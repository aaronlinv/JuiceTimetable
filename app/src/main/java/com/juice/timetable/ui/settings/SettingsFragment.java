package com.juice.timetable.ui.settings;


import android.os.Bundle;
import android.view.View;

import androidx.appcompat.widget.Toolbar;
import androidx.preference.Preference;
import androidx.preference.PreferenceFragmentCompat;

import com.juice.timetable.R;
import com.juice.timetable.app.Constant;
import com.juice.timetable.utils.PreferencesUtils;

import es.dmoral.toasty.Toasty;

public class SettingsFragment extends PreferenceFragmentCompat {

    @Override
    public void onCreatePreferences(Bundle savedInstanceState, String rootKey) {
        setPreferencesFromResource(R.xml.root_preferences, rootKey);

        //隐藏 toolbar 的按钮 和星期下拉菜单按钮
        Toolbar toolbar = requireActivity().findViewById(R.id.toolbar);
        toolbar.findViewById(R.id.spinner).setVisibility(View.INVISIBLE);

        // 重置新手引导
        restFirstGuide();

    }

    private void restFirstGuide() {
        Preference button = findPreference("restButton");
        button.setOnPreferenceClickListener(new Preference.OnPreferenceClickListener() {
            @Override
            public boolean onPreferenceClick(Preference preference) {
                PreferencesUtils.putBoolean(Constant.FIRST_LOGIN_GUIDE, true);
                Toasty.info(preference.getContext(), "重置完成", Toasty.LENGTH_SHORT, false).show();

                return true;
            }
        });
    }
}