package com.chuangsheng.forum.api;

import com.chuangsheng.forum.callback.RequestCallBack;
import com.chuangsheng.forum.ui.forum.bean.CommunityBean;
import com.chuangsheng.forum.ui.forum.bean.DetailForumBean;
import com.chuangsheng.forum.util.OkHttpManager;

import java.util.HashMap;
import java.util.Map;

public class ApiForum {
    /**
     * 获取社区
     * @param callback
     */
    public static void getCommunityList(String url,String page, RequestCallBack<CommunityBean> callback){
        Map<String,String> params = new HashMap<>();
        params.put("page",page);
        OkHttpManager.getInstance().postRequest(url,params,callback);
    }
    /**
     * 发布帖子
     * @param callback
     */
    public static void postFroum(String url,String user_id,String community_id,String subject,String content,String imgs, RequestCallBack<String> callback){
        Map<String,String> params = new HashMap<>();
        params.put("user_id",user_id);
        params.put("community_id",community_id);
        params.put("subject",subject);
        params.put("content",content);
        params.put("imgs",imgs);
        OkHttpManager.getInstance().postRequest(url,params,callback);
    }
    /**
     * 帖子详情
     * @param callback
     */
    public static void getFroumDetail(String url,String user_id,String discussion_id,String page, RequestCallBack<DetailForumBean> callback){
        Map<String,String> params = new HashMap<>();
        params.put("user_id",user_id);
        params.put("discussion_id",discussion_id);
        params.put("page",page);
        OkHttpManager.getInstance().postRequest(url,params,callback);
    }
    /**
     * 发布评论
     * @param callback
     */
    public static void publishComment(String url,String user_id,String discussion_id,String imgs,String content, RequestCallBack<String> callback){
        Map<String,String> params = new HashMap<>();
        params.put("user_id",user_id);
        params.put("discussion_id",discussion_id);
        params.put("imgs",imgs);
        params.put("content",content);
        OkHttpManager.getInstance().postRequest(url,params,callback);
    }
    /**
     * 评论点赞
     * @param callback
     */
    public static void likeComment(String url,String user_id,String comment_id, RequestCallBack<String> callback){
        Map<String,String> params = new HashMap<>();
        params.put("user_id",user_id);
        params.put("discussion_id",comment_id);
        OkHttpManager.getInstance().postRequest(url,params,callback);
    }
}