package com.chuangsheng.forum.ui.mine.ui;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.AbsListView;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.chuangsheng.forum.R;
import com.chuangsheng.forum.base.BaseActivity;

import butterknife.BindView;
import butterknife.OnClick;

public class SignActivity extends BaseActivity {
    @BindView(R.id.tv_title)
    TextView tv_title;
    @BindView(R.id.iv_back)
    ImageView iv_back;
    @BindView(R.id.tv_right)
    TextView tv_right;
    @BindView(R.id.et_sign)
    EditText et_sign;
    @BindView(R.id.tv_totalNum)
    TextView tv_totalNum;
    private Intent intent;
    @Override
    protected void initViews() {
        tv_right.setText("完成");
        tv_title.setText("设置个性签名");
        iv_back.setImageResource(R.mipmap.fanhui);
        intent = getIntent();
        if (intent!=null){
            String sign = intent.getStringExtra("sign");
            et_sign.setText(sign);
            tv_totalNum.setText((30-sign.length())+"");
        }
        BaseActivity.activityList.add(this);
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
        et_sign.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }
            @Override
            public void afterTextChanged(Editable s) {
                int length = s.toString().length();
                tv_totalNum.setText((30-length)+"");
            }
        });
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
