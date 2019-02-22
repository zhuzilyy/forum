package com.chuangsheng.forum.ui.mine.ui;

import android.widget.ListView;
import android.widget.TextView;

import com.chuangsheng.forum.R;
import com.chuangsheng.forum.base.BaseActivity;
import com.chuangsheng.forum.ui.mine.adapter.MyReplyFroumAdapter;

import butterknife.BindView;

public class MyReplyForumActivity extends BaseActivity {
    @BindView(R.id.tv_title)
    TextView tv_title;
    @BindView(R.id.listview)
    ListView lv_forums;
    private MyReplyFroumAdapter myReplyFroumAdapter;
    @Override
    protected void initViews() {
        tv_title.setText("我的回帖");
    }
    @Override
    protected void initData() {
        myReplyFroumAdapter = new MyReplyFroumAdapter(this);
        lv_forums.setAdapter(myReplyFroumAdapter);
    }

    @Override
    protected void getResLayout() {
        setContentView(R.layout.activity_my_reply_forums);
    }

    @Override
    protected void initListener() {

    }

    @Override
    protected void setStatusBarColor() {

    }
}
