package com.chuangsheng.forum.api;

import com.chuangsheng.forum.callback.RequestCallBack;
import com.chuangsheng.forum.ui.loan.bean.LoanBean;
import com.chuangsheng.forum.ui.loan.bean.LoanResult;
import com.chuangsheng.forum.util.OkHttpManager;

import java.util.HashMap;
import java.util.Map;

public class ApiLoan {
    /**
     * 获取贷款列表
     * @param callback
     */
    public static void getLoanList(String url,String page,RequestCallBack<LoanBean> callback){
        Map<String,String> params = new HashMap<>();
        params.put("page",page);
        OkHttpManager.getInstance().postRequest(url,params,callback);
    }
    /**
     * 获取贷款跑马灯title
     * @param callback
     */
    public static void getLoanTitle(String url,String attribute,RequestCallBack<String> callback){
        Map<String,String> params = new HashMap<>();
        params.put("attribute",attribute);
        OkHttpManager.getInstance().postRequest(url,params,callback);
    }
}
