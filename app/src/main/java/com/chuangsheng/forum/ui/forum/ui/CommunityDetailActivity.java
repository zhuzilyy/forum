package com.chuangsheng.forum.ui.forum.ui;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.chuangsheng.forum.R;
import com.chuangsheng.forum.base.BaseActivity;
import com.chuangsheng.forum.fragment.GoodForumFragment;
import com.chuangsheng.forum.fragment.HotForumFragment;
import com.chuangsheng.forum.fragment.NewestForumFragment;
import com.chuangsheng.forum.ui.community.ui.SearchActivity;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

public class CommunityDetailActivity extends BaseActivity {
    private NewestForumFragment newestForumFragment;
    private HotForumFragment hotForumFragment;
    private GoodForumFragment goodForumFragment;
    private FragmentManager fragmentManager;
    private Fragment currentFragment=new Fragment();
    @BindView(R.id.tv_newest)
    TextView tv_newest;
    @BindView(R.id.tv_hot)
    TextView tv_hot;
    @BindView(R.id.tv_good)
    TextView tv_good;
    @BindView(R.id.iv_newest)
    View iv_newest;
    @BindView(R.id.iv_hot)
    View iv_hot;
    @BindView(R.id.iv_good)
    View iv_good;
    @BindView(R.id.iv_community)
    ImageView iv_community;
    @BindView(R.id.tv_titleName)
    TextView tv_title;
    @BindView(R.id.tv_comment)
    TextView tv_comment;
    @BindView(R.id.tv_desc)
    TextView tv_desc;
    @BindView(R.id.tv_top)
    TextView tv_top;
    @BindView(R.id.tv_fire)
    TextView tv_fire;
    @BindView(R.id.tv_subject)
    TextView tv_subject;
    @BindView(R.id.tv_title)
    TextView tv_titleDetail;
    @BindView(R.id.ll_fire)
    LinearLayout ll_fire;
    @BindView(R.id.ll_top)
    LinearLayout ll_top;
    @BindView(R.id.iv_right)
    ImageView iv_right;
    private List<View> viewLines;
    private List<TextView> textViews;
    private int index = 0;
    private String fire_discussion_id,top_discussion_id,link,ad;
    @Override
    protected void initViews() {
        fragmentManager = getSupportFragmentManager();
        newestForumFragment = new NewestForumFragment();
        FragmentTransaction ft=fragmentManager.beginTransaction();
        AddOrShowFra(ft,newestForumFragment);
        tv_titleDetail.setText("详情");
        BaseActivity.activityList.add(this);
        iv_right.setVisibility(View.VISIBLE);
        iv_right.setImageResource(R.mipmap.sousuo);
    }
    @Override
    protected void initData() {
        viewLines = new ArrayList<>();
        textViews = new ArrayList<>();
        viewLines.add(iv_newest);
        viewLines.add(iv_hot);
        viewLines.add(iv_good);
        textViews.add(tv_newest);
        textViews.add(tv_hot);
        textViews.add(tv_good);
        //初始化头部数据
        initHeadData();

    }
    private void initHeadData() {
        Intent intent = getIntent();
        if (intent!=null){
            Bundle extras = intent.getExtras();
            String img = extras.getString("img");
            String subject = extras.getString("subject");
            String comment = extras.getString("comment");
            String discussion = extras.getString("discussion");
            String desc = extras.getString("desc");
            String name = extras.getString("name");
            link = extras.getString("link");
            fire_discussion_id = extras.getString("fire_discussion_id");
            String fire_subject = extras.getString("fire_subject");
            top_discussion_id = extras.getString("top_discussion_id");
            String top_subject = extras.getString("top_subject");
            ad = extras.getString("ad");
            Glide.with(CommunityDetailActivity.this).load(img).into(iv_community);
            tv_title.setText(name);
            tv_subject.setText(subject);
            tv_comment.setText("主题:"+discussion+"  帖子:"+comment);
            tv_desc.setText(desc);
            tv_top.setText(top_subject);
            tv_fire.setText(fire_subject);
            if (TextUtils.isEmpty(fire_subject)){
                ll_fire.setVisibility(View.GONE);
            }
            if (TextUtils.isEmpty(top_subject)){
                ll_top.setVisibility(View.GONE);
            }
        }
    }
    @Override
    protected void getResLayout() {
        setContentView(R.layout.activity_community_detail);
    }

    @Override
    protected void initListener() {

    }

    @Override
    protected void setStatusBarColor() {

    }
    @OnClick({R.id.ll_newest,R.id.ll_hot,R.id.ll_good,R.id.iv_right,R.id.iv_fatie,R.id.iv_back})
    public void click(View view){
        FragmentTransaction fragmentTransaction=fragmentManager.beginTransaction();
        switch (view.getId()){
            case R.id.ll_newest:
                index = 0;
                setSelectImageAndTextColor();
                if(newestForumFragment == null){
                    newestForumFragment =new NewestForumFragment();
                }
                AddOrShowFra(fragmentTransaction,newestForumFragment);
                break;
            case R.id.ll_hot:
                index = 1;
                setSelectImageAndTextColor();
                if(hotForumFragment == null){
                    hotForumFragment =new HotForumFragment();
                }
                AddOrShowFra(fragmentTransaction,hotForumFragment);
                break;
            case R.id.ll_good:
                index = 2;
                setSelectImageAndTextColor();
                if(goodForumFragment == null){
                    goodForumFragment =new GoodForumFragment();
                }
                AddOrShowFra(fragmentTransaction,goodForumFragment);
                break;
            case R.id.iv_right:
                jumpActivity(CommunityDetailActivity.this, SearchActivity.class);
                break;
            case R.id.iv_fatie:
                jumpActivity(CommunityDetailActivity.this,PostForumActivity.class);
                break;
            case R.id.iv_back:
                finish();
                break;
        }
    }
    //设置选中的字体的颜色和图片
    private void setSelectImageAndTextColor() {
        for (int i = 0; i <textViews.size() ; i++) {
            if (i == index){
                textViews.get(i).setTextColor(Color.parseColor("#ff000000"));
                viewLines.get(i).setVisibility(View.VISIBLE);
            }else{
                textViews.get(i).setTextColor(Color.parseColor("#919191"));
                viewLines.get(i).setVisibility(View.INVISIBLE);
            }
        }
    }
    /***
     * 显示隐藏Fragment
     *
     * @param ft
     * @param fragment
     */
    private void AddOrShowFra(FragmentTransaction ft, Fragment fragment) {
        if (currentFragment == fragment) {
            return;
        }
        if (!fragment.isAdded()) {
            ft.hide(currentFragment).add(R.id.main_switch, fragment).commitAllowingStateLoss();

        } else {
            ft.hide(currentFragment).show(fragment).commitAllowingStateLoss();

        }
        currentFragment = fragment;
    }
}
