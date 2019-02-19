package com.chuangsheng.forum.ui.mine.ui;

import android.view.View;
import android.widget.TextView;

import com.chuangsheng.forum.R;
import com.chuangsheng.forum.base.BaseActivity;
import com.chuangsheng.forum.ui.account.ui.FindByEmailActivity;
import com.chuangsheng.forum.ui.account.ui.SetNameActivity;

import java.util.Set;

import butterknife.BindView;
import butterknife.OnClick;

public class BindEmailActivity extends BaseActivity {
    @BindView(R.id.tv_title)
    TextView tv_title;
    @Override
    protected void initViews() {
        tv_title.setText("绑定邮箱");
    }

    @Override
    protected void initData() {

    }
    @Override
    protected void getResLayout() {
        setContentView(R.layout.activity_bind_email);
    }

    @Override
    protected void initListener() {

    }

    @Override
    protected void setStatusBarColor() {

    }
    @OnClick({R.id.btn_nextStep})
    public void click(View view){
        switch (view.getId()){
            case R.id.btn_nextStep:
                jumpActivity(this,SetNameActivity.class);
                break;


        }
    }
}
