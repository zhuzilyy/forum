package com.chuangsheng.forum.api;

import android.text.TextUtils;

import com.chuangsheng.forum.callback.RequestCallBack;
import com.chuangsheng.forum.ui.home.bean.HomeFroumBean;
import com.chuangsheng.forum.ui.mine.bean.MyFroumBean;
import com.chuangsheng.forum.ui.mine.bean.MyReplyFroumBean;
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
}
