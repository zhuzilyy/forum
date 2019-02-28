package com.chuangsheng.forum.fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.chuangsheng.forum.R;
import com.chuangsheng.forum.api.ApiConstant;
import com.chuangsheng.forum.api.ApiForum;
import com.chuangsheng.forum.base.BaseFragment;
import com.chuangsheng.forum.callback.RequestCallBack;
import com.chuangsheng.forum.dialog.CustomLoadingDialog;
import com.chuangsheng.forum.ui.forum.adapter.FroumAdapter;
import com.chuangsheng.forum.ui.forum.bean.CommunityBean;
import com.chuangsheng.forum.ui.forum.bean.CommunityInfo;
import com.chuangsheng.forum.ui.forum.ui.CommunityDetailActivity;
import com.chuangsheng.forum.view.PullToRefreshView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import okhttp3.Call;
import okhttp3.Response;

public class ForumFragment extends BaseFragment {
    @BindView(R.id.listview)
    ListView lv_froum;
    @BindView(R.id.pulltorefreshView)
    PullToRefreshView pulltorefreshView;
    @BindView(R.id.tv_title)
    TextView tv_title;
    @BindView(R.id.iv_back)
    ImageView iv_back;
    private View view_froum;
    private FroumAdapter adapter;
    private int page=1;
    private List<CommunityInfo> infoList;
    private CustomLoadingDialog customLoadingDialog;
    @Override
    protected View getResLayout(LayoutInflater inflater, ViewGroup container) {
        view_froum = inflater.inflate(R.layout.fragment_forum,null);
        return view_froum;
    }
    @Override
    protected void initViews() {
        tv_title.setText("社区");
        iv_back.setVisibility(View.GONE);
        customLoadingDialog = new CustomLoadingDialog(getActivity());
        customLoadingDialog.show();
    }
    @Override
    protected void initData() {
        infoList = new ArrayList<>();
        getData();
        adapter = new FroumAdapter(getActivity(),infoList);
        lv_froum.setAdapter(adapter);
    }
    private void getData() {
        pulltorefreshView.setEnablePullTorefresh(true);
        ApiForum.getCommunityList(ApiConstant.COMMUNITY_LIST,page+"" ,new RequestCallBack<CommunityBean>() {
            @Override
            public void onSuccess(Call call, Response response, CommunityBean communityBean) {
                customLoadingDialog.dismiss();
                int code = communityBean.getCode();
                List<CommunityInfo> list = communityBean.getResult().getCommunitys();
                if (code == ApiConstant.SUCCESS_CODE){
                    if (list!=null && list.size()>0){
                        pulltorefreshView.onHeaderRefreshComplete();
                        infoList.addAll(list);
                        adapter.notifyDataSetChanged();
                        //判断是不是没有更多数据了
                        if (list.size() < ApiConstant.PAGE_SIZE) {
                            pulltorefreshView.onFooterRefreshComplete(true);
                        }else{
                            pulltorefreshView.onFooterRefreshComplete(false);
                        }
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
    @Override
    protected void initListener() {
        lv_froum.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                jumpActivity(getActivity(), CommunityDetailActivity.class,null);
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
                page++;
                loadMore();
            }
        });
    }
    //加载更多数据
    private void loadMore() {
        page++;
        pulltorefreshView.setEnablePullTorefresh(true);
        ApiForum.getCommunityList(ApiConstant.COMMUNITY_LIST,page+"" ,new RequestCallBack<CommunityBean>() {
            @Override
            public void onSuccess(Call call, Response response, CommunityBean communityBean) {
                List<CommunityInfo> list = communityBean.getResult().getCommunitys();
                if (list!=null && list.size()>0){
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
