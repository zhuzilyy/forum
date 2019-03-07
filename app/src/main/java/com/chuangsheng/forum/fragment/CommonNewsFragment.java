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
import com.chuangsheng.forum.base.BaseActivity;
import com.chuangsheng.forum.base.BaseFragment;
import com.chuangsheng.forum.callback.RequestCallBack;
import com.chuangsheng.forum.dialog.CustomLoadingDialog;
import com.chuangsheng.forum.ui.forum.ui.ForumDetailActivity;
import com.chuangsheng.forum.ui.home.adapter.HomeAdapter;
import com.chuangsheng.forum.ui.mine.adapter.CommonNewsAdapter;
import com.chuangsheng.forum.ui.mine.adapter.MyReplyFroumAdapter;
import com.chuangsheng.forum.ui.mine.adapter.SystemNewsAdapter;
import com.chuangsheng.forum.ui.mine.bean.CommonNewsBean;
import com.chuangsheng.forum.ui.mine.bean.CommonNewsInfo;
import com.chuangsheng.forum.ui.mine.bean.MyReplyFroumBean;
import com.chuangsheng.forum.ui.mine.bean.MyReplyFroumInfo;
import com.chuangsheng.forum.ui.mine.ui.MyReplyForumActivity;
import com.chuangsheng.forum.ui.mine.ui.UserDetailActivity;
import com.chuangsheng.forum.util.SPUtils;
import com.chuangsheng.forum.view.PullToRefreshView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import okhttp3.Call;
import okhttp3.Response;

public class CommonNewsFragment extends BaseFragment {
    @BindView(R.id.listview)
    ListView lv_news;
    @BindView(R.id.no_data_rl)
    RelativeLayout no_data_rl;
    @BindView(R.id.pulltorefreshView)
    PullToRefreshView pulltorefreshView;
    private CommonNewsAdapter commonNewsAdapter;
    private int page=1;
    private List<CommonNewsInfo> infoList;
    private CustomLoadingDialog customLoadingDialog;
    private String userId;
    private View view_commonNews;
    @Override
    protected View getResLayout(LayoutInflater inflater, ViewGroup container) {
        view_commonNews = inflater.inflate(R.layout.fragment_common_news,null);
        return view_commonNews;
    }
    @Override
    protected void initViews() {
        infoList = new ArrayList<>();
        customLoadingDialog = new CustomLoadingDialog(getActivity());
        customLoadingDialog.show();
        userId = (String) SPUtils.get(getActivity(),"user_id","");
    }
    @Override
    protected void initData() {
        getData();
        commonNewsAdapter = new CommonNewsAdapter(getActivity(),infoList);
        lv_news.setAdapter(commonNewsAdapter);
        //名字点击事件
        commonNewsAdapter.setHeadClickListener(new CommonNewsAdapter.headClickListener() {
            @Override
            public void headClick(int position) {
                Bundle bundle = new Bundle();
                bundle.putString("user_id",userId);
                jumpActivity(getActivity(), UserDetailActivity.class,bundle);
            }
        });
    }
    private void getData() {
        pulltorefreshView.setEnablePullTorefresh(true);
        ApiMine.getCommonNews(ApiConstant.COMMON_NEWS, userId, page + "", new RequestCallBack<CommonNewsBean>() {
            @Override
            public void onSuccess(Call call, Response response, CommonNewsBean commonNewsBean) {
                customLoadingDialog.dismiss();
                List<CommonNewsInfo> list = commonNewsBean.getResult();
                if (list!=null && list.size()>0){
                    no_data_rl.setVisibility(View.GONE);
                    pulltorefreshView.setVisibility(View.VISIBLE);
                    pulltorefreshView.onHeaderRefreshComplete();
                    infoList.addAll(list);
                    commonNewsAdapter.notifyDataSetChanged();
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
        //点击事件跳转到详情
        lv_news.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Bundle bundle = new Bundle();
                bundle.putString("discussionId",infoList.get(position).getComment().getDiscussion().getId());
                jumpActivity(getActivity(), ForumDetailActivity.class,bundle);
            }
        });
    }
    //加载更多事件
    private void loadMore() {
        page++;
        pulltorefreshView.setEnablePullTorefresh(true);
        ApiMine.getCommonNews(ApiConstant.COMMON_NEWS, userId, page + "", new RequestCallBack<CommonNewsBean>() {
            @Override
            public void onSuccess(Call call, Response response, CommonNewsBean commonNewsBean) {
                List<CommonNewsInfo> list = commonNewsBean.getResult();
                if (list!=null && list.size()>0){
                    infoList.addAll(list);
                    pulltorefreshView.onHeaderRefreshComplete();
                    commonNewsAdapter.notifyDataSetChanged();
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
