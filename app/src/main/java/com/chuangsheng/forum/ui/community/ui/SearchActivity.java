package com.chuangsheng.forum.ui.community.ui;


import android.widget.Toast;

import com.chuangsheng.forum.R;
import com.chuangsheng.forum.base.BaseActivity;
import com.chuangsheng.forum.view.FlowLayout;

import butterknife.BindView;

public class SearchActivity extends BaseActivity {
    @BindView(R.id.flowLayout_hot)
    FlowLayout  flowLayout_hot;
    @Override
    protected void initViews() {
    }
    @Override
    protected void initData() {

    }
    @Override
    protected void getResLayout() {
        setContentView(R.layout.activity_community);
    }
    @Override
    protected void initListener() {
        flowLayout_hot.setChildClickListener(new FlowLayout.ChlidClickListener() {
            @Override
            public void click(String content) {
                Toast.makeText(SearchActivity.this, content, Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    protected void setStatusBarColor() {

    }
}
