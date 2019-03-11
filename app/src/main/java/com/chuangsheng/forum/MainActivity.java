package com.chuangsheng.forum;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.content.LocalBroadcastManager;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.chuangsheng.forum.base.BaseActivity;
import com.chuangsheng.forum.fragment.ForumFragment;
import com.chuangsheng.forum.fragment.HomeFragment;
import com.chuangsheng.forum.fragment.LoanFragment;
import com.chuangsheng.forum.fragment.MineFragment;
import com.chuangsheng.forum.ui.account.ui.LoginActivity;
import com.chuangsheng.forum.ui.forum.ui.PostForumActivity;
import com.chuangsheng.forum.util.SPUtils;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

public class MainActivity extends BaseActivity{
    private HomeFragment homeFragment;
    private ForumFragment forumFragment;
    private LoanFragment loanFragment;
    private MineFragment mineFragment;
    private FragmentManager fragmentManager;
    private Fragment currentFragment=new Fragment();
    @BindView(R.id.iv_home)
    ImageView iv_home;
    @BindView(R.id.iv_forum)
    ImageView iv_forum;
    @BindView(R.id.iv_loan)
    ImageView iv_loan;
    @BindView(R.id.iv_mine)
    ImageView iv_mine;
    @BindView(R.id.tv_home)
    TextView tv_home;
    @BindView(R.id.tv_forum)
    TextView tv_forum;
    @BindView(R.id.tv_loan)
    TextView tv_loan;
    @BindView(R.id.tv_mine)
    TextView tv_mine;
    private List<ImageView> imageViews;
    private List<TextView> textViews;
    private int index = 0;
    private int[] selectedImageRes = {R.mipmap.shouye_xuanzhong,R.mipmap.shequ_xuanzhong,
            R.mipmap.daikuan_xuanzhong,R.mipmap.wode_xuanzhong};
    private int[] unSelectedImageRes = {R.mipmap.shouye_hui,R.mipmap.shequ_hui,
            R.mipmap.daikuan_hui,R.mipmap.wode_hui};
    private BroadcastReceiver broadcastReceiver;
    private String userId;
    @Override
    protected void initViews() {
        fragmentManager = getSupportFragmentManager();
        homeFragment = new HomeFragment();
        FragmentTransaction ft=fragmentManager.beginTransaction();
        AddOrShowFra(ft,homeFragment);
        BaseActivity.activityList.add(this);
    }
    @Override
    protected void initData() {
        imageViews = new ArrayList<>();
        textViews = new ArrayList<>();
        imageViews.add(iv_home);
        imageViews.add(iv_forum);
        imageViews.add(iv_loan);
        imageViews.add(iv_mine);
        textViews.add(tv_home);
        textViews.add(tv_forum);
        textViews.add(tv_loan);
        textViews.add(tv_mine);
        initReceiver();
        userId = (String) SPUtils.get(this,"user_id","");
    }

    private void initReceiver() {
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction("com.action.showloan");

        IntentFilter intentFilterApplyCard = new IntentFilter();
        intentFilterApplyCard.addAction("com.action.applyCard");

        broadcastReceiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                FragmentTransaction fragmentTransaction=fragmentManager.beginTransaction();
                index = 2;
                setSelectImageAndTextColor();
                if(loanFragment == null){
                    loanFragment =new LoanFragment();
                }
                AddOrShowFra(fragmentTransaction,loanFragment);
            }
        };
        LocalBroadcastManager.getInstance(this).registerReceiver(broadcastReceiver,intentFilter);
        LocalBroadcastManager.getInstance(this).registerReceiver(broadcastReceiver,intentFilterApplyCard);
    }

    @Override
    protected void getResLayout() {
        setContentView(R.layout.activity_main);
    }
    @Override
    protected void initListener() {

    }
    @Override
    protected void setStatusBarColor() {

    }
    @OnClick({R.id.ll_home,R.id.ll_forum,R.id.ll_loan,R.id.ll_mine,R.id.ll_bottom})
    public void click(View view){
        FragmentTransaction fragmentTransaction=fragmentManager.beginTransaction();
        switch (view.getId()){
            case R.id.ll_home:
                index = 0;
                setSelectImageAndTextColor();
                if(homeFragment == null){
                    homeFragment =new HomeFragment();
                }
                AddOrShowFra(fragmentTransaction,homeFragment);
                break;
            case R.id.ll_forum:
                index = 1;
                setSelectImageAndTextColor();
                if(forumFragment == null){
                    forumFragment =new ForumFragment();
                }
                AddOrShowFra(fragmentTransaction,forumFragment);
                break;
            case R.id.ll_loan:
                index = 2;
                setSelectImageAndTextColor();
                if(loanFragment == null){
                    loanFragment =new LoanFragment();
                }
                AddOrShowFra(fragmentTransaction,loanFragment);
                break;
            case R.id.ll_mine:
                index = 3;
                setSelectImageAndTextColor();
                if(mineFragment == null){
                    mineFragment =new MineFragment();
                }
                AddOrShowFra(fragmentTransaction,mineFragment);
                break;
            case R.id.ll_bottom:
                if (TextUtils.isEmpty(userId)){
                    Bundle bundle = new Bundle();
                    bundle.putString("intentFrom","main");
                    jumpActivity(this, LoginActivity.class,bundle);
                    return;
                }
                jumpActivity(this, PostForumActivity.class);
                break;
        }
    }
    //设置选中的字体的颜色和图片
    private void setSelectImageAndTextColor() {
        for (int i = 0; i <textViews.size() ; i++) {
            if (i == index){
                textViews.get(i).setTextColor(Color.parseColor("#ff2e2e2e"));
                imageViews.get(i).setImageResource(selectedImageRes[i]);
            }else{
                textViews.get(i).setTextColor(Color.parseColor("#a4a4a4"));
                imageViews.get(i).setImageResource(unSelectedImageRes[i]);
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

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (broadcastReceiver!=null){
            LocalBroadcastManager.getInstance(this).unregisterReceiver(broadcastReceiver);
        }
    }
}
