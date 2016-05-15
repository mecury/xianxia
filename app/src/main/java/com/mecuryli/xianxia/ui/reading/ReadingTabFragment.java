package com.mecuryli.xianxia.ui.reading;

import android.annotation.TargetApi;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.mecuryli.xianxia.R;

/**
 * Created by 海飞 on 2016/5/15.
 */
public class ReadingTabFragment extends android.support.v4.app.Fragment {

    private View parentView;
    private TextView textView;
    @TargetApi(Build.VERSION_CODES.M)
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        parentView = View.inflate(getContext(), R.layout.layout_reading_tab,null);
        textView = (TextView) parentView.findViewById(R.id.text);
        textView.setText(getArguments().getInt("pos"));
        return parentView;
    }
}
