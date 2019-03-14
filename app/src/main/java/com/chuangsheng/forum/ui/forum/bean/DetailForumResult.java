package com.chuangsheng.forum.ui.forum.bean;

import java.util.List;

public class DetailForumResult {
    private DetailForumDiscussion discussion;
    private DetailForumBanner community;
    private List<DetailForumInfo> comments;
    private String total_point;
    private String point;
    public DetailForumDiscussion getDiscussion() {
        return discussion;
    }

    public void setDiscussion(DetailForumDiscussion discussion) {
        this.discussion = discussion;
    }

    public DetailForumBanner getCommunity() {
        return community;
    }

    public void setCommunity(DetailForumBanner community) {
        this.community = community;
    }

    public List<DetailForumInfo> getComments() {
        return comments;
    }

    public void setComments(List<DetailForumInfo> comments) {
        this.comments = comments;
    }
    public String getTotal_point() {
        return total_point;
    }

    public void setTotal_point(String total_point) {
        this.total_point = total_point;
    }

    public String getPoint() {
        return point;
    }

    public void setPoint(String point) {
        this.point = point;
    }
}
