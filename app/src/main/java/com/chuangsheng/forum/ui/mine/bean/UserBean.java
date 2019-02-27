package com.chuangsheng.forum.ui.mine.bean;

public class UserBean {
    private int code;
    private String reason;
    private UserResult result;
    public int getCode() {
        return code;
    }
    public void setCode(int code) {
        this.code = code;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

    public UserResult getResult() {
        return result;
    }

    public void setResult(UserResult result) {
        this.result = result;
    }
}
