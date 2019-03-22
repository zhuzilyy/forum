package com.chuangsheng.forum.api;

import com.chuangsheng.forum.callback.RequestCallBack;
import com.chuangsheng.forum.ui.community.bean.HotWordBean;
import com.chuangsheng.forum.ui.forum.bean.CommunityBean;
import com.chuangsheng.forum.ui.forum.bean.DetailForumBean;
import com.chuangsheng.forum.ui.forum.bean.ReplyForumBean;
import com.chuangsheng.forum.ui.home.bean.HomeFroumBean;
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
    public static void publishComment(String url,String tag,String user_id,String discussion_id,String imgs,String content, RequestCallBack<ReplyForumBean> callback){
        Map<String,String> params = new HashMap<>();
        params.put("user_id",user_id);
        if (tag.equals("comment")){
            params.put("discussion_id",discussion_id);
        }else{
            params.put("comment_id",discussion_id);
        }
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
        params.put("comment_id",comment_id);
        OkHttpManager.getInstance().postRequest(url,params,callback);
    }
    /**
     * 帖子点赞
     * @param callback
     */
    public static void likeDiscussion(String url,String user_id,String discussion_id, RequestCallBack<String> callback){
        Map<String,String> params = new HashMap<>();
        params.put("user_id",user_id);
        params.put("discussion_id",discussion_id);
        OkHttpManager.getInstance().postRequest(url,params,callback);
    }
    /**
     * 帖子收藏
     * @param callback
     */
    public static void collectionDiscussion(String url,String user_id,String discussion_id, RequestCallBack<String> callback){
        Map<String,String> params = new HashMap<>();
        params.put("user_id",user_id);
        params.put("discussion_id",discussion_id);
        OkHttpManager.getInstance().postRequest(url,params,callback);
    }
    /**
     * 获取热词
     * @param callback
     */
    public static void getHotSearch(String url,RequestCallBack<HotWordBean> callback){
        OkHttpManager.getInstance().getRequest(url,callback);
    }
    /**
     * 搜索帖子
     * @param callback
     */
    public static void searchForums(String url,String keyword,String page,RequestCallBack<HomeFroumBean> callback){
        Map<String,String> params = new HashMap<>();
        params.put("keyword",keyword);
        params.put("page",page);
        OkHttpManager.getInstance().postRequest(url,params,callback);
    }

    /**
     * 批量删除收藏
     * @param callback
     */
    public static void cancleCollectionDiscussion(String url,String discussion_collections, RequestCallBack<String> callback){
        Map<String,String> params = new HashMap<>();
        params.put("discussion_collections",discussion_collections);
        OkHttpManager.getInstance().postRequest(url,params,callback);
    }
    /**
     * 批量删除浏览记录
     * @param callback
     */
    public static void cancleHistory(String url,String discussion_history_ids, RequestCallBack<String> callback){
        Map<String,String> params = new HashMap<>();
        params.put("discussion_history_ids",discussion_history_ids);
        OkHttpManager.getInstance().postRequest(url,params,callback);
    }
    /**
     * 批量删除浏览我的帖子
     * @param callback
     */
    public static void deletMyForums(String url,String discussion_ids, RequestCallBack<String> callback){
        Map<String,String> params = new HashMap<>();
        params.put("discussion_ids",discussion_ids);
        OkHttpManager.getInstance().postRequest(url,params,callback);
    }
}
