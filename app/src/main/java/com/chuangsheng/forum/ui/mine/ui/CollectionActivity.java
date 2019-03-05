package com.chuangsheng.forum.ui.mine.ui;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.CheckBox;
import android.widget.CompoundButton;
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
import com.chuangsheng.forum.ui.account.ui.LoginActivity;
import com.chuangsheng.forum.ui.forum.bean.CommunityBean;
import com.chuangsheng.forum.ui.forum.bean.CommunityInfo;
import com.chuangsheng.forum.ui.forum.ui.ForumDetailActivity;
import com.chuangsheng.forum.ui.mine.adapter.MyCollectionAdapter;
import com.chuangsheng.forum.ui.mine.adapter.MyFroumAdapter;
import com.chuangsheng.forum.ui.mine.bean.CollectionBean;
import com.chuangsheng.forum.ui.mine.bean.CollectionInfo;
import com.chuangsheng.forum.util.SPUtils;
import com.chuangsheng.forum.util.ToastUtils;
import com.chuangsheng.forum.view.PullToRefreshView;

import org.json.JSONException;
import org.json.JSONObject;

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
    @BindView(R.id.rl_deleteAll)
    RelativeLayout rl_deleteAll;
    @BindView(R.id.cb_selectAll)
    CheckBox cb_selectAll;
    @BindView(R.id.pulltorefreshView)
    PullToRefreshView pulltorefreshView;
    private MyCollectionAdapter adapter;
    private int page=1;
    private String userId;
    private List<CollectionInfo> infoList;
    private CustomLoadingDialog customLoadingDialog;
    private String textStatus;
    private LocalBroadcastManager localBroadcastManager;
    private BroadcastReceiver broadcastReceiver;
    @Override
    protected void initViews() {
        tv_title.setText("我的收藏");
        tv_right.setVisibility(View.VISIBLE);
        tv_right.setText("管理");
        textStatus = "manage";
        customLoadingDialog = new CustomLoadingDialog(this);
        customLoadingDialog.show();
        BaseActivity.activityList.add(this);
    }
   /* private void registerBoradCastReceiver() {
        localBroadcastManager = LocalBroadcastManager.getInstance(this);
        IntentFilter intentFilterSelectAll = new IntentFilter();
        intentFilterSelectAll.addAction("com.action.selectAll");

        IntentFilter intentFilterNotSellectAll = new IntentFilter();
        intentFilterNotSellectAll.addAction("com.action.notSelectAll");

        broadcastReceiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                if (intent.getAction().equals("com.action.selectAll")){
                    cb_selectAll.setChecked(true);
                }else if (intent.getAction().equals("com.action.notSelectAll")){
                    cb_selectAll.setChecked(false);
                }
            }
        };
        localBroadcastManager.registerReceiver(broadcastReceiver, intentFilterSelectAll);
        localBroadcastManager.registerReceiver(broadcastReceiver, intentFilterNotSellectAll);
    }*/

    @Override
    protected void initData() {
        infoList = new ArrayList<>();
        userId= (String) SPUtils.get(CollectionActivity.this,"user_id","");
        adapter = new MyCollectionAdapter(this,infoList,"gone");
        adapter.initCheck(false);
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
                for (int i = 0; i <list.size() ; i++) {
                    list.get(i).setSelected(false);
                }
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
        cb_selectAll.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked){
                    for (int i = 0; i <infoList.size() ; i++) {
                       infoList.get(i).setSelected(true);
                       adapter.notifyDataSetChanged();
                    }
                }else{
                    for (int i = 0; i <infoList.size() ; i++) {
                        infoList.get(i).setSelected(false);
                        adapter.notifyDataSetChanged();
                    }
                }
            }
        });
        lv_forums.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, final int position, long id) {
                final AlertDialog.Builder builder = new AlertDialog.Builder(CollectionActivity.this);
                builder.setTitle("确定删除此条收藏吗");
                builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                            cancleCollection(infoList.get(position).getCollection_id(),position);
                    }
                });
                builder.setNegativeButton("取消", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });
                builder.show();
                return true;
            }
        });
        //点击事件跳转到详情
        lv_forums.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Bundle bundle = new Bundle();
                bundle.putString("discussionId",infoList.get(position).getId());
                jumpActivity(CollectionActivity.this, ForumDetailActivity.class,bundle);
            }
        });
    }
    //取消收藏
    private void cancleCollection(String discussId, final int position) {
        customLoadingDialog.show();
        ApiForum.collectionDiscussion(ApiConstant.COLLECTION_COMMENT, userId, discussId, new RequestCallBack<String>() {
            @Override
            public void onSuccess(Call call, Response response, String s) {
                customLoadingDialog.dismiss();
                try {
                    JSONObject jsonObject = new JSONObject(s);
                    int code = jsonObject.getInt("error_code");
                    if (code == ApiConstant.SUCCESS_CODE){
                       infoList.remove(position);
                       adapter.notifyDataSetChanged();
                       if (infoList.size()==0){
                           pulltorefreshView.setVisibility(View.GONE);
                           no_data_rl.setVisibility(View.VISIBLE);
                       }
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onEror(Call call, int statusCode, Exception e) {
                customLoadingDialog.dismiss();
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
    @OnClick({R.id.iv_back,R.id.tv_right})
    public void click(View view){
        switch (view.getId()){
            case R.id.iv_back:
                finish();
                break;
            case R.id.tv_right:
                if (textStatus.equals("manage")){
                    tv_right.setText("完成");
                    rl_deleteAll.setVisibility(View.VISIBLE);
                    textStatus = "finish";
                    adapter.setCheckListShow("show");
                }else{
                    tv_right.setText("管理");
                    textStatus = "manage";
                    rl_deleteAll.setVisibility(View.GONE);
                    adapter.setCheckListShow("gone");
                }
                break;
        }
    }
    @Override
    protected void onDestroy() {
        super.onDestroy();
      /*  if (broadcastReceiver!=null){
            LocalBroadcastManager.getInstance(this).unregisterReceiver(broadcastReceiver);
        }*/
    }
}
