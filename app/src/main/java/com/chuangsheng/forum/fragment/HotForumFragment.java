package com.chuangsheng.forum.fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.chuangsheng.forum.R;
import com.chuangsheng.forum.base.BaseFragment;
import com.chuangsheng.forum.ui.froum.adapter.CommunityAdapter;
import com.chuangsheng.forum.ui.mine.adapter.MyCollectionAdapter;

import butterknife.BindView;

public class HotForumFragment extends BaseFragment {
    private View view_hotForum;
    @BindView(R.id.listview)
    ListView lv_forums;
    private CommunityAdapter adapter;
    @Override
    protected View getResLayout(LayoutInflater inflater, ViewGroup container) {
        view_hotForum = inflater.inflate(R.layout.fragment_hot_forum,null);
        return view_hotForum;
    }

    @Override
    protected void initViews() {

    }

    @Override
    protected void initData() {
        adapter = new CommunityAdapter(getActivity());
        lv_forums.setAdapter(adapter);
    }

    @Override
    protected void initListener() {

    }
}
