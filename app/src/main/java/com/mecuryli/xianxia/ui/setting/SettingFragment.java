package com.mecuryli.xianxia.ui.setting;

import android.os.Bundle;
import android.preference.PreferenceFragment;

import com.mecuryli.xianxia.R;

/**
 * Created by 海飞 on 2016/6/3.
 */
public class SettingFragment extends PreferenceFragment {
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        addPreferencesFromResource(R.xml.settings);
    }
}
