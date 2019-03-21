package com.chuangsheng.forum.ui.forum.ui;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.text.TextUtils;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewTreeObserver;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.chuangsheng.forum.R;
import com.chuangsheng.forum.base.BaseActivity;
import com.chuangsheng.forum.fragment.GoodForumFragment;
import com.chuangsheng.forum.fragment.HotForumFragment;
import com.chuangsheng.forum.fragment.NewestForumFragment;
import com.chuangsheng.forum.ui.community.ui.SearchActivity;
import com.chuangsheng.forum.ui.forum.views.AnchorView;
import com.chuangsheng.forum.ui.forum.views.CustomScrollView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by tianou on 2019/3/21.
 */

public class NewCommunityDetailActivity extends BaseActivity implements View.OnClickListener {
    /**
     * 占位tablayout，用于滑动过程中去确定实际的tablayout的位置
     */
    private TabLayout holderTabLayout;
    /**
     * 实际操作的tablayout，
     */
    private TabLayout realTabLayout;
    private CustomScrollView scrollView;
    private FrameLayout container;
    private String[] tabTxt = {"最新帖", "热门贴", "精华贴"};

    private List<AnchorView> anchorList = new ArrayList<>();

    //判读是否是scrollview主动引起的滑动，true-是，false-否，由tablayout引起的
    private boolean isScroll;
    //记录上一次位置，防止在同一内容块里滑动 重复定位到tablayout
    private int lastPos = 0;
    //监听判断最后一个模块的高度，不满一屏时让最后一个模块撑满屏幕
    private ViewTreeObserver.OnGlobalLayoutListener listener;

    //*******薛金柱**********
    private NewestForumFragment newestForumFragment;
    private HotForumFragment hotForumFragment;
    private GoodForumFragment goodForumFragment;
    private FragmentManager fragmentManager;
    private Fragment currentFragment=new Fragment();
  /*  @BindView(R.id.tv_newest)
    TextView tv_newest;*/
  /*  @BindView(R.id.tv_hot)
    TextView tv_hot;
    @BindView(R.id.tv_good)
    TextView tv_good;*/
/*    @BindView(R.id.iv_newest)
    View iv_newest;
    @BindView(R.id.iv_hot)
    View iv_hot;
    @BindView(R.id.iv_good)
    View iv_good;*/
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
    private String fire_discussion_id,top_discussion_id,link,ad,communityId;



    @Override
    protected void initViews() {

        holderTabLayout = findViewById(R.id.tablayout_holder);
        //holderTabLayout.setTabGravity(TabLayout.GRAVITY_CENTER);

        realTabLayout = findViewById(R.id.tablayout_real);
       // realTabLayout.setTabGravity(TabLayout.GRAVITY_CENTER);
        scrollView = findViewById(R.id.scrollView);
        container = findViewById(R.id.container);

        scrollView.smoothScrollTo(0, 0);
        for (int i = 0; i < tabTxt.length; i++) {
            holderTabLayout.addTab(holderTabLayout.newTab().setText(tabTxt[i]));
            realTabLayout.addTab(realTabLayout.newTab().setText(tabTxt[i]));
        }


        listener = new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                //计算让最后一个view高度撑满屏幕
                int screenH = getScreenHeight();
                int statusBarH = getStatusBarHeight(NewCommunityDetailActivity.this);
                int tabH = holderTabLayout.getHeight();
                int lastH = screenH - statusBarH - tabH - 16 * 3;
               /* AnchorView anchorView = anchorList.get(anchorList.size() - 1);
                if (anchorView.getHeight() < lastH) {
                    LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
                    params.height = lastH;
                    anchorView.setLayoutParams(params);
                }*/

                //一开始让实际的tablayout 移动到 占位的tablayout处，覆盖占位的tablayout
                realTabLayout.setTranslationY(holderTabLayout.getTop());
                realTabLayout.setVisibility(View.VISIBLE);
                container.getViewTreeObserver().removeOnGlobalLayoutListener(listener);

            }
        };
        container.getViewTreeObserver().addOnGlobalLayoutListener(listener);


        scrollView.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (event.getAction() == MotionEvent.ACTION_DOWN) {
                    isScroll = true;
                }
                return false;
            }
        });

        //监听scrollview滑动
        scrollView.setCallbacks(new CustomScrollView.Callbacks() {
            @Override
            public void onScrollChanged(int x, int y, int oldx, int oldy) {
                //根据滑动的距离y(不断变化的) 和 holderTabLayout距离父布局顶部的距离(这个距离是固定的)对比，
                //当y < holderTabLayout.getTop()时，holderTabLayout 仍在屏幕内，realTabLayout不断移动holderTabLayout.getTop()距离，覆盖holderTabLayout
                //当y > holderTabLayout.getTop()时，holderTabLayout 移出，realTabLayout不断移动y，相对的停留在顶部，看上去是静止的
                int translation = Math.max(y, holderTabLayout.getTop());
                realTabLayout.setTranslationY(translation);



            }
        });

        //实际的tablayout的点击切换
        realTabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {

                FragmentTransaction fragmentTransaction=fragmentManager.beginTransaction();
               switch (tab.getPosition()){

                   case 0:
                       index = 0;
                       setSelectImageAndTextColor();
                       if(newestForumFragment == null){
                           newestForumFragment =new NewestForumFragment();
                       }
                       AddOrShowFra(fragmentTransaction,newestForumFragment);
                       break;
                   case 1:
                       index = 1;
                       setSelectImageAndTextColor();
                       if(hotForumFragment == null){
                           hotForumFragment =new HotForumFragment();
                       }
                       AddOrShowFra(fragmentTransaction,hotForumFragment);

                       break;
                   case 2:
                       index = 2;
                       setSelectImageAndTextColor();
                       if(goodForumFragment == null){
                           goodForumFragment =new GoodForumFragment();
                       }
                       AddOrShowFra(fragmentTransaction,goodForumFragment);
                       break;
               }

            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
        //******薛金柱***********************
        fragmentManager = getSupportFragmentManager();
        newestForumFragment = new NewestForumFragment();
        FragmentTransaction ft=fragmentManager.beginTransaction();
        AddOrShowFra(ft,newestForumFragment);
        BaseActivity.activityList.add(this);
        iv_right.setVisibility(View.VISIBLE);
        iv_right.setImageResource(R.mipmap.sousuo);


    }

    @Override
    protected void initData() {
        viewLines = new ArrayList<>();
        textViews = new ArrayList<>();
      /*  viewLines.add(iv_newest);
        viewLines.add(iv_hot);
        viewLines.add(iv_good);*/
       // textViews.add(tv_newest);
      /*  textViews.add(tv_hot);
        textViews.add(tv_good);*/
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
            communityId = extras.getString("communityId");
            link = extras.getString("link");
            tv_titleDetail.setText(name);
            fire_discussion_id = extras.getString("fire_discussion_id");
            String fire_subject = extras.getString("fire_subject");
            top_discussion_id = extras.getString("top_discussion_id");
            String top_subject = extras.getString("top_subject");
            ad = extras.getString("ad");
            Glide.with(NewCommunityDetailActivity.this).load(img).into(iv_community);
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
        ll_fire.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle bundle = new Bundle();
                bundle.putString("discussionId",fire_discussion_id);
                jumpActivity(NewCommunityDetailActivity.this, ForumDetailActivity.class,bundle);
            }
        });
        ll_top.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle bundle = new Bundle();
                bundle.putString("discussionId",top_discussion_id);
                jumpActivity(NewCommunityDetailActivity.this, ForumDetailActivity.class,bundle);
            }
        });
    }

    @Override
    protected void getResLayout() {
        setContentView(R.layout.activity_newforum_details);
        Log.i("ss","");

    }

    @Override
    protected void initListener() {

    }

    @Override
    protected void setStatusBarColor() {

    }


    private void setScrollPos(int newPos) {
        if (lastPos != newPos) {
            realTabLayout.setScrollPosition(newPos, 0, true);
        }
        lastPos = newPos;
    }

    private int getScreenHeight() {
        return getResources().getDisplayMetrics().heightPixels;
    }

    public int getStatusBarHeight(Context context) {
        int result = 0;
        int resourceId = context.getResources()
                .getIdentifier("status_bar_height", "dimen", "android");
        if (resourceId > 0) {
            result = context.getResources().getDimensionPixelSize(resourceId);
        }
        return result;
    }

    //跳转事件
    public void jumpActivity(Context context,Class<?> clazz,Bundle bundle){
        Intent intent = new Intent(context,clazz);
        if (bundle!=null){
            intent.putExtras(bundle);
        }
        startActivity(intent);
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
           // ft.hide(currentFragment).add(R.id.main_switch, fragment).commitAllowingStateLoss();
            ft.hide(currentFragment).add(R.id.container, fragment).commitAllowingStateLoss();
        } else {
            ft.hide(currentFragment).show(fragment).commitAllowingStateLoss();

        }
        currentFragment = fragment;
    }
    @OnClick({R.id.iv_right,R.id.iv_fatie,R.id.iv_back})
    @Override
    public void onClick(View v) {
        switch (v.getId()){

            case R.id.iv_right:
                jumpActivity(NewCommunityDetailActivity.this, SearchActivity.class);
                break;
            case R.id.iv_fatie:
                Bundle bundle = new Bundle();
                bundle.putString("communityId",communityId);
                jumpActivity(NewCommunityDetailActivity.this,PostForumActivity.class,bundle);
                break;
            case R.id.iv_back:
                finish();
                break;
    }}
}
