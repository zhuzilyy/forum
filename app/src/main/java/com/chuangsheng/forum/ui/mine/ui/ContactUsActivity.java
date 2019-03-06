package com.chuangsheng.forum.ui.mine.ui;

import android.graphics.Color;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v4.content.ContextCompat;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.chuangsheng.forum.R;
import com.chuangsheng.forum.base.BaseActivity;
import com.chuangsheng.forum.util.StatusBarUtils;

import butterknife.BindView;
import butterknife.OnClick;
import flyn.Eyes;

public class ContactUsActivity extends BaseActivity {
    @BindView(R.id.iv_back)
    ImageView iv_back;
    @BindView(R.id.tv_title)
    TextView tv_title;
    @BindView(R.id.title)
    RelativeLayout title;
    @Override
    protected void initViews() {
        tv_title.setText("联系我们");
        tv_title.setTextColor(getResources().getColor(R.color.white));
        title.setBackgroundColor(getResources().getColor(R.color.colorAccent));
        iv_back.setImageResource(R.mipmap.fanhui_white);
    }
    @Override
    protected void initData() {

    }
    @Override
    protected void getResLayout() {
        //设置全屏
        setContentView(R.layout.activity_contact_us);
    }
    @Override
    protected void initListener() {

    }
    @Override
    protected void setStatusBarColor() {
        if (Build.VERSION.SDK_INT>=Build.VERSION_CODES.KITKAT){
            setTranslucentStatus();
        }
    }
    //沉浸式管理
    public void setTranslucentStatus(){
        Window window = getWindow();
        WindowManager.LayoutParams params = window.getAttributes();
        final int status = WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS;
        params.flags |= status;
        window.setAttributes(params);
    }
    @OnClick({R.id.iv_back})
    public void click(View view){
        switch (view.getId()){
            case R.id.iv_back:
                finish();
                break;
        }
    }

}
