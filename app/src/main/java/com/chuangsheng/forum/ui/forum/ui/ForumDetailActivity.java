package com.chuangsheng.forum.ui.forum.ui;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.v4.content.LocalBroadcastManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.chuangsheng.forum.R;
import com.chuangsheng.forum.api.ApiConstant;
import com.chuangsheng.forum.api.ApiForum;
import com.chuangsheng.forum.base.BaseActivity;
import com.chuangsheng.forum.callback.RequestCallBack;
import com.chuangsheng.forum.ui.forum.adapter.ForumDetailAdapter;
import com.chuangsheng.forum.ui.forum.bean.DetailForumBean;
import com.chuangsheng.forum.ui.forum.bean.DetailForumDiscussion;
import com.chuangsheng.forum.ui.forum.bean.DetailForumInfo;
import com.chuangsheng.forum.ui.home.adapter.GvImageAdapter;
import com.chuangsheng.forum.util.SPUtils;
import com.chuangsheng.forum.util.ToastUtils;
import com.chuangsheng.forum.view.CircleImageView;
import com.chuangsheng.forum.view.MyGridView;
import com.chuangsheng.forum.view.PullToRefreshView;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
import okhttp3.Call;
import okhttp3.Response;

public class ForumDetailActivity extends BaseActivity {
    @BindView(R.id.listview)
    ListView lv_forumDetail;
    @BindView(R.id.pulltorefreshView)
    PullToRefreshView pulltorefreshView;
    @BindView(R.id.no_data_rl)
    RelativeLayout no_data_rl;
    @BindView(R.id.tv_title)
    TextView tv_title;
    private ForumDetailAdapter detailAdapter;
    private View view_header;
    private String userId,discussionId;
    private int page=1;
    private LocalBroadcastManager localBroadcastManager;
    private BroadcastReceiver broadcastReceiver;
    private ImageView iv_headerSinglePic;
    private MyGridView gv_header;
    private TextView tv_headerTitle,tv_name,tv_time,tv_content,tv_browse,tv_commentCount;
    private CircleImageView iv_head;
    private List<DetailForumInfo> infoList;

    @Override
    protected void initViews() {
        view_header = LayoutInflater.from(this).inflate(R.layout.header_forum_detail,null);
        iv_headerSinglePic = view_header.findViewById(R.id.iv_headerSinglePic);
        gv_header = view_header.findViewById(R.id.gv_image);
        tv_headerTitle = view_header.findViewById(R.id.tv_title);
        tv_name = view_header.findViewById(R.id.tv_name);
        tv_time = view_header.findViewById(R.id.tv_time);
        tv_content = view_header.findViewById(R.id.tv_content);
        tv_browse = view_header.findViewById(R.id.tv_browse);
        iv_head = view_header.findViewById(R.id.iv_head);
        tv_commentCount = view_header.findViewById(R.id.tv_commentCount);
        tv_title.setText("详情");
        registerBroadCast();
        infoList = new ArrayList<>();
    }
    private void registerBroadCast() {
        localBroadcastManager = LocalBroadcastManager.getInstance(this);
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction("com.action.replySuccess");
        broadcastReceiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                infoList.clear();
                getData();
            }
        };
        localBroadcastManager.registerReceiver(broadcastReceiver, intentFilter);
    }
    @Override
    protected void initData() {
        Intent intent = getIntent();
        if (intent!=null){
            Bundle extras = intent.getExtras();
            discussionId = extras.getString("discussionId");
        }
        userId = (String) SPUtils.get(this,"user_id","");
        detailAdapter = new ForumDetailAdapter(this,infoList);
        lv_forumDetail.setAdapter(detailAdapter);
        lv_forumDetail.addHeaderView(view_header);
        getData();
    }
    //获取数据
    private void getData() {
        pulltorefreshView.setEnablePullTorefresh(true);
        ApiForum.getFroumDetail(ApiConstant.FORUM_DETAIL, userId, discussionId,page+"", new RequestCallBack<DetailForumBean>() {
            @Override
            public void onSuccess(Call call, Response response, DetailForumBean detailForumBean) {
                int error_code = detailForumBean.getError_code();
                pulltorefreshView.onHeaderRefreshComplete();
                if (error_code == ApiConstant.SUCCESS_CODE){
                    //头部的内容
                    DetailForumDiscussion discussion = detailForumBean.getResult().getDiscussion();
                    tv_headerTitle.setText(discussion.getSubject());
                    tv_name.setText(discussion.getUser_username());
                    tv_time.setText(discussion.getCreated());
                    tv_browse.setText(discussion.getClick());
                    tv_content.setText(discussion.getContent());
                    tv_commentCount.setText("("+discussion.getComments()+")");
                    Glide.with(ForumDetailActivity.this).load(discussion.getUser_img()).into(iv_head);
                    List<String> attachment = detailForumBean.getResult().getDiscussion().getAttachment();
                    //显示和隐藏图片
                    if (attachment !=null && attachment.size()==0){
                        iv_headerSinglePic.setVisibility(View.GONE);
                        gv_header.setVisibility(View.GONE);
                    }else if(attachment !=null && attachment.size()==1){
                        iv_headerSinglePic.setVisibility(View.VISIBLE);
                        gv_header.setVisibility(View.GONE);
                        Glide.with(ForumDetailActivity.this).load(attachment.get(0)).into(iv_headerSinglePic);
                    }else {
                        iv_headerSinglePic.setVisibility(View.GONE);
                        gv_header.setVisibility(View.VISIBLE);
                        GvImageAdapter adapter = new GvImageAdapter(ForumDetailActivity.this,attachment);
                        gv_header.setAdapter(adapter);
                    }
                    //显示评论
                    List<DetailForumInfo> comments = detailForumBean.getResult().getComments();
                    if (comments!=null && comments.size()>0){
                        pulltorefreshView.setVisibility(View.VISIBLE);
                        no_data_rl.setVisibility(View.GONE);
                        infoList.addAll(comments);
                        detailAdapter.notifyDataSetChanged();
                    }
                    //判断是不是没有更多数据了
                    if (comments.size() < ApiConstant.PAGE_SIZE) {
                        pulltorefreshView.onFooterRefreshComplete(true);
                    }else{
                        pulltorefreshView.onFooterRefreshComplete(false);
                    }
                }
            }
            @Override
            public void onEror(Call call, int statusCode, Exception e) {

            }
        });
    }
    @Override
    protected void getResLayout() {
        setContentView(R.layout.activity_forum_detail);
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
        detailAdapter.setDianZanClickListener(new ForumDetailAdapter.DianZanListener() {
            @Override
            public void click(int position) {
                ToastUtils.show(ForumDetailActivity.this,position+"");
               //likeComment(position);
            }
        });
    }
    //给评论点赞
    private void likeComment(final int position) {
        ApiForum.likeComment(ApiConstant.LIKE_COMMENT, userId, infoList.get(position).getId(), new RequestCallBack<String>() {
            @Override
            public void onSuccess(Call call, Response response, String s) {
                try {
                    JSONObject jsonObject = new JSONObject(s);
                    int code = jsonObject.getInt("error_code");
                    String reason = jsonObject.getString("reason");
                    String like_status = jsonObject.getJSONObject("result").getString("like_status");
                    ToastUtils.show(ForumDetailActivity.this,reason);
                    infoList.get(position).setLike_status(like_status);
                    detailAdapter.notifyDataSetChanged();
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onEror(Call call, int statusCode, Exception e) {

            }
        });
    }
    //加载更多事件
    private void loadMore() {
        page++;
        pulltorefreshView.setEnablePullTorefresh(true);
        ApiForum.getFroumDetail(ApiConstant.FORUM_DETAIL, userId, discussionId,page+"", new RequestCallBack<DetailForumBean>() {
            @Override
            public void onSuccess(Call call, Response response, DetailForumBean detailForumBean) {
                List<DetailForumInfo> list = detailForumBean.getResult().getComments();
                if (list!=null && list.size()>0){
                    infoList.addAll(list);
                    pulltorefreshView.onHeaderRefreshComplete();
                    detailAdapter.notifyDataSetChanged();
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
    @OnClick({R.id.iv_back,R.id.tv_comment})
    public void click(View view){
        switch (view.getId()){
            case R.id.iv_back:
                finish();
                break;
            case R.id.tv_comment:
                Intent intent = new Intent(ForumDetailActivity.this,ReplyForumActivity.class);
                intent.putExtra("discussionId",discussionId);
                startActivity(intent);
                break;
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        localBroadcastManager.unregisterReceiver(broadcastReceiver);
    }
}