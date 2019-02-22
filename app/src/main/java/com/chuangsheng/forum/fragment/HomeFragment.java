package com.chuangsheng.forum.fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Toast;

import com.chuangsheng.forum.R;
import com.chuangsheng.forum.base.BaseFragment;
import com.chuangsheng.forum.ui.froum.ui.ForumDetailActivity;
import com.chuangsheng.forum.ui.home.adapter.HomeAdapter;
import com.chuangsheng.forum.ui.home.ui.ApplyCardActivity;
import com.chuangsheng.forum.util.loader.GlideImageLoader;
import com.chuangsheng.forum.view.PullToRefreshView;
import com.youth.banner.Banner;
import com.youth.banner.BannerConfig;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

public class HomeFragment extends BaseFragment implements View.OnClickListener {
    @BindView(R.id.listview)
    ListView lv_home;
    @BindView(R.id.pulltorefreshView)
    PullToRefreshView pulltorefreshView;
    private View view_home,view_header;
    private HomeAdapter homeAdapter;
    private LinearLayout ll_applyCard;
    private Banner banner;
    @Override
    protected View getResLayout(LayoutInflater inflater, ViewGroup container) {
        view_home = inflater.inflate(R.layout.fragment_home,null);
        return view_home;
    }
    @Override
    protected void initViews() {
        view_header = LayoutInflater.from(getActivity()).inflate(R.layout.header_home,null);
        banner = view_header.findViewById(R.id.banner);
        ll_applyCard = view_header.findViewById(R.id.ll_applyCard);
        ll_applyCard.setOnClickListener(this);
    }
    @Override
    protected void initData() {
        initBanner();
        homeAdapter = new HomeAdapter(getActivity());
        lv_home.setAdapter(homeAdapter);
        lv_home.addHeaderView(view_header);

    }
    private void initBanner() {
        List<String> images = new ArrayList<>();
        images.add("http://img1.imgtn.bdimg.com/it/u=2725262009,4290107754&fm=26&gp=0.jpg");
        images.add("http://img3.imgtn.bdimg.com/it/u=3853852840,331334549&fm=26&gp=0.jpg");
        images.add("http://img0.imgtn.bdimg.com/it/u=269312192,3789766024&fm=11&gp=0.jpg");
        images.add("http://img5.imgtn.bdimg.com/it/u=575292385,3193899082&fm=26&gp=0.jpg");
        //设置banner
        banner.setBannerStyle(BannerConfig.CIRCLE_INDICATOR);//设置圆形指示器与标题
        banner.setIndicatorGravity(BannerConfig.CENTER);//设置指示器位置
        banner.setDelayTime(2000);//设置轮播时间
        banner.setImages(images);//设置图片源
        banner.setImageLoader(new GlideImageLoader());
        //banner.setBannerTitles(titles);//设置标题源
        banner.start();
    }
    @Override
    protected void initListener() {
        lv_home.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                jumpActivity(getActivity(), ForumDetailActivity.class,null);
            }
        });
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.ll_applyCard:
                jumpActivity(getActivity(), ApplyCardActivity.class,null);
                break;
        }
    }
}
