package com.chuangsheng.forum.api;

import com.chuangsheng.forum.callback.RequestCallBack;
import com.chuangsheng.forum.util.net.OkHttpManager;

import java.util.HashMap;
import java.util.Map;

public class ApiHome {
    /**
     * 获取新闻条目
     * @param callback
     */
    public static void GetNewsContent(String url,String userId,String catId,int page,int pageSize,int adPage,int adPageSize , RequestCallBack<String> callback){
        Map<String,String> params=new HashMap<>();
        params.put("userId",userId );
        params.put("catId",catId );
        params.put("page",page +"");
        params.put("pageSize",pageSize+"" );
        params.put("adPage",adPage+"" );
        params.put("adPageSize",adPageSize+"" );
        OkHttpManager.getInstance().postRequest(url,params,callback);
    }
}
