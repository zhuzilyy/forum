package com.chuangsheng.forum.ui.mine.bean;

import java.util.List;

public class SystemNewsBean {
    private int code;
    private List<SystemNewsInfo> result;
    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public List<SystemNewsInfo> getResult() {
        return result;
    }

    public void setResult(List<SystemNewsInfo> result) {
        this.result = result;
    }
}
