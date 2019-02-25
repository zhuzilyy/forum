package com.chuangsheng.forum.ui.account.ui;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
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
    private String userId;
    private CustomLoadingDialog customLoadingDialog;
    @Override
    protected void initViews() {
        tv_title.setText("设置");
        customLoadingDialog = new CustomLoadingDialog(this);
        BaseActivity.activityList.add(this);
    }
    @Override
    protected void initData() {
        intent = getIntent();
        if (intent!=null){
            Bundle extras = intent.getExtras();
            userId = extras.getString("userId");
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
                finish();
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
        ApiAccount.setNickName(ApiConstant.SET_NICKNAME, userId, name, new RequestCallBack<String>() {
            @Override
            public void onSuccess(Call call, Response response, String s) {
                customLoadingDialog.dismiss();
                try {
                    JSONObject jsonObject = new JSONObject(s);
                    int code = jsonObject.getInt("error_code");
                    if (code == ApiConstant.SUCCESS_CODE){
                        jumpActivity(SetNameActivity.this, MainActivity.class);
                        BaseActivity.finishAll();
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
}
