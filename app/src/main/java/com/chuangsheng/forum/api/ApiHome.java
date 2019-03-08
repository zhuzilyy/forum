package com.chuangsheng.forum.api;

import com.chuangsheng.forum.callback.RequestCallBack;
import com.chuangsheng.forum.ui.home.bean.BannerBean;
import com.chuangsheng.forum.ui.home.bean.HomeFroumBean;
import com.chuangsheng.forum.util.OkHttpManager;

import java.util.HashMap;
import java.util.Map;

public class ApiHome {
    /**
     * 获取banner
     * @param callback
     */
    public static void getBanner(String url, RequestCallBack<BannerBean> callback){
        OkHttpManager.getInstance().getRequest(url,callback);
    }
    /**
     * 获取首页帖子列表
     * @param callback
     */
    public static void getHomeFroumList(String url,String category,String page,String community_id, RequestCallBack<HomeFroumBean> callback){
        Map<String,String> params = new HashMap<>();
        params.put("category",category);
        params.put("page",page);
        params.put("community_id",community_id);
        OkHttpManager.getInstance().postRequest(url,params,callback);
    }
    public static void login(String url,String phoneNum, RequestCallBack<String> callback){
        Map<String,String> params=new HashMap<>();
        params.put("phone_number",phoneNum);
        OkHttpManager.getInstance().postRequest(url,params,callback);
    }
}
