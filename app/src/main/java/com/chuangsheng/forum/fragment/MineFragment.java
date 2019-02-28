package com.chuangsheng.forum.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.chuangsheng.forum.R;
import com.chuangsheng.forum.base.BaseFragment;
import com.chuangsheng.forum.ui.account.ui.LoginActivity;
import com.chuangsheng.forum.ui.account.ui.SetNameActivity;
import com.chuangsheng.forum.ui.community.ui.SearchActivity;
import com.chuangsheng.forum.ui.forum.ui.PostForumActivity;
import com.chuangsheng.forum.ui.forum.ui.ReplyForumActivity;
import com.chuangsheng.forum.ui.mine.ui.BrowseHistoryActivity;
import com.chuangsheng.forum.ui.mine.ui.CollectionActivity;
import com.chuangsheng.forum.ui.mine.ui.FeedBackActivity;
import com.chuangsheng.forum.ui.mine.ui.MyFroumsActivity;
import com.chuangsheng.forum.ui.mine.ui.MyReplyForumActivity;
import com.chuangsheng.forum.ui.mine.ui.NewsActivity;
import com.chuangsheng.forum.ui.mine.ui.PersonInfoActivity;
import com.chuangsheng.forum.ui.mine.ui.SafeCenterActivity;
import com.chuangsheng.forum.ui.mine.ui.WebviewActivity;
import com.chuangsheng.forum.util.SPUtils;
import com.chuangsheng.forum.view.CircleImageView;

import butterknife.BindView;
import butterknife.OnClick;

public class MineFragment extends BaseFragment {
    @BindView(R.id.iv_right)
    ImageView iv_right;
    @BindView(R.id.tv_title)
    TextView tv_title;
    @BindView(R.id.iv_back)
    ImageView iv_back;
    @BindView(R.id.iv_head)
    CircleImageView iv_head;
    @BindView(R.id.tv_name)
    TextView tv_name;
    private View view_mine;
    private static final int REQUEST_CODE = 100;
    @Override
    protected View getResLayout(LayoutInflater inflater, ViewGroup container) {
        view_mine = inflater.inflate(R.layout.fragment_mine,null);
        return view_mine;
    }
    @Override
    protected void initViews() {
       /* iv_right.setVisibility(View.VISIBLE);
        iv_right.setImageResource(R.mipmap.shezhi);*/
        tv_title.setText("我的");
        iv_back.setVisibility(View.GONE);
    }

    @Override
    protected void initData() {
        //设置个人资料
        setValue();
    }
    private void setValue() {
        String username = (String) SPUtils.get(getActivity(), "username", "");
        String headAvatar = (String) SPUtils.get(getActivity(), "headAvatar", "");
        tv_name.setText(username);
        Glide.with(getActivity()).load(headAvatar).into(iv_head);
    }
    @Override
    protected void initListener() {

    }
    @OnClick({R.id.rl_newsCenter,R.id.rl_safeCenter,R.id.rl_personInfo,R.id.rl_feedback,
            R.id.rl_aboutUs,R.id.rl_contactUs,R.id.rl_myFroums,R.id.rl_myReplyForms,
            R.id.rl_collection,R.id.rl_history})
    public void click(View view){
        Bundle bundle = new Bundle();
        switch (view.getId()){
            case R.id.rl_newsCenter:
                jumpActivity(getActivity(), NewsActivity.class,null);
                //jumpActivity(getActivity(), MyFroumsActivity.class,null);
                break;
            case R.id.rl_safeCenter:
                jumpActivity(getActivity(), SafeCenterActivity.class,null);
                break;
            case R.id.rl_personInfo:
                Intent intent = new Intent(getActivity(), PersonInfoActivity.class);
                startActivityForResult(intent,REQUEST_CODE);
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
            case R.id.rl_myFroums:
                jumpActivity(getActivity(), MyFroumsActivity.class,null);
                break;
            case R.id.rl_myReplyForms:
                jumpActivity(getActivity(), MyReplyForumActivity.class,null);
                break;
            case R.id.rl_collection:
                jumpActivity(getActivity(), CollectionActivity.class,null);
                break;
            case R.id.rl_history:
                jumpActivity(getActivity(), BrowseHistoryActivity.class,null);
                break;
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == getActivity().RESULT_OK){
            if (requestCode == REQUEST_CODE){
                setValue();
            }
        }
    }
}
