package com.chuangsheng.forum.Receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.widget.Toast;

/**
 * Created by tianou on 2019/3/22.
 */

public class MyWakedResultReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        String action = intent.getAction();
        if("cn.jpush.android.intent.WakedReceiver".equalsIgnoreCase(action)){
            Toast.makeText(context, "机关拉起应用", Toast.LENGTH_SHORT).show();
        }
    }
}
