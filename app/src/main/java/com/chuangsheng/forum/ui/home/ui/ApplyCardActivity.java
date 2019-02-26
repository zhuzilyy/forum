package com.chuangsheng.forum.ui.home.ui;

import android.view.View;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;

import com.chuangsheng.forum.R;
import com.chuangsheng.forum.base.BaseActivity;
import com.chuangsheng.forum.ui.loan.adapter.LoanAdapter;
import com.chuangsheng.forum.ui.loan.bean.LoanInfo;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

public class ApplyCardActivity extends BaseActivity {
    @BindView(R.id.gv_loan)
    GridView gv_loan;
    @BindView(R.id.tv_title)
    TextView tv_title;
    private LoanAdapter loanAdapter;
    @Override
    protected void initViews() {
        tv_title.setText("申卡");
    }
    @Override
    protected void initData() {
        loanAdapter = new LoanAdapter(this,null);
        gv_loan.setAdapter(loanAdapter);
    }

    @Override
    protected void getResLayout() {
        setContentView(R.layout.activity_apply_card);
    }

    @Override
    protected void initListener() {

    }

    @Override
    protected void setStatusBarColor() {

    }
}
