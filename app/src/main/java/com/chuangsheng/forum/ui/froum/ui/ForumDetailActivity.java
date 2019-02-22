package com.chuangsheng.forum.ui.froum.ui;

import android.view.LayoutInflater;
import android.view.View;
import android.widget.ListView;

import com.chuangsheng.forum.R;
import com.chuangsheng.forum.base.BaseActivity;
import com.chuangsheng.forum.ui.froum.adapter.ForumDetailAdapter;

import butterknife.BindView;

public class ForumDetailActivity extends BaseActivity {
    @BindView(R.id.listview)
    ListView lv_forumDetail;
    private ForumDetailAdapter detailAdapter;
    private View view_header;
    @Override
    protected void initViews() {
        view_header = LayoutInflater.from(this).inflate(R.layout.header_forum_detail,null);
    }
    @Override
    protected void initData() {
        detailAdapter = new ForumDetailAdapter(this);
        lv_forumDetail.setAdapter(detailAdapter);
        lv_forumDetail.addHeaderView(view_header);
    }
    @Override
    protected void getResLayout() {
        setContentView(R.layout.activity_forum_detail);
    }

    @Override
    protected void initListener() {

    }

    @Override
    protected void setStatusBarColor() {

    }
}
