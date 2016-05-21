package com.mecuryli.xianxia.ui.reading;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.mecuryli.xianxia.R;
import com.mecuryli.xianxia.api.ReadingApi;

/**
 * Created by 海飞 on 2016/5/15.
 */
public class ReadingTabFragment extends android.support.v4.app.Fragment {

    private View parentView;
    private TextView textView;
    private int pos;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        parentView = View.inflate(getContext(), R.layout.layout_reading_tab,null);
        initData();
        return parentView;
    }

    public void initData(){
        pos = getArguments().getInt(getString(R.string.id_pos));
        textView = (TextView) parentView.findViewById(R.id.text);
        textView.setText(ReadingApi.getBookInfo(pos, ReadingDetailActivity.bookBean));
    }
}












