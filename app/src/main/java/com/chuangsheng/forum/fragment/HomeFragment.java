package com.chuangsheng.forum.fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.chuangsheng.forum.R;
import com.chuangsheng.forum.base.BaseFragment;

public class HomeFragment extends BaseFragment {
    private View view_home;
    @Override
    protected View getResLayout(LayoutInflater inflater, ViewGroup container) {
        view_home = inflater.inflate(R.layout.fragment_home,null);
        return view_home;
    }

    @Override
    protected void initViews() {

    }

    @Override
    protected void initData() {

    }

    @Override
    protected void initListener() {

    }
}
