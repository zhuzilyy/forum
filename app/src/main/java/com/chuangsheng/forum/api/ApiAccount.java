package com.chuangsheng.forum.api;

import com.chuangsheng.forum.callback.RequestCallBack;
import com.chuangsheng.forum.ui.mine.bean.UserBean;
import com.chuangsheng.forum.util.OkHttpManager;

import java.util.HashMap;
import java.util.Map;

public class ApiAccount {
    /**
     * 获取验手机证码
     * @param callback
     */
    public static void getConfirmCode(String url,String confirmType, RequestCallBack<String> callback){
        Map<String,String> params=new HashMap<>();
        params.put("phone_number",confirmType);
        OkHttpManager.getInstance().postRequest(url,params,callback);
    }
    /**
     * 获取验邮箱证码
     * @param callback
     */
    public static void getEmailConfirmCode(String url,String confirmType, RequestCallBack<String> callback){
        Map<String,String> params=new HashMap<>();
        params.put("email",confirmType);
        OkHttpManager.getInstance().postRequest(url,params,callback);
    }
    /**
     * 登录
     * @param callback
     */
    public static void login(String url,String phoneNum,String confirmCode, RequestCallBack<String> callback){
        Map<String,String> params=new HashMap<>();
        params.put("phone_number",phoneNum);
        params.put("code",confirmCode);
        OkHttpManager.getInstance().postRequest(url,params,callback);
    }
    /**
     * 绑定email
     * @param callback
     */
    public static void bindEmail(String url,String user_id,String email,String confirmCode, RequestCallBack<String> callback){
        Map<String,String> params=new HashMap<>();
        params.put("user_id",user_id);
        params.put("email",email);
        params.put("code",confirmCode);
        OkHttpManager.getInstance().postRequest(url,params,callback);
    }
    /**
     * 设置昵称
     * @param callback
     */
    public static void setNickName(String url,String user_id,String nickName,RequestCallBack<UserBean> callback){
        Map<String,String> params=new HashMap<>();
        params.put("user_id",user_id);
        params.put("value",nickName);
        params.put("attribute","username");
        OkHttpManager.getInstance().postRequest(url,params,callback);
    }
    /**
     *验证邮箱
     * @param callback
     */
    public static void vertifyEmail(String url,String email,String code,RequestCallBack<String> callback){
        Map<String,String> params=new HashMap<>();
        params.put("email",email);
        params.put("code",code);
        OkHttpManager.getInstance().postRequest(url,params,callback);
    }
}
