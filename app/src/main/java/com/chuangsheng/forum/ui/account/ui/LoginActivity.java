package com.chuangsheng.forum.ui.account.ui;

import android.view.View;

import com.chuangsheng.forum.R;
import com.chuangsheng.forum.base.BaseActivity;
import com.chuangsheng.forum.ui.mine.ui.BindEmailActivity;

import butterknife.OnClick;

public class LoginActivity extends BaseActivity {
    @Override
    protected void initViews() {

    }

    @Override
    protected void initData() {

    }

    @Override
    protected void getResLayout() {
        setContentView(R.layout.activity_login);
    }

    @Override
    protected void initListener() {

    }

    @Override
    protected void setStatusBarColor() {

    }
    @OnClick({R.id.ll_findPwd,R.id.btn_login})
    public void click(View view){
        switch (view.getId()){
            case R.id.ll_findPwd:
                jumpActivity(this,FindByEmailActivity.class);
                break;
            case R.id.btn_login:
                jumpActivity(this,BindEmailActivity.class);
                break;

        }
    }



}
