package com.chuangsheng.forum.fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.chuangsheng.forum.R;
import com.chuangsheng.forum.base.BaseFragment;

public class LoanFragment extends BaseFragment {
    private View view_loan;
    @Override
    protected View getResLayout(LayoutInflater inflater, ViewGroup container) {
        view_loan = inflater.inflate(R.layout.fragment_loan,null);
        return view_loan;
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
