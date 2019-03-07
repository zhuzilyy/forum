package com.chuangsheng.forum.fragment;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.content.LocalBroadcastManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.chuangsheng.forum.R;
import com.chuangsheng.forum.api.ApiConstant;
import com.chuangsheng.forum.api.ApiForum;
import com.chuangsheng.forum.api.ApiHome;
import com.chuangsheng.forum.api.ApiLoan;
import com.chuangsheng.forum.base.BaseFragment;
import com.chuangsheng.forum.callback.RequestCallBack;
import com.chuangsheng.forum.dialog.CustomLoadingDialog;
import com.chuangsheng.forum.ui.forum.bean.CommunityBean;
import com.chuangsheng.forum.ui.forum.bean.CommunityInfo;
import com.chuangsheng.forum.ui.forum.ui.ForumDetailActivity;
import com.chuangsheng.forum.ui.home.adapter.HomeAdapter;
import com.chuangsheng.forum.ui.home.bean.BannerBean;
import com.chuangsheng.forum.ui.home.bean.BannerInfo;
import com.chuangsheng.forum.ui.home.bean.HomeFroumBean;
import com.chuangsheng.forum.ui.home.bean.HomeFroumInfo;
import com.chuangsheng.forum.ui.home.ui.ApplyCardActivity;
import com.chuangsheng.forum.ui.mine.ui.UserDetailActivity;
import com.chuangsheng.forum.ui.mine.ui.WebviewActivity;
import com.chuangsheng.forum.util.ToastUtils;
import com.chuangsheng.forum.util.loader.GlideImageLoader;
import com.chuangsheng.forum.view.PullToRefreshView;
import com.youth.banner.Banner;
import com.youth.banner.BannerConfig;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import okhttp3.Call;
import okhttp3.Response;

public class HomeFragment extends BaseFragment implements View.OnClickListener {
    @BindView(R.id.listview)
    ListView lv_home;
    @BindView(R.id.no_data_rl)
    RelativeLayout no_data_rl;
    @BindView(R.id.pulltorefreshView)
    PullToRefreshView pulltorefreshView;
    private View view_home,view_header;
    private HomeAdapter homeAdapter;
    private LinearLayout ll_applyCard,ll_process,ll_blackSearch,ll_huabei,ll_pos;
    private Banner banner;
    private int page =1;
    private List<HomeFroumInfo> infoList;
    private CustomLoadingDialog customLoadingDialog;
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
        ll_process = view_header.findViewById(R.id.ll_process);
        ll_process.setOnClickListener(this);
        ll_blackSearch = view_header.findViewById(R.id.ll_blackSearch);
        ll_blackSearch.setOnClickListener(this);
        ll_huabei = view_header.findViewById(R.id.ll_huabei);
        ll_huabei.setOnClickListener(this);
        ll_pos = view_header.findViewById(R.id.ll_pos);
        ll_pos.setOnClickListener(this);
       /* if (Build.VERSION.SDK_INT>=Build.VERSION_CODES.KITKAT){
            setTranslucentStatus();
        }*/
        infoList = new ArrayList<>();
        customLoadingDialog = new CustomLoadingDialog(getActivity());
        customLoadingDialog.show();
    }
    //沉浸式管理
    public void setTranslucentStatus(){
        Window window = getActivity().getWindow();
        WindowManager.LayoutParams params = window.getAttributes();
        final int status = WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS;
        params.flags |= status;
        window.setAttributes(params);
    }
    @Override
    protected void initData() {
        initBanner();
        getData();
        homeAdapter = new HomeAdapter(getActivity(),infoList,"精华");
        lv_home.setAdapter(homeAdapter);
        lv_home.addHeaderView(view_header);
        //名字点击事件
        homeAdapter.setHeadClickListener(new HomeAdapter.headClickListener() {
            @Override
            public void headClick(int position) {
                Bundle bundle = new Bundle();
                bundle.putString("user_id",infoList.get(position).getUser_id());
                jumpActivity(getActivity(), UserDetailActivity.class,bundle);
            }
        });
    }
    //获取帖子列表
    private void getData() {
        pulltorefreshView.setEnablePullTorefresh(true);
        ApiHome.getHomeFroumList(ApiConstant.HOME_FROUM_LIST, "精华", page + "", new RequestCallBack<HomeFroumBean>() {
            @Override
            public void onSuccess(Call call, Response response, HomeFroumBean homeFroumBean) {
                customLoadingDialog.dismiss();
                List<HomeFroumInfo> list = homeFroumBean.getResult().getDiscussions();
                pulltorefreshView.onHeaderRefreshComplete();
                if (homeFroumBean.getCode() == ApiConstant.SUCCESS_CODE){
                    if (list!=null && list.size()>0){
                        no_data_rl.setVisibility(View.GONE);
                        pulltorefreshView.setVisibility(View.VISIBLE);
                        infoList.addAll(list);
                        homeAdapter.notifyDataSetChanged();
                    }
                    //判断是不是没有更多数据了
                    if (list.size() < ApiConstant.PAGE_SIZE) {
                        pulltorefreshView.onFooterRefreshComplete(true);
                    }else{
                        pulltorefreshView.onFooterRefreshComplete(false);
                    }
                }
            }
            @Override
            public void onEror(Call call, int statusCode, Exception e) {
                Log.i("tag",e.getMessage());
                customLoadingDialog.dismiss();
            }
        });
    }
    private void initBanner() {
       ApiHome.getBanner(ApiConstant.BANNER, new RequestCallBack<BannerBean>() {
           @Override
           public void onSuccess(Call call, Response response, BannerBean bannerBean) {
               int code = bannerBean.getCode();
               if (code == ApiConstant.SUCCESS_CODE){
                   List<BannerInfo> imgs = bannerBean.getResult().getImgs();
                   setBannerImg(imgs);
               }
           }
           @Override
           public void onEror(Call call, int statusCode, Exception e) {
               Log.i("tag",e.getMessage());
           }
       });
    }
    //设置轮播图片
    private void setBannerImg(List<BannerInfo> imgs) {
        List<String> images = new ArrayList<>();
        for (int i = 0; i <imgs.size() ; i++) {
            images.add(imgs.get(i).getImg());
        }
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
                Bundle bundle = new Bundle();
                bundle.putString("discussionId",infoList.get(position-1).getId());
                infoList.get(position-1).setClick(Integer.parseInt(infoList.get(position-1).getClick())+1+"");
                homeAdapter.notifyDataSetChanged();
                jumpActivity(getActivity(), ForumDetailActivity.class,bundle);
            }
        });
        //刷新记载事件
        pulltorefreshView.setmOnHeaderRefreshListener(new PullToRefreshView.OnHeaderRefreshListener() {
            @Override
            public void onHeaderRefresh(PullToRefreshView view) {
                page =1;
                infoList.clear();
                getData();
            }
        });
        pulltorefreshView.setmOnFooterRefreshListener(new PullToRefreshView.OnFooterRefreshListener() {
            @Override
            public void onFooterRefresh(PullToRefreshView view) {
                loadMore();
            }
        });
    }
    //加载更多事件
    private void loadMore() {
        page++;
        pulltorefreshView.setEnablePullTorefresh(true);
        ApiHome.getHomeFroumList(ApiConstant.HOME_FROUM_LIST, "精华", page + "", new RequestCallBack<HomeFroumBean>() {
            @Override
            public void onSuccess(Call call, Response response, HomeFroumBean homeFroumBean) {
                List<HomeFroumInfo> list = homeFroumBean.getResult().getDiscussions();
                if (list!=null && list.size()>0){
                    infoList.addAll(list);
                    pulltorefreshView.onHeaderRefreshComplete();
                    homeAdapter.notifyDataSetChanged();
                    //判断是不是没有更多数据了
                    if (list.size() < ApiConstant.PAGE_SIZE) {
                        pulltorefreshView.onFooterRefreshComplete(true);
                    }else{
                        pulltorefreshView.onFooterRefreshComplete(false);
                    }
                }else{
                    //已经加载到最后一条
                    pulltorefreshView.onFooterRefreshComplete(true);
                }
            }
            @Override
            public void onEror(Call call, int statusCode, Exception e) {
                Log.i("tag",e.getMessage());
            }
        });
    }

    @Override
    public void onClick(View v) {
        Bundle bundle = new Bundle();
        switch (v.getId()){
            case R.id.ll_applyCard:
                //jumpActivity(getActivity(), ApplyCardActivity.class,null);
                Intent intent = new Intent();
                intent.setAction("com.action.applyCard");
                LocalBroadcastManager.getInstance(getActivity()).sendBroadcast(intent);
                break;
            case R.id.ll_process:
                getLinkUrl("进度查询");
                break;
            case R.id.ll_blackSearch:
                getLinkUrl("网黑查询");
                break;
            case R.id.ll_huabei:
                getLinkUrl("花呗提现");
                break;
            case R.id.ll_pos:
                getLinkUrl("官方pos");
                break;
        }
    }
    //获取链接
    private void getLinkUrl(final String keyword) {
        ApiLoan.getLoanTitle(ApiConstant.GET_SYSTEM_ATTRIBUTE, keyword, new RequestCallBack<String>() {
            @Override
            public void onSuccess(Call call, Response response, String result) {
                try {
                    JSONObject jsonObject = new JSONObject(result);
                    int code = jsonObject.getInt("error_code");
                    if (code == ApiConstant.SUCCESS_CODE){
                        Bundle bundle = new Bundle();
                        String value = jsonObject.getJSONObject("result").getString("value");
                        bundle.putString("title",keyword);
                        bundle.putString("url",value);
                        jumpActivity(getActivity(), WebviewActivity.class,bundle);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
            @Override
            public void onEror(Call call, int statusCode, Exception e) {
                Log.i("tag",e.getMessage());
            }
        });
    }
}
