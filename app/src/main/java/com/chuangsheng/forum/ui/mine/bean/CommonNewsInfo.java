package com.chuangsheng.forum.ui.mine.bean;

public class CommonNewsInfo {
    private String notification_id;
    private MyReplyFroumInfo comment;
    public String getNotification_id() {
        return notification_id;
    }

    public void setNotification_id(String notification_id) {
        this.notification_id = notification_id;
    }

    public MyReplyFroumInfo getComment() {
        return comment;
    }

    public void setComment(MyReplyFroumInfo comment) {
        this.comment = comment;
    }
}
