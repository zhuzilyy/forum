package com.chuangsheng.forum.fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.chuangsheng.forum.R;
import com.chuangsheng.forum.base.BaseFragment;
import com.chuangsheng.forum.ui.froum.adapter.FroumAdapter;

import butterknife.BindView;

public class ForumFragment extends BaseFragment {
    @BindView(R.id.listview)
    ListView lv_froum;
    @BindView(R.id.tv_title)
    TextView tv_title;
    @BindView(R.id.iv_back)
    ImageView iv_back;
    private View view_froum;
    private FroumAdapter adapter;
    @Override
    protected View getResLayout(LayoutInflater inflater, ViewGroup container) {
        view_froum = inflater.inflate(R.layout.fragment_forum,null);
        return view_froum;
    }
    @Override
    protected void initViews() {
        tv_title.setText("社区");
        iv_back.setVisibility(View.GONE);
    }
    @Override
    protected void initData() {
        adapter = new FroumAdapter(getActivity());
        lv_froum.setAdapter(adapter);
    }
    @Override
    protected void initListener() {

    }
}
