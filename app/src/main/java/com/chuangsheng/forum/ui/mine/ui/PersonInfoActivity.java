package com.chuangsheng.forum.ui.mine.ui;

import android.graphics.Color;
import android.view.View;
import android.widget.TextView;

import com.chuangsheng.forum.R;
import com.chuangsheng.forum.base.BaseActivity;

import butterknife.BindView;

public class PersonInfoActivity extends BaseActivity {
    @BindView(R.id.tv_title)
    TextView tv_title;
    @BindView(R.id.tv_right)
    TextView tv_right;
    @Override
    protected void initViews() {
        tv_title.setText("个人中心");
        tv_right.setText("保存");
        tv_right.setVisibility(View.VISIBLE);
        tv_right.setTextColor(Color.parseColor("#77a0fe"));
    }
    @Override
    protected void initData() {

    }

    @Override
    protected void getResLayout() {
        setContentView(R.layout.activity_person_info);
    }

    @Override
    protected void initListener() {

    }

    @Override
    protected void setStatusBarColor() {

    }
}
