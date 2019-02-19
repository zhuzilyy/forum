package com.chuangsheng.forum.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.chuangsheng.forum.R;
import com.chuangsheng.forum.base.BaseFragment;
import com.chuangsheng.forum.ui.account.ui.LoginActivity;
import com.chuangsheng.forum.ui.account.ui.SetNameActivity;
import com.chuangsheng.forum.ui.community.ui.SearchActivity;
import com.chuangsheng.forum.ui.froum.ui.PostForumActivity;
import com.chuangsheng.forum.ui.froum.ui.ReplyForumActivity;
import com.chuangsheng.forum.ui.mine.ui.FeedBackActivity;
import com.chuangsheng.forum.ui.mine.ui.NewsActivity;
import com.chuangsheng.forum.ui.mine.ui.PersonInfoActivity;
import com.chuangsheng.forum.ui.mine.ui.SafeCenterActivity;
import com.chuangsheng.forum.ui.mine.ui.WebviewActivity;

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
    @OnClick({R.id.rl_newsCenter,R.id.rl_safeCenter,R.id.rl_personInfo,R.id.rl_feedback,
            R.id.rl_aboutUs,R.id.rl_contactUs})
    public void click(View view){
        Bundle bundle = new Bundle();
        switch (view.getId()){
            case R.id.rl_newsCenter:
                //jumpActivity(getActivity(), NewsActivity.class,null);
                jumpActivity(getActivity(), SearchActivity.class,null);
                break;
            case R.id.rl_safeCenter:
                jumpActivity(getActivity(), SafeCenterActivity.class,null);
                break;
            case R.id.rl_personInfo:
                jumpActivity(getActivity(), PersonInfoActivity.class,null);
                break;
            case R.id.rl_feedback:
                jumpActivity(getActivity(), FeedBackActivity.class,null);
                break;
            case R.id.rl_aboutUs:
                bundle.putString("title","关于我们");
                bundle.putString("url","https://www.baidu.com/baidu?tn=monline_3_dg&ie=utf-8&wd=%E7%99%BE%E5%BA%A6");
                jumpActivity(getActivity(), WebviewActivity.class,bundle);
                break;
            case R.id.rl_contactUs:
                bundle.putString("title","联系我们");
                bundle.putString("url","https://www.baidu.com/baidu?tn=monline_3_dg&ie=utf-8&wd=%E7%99%BE%E5%BA%A6");
                jumpActivity(getActivity(), WebviewActivity.class,bundle);
                break;
        }
    }
}
