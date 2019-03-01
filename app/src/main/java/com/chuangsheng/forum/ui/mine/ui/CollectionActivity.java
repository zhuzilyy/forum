package com.chuangsheng.forum.ui.mine.ui;

import android.util.Log;
import android.view.View;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.chuangsheng.forum.R;
import com.chuangsheng.forum.api.ApiConstant;
import com.chuangsheng.forum.api.ApiForum;
import com.chuangsheng.forum.api.ApiMine;
import com.chuangsheng.forum.base.BaseActivity;
import com.chuangsheng.forum.callback.RequestCallBack;
import com.chuangsheng.forum.dialog.CustomLoadingDialog;
import com.chuangsheng.forum.ui.forum.bean.CommunityBean;
import com.chuangsheng.forum.ui.forum.bean.CommunityInfo;
import com.chuangsheng.forum.ui.mine.adapter.MyCollectionAdapter;
import com.chuangsheng.forum.ui.mine.adapter.MyFroumAdapter;
import com.chuangsheng.forum.ui.mine.bean.CollectionBean;
import com.chuangsheng.forum.ui.mine.bean.CollectionInfo;
import com.chuangsheng.forum.util.SPUtils;
import com.chuangsheng.forum.view.PullToRefreshView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
import okhttp3.Call;
import okhttp3.Response;

public class CollectionActivity extends BaseActivity {
    @BindView(R.id.tv_title)
    TextView tv_title;
    @BindView(R.id.tv_right)
    TextView tv_right;
    @BindView(R.id.listview)
    ListView lv_forums;
    @BindView(R.id.no_data_rl)
    RelativeLayout no_data_rl;
    @BindView(R.id.pulltorefreshView)
    PullToRefreshView pulltorefreshView;
    private MyCollectionAdapter adapter;
    private int page=1;
    private String userId;
    private List<CollectionInfo> infoList;
    private CustomLoadingDialog customLoadingDialog;
    @Override
    protected void initViews() {
        tv_title.setText("我的收藏");
        tv_right.setVisibility(View.VISIBLE);
        tv_right.setText("管理");
        customLoadingDialog = new CustomLoadingDialog(this);
        customLoadingDialog.show();
    }
    @Override
    protected void initData() {
        infoList = new ArrayList<>();
        userId= (String) SPUtils.get(CollectionActivity.this,"user_id","");
        adapter = new MyCollectionAdapter(this,infoList);
        lv_forums.setAdapter(adapter);
        getData();
    }
    //获取收藏的数据
    private void getData() {
        pulltorefreshView.setEnablePullTorefresh(true);
        ApiMine.myCollection(ApiConstant.MY_COLLECTION, userId, page + "", new RequestCallBack<CollectionBean>() {
            @Override
            public void onSuccess(Call call, Response response, CollectionBean collectionBean) {
                int code = collectionBean.getError_code();
                customLoadingDialog.dismiss();
                List<CollectionInfo> list = collectionBean.getResult();
                if (code == ApiConstant.SUCCESS_CODE){
                    if (list!=null && list.size()>0){
                        pulltorefreshView.setVisibility(View.VISIBLE);
                        no_data_rl.setVisibility(View.GONE);
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
                        pulltorefreshView.setVisibility(View.GONE);
                        no_data_rl.setVisibility(View.VISIBLE);
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
    protected void getResLayout() {
        setContentView(R.layout.activity_collection);
    }

    @Override
    protected void initListener() {
        //刷新加载事件
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
        ApiMine.myCollection(ApiConstant.MY_COLLECTION, userId, page + "", new RequestCallBack<CollectionBean>() {
            @Override
            public void onSuccess(Call call, Response response, CollectionBean collectionBean) {
                List<CollectionInfo> list = collectionBean.getResult();
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
