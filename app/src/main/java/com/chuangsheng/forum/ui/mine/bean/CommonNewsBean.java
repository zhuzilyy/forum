package com.chuangsheng.forum.ui.mine.bean;

import java.util.List;

public class CommonNewsBean {
    private int code;
    private List<CommonNewsInfo> result;
    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public List<CommonNewsInfo> getResult() {
        return result;
    }

    public void setResult(List<CommonNewsInfo> result) {
        this.result = result;
    }
}
