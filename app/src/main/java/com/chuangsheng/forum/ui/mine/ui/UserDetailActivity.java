package com.chuangsheng.forum.ui.mine.ui;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.chuangsheng.forum.R;
import com.chuangsheng.forum.api.ApiConstant;
import com.chuangsheng.forum.api.ApiMine;
import com.chuangsheng.forum.base.BaseActivity;
import com.chuangsheng.forum.callback.RequestCallBack;
import com.chuangsheng.forum.dialog.CustomLoadingDialog;
import com.chuangsheng.forum.ui.forum.ui.ForumDetailActivity;
import com.chuangsheng.forum.ui.home.bean.HomeFroumInfo;
import com.chuangsheng.forum.ui.mine.adapter.MyFroumAdapter;
import com.chuangsheng.forum.ui.mine.adapter.UserDetailAdapter;
import com.chuangsheng.forum.ui.mine.bean.MyFroumBean;
import com.chuangsheng.forum.ui.mine.bean.UserBean;
import com.chuangsheng.forum.ui.mine.bean.UserResult;
import com.chuangsheng.forum.util.LevelUtil;
import com.chuangsheng.forum.view.CircleImageView;
import com.chuangsheng.forum.view.PullToRefreshView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
import okhttp3.Call;
import okhttp3.Response;

public class UserDetailActivity extends BaseActivity {
    @BindView(R.id.tv_title)
    TextView tv_title;
    @BindView(R.id.listview)
    ListView lv_userDetail;
    @BindView(R.id.pulltorefreshView)
    PullToRefreshView pulltorefreshView;
    private View view_header;
    private CircleImageView iv_head;
    private TextView tv_name,tv_sign;
    private ImageView iv_level,iv_sex;
    private CustomLoadingDialog customLoadingDialog;
    private UserDetailAdapter adapter;
    private int page=1;
    private List<HomeFroumInfo> infoList;
    private String userId;
    @Override
    protected void initViews() {
        tv_title.setText("用户详情");
        view_header = LayoutInflater.from(this).inflate(R.layout.header_user_detail,null);
        iv_head = view_header.findViewById(R.id.iv_head);
        tv_name = view_header.findViewById(R.id.tv_name);
        tv_sign = view_header.findViewById(R.id.tv_sign);
        iv_level = view_header.findViewById(R.id.iv_level);
        iv_sex = view_header.findViewById(R.id.iv_sex);
        customLoadingDialog = new CustomLoadingDialog(this);
        infoList = new ArrayList<>();
        BaseActivity.activityList.add(this);
    }
    @Override
    protected void initData() {
        Intent intent = getIntent();
        if (intent!=null){
            userId = intent.getExtras().getString("user_id");
        }
        getUserInfo();
        getData();
        adapter = new UserDetailAdapter(this,infoList,"gone");
        lv_userDetail.setAdapter(adapter);
        lv_userDetail.addHeaderView(view_header);
    }
    //获取我的帖子
    private void getData() {
        pulltorefreshView.setEnablePullTorefresh(true);
        ApiMine.getMyFroumList(ApiConstant.MY_FROUM_LIST, userId, page + "", new RequestCallBack<MyFroumBean>() {
            @Override
            public void onSuccess(Call call, Response response, MyFroumBean myFroumBean) {
                customLoadingDialog.dismiss();
                List<HomeFroumInfo> list = myFroumBean.getResult().getDiscussions();
                if (myFroumBean.getCode() == ApiConstant.SUCCESS_CODE){
                    if (list!=null && list.size()>0){
                        pulltorefreshView.setVisibility(View.VISIBLE);
                        pulltorefreshView.onHeaderRefreshComplete();
                        infoList.addAll(list);
                        adapter.notifyDataSetChanged();
                    }
                    //判断是不是没有更多数据了
                    if (list.size() < ApiConstant.PAGE_SIZE) {
                        pulltorefreshView.onFooterRefreshComplete(true);
                    }else{
                        pulltorefreshView.onFooterRefreshComplete(false);
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
    //获取个人信息
    private void getUserInfo() {
        ApiMine.getUserInfo(ApiConstant.GET_USER_INFO, userId, new RequestCallBack<UserBean>() {
            @Override
            public void onSuccess(Call call, Response response, UserBean userBean) {
                int code = userBean.getCode();
                if (code == ApiConstant.SUCCESS_CODE) {
                    UserResult result = userBean.getResult();
                    Glide.with(UserDetailActivity.this).load(result.getImg()).into(iv_head);
                    tv_name.setText(result.getUsername());
                    String gender = result.getSex().equals("1") ? "男" : "女";
                    if (gender.equals("男")){
                        iv_sex.setImageResource(R.mipmap.nan);
                    }else{
                        iv_sex.setImageResource(R.mipmap.nv);
                    }
                    tv_sign.setText(result.getDescription());
                    iv_level.setImageResource(LevelUtil.userLevel(result.getPoints()));
                }
            }
            @Override
            public void onEror(Call call, int statusCode, Exception e) {

            }
        });
    }
    @Override
    protected void getResLayout() {
        setContentView(R.layout.activity_user_detail);
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
        lv_userDetail.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Bundle bundle = new Bundle();
                bundle.putString("discussionId",infoList.get(position-1).getId());
                jumpActivity(UserDetailActivity.this, ForumDetailActivity.class,bundle);
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
