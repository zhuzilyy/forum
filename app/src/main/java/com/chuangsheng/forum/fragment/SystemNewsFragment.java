package com.chuangsheng.forum.fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Adapter;
import android.widget.ListView;

import com.chuangsheng.forum.R;
import com.chuangsheng.forum.base.BaseFragment;
import com.chuangsheng.forum.ui.mine.adapter.SystemNewsAdapter;

import butterknife.BindView;

public class SystemNewsFragment extends BaseFragment {
    @BindView(R.id.listview)
    ListView lv_news;
    private SystemNewsAdapter systemNewsAdapter;
    private View view_systemNews;
    @Override
    protected View getResLayout(LayoutInflater inflater, ViewGroup container) {
        view_systemNews = inflater.inflate(R.layout.fragment_system_news,null);
        return view_systemNews;
    }

    @Override
    protected void initViews() {
        systemNewsAdapter = new SystemNewsAdapter(getActivity());
        lv_news.setAdapter(systemNewsAdapter);
    }

    @Override
    protected void initData() {

    }

    @Override
    protected void initListener() {

    }
}
