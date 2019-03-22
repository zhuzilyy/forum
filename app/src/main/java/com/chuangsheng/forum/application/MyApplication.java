package com.chuangsheng.forum.application;

import android.app.Application;
import android.util.Log;

import com.chuangsheng.forum.util.SPUtils;
import com.chuangsheng.forum.util.ToastUtils;

import cn.jpush.android.api.JPushInterface;

public class MyApplication extends Application {
    public static MyApplication myApplication;
    @Override
    public void onCreate() {
        super.onCreate();
        JPushInterface.setDebugMode(true);
        JPushInterface.init(this);

    }
    public static MyApplication getInstance(){
        if (myApplication == null){
            synchronized (MyApplication.class){
                if (myApplication == null){
                    myApplication = new MyApplication();
                }
            }
        }
        return myApplication;
    }
}
