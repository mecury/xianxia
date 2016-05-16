package com.mecuryli.xianxia.ui.science;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.mecuryli.xianxia.R;

/**
 * Created by 海飞 on 2016/5/16.
 */
public class ScienceFragment extends Fragment {

    private TextView textView;
    private View parentView;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        parentView = View.inflate(getContext(), R.layout.layout_science_list, null);
        textView = (TextView) parentView.findViewById(R.id.tv_science);
        return parentView;
    }
}
