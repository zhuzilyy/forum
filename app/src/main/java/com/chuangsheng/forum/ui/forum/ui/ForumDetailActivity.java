package com.chuangsheng.forum.ui.forum.ui;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v4.content.LocalBroadcastManager;
import android.text.Spannable;
import android.text.SpannableStringBuilder;
import android.text.TextUtils;
import android.text.style.ClickableSpan;
import android.text.style.URLSpan;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.chuangsheng.forum.R;
import com.chuangsheng.forum.api.ApiConstant;
import com.chuangsheng.forum.api.ApiForum;
import com.chuangsheng.forum.base.BaseActivity;
import com.chuangsheng.forum.callback.RequestCallBack;
import com.chuangsheng.forum.ui.account.ui.LoginActivity;
import com.chuangsheng.forum.ui.forum.adapter.ForumDetailAdapter;
import com.chuangsheng.forum.ui.forum.bean.DetailForumBean;
import com.chuangsheng.forum.ui.forum.bean.DetailForumDiscussion;
import com.chuangsheng.forum.ui.forum.bean.DetailForumInfo;
import com.chuangsheng.forum.ui.forum.bean.EaluationPicBean;
import com.chuangsheng.forum.ui.forum.bean.ForumParent;
import com.chuangsheng.forum.ui.home.adapter.GvForumDetailAdapter;
import com.chuangsheng.forum.ui.home.adapter.GvImageAdapter;
import com.chuangsheng.forum.ui.home.adapter.GvThreeImageAdapter;
import com.chuangsheng.forum.ui.mine.ui.UserDetailActivity;
import com.chuangsheng.forum.ui.mine.ui.WebviewActivity;
import com.chuangsheng.forum.util.LevelUtil;
import com.chuangsheng.forum.util.SPUtils;
import com.chuangsheng.forum.util.ToastUtils;
import com.chuangsheng.forum.view.CircleImageView;
import com.chuangsheng.forum.view.MyGridView;
import com.chuangsheng.forum.view.MyListView;
import com.chuangsheng.forum.view.PullToRefreshView;
import com.google.gson.Gson;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;
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
    @BindView(R.id.common_no__data_tv)
    TextView common_no__data_tv;
    @BindView(R.id.tv_dianzan)
    TextView tv_dianzan;
    @BindView(R.id.tv_collection)
    TextView tv_collection;
    @BindView(R.id.iv_dianzan)
    ImageView iv_dianzan;
    @BindView(R.id.iv_collection)
    ImageView iv_collection;
    @BindView(R.id.ll_comment)
    LinearLayout ll_comment;
    private ForumDetailAdapter detailAdapter;
    private String userId,discussionId,userDetailId;
    private int page=1,showAdPic;
    private LocalBroadcastManager localBroadcastManager;
    private BroadcastReceiver broadcastReceiver;
    private ImageView iv_headerSinglePic;
    private MyListView gv_header;
    private TextView tv_headerTitle,tv_name,tv_time,tv_content,tv_browse,tv_commentCount;
    private CircleImageView iv_head;
    private List<DetailForumInfo> infoList;
    private String advertisement,adLink;
    private ImageView iv_level;
    private View view_header;
    private LinearLayout ll_picWrapper;
    @Override
    protected void initViews() {
        view_header = LayoutInflater.from(this).inflate(R.layout.header_forum_detail,null);
        iv_headerSinglePic = view_header.findViewById(R.id.iv_headerSinglePic);
        ll_picWrapper = view_header.findViewById(R.id.ll_picWrapper);
        gv_header = view_header.findViewById(R.id.gv_image);
        tv_headerTitle = view_header.findViewById(R.id.tv_headertitle);
        tv_name = view_header.findViewById(R.id.tv_name);
        tv_time = view_header.findViewById(R.id.tv_time);
        tv_content =view_header.findViewById(R.id.tv_content);
        tv_browse = view_header.findViewById(R.id.tv_browse);
        iv_head = view_header.findViewById(R.id.iv_head);
        iv_level = view_header.findViewById(R.id.iv_level);
        tv_commentCount = view_header.findViewById(R.id.tv_commentCount);
        registerBroadCast();
        infoList = new ArrayList<>();
        BaseActivity.activityList.add(this);
    }
    private void registerBroadCast() {
        localBroadcastManager = LocalBroadcastManager.getInstance(this);
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction("com.action.replySuccess");
        broadcastReceiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
              /*  String user_img = intent.getStringExtra("user_img");
                String user_username = intent.getStringExtra("user_username");
                String user_points = intent.getStringExtra("user_points");
                String content = intent.getStringExtra("content");
                String created = intent.getStringExtra("created");
                String likes = intent.getStringExtra("likes");
                String like_status = intent.getStringExtra("like_status");
                String attachment = intent.getStringExtra("attachment");
                String parentContent = intent.getStringExtra("content");
                String parentUserId = intent.getStringExtra("parentUserId");
                DetailForumInfo detailForumInfo = new DetailForumInfo();
                detailForumInfo.setUser_img(user_img);
                detailForumInfo.setUser_username(user_username);
                detailForumInfo.setUser_points(user_points);
                detailForumInfo.setContent(content);
                detailForumInfo.setCreated(created);
                detailForumInfo.setLikes(likes);
                detailForumInfo.setLike_status(like_status);
                detailForumInfo.setAdImg(advertisement);
                if (!TextUtils.isEmpty(parentContent)){
                    ForumParent forumParent = new ForumParent();
                    forumParent.setContent(parentContent);
                    forumParent.setUser_id(parentUserId);
                    detailForumInfo.setParent(forumParent);
                }
                Gson gson = new Gson();
                List<String> picList = gson.fromJson(attachment,List.class);
                detailForumInfo.setAttachment(picList);
                infoList.add(detailForumInfo);
                detailAdapter.notifyDataSetChanged();*/
              page =1;
              infoList.clear();
              getData();
            }
        };
        localBroadcastManager.registerReceiver(broadcastReceiver, intentFilter);
    }
    @Override
    protected void initData() {
        final Intent intent = getIntent();
        if (intent!=null){
            Bundle extras = intent.getExtras();
            discussionId = extras.getString("discussionId");
            userDetailId = extras.getString("userId");
        }
        userId = (String) SPUtils.get(this,"user_id","");
        detailAdapter = new ForumDetailAdapter(this,infoList,showAdPic);
        lv_forumDetail.setAdapter(detailAdapter);
        lv_forumDetail.addHeaderView(view_header);
        getData();
        detailAdapter.setAdLinkClickListener(new ForumDetailAdapter.adLinkClickListener() {
            @Override
            public void click() {
                Bundle bundle = new Bundle();
                bundle.putString("title","详情");
                bundle.putString("url",adLink);
                jumpActivity(ForumDetailActivity.this, WebviewActivity.class,bundle);
            }
        });
        detailAdapter.setNameClickListener(new ForumDetailAdapter.nameClickListener() {
            @Override
            public void click(int position) {
                Bundle bundle = new Bundle();
                bundle.putString("user_id",infoList.get(position).getUser_id());
                jumpActivity(ForumDetailActivity.this, UserDetailActivity.class,bundle);
            }
        });
        detailAdapter.setPicClickListener(new ForumDetailAdapter.picClickListener() {
            @Override
            public void click(int position) {
                ToastUtils.show(ForumDetailActivity.this,"1111111111");
                List<String> attachment = infoList.get(position).getAttachment();
                Intent intent = new Intent(ForumDetailActivity.this, LookBigPicActivity.class);
                List<EaluationPicBean> list = new ArrayList<>();
                EaluationPicBean ealuationPicBean = new EaluationPicBean();
                ealuationPicBean.imageUrl =attachment.get(0);
                list.add(ealuationPicBean);
                Bundle bundle = new Bundle();
                bundle.putSerializable(LookBigPicActivity.PICDATALIST, (Serializable)list);
                intent.putExtra("CURRENTITEM",0);
                intent.putExtras(bundle);
                startActivity(intent);
                //overridePendingTransition(0, 0);
            }
        });
        detailAdapter.setGvClickListener(new ForumDetailAdapter.gvClickListener() {
            @Override
            public void click(int position,int tag) {
                List<String> attachment = infoList.get(position).getAttachment();
                Intent intent = new Intent(ForumDetailActivity.this, LookBigPicActivity.class);
                List<EaluationPicBean> list = new ArrayList<>();
                for (int i = 0; i <attachment.size() ; i++) {
                    EaluationPicBean ealuationPicBean = new EaluationPicBean();
                    ealuationPicBean.imageUrl =attachment.get(i);
                    list.add(ealuationPicBean);
                }
                Bundle bundle = new Bundle();
                bundle.putSerializable(LookBigPicActivity.PICDATALIST, (Serializable)list);
                intent.putExtra("CURRENTITEM",tag);
                intent.putExtras(bundle);
                startActivity(intent);
                overridePendingTransition(0, 0);
            }
        });
        detailAdapter.setPicClickListener(new ForumDetailAdapter.picClickListener() {
            @Override
            public void click(int position) {

            }
        });
        detailAdapter.setPingLunClickListener(new ForumDetailAdapter.pingLunClickListener() {
            @Override
            public void click(int position) {
                Bundle bundle = new Bundle();
                String id = infoList.get(position).getId();
                String userName = infoList.get(position).getUser_username();
                bundle.putString("discussionId",id);
                bundle.putString("replyName",userName);
                jumpActivity(ForumDetailActivity.this,ReplyForumActivity.class,bundle);
            }
        });
    }
    //获取数据
    private void getData() {
        pulltorefreshView.setEnablePullTorefresh(true);
        ApiForum.getFroumDetail(ApiConstant.FORUM_DETAIL, userId, discussionId,page+"", new RequestCallBack<DetailForumBean>() {
            /**
             t* @param call
             * @param response        t
             * @param detailForumBean
             */
            @Override
            public void onSuccess(Call call, Response response, DetailForumBean detailForumBean) {
                RequestOptions options = new RequestOptions();
                options.placeholder(R.drawable.pic);
                int error_code = detailForumBean.getError_code();
                if (pulltorefreshView!=null){
                    pulltorefreshView.onHeaderRefreshComplete();
                }
                if (error_code == ApiConstant.SUCCESS_CODE){
                    //头部的内容
                    DetailForumDiscussion discussion = detailForumBean.getResult().getDiscussion();
                    tv_title.setText(discussion.getSubject());
                    tv_title.setTypeface(Typeface.defaultFromStyle(Typeface.BOLD));
                    tv_headerTitle.setText(discussion.getSubject());
                    tv_name.setText(discussion.getUser_username());
                    tv_time.setText(discussion.getCreated());
                    tv_browse.setText(discussion.getClick());
                    tv_content.setText(discussion.getContent());
                    setcontentLink();
                    tv_commentCount.setText("("+discussion.getComments()+")");
                    tv_dianzan.setText(discussion.getLikes());
                    if (discussion.getLike_status().equals("True")){
                        iv_dianzan.setImageResource(R.mipmap.dianzan_xuanzhong);
                    }else{
                        iv_dianzan.setImageResource(R.mipmap.dianzan_hui);
                    }
                    if (discussion.getCollection_status().equals("True")){
                        iv_collection.setImageResource(R.mipmap.collection);
                        tv_collection.setText("已收藏");
                    }else{
                        iv_collection.setImageResource(R.mipmap.soucang);
                        tv_collection.setText("收藏");
                    }
                    Glide.with(ForumDetailActivity.this).load(discussion.getUser_img()).apply(options).into(iv_head);
                    Glide.with(ForumDetailActivity.this).load(LevelUtil.userLevel(discussion.getUser_points())).into(iv_level);
                    List<String> attachment = detailForumBean.getResult().getDiscussion().getAttachment();
                    //显示和隐藏图片
                    if (attachment !=null && attachment.size()==0){
                        iv_headerSinglePic.setVisibility(View.GONE);
                        gv_header.setVisibility(View.GONE);
                    }else if(attachment !=null && attachment.size()==1){
                        iv_headerSinglePic.setVisibility(View.VISIBLE);
                        gv_header.setVisibility(View.GONE);
                        Glide.with(ForumDetailActivity.this).load(attachment.get(0)).into(iv_headerSinglePic);
                        lookBigPic(attachment);
                    }else {
                        iv_headerSinglePic.setVisibility(View.GONE);
                        //gv_header.setVisibility(View.VISIBLE);
                        ll_picWrapper.setVisibility(View.VISIBLE);
                        for (int i = 0; i <attachment.size() ; i++) {
                            ImageView imageView = new ImageView(ForumDetailActivity.this);
                            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,
                                    LinearLayout.LayoutParams.WRAP_CONTENT);//两个400分别为添加图片的大小
                            imageView.setLayoutParams(params);
                            imageView.setScaleType(ImageView.ScaleType.CENTER);
                            Glide.with(ForumDetailActivity.this).load(attachment.get(i)).into(imageView);
                            ll_picWrapper.addView(imageView);
                        }
                       /* GvForumDetailAdapter adapter = new GvForumDetailAdapter(ForumDetailActivity.this,attachment);
                        gv_header.setAdapter(adapter);*/
                        lookBigPic(attachment);
                    }
                    //显示评论
                    List<DetailForumInfo> comments = detailForumBean.getResult().getComments();
                    advertisement = detailForumBean.getResult().getCommunity().getAd();
                    adLink = detailForumBean.getResult().getCommunity().getAd_link();
                    String showPic = detailForumBean.getResult().getCommunity().getAd_button();
                    if (!TextUtils.isEmpty(showPic)){
                        showAdPic = Integer.parseInt(showPic);
                    }
                    if (comments!=null && comments.size()>0){
                        comments.get(0).setAdImg(advertisement);
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
                //删除帖子之后 帖子不存在
                }else if(error_code == 1){
                    pulltorefreshView.setVisibility(View.GONE);
                    no_data_rl.setVisibility(View.VISIBLE);
                    ll_comment.setVisibility(View.GONE);
                    common_no__data_tv.setText("内容已经被删除");
                }
            }
            @Override
            public void onEror(Call call, int statusCode, Exception e) {

            }
        });
    }
    private void setcontentLink() {
        CharSequence content = tv_content.getText();
        if (content instanceof Spannable) {
            int end = content.length();
            Spannable sp = (Spannable) content;
            URLSpan urls[] = sp.getSpans(0, end, URLSpan.class);
            SpannableStringBuilder style = new SpannableStringBuilder(content);
            style.clearSpans();
            for (URLSpan urlSpan : urls) {
                MyURLSpan myURLSpan = new MyURLSpan(urlSpan.getURL());
                style.setSpan(myURLSpan, sp.getSpanStart(urlSpan),
                        sp.getSpanEnd(urlSpan),
                        Spannable.SPAN_EXCLUSIVE_INCLUSIVE);
            }
            tv_content.setText(style);
        }
    }

    //查看大图
    private void lookBigPic(final List<String> attachment) {
        if (attachment.size()==1){
            iv_headerSinglePic.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(ForumDetailActivity.this, LookBigPicActivity.class);
                    List<EaluationPicBean> list = new ArrayList<>();
                    EaluationPicBean ealuationPicBean = new EaluationPicBean();
                    ealuationPicBean.imageUrl =attachment.get(0);
                    list.add(ealuationPicBean);
                    Bundle bundle = new Bundle();
                    bundle.putSerializable(LookBigPicActivity.PICDATALIST, (Serializable)list);
                    intent.putExtra("CURRENTITEM",0);
                    intent.putExtras(bundle);
                    startActivity(intent);
                    overridePendingTransition(0, 0);
                }
            });
        }else{
            gv_header.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    Intent intent = new Intent(ForumDetailActivity.this, LookBigPicActivity.class);
                    List<EaluationPicBean> list = new ArrayList<>();
                    for (int i = 0; i <attachment.size() ; i++) {
                        EaluationPicBean ealuationPicBean = new EaluationPicBean();
                        ealuationPicBean.imageUrl =attachment.get(i);
                        list.add(ealuationPicBean);
                    }
                    Bundle bundle = new Bundle();
                    bundle.putSerializable(LookBigPicActivity.PICDATALIST, (Serializable)list);
                    intent.putExtra("CURRENTITEM",position);
                    intent.putExtras(bundle);
                    startActivity(intent);
                    overridePendingTransition(0, 0);
                }
            });
        }
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
                userId = (String) SPUtils.get(ForumDetailActivity.this,"user_id","");
                if (TextUtils.isEmpty(userId)){
                    jumpActivity(ForumDetailActivity.this, LoginActivity.class);
                    return;
                }
                likeComment(position);
            }
        });
        //只有一张图片的时候点击事件
        iv_headerSinglePic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
        iv_head.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Bundle bundle = new Bundle();
                bundle.putString("user_id",userDetailId);
                jumpActivity(ForumDetailActivity.this, UserDetailActivity.class,bundle);
            }
        });
        tv_name.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Bundle bundle = new Bundle();
                bundle.putString("user_id",userDetailId);
                jumpActivity(ForumDetailActivity.this, UserDetailActivity.class,bundle);
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
                    infoList.get(position).setLike_status(like_status);
                    if (code == ApiConstant.SUCCESS_CODE){
                        if (like_status.equals("True")){
                            infoList.get(position).setLikes(Integer.parseInt(infoList.get(position).getLikes())+1+"");
                        }else{
                            infoList.get(position).setLikes(Integer.parseInt(infoList.get(position).getLikes())-1+"");
                        }
                        detailAdapter.notifyDataSetChanged();
                    }
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
    @OnClick({R.id.iv_back,R.id.tv_comment,R.id.ll_dianzan,R.id.ll_collection})
    public void click(View view){
        Bundle bundle = new Bundle();
        switch (view.getId()){
            case R.id.iv_back:
                finish();
                break;
            case R.id.tv_comment:
                userId = (String) SPUtils.get(this,"user_id","");
                if (TextUtils.isEmpty(userId)){
                    bundle.putString("intentFrom","forumDetail");
                    jumpActivity(this, LoginActivity.class,bundle);
                    return;
                }
                Intent intent = new Intent(ForumDetailActivity.this,ReplyForumActivity.class);
                intent.putExtra("discussionId",discussionId);
                bundle.putString("replyName","");
                startActivity(intent);
                break;
            case R.id.ll_dianzan:
                userId = (String) SPUtils.get(this,"user_id","");
                if (TextUtils.isEmpty(userId)){
                    bundle.putString("intentFrom","forumDetail");
                    jumpActivity(this, LoginActivity.class,bundle);
                    return;
                }
                discussionDianzan();
                break;
            case R.id.ll_collection:
                userId = (String) SPUtils.get(this,"user_id","");
                if (TextUtils.isEmpty(userId)){
                    bundle.putString("intentFrom","forumDetail");
                    jumpActivity(this, LoginActivity.class,bundle);
                    return;
                }
                collection();
                break;
        }
    }
    //收藏
    private void collection() {
        ApiForum.collectionDiscussion(ApiConstant.COLLECTION_COMMENT, userId, discussionId, new RequestCallBack<String>() {
            @Override
            public void onSuccess(Call call, Response response, String s) {
                try {
                    JSONObject jsonObject = new JSONObject(s);
                    int code = jsonObject.getInt("error_code");
                    String reason = jsonObject.getString("reason");
                    String like_status = jsonObject.getJSONObject("result").getString("collection_status");
                    if (code == ApiConstant.SUCCESS_CODE){
                        if (like_status.equals("True")){
                            iv_collection.setImageResource(R.mipmap.collection);
                            tv_collection.setText("已收藏");
                        }else{
                            iv_collection.setImageResource(R.mipmap.soucang);
                            tv_collection.setText("收藏");
                        }
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onEror(Call call, int statusCode, Exception e) {

            }
        });
    }
    //帖子点赞
    private void discussionDianzan() {
        ApiForum.likeDiscussion(ApiConstant.LIKE_COMMENT, userId, discussionId, new RequestCallBack<String>() {
            @Override
            public void onSuccess(Call call, Response response, String s) {
                try {
                    JSONObject jsonObject = new JSONObject(s);
                    int code = jsonObject.getInt("error_code");
                    String reason = jsonObject.getString("reason");
                    String like_status = jsonObject.getJSONObject("result").getString("like_status");
                    if (code == ApiConstant.SUCCESS_CODE){
                        if (like_status.equals("True")){
                            tv_dianzan.setText(Integer.parseInt(tv_dianzan.getText().toString())+1+"");
                            iv_dianzan.setImageResource(R.mipmap.dianzan_xuanzhong);
                        }else{
                            tv_dianzan.setText(Integer.parseInt(tv_dianzan.getText().toString())-1+"");
                            iv_dianzan.setImageResource(R.mipmap.dianzan_hui);
                        }
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onEror(Call call, int statusCode, Exception e) {

            }
        });
    }
    @Override
    protected void onDestroy() {
        super.onDestroy();
        localBroadcastManager.unregisterReceiver(broadcastReceiver);
    }
    public class MyURLSpan extends ClickableSpan {
        private String url;
        public MyURLSpan(String url) {
            this.url = url;
        }
        /**
         * @param arg0
         */
        @Override
        public void onClick(View arg0) {
            Intent intent = new Intent(ForumDetailActivity.this,WebviewActivity.class);
            Bundle bundle = new Bundle();
            bundle.putString("url",url);
            bundle.putString("title","详情");
            intent.putExtras(bundle);
            startActivity(intent,bundle);
        }
    }
}
