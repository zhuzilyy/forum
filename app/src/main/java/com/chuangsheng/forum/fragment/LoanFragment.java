package com.chuangsheng.forum.fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;

import com.chuangsheng.forum.R;
import com.chuangsheng.forum.base.BaseFragment;
import com.chuangsheng.forum.ui.loan.adapter.LoanAdapter;

import butterknife.BindView;

public class LoanFragment extends BaseFragment {
    @BindView(R.id.gv_loan)
    GridView gv_loan;
    @BindView(R.id.tv_title)
    TextView tv_title;
    @BindView(R.id.iv_back)
    ImageView iv_back;
    private View view_loan;
    private LoanAdapter loanAdapter;
    @Override
    protected View getResLayout(LayoutInflater inflater, ViewGroup container) {
        view_loan = inflater.inflate(R.layout.fragment_loan,null);
        return view_loan;
    }
    @Override
    protected void initViews() {
        tv_title.setText("贷款");
        iv_back.setVisibility(View.GONE);
    }
    @Override
    protected void initData() {
        loanAdapter = new LoanAdapter(getActivity());
        gv_loan.setAdapter(loanAdapter);
    }

    @Override
    protected void initListener() {

    }
}
