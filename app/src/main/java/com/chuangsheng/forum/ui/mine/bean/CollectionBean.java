package com.chuangsheng.forum.ui.mine.bean;

import java.util.List;

public class CollectionBean {
    private int error_code;
    private List<CollectionInfo> result;
    public int getError_code() {
        return error_code;
    }

    public void setError_code(int error_code) {
        this.error_code = error_code;
    }

    public List<CollectionInfo> getResult() {
        return result;
    }

    public void setResult(List<CollectionInfo> result) {
        this.result = result;
    }
}
