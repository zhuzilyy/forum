package com.chuangsheng.forum.ui.mine.ui;

import android.os.Build;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.chuangsheng.forum.R;
import com.chuangsheng.forum.api.ApiConstant;
import com.chuangsheng.forum.api.ApiMine;
import com.chuangsheng.forum.base.BaseActivity;
import com.chuangsheng.forum.callback.RequestCallBack;
import com.chuangsheng.forum.dialog.CustomLoadingDialog;

import org.json.JSONException;
import org.json.JSONObject;

import butterknife.BindView;
import butterknife.OnClick;
import okhttp3.Call;
import okhttp3.Response;

public class ContactUsActivity extends BaseActivity {
    @BindView(R.id.iv_back)
    ImageView iv_back;
    @BindView(R.id.tv_title)
    TextView tv_title;
    @BindView(R.id.tv_qq)
    TextView tv_qq;
    @BindView(R.id.tv_wechat)
    TextView tv_wechat;
    @BindView(R.id.tv_phone)
    TextView tv_phone;
    @BindView(R.id.iv_erweima)
    ImageView iv_erweima;
    @BindView(R.id.title)
    RelativeLayout title;
    private CustomLoadingDialog customLoadingDialog;
    @Override
    protected void initViews() {
        tv_title.setText("联系我们");
        tv_title.setTextColor(getResources().getColor(R.color.white));
        title.setBackgroundColor(getResources().getColor(R.color.colorAccent));
        iv_back.setImageResource(R.mipmap.fanhui_white);
        customLoadingDialog = new CustomLoadingDialog(this);
        customLoadingDialog.show();
    }
    @Override
    protected void initData() {
        getData();
    }

    private void getData() {
        ApiMine.contactUs(ApiConstant.CONTACT_US, new RequestCallBack<String>() {
            @Override
            public void onSuccess(Call call, Response response, String result) {
                customLoadingDialog.dismiss();
                try {
                    JSONObject jsonObject = new JSONObject(result);
                    int code = jsonObject.getInt("error_code");
                    if (code == ApiConstant.SUCCESS_CODE){
                        JSONObject jsonResult = jsonObject.getJSONObject("result");
                        String qq = jsonResult.getString("qq");
                        String wechat = jsonResult.getString("wechat");
                        String phone = jsonResult.getString("phone");
                        String qrcode = jsonResult.getString("qrcode");
                        tv_qq.setText(qq);
                        tv_wechat.setText(wechat);
                        tv_phone.setText(phone);
                        Glide.with(ContactUsActivity.this).load(qrcode).into(iv_erweima);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onEror(Call call, int statusCode, Exception e) {
                customLoadingDialog.dismiss();
            }
        });
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
