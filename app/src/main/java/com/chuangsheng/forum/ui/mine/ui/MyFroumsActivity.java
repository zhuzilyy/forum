package com.chuangsheng.forum.ui.mine.ui;

import android.util.Log;
import android.view.View;
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
import com.chuangsheng.forum.ui.home.bean.HomeFroumBean;
import com.chuangsheng.forum.ui.home.bean.HomeFroumInfo;
import com.chuangsheng.forum.ui.mine.adapter.MyFroumAdapter;
import com.chuangsheng.forum.ui.mine.bean.MyFroumBean;
import com.chuangsheng.forum.util.SPUtils;
import com.chuangsheng.forum.view.PullToRefreshView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
import okhttp3.Call;
import okhttp3.Response;

public class MyFroumsActivity extends BaseActivity {
    @BindView(R.id.tv_title)
    TextView tv_title;
    @BindView(R.id.tv_right)
    TextView tv_right;
    @BindView(R.id.listview)
    ListView lv_forums;
    @BindView(R.id.pulltorefreshView)
    PullToRefreshView pulltorefreshView;
    @BindView(R.id.no_data_rl)
    RelativeLayout no_data_rl;
    private MyFroumAdapter adapter;
    private int page=1;
    private List<HomeFroumInfo> infoList;
    private CustomLoadingDialog customLoadingDialog;
    private String userId;
    @Override
    protected void initViews() {
        tv_title.setText("我的帖子");
        //tv_right.setVisibility(View.VISIBLE);
        tv_right.setText("管理");
        infoList = new ArrayList<>();
        userId = (String) SPUtils.get(this,"user_id","");
        customLoadingDialog = new CustomLoadingDialog(this);
        customLoadingDialog.show();
        BaseActivity.activityList.add(this);
    }
    @Override
    protected void initData() {
        getData();
        adapter = new MyFroumAdapter(this,infoList);
        lv_forums.setAdapter(adapter);
    }
    //获取数据
    private void getData() {
        pulltorefreshView.setEnablePullTorefresh(true);
        ApiMine.getMyFroumList(ApiConstant.MY_FROUM_LIST, userId, page + "", new RequestCallBack<MyFroumBean>() {
            @Override
            public void onSuccess(Call call, Response response, MyFroumBean myFroumBean) {
                customLoadingDialog.dismiss();
                List<HomeFroumInfo> list = myFroumBean.getResult().getDiscussions();
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
    protected void getResLayout() {
        setContentView(R.layout.activity_my_froums);
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
    }
    //加载更多事件
    private void loadMore() {
        page++;
        pulltorefreshView.setEnablePullTorefresh(true);
        ApiMine.getMyFroumList(ApiConstant.MY_FROUM_LIST, userId, page + "", new RequestCallBack<MyFroumBean>() {
            @Override
            public void onSuccess(Call call, Response response, MyFroumBean myFroumBean) {
                List<HomeFroumInfo> list = myFroumBean.getResult().getDiscussions();
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
