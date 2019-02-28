package com.chuangsheng.forum.ui.forum.ui;

import android.graphics.Color;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.chuangsheng.forum.R;
import com.chuangsheng.forum.base.BaseActivity;
import com.chuangsheng.forum.fragment.GoodForumFragment;
import com.chuangsheng.forum.fragment.HotForumFragment;
import com.chuangsheng.forum.fragment.NewestForumFragment;

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
    private List<View> viewLines;
    private List<TextView> textViews;
    private int index = 0;
    @Override
    protected void initViews() {
        fragmentManager = getSupportFragmentManager();
        newestForumFragment = new NewestForumFragment();
        FragmentTransaction ft=fragmentManager.beginTransaction();
        AddOrShowFra(ft,newestForumFragment);
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
    @OnClick({R.id.ll_newest,R.id.ll_hot,R.id.ll_good})
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
