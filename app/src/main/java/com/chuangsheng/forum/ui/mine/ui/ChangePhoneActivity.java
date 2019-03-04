package com.chuangsheng.forum.ui.mine.ui;

import android.view.View;
import android.widget.TextView;

import com.chuangsheng.forum.R;
import com.chuangsheng.forum.base.BaseActivity;

import butterknife.BindView;
import butterknife.OnClick;

public class ChangePhoneActivity extends BaseActivity{
    @BindView(R.id.tv_title)
    TextView tv_title;
    @Override
    protected void initViews() {
        tv_title.setText("修改手机号");
        BaseActivity.activityList.add(this);
    }

    @Override
    protected void initData() {

    }

    @Override
    protected void getResLayout() {
        setContentView(R.layout.activity_change_phone);
    }

    @Override
    protected void initListener() {

    }

    @Override
    protected void setStatusBarColor() {

    }
    @OnClick({R.id.btn_nextStep,R.id.iv_back})
    public void click(View view){
        switch (view.getId()){
            case R.id.btn_nextStep:
                jumpActivity(this,NewPhoneActivity.class);
                break;
            case R.id.iv_back:
                finish();
                break;

        }
    }
}
