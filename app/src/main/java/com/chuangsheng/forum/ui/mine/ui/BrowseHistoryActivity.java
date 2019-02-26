package com.chuangsheng.forum.ui.mine.ui;

import android.view.View;
import android.widget.ListView;
import android.widget.TextView;

import com.chuangsheng.forum.R;
import com.chuangsheng.forum.base.BaseActivity;
import com.chuangsheng.forum.ui.mine.adapter.MyCollectionAdapter;

import butterknife.BindView;
import butterknife.OnClick;

public class BrowseHistoryActivity extends BaseActivity {
    @BindView(R.id.tv_title)
    TextView tv_title;
    @BindView(R.id.tv_right)
    TextView tv_right;
    @BindView(R.id.listview)
    ListView lv_forums;
    private MyCollectionAdapter adapter;
    @Override
    protected void initViews() {
        tv_title.setText("浏览历史");
        tv_right.setVisibility(View.VISIBLE);
        tv_right.setText("管理");
    }

    @Override
    protected void initData() {
        adapter = new MyCollectionAdapter(this);
        lv_forums.setAdapter(adapter);
    }

    @Override
    protected void getResLayout() {
        setContentView(R.layout.activity_collection);
    }

    @Override
    protected void initListener() {

    }

    @Override
    protected void setStatusBarColor() {

    }
    @OnClick({R.id.iv_back})
    public void click(View view){
        switch (view.getId()){
            case R.id.iv_back:
                finish();
                break;
        }
    }
}
