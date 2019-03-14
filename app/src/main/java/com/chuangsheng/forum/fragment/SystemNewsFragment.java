package com.chuangsheng.forum.fragment;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.RelativeLayout;

import com.chuangsheng.forum.R;
import com.chuangsheng.forum.api.ApiConstant;
import com.chuangsheng.forum.api.ApiMine;
import com.chuangsheng.forum.base.BaseFragment;
import com.chuangsheng.forum.callback.RequestCallBack;
import com.chuangsheng.forum.dialog.CustomLoadingDialog;
import com.chuangsheng.forum.ui.forum.ui.ForumDetailActivity;
import com.chuangsheng.forum.ui.mine.adapter.SystemNewsAdapter;
import com.chuangsheng.forum.ui.mine.bean.CommonNewsBean;
import com.chuangsheng.forum.ui.mine.bean.CommonNewsInfo;
import com.chuangsheng.forum.ui.mine.bean.SystemNewsBean;
import com.chuangsheng.forum.ui.mine.bean.SystemNewsInfo;
import com.chuangsheng.forum.ui.mine.ui.SystemDetailActivity;
import com.chuangsheng.forum.view.PullToRefreshView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import okhttp3.Call;
import okhttp3.Response;

public class SystemNewsFragment extends BaseFragment {
    @BindView(R.id.listview)
    ListView lv_news;
    @BindView(R.id.no_data_rl)
    RelativeLayout no_data_rl;
    @BindView(R.id.pulltorefreshView)
    PullToRefreshView pulltorefreshView;
    private SystemNewsAdapter systemNewsAdapter;
    private View view_systemNews;
    private int page=1;
    private CustomLoadingDialog customLoadingDialog;
    private List<SystemNewsInfo> infoList;
    @Override
    protected View getResLayout(LayoutInflater inflater, ViewGroup container) {
        view_systemNews = inflater.inflate(R.layout.fragment_system_news,null);
        return view_systemNews;
    }

    @Override
    protected void initViews() {
        infoList = new ArrayList<>();
        customLoadingDialog = new CustomLoadingDialog(getActivity());
        customLoadingDialog.show();
        systemNewsAdapter = new SystemNewsAdapter(getActivity(),infoList);
        lv_news.setAdapter(systemNewsAdapter);
    }

    @Override
    protected void initData() {
        getData();
    }
    private void getData() {
        ApiMine.getSystemNews(ApiConstant.SYSTEM_NEWS, page + "", new RequestCallBack<SystemNewsBean>() {
            @Override
            public void onSuccess(Call call, Response response, SystemNewsBean systemNewsBean) {
                customLoadingDialog.dismiss();
                List<SystemNewsInfo> list = systemNewsBean.getResult();
                if (list!=null && list.size()>0){
                    no_data_rl.setVisibility(View.GONE);
                    pulltorefreshView.setVisibility(View.VISIBLE);
                    pulltorefreshView.onHeaderRefreshComplete();
                    infoList.addAll(list);
                    systemNewsAdapter.notifyDataSetChanged();
                    //判断是不是没有更多数据了
                    if (list.size() < ApiConstant.PAGE_SIZE) {
                        pulltorefreshView.onFooterRefreshComplete(true);
                    }else{
                        pulltorefreshView.onFooterRefreshComplete(false);
                    }
                }else{
                    no_data_rl.setVisibility(View.VISIBLE);
                    pulltorefreshView.setVisibility(View.GONE);
                }
            }
            @Override
            public void onEror(Call call, int statusCode, Exception e) {
                Log.i("tag",e.getMessage());
            }
        });
    }

    @Override
    protected void initListener() {
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
        //点击事件跳转到详情
        lv_news.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Bundle bundle = new Bundle();
                bundle.putString("content",infoList.get(position).getContent());
                bundle.putString("subject",infoList.get(position).getSubject());
                bundle.putString("time",infoList.get(position).getCreate_date());
                bundle.putString("pic",infoList.get(position).getAttachment().get(0));
                jumpActivity(getActivity(), SystemDetailActivity.class,bundle);
            }
        });
    }
    //加载更多事件
    private void loadMore() {
        page++;
        pulltorefreshView.setEnablePullTorefresh(true);
        ApiMine.getSystemNews(ApiConstant.SYSTEM_NEWS, page + "", new RequestCallBack<SystemNewsBean>() {
            @Override
            public void onSuccess(Call call, Response response, SystemNewsBean systemNewsBean) {
                List<SystemNewsInfo> list = systemNewsBean.getResult();
                if (list!=null && list.size()>0){
                    infoList.addAll(list);
                    pulltorefreshView.onHeaderRefreshComplete();
                    systemNewsAdapter.notifyDataSetChanged();
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
}
