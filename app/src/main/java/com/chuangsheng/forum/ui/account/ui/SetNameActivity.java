package com.chuangsheng.forum.ui.account.ui;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.chuangsheng.forum.MainActivity;
import com.chuangsheng.forum.R;
import com.chuangsheng.forum.api.ApiAccount;
import com.chuangsheng.forum.api.ApiConstant;
import com.chuangsheng.forum.base.BaseActivity;
import com.chuangsheng.forum.callback.RequestCallBack;
import com.chuangsheng.forum.dialog.CustomLoadingDialog;
import com.chuangsheng.forum.ui.mine.bean.UserBean;
import com.chuangsheng.forum.ui.mine.bean.UserResult;
import com.chuangsheng.forum.util.SPUtils;
import com.chuangsheng.forum.util.ToastUtils;
import com.chuangsheng.forum.view.MyCountDownTimer;
import com.google.gson.JsonObject;

import org.json.JSONException;
import org.json.JSONObject;

import butterknife.BindView;
import butterknife.OnClick;
import okhttp3.Call;
import okhttp3.Response;

public class SetNameActivity extends BaseActivity {
    @BindView(R.id.tv_title)
    TextView tv_title;
    @BindView(R.id.et_name)
    EditText et_name;
    private Intent intent;
    private String userId,intentFrom;
    private CustomLoadingDialog customLoadingDialog;
    @Override
    protected void initViews() {
        tv_title.setText("设置");
        customLoadingDialog = new CustomLoadingDialog(this);
        BaseActivity.addActivity(this);
    }
    @Override
    protected void initData() {
        intent = getIntent();
        if (intent!=null){
            Bundle extras = intent.getExtras();
            userId = extras.getString("userId");
            intentFrom = extras.getString("intentFrom");
        }
    }
    @Override
    protected void getResLayout() {
        setContentView(R.layout.activity_set_name);
    }

    @Override
    protected void initListener() {

    }

    @Override
    protected void setStatusBarColor() {

    }
    @OnClick({R.id.btn_save,R.id.iv_back})
    public void click(View view){
        switch (view.getId()){
            case R.id.iv_back:
                String userId= (String) SPUtils.get(this,"user_id","");
                if (TextUtils.isEmpty(userId)){
                    ToastUtils.show(this,"必须绑定邮箱");
                }else{
                    finish();
                }
                break;
            case R.id.btn_save:
                String name = et_name.getText().toString();
                if (TextUtils.isEmpty(name)){
                    ToastUtils.show(SetNameActivity.this,"昵称不能为空");
                    return;
                }
                setName(name);
                break;
        }
    }
    //设置名称
    private void setName(String name) {
        customLoadingDialog.show();
        ApiAccount.setNickName(ApiConstant.SET_NICKNAME, userId, name, new RequestCallBack<UserBean>() {
            @Override
            public void onSuccess(Call call, Response response, UserBean userBean) {
                customLoadingDialog.dismiss();
                int code = userBean.getCode();
                if (code == ApiConstant.SUCCESS_CODE){
                    UserResult result = userBean.getResult();
                    String username = result.getUsername();
                    String headAvatar = result.getImg();
                    String user_id = result.getId();
                    String user_points = result.getPoints();
                    String phone_number = result.getPhone_number();
                    SPUtils.put(SetNameActivity.this,"username",username);
                    SPUtils.put(SetNameActivity.this,"user_id",user_id);
                    SPUtils.put(SetNameActivity.this,"headAvatar",headAvatar);
                    SPUtils.put(SetNameActivity.this,"user_points",user_points);
                    SPUtils.put(SetNameActivity.this,"phone_number",phone_number);
                    if (intentFrom.equals("splash")){
                        jumpActivity(SetNameActivity.this, MainActivity.class);
                        BaseActivity.finishAll();
                    }else{
                        finish();
                    }

                }
            }
            @Override
            public void onEror(Call call, int statusCode, Exception e) {
                customLoadingDialog.dismiss();
            }
        });
    }
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            String userId= (String) SPUtils.get(this,"user_id","");
            if (TextUtils.isEmpty(userId)){
                ToastUtils.show(this,"必须绑定邮箱");
            }
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }
}
