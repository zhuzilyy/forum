package com.chuangsheng.forum.api;

import android.text.TextUtils;

import com.chuangsheng.forum.callback.RequestCallBack;
import com.chuangsheng.forum.ui.home.bean.HomeFroumBean;
import com.chuangsheng.forum.ui.mine.bean.CollectionBean;
import com.chuangsheng.forum.ui.mine.bean.CommonNewsBean;
import com.chuangsheng.forum.ui.mine.bean.HistoryBean;
import com.chuangsheng.forum.ui.mine.bean.MyFroumBean;
import com.chuangsheng.forum.ui.mine.bean.MyReplyFroumBean;
import com.chuangsheng.forum.ui.mine.bean.SystemNewsBean;
import com.chuangsheng.forum.ui.mine.bean.UserBean;
import com.chuangsheng.forum.util.OkHttpManager;

import java.util.HashMap;
import java.util.Map;

public class ApiMine {
    /**
     * 我的帖子
     * @param callback
     */
    public static void getMyFroumList(String url,String user_id,String page, RequestCallBack<MyFroumBean> callback){
        Map<String,String> params = new HashMap<>();
        params.put("user_id",user_id);
        params.put("page",page);
        OkHttpManager.getInstance().postRequest(url,params,callback);
    }
    /**
     * 我的回帖
     * @param callback
     */
    public static void getMyReplyFroumList(String url,String user_id,String page, RequestCallBack<MyReplyFroumBean> callback){
        Map<String,String> params = new HashMap<>();
        params.put("user_id",user_id);
        params.put("page",page);
        OkHttpManager.getInstance().postRequest(url,params,callback);
    }
    /**
     * 获取个人信息
     * @param callback
     */
    public static void getUserInfo(String url,String user_id,RequestCallBack<UserBean> callback){
        Map<String,String> params = new HashMap<>();
        params.put("user_id",user_id);
        OkHttpManager.getInstance().postRequest(url,params,callback);
    }
    /**
     * 修改个人信息
     * @param callback
     */
    public static void changeUserInfo(String url,String user_id,String img,String sex,String brith,String description,RequestCallBack<UserBean> callback){
        Map<String,String> params = new HashMap<>();
        params.put("user_id",user_id);
        params.put("img",img);
        params.put("sex",sex);
        params.put("brith",brith);
        params.put("description",description);
        OkHttpManager.getInstance().postRequest(url,params,callback);
    }
    /**
     * 获取我的收藏的列表
     * @param callback
     */
    public static void myCollection(String url,String user_id,String page,RequestCallBack<CollectionBean> callback){
        Map<String,String> params = new HashMap<>();
        params.put("user_id",user_id);
        params.put("page",page);
        OkHttpManager.getInstance().postRequest(url,params,callback);
    }
    /**
     * 获取我的浏览历史
     * @param callback
     */
    public static void myHistory(String url,String user_id,String page,RequestCallBack<CollectionBean> callback){
        Map<String,String> params = new HashMap<>();
        params.put("user_id",user_id);
        params.put("page",page);
        OkHttpManager.getInstance().postRequest(url,params,callback);
    }
    /**
     * 意见反馈
     * @param callback
     */
    public static void feedback(String url,String user_id,String imgs,String content,String contact,RequestCallBack<String> callback){
        Map<String,String> params = new HashMap<>();
        params.put("user_id",user_id);
        params.put("imgs",imgs);
        params.put("content",content);
        params.put("contact",contact);
        OkHttpManager.getInstance().postRequest(url,params,callback);
    }
    /**
     * 删除浏览历史
     * @param callback
     */
    public static void deleteHistory(String url,String user_id,String discussion_history_ids,RequestCallBack<String> callback){
        Map<String,String> params = new HashMap<>();
        params.put("user_id",user_id);
        params.put("discussion_history_ids",discussion_history_ids);
        OkHttpManager.getInstance().postRequest(url,params,callback);
    }
    /**
     * 绑定新的手机号
     * @param callback
     */
    public static void bindNewPhone(String url,String user_id,String phone_number,String code,RequestCallBack<String> callback){
        Map<String,String> params = new HashMap<>();
        params.put("user_id",user_id);
        params.put("phone_number",phone_number);
        params.put("code",code);
        OkHttpManager.getInstance().postRequest(url,params,callback);
    }
    /**
     * 我的消息
     * @param callback
     */
    public static void getCommonNews(String url,String user_id,String page,RequestCallBack<CommonNewsBean> callback){
        Map<String,String> params = new HashMap<>();
        params.put("user_id",user_id);
        params.put("page",page);
        OkHttpManager.getInstance().postRequest(url,params,callback);
    }
    /**
     * 系统消息
     * @param callback
     */
    public static void getSystemNews(String url,String page,RequestCallBack<SystemNewsBean> callback){
        Map<String,String> params = new HashMap<>();
        params.put("page",page);
        OkHttpManager.getInstance().postRequest(url,params,callback);
    }
    /**
     * 联系我们
     * @param callback
     */
    public static void contactUs(String url,RequestCallBack<String> callback){
        OkHttpManager.getInstance().getRequest(url,callback);
    }
}
