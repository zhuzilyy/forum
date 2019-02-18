package com.chuangsheng.forum.fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.chuangsheng.forum.R;
import com.chuangsheng.forum.base.BaseFragment;
import com.chuangsheng.forum.ui.mine.ui.NewsActivity;

import butterknife.BindView;
import butterknife.OnClick;

public class MineFragment extends BaseFragment {
    @BindView(R.id.iv_right)
    ImageView iv_right;
    @BindView(R.id.tv_title)
    TextView tv_title;
    private View view_mine;
    @Override
    protected View getResLayout(LayoutInflater inflater, ViewGroup container) {
        view_mine = inflater.inflate(R.layout.fragment_mine,null);
        return view_mine;
    }
    @Override
    protected void initViews() {
        iv_right.setVisibility(View.VISIBLE);
        iv_right.setImageResource(R.mipmap.shezhi);
        tv_title.setText("我的");
    }

    @Override
    protected void initData() {

    }

    @Override
    protected void initListener() {

    }
    @OnClick({R.id.ll_newsCenter})
    public void click(View view){
        switch (view.getId()){
            case R.id.ll_newsCenter:
                jumpActivity(getActivity(), NewsActivity.class,null);
                break;
        }
    }
}
