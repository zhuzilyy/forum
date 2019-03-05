package com.chuangsheng.forum.ui.account.ui;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.chuangsheng.forum.R;
import com.chuangsheng.forum.api.ApiAccount;
import com.chuangsheng.forum.api.ApiConstant;
import com.chuangsheng.forum.base.BaseActivity;
import com.chuangsheng.forum.callback.RequestCallBack;
import com.chuangsheng.forum.dialog.CustomLoadingDialog;
import com.chuangsheng.forum.ui.mine.ui.BindEmailActivity;
import com.chuangsheng.forum.ui.mine.ui.ChangePhoneActivity;
import com.chuangsheng.forum.ui.mine.ui.NewPhoneActivity;
import com.chuangsheng.forum.util.ToastUtils;
import com.chuangsheng.forum.view.MyCountDownTimer;

import org.json.JSONException;
import org.json.JSONObject;

import butterknife.BindView;
import butterknife.OnClick;
import okhttp3.Call;
import okhttp3.Response;

public class FindByEmailActivity extends BaseActivity {
    @BindView(R.id.tv_title)
    TextView tv_title;
    @BindView(R.id.et_email)
    EditText et_email;
    @BindView(R.id.et_confirmCode)
    EditText et_confirmCode;
    private MyCountDownTimer timer;
    private CustomLoadingDialog customLoadingDialog;
    @Override
    protected void initViews() {
        tv_title.setText("邮箱找回");
        BaseActivity.activityList.add(this);
        customLoadingDialog = new CustomLoadingDialog(this);
        BaseActivity.addActivity(FindByEmailActivity.this);

    }
    @Override
    protected void initData() {

    }

    @Override
    protected void getResLayout() {
        setContentView(R.layout.activity_find_by_email);
    }

    @Override
    protected void initListener() {

    }

    @Override
    protected void setStatusBarColor() {

    }
    @OnClick({R.id.btn_nextStep,R.id.iv_back,R.id.btn_getConfirmCode})
    public void click(View view){
        switch (view.getId()){
            case R.id.btn_nextStep:
                String emailNum = et_email.getText().toString();
                String confirmCode = et_confirmCode.getText().toString();
                bindEmail(emailNum,confirmCode);
                break;
            case R.id.iv_back:
                finish();
                break;
            case R.id.btn_getConfirmCode:
                String email = et_email.getText().toString();
                if (!TextUtils.isEmpty(email)) {
                    if (email.matches("^([a-z0-9A-Z]+[-|\\.]?)+[a-z0-9A-Z]@([a-z0-9A-Z]+(-[a-z0-9A-Z]+)?\\.)+[a-zA-Z]{2,}$")) {
                        timer = new MyCountDownTimer(60000, 1000, (Button) view);
                        timer.start();
                        // 向服务器请求验证码
                        getConfirmCode(email);
                    } else {
                        ToastUtils.show(this, "邮箱地址不对");
                    }
                } else {
                    ToastUtils.show(this, "邮箱不能为空");
                }
                break;
        }
    }
    //绑定email
    private void bindEmail(String emailNum, String confirmCode) {
        customLoadingDialog.show();
        ApiAccount.vertifyEmail(ApiConstant.VERTIFY_EMAIL, emailNum,confirmCode, new RequestCallBack<String>() {
            @Override
            public void onSuccess(Call call, Response response, String s) {
                customLoadingDialog.dismiss();
                JSONObject jsonObject = null;
                try {
                    jsonObject = new JSONObject(s);
                    int code = jsonObject.getInt("error_code");
                    String  reason = jsonObject.getString("reason");
                    ToastUtils.show(FindByEmailActivity.this,reason);
                    if (code == ApiConstant.SUCCESS_CODE){
                        if (timer!=null){
                            timer.cancel();
                        }
                        String user_id = jsonObject.getJSONObject("result").getString("user_id");
                        if (TextUtils.isEmpty(user_id)){
                            ToastUtils.show(FindByEmailActivity.this,"该邮箱未绑定过手机号");
                            return;
                        }else{
                            Bundle bundle = new Bundle();
                            bundle.putString("userId", user_id);
                            jumpActivity(FindByEmailActivity.this, NewPhoneActivity.class, bundle);
                            finish();
                        }
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
    //获取验证码
    private void getConfirmCode(String email) {
        customLoadingDialog.show();
        ApiAccount.getEmailConfirmCode(ApiConstant.SEND_CODE, email, new RequestCallBack<String>() {
            @Override
            public void onSuccess(Call call, Response response, String s) {
                customLoadingDialog.dismiss();
                try {
                    JSONObject jsonObject = new JSONObject(s);
                    String reason = jsonObject.getString("reason");
                    ToastUtils.show(FindByEmailActivity.this,reason);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
            @Override
            public void onEror(Call call, int statusCode, Exception e) {
                customLoadingDialog.dismiss();
                ToastUtils.show(FindByEmailActivity.this,"验证码发送失败");
                //Toast.makeText(BindEmailActivity.this, "验证码发送失败", Toast.LENGTH_SHORT).show();
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
