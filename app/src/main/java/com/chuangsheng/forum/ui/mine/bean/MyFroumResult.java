package com.chuangsheng.forum.ui.mine.bean;

import com.chuangsheng.forum.ui.home.bean.HomeFroumInfo;

import java.util.List;

public class MyFroumResult {
    private List<HomeFroumInfo> discussions;
    public List<HomeFroumInfo> getDiscussions() {
        return discussions;
    }

    public void setDiscussions(List<HomeFroumInfo> discussions) {
        this.discussions = discussions;
    }
}
