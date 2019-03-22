package com.chuangsheng.forum.ui.forum.bean;

/**
 * Created by Administrator on 2019/3/22.
 */

public class ReplyForumResult {
    private DetailForumInfo comment;
    private String total_point;
    private String point;
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

    public DetailForumInfo getComment() {
        return comment;
    }

    public void setComment(DetailForumInfo comment) {
        this.comment = comment;
    }
}
