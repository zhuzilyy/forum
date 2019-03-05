package com.chuangsheng.forum.ui.mine.ui;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.chuangsheng.forum.R;
import com.chuangsheng.forum.api.ApiAccount;
import com.chuangsheng.forum.api.ApiConstant;
import com.chuangsheng.forum.api.ApiMine;
import com.chuangsheng.forum.base.BaseActivity;
import com.chuangsheng.forum.callback.RequestCallBack;
import com.chuangsheng.forum.dialog.CustomLoadingDialog;
import com.chuangsheng.forum.util.ToastUtils;
import com.chuangsheng.forum.view.MyCountDownTimer;

import org.json.JSONException;
import org.json.JSONObject;

import butterknife.BindView;
import butterknife.OnClick;
import okhttp3.Call;
import okhttp3.Response;

public class NewPhoneActivity extends BaseActivity{
    @BindView(R.id.tv_title)
    TextView tv_title;
    @BindView(R.id.et_phoneNum)
    EditText et_phoneNum;
    @BindView(R.id.et_confirmCode)
    EditText et_confirmCode;
    private MyCountDownTimer timer;
    private CustomLoadingDialog customLoadingDialog;
    private String userId;
    @Override
    protected void initViews() {
        tv_title.setText("修改手机号");
        BaseActivity.activityList.add(this);
        customLoadingDialog = new CustomLoadingDialog(this);
        Intent intent = getIntent();
        if (intent!=null){
            userId = intent.getExtras().getString("userId");
        }
    }
    @Override
    protected void initData() {

    }

    @Override
    protected void getResLayout() {
        setContentView(R.layout.activity_new_phone);
    }

    @Override
    protected void initListener() {

    }

    @Override
    protected void setStatusBarColor() {

    }
    @OnClick({R.id.btn_confirm, R.id.iv_back, R.id.btn_getConfirmCode})
    public void click(View view) {
        switch (view.getId()) {
            case R.id.btn_confirm:
                //jumpActivity(this,NewPhoneActivity.class);
                String phonenumber = et_phoneNum.getText().toString().trim();
                String confirmCode = et_confirmCode.getText().toString().trim();
                bindNewPhone(phonenumber, confirmCode);
                break;
            case R.id.iv_back:
                finish();
                break;
            case R.id.btn_getConfirmCode:
                String phoneNumber = et_phoneNum.getText().toString().trim();
                if (!TextUtils.isEmpty(phoneNumber)) {
                    if (phoneNumber.matches("^[1][3467589][0-9]{9}$")) {
                        timer = new MyCountDownTimer(60000, 1000, (Button) view);
                        timer.start();
                        // 向服务器请求验证码
                        getConfirmCode(phoneNumber);
                    } else {
                        ToastUtils.show(this, "手机号码格式不正确");
                    }
                } else {
                    ToastUtils.show(this, "手机号码不能为空");
                }
                break;

        }
    }
    //获取验证码
    private void getConfirmCode(String phoneNumber) {
        customLoadingDialog.show();
        ApiAccount.getConfirmCode(ApiConstant.SEND_CODE, phoneNumber, new RequestCallBack<String>() {
            @Override
            public void onSuccess(Call call, Response response, String s) {
                customLoadingDialog.dismiss();
                try {
                    JSONObject jsonObject = new JSONObject(s);
                    String reason = jsonObject.getString("reason");
                    ToastUtils.show(NewPhoneActivity.this, reason);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onEror(Call call, int statusCode, Exception e) {
                customLoadingDialog.dismiss();
                ToastUtils.show(NewPhoneActivity.this, "发送失败");
            }
        });
    }
    //验证手机号
    private void bindNewPhone(String phoneNum, String confirmCode) {
        customLoadingDialog.show();
        ApiMine.bindNewPhone(ApiConstant.BIND_NEW_PHONE, userId,phoneNum, confirmCode, new RequestCallBack<String>() {
            @Override
            public void onSuccess(Call call, Response response, String s) {
                customLoadingDialog.dismiss();
                try {
                    JSONObject jsonObject = new JSONObject(s);
                    String reason = jsonObject.getString("reason");
                    int code = jsonObject.getInt("error_code");
                    if (code == ApiConstant.SUCCESS_CODE) {
                        if (timer!=null){
                            timer.cancel();
                        }
                        finish();
                    } else {
                        ToastUtils.show(NewPhoneActivity.this, reason);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onEror(Call call, int statusCode, Exception e) {
                Log.i("tag", e.getMessage());
                customLoadingDialog.dismiss();
            }
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (timer!=null){
            timer.cancel();
        }
    }
}
