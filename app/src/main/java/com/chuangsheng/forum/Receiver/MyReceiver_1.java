package com.chuangsheng.forum.Receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.widget.Toast;

/**
 * Created by tianou on 2019/3/22.
 */

public class MyReceiver_1 extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        String action = intent.getAction();
        switch (action){
            case "cn.jpush.android.intent.REGISTRATION":
                //Toast.makeText(context, " <!--Required 用户注册 SDK 的 intent-->", Toast.LENGTH_SHORT).show();
                break;
            case "cn.jpush.android.intent.MESSAGE_RECEIVED":
              //  Toast.makeText(context, " 自定义消息 用户接收 SDK 消息的 intent", Toast.LENGTH_SHORT).show();

                break;
            case "n.jpush.android.intent.NOTIFICATION_RECEIVED":
               // Toast.makeText(context, "  用户接收 SDK 通知栏信息的 ", Toast.LENGTH_SHORT).show();
                break;
            case "cn.jpush.android.intent.NOTIFICATION_OPENED":
               // Toast.makeText(context, "<!--Required 用户打开自定义通知栏的 intent-->", Toast.LENGTH_SHORT).show();
                break;
            case "cn.jpush.android.intent.CONNECTION":
              // Toast.makeText(context, "<!-- 接收网络变化 连接/断开 since 1.6.3 -->", Toast.LENGTH_SHORT).show();
                break;
        }
    }
}
