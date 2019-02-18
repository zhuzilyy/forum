package com.chuangsheng.forum;

import android.graphics.Color;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.chuangsheng.forum.base.BaseActivity;
import com.chuangsheng.forum.fragment.ForumFragment;
import com.chuangsheng.forum.fragment.HomeFragment;
import com.chuangsheng.forum.fragment.LoanFragment;
import com.chuangsheng.forum.fragment.MineFragment;

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
    @Override
    protected void initViews() {
        fragmentManager = getSupportFragmentManager();
        homeFragment = new HomeFragment();
        FragmentTransaction ft=fragmentManager.beginTransaction();
        AddOrShowFra(ft,homeFragment);
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
    @OnClick({R.id.ll_home,R.id.ll_forum,R.id.ll_loan,R.id.ll_mine})
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
        }
    }
    //设置选中的字体的颜色和图片
    private void setSelectImageAndTextColor() {
        for (int i = 0; i <textViews.size() ; i++) {
            if (i == index){
                textViews.get(i).setTextColor(Color.parseColor("#77a0fe"));
            }else{
                textViews.get(i).setTextColor(Color.parseColor("#a4a4a4"));
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
