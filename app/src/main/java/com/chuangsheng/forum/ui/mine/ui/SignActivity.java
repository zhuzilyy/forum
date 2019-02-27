package com.chuangsheng.forum.ui.mine.ui;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.chuangsheng.forum.R;
import com.chuangsheng.forum.base.BaseActivity;

import butterknife.BindView;
import butterknife.OnClick;

public class SignActivity extends BaseActivity {
    @BindView(R.id.tv_title)
    TextView tv_title;
    @BindView(R.id.tv_right)
    TextView tv_right;
    @BindView(R.id.et_sign)
    EditText et_sign;
    private Intent intent;
    @Override
    protected void initViews() {
        tv_right.setText("完成");
        tv_title.setText("设置个性签名");
        intent = getIntent();
        if (intent!=null){
            String sign = intent.getStringExtra("sign");
            et_sign.setText(sign);
        }
    }
    @Override
    protected void initData() {

    }

    @Override
    protected void getResLayout() {
        setContentView(R.layout.activity_sign);
    }

    @Override
    protected void initListener() {

    }

    @Override
    protected void setStatusBarColor() {

    }
    @OnClick({R.id.iv_back,R.id.tv_right})
    public void click(View view){
        switch (view.getId()){
            case R.id.iv_back:
                finish();
                break;
            case R.id.tv_right:
                String sign = et_sign.getText().toString();
                Intent intent = new Intent();
                intent.putExtra("sign",sign);
                setResult(RESULT_OK,intent);
                finish();
                break;
        }
    }
}
