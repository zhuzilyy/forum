package com.chuangsheng.forum.ui.mine.ui;

import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.TextView;

import com.chuangsheng.forum.R;
import com.chuangsheng.forum.base.BaseActivity;
import com.chuangsheng.forum.fragment.CommonNewsFragment;
import com.chuangsheng.forum.fragment.SystemNewsFragment;
import com.chuangsheng.forum.ui.mine.adapter.MyPageAdapter;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.OnClick;

public class NewsActivity extends BaseActivity {
    @BindView(R.id.tab)
    TabLayout tab;
    @BindView(R.id.tv_title)
    TextView tv_title;
    @BindView(R.id.viewpager)
    ViewPager viewpager;
    public MyPageAdapter myPageAdapter;
    @Override
    protected void initViews() {
        myPageAdapter = new MyPageAdapter(getSupportFragmentManager());
        ArrayList<Fragment> datas = new ArrayList<Fragment>();
        datas.add(new CommonNewsFragment());
        datas.add(new SystemNewsFragment());
        myPageAdapter.setData(datas);
        ArrayList<String> titles = new ArrayList<String>();
        titles.add("消息");
        titles.add("系统");
        myPageAdapter.setTitles(titles);
        viewpager.setAdapter(myPageAdapter);
        // 将ViewPager与TabLayout相关联
        tab.setupWithViewPager(viewpager);
        tv_title.setText("消息中心");
        BaseActivity.activityList.add(this);
    }
    @Override
    protected void initData() {

    }
    @Override
    protected void getResLayout() {
        setContentView(R.layout.activity_news);
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
