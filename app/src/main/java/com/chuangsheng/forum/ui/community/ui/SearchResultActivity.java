package com.chuangsheng.forum.ui.community.ui;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.chuangsheng.forum.R;
import com.chuangsheng.forum.api.ApiConstant;
import com.chuangsheng.forum.api.ApiForum;
import com.chuangsheng.forum.api.ApiHome;
import com.chuangsheng.forum.base.BaseActivity;
import com.chuangsheng.forum.callback.RequestCallBack;
import com.chuangsheng.forum.dialog.CustomLoadingDialog;
import com.chuangsheng.forum.ui.forum.ui.ForumDetailActivity;
import com.chuangsheng.forum.ui.home.adapter.HomeAdapter;
import com.chuangsheng.forum.ui.home.bean.HomeFroumBean;
import com.chuangsheng.forum.ui.home.bean.HomeFroumInfo;
import com.chuangsheng.forum.ui.mine.adapter.SearchAdapter;
import com.chuangsheng.forum.util.ToastUtils;
import com.chuangsheng.forum.view.PullToRefreshView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
import okhttp3.Call;
import okhttp3.Response;

public class SearchResultActivity extends BaseActivity {
    @BindView(R.id.listview)
    ListView lv_home;
    @BindView(R.id.no_data_rl)
    RelativeLayout no_data_rl;
    @BindView(R.id.pulltorefreshView)
    PullToRefreshView pulltorefreshView;
    @BindView(R.id.et_search)
    EditText et_search;
    private int page =1;
    private List<HomeFroumInfo> infoList;
    private CustomLoadingDialog customLoadingDialog;
    private SearchAdapter searchAdapter;
    private String keyWord;
    @Override
    protected void initViews() {
        infoList = new ArrayList<>();
        customLoadingDialog = new CustomLoadingDialog(this);
        customLoadingDialog.show();
    }
    @Override
    protected void initData() {
        Intent intent = getIntent();
        if (intent!=null){
            keyWord = intent.getStringExtra("keyWord");
        }
        getData();
        searchAdapter = new SearchAdapter(this,infoList,"普通");
        lv_home.setAdapter(searchAdapter);

    }
    //获取帖子列表
    private void getData() {
        pulltorefreshView.setEnablePullTorefresh(true);
        ApiForum.searchForums(ApiConstant.SEARCH_FORUM, keyWord, page + "", new RequestCallBack<HomeFroumBean>() {
            @Override
            public void onSuccess(Call call, Response response, HomeFroumBean homeFroumBean) {
                customLoadingDialog.dismiss();
                List<HomeFroumInfo> list = homeFroumBean.getResult().getDiscussions();
                if (list!=null && list.size()>0){
                    pulltorefreshView.setVisibility(View.VISIBLE);
                    no_data_rl.setVisibility(View.GONE);
                    pulltorefreshView.onHeaderRefreshComplete();
                    infoList.addAll(list);
                    searchAdapter.notifyDataSetChanged();
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
            @Override
            public void onEror(Call call, int statusCode, Exception e) {
                Log.i("tag",e.getMessage());
                customLoadingDialog.dismiss();
            }
        });
    }
    @Override
    protected void getResLayout() {
        setContentView(R.layout.activity_search_result);
    }

    @Override
    protected void initListener() {
        lv_home.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Bundle bundle = new Bundle();
                bundle.putString("discussionId",infoList.get(position).getId());
                jumpActivity(SearchResultActivity.this, ForumDetailActivity.class,bundle);
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

        et_search.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                InputMethodManager inputMethodManager = (InputMethodManager)getSystemService(Activity.INPUT_METHOD_SERVICE);
                inputMethodManager.hideSoftInputFromWindow(et_search.getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
                keyWord = et_search.getText().toString();
                page = 1;
                infoList.clear();
                getData();
                return false;
            }
        });
    }
    //加载更多事件
    private void loadMore() {
        page++;
        pulltorefreshView.setEnablePullTorefresh(true);
        ApiForum.searchForums(ApiConstant.SEARCH_FORUM, keyWord, page + "", new RequestCallBack<HomeFroumBean>() {
            @Override
            public void onSuccess(Call call, Response response, HomeFroumBean homeFroumBean) {
                List<HomeFroumInfo> list = homeFroumBean.getResult().getDiscussions();
                if (list!=null && list.size()>0){
                    infoList.addAll(list);
                    pulltorefreshView.onHeaderRefreshComplete();
                    searchAdapter.notifyDataSetChanged();
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
    @OnClick({R.id.tv_cancel})
    public void click(View view){
        switch (view.getId()){
            case R.id.tv_cancel:
                finish();
                break;
        }
    }
}
