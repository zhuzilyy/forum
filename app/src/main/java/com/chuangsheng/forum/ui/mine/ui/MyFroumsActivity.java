package com.chuangsheng.forum.ui.mine.ui;

import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.CheckBox;
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
import com.chuangsheng.forum.ui.forum.ui.ForumDetailActivity;
import com.chuangsheng.forum.ui.home.bean.HomeFroumInfo;
import com.chuangsheng.forum.ui.mine.adapter.MyFroumAdapter;
import com.chuangsheng.forum.ui.mine.bean.MyFroumBean;
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
    @BindView(R.id.rl_deleteAll)
    RelativeLayout rl_deleteAll;
    private MyFroumAdapter adapter;
    private int page=1;
    private List<HomeFroumInfo> infoList;
    private CustomLoadingDialog customLoadingDialog;
    private String userId;
    @BindView(R.id.cb_selectAll)
    CheckBox cb_selectAll;
    private String textStatus;
    private List<Boolean> selectedList;
    @Override
    protected void initViews() {
        tv_title.setText("我的帖子");
        tv_right.setVisibility(View.VISIBLE);
        tv_right.setText("管理");
        infoList = new ArrayList<>();
        selectedList = new ArrayList<>();
        userId = (String) SPUtils.get(this,"user_id","");
        customLoadingDialog = new CustomLoadingDialog(this);
        customLoadingDialog.show();
        BaseActivity.activityList.add(this);
        textStatus = "manage";
    }
    @Override
    protected void initData() {
        getData();
        adapter = new MyFroumAdapter(this,infoList,selectedList,"gone");
        lv_forums.setAdapter(adapter);
        adapter.setItemClickListener(new MyFroumAdapter.itemClickListener() {
            @Override
            public void click(int position) {
                Bundle bundle = new Bundle();
                bundle.putString("discussionId",infoList.get(position).getId());
                jumpActivity(MyFroumsActivity.this, ForumDetailActivity.class,bundle);
            }
        });
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
                    for (int i = 0; i <list.size() ; i++) {
                        selectedList.add(false);
                    }
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
        //点击事件跳转到详情
        lv_forums.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Bundle bundle = new Bundle();
                bundle.putString("discussionId",infoList.get(position).getId());
                jumpActivity(MyFroumsActivity.this, ForumDetailActivity.class,bundle);
            }
        });
        adapter.setDeleteCheckedListener(new MyFroumAdapter.deleteCheckedListener() {
            @Override
            public void click(int count) {
                if (count == infoList.size()){
                    cb_selectAll.setChecked(true);
                }else{
                    cb_selectAll.setChecked(false);
                }
            }
        });
        //长按删除
        lv_forums.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, final int position, long id) {
                final AlertDialog.Builder builder = new AlertDialog.Builder(MyFroumsActivity.this);
                builder.setTitle("确定删除该条帖子");
                builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        deleteSingleForum(infoList.get(position).getId(),position);
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
        //全选和反选
        cb_selectAll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (cb_selectAll.isChecked()){
                    for (int i = 0; i <infoList.size() ; i++) {
                        selectedList.set(i,true);
                        adapter.notifyDataSetChanged();
                    }
                }else{
                    for (int i = 0; i <infoList.size() ; i++) {
                        selectedList.set(i,false);
                        adapter.notifyDataSetChanged();
                    }
                }
            }
        });
    }
    //删除一条帖子
    private void deleteSingleForum(String discussId, final int position) {
        customLoadingDialog.show();
        ApiForum.deletMyForums(ApiConstant.DELETE_MY_FORUMS, discussId, new RequestCallBack<String>() {
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
    @OnClick({R.id.iv_back,R.id.tv_right,R.id.btn_deleteAll})
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
            case R.id.btn_deleteAll:
                if (infoList.size()==0){
                    ToastUtils.show(MyFroumsActivity.this,"请选中要删除数据");
                    return;
                }
                List<Boolean> deleteList = new ArrayList<>();
                for (int i = 0; i <selectedList.size() ; i++) {
                    if (selectedList.get(i)){
                        deleteList.add(selectedList.get(i));
                    }
                }
                if (deleteList.size()==0){
                    ToastUtils.show(MyFroumsActivity.this,"请选中要删除数据");
                    return;
                }
                deleteAllSelectItem();
                break;
        }
    }
    //全选删除
    private void deleteAllSelectItem() {
        final AlertDialog.Builder builder = new AlertDialog.Builder(MyFroumsActivity.this);
        builder.setTitle("确定删除选中的帖子");
        builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                deleteForum();
            }
        });
        builder.setNegativeButton("取消", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        builder.show();
    }
    //删除所有收藏
    private void deleteForum() {
        customLoadingDialog.show();
        String deleteIds = "";
        final List<String> deletePositions = new ArrayList<>();
        for (int i = 0; i <selectedList.size() ; i++) {
            if (selectedList.get(i)){
                deleteIds+=infoList.get(i).getId()+",";
                deletePositions.add(i+"");
            }
        }
        ApiForum.deletMyForums(ApiConstant.DELETE_MY_FORUMS, deleteIds, new RequestCallBack<String>() {
            @Override
            public void onSuccess(Call call, Response response, String s) {
                customLoadingDialog.dismiss();
                try {
                    JSONObject jsonObject = new JSONObject(s);
                    int code = jsonObject.getInt("error_code");
                    if (code == ApiConstant.SUCCESS_CODE){
                        for (int i = deletePositions.size()-1; i>=0; i--) {
                            infoList.remove(Integer.parseInt(deletePositions.get(i)));
                            selectedList.remove(Integer.parseInt(deletePositions.get(i)));
                        }
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
}
