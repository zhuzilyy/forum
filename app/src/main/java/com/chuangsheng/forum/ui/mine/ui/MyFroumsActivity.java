package com.chuangsheng.forum.ui.mine.ui;

import android.view.View;
import android.widget.ListView;
import android.widget.TextView;

import com.chuangsheng.forum.R;
import com.chuangsheng.forum.base.BaseActivity;
import com.chuangsheng.forum.ui.mine.adapter.MyFroumAdapter;

import butterknife.BindView;

public class MyFroumsActivity extends BaseActivity {
    @BindView(R.id.tv_title)
    TextView tv_title;
    @BindView(R.id.tv_right)
    TextView tv_right;
    @BindView(R.id.listview)
    ListView lv_forums;
    private MyFroumAdapter adapter;
    @Override
    protected void initViews() {
        tv_title.setText("我的帖子");
        tv_right.setVisibility(View.VISIBLE);
        tv_right.setText("管理");
    }
    @Override
    protected void initData() {
        adapter = new MyFroumAdapter(this);
        lv_forums.setAdapter(adapter);
    }
    @Override
    protected void getResLayout() {
        setContentView(R.layout.activity_my_froums);
    }

    @Override
    protected void initListener() {

    }

    @Override
    protected void setStatusBarColor() {

    }
}
