package com.chuangsheng.forum.dialog;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.text.TextUtils;
import android.widget.ImageView;
import android.widget.TextView;

import com.chuangsheng.forum.R;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ForumDialog extends Dialog {
    @BindView(R.id.iv_froum)
    ImageView iv_froum;
    @BindView(R.id.tv_title)
    TextView tv_title;
    private int imgRes;
    private String title;
    public ForumDialog(@NonNull Context context) {
        super(context);
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_forum);
        ButterKnife.bind(this);
        initData();
    }

    private void initData() {
        if (!TextUtils.isEmpty(imgRes+"")){
            iv_froum.setImageResource(imgRes);
        }
        if (!TextUtils.isEmpty(title)){
            tv_title.setText(title);
        }

    }
    public void setImageRes(int imgRes){
        this.imgRes = imgRes;

    }
    public void setTitle(String title){
        this.title = title;

    }
}
