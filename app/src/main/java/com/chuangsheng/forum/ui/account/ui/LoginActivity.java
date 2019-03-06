package com.chuangsheng.forum.ui.account.ui;

import android.os.Build;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;

import com.chuangsheng.forum.MainActivity;
import com.chuangsheng.forum.R;
import com.chuangsheng.forum.api.ApiAccount;
import com.chuangsheng.forum.api.ApiConstant;
import com.chuangsheng.forum.base.BaseActivity;
import com.chuangsheng.forum.callback.RequestCallBack;
import com.chuangsheng.forum.dialog.CustomLoadingDialog;
import com.chuangsheng.forum.ui.mine.ui.BindEmailActivity;
import com.chuangsheng.forum.util.SPUtils;
import com.chuangsheng.forum.util.ToastUtils;
import com.chuangsheng.forum.view.MyCountDownTimer;

import org.json.JSONException;
import org.json.JSONObject;

import butterknife.BindView;
import butterknife.OnClick;
import okhttp3.Call;
import okhttp3.Response;

public class LoginActivity extends BaseActivity {
    @BindView(R.id.et_confirmCode)
    EditText et_confirmCode;
    @BindView(R.id.et_phoneNum)
    EditText et_phoneNum;
    private MyCountDownTimer timer;
    private CustomLoadingDialog customLoadingDialog;
    @Override
    protected void initViews() {
        customLoadingDialog = new CustomLoadingDialog(this);
        BaseActivity.addActivity(LoginActivity.this);
        String userId = (String) SPUtils.get(this,"user_id","");
        if (!TextUtils.isEmpty(userId)){
            jumpActivity(this, MainActivity.class);
            finish();
        }
        BaseActivity.activityList.add(this);
    }
    @Override
    protected void initData() {

    }
    @Override
    protected void getResLayout() {
        setContentView(R.layout.activity_login);
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
    @OnClick({R.id.ll_findPwd,R.id.btn_login,R.id.btn_getConfirmCode,R.id.iv_back})
    public void click(View view){
        switch (view.getId()){
            case R.id.ll_findPwd:
                jumpActivity(this,FindByEmailActivity.class);
                break;
            case R.id.btn_login:
                String phoneNum = et_phoneNum.getText().toString();
                String confirmCode = et_confirmCode.getText().toString();
                login(phoneNum,confirmCode);
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
            case R.id.iv_back:
                finish();
                break;
        }
    }
    //登录
    private void login(String phoneNum, String confirmCode) {
        customLoadingDialog.show();
        ApiAccount.login(ApiConstant.LOGIN, phoneNum, confirmCode, new RequestCallBack<String>() {
            @Override
            public void onSuccess(Call call, Response response, String s) {
                customLoadingDialog.dismiss();
                try {
                    JSONObject jsonObject = new JSONObject(s);
                    String reason = jsonObject.getString("reason");
                    int code = jsonObject.getInt("error_code");
                    if (code == ApiConstant.SUCCESS_CODE){
                        JSONObject result = jsonObject.getJSONObject("result");
                        String user_id = result.getString("user_id");
                        String email = result.getString("email");
                        String user_points = result.getString("user_points");
                        String username = result.getString("username");
                        String img = result.getString("img");
                        SPUtils.put(LoginActivity.this,"user_id",user_id);
                        SPUtils.put(LoginActivity.this,"headAvatar",img);
                        SPUtils.put(LoginActivity.this,"user_points",user_points);
                        if (TextUtils.isEmpty(email)){
                            Bundle bundle = new Bundle();
                            bundle.putString("userId",user_id);
                            bundle.putString("intentFrom","login");
                            jumpActivity(LoginActivity.this, BindEmailActivity.class,bundle);
                            timer.cancel();
                        }else{
                            jumpActivity(LoginActivity.this, MainActivity.class);
                        }
                    }else{
                        ToastUtils.show(LoginActivity.this,reason);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onEror(Call call, int statusCode, Exception e) {
                Log.i("tag",e.getMessage());
                customLoadingDialog.dismiss();
            }
        });
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
                    ToastUtils.show(LoginActivity.this,reason);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
            @Override
            public void onEror(Call call, int statusCode, Exception e) {
                customLoadingDialog.dismiss();
                ToastUtils.show(LoginActivity.this,"发送失败");
            }
        });
    }
}
