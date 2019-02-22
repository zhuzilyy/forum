package com.chuangsheng.forum.ui.account.ui;

import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;

import com.chuangsheng.forum.R;
import com.chuangsheng.forum.base.BaseActivity;
import com.chuangsheng.forum.ui.mine.ui.BindEmailActivity;
import com.chuangsheng.forum.util.ToastUtils;

import butterknife.BindView;
import butterknife.OnClick;

public class LoginActivity extends BaseActivity {
    @BindView(R.id.et_confirmCode)
    EditText et_confirmCode;
    @Override
    protected void initViews() {

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

    }
    @OnClick({R.id.ll_findPwd,R.id.btn_login,R.id.btn_getConfirmCode})
    public void click(View view){
        switch (view.getId()){
            case R.id.ll_findPwd:
                jumpActivity(this,FindByEmailActivity.class);
                break;
            case R.id.btn_login:
                jumpActivity(this,BindEmailActivity.class);
                break;
            case R.id.btn_getConfirmCode:
                String phoneNumber = et_confirmCode.getText().toString().trim();
                if (!TextUtils.isEmpty(phoneNumber)) {
                    if (phoneNumber.matches("^[1][3467589][0-9]{9}$")) {
//                        timer = new MyCountDownTimer(60000, 1000, (Button) view);
//                        timer.start();
                        // 向服务器请求验证码
                        //getConfirmCode(phoneNumber);
                    } else {
                        ToastUtils.show(this, "手机号码格式不正确");
                    }
                } else {
                    ToastUtils.show(this, "手机号码不能为空");
                }
                break;
        }
    }
}
