package com.chuangsheng.forum.ui.forum.bean;

/**
 * Created by Administrator on 2019/3/22.
 */

public class ReplyForumBean {
    private int error_code;
    private ReplyForumResult result;
    public int getError_code() {
        return error_code;
    }

    public void setError_code(int error_code) {
        this.error_code = error_code;
    }

    public ReplyForumResult getResult() {
        return result;
    }

    public void setResult(ReplyForumResult result) {
        this.result = result;
    }
}
