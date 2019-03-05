package com.chuangsheng.forum.ui.mine.ui;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.chuangsheng.forum.R;
import com.chuangsheng.forum.api.ApiConstant;
import com.chuangsheng.forum.api.ApiHome;
import com.chuangsheng.forum.api.ApiMine;
import com.chuangsheng.forum.base.BaseActivity;
import com.chuangsheng.forum.callback.RequestCallBack;
import com.chuangsheng.forum.dialog.CustomLoadingDialog;
import com.chuangsheng.forum.ui.forum.ui.ForumDetailActivity;
import com.chuangsheng.forum.ui.home.bean.HomeFroumBean;
import com.chuangsheng.forum.ui.home.bean.HomeFroumInfo;
import com.chuangsheng.forum.ui.mine.adapter.MyReplyFroumAdapter;
import com.chuangsheng.forum.ui.mine.bean.MyReplyFroumBean;
import com.chuangsheng.forum.ui.mine.bean.MyReplyFroumInfo;
import com.chuangsheng.forum.util.SPUtils;
import com.chuangsheng.forum.view.PullToRefreshView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
import okhttp3.Call;
import okhttp3.Response;

public class MyReplyForumActivity extends BaseActivity {
    @BindView(R.id.tv_title)
    TextView tv_title;
    @BindView(R.id.listview)
    ListView lv_forums;
    @BindView(R.id.no_data_rl)
    RelativeLayout no_data_rl;
    @BindView(R.id.pulltorefreshView)
    PullToRefreshView pulltorefreshView;
    private MyReplyFroumAdapter myReplyFroumAdapter;
    private int page=1;
    private List<MyReplyFroumInfo> infoList;
    private CustomLoadingDialog customLoadingDialog;
    private String userId;
    @Override
    protected void initViews() {
        tv_title.setText("我的回帖");
        infoList = new ArrayList<>();
        customLoadingDialog = new CustomLoadingDialog(this);
        customLoadingDialog.show();
        userId = (String) SPUtils.get(this,"user_id","");
        BaseActivity.activityList.add(this);
    }
    @Override
    protected void initData() {
        getData();
        myReplyFroumAdapter = new MyReplyFroumAdapter(this,infoList);
        lv_forums.setAdapter(myReplyFroumAdapter);
    }
    private void getData() {
        pulltorefreshView.setEnablePullTorefresh(true);
        ApiMine.getMyReplyFroumList(ApiConstant.MY_REPLY_FROUM_LIST, userId, page + "", new RequestCallBack<MyReplyFroumBean>() {
            @Override
            public void onSuccess(Call call, Response response, MyReplyFroumBean myReplyFroumBean) {
                customLoadingDialog.dismiss();
                List<MyReplyFroumInfo> list = myReplyFroumBean.getResult().getComments();
                if (list!=null && list.size()>0){
                    no_data_rl.setVisibility(View.GONE);
                    pulltorefreshView.setVisibility(View.VISIBLE);
                    pulltorefreshView.onHeaderRefreshComplete();
                    infoList.addAll(list);
                    myReplyFroumAdapter.notifyDataSetChanged();
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
    protected void getResLayout() {
        setContentView(R.layout.activity_my_reply_forums);
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
        lv_forums.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Bundle bundle = new Bundle();
                bundle.putString("discussionId",infoList.get(position).getDiscussion().getId());
                jumpActivity(MyReplyForumActivity.this, ForumDetailActivity.class,bundle);
            }
        });
    }
    //加载更多事件
    private void loadMore() {
        page++;
        pulltorefreshView.setEnablePullTorefresh(true);
        ApiMine.getMyReplyFroumList(ApiConstant.MY_REPLY_FROUM_LIST, userId, page + "", new RequestCallBack<MyReplyFroumBean>() {
            @Override
            public void onSuccess(Call call, Response response, MyReplyFroumBean myReplyFroumBean) {
                List<MyReplyFroumInfo> list = myReplyFroumBean.getResult().getComments();
                if (list!=null && list.size()>0){
                    infoList.addAll(list);
                    pulltorefreshView.onHeaderRefreshComplete();
                    myReplyFroumAdapter.notifyDataSetChanged();
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
    protected void setStatusBarColor() {

    }
    @OnClick({R.id.iv_back})
    public void click(View view){
        switch (view.getId()){
            case R.id.iv_back:
                finish();
                break;
        }
    }
}
