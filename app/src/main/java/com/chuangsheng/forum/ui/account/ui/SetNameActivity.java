package com.chuangsheng.forum.ui.account.ui;

import android.widget.TextView;

import com.chuangsheng.forum.R;
import com.chuangsheng.forum.base.BaseActivity;

import butterknife.BindView;

public class SetNameActivity extends BaseActivity {
    @BindView(R.id.tv_title)
    TextView tv_title;
    @Override
    protected void initViews() {
        tv_title.setText("设置");
    }

    @Override
    protected void initData() {

    }
    @Override
    protected void getResLayout() {
        setContentView(R.layout.activity_set_name);
    }

    @Override
    protected void initListener() {

    }

    @Override
    protected void setStatusBarColor() {

    }
}
