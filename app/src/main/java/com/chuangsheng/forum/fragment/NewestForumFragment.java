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
import com.chuangsheng.forum.api.ApiHome;
import com.chuangsheng.forum.base.BaseFragment;
import com.chuangsheng.forum.callback.RequestCallBack;
import com.chuangsheng.forum.dialog.CustomLoadingDialog;
import com.chuangsheng.forum.ui.forum.adapter.CommunityAdapter;
import com.chuangsheng.forum.ui.forum.ui.ForumDetailActivity;
import com.chuangsheng.forum.ui.home.adapter.HomeAdapter;
import com.chuangsheng.forum.ui.home.bean.HomeFroumBean;
import com.chuangsheng.forum.ui.home.bean.HomeFroumInfo;
import com.chuangsheng.forum.ui.mine.adapter.MyCollectionAdapter;
import com.chuangsheng.forum.view.PullToRefreshView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import okhttp3.Call;
import okhttp3.Response;

public class NewestForumFragment extends BaseFragment {
    @BindView(R.id.listview)
    ListView lv_forums;
    @BindView(R.id.pulltorefreshView)
    PullToRefreshView pulltorefreshView;
    @BindView(R.id.no_data_rl)
    RelativeLayout no_data_rl;
    private View view_newestForum;
    private HomeAdapter adapter;
    private int page=1;
    private CustomLoadingDialog customLoadingDialog;
    private List<HomeFroumInfo> infoList;
    @Override
    protected View getResLayout(LayoutInflater inflater, ViewGroup container) {
        view_newestForum = inflater.inflate(R.layout.fragment_newest_forum,null);
        return view_newestForum;
    }
    @Override
    protected void initViews() {
        customLoadingDialog = new CustomLoadingDialog(getActivity());
        customLoadingDialog.show();
        infoList = new ArrayList<>();
    }
    @Override
    protected void initData() {
        adapter = new HomeAdapter(getActivity(),infoList,"最新");
        lv_forums.setAdapter(adapter);
        getData();
    }
    private void getData() {
        pulltorefreshView.setEnablePullTorefresh(true);
        ApiHome.getHomeFroumList(ApiConstant.HOME_FROUM_LIST, "最新", page + "", new RequestCallBack<HomeFroumBean>() {
            @Override
            public void onSuccess(Call call, Response response, HomeFroumBean homeFroumBean) {
                customLoadingDialog.dismiss();
                List<HomeFroumInfo> list = homeFroumBean.getResult().getDiscussions();
                if (list!=null && list.size()>0){
                    no_data_rl.setVisibility(View.GONE);
                    pulltorefreshView.setVisibility(View.VISIBLE);
                    pulltorefreshView.onHeaderRefreshComplete();
                    infoList.addAll(list);
                    adapter.notifyDataSetChanged();
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
                customLoadingDialog.dismiss();
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
        lv_forums.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                infoList.get(position).setClick(Integer.parseInt(infoList.get(position).getClick())+1+"");
                adapter.notifyDataSetChanged();
                Bundle bundle = new Bundle();
                bundle.putString("discussionId",infoList.get(position).getId());
                jumpActivity(getActivity(), ForumDetailActivity.class,bundle);
            }
        });

    }
    //加载更多事件
    private void loadMore() {
        page++;
        pulltorefreshView.setEnablePullTorefresh(true);
        ApiHome.getHomeFroumList(ApiConstant.HOME_FROUM_LIST, "最新", page + "", new RequestCallBack<HomeFroumBean>() {
            @Override
            public void onSuccess(Call call, Response response, HomeFroumBean homeFroumBean) {
                List<HomeFroumInfo> list = homeFroumBean.getResult().getDiscussions();
                if (list!=null && list.size()>0){
                    infoList.addAll(list);
                    pulltorefreshView.onHeaderRefreshComplete();
                    adapter.notifyDataSetChanged();
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
