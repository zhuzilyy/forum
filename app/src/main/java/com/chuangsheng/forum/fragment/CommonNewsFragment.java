package com.chuangsheng.forum.fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.chuangsheng.forum.R;
import com.chuangsheng.forum.base.BaseFragment;
import com.chuangsheng.forum.ui.mine.adapter.CommonNewsAdapter;
import com.chuangsheng.forum.ui.mine.adapter.SystemNewsAdapter;

import butterknife.BindView;

public class CommonNewsFragment extends BaseFragment {
    @BindView(R.id.listview)
    ListView lv_news;
    private CommonNewsAdapter commonNewsAdapter;
    private View view_systemNews;
    private View view_commonNews;
    @Override
    protected View getResLayout(LayoutInflater inflater, ViewGroup container) {
        view_commonNews = inflater.inflate(R.layout.fragment_common_news,null);
        return view_commonNews;
    }

    @Override
    protected void initViews() {
        commonNewsAdapter = new CommonNewsAdapter(getActivity());
        lv_news.setAdapter(commonNewsAdapter);
    }

    @Override
    protected void initData() {

    }

    @Override
    protected void initListener() {

    }
}
